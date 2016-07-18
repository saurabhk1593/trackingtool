package com.techm.trackingtool.admin.dao;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.techm.trackingtool.admin.bean.Roles;
import com.techm.trackingtool.admin.bean.User;
import com.techm.trackingtool.util.LindeList;
import com.techm.trackingtool.util.LindeMap;
import com.techm.trackingtool.util.LindeToolLogManager;

//@Transactional
@Repository("userDetailDAO")
public class UserDetailDAO   {
	
	//private static Configuration cfg;
	//private static SessionFactory factory;
	private static LindeToolLogManager lindeLogMgr = new LindeToolLogManager(UserDetailDAO.class.getName());
	/*public UserDetailDAO()
	{
		lindeLogMgr.logMessage("DEBUG", "Inside UserDetailDAO");
		System.out.println("DEBUG Inside UserDetailDAO");
		Configuration cfg = new Configuration();
		try{
		cfg.configure("hibernate.cfg.xml");
		}catch (Exception e) {
			e.printStackTrace();
		}
		lindeLogMgr.logMessage("DEBUG", "After cfg loaded");
		lindeLogMgr.logMessage("DEBUG", "After hbm loaded");
		factory = cfg.buildSessionFactory();
		System.out.println("After cfg");
		System.out.println("After hbm");
		
	}*/
	
//private final Class<Tbl_Users> persistentClass;
	
/*	@SuppressWarnings("unchecked")
    public UserDetailDAO(){
        //this.persistentClass =(Class<Employee>)((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		this.persistentClass =(Class<Tbl_Users>)this.getClass().getGenericSuperclass().getClass();
    }*/
	
	
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
	
	public User checkUser(String userID,String password){
		User userInfo = new User();
		try
		{
		
		//String usersmall=userID.toUpperCase();
			String usersmall=userID;
		System.out.println("usersmall is "+usersmall);
		lindeLogMgr.logMessage("DEBUG","usersmall is "+usersmall);
		//Session session = factory.openSession();
		//Transaction txn = session.beginTransaction();
		lindeLogMgr.logMessage("DEBUG", "Before call to checkUser query");
		//Query queryObj = session.getNamedQuery("checkUser");
		Query queryObj = getSession().getNamedQuery("selUser");
		lindeLogMgr.logMessage("DEBUG", "After call to checkUser query: "+queryObj.getQueryString());
		queryObj.setString("userId", usersmall);
		queryObj.setString("password", password);
		ArrayList persons = (ArrayList)queryObj.list();
		
		//ArrayList persons = (ArrayList)session.createQuery("from com.linde.lindeTool.admin.dao.User where domain='SS32927'").list();
		
		for(int i=0;i<persons.size();i++)
		{
			lindeLogMgr.logMessage("DEBUG", "User validation : Loop");
		//	userInfo = (User)persons.get(i); 
			User tblUserObj =(User)persons.get(i);
			lindeLogMgr.logMessage("DEBUG", "userObj.toString() :: "+tblUserObj.toString());
			userInfo.setUserID(tblUserObj.getUserID());
			//userInfo.setDomain(tblUserObj.getDomain());
			userInfo.setEmailId(tblUserObj.getEmailId());
			userInfo.setFirstName(tblUserObj.getFirstName());
			userInfo.setLastName(tblUserObj.getLastName());
			userInfo.setRoleId(tblUserObj.getRoleId());
			userInfo.setApprovalStatus(tblUserObj.getApprovalStatus());
			userInfo.setLocation(tblUserObj.getLocation());
			userInfo.setPassword(tblUserObj.getPassword());
			
			userInfo.setAvailable(true);
			if(!userInfo.getApprovalStatus().equals("Pending"));{
				Roles roleInfo = getRoleInfo(userInfo.getRoleId());
				
				userInfo.setUserRole(roleInfo);
			}
			lindeLogMgr.logMessage("DEBUG", userInfo.isAvailable() + " " + userInfo.getFirstName());
		}
		//txn.commit();
		//session.flush();
		//session.close();
		//userInfo.setAvailable(true);
		}
		catch(Exception ex)
		{
			lindeLogMgr.logStackTrace("ERROR", ex.getMessage(), ex);
		}
		lindeLogMgr.logMessage("DEBUG", "Inside checkUser DAo:"+userInfo.isAvailable());
		return userInfo;
	}
	
