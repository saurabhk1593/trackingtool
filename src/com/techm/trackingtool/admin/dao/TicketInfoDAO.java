package com.techm.trackingtool.admin.dao;

import java.io.InputStream;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import com.techm.trackingtool.admin.bean.Application;
import com.techm.trackingtool.admin.bean.DataUploadInformation;
import com.techm.trackingtool.admin.bean.Module;
import com.techm.trackingtool.admin.bean.TicketInfo;
import com.techm.trackingtool.admin.bean.User;
import com.techm.trackingtool.admin.vo.TicketSummaryVO;
import com.techm.trackingtool.util.LindeList;
import com.techm.trackingtool.util.LindeMap;
import com.techm.trackingtool.util.LindeToolLogManager;

@Repository("ticketinfodao")
public class TicketInfoDAO {
	

	private static final LindeToolLogManager lindeLogMgr = new LindeToolLogManager(TicketInfoDAO.class.getName());

	
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
	    
	public TicketSummaryVO getTTInformations(User userInfo,String month,String year) throws ParseException{
		TicketSummaryVO ttSummaryVO = new TicketSummaryVO();
		LindeMap severityMap = getTTInfoBySeverity(userInfo,month,year);
		LindeMap statusMap = getTTStatus(userInfo,month,year);	
		LindeMap byAgeMap = getTTInfoByAge(userInfo,month,year);
		LindeMap byAgeRegionWiseMap = getRegionWiseTTInfoByAge(userInfo,month,year);
		String infoUploadDate = getUploadDate();
		ttSummaryVO.setBySeverity(severityMap);
		ttSummaryVO.setByStatus(statusMap);
		ttSummaryVO.setByAge(byAgeMap);
		ttSummaryVO.setByAgeRegionWise(byAgeRegionWiseMap);
		ttSummaryVO.setUploadDate(infoUploadDate);
		
		
		return ttSummaryVO;
	}
	public LindeMap getTTInfoBySeverity(User userInfo,String month,String year)
	{
		lindeLogMgr.logMessage("DEBUG","Inside getTTInfoBySeverity method ");
		
		LindeMap severityMap = new LindeMap(); 
		

		String tableObjectName="TicketInfo";
	
		lindeLogMgr.logMessage("DEBUG", "Before call to  getTTInfoBySeverity query tableObjectName is : "+tableObjectName);
		
		StringBuffer SQL_QUERY = new StringBuffer("select replace(priority,' ',''),count(*) from TicketInfo where status in('Pending Customer','Work In Progress','Assigned') ");
		try {
			System.out.println("Inside getTTInfoBySeverity");
			long sum = 0;
			if(!month.equals("ALL"))
			{
				SQL_QUERY.append("and to_date(openTime,'dd/MM/yyyy  HH24:MI:ss') Between trunc(to_date('"+month+"', 'MON-YYYY'),'MON') AND LAST_DAY(TO_DATE('"+month+"', 'MON-YYYY')) ");
			}
			//String str1="";
		   //StringBuffer SQL_QUERY = new StringBuffer("select replace(ttinfo.priority,' ',''),count(*) from "+tableObjectName+" ttinfo where ttinfo.status in ('Pending Customer','Work In Progress','Assigned')");
			if(userInfo.getRoleId()==1)
			{
				SQL_QUERY.append("and assigneeFullname in (").append("select UPPER(lastName||', '||firstName) from User where userID='").append(userInfo.getUserID()).append("')");
			}
			else if(userInfo.getRoleId()==2)
			{
				SQL_QUERY.append("and assigneeFullname in (").append("select UPPER(lastName||', '||firstName) from User where location='").append(userInfo.getLocation()).append("')");
			}
			if(userInfo.getRoleId()!=3)
            {
				SQL_QUERY.append(" group by priority  ");
              LindeMap lResultMap = getIncidentPriorityDetails(userInfo,SQL_QUERY.toString(),userInfo.getLocation());
              severityMap.put("OffShore", lResultMap);
              severityMap.put("Location", userInfo.getLocation());
            }
			
			if(userInfo.getRoleId()==3)
            {
                  lindeLogMgr.logMessage("DEBUG", "user.getRoleId() :: "+userInfo.getRoleId());
                  String subPrgQueryStr = SQL_QUERY.toString();
                  
                  for(int i=1;i<=2;i++)
                  {
                         
                         String location=null;
                         if(i==1)
                         {
                                location="OffShore";
                                SQL_QUERY.append("and assigneeFullname in (select UPPER(users1.lastName||', '||users1.firstName) from User users1 where users1.location ='Offshore')");
                         }
                         else if(i==2)
                         {
                        	 SQL_QUERY.append(subPrgQueryStr);
                             location="OnShore";
                             SQL_QUERY.append("and assigneeFullname in (select UPPER(users1.lastName||', '||users1.firstName) from User users1 where users1.location ='Onsite')");
                         }
                         
                         
                         SQL_QUERY.append(" group by priority  ");
                         lindeLogMgr.logMessage("DEBUG", "user.getRoleId() : prioritySqlQuery.toString() : "+SQL_QUERY.toString());
                         
                         LindeMap lResultMap = getIncidentPriorityDetails(userInfo,SQL_QUERY.toString(),location);
                         severityMap.put(location, lResultMap);
                         severityMap.put("Location", location);
                         
                         SQL_QUERY.delete(0, SQL_QUERY.toString().length());
                         
                         
                         
                  }
                  
            }
	    	   	
		} catch(Exception e){
			lindeLogMgr.logMessage("ERROR", "SQLException in getTTInfoBySeverity" +e.getMessage());			
		}
	    	//   session.close();  
		
	    	return severityMap;
	}
	
	
	private LindeMap getIncidentPriorityDetails(User user,String queryString,String location)
    {
       LindeMap lPriorityMap = new LindeMap();
       try
       {
       Query query = getSession().createQuery(queryString);
         
          
          long sum = 0;
         for (Iterator it = query.iterate(); it.hasNext();) {
                Object[] row = (Object[]) it.next();
                
              
                sum += (Long) row[1];
                
                String priorityStr =(String) row[0];
                lindeLogMgr.logMessage("DEBUG", "priorityStr "+priorityStr);
                String valueStr =  row[1].toString();
                
                lindeLogMgr.logMessage("DEBUG", "priorityStr value "+valueStr);
                
                if(priorityStr.contains("1-"))
                       lPriorityMap.put("P1", valueStr);
                else if(priorityStr.contains("2-"))
                       lPriorityMap.put("P2", valueStr);
                else if(priorityStr.contains("3-"))
                       lPriorityMap.put("P3",valueStr);
                else if(priorityStr.contains("4-"))
                       lPriorityMap.put("P4", valueStr);

                lindeLogMgr.logMessage("DEBUG", "priorityStr "+row[0]);
                lindeLogMgr.logMessage("DEBUG", "priorityStr value "+row[1]);
                lPriorityMap.put(row[0], row[1]);
                

         }
                lindeLogMgr.logMessage("DEBUG", "Total Number of Records::: "+sum);
                lPriorityMap.put("Total", sum);
                
                if(user.getLocation()!=null && user.getRoleId() !=3)
                       lPriorityMap.put("Location", user.getLocation());
                else
                       lPriorityMap.put("Location", location);
       }
       catch(Exception e)
       {
              lindeLogMgr.logStackTrace("ERROR", "SQLException in getIncidentPriorityDetails" +e.getMessage(),e);  
       }
                
                return lPriorityMap;
    }


	
	
	
	public LindeMap getTTStatus(User userInfo,String month,String year)
	{
		
		LindeMap statusMap = new LindeMap(); 
		//Session session = factory.openSession();
		//Transaction txn = session.beginTransaction();
		lindeLogMgr.logMessage("DEBUG", "Before call to  getTTStatus query");
		//Query queryObj = session.getNamedQuery("checkUser");
		
		String tableObjectName="TicketInfo"; 
		 
        LindeMap innerMap = new LindeMap();
        
        //String tableBeanName = "TicketInfo";
        try{
        	lindeLogMgr.logMessage("DEBUG", "Before executing status HBL query in status");
             // StringBuffer SQL_QUERY = new StringBuffer(" select replace(ttinfo.status,'Work In Progress','WIP'),app.region,count(*) from "+tableObjectName+" ttinfo,Application app where ttinfo.application=app.applicationname ");
              StringBuffer statusSqlQuery = new StringBuffer("select info1.status, module1.modulename ,count(*) from "+tableObjectName+" info1, OwnerModule owner1, Module module1, User user1 where info1.assigneeFullname in (UPPER(user1.lastName||', '||user1.firstName)) and module1.moduleid = owner1.moduleId and user1.userID=owner1.userId ");
              if(!month.equals("ALL"))
  			{
            	  statusSqlQuery.append("and to_date(info1.openTime,'dd/MM/yyyy  HH24:MI:ss') Between trunc(to_date('"+month+"', 'MON-YYYY'),'MON') AND LAST_DAY(TO_DATE('"+month+"', 'MON-YYYY')) ");
  			}
              if(userInfo.getRoleId()==1){
            	  
             
                            statusSqlQuery.append("and user1.userID ='").append(userInfo.getUserID()).append("'");
                            
                            
              }
              else if(userInfo.getRoleId()==2)
              {
            	  /*statusSqlQuery.append("and user1.userID ='").append(userInfo.getUserID()).append("'");*/
            	  statusSqlQuery.append("and user1.location='"+userInfo.getLocation()+"'");
            	  lindeLogMgr.logMessage("DEBUG", "After role not 3 string buffer status HBL query in status");
            	  
              }
              
              if(userInfo.getRoleId()!=3)
              {
            	  statusSqlQuery.append("group by info1.status, module1.modulename order by info1.status");
            	 
                  LindeMap lResultMap = getStatusDetails(userInfo,statusSqlQuery.toString(),userInfo.getLocation());
                  statusMap.put("OffShore", lResultMap);
                  
                  statusMap.put("Location", userInfo.getLocation());
              }
              	 
              if(userInfo.getRoleId()==3)
              {
                    lindeLogMgr.logMessage("INFO", "user.getRoleId() :: "+userInfo.getRoleId());
                    String subPrgQueryStr = statusSqlQuery.toString();
                    
                    for(int i=1;i<=2;i++)
                    {
                           
                           String location=null;
                           if(i==1)
                           {
                                  location="OffShore";
                                  statusSqlQuery.append("and user1.location='Offshore'");
                           }
                           else if(i==2)
                           {
                        	   statusSqlQuery.append(subPrgQueryStr);
                               location="OnShore";
                               statusSqlQuery.append("and user1.location='Onsite'");
                           }
                           
                           
                           statusSqlQuery.append(" group by info1.status, module1.modulename order by info1.status");
                           lindeLogMgr.logMessage("INFO", "user.getRoleId() : StatusSqlQuery.toString() : "+statusSqlQuery.toString());
                           
                           LindeMap lResultMap = getStatusDetails(userInfo,statusSqlQuery.toString(),location);
                           statusMap.put(location, lResultMap);
                           statusMap.put("Location", location);
                           
                           statusSqlQuery.delete(0, statusSqlQuery.toString().length());
                           
                           
                    }
                    
              }
              
              
                 

      }catch(Exception e){
            lindeLogMgr.logStackTrace("ERROR", "SQLException in getCRStatus : " +e.getMessage(),e);
      }


		
	    	return statusMap;	   
	}
	
