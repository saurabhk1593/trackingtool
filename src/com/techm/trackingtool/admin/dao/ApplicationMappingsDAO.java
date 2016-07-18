package com.techm.trackingtool.admin.dao;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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
import com.techm.trackingtool.admin.bean.Module;
import com.techm.trackingtool.admin.bean.User;
import com.techm.trackingtool.admin.controller.LoginController;
import com.techm.trackingtool.util.LindeList;
import com.techm.trackingtool.util.LindeMap;
import com.techm.trackingtool.util.LindeToolLogManager;

@Repository
public class ApplicationMappingsDAO{
	
	private static LindeToolLogManager logMgr = new LindeToolLogManager(ApplicationMappingsDAO.class.getName());
	
	@Autowired
	 private SessionFactory sessionFactory;
	 
	    public Session getSession() {
	    	Session session=null;
	    	try{
	    		
	    		session = sessionFactory.getCurrentSession();
	    	}
	    	catch(Exception ex)
	    	{
	    		logMgr.logStackTrace("ERROR", ex.getMessage(), ex);
	    	}
	        return session;
	    }
	
	public LindeList getUserModuleInfo(){
		LindeList usersList = new LindeList();

		Query queryObj = getSession().getNamedQuery("selUsers");
		ArrayList users = (ArrayList)queryObj.list();
	
		logMgr.logMessage("DEBUG","******get User Info Query *** "+queryObj.getQueryString());
		
		for(int i=0;i<users.size();i++)
		{
			LindeMap userMap=new LindeMap();
			User user=(User)users.get(i);
		//	System.out.println("User ID"+user.getUserID()+"User First Name"+user.getFirstName());
			Set moduleListSet1=new HashSet();
			LindeList moduleList=new LindeList();
			moduleListSet1=user.getModuleSet();
			String ApplicationList="";

			for(Iterator iterator=moduleListSet1.iterator();iterator.hasNext();)
			   {
				
				Module module=(Module)iterator.next();
				
				moduleList.add(module.getModulename());
				logMgr.logMessage("DEBUG","module name temp is "+module.getModulename());   
			   }
			Collections.sort(moduleList);
			for(Iterator iterator=moduleList.iterator();iterator.hasNext();)
			   {
				  
					String modname=(String)iterator.next();
				//	System.out.println("appname final is "+modname);
				   if(!ApplicationList.equals("")){
				   ApplicationList=ApplicationList+"<br>"+modname;
				   }
				   else
				   {
					   ApplicationList=modname;
				   }
				   
			   }
			logMgr.logMessage("DEBUG","moduleList  "+moduleList);

		userMap.put("userBean", user);
		userMap.put("applicationName",moduleList);
			
		usersList.add(userMap);
		}
		
	//	System.out.println("*********getUserInfo Exit--*****-->");
		return usersList;
	}
	
	
	
	public LindeList getApplicationZeroOwnersInfo(){
		LindeList applicationList = new LindeList();
		try{
		Query queryObj = getSession().getNamedQuery("selModules");
		ArrayList moduleList = (ArrayList)queryObj.list();
		
		
		for(int i=0;i<moduleList.size();i++)
		{
			LindeMap applicationMap=new LindeMap();
			Module module=new Module();
		
			module = (Module)moduleList.get(i);
			
			Set userSet=new HashSet();
			userSet=module.getUserid();
			int noofusers=userSet.size();
			
			String userList="";
			Iterator iterator=userSet.iterator();
			
			if(noofusers==1)
			   {
				   User user=(User)iterator.next();
				   
				   if(!userList.equals(""))
				   {
					   userList=userList+"<	br>"+user.getFirstName();  
					   
				   }
				   else
				   {
					   userList=user.getFirstName();
					   logMgr.logMessage("DEBUG", "user.getFirstName() "+user.getFirstName()+" added to list");
				   }
				   
				   
			   }
			
		if(noofusers<=1 )
		{
		if(noofusers==0){userList="No Owners";}
		applicationMap.put("applicationBean", module);
		applicationMap.put("userName",userList);
		applicationList.add(applicationMap);
		}
	
		}

		}catch(Exception accessException)
		{
			logMgr.logMessage("DEBUG","Exception in getApplicationZeroOwnersInfo is "+accessException);
			accessException.printStackTrace();
			
		}
		
		logMgr.logMessage("DEBUG","*********getApplicationZeroOwnersInfo exit--*****-->");
		return applicationList;
	}
	
	
	
	public LindeList getModuleUserInfo(){
		LindeList moduleList = new LindeList();
	try{
		Query queryObj = getSession().getNamedQuery("selModules");
		ArrayList modules = (ArrayList)queryObj.list();
		
		
		for(int i=0;i<modules.size();i++)
		{
			LindeMap moduleMap=new LindeMap();
			Module module=new Module();
		
			module = (Module)modules.get(i);
			
			Set userSet=new HashSet();
			LindeList userList1=new LindeList();
			userSet=module.getUserid();
			String userList="";
			
			
			for(Iterator iterator=userSet.iterator();iterator.hasNext();)
			   {
				   User user=(User)iterator.next();
				   userList1.add(user.getFirstName());
			   }
			Collections.sort(userList1);
			for(Iterator iterator=userList1.iterator();iterator.hasNext();)
			   {
				   String userfirstname=(String)iterator.next();
				   
				   if(!userList.equals("")){
					   userList=userList+"<br>"+userfirstname;   
				   }
				   else
				   {
					   userList=userfirstname;
				   }
				   
				   
			   }
			//System.out.println("userList "+userList);
			moduleMap.put("applicationBean", module);
			moduleMap.put("userName",userList);
			
			moduleList.add(moduleMap);
			
		}

		}catch(Exception accessException)
		{
			logMgr.logMessage("DEBUG","Exception in getApplicationInfo is "+accessException);
			accessException.printStackTrace();
			
		}
	//	transaction.commit();
	//	session.close();
		logMgr.logMessage("DEBUG","*********getApplicationInfo method exit--*****-->");
		return moduleList;
	}
	
	
	public LindeList getApplicationNames(){
		LindeList applicationList = new LindeList();

		try{
		Query queryObj = getSession().getNamedQuery("selModules");
		ArrayList applications = (ArrayList)queryObj.list();
		
		
		for(int i=0;i<applications.size();i++)
		{
			String appname="";
			Module application=new Module();
		
			application = (Module)applications.get(i);
			appname=application.getModulename();
			
			applicationList.add(appname);
		
			
			
			
		}

		}catch(Exception accessException)
		{
			logMgr.logMessage("DEBUG","Exception in getApplicationNames is "+accessException);
			accessException.printStackTrace();
			
		}

		logMgr.logMessage("DEBUG","*********getApplicationNames Exit--*****-->");
		return applicationList;
	}

}
