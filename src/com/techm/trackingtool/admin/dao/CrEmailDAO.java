package com.techm.trackingtool.admin.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.techm.trackingtool.admin.bean.Application;
import com.techm.trackingtool.admin.bean.CRinfo;
import com.techm.trackingtool.admin.bean.CrEmailFrequency;
import com.techm.trackingtool.admin.bean.CrMailDetails;
import com.techm.trackingtool.admin.bean.MailDetails;
import com.techm.trackingtool.admin.bean.TicketInfo;
import com.techm.trackingtool.admin.bean.User;
import com.techm.trackingtool.services.UserdetailsService;
import com.techm.trackingtool.util.LindeList;
import com.techm.trackingtool.util.LindeToolLogManager;
import com.techm.trackingtool.util.TrackingToolConstant;

@PropertySource(value = { "classpath:Email.properties" })
@Repository
	public class CrEmailDAO {
		
	
	@Autowired
	private Environment environment;
	
	@Autowired
	UserdetailsService serviceee;
	
	private static LindeToolLogManager lindeLogMgr = new LindeToolLogManager(CrEmailDAO.class.getName());
		
		
	    private String smtp;
	    private String username;
	    private String password;
			
		 @Autowired
		 private SessionFactory sessionFactory;
		 
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
			
	public CrEmailFrequency submitCrEmailSetting(CrEmailFrequency cremailFrequency){
			
		getSession().save(cremailFrequency);
		   return cremailFrequency;	 
		}

	public void deleteCrEmailSetting(String priority){
		  
			LindeList severitySetting = new LindeList();
			Query queryObj = getSession().getNamedQuery("selCrSeveritySetting");
			ArrayList severityList = (ArrayList)queryObj.list();
			System.out.println("<---In deleteEmailSetting---->");
			
			for(int i=0;i<severityList.size();i++)
			{
				severitySetting.add(severityList.get(i));			
				
			}
			for(int k=0;k<severitySetting.size();k++)
			{
			CrEmailFrequency emailFrequency=(CrEmailFrequency)severitySetting.get(k);
			String severityid=emailFrequency.getId();
				if(priority.equals(severityid))
				{
					getSession().delete(emailFrequency);
				}
			}
	}


	public CrEmailFrequency getCrEmailSetting(String priority){
		LindeList severitySetting = new LindeList();
		Query queryObj = getSession().getNamedQuery("selCrSeveritySetting");
		ArrayList severityList = (ArrayList)queryObj.list();
		CrEmailFrequency cremailFrequency=new CrEmailFrequency();
		
		for(int i=0;i<severityList.size();i++)
		{
		CrEmailFrequency crtempemailFrequency=(CrEmailFrequency)severityList.get(i);
		String severityid=crtempemailFrequency.getPriority();
			if(priority.equals(severityid))
			{
				cremailFrequency=crtempemailFrequency;
			
			System.out.println("priority selected"+cremailFrequency.getP1_interval());}
		}
	return cremailFrequency;
	}


	public LindeList MailToBeSend()
	{
		LindeList severitySetting = new LindeList();
		System.out.println("<---inside MailToBeSend----> ");
		
		if(getSession() != null){

			Query queryObj = getSession().getNamedQuery("selCrSeveritySetting");
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
	
		for(int i=0;i<severityList.size();i++)
		{
			severitySetting.add((MailDetails)severityList.get(i));		
		}
		return severitySetting;
	}

	public void UpdateMailsSent(MailDetails mailDetails)
	{
		
		boolean status=false;
		LindeList ttlist=TTsMailSent();
		
		if(!ttlist.isEmpty()){
			for(Iterator iterator=ttlist.iterator();iterator.hasNext();)
			{
				MailDetails ticketInfo=(MailDetails)iterator.next();
				if(ticketInfo.getIncidentID().equals(mailDetails.getIncidentID())){
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

	

public void sendCREmailNotifications()
{
	
	try {
		serviceee.sendEmailNotifications();
	} catch (Exception e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} 
	
	
	
	lindeLogMgr.logMessage("DEBUG", "After execution of incident sendEmailNotifications :: ");
	Session sesssion = getSession();
	lindeLogMgr.logMessage("DEBUG", "sendCREmailNotifications :: ");
	ArrayList<User> usersList =(ArrayList<User>)sesssion.createCriteria(User.class).list();
	Map<String,User> userMap = new HashMap<String,User>();
	for (User user : usersList) {
		userMap.put(user.getLastName().toUpperCase()+", "+user.getFirstName().toUpperCase(), user);
	}
	
	
	ArrayList<CrEmailFrequency> crEmailFreequencyList =(ArrayList<CrEmailFrequency>)sesssion.createCriteria(CrEmailFrequency.class).list();
	
	Map<String,CrEmailFrequency> crEmailFrequencyMap = new HashMap<String,CrEmailFrequency>();
	for (CrEmailFrequency crEmailFrequency : crEmailFreequencyList) {
		//crEmailFrequencyMap.put(crEmailFrequency.getPriority().replace(" ", ""), crEmailFrequency);
		crEmailFrequencyMap.put(crEmailFrequency.getPriority(), crEmailFrequency);
	}

	ArrayList<String> phases = new ArrayList<String>();
	phases.add(TrackingToolConstant.ACCEPT_PHASE);
	phases.add(TrackingToolConstant.CI_VALIDATE_PHASE);
	phases.add(TrackingToolConstant.VALIDATE_PHASE);
	phases.add(TrackingToolConstant.POST_IMPL);
	phases.add(TrackingToolConstant.CORRECT_IMPL);
	//phases.add(TrackingToolConstant.APPROVAL);
	phases.add(TrackingToolConstant.IMPL);
	
	List<CRinfo> crsList =(ArrayList<CRinfo>)getSession().createCriteria(CRinfo.class).add(Restrictions.in("phase", phases)).list();
	lindeLogMgr.logMessage("DEBUG", "Size of the CRList is :: "+crsList.size());
	
	int i=0;
	for (Iterator iterator = crsList.iterator(); iterator.hasNext();) {
		CRinfo cRinfo = (CRinfo) iterator.next();
		lindeLogMgr.logMessage("DEBUG", "cRinfo.getChangeId() :: "+cRinfo.getChangeId());
		i++;
		User userObj = userMap.get(cRinfo.getAssignee());
		if(cRinfo.getPriority() != null && userObj != null)
		{
			lindeLogMgr.logMessage("DEBUG", "cRinfo.getAssignee() :: "+cRinfo.getAssignee() );
			
			StringBuilder ccEmailListBuilder = new StringBuilder(); 
			
			CrEmailFrequency crEmailFreqObj = crEmailFrequencyMap.get(cRinfo.getPriority());
			if(crEmailFreqObj.getPm_cc().equalsIgnoreCase("Y"))
			{
				lindeLogMgr.logMessage("DEBUG", "userObj.getLocation() :: "+userObj.getLocation() );
			//	List<User> ccEmail_PM_List =(ArrayList<User>) getSession().createCriteria(User.class).add(Restrictions.eq("location", userObj.getLocation())).add(Restrictions.eq("roleId", 2)).list();
				for (User user : usersList) {
					if(user.getRoleId().intValue() == 2 )
					  ccEmailListBuilder.append(user.getEmailId()).append(";");					
				}
			}
			if(crEmailFreqObj.getPgm_cc().equalsIgnoreCase("Y"))
			{
				//List<User> ccEmail_PGM_List =(ArrayList<User>) getSession().createCriteria(User.class).add(Restrictions.eq("roleId", 3)).list();
				for (User user : usersList) {
					if(user.getRoleId().intValue() == 3 )
					  ccEmailListBuilder.append(user.getEmailId()).append(";");
				}
			}
			
			try {
				
				List<CrMailDetails> crMailDetObj =(ArrayList<CrMailDetails>) getSession().createCriteria(CrMailDetails.class).add(Restrictions.eq("changeID", cRinfo.getChangeId())).list();
				
				
				if(crMailDetObj.size() > 0)
				{
					lindeLogMgr.logMessage("DEBUG", "Inside if condition crMailDetObj.size() ::: "+crMailDetObj.size());
					
					for (CrMailDetails crMailDetails2 : crMailDetObj) 
					{
						Timestamp crMailtTime = crMailDetails2.getMailtime();
						Timestamp curTimeStamp = new Timestamp(System.currentTimeMillis());
				
						if(cRinfo.getPriority().equalsIgnoreCase(TrackingToolConstant.PRIORITY_1))
						{
						//	List<CrMailDetails> crMailDetObj =(ArrayList<CrMailDetails>) getSession().createCriteria(CrMailDetails.class).add(Restrictions.eq("changeID", cRinfo.getChangeId())).list();
						//	for (CrMailDetails crMailDetails2 : crMailDetObj) {
						//		Timestamp crMailtTime = crMailDetails2.getMailtime();
						//		Timestamp curTimeStamp = new Timestamp(System.currentTimeMillis());
								if(curTimeStamp.after(crMailtTime) && (curTimeStamp.getTime() - crMailtTime.getTime()) >= (Long.valueOf(crEmailFreqObj.getP1_interval()) * 60 * 1000)) //TrackingToolConstant.EMAIL_INTRVAL_PRIORITY_1
								{
									sendCREmails(cRinfo,userObj.getEmailId(),ccEmailListBuilder.toString());
								}
						//	}
						}
						else if(cRinfo.getPriority().equalsIgnoreCase(TrackingToolConstant.PRIORITY_2))
						{
						//	List<CrMailDetails> crMailDetObj =(ArrayList<CrMailDetails>) getSession().createCriteria(CrMailDetails.class).add(Restrictions.eq("changeID", cRinfo.getChangeId())).list();
						//	for (CrMailDetails crMailDetails2 : crMailDetObj) {
						//		Timestamp crMailtTime = crMailDetails2.getMailtime();
						//		Timestamp curTimeStamp = new Timestamp(System.currentTimeMillis());
								if(curTimeStamp.after(crMailtTime) && (curTimeStamp.getTime() - crMailtTime.getTime()) >= (Long.valueOf(crEmailFreqObj.getP2_interval()) * 60 * 60 * 1000)) //crEmailFreqObj
								{
									sendCREmails(cRinfo,userObj.getEmailId(),ccEmailListBuilder.toString());
								}
						//	}
						}
						else if(cRinfo.getPriority().equalsIgnoreCase(TrackingToolConstant.PRIORITY_3))
						{
							//List<CrMailDetails> crMailDetObj =(ArrayList<CrMailDetails>) getSession().createCriteria(CrMailDetails.class).add(Restrictions.eq("changeID", cRinfo.getChangeId())).list();
						//	for (CrMailDetails crMailDetails2 : crMailDetObj) {
						//		Timestamp crMailtTime = crMailDetails2.getMailtime();
						//		Timestamp curTimeStamp = new Timestamp(System.currentTimeMillis());
								if(curTimeStamp.after(crMailtTime) && (curTimeStamp.getTime() - crMailtTime.getTime()) >= (Long.valueOf(crEmailFreqObj.getP3_interval()) * 60 * 60 * 1000)) //crEmailFreqObj
								{
									sendCREmails(cRinfo,userObj.getEmailId(),ccEmailListBuilder.toString());
								}
							//}
						}
						else if(cRinfo.getPriority().equalsIgnoreCase(TrackingToolConstant.PRIORITY_4))
						{
							//List<CrMailDetails> crMailDetObj =(ArrayList<CrMailDetails>) getSession().createCriteria(CrMailDetails.class).add(Restrictions.eq("changeID", cRinfo.getChangeId())).list();
						//	for (CrMailDetails crMailDetails2 : crMailDetObj) {
						//		Timestamp crMailtTime = crMailDetails2.getMailtime();
						//		Timestamp curTimeStamp = new Timestamp(System.currentTimeMillis());
								if(curTimeStamp.after(crMailtTime) && (curTimeStamp.getTime() - crMailtTime.getTime()) >= (Long.valueOf(crEmailFreqObj.getP4_interval()) * 60 * 60 * 1000)) //crEmailFreqObj
								{
									sendCREmails(cRinfo,userObj.getEmailId(),ccEmailListBuilder.toString());
								}
						//	}
						}
					}
				}
				else
				{
					saveEmailNotification(cRinfo);
				}
				
				
				if(i>3)
					break;
			} catch (MessagingException e) {
				lindeLogMgr.logStackTrace("ERROR", "MessagingException ", e);
			}
			
		}
		
	}	
}

private void saveEmailNotification(CRinfo cRinfo) {
	
	lindeLogMgr.logMessage("INFO", "Entered into saveEmailNotification");
	try
	{
		CrMailDetails crMailDetObj = new CrMailDetails();
		crMailDetObj.setChangeID(cRinfo.getChangeId());
		crMailDetObj.setPriority(cRinfo.getPriority());
		crMailDetObj.setMailtime(new Timestamp(System.currentTimeMillis()));
		getSession().saveOrUpdate(crMailDetObj);
		
	}
	catch(Exception e)
	{
		lindeLogMgr.logStackTrace("ERROR", "Exceprion in saveEmailNotification( ) ", e);
	}
	
}

private void sendCREmails(CRinfo cRinfo,String toEmailId,String ccEmailId) throws AddressException, MessagingException
{
	lindeLogMgr.logMessage("INFO", "Entered into sendCREmails(");
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
	strBuilder.append("<b><center>Reminder Mail</center></b><br>");
	strBuilder.append("This is to remind you about the following Change Request which has been in "+cRinfo.getPhase()+" phase from the Open Date "+cRinfo.getOpenDate()+ " <br><br>");
	strBuilder.append("<b>Change Id &nbsp&nbsp&nbsp&nbsp&nbsp: </b>"+cRinfo.getChangeId()+"<br>");
	strBuilder.append("<b>Brief Description &nbsp&nbsp&nbsp&nbsp: </b>"+cRinfo.getBriefDescription()+"<br>");
	strBuilder.append("<b>Module &nbsp&nbsp&nbsp&nbsp: </b>"+cRinfo.getAffectedCIs()+"<br>");
	strBuilder.append("<b>Priority &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp: </b>"+cRinfo.getPriority()+"<br>");
	strBuilder.append("</body></html>");
	
	message.setSubject("CR Reminder Mail - "+cRinfo.getChangeId()+" - "+cRinfo.getAffectedCIs()+" - "+cRinfo.getPriority());
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
	
	saveEmailNotification(cRinfo);
	
	}
	catch(Exception e)
	{
		lindeLogMgr.logStackTrace("ERROR", "Exception while sending email in sendCREmails() "+e.getMessage(), e);
	}
	
}
}
	
	

