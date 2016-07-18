package com.techm.trackingtool.admin.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.techm.trackingtool.admin.bean.CRinfo;
import com.techm.trackingtool.admin.bean.CrEmailFrequency;
import com.techm.trackingtool.admin.bean.EmailFrequency;
import com.techm.trackingtool.admin.bean.Leave;
import com.techm.trackingtool.admin.bean.MailDetails;
import com.techm.trackingtool.admin.bean.TicketInfo;
import com.techm.trackingtool.admin.bean.User;
import com.techm.trackingtool.util.LindeMap;
import com.techm.trackingtool.util.LindeToolLogManager;
import com.techm.trackingtool.util.TrackingToolConstant;

@Repository
public class LeavePlanDAO {
	
	private static LindeToolLogManager lindeLogMgr = new LindeToolLogManager(LeavePlanDAO.class.getName());
	
	/*private static Configuration cfg;
	private static SessionFactory factory;
	 
	public EmailDAO()
	{
		System.out.println("Inside DeleteUserDAO");
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		System.out.println("After cfg");
		//cfg.addFile("UserDetails.hbm.xml");
		System.out.println("After hbm");
		factory = cfg.buildSessionFactory();

	}*/
	
	
	@Autowired
	private Environment environment;
	
	 @Autowired
	 private SessionFactory sessionFactory;
	 
	 private String smtp;
	    private String username;
	    private String password;
	 
	    public Session getSession() {
	    	Session session=null;
	    	try{
	    		
	    		session = sessionFactory.getCurrentSession();
	    	}
	    	catch(Exception ex)
	    	{
	    		lindeLogMgr.logStackTrace("ERROR", ex.getMessage(), ex);
	    	}
	        return session;
	    }
	
	public Leave submitLeaves(User user,Leave leaveDetails)
	{
		leaveDetails.setUserID(user.getUserID());
		getSession().save(leaveDetails);
		return leaveDetails;
		
	}
	
	public boolean checkLeaves(User user,String startDate,String endDate)
	{
		StringBuffer sqlQuery=new StringBuffer("select count(*) from Leave where userID='"+user.getUserID()+"' AND ((to_date('"+startDate+"','yyyy-mm-dd')) BETWEEN (to_date(leaveStartDate,'yyyy-mm-dd')) and (to_date(leaveEndDate,'yyyy-mm-dd')) or (to_date('"+endDate+"','yyyy-mm-dd')) BETWEEN (to_date(leaveStartDate,'yyyy-mm-dd')) and (to_date(leaveEndDate,'yyyy-mm-dd')))");
		try{
			
			Query query=getSession().createQuery(sqlQuery.toString());
			for (Iterator it = query.iterate(); it.hasNext();){
				 Object row = (Object) it.next();
				 if(row.toString().equals("0"))
				 {
					 return true;
				 }
			 }
			
			
		}catch(Exception e)
		{
			lindeLogMgr.logStackTrace("ERROR", "Exception in checkLeaves method", e);
		}
		
		return false;
	}
	
