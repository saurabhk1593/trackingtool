package com.techm.trackingtool.admin.dao;

import java.io.InputStream;
import java.sql.Timestamp;
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
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import oracle.net.aso.p;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;
import com.techm.trackingtool.admin.bean.Application;
import com.sun.org.apache.regexp.internal.recompile;
import com.techm.trackingtool.admin.bean.Application;
import com.techm.trackingtool.admin.bean.CRinfo;
import com.techm.trackingtool.admin.bean.CrEmailFrequency;
import com.techm.trackingtool.admin.bean.CrMailDetails;
import com.techm.trackingtool.admin.bean.EmailFrequency;
import com.techm.trackingtool.admin.bean.Leave;
import com.techm.trackingtool.admin.bean.MailDetails;
import com.techm.trackingtool.admin.bean.Module;
import com.techm.trackingtool.admin.bean.TicketInfo;
import com.techm.trackingtool.admin.bean.User;
import com.techm.trackingtool.util.LindeList;
import com.techm.trackingtool.util.LindeToolLogManager;
import com.techm.trackingtool.util.TrackingToolConstant;

@Repository
public class EmailDAO {
	
	private static LindeToolLogManager lindeLogMgr = new LindeToolLogManager(EmailDAO.class.getName());
	 
	@Autowired
		private Environment environment;
	
	private String smtp;
    private String username;
    private String password;
    
	 @Autowired
	 private SessionFactory sessionFactory;
	 
	
	 
