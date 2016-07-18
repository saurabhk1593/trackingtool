package com.techm.trackingtool.admin.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import com.techm.trackingtool.admin.bean.Module;
import com.techm.trackingtool.util.LindeToolLogManager;

@Repository("addApplicationDAO")
public class AddApplicationDAO{
	
	private static LindeToolLogManager logMan = new LindeToolLogManager(AddApplicationDAO.class.getName());
	
	 @Autowired
	 private SessionFactory sessionFactory;
	 
	    public Session getSession() {
	    	Session session=null;
	    	try{
	    		
	    		session = sessionFactory.getCurrentSession();
	    	}
	    	catch(Exception ex)
	    	{
	    		logMan.logStackTrace("ERROR", ex.getMessage(), ex);
	    	}
	        return session;
	    }
	
public Module SubmitApplication(Module module){
		
		try{
			
		
			getSession().save(module);

		}
		catch(Exception ex)
		{
		
			logMan.logStackTrace("ERROR","In SubmitApplication "+ex.getMessage(),ex);
			throw ex;
		}
	
		return module;	 
	}

public List<Module> getModuleList(){
	List<Module> moduleList =null;
	try{
		
	
	 moduleList =	getSession().getNamedQuery("selModules").list();

	}
	catch(Exception ex)
	{
	
		logMan.logStackTrace("ERROR","In SubmitApplication "+ex.getMessage(),ex);
		throw ex;
	}

	return moduleList;	 
}


}
