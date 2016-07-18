package com.techm.trackingtool.admin.dao;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.techm.trackingtool.admin.bean.Application;
import com.techm.trackingtool.admin.bean.Feedback;
import com.techm.trackingtool.admin.bean.Roles;
import com.techm.trackingtool.admin.bean.User;
import com.techm.trackingtool.admin.vo.AdminVO;
import com.techm.trackingtool.util.LindeList;
import com.techm.trackingtool.util.LindeMap;

@Repository("adminDAO")
public class AdminDAO {


	/*private static Configuration cfg;
	private static SessionFactory factory;
	public AdminDAO(){
		System.out.println("Inside AdminDAO");
		cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		System.out.println("After cfg");
		//cfg.addFile("UserDetails.hbm.xml");
		System.out.println("After hbm");
		factory = cfg.buildSessionFactory();

		}*/
	
	@Autowired
	 private SessionFactory sessionFactory;
	 
	 public Session getSession() {
	        return sessionFactory.getCurrentSession();
	    }
	
	public int getAdminInfo(AdminVO adminInfo){

	//	Session session = factory.openSession();
	//	Transaction txn = session.beginTransaction();
			
		Query queryObj = getSession().getNamedQuery("selPendingUser");
		queryObj.setString("statusVal", "Pending");
		List user = (List)queryObj.list();
		System.out.println("admin info method ");
		int s=user.size();
 	  // 	txn.commit();
 	 //  	session.close();  
		return s;
	}
	
	public LindeList getApplZeroOwnersCount(){
		LindeList applicationList = new LindeList();
		//Session session = factory.openSession();
	//	Transaction txn = session.beginTransaction();
		try{
		Query queryObj = getSession().getNamedQuery("selApplications");
		ArrayList applications = (ArrayList)queryObj.list();
		
		
		for(int i=0;i<applications.size();i++)
		{
			LindeMap applicationMap=new LindeMap();
			Application application=new Application();
		
			application = (Application)applications.get(i);
			
			Set userSet=new HashSet();
			userSet=application.getUserid();
			int noofusers=userSet.size();
			
			String userList="";
			Iterator iterator=userSet.iterator();
			//ArrayList arrayList=(ArrayList)userSet.toArray();
			if(noofusers==1)
			   {
				   User user=(User)iterator.next();
				   
				   if(!userList.equals(""))
				   {
					   userList=userList+", "+user.getFirstName();  
					   
				   }
				   else
				   {
					   userList=user.getFirstName();
					   //System.out.println(""+user.getFirstName()+" added to list");
				   }
				   
				   
			   }
			
		if(noofusers<=1 )
		{
		if(noofusers==0){userList="No Owners";}
		applicationMap.put("applicationBean", application);
		applicationMap.put("userName",userList);
		applicationList.add(applicationMap);
		}
			
			
			
		}

		}catch(Exception accessException)
		{
			System.out.println("Exception is "+accessException);
			accessException.printStackTrace();
			
		}
		//txn.commit();
		//session.close();
		System.out.println("*********AdminDAO getApplZeroOwnersCount exit--*****-->");
		return applicationList;
	}
	
	
	