	    public Session getSession() {
	    	Session session=null;
	    	try{
	    		
	    		session = sessionFactory.getCurrentSession();
	    	//	if(session == null)
	    	//		session =sessionFactory.openSession();
	    	}
	    	catch(Exception ex)
	    	{
	    		lindeLogMgr.logStackTrace("ERROR", ex.getMessage(), ex);
	    	}
	        return session;
	    }
		
public EmailFrequency SubmitEmailSetting(EmailFrequency emailFrequency){
		
	getSession().save(emailFrequency);
   return emailFrequency;	 
	}

public ArrayList getEmailConfigList(String priority)
{
	ArrayList list=new ArrayList();
	
	
	try {
		StringBuffer SQL_QUERY = new StringBuffer("select monday_email,tuesday_email,wednesday_email,thursday_email,friday_email,tm_cc,pm_cc,pgm_cc from EmailFrequency");
		if(priority==null||priority.equals(""))
		{
			 SQL_QUERY.append("where priority='"+priority+"'");
		}
		list=(ArrayList) getSession().createQuery(SQL_QUERY.toString()).list();
		
	} catch (Exception e) {
		// TODO: handle exception
	}
	return list;
}

public void deleteEmailSetting(String priority){
	  
	LindeList severitySetting = new LindeList();
	Query queryObj = getSession().getNamedQuery("selSeveritySetting");
	ArrayList severityList = (ArrayList)queryObj.list();
	System.out.println("<---In deleteEmailSetting---->");
	
	for(int i=0;i<severityList.size();i++)
	{
		severitySetting.add(severityList.get(i));			
		
	}
	for(int k=0;k<severitySetting.size();k++)
	{
	EmailFrequency emailFrequency=(EmailFrequency)severitySetting.get(k);
	String severityid=emailFrequency.getId();
		if(priority.equals(severityid))
		{
			getSession().delete(emailFrequency);
		}
	}
}


public EmailFrequency getEmailSetting(String priority){
	
	  
	LindeList severitySetting = new LindeList();
	Query queryObj = getSession().getNamedQuery("selSeveritySetting");
	ArrayList severityList = (ArrayList)queryObj.list();
	EmailFrequency emailFrequency=new EmailFrequency();
	
	for(int i=0;i<severityList.size();i++)
	{
	EmailFrequency tempemailFrequency=(EmailFrequency)severityList.get(i);
	String severityid=tempemailFrequency.getPriority();
		if(priority.equals(severityid))
		{
		emailFrequency=tempemailFrequency;
		
		System.out.println("priority selected"+emailFrequency.getMonday_email());}
	}

	System.out.println("*********Problem in getEmailSetting--*****-->");
return emailFrequency;
}


public LindeList MailToBeSend()
{
	LindeList severitySetting = new LindeList();
	System.out.println("<---inside MailToBeSend----> ");
	
	if(getSession() != null){

		Query queryObj = getSession().getNamedQuery("selSeveritySetting");
		ArrayList severityList = (ArrayList)queryObj.list();
		for(int i=0;i<severityList.size();i++)
		{
			severitySetting.add(severityList.get(i));
		}
		
	}
	return severitySetting;
}


public LindeList TTsMailSent()
{
	LindeList severitySetting = new LindeList();
	Query queryObj = getSession().getNamedQuery("selMailDetails");
	ArrayList severityList = (ArrayList)queryObj.list();
	System.out.println("<---in TTsMailSent---->");
	
	for(int i=0;i<severityList.size();i++)
	{
		severitySetting.add((MailDetails)severityList.get(i));		
	}
	System.out.println("<---in TTsMailSent ----severitySetting size >>"+severitySetting.size());
	return severitySetting;
}

public void UpdateMailsSent(MailDetails mailDetails)
{

	System.out.println("<---in UpdateMailsSent---->");
	
	boolean status=false;
	LindeList ttlist=TTsMailSent();
	
	if(!ttlist.isEmpty()){
		for(Iterator iterator=ttlist.iterator();iterator.hasNext();)
		{
			MailDetails ticketInfo=(MailDetails)iterator.next();
			if(ticketInfo.getIncidentID().equals(mailDetails.getIncidentID())){
				System.out.println("tt no exists in db "+mailDetails.getIncidentID());
				status=true;
			}
		}
	}
	
	if(status==false){
		getSession().save(mailDetails);
	}
	else
		getSession().update(mailDetails);	
}

public void sendNotification() throws Exception{
	{
		lindeLogMgr.logMessage("DEBUG", "sendEmailNotifications :: ");
		   
		Session sesssion = getSession();
		
		ArrayList<User> usersList =(ArrayList<User>)sesssion.createCriteria(User.class).list();
		Map<String,User> userMap = new HashMap<String,User>();
		for (User user : usersList) {
			userMap.put(user.getLastName().toUpperCase()+", "+user.getFirstName().toUpperCase(), user);
		}
		
		
		ArrayList<EmailFrequency> EmailFreequencyList =(ArrayList<EmailFrequency>)sesssion.createCriteria(EmailFrequency.class).list();
		
		Map<String,EmailFrequency> EmailFrequencyMap = new HashMap<String,EmailFrequency>();
		for (EmailFrequency EmailFrequency : EmailFreequencyList) {
			//crEmailFrequencyMap.put(crEmailFrequency.getPriority().replace(" ", ""), crEmailFrequency);
			EmailFrequencyMap.put(EmailFrequency.getPriority(), EmailFrequency);
		}

		ArrayList<String> phases = new ArrayList<String>();
		phases.add(TrackingToolConstant.ASSIGNED);
		phases.add(TrackingToolConstant.WIP);
		phases.add(TrackingToolConstant.P_CUSTOMER);
		
		List<TicketInfo> incidentsList =(ArrayList<TicketInfo>)getSession().createCriteria(CRinfo.class).add(Restrictions.in("status", phases)).list();
		lindeLogMgr.logMessage("DEBUG", "Size of the CRList is :: "+incidentsList.size());
		
		int i=0;
		for (Iterator iterator = incidentsList.iterator(); iterator.hasNext();) {
			TicketInfo incidentinfo = (TicketInfo) iterator.next();
			lindeLogMgr.logMessage("DEBUG", "cRinfo.getChangeId() :: "+incidentinfo.getIncidentID());
			i++;
			User userObj = userMap.get(incidentinfo.getAssigneeFullname());
			if(incidentinfo.getPriority() != null && userObj != null)
			{
				lindeLogMgr.logMessage("DEBUG", "cRinfo.getAssignee() :: "+incidentinfo.getAssigneeFullname());
				
				StringBuilder ccEmailListBuilder = new StringBuilder(); 
				
				EmailFrequency emailFreqObj = EmailFrequencyMap.get(incidentinfo.getPriority());
				
				if(emailFreqObj.getPm_cc().equalsIgnoreCase("Y"))
				{
					lindeLogMgr.logMessage("DEBUG", "userObj.getLocation() :: "+userObj.getLocation() );
				//	List<User> ccEmail_PM_List =(ArrayList<User>) getSession().createCriteria(User.class).add(Restrictions.eq("location", userObj.getLocation())).add(Restrictions.eq("roleId", 2)).list();
					for (User user : usersList) {
						if(user.getRoleId().intValue() == 2 )
						  ccEmailListBuilder.append(user.getEmailId()).append(";");					
					}
				}
				if(emailFreqObj.getPgm_cc().equalsIgnoreCase("Y"))
				{
					//List<User> ccEmail_PGM_List =(ArrayList<User>) getSession().createCriteria(User.class).add(Restrictions.eq("roleId", 3)).list();
					for (User user : usersList) {
						if(user.getRoleId().intValue() == 3 )
						  ccEmailListBuilder.append(user.getEmailId()).append(";");
					}
				}
				
				
				
				try {
					
					List<MailDetails> incidentMailDetObj =(ArrayList<MailDetails>) getSession().createCriteria(MailDetails.class).add(Restrictions.eq("incidentID", incidentinfo.getIncidentID())).list();
					
					
					if(incidentMailDetObj.size() > 0)
					{
						lindeLogMgr.logMessage("DEBUG", "Inside if condition crMailDetObj.size() ::: "+incidentMailDetObj.size());
						
						for (MailDetails MailDetails2 : incidentMailDetObj) 
						{
							
							String mailDate=MailDetails2.getMaildate();
							Date date=new Date();
							
							String day = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date);
							lindeLogMgr.logMessage("INFO", "The value of day is:::::"+day);
							
												
							if(incidentinfo.getPriority().equalsIgnoreCase(TrackingToolConstant.PRIORITY_1))
							{
							
									if(day.equalsIgnoreCase("Monday") && emailFreqObj.getMonday_email().equalsIgnoreCase("Y"))
									{
										sendEMails(incidentinfo,userObj.getEmailId(),ccEmailListBuilder.toString());
									}
									else if(day.equalsIgnoreCase("Tuesday") && emailFreqObj.getTuesday_email().equalsIgnoreCase("Y"))
									{
										sendEMails(incidentinfo,userObj.getEmailId(),ccEmailListBuilder.toString());
									}
									else if(day.equalsIgnoreCase("Wednesday") && emailFreqObj.getWednesday_email().equalsIgnoreCase("Y"))
									{
										sendEMails(incidentinfo,userObj.getEmailId(),ccEmailListBuilder.toString());
									}
									else if(day.equalsIgnoreCase("Thursday") && emailFreqObj.getThursday_email().equalsIgnoreCase("Y"))
									{
										sendEMails(incidentinfo,userObj.getEmailId(),ccEmailListBuilder.toString());
									}
									else if(day.equalsIgnoreCase("Friday") && emailFreqObj.getFriday_email().equalsIgnoreCase("Y"))
									{
										sendEMails(incidentinfo,userObj.getEmailId(),ccEmailListBuilder.toString());
									}
							
							}
							else if(incidentinfo.getPriority().equalsIgnoreCase(TrackingToolConstant.PRIORITY_2))
							{
							
								if(day.equalsIgnoreCase("Monday") && emailFreqObj.getMonday_email().equalsIgnoreCase("Y"))
								{
									sendEMails(incidentinfo,userObj.getEmailId(),ccEmailListBuilder.toString());
								}
								else if(day.equalsIgnoreCase("Tuesday") && emailFreqObj.getTuesday_email().equalsIgnoreCase("Y"))
								{
									sendEMails(incidentinfo,userObj.getEmailId(),ccEmailListBuilder.toString());
								}
								else if(day.equalsIgnoreCase("Wednesday") && emailFreqObj.getWednesday_email().equalsIgnoreCase("Y"))
								{
									sendEMails(incidentinfo,userObj.getEmailId(),ccEmailListBuilder.toString());
								}
								else if(day.equalsIgnoreCase("Thursday") && emailFreqObj.getThursday_email().equalsIgnoreCase("Y"))
								{
									sendEMails(incidentinfo,userObj.getEmailId(),ccEmailListBuilder.toString());
								}
								else if(day.equalsIgnoreCase("Friday") && emailFreqObj.getFriday_email().equalsIgnoreCase("Y"))
								{
									sendEMails(incidentinfo,userObj.getEmailId(),ccEmailListBuilder.toString());
								}
							}
							else if(incidentinfo.getPriority().equalsIgnoreCase(TrackingToolConstant.PRIORITY_3))
							{
								if(day.equalsIgnoreCase("Monday") && emailFreqObj.getMonday_email().equalsIgnoreCase("Y"))
								{
									sendEMails(incidentinfo,userObj.getEmailId(),ccEmailListBuilder.toString());
								}
								else if(day.equalsIgnoreCase("Tuesday") && emailFreqObj.getTuesday_email().equalsIgnoreCase("Y"))
								{
									sendEMails(incidentinfo,userObj.getEmailId(),ccEmailListBuilder.toString());
								}
								else if(day.equalsIgnoreCase("Wednesday") && emailFreqObj.getWednesday_email().equalsIgnoreCase("Y"))
								{
									sendEMails(incidentinfo,userObj.getEmailId(),ccEmailListBuilder.toString());
								}
								else if(day.equalsIgnoreCase("Thursday") && emailFreqObj.getThursday_email().equalsIgnoreCase("Y"))
								{
									sendEMails(incidentinfo,userObj.getEmailId(),ccEmailListBuilder.toString());
								}
								else if(day.equalsIgnoreCase("Friday") && emailFreqObj.getFriday_email().equalsIgnoreCase("Y"))
								{
									sendEMails(incidentinfo,userObj.getEmailId(),ccEmailListBuilder.toString());
								}
							}
							else if(incidentinfo.getPriority().equalsIgnoreCase(TrackingToolConstant.PRIORITY_4))
							{
								
								if(day.equalsIgnoreCase("Monday") && emailFreqObj.getMonday_email().equalsIgnoreCase("Y"))
								{
									sendEMails(incidentinfo,userObj.getEmailId(),ccEmailListBuilder.toString());
								}
								else if(day.equalsIgnoreCase("Tuesday") && emailFreqObj.getTuesday_email().equalsIgnoreCase("Y"))
								{
									sendEMails(incidentinfo,userObj.getEmailId(),ccEmailListBuilder.toString());
								}
								else if(day.equalsIgnoreCase("Wednesday") && emailFreqObj.getWednesday_email().equalsIgnoreCase("Y"))
								{
									sendEMails(incidentinfo,userObj.getEmailId(),ccEmailListBuilder.toString());
								}
								else if(day.equalsIgnoreCase("Thursday") && emailFreqObj.getThursday_email().equalsIgnoreCase("Y"))
								{
									sendEMails(incidentinfo,userObj.getEmailId(),ccEmailListBuilder.toString());
								}
								else if(day.equalsIgnoreCase("Friday") && emailFreqObj.getFriday_email().equalsIgnoreCase("Y"))
								{
									sendEMails(incidentinfo,userObj.getEmailId(),ccEmailListBuilder.toString());
								}
							}
						}
					}
					else
					{
						saveEmailNotification(incidentinfo);
					}
					
					
					if(i>3)
						break;
				} catch (MessagingException e) {
					lindeLogMgr.logStackTrace("ERROR", "MessagingException ", e);
				}
				
			}
			
		}	
	}
}
	
	
	
	private void saveEmailNotification(TicketInfo incidentinfo) {
		
		lindeLogMgr.logMessage("INFO", "Entered into saveEmailNotification");
		try
		{
			MailDetails MailDetObj = new MailDetails();
			MailDetObj.setIncidentID(incidentinfo.getIncidentID());
			MailDetObj.setPriority(incidentinfo.getPriority());
			Date date=new Date();
			String datee=date.toString();
			
			MailDetObj.setMaildate(datee);
			getSession().saveOrUpdate(MailDetObj);
			
		}
		catch(Exception e)
		{
			lindeLogMgr.logStackTrace("ERROR", "Exceprion in saveEmailNotification( ) ", e);
		}
		
	}