	private LindeMap getStatusDetails(User user,String queryString,String location)
    {
		lindeLogMgr.logMessage("DEBUG", "outside try block of getStatusDetails");
       LindeMap lstatusMap = new LindeMap();
       LindeMap innerMap = innerMap = new LindeMap();
       try
       {
    	   
    	   ArrayList query = (ArrayList) getSession().createQuery(queryString.toString()).list();
           Object[] temp = new Object[1];
           Object[] row;
           
           
          boolean isRowInserted =false;                    
           long groupSum=0,total=0;
           long tempothers=0;
                
                
                
           for (int i=0;i<query.size();i++) {
               row = (Object[]) query.get(i);
               
               if(row[0].equals("Closed")) 
               {
                     row[0]="Closed";
               }
               else if(row[0].equals("Pending Customer")) 
                {
                      row[0]="Pending";
                }
               
               else if(row[0].equals("Assigned")) 
               {
                     row[0]="Assigned";
               }
               else if(row[0].equals("Work In Progress")) 
                {
                      row[0]="WIP";
                }
                
               lindeLogMgr.logMessage("DEBUG", "Values++++++++++++:::"+row[0]+"  "+row[1]+" "+row[2]);
               
               if (i!=0){
                      if(!row[0].toString().equals(temp[0].toString()))
                      {
                    	     innerMap = new LindeMap();
                             lindeLogMgr.logMessage("DEBUG", "Resetting Tempother value : in statusinfo ");
                             groupSum=0;
                             lindeLogMgr.logMessage("DEBUG", "Different status:"+row[0]+":temp:"+temp[0]);
                      }
               }
               groupSum +=(Long)row[2];
               total+=(Long)row[2];

               innerMap.put(row[1], row[2]);
               innerMap.put("Total", groupSum);
               
               if(i==0 || !row[0].toString().equals(temp[0].toString()))
            	   lstatusMap.put(row[0], innerMap);
                          
                          temp[0] = row[0];
        }
           lstatusMap.put("Total", total);
       
                
                if(user.getLocation()!=null && user.getRoleId() !=3)
                       lstatusMap.put("Location", user.getLocation());
                else
                       lstatusMap.put("Location", location);
       }
       catch(Exception e)
       {
              lindeLogMgr.logStackTrace("ERROR", "SQLException in getStatusDetails" +e.getMessage(),e);  
       }
                
                return lstatusMap;
    }
	public long calDate() throws ParseException
	{
		
		
		
			
		long age=0;
		
   		
   		return age;
	}
	