	public LindeList getRoles(){
		LindeList rolesList = new LindeList();
	//	Session session = factory.openSession();
	//	Transaction txn = session.beginTransaction();
		List roles =  getSession().createQuery("from Roles order by roleId").list();
		
		Roles role = new Roles();
		for(int i = 0 ; i<roles.size();i++){
			role = (Roles)roles.get(i);
			rolesList.add(role);
		}
 	  // 	txn.commit();session.close();  
		return rolesList;
	}
	
	
	public void submitFeedback(User user,Feedback feedback) throws Exception {
		
	///	Session session = factory.openSession();
	//	Transaction txn = session.beginTransaction();
		System.out.println("in submit Feedback");
		try{
			getSession().save(feedback);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		sendEMailFeedback(user,feedback);
 	  // 	txn.commit();
 	 //
	//	session.close();  
		
	}
	
	public void sendEMailFeedback(User user,Feedback feedback) throws Exception {

		String smtpProperty = "";
		boolean flag=true;
		Transport tr = null;
		Address[] address = null;
		String Text= "";
		String from= "";
		String MID= "";
		String TO= "";
		String Cc= "";
		String Bcc= "";
		Date L_Date=null;
		String Body= "";
		String Subject= "";
		String username= "";
		String password= "";   
		String PROPERTIES_FILE = "Email.properties";
		Properties props = new Properties ();
		
		ClassLoader loader = ClassLoader.getSystemClassLoader(); 
		InputStream in = EmailDAO.class.getClassLoader().
		getResourceAsStream("Email.properties");
	        if (in != null)
	        {
	           	props.load (in); 
	        	System.out.println("Props file loaded");
	        }
					try {
				javax.mail.Session mailSession = javax.mail.Session.getInstance(props, null);
		        String smtp=props.getProperty("smtp");
	            username=props.getProperty("username");
	            password=props.getProperty("password");
	            System.out.println("SMTP Address------>"+smtp);	
	            
	            MimeMessage message=new MimeMessage(mailSession);
	            System.out.println("Inside sendEMails------>");
				message.setFrom(new InternetAddress("LindeTool"));
				System.out.println("Before Setting TO"+TO);
				UserDetailDAO userDetailDAO=new UserDetailDAO();
				LindeList adminUsers=userDetailDAO.getAdminUser();
				for(Iterator iter=adminUsers.iterator();iter.hasNext();)
				{
					User adminUser=(User)iter.next();
					
					if(TO.equals(""))
					{
						TO=adminUser.getEmailId();
					}
					else if(!TO.equals(""))
					{
						TO=TO+","+adminUser.getEmailId();
					}
					
				}
				if(Cc.equals(""))
				{
					Cc=feedback.getMailid();
				}
				else if(!Cc.equals(""))
				{
					Cc=Cc+","+feedback.getMailid();
				}
				message.setRecipients(Message.RecipientType.TO,TO);
				System.out.println("Before Setting CC:"+Cc+" TO :"+TO);
				message.setRecipients(Message.RecipientType.CC,Cc);
				System.out.println("Before Setting Date");
				message.setSentDate(new GregorianCalendar().getTime());
				L_Date=new GregorianCalendar().getTime();

				message.setSubject("Feedback Mail");

				StringBuffer buffer = new StringBuffer();		
					
				System.out.println("Mail id is "+feedback.getMailid());
				buffer.append("<html><head></head>");
				buffer.append("<body><table border=0 cellspacing=0 cellpadding=0 width=\"100%\"> ");
				buffer.append(" <tr><td>&nbsp<br></td></tr>");
				buffer.append("<tr><td height= 20  valign=top><font face=\"Trebuchet MS\" size=2px>");
				buffer.append("******* This is an auto-generated mail. Please do not reply********</font></td>");
				buffer.append("</tr>");
				buffer.append("<tr>");
				buffer.append("<td align=center><center><table align=center ><tr><td height= 40  align=center> <font face=\"Trebuchet MS\" size=4px><b>Feedback Mail</b></font></td></tr></table><center></td>");
				buffer.append("<td  align= right ><table align=center> <tr><td></td></tr></table></td></tr>");
				buffer.append("<table border=0 cellspacing=0 cellpadding=0 width=\"100%\"> ");
				buffer.append("<tr><td height= 20  valign=top><font face=\"Trebuchet MS\" size=2px>");
				buffer.append("This is to inform you that <b>"+user.getFirstName()+" </b> has been given the following feedback for LindeTool</font></td>");
				buffer.append("</tr> <tr><td>&nbsp</td></tr> ");
						
				buffer.append("<tr><td height= 20  valign=top><font face=\"Trebuchet MS\" size=2px>");
				buffer.append("<b>User Name&nbsp&nbsp&nbsp&nbsp: </b></font>");
				buffer.append("<font face=\"Trebuchet MS\" size=2px>"+user.getFirstName()+" </font></td></tr> <tr><td>&nbsp</td></tr>");
				buffer.append("<tr><td height= 20  valign=top><font face=\"Trebuchet MS\" size=2px>");
				buffer.append("<b>Feedback&nbsp&nbsp&nbsp&nbsp: </b></font>");
				buffer.append("<font face=\"Trebuchet MS\" size=2px>"+feedback.getDescription()+" </font></td></tr> <tr><td>&nbsp</td></tr>");
						
				buffer.append("</table></body></html>");
				message.setContent(buffer.toString(), "text/html");
				message.saveChanges();
					
				System.out.println("Before Getting transport");   
						   
				tr = mailSession.getTransport("smtp");
				System.out.println("After setting transport");    
				tr.connect(smtp,username,password);
				address = message.getAllRecipients();
				System.out.println("Before send message");   
				tr.sendMessage(message, address);
				System.out.println("After send message"); 
				
				}
				catch (Exception e) {
				flag=false;
				e.printStackTrace();
				throw new Exception(e.getMessage());
				}
				finally{
				
				}
			}

	
}