	private Roles getRoleInfo(int roleId){
		Roles roleInfo = new Roles();
		
		//Session session = factory.openSession();
		//Transaction txn = session.beginTransaction();
		Query queryObj = getSession().getNamedQuery("getRole");
		queryObj.setInteger("roleID", roleId);
		ArrayList roles = (ArrayList)queryObj.list();
		for(int i=0;i<roles.size();i++)
		{
		// 	roleInfo = (Roles)roles.get(i);
			Roles tblRoleInfo = (Roles)roles.get(i);
			roleInfo.setRoleId(tblRoleInfo.getRoleId());
			roleInfo.setRoleDesc(tblRoleInfo.getRoleDesc());
			roleInfo.setRoleName(tblRoleInfo.getRoleName());
		}
		//txn.commit();
		//session.flush();
		//session.close();
		return roleInfo;
	}
	
	public LindeMap checkUsersAwaitingApproval(){
		LindeMap userMap = new LindeMap();
		//Session session = factory.openSession();
		//Transaction txn = session.beginTransaction();
		lindeLogMgr.logMessage("DEBUG", "Before call to checkUsersAwaitingApproval query");
		//Query queryObj = session.getNamedQuery("checkUser");
		Query queryObj = getSession().getNamedQuery("selPendingUser");
		lindeLogMgr.logMessage("DEBUG", "After call to checkUsersAwaitingApproval query:");
		queryObj.setString("statusVal", "Pending");
		ArrayList persons = (ArrayList)queryObj.list();
		lindeLogMgr.logMessage("DEBUG", "<---Problem is herer---->");
		//ArrayList persons = (ArrayList)session.createQuery("from com.linde.lindeTool.admin.dao.User where domain='SS32927'").list();
		
		for(int i=0;i<persons.size();i++)
		{
			User userInfo = new User();
			lindeLogMgr.logMessage("DEBUG", "Problem in userInfo ---->");
			userInfo = (User)persons.get(i);
			lindeLogMgr.logMessage("DEBUG", "--<-Problem is here");
			userInfo.setAvailable(true);
			userMap.put(userInfo.getUserID(),userInfo);
			lindeLogMgr.logMessage("DEBUG", userInfo.isAvailable() + " " + userInfo.getFirstName());
			//lindeLogMgr.logMessage("DEBUG", userInfo.isAvailable() + " " + userInfo.getFirstName());
		}
		//txn.commit();
	//	session.flush();session.close();
		//userInfo.setAvailable(true);
		//lindeLogMgr.logMessage("DEBUG", "Inside checkUser DAo:"+userInfo.isAvailable());
		lindeLogMgr.logMessage("DEBUG", "*********checkUsersAwaitingApproval exit--*****-->");
		return userMap;
	}
	
	public boolean approveUser(User userDetails, User creator) throws Exception{
		boolean success = true;
		
		try{
			//Session session = factory.openSession();
	//	Transaction txn = session.beginTransaction();
			getSession().update(userDetails);
		//txn.commit();
		//session.flush();
		//session.close();
		}catch (Exception e) {
			throw e;	
		}
		sendEMails(userDetails, "approved", creator);
		return success;
	}
	
	public boolean addUser(User userDetails, User creator) throws Exception{
		boolean success = true;
		String userstatus="";
		try{
		//Session session = factory.openSession();
		//Transaction txn = session.beginTransaction();
		
		lindeLogMgr.logMessage("DEBUG","user id is "+userDetails.getUserID()+" ");
		
		getSession().save(userDetails);
		
		//txn.commit();
		//session.flush();
		//session.close();
		}catch (Exception e) {
		throw e;
		}
		if(creator.getRoleId().intValue()==3)
		{
			userstatus="approved";
		}
		else
			userstatus="registered";
		
		sendEMails(userDetails,userstatus,creator);
		return success;
	}
	
	public boolean registerUser(User userDetails) throws Exception{
		boolean success = true;
		String userstatus="";
		try{
	//	Session session = factory.openSession();
	//	Transaction txn = session.beginTransaction();
		
		System.out.println("user id is "+userDetails.getUserID()+" ");
		
		getSession().save(userDetails);
		
		//txn.commit();
	//	session.flush();
	//	session.close();
		}catch (Exception e) {
		throw e;
		}
		
		userstatus="registered";
		
		sendEMailRegister(userDetails,userstatus);
		return success;
	}
	