	public LindeMap getTTInfoByAge(User userInfo,String month,String year) throws ParseException
	{
		System.out.println("Inside getTTInfoByAge");
		LindeMap byAgeMap = new LindeMap(); 
		lindeLogMgr.logMessage("DEBUG", "Before call to  getTTInfoByAge query");
		lindeLogMgr.logMessage("DEBUG", "After call to  getTTInfoByAge query");
		String tableObjectName="TicketInfo";			
		

		long total =0;
		StringBuffer SQL_QUERY = new StringBuffer("select ageGrp.ageGroup,count(*) from "+tableObjectName+" ttInfo,TTAge ageGrp where ttInfo.status in ('Pending Customer','Work In Progress','Assigned')");
		SQL_QUERY.append(" AND CURRENT_DATE BETWEEN (to_date(ttInfo.openTime,'dd/MM/yyyy HH24:MI:ss') + ageGrp.rangeStart) and (to_date(ttInfo.openTime,'dd/MM/yyyy HH24:MI:ss') + ageGrp.rangeEnd) ");
		if(!month.equals("ALL"))
			{
			SQL_QUERY.append("and to_date(ttInfo.openTime,'dd/MM/yyyy  HH24:MI:ss') Between trunc(to_date('"+month+"', 'MON-YYYY'),'MON') AND LAST_DAY(TO_DATE('"+month+"', 'MON-YYYY')) ");
			}
		if(userInfo.getRoleId()==1)
			SQL_QUERY.append("and ttInfo.assigneeFullname=(").append("select UPPER(lastName||', '||firstName) from User where userID ='").append(userInfo.getUserID()).append("')");
		if(userInfo.getRoleId()==2)
			SQL_QUERY.append("and ttInfo.assigneeFullname in (").append("select UPPER(lastName||', '||firstName) from User where location ='").append(userInfo.getLocation()).append("')");
		
		
		if(userInfo.getRoleId()!=3)
        {
			SQL_QUERY.append(" group by ageGrp.ageGroup  ");
          LindeMap lResultMap = getIncidentInfoByAge(userInfo,SQL_QUERY.toString(),userInfo.getLocation());
          byAgeMap.put("OffShore", lResultMap);
          byAgeMap.put("Location", userInfo.getLocation());
        }
		
		if(userInfo.getRoleId()==3)
        {
              lindeLogMgr.logMessage("INFO", "user.getRoleId() :: "+userInfo.getRoleId());
              String subPrgQueryStr = SQL_QUERY.toString();
              
              for(int i=1;i<=2;i++)
              {
                     
                     String location=null;
                     if(i==1)
                     {
                            location="OffShore";
                            SQL_QUERY.append("and ttInfo.assigneeFullname in (select UPPER(users1.lastName||', '||users1.firstName) from User users1 where users1.location ='Offshore')");
                     }
                     else if(i==2)
                     {
                    	 SQL_QUERY.append(subPrgQueryStr);
                         location="OnShore";
                         SQL_QUERY.append("and ttInfo.assigneeFullname in (select UPPER(users1.lastName||', '||users1.firstName) from User users1 where users1.location ='Onsite')");
                     }
                     
                     
                     SQL_QUERY.append(" group by ageGrp.ageGroup  ");
                     lindeLogMgr.logMessage("INFO", "user.getRoleId() : prioritySqlQuery.toString() : "+SQL_QUERY.toString());
                     
                     LindeMap lResultMap = getIncidentInfoByAge(userInfo,SQL_QUERY.toString(),location);
                     byAgeMap.put(location, lResultMap);
                     byAgeMap.put("Location", location);
                     
                     SQL_QUERY.delete(0, SQL_QUERY.toString().length());
                     
              }
              
        }
	 
    	return byAgeMap;	
    }
	
	
	private LindeMap getIncidentInfoByAge(User user,String queryString,String location)
    {
       LindeMap lAgeMap = new LindeMap();
       try
       {
       Query query = getSession().createQuery(queryString);
         
          
          long sum = 0;
         for (Iterator it = query.iterate(); it.hasNext();) {
                Object[] row = (Object[]) it.next();
                
                sum += (Long) row[1];
                
                lAgeMap.put(row[0], row[1]);
           

                lindeLogMgr.logMessage("DEBUG", "priorityStr "+row[0]);
                lindeLogMgr.logMessage("DEBUG", "priorityStr value "+row[1]);
                lAgeMap.put(row[0], row[1]);
                

         }
         lAgeMap.put("Total",sum);
        
         if(user.getLocation()!=null && user.getRoleId() !=3)
        	 lAgeMap.put("Location", user.getLocation());
                else
                	lAgeMap.put("Location", location);
       }
       catch(Exception e)
       {
              lindeLogMgr.logStackTrace("ERROR", "SQLException in getIncidentInfoByAge" +e.getMessage(),e);  
       }
                
                return lAgeMap;
    }
	
	
	
	
	
	
	public LindeMap getRegionWiseTTInfoByAge(User userInfo,String month,String year)
	{
		LindeMap byAgeRegionWiseMap = new LindeMap(); 
	//	Session session = factory.openSession();
	//	Transaction txn = session.beginTransaction();
		lindeLogMgr.logMessage("DEBUG", "Before call to  getRegionWiseTTInfoByAge query");
		//Query queryObj = session.getNamedQuery("checkUser");
		lindeLogMgr.logMessage("DEBUG", "After call to  getRegionWiseTTInfoByAge query");
			
        
        //LindeMap statusMap = new LindeMap(); 
        LindeMap innerMap = new LindeMap();
        
        String tableBeanName = "TicketInfo";
       // lindeLogMgr.logMessage("DEBUG", "before try block+++++++++:::::");
        try{
        	//lindeLogMgr.logMessage("DEBUG", "inside try block+++++++++:::::");
             // StringBuffer SQL_QUERY = new StringBuffer(" select replace(ttinfo.status,'Work In Progress','WIP'),app.region,count(*) from "+tableObjectName+" ttinfo,Application app where ttinfo.application=app.applicationname ");
              StringBuffer statusSqlQuery = new StringBuffer("select ageGrp.ageGroup, module1.modulename, count(*) from OwnerModule ownermodule1, Module module1, User user1, "+tableBeanName+" ttInfo,TTAge ageGrp where ttInfo.status in ('Pending Customer','Work In Progress','Assigned','Closed') AND CURRENT_DATE BETWEEN (to_date(ttInfo.openTime,'dd/MM/yyyy HH24:MI:ss') + ageGrp.rangeStart) and (to_date(ttInfo.openTime,'dd/MM/yyyy HH24:MI:ss') + ageGrp.rangeEnd) and ttInfo.assigneeFullname in (UPPER(user1.lastName||', '||user1.firstName)) and module1.moduleid=ownermodule1.moduleId and user1.userID=ownermodule1.userId ");
              if(!month.equals("ALL"))
  			{
            	  statusSqlQuery.append("and to_date(ttInfo.openTime,'dd/MM/yyyy  HH24:MI:ss') Between trunc(to_date('"+month+"', 'MON-YYYY'),'MON') AND LAST_DAY(TO_DATE('"+month+"', 'MON-YYYY')) ");
  			}
              if(userInfo.getRoleId()==1)
            	  statusSqlQuery.append(" and user1.userID='"+userInfo.getUserID()+"'");
              else if(userInfo.getRoleId()==2)
            	  statusSqlQuery.append(" and user1.location='"+userInfo.getLocation()+"'");
                  
              if(userInfo.getRoleId()!=3)
              {
            	  statusSqlQuery.append(" group by ageGrp.ageGroup, module1.modulename order by ageGrp.ageGroup"); 
            	  LindeMap lResultMap = getModuleWiseAgeDetails(userInfo,statusSqlQuery.toString(),userInfo.getLocation());
            	  byAgeRegionWiseMap.put("OffShore", lResultMap);
            	  byAgeRegionWiseMap.put("Location", userInfo.getLocation());
              }
              
              if(userInfo.getRoleId()==3)
              {
            	  lindeLogMgr.logMessage("INFO", "user.getRoleId() :: "+userInfo.getRoleId());
                  String subPrgQueryStr = statusSqlQuery.toString();
                  
                  for(int i=1;i<=2;i++)
                  {
                         
                         String location=null;
                         if(i==1)
                         {
                                location="OffShore";
                                statusSqlQuery.append("and user1.location='Offshore'");
                         }
                         else if(i==2)
                         {
                        	 statusSqlQuery.append(subPrgQueryStr);
                             location="OnShore";
                             statusSqlQuery.append("and user1.location='Onsite'");
                         }
                         
                         
                         statusSqlQuery.append(" group by ageGrp.ageGroup, module1.modulename order by ageGrp.ageGroup");
                         lindeLogMgr.logMessage("INFO", "user.getRoleId() : prioritySqlQuery.toString() : "+statusSqlQuery.toString());
                         
                         LindeMap lResultMap = getModuleWiseAgeDetails(userInfo,statusSqlQuery.toString(),location);
                         byAgeRegionWiseMap.put(location, lResultMap);
                         byAgeRegionWiseMap.put("Location", location);
                         
                         statusSqlQuery.delete(0, statusSqlQuery.toString().length());
                         
                  } 
              }
              	
        }catch (Exception e) {
					e.printStackTrace();
				}

	    	return byAgeRegionWiseMap;	 
	}
	
	
	private LindeMap getModuleWiseAgeDetails(User user,String queryString,String location)
    {
		lindeLogMgr.logMessage("DEBUG", "outside try block of getStatusDetails");
       LindeMap lstatusMap = new LindeMap();
       LindeMap innerMap = innerMap = new LindeMap();
       try
       {
    	   
    	   ArrayList query = (ArrayList) getSession().createQuery(queryString.toString()).list();
           Object[] temp = new Object[1];
           Object[] row;
           
           
          boolean isRowInserted =false;                    
           long groupSum=0,total=0;
           long tempothers=0;
                
                
                
           for (int i=0;i<query.size();i++) {
               row = (Object[]) query.get(i);
               
             
                
               lindeLogMgr.logMessage("DEBUG", "Values++++++++++++:::"+row[0]+"  "+row[1]+" "+row[2]);
               
               if (i!=0){
                      if(!row[0].toString().equals(temp[0].toString()))
                      {
                    	     innerMap = new LindeMap();
                             lindeLogMgr.logMessage("DEBUG", "Resetting Tempother value : in statusinfo ");
                             groupSum=0;
                             lindeLogMgr.logMessage("DEBUG", "Different status:"+row[0]+":temp:"+temp[0]);
                      }
               }
               groupSum +=(Long)row[2];
               total+=(Long)row[2];

               innerMap.put(row[1], row[2]);
               innerMap.put("Total", groupSum);
               
               if(i==0 || !row[0].toString().equals(temp[0].toString()))
            	   lstatusMap.put(row[0], innerMap);
                          
                          temp[0] = row[0];
        }
           lstatusMap.put("Total", total);
       
                
                if(user.getLocation()!=null && user.getRoleId() !=3)
                       lstatusMap.put("Location", user.getLocation());
                else
                       lstatusMap.put("Location", location);
       }
       catch(Exception e)
       {
              lindeLogMgr.logStackTrace("ERROR", "SQLException in getModuleWiseAgeDetails" +e.getMessage(),e);  
       }
                
                return lstatusMap;
    }
	
	
	public String uploadInfo(List ticketList,User user) throws Exception,SQLException
	{
		
		String status = "failed";
		//Session session = factory.openSession();
	//	Transaction txn = session.beginTransaction();
		lindeLogMgr.logMessage("DEBUG", "ticket list"+ticketList.toString());
		lindeLogMgr.logMessage("DEBUG", "ticket list size"+ticketList.size());
		Date sysDate = new Date();
		DateFormat formatter=DateFormat.getDateInstance();
		formatter.format(formatter.LONG);
		String currentDate = formatter.format(sysDate);
		try{
			List<User> userList = getSession().createCriteria(User.class).list();
			Map<String,String> userMap= new HashMap();
			for (User user2 : userList) {
				userMap.put(user2.getUserID(), user2.getLastName().toUpperCase()+", "+user2.getFirstName().toUpperCase());
			}
		for(int i=0;i<ticketList.size();i++)
		{
			TicketInfo ticketInfo = (TicketInfo)ticketList.get(i);
			lindeLogMgr.logMessage("DEBUG", "getAssigneeFullname i"+ticketInfo.getAssigneeFullname());
			lindeLogMgr.logMessage("DEBUG", "getResolvedBy i"+ticketInfo.getResolvedBy());
			if( ticketInfo.getAssigneeFullname().equals(""))
			{
				ticketInfo.setAssigneeFullname(userMap.get(ticketInfo.getResolvedBy()));
			}
			
			lindeLogMgr.logMessage("DEBUG", "ticketInfo.getChangeId() "+ticketInfo.getIncidentID());
			getSession().saveOrUpdate(ticketInfo);
		}
		//boolean val = setUploadDate(user);
		
		}
		
		catch (Exception e) {
			lindeLogMgr.logMessage("ERROR","error in uploading is: "+e.getMessage());
			//throw e;
		}
		
		//txn.commit();
 	   //	session.close(); 
		status="true";
		//getSLAdetails("AU");
		return status;
	}
	
/*	private LindeMap checkAvailability(TicketInfo ttInfo, User user) throws Exception
	{
		boolean isAvailable = false;
		LindeMap ticketExist=new LindeMap();
		ArrayList ticket=null;
	//	Session session=null;
	//	Transaction txn=null;
		Object row[]=null;
	//	session	= factory.openSession();
	//	txn = session.beginTransaction();
		StringBuffer buffer=new StringBuffer();
		buffer.append("select incidentID,priority,assigneeFullname,region,round(age,2),status,description,casetype,assigneddate,createddate,resolveddate,remarks,application,classification,");
		buffer.append("effort_total,resolution  from TicketInfo where incidentID='"+ttInfo.getIncidentID()+"'");
		System.out.println("select query is "+buffer.toString());
		ticket = (ArrayList)getSession().createQuery(buffer.toString()).list();
			
		if(ticket.size()>0){
			isAvailable = true;
			ticketExist.put("isAvailable", "true");
		}
		else
		{
			ticketExist.put("isAvailable", "false");
		}
		if(isAvailable==true)
		{
			for(int i=0;i<ticket.size();i++)
			{
				 row = (Object[]) ticket.get(i);
				 TicketInfo ticketInfo=new TicketInfo();
				   	ticketInfo.setIndividual((String)row[2]);
		    	   	ticketInfo.setSeverity((String)row[1]);
		    	   	ticketInfo.setStatus((String)row[5]);
		    	   	ticketInfo.setTTNo((String)row[0]);
		    	   	ticketInfo.setAge((String)row[4]);
		    	   	ticketInfo.setRegion((String)row[3]);
		    	   	ticketInfo.setDescription((String)row[6]);
		    	   	ticketInfo.setCasetype((String)row[7]);
		    	   	ticketInfo.setAssigneddate((String)row[8]);
		    	   	ticketInfo.setCreateddate((String)row[9]);
		    	   	ticketInfo.setResolveddate((String)row[10]);
		    	   	ticketInfo.setRemarks((String)row[11]);
		    	   	ticketInfo.setApplication((String)row[12]);
		    	   	ticketInfo.setClassification((String)row[13]);
		    	   	*/
		    	   	