public void sendEMails(TicketInfo incidentinfo, String toEmailId, String ccEmailId) throws Exception {
	

	lindeLogMgr.logMessage("INFO", "Entered into sendIncidentEmails(");
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
	
 
    MimeMessage message=new MimeMessage(mailSession);
	message.setFrom(new InternetAddress(environment.getRequiredProperty("EmailFrom")));
	message.setSentDate(new GregorianCalendar().getTime());

	StringBuilder strBuilder = new StringBuilder();		
	strBuilder.append("<html><head></head><body>");
	strBuilder.append(" <br><br>");
	strBuilder.append("******* This is an auto-generated mail. Please do not reply********<br><br><br>");
	strBuilder.append("<b><center>Reminder Mail</center></b><br>");
	strBuilder.append("This is to remind you about the following Change Request which has been in "+incidentinfo.getPriority()+" phase from the Open Date "+incidentinfo.getOpenTime()+ " <br><br>");
	strBuilder.append("<b>Change Id &nbsp&nbsp&nbsp&nbsp&nbsp: </b>"+incidentinfo.getIncidentID()+"<br>");
	strBuilder.append("<b>Brief Description &nbsp&nbsp&nbsp&nbsp: </b>"+incidentinfo.getTitle()+"<br>");
	
	strBuilder.append("<b>Priority &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp: </b>"+incidentinfo.getPriority()+"<br>");
	strBuilder.append("</body></html>");
	
	message.setSubject("CR Reminder Mail - "+incidentinfo.getIncidentID()+" - "+incidentinfo.getPriority());
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
	
	saveEmailNotification(incidentinfo);
	
	}
	catch(Exception e)
	{
		lindeLogMgr.logStackTrace("ERROR", "Exception while sending email in sendCREmails() "+e.getMessage(), e);
	}
	

	
	
	
	/*

	String retry_countString = "";
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
	String monday_email="";
	String tuesday_email="";
	String wednesday_email="";
	String thursday_email="";
	String friday_email="";
	String owner_cc="";
	String tm_cc="";
	String pm_cc="";
	String pgm_cc="";
	
	System.out.println("Inside sendEMails------>:");
   
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
            MimeMessage message=new MimeMessage(mailSession);
        	message.setFrom(new InternetAddress("LindeTool"));
			message.setSentDate(new GregorianCalendar().getTime());
			L_Date=new GregorianCalendar().getTime();
			System.out.println("After setting email parameters------>:");
		
			
				TicketInfoDAO ticketInfoDAO=new TicketInfoDAO();
				UserDetailDAO userDetailDAO=new UserDetailDAO();
				DeleteUserDAO deleteUserDAO=new DeleteUserDAO();
				
				for(int i=0;i<lindeList.size();i++){
				EmailFrequency emailFrequency=(EmailFrequency)lindeList.get(i);
				LindeList ticketslist=(LindeList)ticketInfoDAO.getAllTTDetailedInfoBySeverity(emailFrequency.getPriority(), "priority","");
				Date date=new Date();
				SimpleDateFormat dateFormat=new SimpleDateFormat("MM/dd/yyyy HH:mm");
				 
				String todaydate=dateFormat.format(new Date());
				String temp3=todaydate.substring(todaydate.lastIndexOf("/")+1, todaydate.lastIndexOf("/")+3);
	
				if(temp3.equals("08"))
				{
				todaydate=todaydate.substring(0, todaydate.lastIndexOf("/")+1)+"2008"+todaydate.substring(todaydate.lastIndexOf("/")+3);
				}	
				
				Date systemdate=(Date)dateFormat.parse(todaydate);
				System.out.println("ticketslist size------>:"+ticketslist.size());
				for(int j=0;j<ticketslist.size();j++)
				{
					TicketInfo ticketInfo=(TicketInfo)ticketslist.get(j);
					String userName=ticketInfo.getAssigneeFullname();
					User user=userDetailDAO.getUser(userName);
					String mailid=user.getEmailId();
					StringBuffer buffer = new StringBuffer();		
				
					buffer.append("<html><head></head><body>");
					buffer.append(" <br><br>");
					buffer.append("******* This is an auto-generated mail. Please do not reply********<br><br><br>");
					buffer.append("<b><center>Reminder Mail</center></b><br>");
					buffer.append("This is to remind you about the following ticket which has been in "+ticketInfo.getStatus()+" status from "+ticketInfo.getOpenTime()+" days<br><br>");
					buffer.append("<b>Ticket No.&nbsp&nbsp&nbsp&nbsp&nbsp: </b>"+ticketInfo.getIncidentID()+"<br>");
					buffer.append("<b>Description&nbsp&nbsp&nbsp&nbsp: </b>"+ticketInfo.getTitle()+"<br>");
					buffer.append("<b>Application&nbsp&nbsp&nbsp&nbsp: </b>"+ticketInfo.getModule()+"<br>");
					buffer.append("<b>Severity&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp: </b>"+ticketInfo.getPriority()+"<br>");
					buffer.append("</body></html>");
					
					message.setSubject("Reminder Mail - "+ticketInfo.getIncidentID()+" - "+ticketInfo.getModule()+" - "+ticketInfo.getPriority());
					message.setContent(buffer.toString(), "text/html");
						
					Module application=(Module)deleteUserDAO.getApplicationByName(ticketInfo.getModule());
					
					if(emailFrequency.getOwners_cc().equals("Y")){
					Set owners=application.getUserid();
					for(Iterator iterator=owners.iterator();iterator.hasNext();)
					{
						User user1=(User)iterator.next();
						if(!TO.equals("")){
						TO=TO+","+user1.getEmailId();
						}
						else TO=(String)user1.getEmailId();
						
					}
					System.out.println("Application Owners is "+TO);
					
					}
					if(emailFrequency.getTm_cc().equals("Y"))
					{
						LindeList tllist=userDetailDAO.getUsersByRole(2);
						for(Iterator iterator=tllist.iterator();iterator.hasNext();)
						{
							User user1=(User)iterator.next();
							if(!Cc.equals("")){
								Cc=Cc+","+user1.getEmailId();
							}
							else Cc=(String)user1.getEmailId();
						 
						}
						System.out.println("After Adding TLs are "+Cc);
					}
					
					if(emailFrequency.getPm_cc().equals("Y"))
					{
						LindeList tllist=userDetailDAO.getUsersByRole(4);
						for(Iterator iterator=tllist.iterator();iterator.hasNext();)
						{
							User user1=(User)iterator.next();
							if(!Cc.equals("")){
								Cc=Cc+","+user1.getEmailId();
							}
							else Cc=(String)user1.getEmailId();
							
						}
						System.out.println("After Adding PL is "+TO);
					}
					if(emailFrequency.getPgm_cc().equals("Y"))
					{
						LindeList tllist=userDetailDAO.getUsersByRole(3);
						for(Iterator iterator=tllist.iterator();iterator.hasNext();)
						{
							User user1=(User)iterator.next();
							if(!Cc.equals("")){
								Cc=Cc+","+user1.getEmailId();
							}
							else Cc=(String)user1.getEmailId();
							
						}
						//System.out.println("TO is "+TO);
					 }
					
					message.setRecipients(Message.RecipientType.TO,TO);
					message.setRecipients(Message.RecipientType.CC,Cc);
					message.saveChanges();
					tr = mailSession.getTransport("smtp");
					tr.connect(smtp,username,password);
					address = message.getAllRecipients();
					
					
					MailDetails mailDetails=new MailDetails();
					mailDetails.setPriority(ticketInfo.priority);
					mailDetails.setMaildate(todaydate);
					mailDetails.setIncidentID(ticketInfo.incidentID);
					boolean mailsentstatus=false;
					boolean repeatemail=false;
					boolean alreadymailsent=false;
					double  diff=0;
					LindeList ttlist=TTsMailSent();
					for(Iterator iterator=ttlist.iterator();iterator.hasNext();)
					{
						MailDetails mailDetails1=(MailDetails)iterator.next();
						String maildate=(String)mailDetails1.getMaildate();
						Date mailsentdate=(Date)dateFormat.parse(maildate);
						diff=(date.getTime()-mailsentdate.getTime())/(1000*60*60);
						
						System.out.println("Ticket Number -->  "+ticketInfo.getIncidentID());
						System.out.println("MailDetails Ticket Number -->  "+mailDetails1.getIncidentID());
						
						if(ticketInfo.getIncidentID().equals(mailDetails1.getIncidentID()))
						{
							alreadymailsent=true;
							System.out.println("diff between dates is  "+diff);
							 
							if(emailFrequency.getPriority().equals("1 - Critical"))
							{	if(diff>=1)
								{
								repeatemail=true;
								}
							}
							else if(emailFrequency.getPriority().equals("2 - High"))
							{	if(diff>=2)
								{
								repeatemail=true;
								}
							}
							
							else if(emailFrequency.getPriority().equals("3 - Medium"))
							{	if(diff>=10)
								{
								repeatemail=true;
								}
							}
							else if(emailFrequency.getPriority().equals("4 - Low"))
							{
								
								System.out.println("ttno repeat check is  "+ticketInfo.getIncidentID());
								
								if(diff>=24)
								{
								repeatemail=true;
								}
							}
							
							if(repeatemail==true)
							{
								
								if(date.getDay()==1){
									System.out.println("Monday_email");
									if(emailFrequency.getMonday_email().equals("Y"))
									{
										if(ticketInfo.getPriority().equals("4 - Low"))
										{
										
										tr.sendMessage(message, address);
										UpdateMailsSent(mailDetails);
										mailsentstatus=true;
										
										}
										else
										{
										tr.sendMessage(message, address);
										UpdateMailsSent(mailDetails);
										}
									}
								}
								else if(date.getDay()==2)
								{
									System.out.println("Tuesday_email");
									if(emailFrequency.getTuesday_email().equals("Y"))
									{
										if(ticketInfo.getPriority().equals("4 - High"))
										{
										
										tr.sendMessage(message, address);
										UpdateMailsSent(mailDetails);
										mailsentstatus=true;
										
										}
										else
										{
										tr.sendMessage(message, address);
										UpdateMailsSent(mailDetails);
										}
									}
								}
								else if(date.getDay()==3){
									System.out.println("Wednesday_email");
									if(emailFrequency.getWednesday_email().equals("Y"))
									{
										if(ticketInfo.getPriority().equals("4 - High"))
										{
										
											tr.sendMessage(message, address);
											UpdateMailsSent(mailDetails);
											mailsentstatus=true;
										
										}
										else
										{
										tr.sendMessage(message, address);
										UpdateMailsSent(mailDetails);
										}
									}
								}
								else if(date.getDay()==4){
									System.out.println("Thursday_email");
									if(emailFrequency.getThursday_email().equals("Y"))
									{
										if(ticketInfo.getPriority().equals("4 - High"))
										{
										
										System.out.println("sending mail for next time");
										tr.sendMessage(message, address);
										UpdateMailsSent(mailDetails);
										mailsentstatus=true;
										
										}
										else
										{
											tr.sendMessage(message, address);
											UpdateMailsSent(mailDetails);
										}
									}
								}
								else if(date.getDay()==5){
									System.out.println("Friday_email");
									if(emailFrequency.getFriday_email().equals("Y"))
									{
										if(ticketInfo.getPriority().equals("4 - High"))
										{
										
										tr.sendMessage(message, address);
										UpdateMailsSent(mailDetails);
										mailsentstatus=true;
										
										}
										else
										{
											tr.sendMessage(message, address);
											UpdateMailsSent(mailDetails);
										}
									}
								}
								else
								{
									System.out.println("empty else block");
								}
								}
							}
						}
					System.out.println("Before Sending mail for first time mailsentstatus: "+mailsentstatus+":::repeatmail::: "+repeatemail); 		
					if(mailsentstatus==false && alreadymailsent==false){
						if(date.getDay()==1){					
							if(emailFrequency.getMonday_email().equals("Y"))
							{
								System.out.println("Sending mail for first time for Monday"); 
								if(ticketInfo.getPriority().equals("4 - High"))
								{
									System.out.println("ticketInfo age" + ticketInfo.age);
									
										System.out.println("Day One - If part"); 
										tr.sendMessage(message, address);
										UpdateMailsSent(mailDetails);
									
								}
								else
								{
									System.out.println("Day One - Else part"); 
									tr.sendMessage(message, address);
									UpdateMailsSent(mailDetails);
								}
							}
						}
						else if(date.getDay()==2)
						{
							if(emailFrequency.getTuesday_email().equals("Y"))
							{
								if(ticketInfo.getPriority().equals("4 - High"))
								{
								
								
									System.out.println("Day Two - If part");
								tr.sendMessage(message, address);
								UpdateMailsSent(mailDetails);
								
								}
								else
								{
									tr.sendMessage(message, address);
									UpdateMailsSent(mailDetails);
									
									
								}
							}
						}
					else if(date.getDay()==3){
						if(emailFrequency.getWednesday_email().equals("Y"))
						{
							if(ticketInfo.getPriority().equals("4 - High"))
							{
							
							
								System.out.println("Day Three - If part");
							tr.sendMessage(message, address);
							UpdateMailsSent(mailDetails);
							
							}
							else
							{
								tr.sendMessage(message, address);
								UpdateMailsSent(mailDetails);
								
								
							}
						}
					}
					else if(date.getDay()==4){
						if(emailFrequency.getThursday_email().equals("Y"))
						{
							if(ticketInfo.getPriority().equals("4 - High"))
							{							
							
							System.out.println("Day Four - If part");
							tr.sendMessage(message, address);
							UpdateMailsSent(mailDetails);
							
							}
							else
							{
							tr.sendMessage(message, address);
							UpdateMailsSent(mailDetails);
												
							}
						}
					}
					else if(date.getDay()==5){
						if(emailFrequency.getFriday_email().equals("Y"))
						{
							if(ticketInfo.getPriority().equals("Severity 4"))
							{							


								System.out.println("Day Five - If part");
							tr.sendMessage(message, address);
							UpdateMailsSent(mailDetails);

							}
							else
							{
							tr.sendMessage(message, address);
							UpdateMailsSent(mailDetails);
												
							}
						}
					}
					else
					{
						System.out.println("Blank ELSE");
					}
					
					}
					
					TO="";
					Cc="";
					System.out.println("After send message"); 
									
				}
				ticketslist.clear();
				}
			
		
			  
				}
							catch (Exception e) {
								flag=false;
								System.out.println("Send Emails exception:::"+e.getMessage());
								e.printStackTrace();
								//throw new Exception(e.getMessage());
								}
									finally{
							
							}

 //Checks whether all properties have been set or not 
					
*/}



}