	public ArrayList getLeaveDetails()
	{
		ArrayList leaves=new ArrayList();
		StringBuffer SQL_QUERY = new StringBuffer(" from LeaveReasonsMaster");
		try{
			
			Query query = getSession().createQuery(SQL_QUERY.toString());
			 for (Iterator it = query.iterate(); it.hasNext();){
				 Object row = (Object) it.next();
				 leaves.add(row);
			 }
			lindeLogMgr.logMessage("INFO", "Value of Leave::::::"+leaves.toString());
		}catch(Exception e)
		{
			lindeLogMgr.logStackTrace("ERROR", "Error in fetching leave data", e);
		}
		return leaves;
	}
	
	
	public void sendNotificationForLeave() throws Exception
	{

		String tolist="";
		String cclist="";
		Session sesssion = getSession();
		
		ArrayList<User> usersList =(ArrayList<User>)sesssion.createCriteria(User.class).list();
		Map<String,User> userMap = new HashMap<String,User>();
		for (User user : usersList) {
			userMap.put(user.getRoleId().toString(), user);
		}
		
		
		for (Map.Entry<String, User> entry : userMap.entrySet()) {
			//System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
			if(entry.getKey().equals("2"))
			{
				cclist=cclist+";"+entry.getValue().toString();
			}
			
		}
		
		lindeLogMgr.logMessage("DEBUG", "sendLeaveEmailNotifications :: ");
		LindeMap leaveMap=new LindeMap();
		StringBuffer SQL_QUERY = new StringBuffer("select leave1.leaveStartDate,user1.emailId from Leave leave1,User user1 where leave1.userID=user1.userID and leave1.leaveStartDate=(TO_CHAR((CURRENT_DATE()+7), 'YYYY-MM-DD'))");
		 Query query = getSession().createQuery(SQL_QUERY.toString());
		 for (Iterator it = query.iterate(); it.hasNext();)
		 {
			 Object[] row = (Object[]) it.next();
			 leaveMap.put(row[0], row[1]);
			 tolist=row[1].toString();
			 sendEmailsForLeave(leaveMap,tolist,cclist);
			 
		 }			
		 
		 
	}
	public void sendEmailsForLeave(LindeMap leave,String toEmailList,String ccEmailList)
	{
		

		lindeLogMgr.logMessage("INFO", "Entered into LeaveMailNotification(");
		try
		{
		Properties props =new Properties();
		
		//String smtp="BLREXCHMBX002.TechMahindra.com";//props.getProperty("smtp");
	 //   String username="PV95235";//props.getProperty("username");
	  //  String password="Pras5678$";//props.getProperty("password");
		lindeLogMgr.logMessage("DEBUG", "smtp value is ::: "+environment.getRequiredProperty("smtp"));
	    props.put("smtp", environment.getRequiredProperty("smtp"));
	    props.put("username", environment.getRequiredProperty("EmailAccount"));
	    props.put("password", environment.getRequiredProperty("EmailPassword"));
	    
		javax.mail.Session mailSession = javax.mail.Session.getInstance(props, null);
		
	  /*  String smtp="BLREXCHMBX002.TechMahindra.com";//props.getProperty("smtp");
	    String username="PV95235";//props.getProperty("username");
	    String password="Pras5678$";//props.getProperty("password");*/
	    MimeMessage message=new MimeMessage(mailSession);
		message.setFrom(new InternetAddress(environment.getRequiredProperty("EmailFrom")));
		message.setSentDate(new GregorianCalendar().getTime());

		StringBuilder strBuilder = new StringBuilder();		
		strBuilder.append("<html><head></head><body>");
		strBuilder.append(" <br><br>");
		strBuilder.append("******* This is an auto-generated mail. Please do not reply********<br><br><br>");
		strBuilder.append("<b><center>Leave Reminder Mail</center></b><br>");
		strBuilder.append("This is to remind you about the leave start from"+leave.getId()+"<br><br>");
		strBuilder.append("</body></html>");
		
		message.setSubject("Leave Reminder Mail");
		message.setContent(strBuilder.toString(), "text/html");
		
		
		
		message.setRecipients(Message.RecipientType.TO,"prasad.vankadara@TechMahindra.com");
		message.setRecipients(Message.RecipientType.CC,"SK00355036@TechMahindra.com");
		message.saveChanges();
		Transport transport = null; 
		transport = mailSession.getTransport("smtp");
		transport.connect(smtp,username,password);
		Address[] address = null;
		address = message.getAllRecipients();
		
		transport.sendMessage(message, address);
		}
		catch(Exception e)
		{
			lindeLogMgr.logStackTrace("ERROR", "Exception while sending email in sendCREmails() "+e.getMessage(), e);
		}
		

		
		
	}

}