		    	   	/*
		    	   	if(row[14]!=null)
		    	   	{
		    	   		float effort_netmeeting=((Float)row[14]).floatValue();
		    	   		ticketInfo.setEffort_netmeeting(effort_netmeeting);
		    	   	}
		    	   	if(row[15]!=null)
		    	   	{
		    	   		float effort_discussion=((Float)row[15]).floatValue();
		    	   		ticketInfo.setEffort_discussion(effort_discussion);
		    	   	}
		    	   	if(row[16]!=null)
		    	   	{
		    	   		float effort_ktdoc=((Float)row[16]).floatValue();
		    	   		ticketInfo.setEffort_ktdoc(effort_ktdoc);
		    	   	}
		    	   	if(row[17]!=null)
		    	   	{
		    	   		float effort_gsd=((Float)row[17]).floatValue();
		    	   		System.out.println("effort_gsd"+effort_gsd);
		    	   		ticketInfo.setEffort_gsd(effort_gsd);
		        	 }
		    	   	
		    	   	ticketInfo.setWorkaround((String)row[19]);
		    	   	*/
	/*
		    	   	if(row[14]!=null)
		    	   	{
		    	   		float effort_total=((Float)row[14]).floatValue();
		    	   		System.out.println("effort_total"+effort_total);
		    	   		ticketInfo.setEffort_total(effort_total);
		        	}
		    	   	ticketInfo.setResolution((String)row[15]);
		    	   	ticketExist.put("TicketOld", ticketInfo);
			
		    	   	if(ttInfo.getStatus().equals("Assigned")||ttInfo.getStatus().equals("Pending")||ttInfo.getStatus().equals("Work In Progress"))
				{	
				if(ticketInfo.getStatus().equals("Resolved")||ticketInfo.getStatus().equals("Closed"))
				{
					try{
						sendEmail(ttInfo,ticketInfo,"1",user);
					   }
					catch (Exception e) {
					throw e;
					}
				}
				}
			}	
		}
		
		if(ttInfo.getStatus().equals("Assigned"))
		{
			try{
				TicketInfo ticketInfo=new TicketInfo();
				sendEmail(ttInfo,ticketInfo,"2",user);
			   }
			catch (Exception e) {
			throw e;
			}
		}
		
		if(ttInfo.getApplication().equals("OTHERS (GLOBAL)"))
		{
			try{
				TicketInfo ticketInfo=new TicketInfo();
				sendEmail(ttInfo,ticketInfo,"3",user);
			   }
			catch (Exception e) {
				System.out.println("checking duplicates error "+e.getMessage());
			throw e;
			}
		}
		
	   //	txn.commit();
 	  // 	session.close(); 
	   	return ticketExist;
	}
	*/
	private boolean setUploadDate(User user){
		boolean dateUpdated = false;
		//Date uploadDate = new Date();
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 20);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		Date date = c.getTime();
		
		
		lindeLogMgr.logMessage("DEBUG", "Date with time:"+date);
		DataUploadInformation uploadInfo = new DataUploadInformation();
		//Session session = factory.openSession();
	//	Transaction txn = session.beginTransaction();
		/*List result = session.createQuery("from DataUploadInformation").list();
		DateFormat formatter=DateFormat.getDateInstance();
		formatter.format(formatter.LONG);*/
		uploadInfo.setUploadDate(date);
		uploadInfo.setUploader(user.getUserID());
		getSession().save(uploadInfo);
		
		//formatter = new SimpleDateFormat("dd-MMM-yy");
        
		/*for(int i=0;i<result.size();i++)
			uploadInfo = (DataUploadInformation) result.get(i);
		
		uploadDate = uploadInfo.getUploadDate();
		String formattedDate="";*/
		
		
		try{
			//formattedDate = formatter.format(uploadDate);
		
		}
		 catch (Exception e)
		    {lindeLogMgr.logMessage("DEBUG", "Exception :"+e);    }   
		
