package com.techm.trackingtool.admin.dao;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.techm.trackingtool.admin.bean.TicketInfo;
import com.techm.trackingtool.admin.bean.User;
import com.techm.trackingtool.admin.bean.VivaldiInfo;
import com.techm.trackingtool.util.LindeList;
import com.techm.trackingtool.util.LindeMap;
import com.techm.trackingtool.util.LindeToolLogManager;

@Repository("vivaldiinfodao")
public class VivaldiInfoDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	private static final LindeToolLogManager lindeLogMgr = new LindeToolLogManager(VivaldiInfoDAO.class.getName());	
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
	    
	
	 public String uploadInfo(List vivaldiList,User user) throws Exception,SQLException
		{
		 lindeLogMgr.logMessage("DEBUG", "Inside uploadinfo of vivaldi ");
			
			String status = "failed";
				
			Date sysDate = new Date();
			DateFormat formatter=DateFormat.getDateInstance();
			formatter.format(formatter.LONG);
			String currentDate = formatter.format(sysDate);
			try{
								
			for(int i=0;i<vivaldiList.size();i++)
			{
				VivaldiInfo vivaldiInfo = (VivaldiInfo)vivaldiList.get(i);
				getSession().saveOrUpdate(vivaldiInfo);
			}
			}
			
			catch (Exception e) {
				lindeLogMgr.logMessage("ERROR","error in uploading is: "+e.getMessage());
				lindeLogMgr.logStackTrace("ERROR", "Unable to upload vivaldi data", e);
				//throw e;
			}
			
			//txn.commit();
	 	   //	session.close(); 
			status="true";
			//getSLAdetails("AU");
			return status;
		}

}
