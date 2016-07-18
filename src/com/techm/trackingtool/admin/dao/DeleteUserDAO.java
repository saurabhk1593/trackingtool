package com.techm.trackingtool.admin.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.techm.trackingtool.admin.bean.Application;
import com.techm.trackingtool.admin.bean.CRinfo;
import com.techm.trackingtool.admin.bean.Module;
import com.techm.trackingtool.admin.bean.TicketInfo;
import com.techm.trackingtool.admin.bean.User;
import com.techm.trackingtool.admin.controller.DeleteUserController;
import com.techm.trackingtool.util.LindeList;
import com.techm.trackingtool.util.LindeMap;
import com.techm.trackingtool.util.LindeToolLogManager;

@Repository("deleteUserDAO")
public class DeleteUserDAO {

	private static final LindeToolLogManager logMgr = new LindeToolLogManager(DeleteUserDAO.class.getName());
	
	@Autowired
	 private SessionFactory sessionFactory;
	 
	 public Session getSession() {
	        return sessionFactory.getCurrentSession();
	    }
		
	public LindeList getUserInfo(){
		LindeList usersList = new LindeList();
		//Session session = factory.openSession();
		//Transaction txn = session.beginTransaction();
		Query queryObj = getSession().getNamedQuery("selUsers");
		ArrayList users = (ArrayList)queryObj.list();
		
		logMgr.logMessage("DEBUG","******get User Info Query in DeleteUserDAO*** "+queryObj.getQueryString());
		
		for(int i=0;i<users.size();i++)
		{
			User user=(User)users.get(i);
			LindeMap userMap=new LindeMap();
			LindeMap userDetails=new LindeMap();
			String deletionStatus="";
			LindeList ticketList=(LindeList)getTTsByUser(user);
			
			if(ticketList.size()!=0){
				deletionStatus="NO";
				}
			else {
				deletionStatus="YES";
			}
			userDetails.put("deletionStatus", deletionStatus);
			userDetails.put("user",user);
			
			userMap.put("userDetails", userDetails);
			usersList.add(userMap);
			System.out.println("User ID"+user.getUserID()+"User First Name"+user.getFirstName());
		}
	
		return usersList;
	}
	
	
	public LindeList getApplicationInfo(){
		LindeList moduleList = new LindeList();
		
		try{
		Query queryObj = getSession().getNamedQuery("selModules");
		ArrayList modulesList = (ArrayList)queryObj.list();
				
		for(int i=0;i<modulesList.size();i++)
		{
			Module module=new Module();
			module = (Module)modulesList.get(i);
			moduleList.add(module);

		}

		}catch(Exception accessException)
		{
			logMgr.logStackTrace("ERROR","Exception in DeleteUserDAO getApplicationInfo  "+accessException.getMessage(),accessException);
			
		}

		return moduleList;
	}
	
	
public Module getApplication(String appid){
		
	
	    Module module=new Module();
		
		getSession().load(module,new Integer(appid));
	   Set set=module.getUserid();
	   logMgr.logMessage("DEBUG","app name : "+module.getModulename()+" app user id "+module.getUserid());
	   for(Iterator iterator=set.iterator();iterator.hasNext();)
	   {
		   User user=(User)iterator.next();

	   }
	
	   return module;	 
	
}

public User getUser(String userid){
	
	User user=new User();
	
	getSession().load(user,userid);
   
      System.out.println("User ID"+user.getFirstName());

    return user;	 

}
	

public void deleteApplication(Module application){
	

	
	Module application1=new Module();
	
	getSession().delete(application);
   
	System.out.println("Deletion completed");

}

public void deleteUser(User user,User creator) throws Exception
{
	
	getSession().delete(user);
   
	UserDetailDAO userDetailDAO=new UserDetailDAO();
	userDetailDAO.sendEMails(user, "deleted", creator);
 

}
public LindeList getTTsByUser(User user)
{

	LindeMap ttinfoMap = new LindeMap();
	LindeList crinfoList=new LindeList();

	
	Object[] row;
	String userrole="";
	StringBuffer SQL_QUERY = new StringBuffer("SELECT crinfo.changeId,module.modulename from CRinfo crinfo, Module module ");
	
			SQL_QUERY.append(" where crinfo.phase in ('Accept RfC & Prepare Approval','CI: Validate & Assign','Validate & Assign','Approval','Implementation','Post Implementation Review','Correct Implementation')");
    			SQL_QUERY.append(" AND crinfo.assignee='").append(user.getLastName()).append(", ").append(user.getFirstName()).append("'");
    	   ArrayList query = (ArrayList)getSession().createQuery(SQL_QUERY.toString()).list();
		   
		   for (int i=0;i < query.size(); i++) {
    		   row = (Object[]) query.get(i);
    		   		CRinfo crInfo=new CRinfo();
    		   		crInfo.setChangeId((String)row[0]);
    		   		//crInfo.setModuleName((String)row[1]);
    		   		crInfo.setAffectedCIs((String)row[1]);
		    	   	
		    	   	
		    	   	crinfoList.add(crInfo);
    		   	   }
	    	return crinfoList;
}


public Module getApplicationByName(String name){
	
	Module application=new Module();
	try{
	Query queryObj = getSession().getNamedQuery("selModulebyname");
	//queryObj.setString("appname",name);
	ArrayList applications = (ArrayList)queryObj.list();
	System.out.println("in application retrieval name of application is "+name);
	for(int i=0;i<applications.size();i++)
	{
		Module application1=(Module)applications.get(i);
		if(application1.getModulename().equals(name))
		{	
		
		application = (Module)applications.get(i);
		Set owners=application.getUserid();
		for(Iterator iterator=owners.iterator();iterator.hasNext();)
		{
			User user1=(User)iterator.next();
			logMgr.logMessage("DEBUG","user name is "+user1.getFirstName());
			
		}
		return application1;
		}
		
	}

	}catch(Exception accessException)
	{
		System.out.println("Exception in DeleteUserDAO getApplicationByName is "+accessException);
		accessException.printStackTrace();
		
	}

	System.out.println("*********DeleteUserDAO getApplicationByName exit--*****-->");
	return application;
}

}