 	  // 	txn.commit();
 	 //  	session.close();  
		return dateUpdated;
	}
	
	private String getUploadDate(){
		Date uploadDate = new Date();
		DataUploadInformation uploadInfo = new DataUploadInformation();
	//	Session session = factory.openSession();
	//	Transaction txn = session.beginTransaction();
		List result = getSession().createQuery("select max(uploadDate) from DataUploadInformation ").list();
		DateFormat formatter=DateFormat.getDateInstance();
		formatter.format(formatter.LONG);
		
		//formatter = new SimpleDateFormat("dd-MMM-yy");
        
		for(int i=0;i<result.size();i++)
			uploadDate = (Date) result.get(i);
		
		//uploadDate = uploadInfo.getUploadDate();
		String formattedDate="";
		try{
			formattedDate = formatter.format(uploadDate);
		lindeLogMgr.logMessage("DEBUG", "************************************:"+formattedDate);
		}
		 catch (Exception e)
		    {lindeLogMgr.logMessage("DEBUG", "Exception :"+e);    }   
		
 	  // 	txn.commit();
 	  // 	session.close();  
		return formattedDate;
	}
	
	
	public LindeList getTTDetailedInfoBySeverity(String criteria1, String type,String criteria2,User user,String month,String year)
	{

		LindeMap ttinfoMap = new LindeMap();
		LindeList ttinfo=new LindeList();
		lindeLogMgr.logMessage("DEBUG", "Value of tt is");
		
		try{
		
		String tableObjectName="";
		tableObjectName="TicketInfo";
		StringBuffer  SQL_QUERY = new StringBuffer(5000);
		SQL_QUERY.append("SELECT ttinfo.incidentID, ttinfo.title, ttinfo.priority, ttinfo.openTime, ttinfo.status, ttinfo.reopenTime, ttinfo.assigneeFullname, ttinfo.updateTime, ttinfo.closeTime from "+tableObjectName+" ttinfo where priority in ('").append(criteria1).append("')");
		SQL_QUERY.append(" and ttinfo.status in ('Pending Customer','Work In Progress','Assigned')");
		if(!month.equals("ALL"))
		{
		SQL_QUERY.append("and to_date(ttinfo.openTime,'dd/MM/yyyy  HH24:MI:ss') Between trunc(to_date('"+month+"', 'MON-YYYY'),'MON') AND LAST_DAY(TO_DATE('"+month+"', 'MON-YYYY')) ");
		}
		if(user.getRoleId()==1)
		{
			SQL_QUERY.append(" and ttinfo.assigneeFullname in (").append("select UPPER(lastName||', '||firstName) from User where userID='").append(user.getUserID()).append("')");
		}
		else if(user.getRoleId()==2)
		{
			SQL_QUERY.append(" and ttinfo.assigneeFullname in (").append("select UPPER(lastName||', '||firstName) from User where location='").append(user.getLocation()).append("')");
		}
		
		ArrayList query = (ArrayList) getSession().createQuery(SQL_QUERY.toString()).list();

        
		lindeLogMgr.logMessage("DEBUG", "Size of query string"+query.size());
		Object[] row = null;
        for (int i=0;i<query.size();i++) {
        	
            row = (Object[]) query.get(i);
            lindeLogMgr.logMessage("DEBUG", "Value of tt is..."+row[3]);
         TicketInfo ticketinfo=new TicketInfo();
         ticketinfo.setIncidentID((String)row[0]);
         ticketinfo.setTitle((String)row[1]);
         ticketinfo.setPriority((String)row[2]);
         ticketinfo.setOpenTime((String)row[3]);
         ticketinfo.setStatus((String)row[4]);
         ticketinfo.setReopenTime((String)row[5]);
         ticketinfo.setAssigneeFullname((String)row[6]);
         ticketinfo.setUpdateTime((String)row[7]);
         ticketinfo.setCloseTime((String)row[8]);
        
         
         int count=countAge((String)row[3],(String)row[2]);
         String countDays=Integer.toString(count);
         SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
         
         //     java.sql.Date currentDate = new java.sql.Date(new Date().getTime());
                long currentDate =new Date().getTime();
                Date openDate = dateFormat.parse((String)row[3]);
                long differInDays = (new Date().getTime() - openDate.getTime())/(24 * 60 * 60 * 1000);

          ticketinfo.setAging(countDays+"");
         String targetDate="";
         
          if(((String)row[2]).contains("1 - "))
          {
        	  targetDate=calculateTargetDate(((String)row[3]),1);
          }
          else if(((String)row[2]).contains("2 - "))
          {
        	  targetDate=calculateTargetDate(((String)row[3]),3);
          }
          else if(((String)row[2]).contains("3 - "))
          {
        	  targetDate=calculateTargetDate(((String)row[3]),6);
          }
          else if(((String)row[2]).contains("4 - "))
          {
        	  targetDate=calculateTargetDate(((String)row[3]),12);
          }
          
          ticketinfo.setTargetdate(targetDate);
          lindeLogMgr.logMessage("DEBUG","target Date::::::"+targetDate);
         ttinfo.add(ticketinfo);
         lindeLogMgr.logMessage("DEBUG","Detail TTInfo"+i+ticketinfo);
            
        }
        
        
        
		}catch(Exception e)
		{
			lindeLogMgr.logStackTrace("ERROR", "Exception in getTTDetailedInfoBySeverity", e);
		}
		
		
	    	return ttinfo;
	}
	
	
	public LindeList getTTInfo(User user,String uploaddate,String regionselection,String fromDate,String toDate, String criteria,String year)
	{

		LindeList ttinfo=new LindeList();
	//	Session session = factory.openSession();
	//	Transaction txn = session.beginTransaction();
		String tableObjectName="";
		if(year.equals("2008-2009"))
		{
			tableObjectName="TicketInfo20082009";
		} else if(year.equals("2009-2010")){
			tableObjectName="TicketInfo2010";
		}
		else
		{
			tableObjectName="TicketInfo";
		}
		Object[] row;
		String userrole="";
		SimpleDateFormat dateFormat=new SimpleDateFormat("MM/dd/yyyy");
		
	    StringBuffer  SQL_QUERY = new StringBuffer(5000);
	    SQL_QUERY.append("SELECT ttinfo.incidentID,ttinfo.priority,ttinfo.assigneeFullname,ttinfo.region,round(ttinfo.age,2),ttinfo.status");
	    SQL_QUERY.append(" ,substr(ttinfo.description,0,80),ttinfo.casetype,ttinfo.assigneddate,ttinfo.createddate,ttinfo.resolveddate,ttinfo.remarks,ttinfo.application,ttinfo.uploaddate,ttinfo.classification,ttinfo.effort_total,ttinfo.resolution  from "+tableObjectName+" ttinfo ");
	    SQL_QUERY.append(" where ttinfo.incidentID is not null  ");
	    
	    if(uploaddate.equals("ALL")||uploaddate.equals("")){SQL_QUERY.append(" ");}
	    else
	    {
	    	SQL_QUERY.append(" and uploaddate='"+uploaddate+"'");	
	    }
	    
	    if(regionselection.equals("ALL")||regionselection.equals("")){SQL_QUERY.append(" ");}
	    else if(regionselection.equals("REMEDYLOGREPORT"))
	    {
	    	SQL_QUERY.append(" and (ttinfo.application in (select applicationname from Application where region in ('Global','Europe')) or ttinfo.region in ('AUS','SP','NZ') )  ");	
	    	
	    	
	    	/*SQL_QUERY.append(" UNION (SELECT ttinfo.incidentID,ttinfo.priority,ttinfo.assigneeFullname,ttinfo.region,ttinfo.age,ttinfo.status ");
	    	SQL_QUERY.append("  ,ttinfo.description,ttinfo.casetype,ttinfo.assigneddate,ttinfo.createddate,ttinfo.resolveddate,ttinfo.remarks,ttinfo.application,ttage.ageGroup,ttinfo.uploaddate  from TicketInfo ttinfo, TTAge ttage   ");
	    	SQL_QUERY.append("  where ttinfo.age>ttage.rangeStart and ttinfo.age<=ttage.rangeEnd  AND ttinfo.region in ('AUS','SP','NZ')");
	    	if(uploaddate.equals("ALL")||uploaddate.equals("")){SQL_QUERY.append(" ) ");}
		    else
		    {
		    	SQL_QUERY.append(" and uploaddate='"+uploaddate+"')");	
		    }*/
	    }
	    else if(regionselection.equals("SP"))
	    {
	    	SQL_QUERY.append(" and ttinfo.region in ('AUS','SP','NZ') ");	
	    }
	    else
	    {
	    	SQL_QUERY.append(" and ttinfo.application in (select applicationname from Application where region='"+regionselection+"' ) ");	
	    }
	    
	    if(!criteria.equals("")&&criteria!=null){
			if(criteria.equals("crdt")){criteria="createddate";}
			if(criteria.equals("asdt")){criteria="assigneddate";}
			if(criteria.equals("rsdt")){criteria="resolveddate";}
	    }
		if(!fromDate.equals("01/01/1000"))
		{
			
			SQL_QUERY.append(" and to_date(substr("+criteria+",1,instr("+criteria+",' ')),'mm/dd/yyyy')>=to_date('"+fromDate+"','MM/dd/yyyy') ");
			
			
		}
		System.out.println("comparing to date");
		
		if(!toDate.equals("01/01/3000"))
		{
		System.out.println("comparing to date");
			
		SQL_QUERY.append(" and to_date(substr("+criteria+",1,instr("+criteria+",' ')),'mm/dd/yyyy')<=to_date('"+toDate+"','MM/dd/yyyy') ");
		System.out.println("comparing to date");
		}
	    
		if(user.getRoleId()==2 || user.getRoleId().intValue() == 3 || user.getRoleId().intValue() == 4){userrole="admin";}
	   // if(!userrole.equals("admin"))
		//SQL_QUERY.append(" and ttinfo.assigneeFullname='").append(user.getFirstName()).append("' ");

	    SQL_QUERY.append(" order by ttinfo.status ");
	    System.out.println("comparing to date");	   
	    lindeLogMgr.logMessage("DEBUG", "SQL Query is "+SQL_QUERY.toString());
	    System.out.println("SQL Query is "+SQL_QUERY.toString());
	    ArrayList query = (ArrayList)getSession().createQuery(SQL_QUERY.toString()).list();
		   
		for (int i=0;i < query.size(); i++) {
	    
			row = (Object[]) query.get(i);
		    TicketInfo ticketInfo=new TicketInfo();
		    ticketInfo.setIncidentID((String)row[0]);
	   		ticketInfo.setOpenTime((String)row[1]);
	   		ticketInfo.setPriority((String)row[2]);
	   		ticketInfo.setStatus((String)row[3]);
	   		ticketInfo.setAssigneeFullname((String)row[4]);
	   		ticketInfo.setContactFullname((String)row[5]);
	   		//ticketInfo.setProductType((String)row[6]);
	   		ticketInfo.setTitle((String)row[6]);
	   		ticketInfo.setModule((String)row[7]);
	   		
	   		//ticketInfo.setRecipientLocationCode((String)row[8]);
	   		ticketInfo.setUpdateTime((String)row[8]);
	   		//ticketInfo.setReclaimation((String)row[10]);
	   		//ticketInfo.setReassignmentCount((String)row[11]);
	   		ticketInfo.setReopenTime((String)row[9]);
	   		ticketInfo.setAssignmentGroup((String)row[10]);
	   		//ticketInfo.setChangeDate((String)row[10]);
	   		//ticketInfo.setSLABreached((String)row[15]);
	   		ticketInfo.setCloseTime((String)row[11]);
	   		//ticketInfo.setResolveddate((String)row[16]);
	   		ticketInfo.setResolvedBy((String)row[12]);
	   		//ticketInfo.setUploaddate((String)row[19]);
			
			
    	   /*	if(row[15]!=null)
    	   	{
    	   		float effort_netmeeting=((Float)row[15]).floatValue();
    	   		ticketInfo.setEffort_netmeeting(effort_netmeeting);
    	   	}
    	   	if(row[16]!=null)
    	   	{
    	   		float effort_discussion=((Float)row[16]).floatValue();
    	   		ticketInfo.setEffort_discussion(effort_discussion);
    	   	}
    	   	if(row[17]!=null)
    	   	{
    	   		float effort_ktdoc=((Float)row[17]).floatValue();
    	   		ticketInfo.setEffort_ktdoc(effort_ktdoc);
    	   	}
    	   	if(row[18]!=null)
    	   	{
    	   		float effort_gsd=((Float)row[18]).floatValue();
    	   		System.out.println("effort_gsd"+effort_gsd);
    	   		ticketInfo.setEffort_gsd(effort_gsd);
        	 }
    	   	
    	   	
    	   	ticketInfo.setWorkaround((String)row[20]);
    	   	*/
			/*if(row[15]!=null)
    	   	{
    	   		float effort_efforttotal=((Float)row[15]).floatValue();
    	   		ticketInfo.setEffort_total(effort_efforttotal);
        	}
			ticketInfo.setResolution((String)row[16]);*/
    	   	ttinfo.add(ticketInfo);
	    
		}
			//ttinfoMap.put("ttinfoMap", ttinfo); 
	    //	txn.commit();
	    //	session.close();  
	    	return ttinfo;
		
	}
	
	
	
	public LindeList saveTTInfo(LindeList ttList)
	{
	//	Session session = factory.openSession();
	//	Transaction txn = session.beginTransaction();
		LindeList ttinfo=new LindeList();
		Object[] row;
								  	    	   
			   for (int i=0;i < ttList.size(); i++) {
	        	TicketInfo ticketInfo=(TicketInfo)ttList.get(i);
	        	getSession().update(ticketInfo);
				   ttinfo.add(ticketInfo);
			   }
		
		//	txn.commit();
	    //	session.close();  
	    	return ttinfo;
	}
	
	public LindeList getUploadInfo()
	{
		LindeList ttinfo=new LindeList();
		
	//	Session session = factory.openSession();
	//	Transaction txn = session.beginTransaction();
		Object[] row;
		String userrole="";
						   
	    StringBuffer  SQL_QUERY = new StringBuffer("SELECT distinct(uploaddate) from TicketInfo");
	    		   
	   	   lindeLogMgr.logMessage("DEBUG", "SQL Query is "+SQL_QUERY.toString());
	    	   ArrayList query = (ArrayList)getSession().createQuery(SQL_QUERY.toString()).list();
			   
			   for (int i=0;i < query.size(); i++) {
				   LindeMap dateupload=new LindeMap();
				   String uploaddate=(String) query.get(i);
	    		   		TicketInfo ticketInfo=new TicketInfo();
			   // 	   	ticketInfo.setUploaddate(uploaddate);
			    	   dateupload.put("uploadeddate", ticketInfo);
	    		   		ttinfo.add(dateupload);
	    		   	   }
			//ttinfoMap.put("ttinfoMap", ttinfo); 
	    //	txn.commit();
	    //	session.close();  
	    	return ttinfo;
	}
	
	
	public LindeList getAllTTDetailedInfoBySeverity(String criteria1, String type,String year)
	{

		LindeMap ttinfoMap = new LindeMap();
		LindeList ttinfo=new LindeList();
		//Session session = factory.openSession();
		//Transaction txn = session.beginTransaction();
		lindeLogMgr.logMessage("DEBUG", "Before call to  getAllTTDetailedInfoBySeverity query");
		String tableObjectName="";
		if(year.equals("2008-2009"))
		{
			tableObjectName="TicketInfo20082009";
		} else if(year.equals("2009-2010")){
			tableObjectName="TicketInfo2010";
		}
		else
		{
			tableObjectName="TicketInfo";			
		}
		Object[] row;
		String userrole="";
		
		//StringBuffer SQL_QUERY = new StringBuffer("select ttInfo.incidentID,ttInfo.priority,ttInfo.assigneeFullname,ttInfo.region,ttInfo.age,ttInfo.status from TicketInfo ttInfo ");
		StringBuffer SQL_QUERY = new StringBuffer("SELECT incidentID,priority,assigneeFullname,status,title,openTime,closeTime,module,resolvedBy  from "+tableObjectName+" ");
		
		   if(type.equals("priority"))
	    	   {
	    		SQL_QUERY.append(" where priority in ('"+criteria1+"') AND status in ('Pending Customer','Work In Progress','Assigned')");
	    	   }
	    	   SQL_QUERY.append(" order by status");
	    	   
	    	   lindeLogMgr.logMessage("DEBUG", "SQL_QUERY is: "+SQL_QUERY);
	    	   
			   ArrayList query = (ArrayList)getSession().createQuery(SQL_QUERY.toString()).list();
			   lindeLogMgr.logMessage("DEBUG", "QUERY size is: "+query.size());
			   for (int i=0;i < query.size(); i++) {
	    		   row = (Object[]) query.get(i);
	    		   		TicketInfo ticketInfo=new TicketInfo();
	    		   		ticketInfo.setIncidentID((String)row[0]);
	    		   		ticketInfo.setOpenTime((String)row[1]);
	    		   		ticketInfo.setPriority((String)row[2]);
	    		   		ticketInfo.setStatus((String)row[3]);
	    		   		ticketInfo.setAssigneeFullname((String)row[4]);
	    		   		ticketInfo.setContactFullname((String)row[5]);
	    		   		//ticketInfo.setProductType((String)row[6]);
	    		   		ticketInfo.setTitle((String)row[6]);
	    		   		ticketInfo.setModule((String)row[7]);
	    		   		
	    		   		//ticketInfo.setRecipientLocationCode((String)row[8]);
	    		   		ticketInfo.setUpdateTime((String)row[8]);
	    		   		//ticketInfo.setReclaimation((String)row[10]);
	    		   		//ticketInfo.setReassignmentCount((String)row[11]);
	    		   		ticketInfo.setReopenTime((String)row[9]);
	    		   		ticketInfo.setAssignmentGroup((String)row[10]);
	    		   		//ticketInfo.setChangeDate((String)row[10]);
	    		   		//ticketInfo.setSLABreached((String)row[15]);
	    		   		ticketInfo.setCloseTime((String)row[11]);
	    		   		//ticketInfo.setResolveddate((String)row[16]);
	    		   		ticketInfo.setResolvedBy((String)row[12]);
	    		   		
			    	   	
			    	   	/*
			    	   	if(row[14]!=null)
			    	   	{
			    	   		float effort_netmeeting=((Float)row[14]).floatValue();
			    	   		ticketInfo.setEffort_netmeeting(effort_netmeeting);
			    	   	}
			    	   	if(row[15]!=null)
			    	   	{
			    	   		float effort_discussion=((Float)row[15]).floatValue();
			    	   		ticketInfo.setEffort_discussion(effort_discussion);
			    	   	}
			    	   	if(row[16]!=null)
			    	   	{
			    	   		float effort_ktdoc=((Float)row[16]).floatValue();
			    	   		ticketInfo.setEffort_ktdoc(effort_ktdoc);
			    	   	}
			    	   	if(row[17]!=null)
			    	   	{
			    	   		float effort_gsd=((Float)row[17]).floatValue();
			    	   		System.out.println("effort_gsd"+effort_gsd);
			    	   		ticketInfo.setEffort_gsd(effort_gsd);
			        	 }
			    	   	
			    	   	
			    	   	ticketInfo.setWorkaround((String)row[19]);
			    	   	*/
			    	   	/*if(row[14]!=null)
			    	   	{
			    	   		float effort_total=((Float)row[14]).floatValue();
			    	   		System.out.println("effort_total"+effort_total);
			    	   		ticketInfo.setEffort_total(effort_total);
			        	}
			    	   	ticketInfo.setResolution((String)row[15]);*/
			    	   	ttinfo.add(ticketInfo);
	    		   	   }
			//ttinfoMap.put("ttinfoMap", ttinfo); 
	    //	txn.commit();
	    //	session.close();  
	    	return ttinfo;
	}
	
	
	
	
	
	
	
	
	public void sendEmail(TicketInfo newTicketInfo,TicketInfo oldTicketInfo,String category,User sessionUser) throws Exception
	{
		
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
		String tl_cc="";
		String pl_cc="";
		String pm_cc="";
		
		
		ClassLoader loader = ClassLoader.getSystemClassLoader(); 
		InputStream in = EmailDAO.class.getClassLoader().
		getResourceAsStream("Email.properties");
	        if (in != null)
	        {
	           	props.load (in); 
	        	System.out.println("Props file loaded");
	        	lindeLogMgr.logMessage("DEBUG", "Props file loaded");
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
		message.setSubject("Mail Regarding TT Status");
		//System.out.println("mail session is "+message);
		String userName=newTicketInfo.getAssigneeFullname();
		UserDetailDAO userDetailDAO=new UserDetailDAO();
		DeleteUserDAO deleteUserDAO=new DeleteUserDAO();
		User user=userDetailDAO.getUser(userName);
		String mailid=user.getEmailId();
		StringBuffer buffer = new StringBuffer();		
	
		buffer.append("<html><head></head><body>");
		buffer.append("******* This is an auto-generated mail. Please do not reply********<br><br><br>");
		buffer.append("<center><b>Reminder Mail</b></center><br><br>");
		
		if(category.equals("1")){
		buffer.append("This is to inform you about the following ticket which has been previously in "+oldTicketInfo.getStatus()+" has been changed to "+newTicketInfo.getStatus()+" state <br><br>");
		}
		
		else if(category.equals("2")){
		buffer.append("This is to inform you about the following ticket which has been in "+newTicketInfo.getStatus()+" state. Please change the status to Work In Progress/Pending in Remedy <br><br>");
		}
		
		else if(category.equals("3")){
		buffer.append("This is to inform you that the application name in Remedy for the following ticket has been showing as "+newTicketInfo.getModule()+". Please change the application name to appropriate one in Remedy<br><br>");
		}	
		
		buffer.append("<b>Ticket No.&nbsp&nbsp&nbsp&nbsp&nbsp: </b>"+newTicketInfo.getIncidentID()+"<br>");
		buffer.append("<b>Description&nbsp&nbsp&nbsp&nbsp: </b>"+newTicketInfo.getTitle()+"<br>");
		buffer.append("<b>Application&nbsp&nbsp&nbsp&nbsp: </b>"+newTicketInfo.getModule()+"<br>");
		buffer.append("</body></html>");
		
		message.setContent(buffer.toString(), "text/html");
			
		Module application=(Module)deleteUserDAO.getApplicationByName(newTicketInfo.getModule());
		
		User user2=userDetailDAO.getUser(newTicketInfo.getAssigneeFullname());
		
		if(user2.getEmailId()!=null&&!user2.getEmailId().equals("")){
			if(!TO.equals("")){
		
			TO=TO+","+user2.getEmailId();
			}
		else TO=(String)user2.getEmailId();
		}
		
		
		if(!category.equals("3"))
		{
		Set owners=application.getUserid();
		for(Iterator iterator=owners.iterator();iterator.hasNext();)
		{
			User user1=(User)iterator.next();
			if(!TO.equals("")){
			TO=TO+","+user1.getEmailId();
			}
			else TO=(String)user1.getEmailId();
			
		}
		
		
		}
		else
		{
			if(!TO.equals("")){
				TO=TO+","+"BOCApp_Support@Satyam.com";
				}
				else{ 
					TO="BOCApp_Support@Satyam.com";
				}
		}
		//System.out.println("Application Owners is "+TO);
		
		
		LindeList tllist1=userDetailDAO.getUsersByRole(2);
		for(Iterator iterator=tllist1.iterator();iterator.hasNext();)
			{
				User user1=(User)iterator.next();
				if(!Cc.equals("")){
					Cc=Cc+","+user1.getEmailId();
				}
				else Cc=(String)user1.getEmailId();
			 
			}
		if(!Cc.equals("")){
			Cc=Cc+","+sessionUser.getEmailId();
			}
		else Cc=(String)sessionUser.getEmailId();
		
		
				LindeList tllist3=userDetailDAO.getUsersByRole(3);
			for(Iterator iterator=tllist3.iterator();iterator.hasNext();)
			{
				User user1=(User)iterator.next();
				if(!Cc.equals("")){
					Cc=Cc+","+user1.getEmailId();
				}
				else Cc=(String)user1.getEmailId();
				
			}
			//System.out.println("CC is "+Cc);
		
			//System.out.println("mime message is  "+message.getFrom()+"TO IS "+message.getRecipients(Message.RecipientType.TO));
			message.setRecipients(Message.RecipientType.TO,TO);
			message.setRecipients(Message.RecipientType.CC,Cc);
			message.saveChanges();
		tr = mailSession.getTransport("smtp");
		tr.connect(smtp,username,password);
		address = message.getAllRecipients();
		
			
		tr.sendMessage(message, address);
	
	}catch (Exception e) {
		
		System.out.println("exception caught is "+e.getMessage());
		lindeLogMgr.logMessage("ERROR", "exception caught is"+e.getMessage());
		//throw e;
	}
	}
	
	public LindeList getTTDetailedInfoByAge(String criteria1, String type,String criteria2,User user,String month,String year)
	{
		


		LindeMap ttinfoMap = new LindeMap();
		LindeList ttinfo=new LindeList();
	//	Session session = factory.openSession();
	//	Transaction txn = session.beginTransaction();
		lindeLogMgr.logMessage("DEBUG", "Value of tt is");
		
		try{
		
		String tableObjectName="";
		tableObjectName="TicketInfo";
		StringBuffer  SQL_QUERY = new StringBuffer(5000);
		SQL_QUERY.append("select ttInfo.incidentID , ttInfo.title , ttInfo.priority , ttInfo.openTime , ttInfo.status , ttInfo.reopenTime , ttInfo.assigneeFullname , ttInfo.updateTime , ttInfo.closeTime from "+tableObjectName+" ttInfo,TTAge ageGrp where ttInfo.status in ('Pending Customer','Work In Progress','Assigned')");
		SQL_QUERY.append(" AND CURRENT_DATE BETWEEN (to_date(ttInfo.openTime,'dd/MM/yyyy HH24:MI:ss') + ageGrp.rangeStart) and (to_date(ttInfo.openTime,'dd/MM/yyyy HH24:MI:ss') + ageGrp.rangeEnd) and ageGrp.ageGroup in ('").append(""+criteria1+"')");
		if(!month.equals("ALL"))
		{
		SQL_QUERY.append("and to_date(ttInfo.openTime,'dd/MM/yyyy  HH24:MI:ss') Between trunc(to_date('"+month+"', 'MON-YYYY'),'MON') AND LAST_DAY(TO_DATE('"+month+"', 'MON-YYYY')) ");
		}
		
		if(user.getRoleId()==1)
			SQL_QUERY.append("and ttInfo.assigneeFullname in (").append("select UPPER(lastName||', '||firstName) from User where userID ='").append(user.getUserID()).append("')");
		if(user.getRoleId()==2)
			SQL_QUERY.append("and ttInfo.assigneeFullname in (").append("select UPPER(lastName||', '||firstName) from User where location ='").append(user.getLocation()).append("')");
		
		ArrayList query = (ArrayList) getSession().createQuery(SQL_QUERY.toString()).list();

        
		lindeLogMgr.logMessage("DEBUG", "Size of query string"+query.size());
		Object[] row = null;
        for (int i=0;i<query.size();i++) {
        	
            row = (Object[]) query.get(i);
            lindeLogMgr.logMessage("DEBUG", "Value of tt is..."+row[2]);
         TicketInfo ticketinfo=new TicketInfo();
         ticketinfo.setIncidentID((String)row[0]);
         ticketinfo.setTitle((String)row[1]);
         ticketinfo.setPriority((String)row[2]);
         ticketinfo.setOpenTime((String)row[3]);
         ticketinfo.setStatus((String)row[4]);
         ticketinfo.setReopenTime((String)row[5]);
         ticketinfo.setAssigneeFullname((String)row[6]);
         ticketinfo.setUpdateTime((String)row[7]);
         ticketinfo.setCloseTime((String)row[8]);
        
         
         int count=countAge((String)row[3],(String)row[2]);
         String countDays=Integer.toString(count);
         SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
         
         //     java.sql.Date currentDate = new java.sql.Date(new Date().getTime());
                long currentDate =new Date().getTime();
                Date openDate = dateFormat.parse((String)row[3]);
                long differInDays = (new Date().getTime() - openDate.getTime())/(24 * 60 * 60 * 1000);

          ticketinfo.setAging(countDays+"");
         
         ttinfo.add(ticketinfo);
         lindeLogMgr.logMessage("DEBUG","Detail TTInfo"+i+ticketinfo);
            
        }
        
        
        
		}catch(Exception e)
		{
			lindeLogMgr.logStackTrace("ERROR", "Exception in getTTDetailedInfoByAge", e);
		}
		
		
	    	return ttinfo;
	}
	
	public LindeList getTTDetailedInfoByStatus(String criteria1, String type,String criteria2,User user,String month,String year)
	{
		
		LindeMap ttinfoMap = new LindeMap();
		LindeList ttinfo=new LindeList();
		lindeLogMgr.logMessage("DEBUG", "Value of tt in getTTDetailedInfoByStatus");
		
		try{
		
		String tableObjectName="";
		tableObjectName="TicketInfo";
		StringBuffer  SQL_QUERY = new StringBuffer(5000);
		SQL_QUERY.append("select ttInfo.incidentID , ttInfo.title , ttInfo.priority , ttInfo.openTime , ttInfo.status , ttInfo.reopenTime , ttInfo.assigneeFullname , ttInfo.updateTime , ttInfo.closeTime from "+tableObjectName+" ttInfo, OwnerModule owner1, Module module1, User user1 where ttInfo.assigneeFullname in (UPPER(user1.lastName||', '||user1.firstName)) and module1.moduleid = owner1.moduleId and user1.userID=owner1.userId and ttInfo.status='").append(""+criteria1+"' and module1.modulename in ('").append(""+criteria2+"') ");
        
		if(!month.equals("ALL"))
		{
		SQL_QUERY.append("and to_date(ttInfo.openTime,'dd/MM/yyyy  HH24:MI:ss') Between trunc(to_date('"+month+"', 'MON-YYYY'),'MON') AND LAST_DAY(TO_DATE('"+month+"', 'MON-YYYY')) ");
		}
		
		
		if(user.getRoleId()==1){
      	  
            
			SQL_QUERY.append("and user1.userID ='").append(user.getUserID()).append("'");
            
            
		}
		else if(user.getRoleId()==2)
		{
  	
			SQL_QUERY.append("and user1.location='"+user.getLocation()+"'");
			lindeLogMgr.logMessage("DEBUG", "After role not 3 string buffer status HBL query in status in ");
		}
		
		ArrayList query = (ArrayList) getSession().createQuery(SQL_QUERY.toString()).list();

        
		lindeLogMgr.logMessage("DEBUG", "Size of query string"+query.size());
		Object[] row = null;
        for (int i=0;i<query.size();i++) {
        	
            row = (Object[]) query.get(i);
            lindeLogMgr.logMessage("DEBUG", "Value of tt is..."+row[2]);
         TicketInfo ticketinfo=new TicketInfo();
         ticketinfo.setIncidentID((String)row[0]);
         ticketinfo.setTitle((String)row[1]);
         ticketinfo.setPriority((String)row[2]);
         ticketinfo.setOpenTime((String)row[3]);
         ticketinfo.setStatus((String)row[4]);
         ticketinfo.setReopenTime((String)row[5]);
         ticketinfo.setAssigneeFullname((String)row[6]);
         ticketinfo.setUpdateTime((String)row[7]);
         ticketinfo.setCloseTime((String)row[8]);
        
         
         int count=countAge((String)row[3],(String)row[2]);
         String countDays=Integer.toString(count);
         SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
         
         //     java.sql.Date currentDate = new java.sql.Date(new Date().getTime());
                long currentDate =new Date().getTime();
                Date openDate = dateFormat.parse((String)row[3]);
                long differInDays = (new Date().getTime() - openDate.getTime())/(24 * 60 * 60 * 1000);

          ticketinfo.setAging(countDays+"");
         
         ttinfo.add(ticketinfo);
         lindeLogMgr.logMessage("DEBUG","Detail TTInfo"+i+ticketinfo);
            
        }
        
        
        
		}catch(Exception e)
		{
			lindeLogMgr.logStackTrace("ERROR", "Exception in getTTDetailedInfoByAge", e);
		}
		
		
	    	return ttinfo;
	}
	
	public LindeList getTTDetailedInfoModulewiseAge(String criteria1, String type,String criteria2,User user,String month,String year)
	{
		
		LindeMap ttinfoMap = new LindeMap();
		LindeList ttinfo=new LindeList();
		lindeLogMgr.logMessage("DEBUG", "Value of tt is");
		
		try{
		
		String tableObjectName="";
		tableObjectName="TicketInfo";
		StringBuffer  SQL_QUERY = new StringBuffer(5000);
		SQL_QUERY.append("SELECT ttinfo.incidentID, ttinfo.title, ttinfo.priority, ttinfo.openTime, ttinfo.status, ttinfo.reopenTime, ttinfo.assigneeFullname, ttinfo.updateTime, ttinfo.closeTime from "+tableObjectName+" ttinfo, OwnerModule ownermodule1, Module module1, User user1, TTAge ageGrp where ttinfo.status in ('Pending Customer','Work In Progress','Assigned','Closed') AND CURRENT_DATE BETWEEN (to_date(ttinfo.openTime,'dd/MM/yyyy HH24:MI:ss') + ageGrp.rangeStart) and (to_date(ttinfo.openTime,'dd/MM/yyyy HH24:MI:ss') + ageGrp.rangeEnd) and ttinfo.assigneeFullname in (UPPER(user1.lastName||', '||user1.firstName)) and module1.moduleid=ownermodule1.moduleId and user1.userID=ownermodule1.userId and ageGrp.ageGroup in ('"+criteria1+"') and module1.modulename in ('"+criteria2+"')");
		if(!month.equals("ALL"))
			{
			SQL_QUERY.append("and to_date(ttinfo.openTime,'dd/MM/yyyy  HH24:MI:ss') Between trunc(to_date('"+month+"', 'MON-YYYY'),'MON') AND LAST_DAY(TO_DATE('"+month+"', 'MON-YYYY')) ");
			}
		
		if(user.getRoleId()==1)
			SQL_QUERY.append(" and user1.userID='"+user.getUserID()+"'");
        else if(user.getRoleId()==2)
        	SQL_QUERY.append(" and user1.location='"+user.getLocation()+"'");
		
		ArrayList query = (ArrayList) getSession().createQuery(SQL_QUERY.toString()).list();

        
		lindeLogMgr.logMessage("DEBUG", "Size of query string"+query.size());
		Object[] row = null;
        for (int i=0;i<query.size();i++) {
        	
            row = (Object[]) query.get(i);
            lindeLogMgr.logMessage("DEBUG", "Value of tt is..."+row[2]);
         TicketInfo ticketinfo=new TicketInfo();
         ticketinfo.setIncidentID((String)row[0]);
         ticketinfo.setTitle((String)row[1]);
         ticketinfo.setPriority((String)row[2]);
         ticketinfo.setOpenTime((String)row[3]);
         ticketinfo.setStatus((String)row[4]);
         ticketinfo.setReopenTime((String)row[5]);
         ticketinfo.setAssigneeFullname((String)row[6]);
         ticketinfo.setUpdateTime((String)row[7]);
         ticketinfo.setCloseTime((String)row[8]);
        
         
         int count=countAge((String)row[3],(String)row[2]);
         String countDays=Integer.toString(count);
         SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
         
         //     java.sql.Date currentDate = new java.sql.Date(new Date().getTime());
                long currentDate =new Date().getTime();
                Date openDate = dateFormat.parse((String)row[3]);
                long differInDays = (new Date().getTime() - openDate.getTime())/(24 * 60 * 60 * 1000);

          ticketinfo.setAging(countDays+"");
         
         ttinfo.add(ticketinfo);
         lindeLogMgr.logMessage("DEBUG","Detail TTInfo"+i+ticketinfo);
            
        }
        
        
        
		}catch(Exception e)
		{
			lindeLogMgr.logStackTrace("ERROR", "Exception in getTTDetailedInfoBySeverity", e);
		}
		
	    	return ttinfo;
	}
	
	
