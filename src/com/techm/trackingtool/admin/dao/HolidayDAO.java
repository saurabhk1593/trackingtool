package com.techm.trackingtool.admin.dao;

import java.util.Iterator;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.techm.trackingtool.admin.bean.Holidays;
import com.techm.trackingtool.admin.bean.Leave;
import com.techm.trackingtool.admin.bean.User;
import com.techm.trackingtool.util.HashMap;
import com.techm.trackingtool.util.LindeToolLogManager;


@Repository
public class HolidayDAO {
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
	
	public Holidays submitHolidays(Holidays holidayDetails)
	{
		
		getSession().save(holidayDetails);
		return holidayDetails;
		
	}
	
	public Holidays getHolidays()
	{
		
		return new Holidays();
		
	}
	
	public HashMap getHoliday()
	{
		HashMap holiday=new HashMap();
		
		
		StringBuffer SQL_QUERY = new StringBuffer("select holidayDate,description from Holidays");
		try{
			
			Query query = getSession().createQuery(SQL_QUERY.toString());
			 for (Iterator it = query.iterate(); it.hasNext();){
				 Object row[] = (Object[]) it.next();
				 holiday.put(row[0], row[1]);
			 }
			lindeLogMgr.logMessage("INFO", "Value of holiday::::::"+holiday.toString());
		}catch(Exception e)
		{
			lindeLogMgr.logStackTrace("ERROR", "Error in fetching leave data", e);
		}
		
		
		return holiday;
	}
	

	
	
}