	public User getUser(String userName){
		User userInfo = new User();
		//Session session = factory.openSession();
		//Transaction txn = session.beginTransaction();
		lindeLogMgr.logMessage("DEBUG", "Before call to getUser query");
		Query queryObj = getSession().getNamedQuery("selUserWithUserName");
		lindeLogMgr.logMessage("DEBUG", "After call to  getUser query" + queryObj.getQueryString());
		queryObj.setString("userName", userName);
		ArrayList persons = (ArrayList)queryObj.list();
		
		for(int i=0;i<persons.size();i++)
		{
			lindeLogMgr.logMessage("DEBUG", "User validation : Loop");
			userInfo = (User)persons.get(i);
			lindeLogMgr.logMessage("DEBUG", userInfo.isAvailable() + " " + userInfo.getFirstName());
		}
		//txn.commit();
		//session.flush();session.close();
	
		lindeLogMgr.logMessage("DEBUG", "userInfo :"+userInfo.isAvailable());

		return userInfo;
	}
	
	
	public void sendEMails(User user,String status,User creator) throws Exception {

		String smtpProperty = "";
		boolean flag=true;
		Transport tr = null;
		Address[] address = null;
		String Text= "";
		String from= "";
		String MID= "";
		String TO= "bhanu_gudiyatham@satyam.com";
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
				LindeList adminUsers=getAdminUser();
				for(Iterator iter=adminUsers.iterator();iter.hasNext();)
				{
					User adminUser=(User)iter.next();
					
					if(TO.equals(""))
					{
						TO=adminUser.getEmailId();
					}
					if(!TO.equals(""))
					{
						TO=TO+","+adminUser.getEmailId();
					}
					
				}
				message.setRecipients(Message.RecipientType.TO,creator.emailId);
				System.out.println("Before Setting CC"+"");
				message.setRecipients(Message.RecipientType.CC,creator.emailId+","+user.getEmailId());
				System.out.println("Before Setting Date");
				message.setSentDate(new GregorianCalendar().getTime());
				L_Date=new GregorianCalendar().getTime();
				System.out.println("Before Setting Subject");
				if(status.equals("registered"))
				{
					Subject="User Registration";
				}
				if(status.equals("approved"))
				{
					Subject="User Approval";
				}
				
				if(status.equals("deleted"))
				{
					Subject="User Deletion";
				}
				message.setSubject(Subject+" Mail");

				String userName=user.getFirstName();
				String mailid=user.getEmailId();
				StringBuffer buffer = new StringBuffer();		
					
				System.out.println("Mail id is "+mailid);
				buffer.append("<html><head></head>");
				buffer.append("******* This is an auto-generated mail. Please do not reply********</font></td>");
				buffer.append("<center><b>User Access</b></center><br><br>");
				buffer.append("This is to inform you that <b>"+user.getFirstName()+" </b> has been "+status+" for accessing LindeTool</font></td>");
				
				buffer.append("<b>User Name&nbsp&nbsp&nbsp&nbsp: </b>"+user.getFirstName()+"<br>");
				buffer.append("<b>Role&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp: </b>"+getRoleInformation(user.getRoleId())+"<br>");
				buffer.append("</body></html>");
				
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
	
	public String getRoleInformation(Integer roleid){
		Roles roles=new Roles();
	//	Session session = factory.openSession();
	//	Transaction txn = session.beginTransaction();
		getSession().load(roles, roleid);
		String rolename=roles.getRoleName();
 	   //	txn.commit();
 	   //	session.close();  
		return rolename;
	}
	
	public LindeList getAdminUser(){
		LindeList adminUsers=new LindeList();
		
		//Session session = factory.openSession();
		//Transaction txn = session.beginTransaction();
		Query queryObj = getSession().getNamedQuery("selUsers");
		ArrayList users = (ArrayList)queryObj.list();
		System.out.println("<---Problem in getUserInfo()---->");
		
		for(int i=0;i<users.size();i++)
		{
			User user=(User)users.get(i);
			if(user.getRoleId().equals(new Integer(3)))
			{
				adminUsers.add(user);
			}
			
			
		}
	//	txn.commit();
	//	session.close(); 
		return adminUsers;
	}

	public LindeList getUsersByRole(int role)
	{

		LindeMap ttinfoMap = new LindeMap();
		LindeList userInfo=new LindeList();
	//	Session session = factory.openSession();
	//	Transaction txn = session.beginTransaction();
		
		Object[] row;
		String userrole="";
		StringBuffer SQL_QUERY = new StringBuffer("SELECT firstName,emailId from User ");
		
				SQL_QUERY.append(" where roleId="+new Integer(role));
	    	   ArrayList query = (ArrayList)getSession().createQuery(SQL_QUERY.toString()).list();
			   
			   for (int i=0;i < query.size(); i++) {
	    		   row = (Object[]) query.get(i);
	    		   		User user=new User();
	    		   		user.setFirstName((String)row[0]);
	    		   		user.setEmailId((String)row[1]);
	    		   		userInfo.add(user);
	    		   	   }
			//ttinfoMap.put("ttinfoMap", ttinfo); 
	    //	txn.commit();
	    //	session.close();  
	    	return userInfo;
	}
	
	
	public LindeMap getUsers()
	{

		LindeMap userInfo=new LindeMap();
		//Session session = factory.openSession();
	//	Transaction txn = session.beginTransaction();
		
		Object[] row;
		String userrole="";
		StringBuffer SQL_QUERY = new StringBuffer("SELECT firstName,emailId from User ");
		
			ArrayList query = (ArrayList)getSession().createQuery(SQL_QUERY.toString()).list();
			   
			   for (int i=0;i < query.size(); i++) {
	    		   row = (Object[]) query.get(i);
	    		   		userInfo.put((String)row[0],(String)row[1]);
	    		   		}
	    	//txn.commit();
	    	//session.close();  
	    	return userInfo;
	}
	
	public void sendEMailRegister(User user,String status) throws Exception {

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
				LindeList adminUsers=getAdminUser();
				for(Iterator iter=adminUsers.iterator();iter.hasNext();)
				{
					User adminUser=(User)iter.next();
					
					if(TO.equals(""))
					{
						TO=adminUser.getEmailId();
					}
					if(!TO.equals(""))
					{
						TO=TO+","+adminUser.getEmailId();
					}
					
				}
				if(Cc.equals(""))
				{
					Cc=user.getEmailId();
				}
				if(!Cc.equals(""))
				{
					Cc=Cc+","+user.getEmailId();;
				}
				message.setRecipients(Message.RecipientType.TO,TO);
				System.out.println("Before Setting CC"+"");
				message.setRecipients(Message.RecipientType.CC,Cc);
				System.out.println("Before Setting Date");
				message.setSentDate(new GregorianCalendar().getTime());
				L_Date=new GregorianCalendar().getTime();
				System.out.println("Before Setting Subject");
				if(status.equals("registered"))
				{
					Subject="User Registration";
				}
				if(status.equals("approved"))
				{
					Subject="User Approval";
				}
				
				if(status.equals("deleted"))
				{
					Subject="User Deletion";
				}
				message.setSubject(Subject+" Mail");

				String userName=user.getFirstName();
				String mailid=user.getEmailId();
				StringBuffer buffer = new StringBuffer();		
					
				System.out.println("Mail id is "+mailid);
				buffer.append("<html><head></head>");
				buffer.append("<body><table border=0 cellspacing=0 cellpadding=0 width=\"100%\"> ");
				buffer.append(" <tr><td>&nbsp<br></td></tr>");
				buffer.append("<tr><td height= 20  valign=top><font face=\"Trebuchet MS\" size=2px>");
				buffer.append("******* This is an auto-generated mail. Please do not reply********</font></td>");
				buffer.append("</tr> ");
				buffer.append("<tr>");
				buffer.append("<td align=center><center><table align=center ><tr><td height= 40  align=center> <font face=\"Trebuchet MS\" size=4px><b>"+Subject+" Mail</b></font></td></tr></table><center></td>");
				buffer.append("<td  align= right ><table align=center> <tr><td></td></tr></table></td></tr>");
				buffer.append("<table border=0 cellspacing=0 cellpadding=0 width=\"100%\"> ");
				buffer.append("<tr><td height= 20  valign=top><font face=\"Trebuchet MS\" size=2px>");
				buffer.append("This is to inform you that <b>"+user.getFirstName()+" </b> has been "+status+" for accessing LindeTool</font></td>");
				buffer.append("</tr> <tr><td>&nbsp</td></tr> ");
						
				buffer.append("<tr><td height= 20  valign=top><font face=\"Trebuchet MS\" size=2px>");
				buffer.append("<b>User Name&nbsp&nbsp&nbsp&nbsp: </b></font>");
				buffer.append("<font face=\"Trebuchet MS\" size=2px>"+user.getFirstName()+" </font></td></tr> <tr><td>&nbsp</td></tr>");
				buffer.append("<tr><td height= 20  valign=top><font face=\"Trebuchet MS\" size=2px>");
				buffer.append("<b>Role&nbsp&nbsp&nbsp&nbsp: </b></font>");
				buffer.append("<font face=\"Trebuchet MS\" size=2px>"+getRoleInformation(user.getRoleId())+" </font></td></tr> <tr><td>&nbsp</td></tr>");
						
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