public int countAge(String startDate,String priority)throws ParseException
{
	 Date currDate=new Date();
     
     Format formatter = new SimpleDateFormat("dd/MM/yyyy");
     String currnDate = formatter.format(currDate);
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    Date date1 = df.parse(startDate);
    
    Date date2 = df.parse(currnDate);
    
    Calendar cal1 = Calendar.getInstance();
    Calendar cal2 = Calendar.getInstance();
    cal1.setTime(date1);
    cal2.setTime(date2);

    int numberOfDays = 0;
    int dayscount=0;
    if(priority.contains("1 - "))
    {
    	
    }
    else if(priority.contains("2 - "))
    {
    	
    }
    
    else if(priority.contains("3 - "))
    {
    	
    }
    else if(priority.contains("4 - "))
    {
    	
    }
    while (cal1.before(cal2)) {
        if ((Calendar.SATURDAY != cal1.get(Calendar.DAY_OF_WEEK))
                &&(Calendar.SUNDAY != cal1.get(Calendar.DAY_OF_WEEK))) {
            numberOfDays++;
        }
        cal1.add(Calendar.DATE,1);
    
    }
    System.out.println(numberOfDays);
	return numberOfDays;
}

public String calculateTargetDate(String startDate,int noOfDays) throws ParseException
{
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    Date date1 = df.parse(startDate);
    
    lindeLogMgr.logMessage("DEBUG","Inside While method::::"+date1.toString());

    Calendar cal1 = Calendar.getInstance();
    cal1.setTime(date1);

    

    int numberOfDays = 0;
           
    for (int i=1 ; i<=noOfDays; i++) {
              
               if ((Calendar.SATURDAY != cal1.get(Calendar.DAY_OF_WEEK))
                &&(Calendar.SUNDAY != cal1.get(Calendar.DAY_OF_WEEK))) 
               {
                      cal1.add(Calendar.DATE,1);
        }
               else
               {
                     noOfDays+=1;
                      cal1.add(Calendar.DATE,1);
               }
        
        lindeLogMgr.logMessage("DEBUG","Calendar.cal1.getTime()::::"+cal1.getTime());
    }
    String targetDate = df.format(cal1.getTime());
    return targetDate;
}

