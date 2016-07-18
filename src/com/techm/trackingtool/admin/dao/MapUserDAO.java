package com.techm.trackingtool.admin.dao;
import java.util.ArrayList;
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
import com.techm.trackingtool.util.LindeList;
import com.techm.trackingtool.util.LindeToolLogManager;

@Repository
public class MapUserDAO{
	/*private static Configuration cfg;
	private static SessionFactory factory;
	
	public MapUserDAO()
	{
		System.out.println("Inside MapUserDAO");
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		System.out.println("After cfg");
		//cfg.addFile("UserDetails.hbm.xml");
		System.out.println("After hbm");
		factory = cfg.buildSessionFactory();

	}*/
	
	private static final LindeToolLogManager lindeLogMgr = new LindeToolLogManager(MapUserDAO.class.getName());
	
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
	
	public LindeList getApplicationInfo(){
		LindeList moduleList = new LindeList();
	try{
		System.out.println("<---in MapUserDAO getApplicationInfo---->");
		try{
		Query queryObj = getSession().getNamedQuery("selModules");
		ArrayList modulesLst = (ArrayList)queryObj.list();
		System.out.println("<---Problem in MapUserDAO getApplicationInfo 1 ---->");
		
		for(int i=0;i<modulesLst.size();i++)
		{
			Module module=new Module();
			System.out.println("Problem in MapUserDAO getApplicationInfo 2 ---->");
			module = (Module)modulesLst.get(i);
			moduleList.add(module);
			//System.out.println(" " + application.getModulename());
		}

		}catch(Exception accessException)
		{

			lindeLogMgr.logStackTrace("ERROR", "accessException in getApplicationInfo", accessException);
			
		}

	}
	catch(Exception e)
	{
		lindeLogMgr.logStackTrace("ERROR", "Exception in getApplicationInfo", e);
	}
		return moduleList;
	}
	
	public LindeList getUserInfo(){
		LindeList usersList = new LindeList();
	
		Query queryObj = getSession().getNamedQuery("selUsers");
		ArrayList users = (ArrayList)queryObj.list();
		
		for(int i=0;i<users.size();i++)
		{
			usersList.add(users.get(i));
			User user=(User)users.get(i);
			System.out.println("User ID"+user.getUserID()+"User First Name"+user.getFirstName());
		}

		return usersList;
		
	}
	
	
	public Set getUserSet(Set userSet){
			
			Set finalUsers=new HashSet();
			String querypart="('";
			Iterator iter = userSet.iterator();
			while (iter.hasNext())
			{
			     querypart += ""+(String)iter.next()+"','";
			     
			}

			querypart+="')";
			 
			//Session session3 = factory.openSession();
		//	Transaction txn3 = session3.beginTransaction();
			System.out.println("in Application getUserSet()");
	 	   
	 	   Object[] row;
		   String SQL_QUERY = new String("SELECT userID,location,firstName,lastName,emailId,roleId,approvalStatus,password FROM User user where user.userID in "+querypart);
		   System.out.println("sql query is : "+SQL_QUERY);
		   ArrayList query =(ArrayList) getSession().createQuery(SQL_QUERY).list();
    	   for (int i=0;i < query.size(); i++) {
    		   row = (Object[]) query.get(i);
    		   		User user=new User();
		    	   	user.setUserID((String)row[0]);
		    	 	user.setLocation((String)row[1]);
		    	   	user.setFirstName((String)row[2]);
		    	   	user.setLastName((String)row[3]);
		    	   	user.setEmailId((String)row[4]);
		    	   	user.setRoleId((Integer)row[5]);
		    	   	user.setApprovalStatus((String)row[6]);
		    	   	user.setPassword((String)row[7]);
    		   		finalUsers.add(user);
    		   	   }
		//   txn3.commit();
		//   session3.close();  
	return finalUsers;	 
		
	}
	
	
	public Module getApplication(int appid){
		
	//	Session session4 = factory.openSession();
	//	Transaction txn4 = session4.beginTransaction();
		Module application=new Module();
		Module application1=new Module();
		System.out.println("in Application getApplication()");
 	   Object[] row1;
 	  Object[] row2;
	   String SQL_QUERY1 = new String(" FROM Module app where app.moduleid="+appid);
	   System.out.println("sql query is : "+SQL_QUERY1);
	   ArrayList query1 =(ArrayList) getSession().createQuery(SQL_QUERY1).list();
	 
	  /* String SQL_QUERY2 = new String("SELECT app.userid FROM Application app where app.applicationid="+appid);
	   System.out.println("sql query is : "+SQL_QUERY2);
	   ArrayList query2 =(ArrayList) getSession().createQuery(SQL_QUERY2).list();*/
	   
	   Set set=new HashSet();
	 //  getSession().load(application1,new Integer(appid));
	   application1 =(Module) query1.get(0);
	   set=application1.getUserid();
	   System.out.println("app name : "+application1.getModulename()+" app user id "+application1.getUserid());
	   for(Iterator iterator=set.iterator();iterator.hasNext();)
	   {
		   User user=(User)iterator.next();
		   System.out.println("User ID"+user.getFirstName());
	   }
		   
	   /*for (int i=0;i < query1.size(); i++) {
		   row1 = (Object[]) query1.get(i);
		   		
	    	   	application.setApplicationid((Integer)row1[0]);
	    	   	application.setApplicationname((String)row1[1]);
	    	   	application.setRegion((String)row1[2]);
	    	   /*	for (int j=0;j < query2.size(); j++) {
	    	   		set = (Set) query2.get(j);
	    	   		//set=(Set)row2[0];
	    	   	}
	    	   	
	    	   	System.out.println("value from database "+set.toString());
	    	   	
	    	   	
	    	   	
	    	   	application.setUserid(set);
	      	   }*/
	//   txn4.commit();
	//   session4.close();  
	   
return application1;	 
	
}
	
	
public Application deleteApplication(Application application){
		
		//Session session5 = factory.openSession();
		//Transaction txn5 = session5.beginTransaction();
		Application application1=new Application();
		application.userid=null;
		application.setApplicationname(application.getApplicationname());
		application.setRegion(application.getRegion());
		System.out.println("in Application delete"+"NAME"+application.getApplicationname()+""+application.getRegion()+"");
		getSession().update(application);
	  
	 //  txn5.commit();
	 //  session5.close();  
return application1;	 
	
}
	
	public boolean submitUserMapping(Module application3){
		boolean success = true;
		

		lindeLogMgr.logMessage("INFO", "Entered in submitUserMapping");
		
		try{
		
		
			getSession().update(application3);
		}
		catch(Exception accessException)
		{

			lindeLogMgr.logStackTrace("ERROR", "accessException in submitUserMapping", accessException);
			
		}
		
		return success;
	}
	

}