public List<TicketInfo> getIncidentData(User userInfo,String fromDate,String toDate,String moduleNAme)
{
	
	lindeLogMgr.logMessage("DEBUG", "getIncidentData");
	
	List incidentList = new ArrayList(); 
	List<TicketInfo> incidentDataList = new ArrayList<TicketInfo>(); 
	//Session session = factory.openSession();
	//Transaction txn = session.beginTransaction();
	lindeLogMgr.logMessage("DEBUG", "Before call to  getTTStatus query");
	//Query queryObj = session.getNamedQuery("checkUser");
	
	String tableObjectName="TicketInfo"; 
	 
    LindeMap innerMap = new LindeMap();
    
    //String tableBeanName = "TicketInfo";
    try{
    	
         // StringBuffer SQL_QUERY = new StringBuffer(" select replace(ttinfo.status,'Work In Progress','WIP'),app.region,count(*) from "+tableObjectName+" ttinfo,Application app where ttinfo.application=app.applicationname ");
        //  StringBuffer statusSqlQuery = new StringBuffer("SELECT ttinfo.incidentID, ttinfo.title, ttinfo.priority, ttinfo.openTime, ttinfo.status, ttinfo.reopenTime, ttinfo.assigneeFullname, ttinfo.updateTime, ttinfo.closeTime,module1.modulename from "+tableObjectName+" ttinfo, OwnerModule owner1, Module module1, User user1 where ");
    	 StringBuffer statusSqlQuery = new StringBuffer(" from "+tableObjectName+" ttinfo, OwnerModule owner1, Module module1, User user1 where ");
          statusSqlQuery.append("  to_date(ttinfo.openTime,'dd/MM/yyyy HH24:MI:ss') BETWEEN to_date('");
          statusSqlQuery.append(fromDate).append("','yyyy-MM-dd HH24:MI:ss') and to_date('").append(toDate).append("','yyyy-MM-dd HH24:MI:ss')");
          statusSqlQuery.append(" and module1.modulename='").append(moduleNAme).append("'");
         
          
          if(userInfo.getRoleId()==1){
        	  
         
                        statusSqlQuery.append("and user1.userID ='").append(userInfo.getUserID()).append("'");
                        
                        
          }
          else if(userInfo.getRoleId()==2)
          {
        	  /*statusSqlQuery.append("and user1.userID ='").append(userInfo.getUserID()).append("'");*/
        	  statusSqlQuery.append("and user1.location='"+userInfo.getLocation()+"'");
        	  lindeLogMgr.logMessage("DEBUG", "After role not 3 string buffer status HBL query in status");
        	  
          }
          statusSqlQuery.append(" and module1.moduleid = owner1.moduleId and user1.userID=owner1.userId") ;
        //  statusSqlQuery.append(" group by info1.status, module1.modulename order by info1.status");
          lindeLogMgr.logMessage("INFO", "user.getRoleId() : StatusSqlQuery.toString() : "+statusSqlQuery.toString());
          incidentList = getSession().createQuery(statusSqlQuery.toString()).list();
               for (int i = 0; i < incidentList.size(); i++) {
        	  Object[] Obj = (Object[])incidentList.get(i);
        	  TicketInfo ttinofObj = (TicketInfo)Obj[0];
        	  lindeLogMgr.logMessage("DEBUG", "ttinofObjgetIncidentID :"+ttinofObj.getIncidentID()); 
        	  Module moduleObj = (Module)Obj[2];
        	  lindeLogMgr.logMessage("DEBUG", "moduleObj.getModulename() () :"+moduleObj.getModulename()); 
        	  ttinofObj.setModule(moduleObj.getModulename());
        	  incidentDataList.add(ttinofObj);
		}
         
   /*   for (int i = 0; i < incidentList.size(); i++) {
          	
        	  Object[]  row = (Object[]) incidentList.get(i);
         //    lindeLogMgr.logMessage("DEBUG", "Value of tt is..."+row[0]+ " row[1] "+row[1]+" row[2] "+row[2]+" row[3] "+row[3]);
           TicketInfo ticketinfo=new TicketInfo();
           ticketinfo=(TicketInfo)row[0];
           TicketInfo tempticketinfo=new TicketInfo();
           tempticketinfo=ticketinfo;
        //   Module moduleObj = (Module)row[2];
         //  tempticketinfo.setModule(moduleObj.getModulename());
       /*    ticketinfo.setIncidentID((String)row[0]);
           ticketinfo.setTitle((String)row[1]);
           ticketinfo.setPriority((String)row[2]);
           ticketinfo.setOpenTime((String)row[3]);
           ticketinfo.setStatus((String)row[4]);
           ticketinfo.setReopenTime((String)row[5]);
           ticketinfo.setAssigneeFullname((String)row[6]);
           ticketinfo.setUpdateTime((String)row[7]);
           ticketinfo.setCloseTime((String)row[8]);
           ticketinfo.setModule((String)row[9]);
          
           
           int count=countAge((String)row[3],(String)row[2]);
           String countDays=Integer.toString(count);
           SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
           
           //     java.sql.Date currentDate = new java.sql.Date(new Date().getTime());
                  long currentDate =new Date().getTime();
                  Date openDate = dateFormat.parse((String)row[3]);
                  long differInDays = (new Date().getTime() - openDate.getTime())/(24 * 60 * 60 * 1000);

            ticketinfo.setAging(countDays+"");*/
           
        //    incidentList.add(tempticketinfo);
        //   lindeLogMgr.logMessage("DEBUG","Detail TTInfo"+i+ticketinfo);
              
          
	//	}
          
          
             

  }catch(Exception e){
        lindeLogMgr.logStackTrace("ERROR", "SQLException in getCRStatus : " +e.getMessage(),e);
  }


	
    	return incidentDataList;	   

}


}
