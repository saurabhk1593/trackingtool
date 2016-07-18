package com.techm.trackingtool.admin.dao;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.techm.trackingtool.admin.bean.CRinfo;
import com.techm.trackingtool.admin.bean.Module;
import com.techm.trackingtool.admin.bean.User;
import com.techm.trackingtool.admin.vo.CrSummaryVO;
import com.techm.trackingtool.util.HashMap;
import com.techm.trackingtool.util.LindeList;
import com.techm.trackingtool.util.LindeMap;
import com.techm.trackingtool.util.LindeToolLogManager;
import com.techm.trackingtool.util.TrackingToolConstant;


@Repository("crinfoDAO")
public class CRInfoDAO {
	
	
	private static final LindeToolLogManager lindeLogMgr = new LindeToolLogManager(CRInfoDAO.class.getName());
	
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
	
	public String uploadInfo(List crList,User user) throws Exception,SQLException
	{
		String status = "failed";
		lindeLogMgr.logMessage("DEBUG", "ticket list size"+crList.size());
		Date sysDate = new Date();
		DateFormat formatter=DateFormat.getDateInstance();
		formatter.format(formatter.LONG);
		String currentDate = formatter.format(sysDate);
		try{
		for(int i=0;i<crList.size();i++)
		{
			CRinfo crInfo = (CRinfo)crList.get(i);
			lindeLogMgr.logMessage("DEBUG", "crInfo.getChangeId() "+crInfo.getAffectedCIs());
			
			String affectedCI = crInfo.getAffectedCIs();
			String uppercaseAffetctedCi = affectedCI.toUpperCase();
			String[] affetctedCI = uppercaseAffetctedCi.split("\\s*\\r?\\n\\s*");
			 
			boolean checkBoolean=false;
			lindeLogMgr.logMessage("DEBUG", "uppercaseAffetctedCi  ::"+uppercaseAffetctedCi);
			
				for (int ii=0; ii< affetctedCI.length;ii++) {
					String str = affetctedCI[ii];
					if(str.indexOf("_CHGTEAM")!=-1)
					{
						String moduleStr =str.substring(0, str.indexOf("_CHGTEAM"));
					String moduleName = moduleStr.substring( moduleStr.lastIndexOf("_")+1,moduleStr.length());
					if(moduleName.equalsIgnoreCase("DEV"))
						crInfo.setAffectedCIs("ABAP");
					else
						crInfo.setAffectedCIs(moduleName);
					
					lindeLogMgr.logMessage("DEBUG", "moduleName is ::: ----- "+moduleName);
					checkBoolean=true;
					}
					if(checkBoolean)
					 break;
				}
				
			if(!checkBoolean)
			{
				//List moduleList = getSession().createCriteria(Module.class).setFetchMode("OwnerModule", FetchMode.JOIN).setFetchMode("User", FetchMode.JOIN).add(Restrictions.eq("lastName", crInfo.getAssignee().substring(0, crInfo.getAssignee().lastIndexOf(","))).ignoreCase()).list();
				
				//List moduleList = getSession().createCriteria(Module.class,"module").createAlias("module.moduleid", "OwnerModule").createAlias("OwnerModule.userID", "User").add(Restrictions.eq("User.lastName", crInfo.getAssignee().substring(0, crInfo.getAssignee().lastIndexOf(","))).ignoreCase()).list();
//						setFetchMode("OwnerModule", FetchMode.JOIN).setFetchMode("User", FetchMode.JOIN).add(Restrictions.eq("lastName", crInfo.getAssignee().substring(0, crInfo.getAssignee().lastIndexOf(","))).ignoreCase()).list();
						
				StringBuilder sqlBuilder = new StringBuilder();
				sqlBuilder.append("from Module module,OwnerModule ownerMod,User user where module.moduleid=ownerMod.moduleId and ownerMod.userId = user.userID");
				sqlBuilder.append(" and upper(user.lastName||', '||user.firstName)='").append(crInfo.getAssignee()).append("'");
				List moduleList = (List)getSession().createQuery(sqlBuilder.toString()).list();
			//	lindeLogMgr.logMessage("DEBUG", "moduleList "+moduleList.size());

				for (Object object : moduleList) {
					
					Object[] str1 = (Object[])object;
					lindeLogMgr.logMessage("DEBUG", "str1[0] : "+str1[0]);
				//	lindeLogMgr.logMessage("DEBUG", "str1[0] : "+str1[1]);
				//	lindeLogMgr.logMessage("DEBUG", "str1[0] : "+str1[2]);
					Module module = (Module)str1[0];
					lindeLogMgr.logMessage("DEBUG", "Module Name is :: else ] : "+module.getModulename());
					crInfo.setAffectedCIs(module.getModulename());
				}
				
			}
			
			getSession().saveOrUpdate(crInfo);
		}
		}
		
		catch (Exception e) {
			lindeLogMgr.logStackTrace("ERROR","Error in uploading is: "+e.getMessage(),e);
		}
		status="true";
		return status;
	}
	
	public CrSummaryVO getCRDetails(User user,String selectedMonth){
		
		lindeLogMgr.logMessage("INFO", "getCRDetails()");
		CrSummaryVO crSummaryVO = new CrSummaryVO();
		LindeMap priorityMap = getCRPriorityInfo(user,selectedMonth);
		LindeMap statusMap = getCRStatus(user,selectedMonth);	
		LindeMap byAgeMap = getCRInfoByAge(user,selectedMonth);
		LindeMap byAgeRegionWiseMap = getModuleCRInfoByAge(user,selectedMonth);
		
		String infoUploadDate = getUploadDate();
		crSummaryVO.setByPriority(priorityMap);
		crSummaryVO.setByStatus(statusMap);
		crSummaryVO.setByAge(byAgeMap);
		crSummaryVO.setByAgeModuleWise(byAgeRegionWiseMap);
		crSummaryVO.setUploadDate(infoUploadDate);	
		
		return crSummaryVO;
	}
	
    private LindeMap getCRPriorityInfo(User user,String selectedMonth){
		
		LindeMap priorityMap = new LindeMap();
		String tableBeanName = "CRinfo";
		try {
		    StringBuffer prioritySqlQuery = new StringBuffer("select replace(crinfo.priority,' ',''),count(*) from "+tableBeanName+" crinfo where crinfo.phase in ('Accept RfC & Prepare Approval','CI: Validate & Assign','Validate & Assign','Approval','Implementation','Post Implementation Review','Correct Implementation')");
			
		    if(selectedMonth != null && !selectedMonth.equalsIgnoreCase("ALL"))
		    {
		    	prioritySqlQuery.append(" and to_date(crinfo.openDate,'dd/MM/yyyy HH24:MI:ss') BETWEEN trunc(to_date('");
		    	prioritySqlQuery.append(selectedMonth).append("','MON-yyyy HH24:MI:ss'),'MON') and LAST_DAY(to_date('").append(selectedMonth).append("','MON-yyyy HH24:MI:ss'))");
		    }
		    
		    
		    if(user.getRoleId()==1)
		    	prioritySqlQuery.append("and crinfo.assignee ='").append(user.getLastName().toUpperCase()).append(", ").append(user.getFirstName().toUpperCase()).append("'");
			
			if(user.getRoleId()==2)
				prioritySqlQuery.append("and crinfo.assignee in (select UPPER(users1.lastName)||', '||UPPER(users1.firstName) from User users1 where users1.location ='").append(user.getLocation()).append("')");
			
			if(user.getRoleId()!=3)
			{
			  prioritySqlQuery.append(" group by priority  ");
			  LindeMap lResultMap = getCRPriorityDetails(user,prioritySqlQuery.toString(),user.getLocation());
			  priorityMap.put("OffShore", lResultMap);
			  priorityMap.put("Location", user.getLocation());
			}
			
			if(user.getRoleId()==3)
			{
				lindeLogMgr.logMessage("INFO", "user.getRoleId() :: "+user.getRoleId());
				String subPrgQueryStr = prioritySqlQuery.toString();
				
				for(int i=1;i<=2;i++)
				{
					
					String location=null;
					if(i==1)
					{
						location="OffShore";
						prioritySqlQuery.append("and crinfo.assignee in (select UPPER(users1.lastName)||', '||UPPER(users1.firstName) from User users1 where users1.location ='Offshore')");
					}
					else if(i==2)
					{
						prioritySqlQuery.append(subPrgQueryStr);
						location="OnShore";
						 prioritySqlQuery.append("and crinfo.assignee in (select UPPER(users1.lastName)||', '||UPPER(users1.firstName) from User users1 where users1.location ='Onsite')");
					}
					
					
					prioritySqlQuery.append(" group by priority  ");
					lindeLogMgr.logMessage("INFO", "user.getRoleId() : prioritySqlQuery.toString() : "+prioritySqlQuery.toString());
					
					LindeMap lResultMap = getCRPriorityDetails(user,prioritySqlQuery.toString(),location);
					priorityMap.put(location, lResultMap);
					priorityMap.put("Location", location);
					
					prioritySqlQuery.delete(0, prioritySqlQuery.toString().length());
					
				}
				
			}
			
			
    	   

		} catch(Exception e){
			lindeLogMgr.logStackTrace("ERROR", "SQLException in getCRPriorityInfo" +e.getMessage(),e);			
		}
		
		return priorityMap;
	}

     private LindeMap getCRPriorityDetails(User user,String queryString,String location)
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
    		 lindeLogMgr.logStackTrace("ERROR", "SQLException in getCRPriorityDetails" +e.getMessage(),e);	
    	 }
	    	   
	    	   return lPriorityMap;
     }
	
    
	
	
     
     private LindeMap getCRStatus(User user,String selectedMonth){
 		
 		LindeMap statusMap = new LindeMap(); 
 		LindeMap innerMap = new LindeMap();
 		
 		 
 		
 		String tableBeanName = "CRinfo";
 		try{
 		     // StringBuffer statusSqlQuery = new StringBuffer("select crinfo.phase,module.modulename,count(*) from "+tableBeanName+" crinfo,OwnerModule owner,Module module,User user where ");
 			
 			
 		     // statusSqlQuery.append("crinfo.assignee in (user.firstName||','||user.lastName) and module.moduleid=owner.moduleId and user.userID=owner.userId");
 		   
 		      //commented by Prasad on 6/10/2016
 		      /*   if(user.getRoleId()==1)
 					statusSqlQuery.append(" crinfo.assignee ='").append(user.getLastName().toUpperCase()).append(", ").append(user.getFirstName().toUpperCase()).append("'").append("and user.userID='").append(user.getUserID()).append("'");
 		      
 		      if(user.getRoleId()==2)
 		    	  statusSqlQuery.append(" crinfo.assignee in (UPPER(user.lastName||', '||user.firstName)) and user.location ='").append(user.getLocation()).append("'");

 		      if(user.getRoleId()!=3)
 			  {
 		    	 statusSqlQuery.append(" and module.moduleid = owner.moduleId and user.userID = owner.userId");
 	 			 statusSqlQuery.append(" group by crinfo.phase , module.modulename order by crinfo.phase");
 	 			 
 			    LindeMap lResultMap = getCRStatusDetails(user,statusSqlQuery.toString(),user.getLocation());
 			    statusMap.put("OffShore", lResultMap);
 			    statusMap.put("Location", user.getLocation());
 			  } */
 		   //commented by Prasad on 6/10/2016
 		      
 			 StringBuffer statusSqlQuery = new StringBuffer("select crinfo.phase,crinfo.affectedCIs,count(*) from "+tableBeanName+" crinfo ");
 		     if(user.getRoleId()==1)
					statusSqlQuery.append(" where crinfo.assignee ='").append(user.getLastName().toUpperCase()).append(", ").append(user.getFirstName().toUpperCase()).append("'");
		      
		      if(user.getRoleId()==2)
		    	  statusSqlQuery.append(" , User user where crinfo.assignee in (UPPER(user.lastName||', '||user.firstName)) and user.location ='").append(user.getLocation()).append("'");

		      if(user.getRoleId()!=3)
			  {
		    	// statusSqlQuery.append(" and module.moduleid = owner.moduleId and user.userID = owner.userId");
		    	  if(selectedMonth != null && !selectedMonth.equalsIgnoreCase("ALL"))
				    {
		    		  statusSqlQuery.append(" and to_date(crinfo.openDate,'dd/MM/yyyy HH24:MI:ss') BETWEEN trunc(to_date('");
		    		  statusSqlQuery.append(selectedMonth).append("','MON-yyyy HH24:MI:ss'),'MON') and LAST_DAY(to_date('").append(selectedMonth).append("','MON-yyyy HH24:MI:ss'))");
				    }
		    	  
	 			 statusSqlQuery.append(" group by crinfo.phase , crinfo.affectedCIs order by crinfo.phase");
	 			 
			    LindeMap lResultMap = getCRStatusDetails(user,statusSqlQuery.toString(),user.getLocation());
			    statusMap.put("OffShore", lResultMap);
			    statusMap.put("Location", user.getLocation());
			  }
 		      
 		     if(user.getRoleId()==3)
 			{
 				lindeLogMgr.logMessage("INFO", "user.getRoleId() :: "+user.getRoleId());
 				String subPrgQueryStr = statusSqlQuery.toString();
 				for(int i=1;i<=2;i++)
 				{
 					
 					String location=null;
 					if(i==1)
 					{
 						location="OffShore";
 						statusSqlQuery.append(" ,User user where crinfo.assignee in (UPPER(user.lastName||', '||user.firstName)) and user.location ='").append("Offshore").append("'");
 					}
 					else if(i==2)
 					{
 						statusSqlQuery.append(subPrgQueryStr);
 						location="OnShore";
 						statusSqlQuery.append(" ,User user where crinfo.assignee in (UPPER(user.lastName||', '||user.firstName)) and user.location ='").append("Onsite").append("'");
 					}
 					
 				//	statusSqlQuery.append(" and module.moduleid = owner.moduleId and user.userID = owner.userId");
 					if(selectedMonth != null && !selectedMonth.equalsIgnoreCase("ALL"))
				    {
		    		  statusSqlQuery.append(" and to_date(crinfo.openDate,'dd/MM/yyyy HH24:MI:ss') BETWEEN trunc(to_date('");
		    		  statusSqlQuery.append(selectedMonth).append("','MON-yyyy HH24:MI:ss'),'MON') and LAST_DAY(to_date('").append(selectedMonth).append("','MON-yyyy HH24:MI:ss'))");
				    }
 					
 	 	 			statusSqlQuery.append(" group by crinfo.phase , crinfo.affectedCIs order by crinfo.phase");
 					
 					lindeLogMgr.logMessage("INFO", "user.getRoleId() : statusSqlQuery.toString() : "+statusSqlQuery.toString());
 					
 					LindeMap lResultMap = getCRStatusDetails(user,statusSqlQuery.toString(),location);
 					statusMap.put(location, lResultMap);
 					statusMap.put("Location", location);
 					
 					statusSqlQuery.delete(0, statusSqlQuery.toString().length());
 					
 				}
 				
 			}
 			  
 		    
 		   	   lindeLogMgr.logMessage("DEBUG", "statusMap Map size:"+statusMap.size());

 			}catch(Exception e){
 				lindeLogMgr.logStackTrace("ERROR", "SQLException in getCRStatus : " +e.getMessage(),e);
 			}
 			
 		    	return statusMap;	
 		
 		}
     
     private LindeMap getCRStatusDetails(User user,String queryString,String location)
     {
    	 LindeMap lInnerMap = new LindeMap();
    	 LindeMap lStatusMap = new LindeMap();
    	 ArrayList query = (ArrayList) getSession().createQuery(queryString).list();
   	     Object[] temp = new Object[1];
   	     Object[] row;
   	   
   	   
   	     boolean isRowInserted =false;		    	   
   	     long groupSum=0,total=0;
   	     long tempothers=0;

   	      
		for (int i=0;i<query.size();i++) {
	    	   row = (Object[]) query.get(i);
	    	   if(row[0].equals("Accept RfC & Prepare Approval")) 
	    	    {
	    		   row[0]="AccRfcAndPrepApp";
	    	    }
	    	   else if(row[0].equals("CI: Validate & Assign")) 
	    	    {
	    		   row[0]="CIValidAndAssign";
	    	    }
	    	   else if(row[0].equals("Validate & Assign")) 
	    	    {
	    		   row[0]="ValidAndAssign";
	    	    }
	    	   else if(row[0].equals("Post Implementation Review")) 
	    	    {
	    		   row[0]="PostImpReview";
	    	    }
	    	   else if(row[0].equals("Correct Implementation")) 
	    	    {
	    		   row[0]="CorrectImp";
	    	    }
	    	   lindeLogMgr.logMessage("DEBUG", "Values++++++++++++:::"+row[0]+"  "+row[1]+" "+row[2]);
	    	   
	    	   
			if (i!=0){
	    		   if(!row[0].toString().equals(temp[0].toString()))
	    		   {
	    			   lInnerMap = new LindeMap();
	    			   lindeLogMgr.logMessage("DEBUG", "Resetting Tempother value : ");
	    			   groupSum=0;
	    			   lindeLogMgr.logMessage("DEBUG", "Different status:"+row[0]+":temp:"+temp[0]);
	    		   }
	    	   }
	    	   groupSum +=(Long)row[2];
	    	   total+=(Long)row[2];

	    	   lInnerMap.put(row[1], row[2]);
	    	   lInnerMap.put("Total", groupSum);
	    	   
	    	   if(i==0 || !row[0].toString().equals(temp[0].toString()))
	    		   lStatusMap.put(row[0], lInnerMap);
	    	   		
	    	   		temp[0] = row[0];
   	   }
		lStatusMap.put("Total", total);
		
		if(user.getLocation()!=null && user.getRoleId() !=3)
		  lStatusMap.put("Location", user.getLocation());
		else
			 lStatusMap.put("Location", location);
   	  
   	  
   	  if(lStatusMap.containsKey("CIValidAndAssign"))
   	  {
   		  LindeMap ciValMap = (LindeMap)lStatusMap.get("CIValidAndAssign");
   		  LindeMap valMap = (LindeMap)lStatusMap.get("ValidAndAssign");
   		  
   		  if(ciValMap != null && !ciValMap.isEmpty())
   		  {
   			  if(valMap != null && !valMap.isEmpty())
   			  {
   				  Set keySet = ciValMap.keySet();
   				  Iterator<String> itr = keySet.iterator();
   				  while(itr.hasNext())
   				  {
   					String key = (String)itr.next();
   					long keyValue = (Long)ciValMap.get(key);
   		 			if(valMap.containsKey(key))
						{  
							long lValue = (Long)valMap.get(key);
							 lValue+=keyValue;
							 valMap.put(key, lValue);		    					  
						} 
   				  }
   				lStatusMap.put("ValidAndAssign", valMap);
   			  }
   			  else
   			  {
   				lStatusMap.put("ValidAndAssign", ciValMap);
   			  }
   		  }
   	  }
   	  if(lStatusMap.containsKey("CorrectImp"))
   	  {
   		  LindeMap correctImpMap = (LindeMap)lStatusMap.get("CorrectImp");
   		  LindeMap postImpMap = (LindeMap)lStatusMap.get("PostImpReview");
   		  
   		  if(correctImpMap !=null && !correctImpMap.isEmpty())
   		  {
   			  if(postImpMap !=null && !postImpMap.isEmpty())
   			  {
   				  Set keySet = correctImpMap.keySet();
   				  Iterator<String> itr = keySet.iterator();
   				  while(itr.hasNext())
   				  {
   					String key = (String)itr.next();
   					long keyValue = (Long)correctImpMap.get(key);
   		 			if(postImpMap.containsKey(key))
						{  
							long lValue = (Long)postImpMap.get(key);
							 lValue+=keyValue;
							 postImpMap.put(key, lValue);		    					  
						} 
   				  }
   				lStatusMap.put("PostImpReview", postImpMap);
   			  }
   			  else
   			  {
   				lStatusMap.put("PostImpReview", correctImpMap);
   			  }
   		  }
   	  }
   	  return lStatusMap;
     }
	
	
     
     private LindeMap getCRInfoByAge(User user,String selectedMonth){
 		
 		LindeMap crbyAgeMap = new LindeMap();
 		String tableBeanName = "CRinfo";
 		try {
 		    StringBuffer crAgeSqlQuery = new StringBuffer("select ageGrp.ageGroup,count(*) from "+tableBeanName+" crinfo,CRAge ageGrp where crinfo.phase in ('Accept RfC & Prepare Approval','CI: Validate & Assign','Validate & Assign','Approval','Implementation','Post Implementation Review','Correct Implementation')");
 		    
 		   if(selectedMonth != null && !selectedMonth.equalsIgnoreCase("ALL"))
		    {
 			  crAgeSqlQuery.append(" and to_date(crinfo.openDate,'dd/MM/yyyy HH24:MI:ss') BETWEEN trunc(to_date('");
 			  crAgeSqlQuery.append(selectedMonth).append("','MON-yyyy HH24:MI:ss'),'MON') and LAST_DAY(to_date('").append(selectedMonth).append("','MON-yyyy HH24:MI:ss'))");
		    }
 		    
 		    crAgeSqlQuery.append(" AND CURRENT_DATE BETWEEN (to_date(crinfo.openDate,'dd/MM/yyyy HH24:MI:ss') + ageGrp.rangeStart) and (to_date(crinfo.openDate,'dd/MM/yyyy HH24:MI:ss') + ageGrp.rangeEnd) ");
 		   
 		    //crAgeSqlQuery.append(" AND (SYSDATE - (to_date(crinfo.openDate,'dd/MM/yyyy HH24:MI:ss'))) >= ageGrp.rangeStart and (SYSDATE - (to_date(crinfo.openDate,'dd/MM/yyyy HH24:MI:ss'))) <= ageGrp.rangeEnd ");
 		    
 			if(user.getRoleId()==1)
 				crAgeSqlQuery.append(" AND crinfo.assignee ='").append(user.getLastName().toUpperCase()).append(", ").append(user.getFirstName().toUpperCase()).append("'");
 			
 			if(user.getRoleId()==2)
 				crAgeSqlQuery.append("and crinfo.assignee in (select UPPER(users1.lastName||', '||users1.firstName) from User users1 where users1.location ='").append(user.getLocation()).append("')");
 				//crAgeSqlQuery.append(" crinfo.assignee in (user.firstName||','||user.lastName) and user.location ='").append(user.getLocation()).append("'");
 				
 			if(user.getRoleId()!=3)
			{
 				crAgeSqlQuery.append(" group by ageGrp.ageGroup  ");
			    LindeMap lResultMap = getCRInfoByAgeDetails(user,crAgeSqlQuery.toString(),user.getLocation());
			    crbyAgeMap.put("OffShore", lResultMap);
			    crbyAgeMap.put("Location", user.getLocation());
			}  
 			
 			if(user.getRoleId()==3)
			{
				lindeLogMgr.logMessage("INFO", "user.getRoleId() :: "+user.getRoleId());
				String subPrgQueryStr = crAgeSqlQuery.toString();
				
				for(int i=1;i<=2;i++)
				{
					
					String location=null;
					if(i==1)
					{
						location="OffShore";
						crAgeSqlQuery.append("and crinfo.assignee in (select UPPER(users1.lastName||', '||users1.firstName) from User users1 where users1.location ='Offshore')");
						//crAgeSqlQuery.append("and crinfo.assignee in (select users1.firstName||','||users1.lastName from User users1 where users1.location ='OnShore')");
					}
					else if(i==2)
					{
						crAgeSqlQuery.append(subPrgQueryStr);
						location="OnShore";
						crAgeSqlQuery.append("and crinfo.assignee in (select UPPER(users1.lastName||', '||users1.firstName) from User users1 where users1.location ='Onsite')");
					}
					
					
					crAgeSqlQuery.append(" group by ageGrp.ageGroup  ");
					lindeLogMgr.logMessage("INFO", "user.getRoleId() : prioritySqlQuery.toString() : "+crAgeSqlQuery.toString());
					
					LindeMap lResultMap = getCRInfoByAgeDetails(user,crAgeSqlQuery.toString(),location);
					crbyAgeMap.put(location, lResultMap);
					crbyAgeMap.put("Location", location);
					
					crAgeSqlQuery.delete(0, crAgeSqlQuery.toString().length());
					
				}
				
			}
     	   
 	    	   lindeLogMgr.logMessage("DEBUG", "Total Number of Records::: ");
 	    	   

 		} catch(Exception e){
 			lindeLogMgr.logMessage("ERROR", "SQLException in getCRInfoByAge" +e.getMessage());			
 		}
 		
 		return crbyAgeMap;
 	
 	}
	
	private LindeMap getCRInfoByAgeDetails(User user,String queryString,String location)
	{
		
		LindeMap lCrbyAgeMap = new LindeMap();
		try {
		    
    	    Query query = getSession().createQuery(queryString.toString());
    	    long sum = 0;
    	    
			if(user.getLocation() !=null && user.getRoleId()!=3)
	    	   		lCrbyAgeMap.put("Location", user.getLocation());
			else
				lCrbyAgeMap.put("Location", location);
			
    	    lindeLogMgr.logMessage("DEBUG", "user.getLocation()  : "+user.getLocation());
    	    
    	    for (Iterator it = query.iterate(); it.hasNext();) {
	    	   Object[] row = (Object[]) it.next();
	    	   sum += (Long) row[1];
	    	   lCrbyAgeMap.put(row[0], row[1]);
    	    }
	    	   lindeLogMgr.logMessage("DEBUG", "Total Number of Records::: "+sum);
	    	   lCrbyAgeMap.put("Total", sum);

		} catch(Exception e){
			lindeLogMgr.logMessage("ERROR", "SQLException in getCRInfoByAge" +e.getMessage());			
		}
		
		return lCrbyAgeMap;
	}
	
	private LindeMap getModuleCRInfoByAge(User user,String selectedMonth){
		
		LindeMap crModulebyAgeMap = new LindeMap();
		String tableBeanName = "CRinfo";
		try {
		   /* StringBuffer crModuleAgeSqlQuery = new StringBuffer("select ageGrp.ageGroup,module.modulename,count(*) from "+tableBeanName+" crinfo,CRAge ageGrp,OwnerModule owner,Module module, User user where ");
		    //crAgeSqlQuery.append(" AND (SYSDATE - (to_date(crinfo.openDate,'dd/MM/yyyy HH24:MI:ss'))) >= ageGrp.rangeStart and (SYSDATE - (to_date(crinfo.openDate,'dd/MM/yyyy HH24:MI:ss'))) <= ageGrp.rangeEnd ");
			
		    if(user.getRoleId()==1)
				crModuleAgeSqlQuery.append(" crinfo.assignee ='").append(user.getLastName().toUpperCase()).append(", ").append(user.getFirstName().toUpperCase()).append("'").append("and user.userID='").append(user.getUserID()).append("'");
			
			if(user.getRoleId()==2)
				crModuleAgeSqlQuery.append(" crinfo.assignee in (UPPER(user.lastName||', '||user.firstName)) and user.location ='").append(user.getLocation()).append("'");
			 
			if(user.getRoleId()!=3)
			{
				crModuleAgeSqlQuery.append(" AND module.moduleid=owner.moduleId and user.userID=owner.userId  ");
				crModuleAgeSqlQuery.append(" AND crinfo.phase in ('Accept RfC & Prepare Approval','CI: Validate & Assign','Validate & Assign','Approval','Implementation','Post Implementation Review','Correct Implementation')");
				crModuleAgeSqlQuery.append(" AND CURRENT_DATE BETWEEN (to_date(crinfo.openDate,'dd/MM/yyyy HH24:MI:ss') + ageGrp.rangeStart) and (to_date(crinfo.openDate,'dd/MM/yyyy HH24:MI:ss') + ageGrp.rangeEnd) ");
				crModuleAgeSqlQuery.append(" group by ageGrp.ageGroup,module.modulename order by ageGrp.ageGroup");
				
				LindeMap lResultMap = getModuleCRInfoByAgeDetails(user,crModuleAgeSqlQuery.toString(),user.getLocation());
				crModulebyAgeMap.put("OffShore", lResultMap);
				crModulebyAgeMap.put("Location", user.getLocation());
			}*/
			
			 StringBuffer crModuleAgeSqlQuery = new StringBuffer("select ageGrp.ageGroup,crinfo.affectedCIs,count(*) from "+tableBeanName+" crinfo,CRAge ageGrp ");
			   
				
			    if(user.getRoleId()==1)
					crModuleAgeSqlQuery.append(" where crinfo.assignee ='").append(user.getLastName().toUpperCase()).append(", ").append(user.getFirstName().toUpperCase()).append("'");
				
				if(user.getRoleId()==2)
					crModuleAgeSqlQuery.append(" , User user where crinfo.assignee in (UPPER(user.lastName||', '||user.firstName)) and user.location ='").append(user.getLocation()).append("'");
				 
				if(user.getRoleId()!=3)
				{
				//	crModuleAgeSqlQuery.append(" AND module.moduleid=owner.moduleId and user.userID=owner.userId  ");
					if(selectedMonth != null && !selectedMonth.equalsIgnoreCase("ALL"))
					    {
						 crModuleAgeSqlQuery.append(" and to_date(crinfo.openDate,'dd/MM/yyyy HH24:MI:ss') BETWEEN trunc(to_date('");
						 crModuleAgeSqlQuery.append(selectedMonth).append("','MON-yyyy HH24:MI:ss'),'MON') and LAST_DAY(to_date('").append(selectedMonth).append("','MON-yyyy HH24:MI:ss'))");
					    }
					
					crModuleAgeSqlQuery.append(" AND crinfo.phase in ('Accept RfC & Prepare Approval','CI: Validate & Assign','Validate & Assign','Approval','Implementation','Post Implementation Review','Correct Implementation')");
					crModuleAgeSqlQuery.append(" AND CURRENT_DATE BETWEEN (to_date(crinfo.openDate,'dd/MM/yyyy HH24:MI:ss') + ageGrp.rangeStart) and (to_date(crinfo.openDate,'dd/MM/yyyy HH24:MI:ss') + ageGrp.rangeEnd) ");
					crModuleAgeSqlQuery.append(" group by ageGrp.ageGroup,crinfo.affectedCIs order by ageGrp.ageGroup");
					
					LindeMap lResultMap = getModuleCRInfoByAgeDetails(user,crModuleAgeSqlQuery.toString(),user.getLocation());
					crModulebyAgeMap.put("OffShore", lResultMap);
					crModulebyAgeMap.put("Location", user.getLocation());
				}
			 
			if(user.getRoleId()==3)
			{
				lindeLogMgr.logMessage("INFO", "user.getRoleId() :: "+user.getRoleId());
				String subPrgQueryStr = crModuleAgeSqlQuery.toString();
				
				for(int i=1;i<=2;i++)
				{
					
					String location=null;
					if(i==1)
					{
						location="OffShore";
						crModuleAgeSqlQuery.append(" , User user where crinfo.assignee in (UPPER(user.lastName||', '||user.firstName)) and user.location ='Offshore'");
					}
					else if(i==2)
					{
						crModuleAgeSqlQuery.append(subPrgQueryStr);
						location="OnShore";
						crModuleAgeSqlQuery.append(" , User user where crinfo.assignee in (UPPER(user.lastName||', '||user.firstName)) and user.location ='Onsite'");
					}
					
					//crModuleAgeSqlQuery.append(" AND module.moduleid=owner.moduleId and user.userID=owner.userId  ");
					if(selectedMonth != null && !selectedMonth.equalsIgnoreCase("ALL"))
				    {
					 crModuleAgeSqlQuery.append(" and to_date(crinfo.openDate,'dd/MM/yyyy HH24:MI:ss') BETWEEN trunc(to_date('");
					 crModuleAgeSqlQuery.append(selectedMonth).append("','MON-yyyy HH24:MI:ss'),'MON') and LAST_DAY(to_date('").append(selectedMonth).append("','MON-yyyy HH24:MI:ss'))");
				    }
					crModuleAgeSqlQuery.append(" AND crinfo.phase in ('Accept RfC & Prepare Approval','CI: Validate & Assign','Validate & Assign','Approval','Implementation','Post Implementation Review','Correct Implementation')");
					crModuleAgeSqlQuery.append(" AND CURRENT_DATE BETWEEN (to_date(crinfo.openDate,'dd/MM/yyyy HH24:MI:ss') + ageGrp.rangeStart) and (to_date(crinfo.openDate,'dd/MM/yyyy HH24:MI:ss') + ageGrp.rangeEnd) ");
					crModuleAgeSqlQuery.append(" group by ageGrp.ageGroup,crinfo.affectedCIs order by ageGrp.ageGroup");
					
					
					lindeLogMgr.logMessage("INFO", "user.getRoleId() : prioritySqlQuery.toString() : "+crModuleAgeSqlQuery.toString());
					
					LindeMap lResultMap = getModuleCRInfoByAgeDetails(user,crModuleAgeSqlQuery.toString(),location);
					crModulebyAgeMap.put(location, lResultMap);
					crModulebyAgeMap.put("Location", location);
					
					crModuleAgeSqlQuery.delete(0, crModuleAgeSqlQuery.toString().length());
					
				}
				
			
	    	   lindeLogMgr.logMessage("DEBUG", "Total Number of Records::: test");
	    	  // crModulebyAgeMap.put("Total", total);
			}
		} catch(Exception e){
			lindeLogMgr.logMessage("ERROR", "SQLException in getModuleCRInfoByAge" +e.getMessage());			
		}
		return crModulebyAgeMap;
	}
	
	
	private LindeMap getModuleCRInfoByAgeDetails(User user,String queryString,String location)
	{
		LindeMap lCrModulebyAgeMap = new LindeMap();
		
		try
		{
		ArrayList query = (ArrayList)getSession().createQuery(queryString.toString()).list();
	    long sum = 0;
	    
		  if(user.getLocation() !=null && user.getRoleId() !=3 )
    	   	 lCrModulebyAgeMap.put("Location", user.getLocation());
		 else
			 lCrModulebyAgeMap.put("Location", location);
		  
	       lindeLogMgr.logMessage("DEBUG", "user.getLocation()  : "+user.getLocation());
	       Object[] temp = new Object[1];
		   Object[] row;
		   long groupSum=0,total=0;
		   LindeMap innerMap = new LindeMap();
		   for (int i=0;i<query.size();i++) {
	    	   row = (Object[]) query.get(i);
	    	  lindeLogMgr.logMessage("DEBUG", "Values++++++++++++:::"+row[0]+"  "+row[1]+" "+row[2]);
	    	   if (i!=0){
	    		   
	    		   if(!row[0].toString().equals(temp[0].toString()))
	    		   {
	    			   innerMap = new LindeMap();
	    			   lindeLogMgr.logMessage("DEBUG", "Resetting Tempother value : ");
	    			   groupSum =0;
	    			   lindeLogMgr.logMessage("DEBUG", "Differnt status:"+row[0]+":temp:"+temp[0]);
	    		   }
	    	   }
	    	  		   innerMap.put(row[1], row[2]); 
	    	    	   groupSum += (Long) row[2];	
	    	           total += (Long) row[2];	
	    	           innerMap.put("Total", groupSum);
	    	       	   if(i==0 || !row[0].toString().equals(temp[0].toString()))
	    	       		lCrModulebyAgeMap.put(row[0], innerMap);
 	    	   	   
	    	       	   temp[0] = row[0];
	    	  }
    	   lindeLogMgr.logMessage("DEBUG", "Total Number of Records::: "+sum);
    	   lCrModulebyAgeMap.put("Total", total);

	} catch(Exception e){
		lindeLogMgr.logMessage("ERROR", "SQLException in getModuleCRInfoByAgeDetails" +e.getMessage());			
	}
		return lCrModulebyAgeMap;
	}
	
	public List<CRinfo> getCRDetailedList(String priority_age_phase, String type, String module,User user,String year,String selectedMonth)
	{
		lindeLogMgr.logMessage("DEBUG", "Before call to  getCRDetailedList()");
		
		LindeList crInfoList =new LindeList();
		Query sqlCriteria = null;
		
		List<CRinfo> crList = new ArrayList<CRinfo>();
		try
		{
		
		String tableName = "CRinfo";
		 
		
		if(type!=null && type.equals("priority"))
		{
			
           StringBuffer prioritySqlQuery = new StringBuffer("from CRinfo crinfo where crinfo.phase in ('Accept RfC & Prepare Approval','CI: Validate & Assign','Validate & Assign','Approval','Implementation','Post Implementation Review','Correct Implementation')");
			
           if(selectedMonth != null && !selectedMonth.equalsIgnoreCase("ALL"))
           {
        	   prioritySqlQuery.append(" and to_date(crinfo.openDate,'dd/MM/yyyy HH24:MI:ss') BETWEEN trunc(to_date('");
		       prioritySqlQuery.append(selectedMonth).append("','MON-yyyy HH24:MI:ss'),'MON') and LAST_DAY(to_date('").append(selectedMonth).append("','MON-yyyy HH24:MI:ss'))");

           }
           
		    if(user.getRoleId()==1)
		    	prioritySqlQuery.append("and crinfo.assignee ='").append(user.getLastName().toUpperCase()).append(", ").append(user.getFirstName().toUpperCase()).append("'");
			
			if(user.getRoleId()==2)
				prioritySqlQuery.append("and crinfo.assignee in (select UPPER(users1.lastName||', '||users1.firstName) from User users1 where users1.location ='").append(user.getLocation()).append("')");
			
			if(user.getRoleId() ==3)
				prioritySqlQuery.append("and crinfo.assignee in (select UPPER(users1.lastName||', '||users1.firstName) from User users1)");
			
			prioritySqlQuery.append(" and crinfo.priority in ( '").append(priority_age_phase).append("')");
			
			sqlCriteria = getSession().createQuery(prioritySqlQuery.toString());
		 // sqlCriteria.add(Restrictions.eq("priority", priority_age_phase));
		}
		else if(type!=null && type.equals("age") && module == "")
		{
			lindeLogMgr.logMessage("DEBUG", "crAgeSqlQuery  modulemodule module: "+module);
			 StringBuffer crAgeSqlQuery = new StringBuffer(" from CRinfo crinfo1 where crinfo1.changeId in (select crinfo.changeId from CRinfo crinfo, CRAge ageGrp where crinfo.phase in ('Accept RfC & Prepare Approval','CI: Validate & Assign','Validate & Assign','Approval','Implementation','Post Implementation Review','Correct Implementation')");
			 
			 if(selectedMonth != null && !selectedMonth.equalsIgnoreCase("ALL"))
	           {
				 crAgeSqlQuery.append(" and to_date(crinfo.openDate,'dd/MM/yyyy HH24:MI:ss') BETWEEN trunc(to_date('");
				 crAgeSqlQuery.append(selectedMonth).append("','MON-yyyy HH24:MI:ss'),'MON') and LAST_DAY(to_date('").append(selectedMonth).append("','MON-yyyy HH24:MI:ss'))");

	           }
			 
	 		    crAgeSqlQuery.append(" AND CURRENT_DATE BETWEEN (to_date(crinfo.openDate,'dd/MM/yyyy HH24:MI:ss') + ageGrp.rangeStart) and (to_date(crinfo.openDate,'dd/MM/yyyy HH24:MI:ss') + ageGrp.rangeEnd) and ageGrp.ageGroup in ('").append(priority_age_phase).append("')");
	 		    
	 			if(user.getRoleId()==1)
	 				crAgeSqlQuery.append(" AND crinfo.assignee ='").append(user.getLastName().toUpperCase()).append(", ").append(user.getFirstName().toUpperCase()).append("')");
	 			
	 			if(user.getRoleId()==2)
	 				crAgeSqlQuery.append(" and crinfo.assignee in (select UPPER(users1.lastName||', '||users1.firstName)  from User users1 where users1.location ='").append(user.getLocation()).append("'))");
	 					 				
	 			if(user.getRoleId() ==3)
				{
	 				crAgeSqlQuery.append(" and crinfo.assignee in (select UPPER(users1.lastName||', '||users1.firstName) from User users1))");
				} 
			
	 			lindeLogMgr.logMessage("DEBUG", "crAgeSqlQuery  :: "+crAgeSqlQuery.toString());
			sqlCriteria = getSession().createQuery(crAgeSqlQuery.toString()); 
			
	/*	sqlCriteria = getSession().createCriteria(CRinfo.class,"crinfo").createCriteria("CRAge","ageGrp");
			sqlCriteria.setFetchMode("crinfo", FetchMode.JOIN).add(Restrictions.between("CURRENT_DATE", "to_date(openDate,'dd/MM/yyyy HH24:MI:ss')+ ageGrp.rangeStart","to_date(crinfo.openDate,'dd/MM/yyyy HH24:MI:ss') + ageGrp.rangeEnd"));
			 sqlCriteria.add(Restrictions.eq("ageGroup", priority_age_phase));  */
			 
		}
		else if(type!=null && type.equals("phase"))
		{
		
			// StringBuffer statusSqlQuery = new StringBuffer("from CRinfo crinfo1 where crinfo1.changeId in (select crinfo.changeId from CRinfo crinfo,OwnerModule owner,Module module,User user where ");
			
			 StringBuffer statusSqlQuery = new StringBuffer("from CRinfo crinfo1 where crinfo1.changeId in (select crinfo.changeId from CRinfo crinfo,User user where ");
 		     
			 if(user.getRoleId()==1)
 					statusSqlQuery.append(" crinfo.assignee ='").append(user.getLastName().toUpperCase()).append(", ").append(user.getFirstName().toUpperCase()).append("'").append("and user.userID='").append(user.getUserID()).append("'");
 		      
 		      if(user.getRoleId()==2)
 		    	  statusSqlQuery.append(" crinfo.assignee in ( UPPER(user.lastName||', '||user.firstName)) and user.location ='").append(user.getLocation()).append("'");

 		      if(user.getRoleId()==3)
 			  {
 		    	 statusSqlQuery.append(" crinfo.assignee in (UPPER(user.lastName||', '||user.firstName))");
 	 		  }
 		      
 		     if(selectedMonth != null && !selectedMonth.equalsIgnoreCase("ALL"))
	           {
 		    	statusSqlQuery.append(" and to_date(crinfo.openDate,'dd/MM/yyyy HH24:MI:ss') BETWEEN trunc(to_date('");
 		    	statusSqlQuery.append(selectedMonth).append("','MON-yyyy HH24:MI:ss'),'MON') and LAST_DAY(to_date('").append(selectedMonth).append("','MON-yyyy HH24:MI:ss'))");

	           }
 		     
 		   //  statusSqlQuery.append(" and module.moduleid = owner.moduleId and user.userID = owner.userId and crinfo.phase in ('").append(priority_age_phase).append("')").append(" and module.modulename in ('").append(module).append("'))");
 		      
 		     statusSqlQuery.append(" and  crinfo.phase in ('").append(priority_age_phase).append("')").append(" and crinfo.affectedCIs in ('").append(module).append("'))");  
	 			 
 		 	lindeLogMgr.logMessage("DEBUG", "statusSqlQuery  :::: "+statusSqlQuery.toString());
			sqlCriteria = getSession().createQuery(statusSqlQuery.toString()); 
			
		}
		else
		{
			
			
		//	StringBuffer crModuleAgeSqlQuery = new StringBuffer("from CRinfo crinfo1 where crinfo1.changeId in (select crinfo.changeId from CRinfo crinfo,CRAge ageGrp,OwnerModule owner,Module module, User user where ");
			
			StringBuffer crModuleAgeSqlQuery = new StringBuffer("from CRinfo crinfo1 where crinfo1.changeId in (select crinfo.changeId from CRinfo crinfo,CRAge ageGrp,OwnerModule owner,Module module, User user where ");
		  			
		    if(user.getRoleId()==1)
				crModuleAgeSqlQuery.append(" crinfo.assignee ='").append(user.getLastName().toUpperCase()).append(", ").append(user.getFirstName().toUpperCase()).append("'").append("and user.userID='").append(user.getUserID()).append("'");
			
			if(user.getRoleId()==2)
				crModuleAgeSqlQuery.append(" crinfo.assignee in (UPPER(user.lastName||', '||user.firstName)) and user.location ='").append(user.getLocation()).append("'");
			 
			if(user.getRoleId()==3)
			{
				
				crModuleAgeSqlQuery.append(" crinfo.assignee in (UPPER(user.lastName||', '||user.firstName))");
			}
			//crModuleAgeSqlQuery.append(" AND module.moduleid=owner.moduleId and user.userID=owner.userId  ");
			
			if(selectedMonth != null && !selectedMonth.equalsIgnoreCase("ALL"))
	           {
				crModuleAgeSqlQuery.append(" and to_date(crinfo.openDate,'dd/MM/yyyy HH24:MI:ss') BETWEEN trunc(to_date('");
				crModuleAgeSqlQuery.append(selectedMonth).append("','MON-yyyy HH24:MI:ss'),'MON') and LAST_DAY(to_date('").append(selectedMonth).append("','MON-yyyy HH24:MI:ss'))");

	           }
			
			crModuleAgeSqlQuery.append(" AND crinfo.phase in ('Accept RfC & Prepare Approval','CI: Validate & Assign','Validate & Assign','Approval','Implementation','Post Implementation Review','Correct Implementation')");
			crModuleAgeSqlQuery.append(" AND CURRENT_DATE BETWEEN (to_date(crinfo.openDate,'dd/MM/yyyy HH24:MI:ss') + ageGrp.rangeStart) and (to_date(crinfo.openDate,'dd/MM/yyyy HH24:MI:ss') + ageGrp.rangeEnd) and ageGrp.ageGroup in ('").append(priority_age_phase).append("')");
			crModuleAgeSqlQuery.append(" AND crinfo.affectedCIs in ('").append(module).append("') )");
			
			lindeLogMgr.logMessage("DEBUG", "crModuleAgeSqlQuery  :::: "+crModuleAgeSqlQuery.toString());
			sqlCriteria = getSession().createQuery(crModuleAgeSqlQuery.toString()); 
		}
		
		
		crList = (ArrayList<CRinfo>) sqlCriteria.list();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
	//	java.sql.Date currentDate = new java.sql.Date(new Date().getTime());
		long currentDate =new Date().getTime();
		for (CRinfo cRinfo : crList) {
			lindeLogMgr.logMessage("DEBUG", "cRinfo 1 ");
	       	//	Date openDate = dateFormat.parse(cRinfo.getOpenDate());
			//	long differInDays = (new Date().getTime() - openDate.getTime())/(24 * 60 * 60 * 1000);
			//	cRinfo.setAgeing(differInDays+"");
			
			long differInDays = countAge(cRinfo.getOpenDate());		
			lindeLogMgr.logMessage("DEBUG", "differInDays "+differInDays);
			
			if(TrackingToolConstant.ACCEPT_PHASE.equalsIgnoreCase(cRinfo.getPhase()) || TrackingToolConstant.CI_VALIDATE_PHASE.equalsIgnoreCase(cRinfo.getPhase())
					|| TrackingToolConstant.VALIDATE_PHASE.equalsIgnoreCase(cRinfo.getPhase()))
			{
				if(cRinfo.getPriority().equalsIgnoreCase(TrackingToolConstant.PRIORITY_1) || cRinfo.getPriority().equalsIgnoreCase(TrackingToolConstant.PRIORITY_2))
				  cRinfo.setTargetDate(calculateTargetDate(cRinfo.getOpenDate(),4));
				else
					cRinfo.setTargetDate(calculateTargetDate(cRinfo.getOpenDate(),17));
			}
			else
			{
				cRinfo.setTargetDate(cRinfo.getRequestedDate());
			}
				
			cRinfo.setAgeing(differInDays+"");
			crInfoList.add(cRinfo);
		}
		}
		catch(Exception ex)
		{
			lindeLogMgr.logStackTrace("ERROR", "Error in getCRDetailedList ", ex);
		}
		
		
		return crInfoList;
	}
	
	
	private String getUploadDate(){
		
		return new String();
	}
	
	public long countAge(String startDate) throws ParseException
	{
	       DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	    Date date1 = df.parse(startDate);
	    lindeLogMgr.logMessage("DEBUG","Inside While method::::"+date1.toString());
	    
	    Date date2 = df.parse(df.format(new Date().getTime()).toString());
	//    lindeLogMgr.logMessage("DEBUG","Inside While method::::"+date2.toString());
	    Calendar cal1 = Calendar.getInstance();
	    Calendar cal2 = Calendar.getInstance();
	    cal1.setTime(date1);
	    cal2.setTime(date2);

	    int numberOfDays = 0;
	    while (cal1.before(cal2)) {
	        if ((Calendar.SATURDAY != cal1.get(Calendar.DAY_OF_WEEK))
	                &&(Calendar.SUNDAY != cal1.get(Calendar.DAY_OF_WEEK))) {
	            numberOfDays++;
	        }
	        cal1.add(Calendar.DATE,1);
	   //     lindeLogMgr.logMessage("DEBUG","Inside While method::::"+numberOfDays);
	    }
	//    System.out.println(numberOfDays);
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

	  //  Date targetDate = df.parse(df.format(cal1.getTime()).toString());
	    String targetDate = df.format(cal1.getTime());
	       return targetDate;
	}


	public List<CRinfo> saveCRList(List<CRinfo> crlist, String priority_age_module, String type,String module, User user,String selectedMonth) {
		
		lindeLogMgr.logMessage("INFO", "Enter into saveCRList() ");
		List<CRinfo> tempLst =null;
		try{
			for(int i=0;i<crlist.size();i++)
			{
				CRinfo crInfo = (CRinfo)crlist.get(i);
				
				CRinfo record =(CRinfo) getSession().get(CRinfo.class, crInfo.getChangeId());
				lindeLogMgr.logMessage("DEBUG", "record  "+record.getChangeId()+" record :: "+record.getPhase());
				record.setPhase(crInfo.getPhase());
				record.setAffectedCIs(crInfo.getAffectedCIs());
				getSession().update(record);
			}
			
			tempLst = getCRDetailedList(priority_age_module,type,module,user,"",selectedMonth); 
			}
			
			catch (Exception e) {
				lindeLogMgr.logStackTrace("ERROR","Error in saveCRList is: "+e.getMessage(),e);
			}
			
		
		return tempLst;
	}

	public CrSummaryVO getCRSLADetails(User user) {
		
			CrSummaryVO crSummaryVO = new CrSummaryVO();
			
			if (user.getRoleId()==1){
				lindeLogMgr.logMessage("INFO", "for user condition");
				String username=user.getLastName().toUpperCase()+", "+user.getFirstName().toUpperCase();
				
				LindeMap openMap = getOpenUserSLAdetails(username);
				
				String crquery="select phase,count(*) from CRinfo where to_date(open_date, 'DD/MM/YYYY HH24:MI:SS') BETWEEN ADD_MONTHS (TRUNC (SYSDATE, 'MM'), :startDate) and  LAST_DAY(ADD_MONTHS (TRUNC (SYSDATE, 'MM'), :endDate)) and assignee in('"+username+"') group by phase";
				LindeMap crinfoMap = getSLADetails(crquery);
				LindeMap impMap =  getImpSLAdetails(user);
				LindeMap closedMap =  getclosedSLAdetails(user);
				
				crSummaryVO.setByOpenSlaDetails(openMap);
				crSummaryVO.setByCRDetails(crinfoMap);
				crSummaryVO.setByImpSlaDetails(impMap);
				crSummaryVO.setByClosedSlaDetails(closedMap);
			}
	  	   		
			else if(user.getRoleId()==2){
				String crquery;
				if(user.getLocation().equals("Offshore")){
			//	crquery="select phase,count(*) from CRinfo where to_date(open_date, 'DD/MM/YYYY HH24:MI:SS') >= to_date( :Date ,'DD/MM/YYYY HH24:MI:SS') and assignee in(select UPPER(lastName||', '||firstName) from User where location='Offshore') group by phase";}
				//crquery="select phase,count(*) from CRinfo where to_date(open_date, 'DD/MM/YYYY HH24:MI:SS') BETWEEN (to_date(:startDate,'dd/MM/yyyy HH24:MI:ss') and  to_date( :endDate ,'DD/MM/YYYY HH24:MI:SS')) and assignee in(select UPPER(lastName||', '||firstName) from User where location='Offshore') group by phase";
				crquery="select phase,count(*) from CRinfo where to_date(open_date, 'DD/MM/YYYY HH24:MI:SS') BETWEEN ADD_MONTHS (TRUNC (SYSDATE, 'MM'), :startDate) and  LAST_DAY(ADD_MONTHS (TRUNC (SYSDATE, 'MM'), :endDate)) and assignee in(select UPPER(lastName||', '||firstName) from User where location='Offshore') group by phase";
				}		
				else {
				// crquery="select phase,count(*) from CRinfo where to_date(open_date, 'DD/MM/YYYY HH24:MI:SS') BETWEEN (to_date(:startDate,'dd/MM/yyyy HH24:MI:ss') and  to_date( :endDate ,'DD/MM/YYYY HH24:MI:SS')) and assignee in(select UPPER(lastName||', '||firstName) from User where location='Onsite') group by phase";
					crquery="select phase,count(*) from CRinfo where to_date(open_date, 'DD/MM/YYYY HH24:MI:SS') BETWEEN ADD_MONTHS (TRUNC (SYSDATE, 'MM'), :startDate) and  LAST_DAY(ADD_MONTHS (TRUNC (SYSDATE, 'MM'), :endDate)) and assignee in(select UPPER(lastName||', '||firstName) from User where location='Onsite') group by phase";	
				}
				LindeMap crinfoMap = getSLADetails(crquery);
				
				
				lindeLogMgr.logMessage("INFO", "for PL condition");
				
			LindeMap openMap = getOpenSLAdetails(user);
			LindeMap impMap =  getImpSLAdetails(user);
			LindeMap closedMap =  getclosedSLAdetails(user);
			crSummaryVO.setByOpenSlaDetails(openMap);
			crSummaryVO.setByClosedSlaDetails(closedMap);
			crSummaryVO.setByImpSlaDetails(impMap);
			crSummaryVO.setByCRDetails(crinfoMap);
			}
			
			else if(user.getRoleId()==3){
				lindeLogMgr.logMessage("INFO", "for PM condition");
	
				//	String crquery="select phase,count(*) from CRinfo where to_date(open_date, 'DD/MM/YYYY HH24:MI:SS') >= to_date( :Date ,'DD/MM/YYYY HH24:MI:SS') group by phase";
				
				//String crquery="select phase,count(*) from CRinfo where to_date(open_date, 'DD/MM/YYYY HH24:MI:SS') BETWEEN to_date(:startDate,'DD/MM/YYYY HH24:MI:ss') and  to_date( :endDate ,'DD/MM/YYYY HH24:MI:SS') group by phase";
				
				String crquery="select phase,count(*) from CRinfo where to_date(open_date, 'DD/MM/YYYY HH24:MI:SS') BETWEEN ADD_MONTHS (TRUNC (SYSDATE, 'MM'), :startDate) and  LAST_DAY(ADD_MONTHS (TRUNC (SYSDATE, 'MM'), :endDate)) group by phase";
				
			//	String crquery="select phase,count(*) from CRinfo where to_date(open_date, 'DD/MM/YYYY HH24:MI:SS') BETWEEN :startDate and :endDate  group by phase";
			//	StringBuilder sqlBuilder = new StringBuilder(crquery);
			//	sqlBuilder.append(monthStr).append("','DD/MM/YYYY HH24:MI:SS') group by phase");
				LindeMap crinfoMap = getSLADetails(crquery);
				String location="Offshore";
				String location1="Onsite";
				LindeMap openPMOffMap =getPMOpenSLAdetails(location);
				LindeMap openPMOnMap =getPMOpenSLAdetails(location1);
				LindeMap closedPMOffMap =getPMClosedSLAdetails(location);
				LindeMap closedPMOnMap =getPMClosedSLAdetails(location1);
				crSummaryVO.setByPMOpenSlaDetails(openPMOffMap);
				crSummaryVO.setByPMOnOpenSlaDetails(openPMOnMap);
				crSummaryVO.setByPMClosedSlaDetails(closedPMOffMap);
				crSummaryVO.setByPMOnClosedSlaDetails(closedPMOnMap);
				crSummaryVO.setByCRDetails(crinfoMap);
				
			}
			 return crSummaryVO;
		}
		
		
	public LindeMap getSLADetails( String crquery)
	{
		lindeLogMgr.logMessage("INFO", "getSLADetails() ");
		
		LindeMap severityMap1 = new LindeMap();
		   // get the value of all the calendar date fields.
		
		String month="";
		  try
		  {
		
			  for(int loop=0;loop>-6;loop--)
			  {
				  LindeMap severityMap = new LindeMap();

				  lindeLogMgr.logMessage("DEBUG", "loop value is "+loop);

				  Calendar cal = Calendar.getInstance();
				  cal.add(cal.MONTH, loop);
				  
				   month = cal.getDisplayName(cal.MONTH, cal.LONG, Locale.getDefault())+"-"+cal.get(Calendar.YEAR);
				   lindeLogMgr.logMessage("DEBUG", "month value is "+month);
				  
		
		 Query query = getSession().createQuery(crquery).setParameter("startDate",loop).setParameter("endDate", loop);
		 lindeLogMgr.logMessage("DEBUG", "query String in getSLADetails() "+query.getQueryString());
	   long sum = 0,impTotal=0,valTotal=0;
	   for (Iterator it = query.iterate(); it.hasNext();) {
	    	   Object[] row = (Object[]) it.next();
	    	   sum += (Long) row[1];
	    	   String phaseStr =(String) row[0];
               lindeLogMgr.logMessage("DEBUG", "priorityStr1 "+phaseStr);
               String valueStr =  row[1].toString();
               
               lindeLogMgr.logMessage("DEBUG", "priorityStr value1 "+valueStr);
               
               if(phaseStr.equals("Closed"))
            	   severityMap.put("Closed", valueStr);
               
               else if(phaseStr.equals("Approval"))
            	   severityMap.put("Approval", valueStr);
               
               else if(phaseStr.contains("Accept"))
            	   severityMap.put("AcceptRfCPrepareApp",valueStr);
               
               else if(phaseStr.contains("Validate"))
            	   valTotal+=(Long) row[1];
               else if(phaseStr.contains("CI:"))
            	   valTotal+=(Long) row[1];
               
               else if(phaseStr.contains("Post"))
            	   impTotal+=(Long) row[1];
               else if(phaseStr.contains("Correct"))
            	   impTotal+=(Long) row[1];
               
               else if(phaseStr.equals("Implementation"))
            	   severityMap.put("Implementation", valueStr);
               


	   }
	   
	   severityMap.put("ValidateAss", valTotal);
	   severityMap.put("PostImplementation", impTotal);
	   severityMap.put("Total", sum);
	   
	   lindeLogMgr.logMessage("DEBUG", "value ValidateAss="+severityMap.get("Implementation"));
	   lindeLogMgr.logMessage("DEBUG", "value total"+sum);
	   
	   severityMap1.put(month, severityMap);
	   
			  }
		  }
		  catch(Exception e)
		  {
			  lindeLogMgr.logStackTrace("ERROR","Exception in getSLADetails()", e);
		  }
		  lindeLogMgr.logMessage("DEBUG", "size of the map is "+severityMap1.size());
	   return severityMap1; 
		
	}	
		
	
public LindeMap getOpenSLAdetails(User user) 
	
	{
		/// for CR met
		LindeMap severityMap = new LindeMap();
		
		String mpri1query="select changeId from CRinfo where   priority like '%1%' AND phase NOT in ('Accept RfC & Prepare Approval','Validate & Assign','CI: Validate & Assign') and CURRENT_DATE-to_date(open_date, 'DD/MM/YYYY HH24:MI:SS')<4 and assignee in(select UPPER(lastName||', '||firstName) from User where location= :Location)";
		Query mquery1 = getSession().createQuery(mpri1query);
		mquery1.setParameter("Location", user.getLocation());
		List<String> mlist1 = mquery1.list();
		
					severityMap.put("mprio1", mlist1.size());
			
			String mpri2query="select changeId from CRinfo where   priority like '%2%' AND phase NOT in ('Accept RfC & Prepare Approval','Validate & Assign','CI: Validate & Assign') and CURRENT_DATE-to_date(open_date, 'DD/MM/YYYY HH24:MI:SS')<4 and assignee in(select UPPER(lastName||', '||firstName) from User where location= :Location)";
					Query mquery2 = getSession().createQuery(mpri2query);
					mquery2.setParameter("Location", user.getLocation());
					List<String> mlist2 = mquery2.list();					
					severityMap.put("mprio2", mlist2.size());
			
			String mpri3query="select changeId from CRinfo where   priority like '%3%' AND phase NOT in ('Accept RfC & Prepare Approval','Validate & Assign','CI: Validate & Assign') and CURRENT_DATE-to_date(open_date, 'DD/MM/YYYY HH24:MI:SS')<17 and assignee in(select UPPER(lastName||', '||firstName) from User where location= :Location)";
			 		Query mquery3 = getSession().createQuery(mpri3query);
			 		mquery3.setParameter("Location", user.getLocation());
			 		List<String> mlist3 = mquery3.list();
			 		severityMap.put("mprio3", mlist3.size());
				 
			String mpri4query="select changeId from CRinfo where   priority like '%4%' AND phase in ('Accept RfC & Prepare Approval','Validate & Assign','CI: Validate & Assign') and assignee in(select UPPER(lastName||', '||firstName) from User where location= :Location)";
					Query mquery4 = getSession().createQuery(mpri4query);
					mquery4.setParameter("Location", user.getLocation());
					List<String> mlist4 = mquery4.list();
					severityMap.put("mprio4", mlist4.size());
					
					int totalMetcr=mlist1.size()+mlist2.size()+mlist3.size()+mlist4.size();
					
		//for CR not met 		 
					
					String pri1query="select changeId from CRinfo where   priority like '%1%' AND phase in ('Accept RfC & Prepare Approval','Validate & Assign','CI: Validate & Assign') and CURRENT_DATE-to_date(open_date, 'DD/MM/YYYY HH24:MI:SS')>4 and assignee in(select UPPER(lastName||', '||firstName) from User where location= :Location)";
					Query query1 = getSession().createQuery(pri1query);
					query1.setParameter("Location", user.getLocation());
					List<String> list1 = query1.list();
					
					severityMap.put("nmprio1", list1.size());
					
					
			String pri2query="select changeId from CRinfo where   priority like '%2%' AND phase in ('Accept RfC & Prepare Approval','Validate & Assign','CI: Validate & Assign') and CURRENT_DATE-to_date(open_date, 'DD/MM/YYYY HH24:MI:SS')>4 and assignee in(select UPPER(lastName||', '||firstName) from User where location= :Location)";
					Query query2 = getSession().createQuery(pri2query);
					query2.setParameter("Location", user.getLocation());
					List<String> list2 = query2.list();
					severityMap.put("nmprio2", list2.size());
				 
			String pri3query="select changeId from CRinfo where   priority like '%3%' AND phase in ('Accept RfC & Prepare Approval','Validate & Assign','CI: Validate & Assign') and CURRENT_DATE-to_date(open_date, 'DD/MM/YYYY HH24:MI:SS')>17 and assignee in(select UPPER(lastName||', '||firstName) from User where location= :Location)";
					Query query3 = getSession().createQuery(pri3query);
					query3.setParameter("Location", user.getLocation());
					List<String> list3 = query3.list();
					severityMap.put("nmprio3", list3.size());
					 
					String pri4query="select changeId from CRinfo where   priority like '%4%' AND phase in ('Accept RfC & Prepare Approval','Validate & Assign','CI: Validate & Assign') and CURRENT_DATE-to_date(open_date, 'DD/MM/YYYY HH24:MI:SS')>17 and assignee in(select UPPER(lastName||', '||firstName) from User where location= :Location)";
					Query query4 = getSession().createQuery(pri4query);
					query4.setParameter("Location", user.getLocation());
					List<String> list4 = query4.list();
					
					severityMap.put("nmprio4", list4.size());
					 
					int toatlNotMetcr=list1.size()+list2.size()+list3.size()+list4.size();
					 
					 
					int toatlCR=toatlNotMetcr+totalMetcr;
					 
					 //to calculate the percentage
					 
					
					
					 	
				
					 if(mlist1.size()!=0)
					 severityMap.put("perprio1", java.lang.Math.ceil((mlist1.size()*100)/toatlCR));
					 if(mlist2.size()!=0)
						 severityMap.put("perprio2", java.lang.Math.ceil((mlist2.size()*100)/toatlCR));
					 if(mlist3.size()!=0)
						 severityMap.put("perprio3", java.lang.Math.ceil((mlist3.size()*100)/toatlCR));
					 if(mlist4.size()!=0)
						 severityMap.put("perprio4", java.lang.Math.ceil((mlist4.size()*100)/toatlCR));
					 
					 //for percentage not met
					 if(list1.size()!=0)
					 severityMap.put("nperprio1", java.lang.Math.ceil((list1.size()*100)/toatlCR));
					 if(list2.size()!=0)
						 severityMap.put("nperprio2", java.lang.Math.ceil((list2.size()*100)/toatlCR));
					 if(list3.size()!=0)
						 severityMap.put("nperprio3", java.lang.Math.ceil((list3.size()*100)/toatlCR));
					 if(list4.size()!=0)
					   severityMap.put("nperprio4", java.lang.Math.ceil((list4.size()*100)/toatlCR));
					 
					 
					 lindeLogMgr.logMessage("INFO","per of met priority 2::"+toatlCR);
					 
					 
					 return severityMap;
		}
	
	public LindeMap getImpSLAdetails(User user) 
		
		{
			/// for CR met
			
					LindeMap severityMap = new LindeMap();
					
					StringBuilder impri1query=new StringBuilder("select changeId from CRinfo where   priority like '%1%' AND phase  IN ('Implementation','Post Implementation Review','Correct Implementation') and  to_date(REQUESTED_DATE, 'DD/MM/YYYY HH24:MI:SS') > CURRENT_DATE and assignee ");
					if(user.getRoleId()==1)
						impri1query.append(" = '").append(user.getLastName().toUpperCase()).append(", ").append(user.getFirstName().toUpperCase()).append("'");
					else
						impri1query.append(" in(select UPPER(lastName||', '||firstName) from User where location= :Location)");
					
					Query imquery1 = getSession().createQuery(impri1query.toString());
					if(user.getRoleId()==2)
					  imquery1.setParameter("Location", user.getLocation());
					List<String> imlist1 = imquery1.list();
			
						severityMap.put("imprio1", imlist1.size());
				
						StringBuilder impri2query=new StringBuilder("select changeId from CRinfo where   priority like '%2%' AND phase  IN ('Implementation','Post Implementation Review','Correct Implementation') and  to_date(REQUESTED_DATE, 'DD/MM/YYYY HH24:MI:SS') > CURRENT_DATE and assignee ");
						if(user.getRoleId()==1)
							impri2query.append(" = '").append(user.getLastName().toUpperCase()).append(", ").append(user.getFirstName().toUpperCase()).append("'");
						else
							impri2query.append(" in(select UPPER(lastName||', '||firstName) from User where location= :Location)");
						
						Query imquery2 = getSession().createQuery(impri2query.toString());
						if(user.getRoleId()==2)
						imquery2.setParameter("Location", user.getLocation());
						List<String> imlist2 = imquery2.list();
						
						severityMap.put("imprio2", imlist2.size());
				 
						StringBuilder impri3query=new StringBuilder("select changeId from CRinfo where   priority like '%3%' AND phase  IN ('Implementation','Post Implementation Review','Correct Implementation') and to_date(REQUESTED_DATE, 'DD/MM/YYYY HH24:MI:SS') > CURRENT_DATE and assignee ");
						if(user.getRoleId()==1)
							impri3query.append(" = '").append(user.getLastName().toUpperCase()).append(", ").append(user.getFirstName().toUpperCase()).append("'");
						else
							impri3query.append(" in(select UPPER(lastName||', '||firstName) from User where location= :Location)");
						
						
						Query imquery3 = getSession().createQuery(impri3query.toString());
						if(user.getRoleId()==2)
						imquery3.setParameter("Location", user.getLocation());
						List<String> imlist3 = imquery3.list();
						
						severityMap.put("imprio3", imlist3.size());
					 
						StringBuilder impri4query=new StringBuilder("select changeId from CRinfo where   priority like '%4%' AND phase  IN ('Implementation','Post Implementation Review','Correct Implementation') and to_date(REQUESTED_DATE, 'DD/MM/YYYY HH24:MI:SS') > CURRENT_DATE and assignee ");
						
						if(user.getRoleId()==1)
							impri4query.append(" = '").append(user.getLastName().toUpperCase()).append(", ").append(user.getFirstName().toUpperCase()).append("'");
						else
							impri4query.append(" in(select UPPER(lastName||', '||firstName) from User where location= :Location)");
						
						Query imquery4 = getSession().createQuery(impri4query.toString());
						if(user.getRoleId()==2)
						imquery4.setParameter("Location", user.getLocation());
						List<String> imlist4 = imquery4.list();
						
						severityMap.put("imprio4", imlist4.size());
						
						int totalMetcr=imlist1.size()+imlist2.size()+imlist3.size()+imlist4.size();
						 
			///for cr not met 
						
						StringBuilder inmpri1query=new StringBuilder("select changeId from CRinfo where   priority like '%1%' AND phase  IN ('Implementation','Post Implementation Review','Correct Implementation') and CURRENT_DATE >to_date(REQUESTED_DATE, 'DD/MM/YYYY HH24:MI:SS') and assignee ");
						if(user.getRoleId()==1)
							inmpri1query.append(" = '").append(user.getLastName().toUpperCase()).append(", ").append(user.getFirstName().toUpperCase()).append("'");
						else
							inmpri1query.append(" in(select UPPER(lastName||', '||firstName) from User where location= :Location)");
						
						Query inmquery1 = getSession().createQuery(inmpri1query.toString());
						if(user.getRoleId()==2)
						   inmquery1.setParameter("Location", user.getLocation());
						List<String> mlist1 = inmquery1.list();
						
						severityMap.put("inmprio1", mlist1.size());
								
						StringBuilder inmpri2query=new StringBuilder("select changeId from CRinfo where   priority like '%2%' AND phase  IN ('Implementation','Post Implementation Review','Correct Implementation') and CURRENT_DATE >to_date(REQUESTED_DATE, 'DD/MM/YYYY HH24:MI:SS') and assignee ");
						if(user.getRoleId()==1)
							inmpri2query.append(" = '").append(user.getLastName().toUpperCase()).append(", ").append(user.getFirstName().toUpperCase()).append("'");
						else
							inmpri2query.append(" in(select UPPER(lastName||', '||firstName) from User where location= :Location)");
						Query inmquery2 = getSession().createQuery(inmpri2query.toString());
						if(user.getRoleId()==2)
						    inmquery2.setParameter("Location", user.getLocation());
						List<String> mlist2 = inmquery2.list();
						
						severityMap.put("inmprio2", mlist2.size());
								 
						StringBuilder inmpri3query=new StringBuilder("select changeId from CRinfo where   priority like '%3%' AND phase  IN ('Implementation','Post Implementation Review','Correct Implementation') and CURRENT_DATE >to_date(REQUESTED_DATE, 'DD/MM/YYYY HH24:MI:SS')and assignee ");
						if(user.getRoleId()==1)
							inmpri3query.append(" = '").append(user.getLastName().toUpperCase()).append(", ").append(user.getFirstName().toUpperCase()).append("'");
						else
							inmpri3query.append(" in(select UPPER(lastName||', '||firstName) from User where location= :Location)");
						Query inmquery3 = getSession().createQuery(inmpri3query.toString());
						if(user.getRoleId()==2)
						   inmquery3.setParameter("Location", user.getLocation());
						List<String> mlist3 = inmquery3.list();
						
						severityMap.put("inmprio3", mlist3.size());
					
						StringBuilder inmpri4query=new StringBuilder("select changeId from CRinfo where   priority like '%4%' AND phase  IN ('Implementation','Post Implementation Review','Correct Implementation') and CURRENT_DATE >to_date(REQUESTED_DATE, 'DD/MM/YYYY HH24:MI:SS')and assignee ");
						if(user.getRoleId()==1)
							inmpri4query.append(" = '").append(user.getLastName().toUpperCase()).append(", ").append(user.getFirstName().toUpperCase()).append("'");
						else
							inmpri4query.append(" in(select UPPER(lastName||', '||firstName) from User where location= :Location)");
						Query inmquery4 = getSession().createQuery(inmpri4query.toString());
						if(user.getRoleId()==2)
						  inmquery4.setParameter("Location", user.getLocation());
						List<String> mlist4 = inmquery4.list();
						
						severityMap.put("inmprio4", mlist4.size());
						
				
				
						return severityMap;
			}
		public LindeMap getclosedSLAdetails(User user) 
		
		{
					LindeMap severityMap = new LindeMap();
					
					//for CR met
					
					StringBuilder clopri1query=new StringBuilder("select changeId from CRinfo where   priority like '%1%' AND phase='Closed' and  to_date(REQUESTED_DATE, 'DD/MM/YYYY HH24:MI:SS') > CURRENT_DATE and assignee ");//in(select UPPER(lastName||', '||firstName) from User where location= :Location)
					
					if(user.getRoleId()==1)
						clopri1query.append(" = '").append(user.getLastName().toUpperCase()).append(", ").append(user.getFirstName().toUpperCase()).append("'");
					else
						clopri1query.append(" in(select UPPER(lastName||', '||firstName) from User where location= :Location)");
					
					Query clmquery1 = getSession().createQuery(clopri1query.toString());
					
					if(user.getRoleId()==2)
					  clmquery1.setParameter("Location", user.getLocation());
					
					List<String> mlist1 = clmquery1.list();
					
					severityMap.put("clmprio1",mlist1.size());
				
					StringBuilder clopri2query=new StringBuilder("select changeId from CRinfo where   priority like '%2%' AND phase='Closed' and  to_date(REQUESTED_DATE, 'DD/MM/YYYY HH24:MI:SS') > CURRENT_DATE and assignee ");
					if(user.getRoleId()==1)
						clopri2query.append(" = '").append(user.getLastName().toUpperCase()).append(", ").append(user.getFirstName().toUpperCase()).append("'");
					else
						clopri2query.append(" in(select UPPER(lastName||', '||firstName) from User where location= :Location)");
					
						Query clmquery2 = getSession().createQuery(clopri2query.toString());
						if(user.getRoleId()==2)
						  clmquery2.setParameter("Location", user.getLocation());
						List<String> mlist2 = clmquery2.list();
						
						severityMap.put("clmprio2", mlist2.size());
						
						StringBuilder clopri3query=new StringBuilder("select changeId from CRinfo where   priority like '%3%' AND phase='Closed' and  to_date(REQUESTED_DATE, 'DD/MM/YYYY HH24:MI:SS') > CURRENT_DATE and assignee ");
						if(user.getRoleId()==1)
							clopri3query.append(" = '").append(user.getLastName().toUpperCase()).append(", ").append(user.getFirstName().toUpperCase()).append("'");
						else
							clopri3query.append(" in(select UPPER(lastName||', '||firstName) from User where location= :Location)");
						
						Query clmquery3 = getSession().createQuery(clopri3query.toString());
						if(user.getRoleId()==2)
					    	clmquery3.setParameter("Location", user.getLocation());
						List<String> mlist3 = clmquery3.list();
						
						severityMap.put("clmprio3", mlist3.size());
						
						StringBuilder clopri4query=new StringBuilder("select changeId from CRinfo where   priority like '%4%' AND phase='Closed' and  to_date(REQUESTED_DATE, 'DD/MM/YYYY HH24:MI:SS') > CURRENT_DATE and assignee ");
						if(user.getRoleId()==1)
							clopri4query.append(" = '").append(user.getLastName().toUpperCase()).append(", ").append(user.getFirstName().toUpperCase()).append("'");
						else
							clopri4query.append(" in(select UPPER(lastName||', '||firstName) from User where location= :Location)");
						
						Query clmquery4 = getSession().createQuery(clopri4query.toString());
						if(user.getRoleId()==2)
						   clmquery4.setParameter("Location", user.getLocation());
						List<String> mlist4 = clmquery4.list();
						
						severityMap.put("clmprio4", mlist4.size());
						
						int totalMetcr=mlist1.size()+mlist2.size()+mlist3.size()+mlist4.size();
				
						
						// for CR not met
						
						StringBuilder clonpri1query=new StringBuilder("select changeId from CRinfo where   priority like '%1%' AND phase not in ('Closed') and  to_date(REQUESTED_DATE, 'DD/MM/YYYY HH24:MI:SS') < CURRENT_DATE and assignee ");
						if(user.getRoleId()==1)
							clonpri1query.append(" = '").append(user.getLastName().toUpperCase()).append(", ").append(user.getFirstName().toUpperCase()).append("'");
						else
							clonpri1query.append(" in(select UPPER(lastName||', '||firstName) from User where location= :Location)");
						
						
						Query clnmquery1 = getSession().createQuery(clonpri1query.toString());
						if(user.getRoleId()==2)
						  clnmquery1.setParameter("Location", user.getLocation());
						List<String> nmlist1 = clnmquery1.list();
						
						severityMap.put("clnmprio1", nmlist1.size());
			
						StringBuilder clonpri2query=new StringBuilder("select changeId from CRinfo where   priority like '%2%' AND phase not in ('Closed') and  to_date(REQUESTED_DATE, 'DD/MM/YYYY HH24:MI:SS') < CURRENT_DATE and assignee ");
						if(user.getRoleId()==1)
							clonpri2query.append(" = '").append(user.getLastName().toUpperCase()).append(", ").append(user.getFirstName().toUpperCase()).append("'");
						else
							clonpri2query.append(" in(select UPPER(lastName||', '||firstName) from User where location= :Location)");
						
						Query clnmquery2 = getSession().createQuery(clonpri2query.toString());
						if(user.getRoleId()==2)
						  clnmquery2.setParameter("Location", user.getLocation());
						List<String> nmlist2 = clnmquery2.list();
						
						severityMap.put("clnmprio2", nmlist2.size());
						
						StringBuilder clonpri3query=new StringBuilder("select changeId from CRinfo where   priority like '%3%' AND phase not in ('Closed') and  to_date(REQUESTED_DATE, 'DD/MM/YYYY HH24:MI:SS') < CURRENT_DATE and assignee ");
						if(user.getRoleId()==1)
							clonpri3query.append(" = '").append(user.getLastName().toUpperCase()).append(", ").append(user.getFirstName().toUpperCase()).append("'");
						else
							clonpri3query.append(" in(select UPPER(lastName||', '||firstName) from User where location= :Location)");
						
						Query clnmquery3 = getSession().createQuery(clonpri3query.toString());
						if(user.getRoleId()==2)
						   clnmquery3.setParameter("Location", user.getLocation());
						List<String> nmlist3 = clnmquery3.list();
						
						severityMap.put("clnmprio3", nmlist3.size());
						
						StringBuilder clonpri4query=new StringBuilder("select changeId from CRinfo where   priority like '%4%' AND phase not in ('Closed') and  to_date(REQUESTED_DATE, 'DD/MM/YYYY HH24:MI:SS') < CURRENT_DATE and assignee ");
						if(user.getRoleId()==1)
							clonpri4query.append(" = '").append(user.getLastName().toUpperCase()).append(", ").append(user.getFirstName().toUpperCase()).append("'");
						else
							clonpri4query.append(" in(select UPPER(lastName||', '||firstName) from User where location= :Location)");
						
						Query clnmquery4 = getSession().createQuery(clonpri4query.toString());
						if(user.getRoleId()==2)
						  clnmquery4.setParameter("Location", user.getLocation());
						List<String> nmlist4 = clnmquery4.list();
						
						severityMap.put("clnmprio4", nmlist4.size());
						
						int toatlNotMetcr=nmlist1.size()+nmlist2.size()+nmlist3.size()+nmlist4.size();
						 
						 
						int toatlCR=toatlNotMetcr+totalMetcr;
						
						//for met CRS
					 
						 
						 if(mlist1.size()!=0)
						 severityMap.put("perprio1", java.lang.Math.ceil((mlist1.size()*100)/toatlCR));
						 if(mlist2.size()!=0)
							 severityMap.put("perprio2", java.lang.Math.ceil((mlist2.size()*100)/toatlCR));
						 if(mlist3.size()!=0)
							 severityMap.put("perprio3", java.lang.Math.ceil((mlist3.size()*100)/toatlCR));
						 if(mlist4.size()!=0)
							 severityMap.put("perprio4", java.lang.Math.ceil((mlist4.size()*100)/toatlCR));
						 
						 //for percentage not met
						 if(nmlist1.size()!=0)
						 severityMap.put("nperprio1", java.lang.Math.ceil((nmlist1.size()*100)/toatlCR));
						 if(nmlist2.size()!=0)
							 severityMap.put("nperprio2", java.lang.Math.ceil((nmlist2.size()*100)/toatlCR));
						 if(nmlist3.size()!=0)
							 severityMap.put("nperprio3", java.lang.Math.ceil((nmlist3.size()*100)/toatlCR));
						 if(nmlist4.size()!=0)
							 severityMap.put("nperprio4", java.lang.Math.ceil((nmlist4.size()*100)/toatlCR));
						
			
			return severityMap;
		
		}
		public LindeMap getPMOpenSLAdetails(String location) 
		
		{
			/// for CR met
			LindeMap severityMap = new LindeMap();
			
			String mpri1query="select changeId from CRinfo where   priority like '%1%' AND phase NOT in ('Accept RfC & Prepare Approval','Validate & Assign','CI: Validate & Assign') and CURRENT_DATE-to_date(open_date, 'DD/MM/YYYY HH24:MI:SS')<4 and assignee in(select UPPER(lastName||', '||firstName) from User where location= :Location)";
			Query mquery1 = getSession().createQuery(mpri1query).setParameter("Location", location);
			List<String> mlist1 = mquery1.list();	
			
						severityMap.put(location+"1", mlist1.size());
				
				String mpri2query="select changeId from CRinfo where   priority like '%2%' AND phase NOT in ('Accept RfC & Prepare Approval','Validate & Assign','CI: Validate & Assign') and CURRENT_DATE-to_date(open_date, 'DD/MM/YYYY HH24:MI:SS')<4 and assignee in(select UPPER(lastName||', '||firstName) from User where location= :Location)";
						Query mquery2 = getSession().createQuery(mpri2query).setParameter("Location", location);
						List<String> mlist2 = mquery2.list();					
						
						severityMap.put(location+"2", mlist2.size());
						
				
				String mpri3query="select changeId from CRinfo where   priority like '%3%' AND phase NOT in ('Accept RfC & Prepare Approval','Validate & Assign','CI: Validate & Assign') and CURRENT_DATE-to_date(open_date, 'DD/MM/YYYY HH24:MI:SS')<17 And assignee in(select UPPER(lastName||', '||firstName) from User where location= :Location)";
				 		Query mquery3 = getSession().createQuery(mpri3query).setParameter("Location", location);
				 		List<String> mlist3 = mquery3.list();
				 		
				 		severityMap.put(location+"3", mlist3.size());
				 		
					 
				String mpri4query="select changeId from CRinfo where   priority like '%4%' AND phase NOT in ('Accept RfC & Prepare Approval','Validate & Assign','CI: Validate & Assign') and CURRENT_DATE-to_date(open_date, 'DD/MM/YYYY HH24:MI:SS')<17 And assignee in(select UPPER(lastName||', '||firstName) from User where location= :Location) ";
						Query mquery4 = getSession().createQuery(mpri4query).setParameter("Location", location);
						List<String> mlist4 = mquery4.list();
						
						severityMap.put(location+"4", mlist4.size());
						
						
						int totalMetcr=mlist1.size()+mlist2.size()+mlist3.size()+mlist4.size();
						
						severityMap.put(location+"5", totalMetcr);
						
			//for CR not met 		 
						
						String pri1query="select changeId from CRinfo where   priority like '%1%' AND phase in ('Accept RfC & Prepare Approval','Validate & Assign','CI: Validate & Assign') and CURRENT_DATE-to_date(open_date, 'DD/MM/YYYY HH24:MI:SS')>4 and assignee in(select UPPER(lastName||', '||firstName) from User where location= :Location)";
						Query query1 = getSession().createQuery(pri1query).setParameter("Location", location);
						List<String> list1 = query1.list();
						
						severityMap.put(location+"n1", list1.size());
						
						
				String pri2query="select changeId from CRinfo where   priority like '%2%' AND phase in ('Accept RfC & Prepare Approval','Validate & Assign','CI: Validate & Assign') and CURRENT_DATE-to_date(open_date, 'DD/MM/YYYY HH24:MI:SS')>4 and assignee in(select UPPER(lastName||', '||firstName) from User where location= :Location)";
						Query query2 = getSession().createQuery(pri2query).setParameter("Location", location);
						
						List<String> list2 = query2.list();
						
						severityMap.put(location+"n2", list2.size());
					 
				String pri3query="select changeId from CRinfo where   priority like '%3%' AND phase in ('Accept RfC & Prepare Approval','Validate & Assign','CI: Validate & Assign') and CURRENT_DATE-to_date(open_date, 'DD/MM/YYYY HH24:MI:SS')>17 and assignee in(select UPPER(lastName||', '||firstName) from User where location= :Location)";
						Query query3 = getSession().createQuery(pri3query).setParameter("Location", location);
						List<String> list3 = query3.list();
						
						severityMap.put(location+"n3", list3.size());
						
						String pri4query="select changeId from CRinfo where   priority like '%4%' AND phase in ('Accept RfC & Prepare Approval','Validate & Assign','CI: Validate & Assign') and CURRENT_DATE-to_date(open_date, 'DD/MM/YYYY HH24:MI:SS')>17 and assignee in(select UPPER(lastName||', '||firstName) from User where location= :Location)";
						Query query4 = getSession().createQuery(pri4query).setParameter("Location", location);
						List<String> list4 = query4.list();
						
						
						severityMap.put(location+"n4",list4.size());
						
						int totalNMetcr=list1.size()+list2.size()+list3.size()+list4.size();
						//severityMap.put(location+"n5", totalNMetcr+totalMetcr);
						severityMap.put(location+"n5", totalNMetcr);
						
						if(mlist1.size()!=0)
						severityMap.put(location+"p1",java.lang.Math.ceil((mlist1.size()*100)/(totalNMetcr+totalMetcr)));
						if(mlist2.size()!=0)
						  severityMap.put(location+"p2",java.lang.Math.ceil((mlist2.size()*100)/(totalNMetcr+totalMetcr)));
						if(mlist3.size()!=0)
						  severityMap.put(location+"p3",java.lang.Math.ceil((mlist3.size()*100)/(totalNMetcr+totalMetcr)));
						if(mlist4.size()!=0)
						  severityMap.put(location+"p4",java.lang.Math.ceil((mlist4.size()*100)/(totalNMetcr+totalMetcr)));
						
						if(list1.size()!=0)
					    	severityMap.put(location+"np1",java.lang.Math.ceil((list1.size()*100)/(totalNMetcr+totalMetcr)));
						if(list2.size()!=0)
						   severityMap.put(location+"np2",java.lang.Math.ceil((list2.size()*100)/(totalNMetcr+totalMetcr)));
						if(list3.size()!=0)
						   severityMap.put(location+"np3",java.lang.Math.ceil((list3.size()*100)/(totalNMetcr+totalMetcr)));
						
						if(list4.size()!=0)
					    	severityMap.put(location+"np4",java.lang.Math.ceil((list4.size()*100)/(totalNMetcr+totalMetcr)));
					
						return severityMap;
		}

public LindeMap getPMClosedSLAdetails(String location) 
		
		{
	
				LindeMap severityMap = new LindeMap();
				
				String mpri1query="select changeId from CRinfo where   priority like '%1%' AND phase not in ('Closed') and  to_date(REQUESTED_DATE, 'DD/MM/YYYY HH24:MI:SS') > CURRENT_DATE and assignee in(select UPPER(lastName||', '||firstName) from User where location= :Location)";
				Query mquery1 = getSession().createQuery(mpri1query).setParameter("Location", location);
				List<String> mlist1 = mquery1.list();
				
				severityMap.put(location+"cl1", mlist1.size());

				String mpri2query="select changeId from CRinfo where   priority like '%2%' AND phase not in ('Closed') and  to_date(REQUESTED_DATE, 'DD/MM/YYYY HH24:MI:SS') > CURRENT_DATE and assignee in(select UPPER(lastName||', '||firstName) from User where location= :Location)";
				Query mquery2 = getSession().createQuery(mpri2query).setParameter("Location", location);
				List<String> mlist2 = mquery2.list();					
	
				severityMap.put(location+"cl2", mlist2.size());
	

				String mpri3query="select changeId from CRinfo where   priority like '%3%' AND phase not in ('Closed') and  to_date(REQUESTED_DATE, 'DD/MM/YYYY HH24:MI:SS') > CURRENT_DATE and assignee in(select UPPER(lastName||', '||firstName) from User where location= :Location)";
				Query mquery3 = getSession().createQuery(mpri3query).setParameter("Location", location);
				List<String> mlist3 = mquery3.list();
		
				severityMap.put(location+"cl3", mlist3.size());
		
 
				String mpri4query="select changeId from CRinfo where   priority like '%4%' AND phase not in ('Closed') and  to_date(REQUESTED_DATE, 'DD/MM/YYYY HH24:MI:SS') > CURRENT_DATE and assignee in(select UPPER(lastName||', '||firstName) from User where location= :Location)";
				Query mquery4 = getSession().createQuery(mpri4query).setParameter("Location", location);
				List<String> mlist4 = mquery4.list();
	
				severityMap.put(location+"cl4", mlist4.size());

	
				int totalMetcr=mlist1.size()+mlist2.size()+mlist3.size()+mlist4.size();
				severityMap.put(location+"cl5", totalMetcr);
	
				//for CR not met 	
				
				String pri1query="select changeId from CRinfo where   priority like '%1%' AND phase not in ('Closed') and  to_date(REQUESTED_DATE, 'DD/MM/YYYY HH24:MI:SS') < CURRENT_DATE and assignee in(select UPPER(lastName||', '||firstName) from User where location= :Location)";
				Query query1 = getSession().createQuery(pri1query).setParameter("Location", location);
				List<String> list1 = query1.list();
	
				severityMap.put(location+"ncl1", list1.size());
	
	
				String pri2query="select changeId from CRinfo where   priority like '%2%' AND phase not in ('Closed') and  to_date(REQUESTED_DATE, 'DD/MM/YYYY HH24:MI:SS') < CURRENT_DATE and assignee in(select UPPER(lastName||', '||firstName) from User where location= :Location)";
				Query query2 = getSession().createQuery(pri2query).setParameter("Location", location);
				List<String> list2 = query2.list();
	
				severityMap.put(location+"ncl2", list2.size());
 
				String pri3query="select changeId from CRinfo where   priority like '%3%' AND phase not in ('Closed') and  to_date(REQUESTED_DATE, 'DD/MM/YYYY HH24:MI:SS') < CURRENT_DATE and assignee in(select UPPER(lastName||', '||firstName) from User where location= :Location)";
				Query query3 = getSession().createQuery(pri3query).setParameter("Location", location);
				List<String> list3 = query3.list();
	
				severityMap.put(location+"ncl3", list3.size());
	 
				String pri4query="select changeId from CRinfo where   priority like '%4%' AND phase not in ('Closed') and  to_date(REQUESTED_DATE, 'DD/MM/YYYY HH24:MI:SS') < CURRENT_DATE and assignee in(select UPPER(lastName||', '||firstName) from User where location= :Location)";
				Query query4 = getSession().createQuery(pri4query).setParameter("Location", location);
				List<String> list4 = query4.list();
		
				severityMap.put(location+"ncl4", list4.size());
				
				int totalNMetcr=list1.size()+list2.size()+list3.size()+list4.size();
				//severityMap.put(location+"ncl5", totalNMetcr+totalMetcr);
				severityMap.put(location+"ncl5", totalNMetcr);
	
				if(mlist1.size()!=0)
				 severityMap.put(location+"pcl1",java.lang.Math.ceil((mlist1.size()*100)/(totalNMetcr+totalMetcr)));
				if(mlist2.size()!=0)
				severityMap.put(location+"pcl2",java.lang.Math.ceil((mlist2.size()*100)/(totalNMetcr+totalMetcr)));
				if(mlist3.size()!=0)
				severityMap.put(location+"pcl3",java.lang.Math.ceil((mlist3.size()*100)/(totalNMetcr+totalMetcr)));
				if(mlist4.size()!=0)
				severityMap.put(location+"pcl4",java.lang.Math.ceil((mlist4.size()*100)/(totalNMetcr+totalMetcr)));
			
				if(list1.size()!=0)
				 severityMap.put(location+"pncl1",java.lang.Math.ceil((list1.size()*100)/(totalNMetcr+totalMetcr)));
				if(list2.size()!=0)
				severityMap.put(location+"pncl2",java.lang.Math.ceil((list2.size()*100)/(totalNMetcr+totalMetcr)));
				if(list3.size()!=0)
				severityMap.put(location+"pncl3",java.lang.Math.ceil((list3.size()*100)/(totalNMetcr+totalMetcr)));
				if(list4.size()!=0)
				severityMap.put(location+"pncl4",java.lang.Math.ceil((list4.size()*100)/(totalNMetcr+totalMetcr)));
				
			return severityMap;
	
		}

		public LindeMap getOpenUserSLAdetails(String  Username) 

		{
				LindeMap severityMap = new LindeMap();
	
				String mpri1query="select changeId from CRinfo where   priority like '%1%' AND phase NOT in ('Accept RfC & Prepare Approval','Validate & Assign','CI: Validate & Assign') and CURRENT_DATE-to_date(open_date, 'DD/MM/YYYY HH24:MI:SS')<4 and assignee= :Name ";
				Query mquery1 = getSession().createQuery(mpri1query);
				mquery1.setParameter("Name", Username);
				List<String> mlist1 = mquery1.list();
				
				severityMap.put("mprio1", mlist1.size());

				String mpri2query="select changeId from CRinfo where   priority like '%2%' AND phase NOT in ('Accept RfC & Prepare Approval','Validate & Assign','CI: Validate & Assign') and CURRENT_DATE-to_date(open_date, 'DD/MM/YYYY HH24:MI:SS')<4 and assignee= :Name ";
				Query mquery2 = getSession().createQuery(mpri2query);
				mquery2.setParameter("Name", Username);
				List<String> mlist2 = mquery2.list();					
	
				severityMap.put("mprio2", mlist2.size());

				String mpri3query="select changeId from CRinfo where   priority like '%3%' AND phase NOT in ('Accept RfC & Prepare Approval','Validate & Assign','CI: Validate & Assign') and CURRENT_DATE-to_date(open_date, 'DD/MM/YYYY HH24:MI:SS')<17 and assignee= :Name ";
				Query mquery3 = getSession().createQuery(mpri3query);
				mquery3.setParameter("Name", Username);
				List<String> mlist3 = mquery3.list();
		
				severityMap.put("mprio3", mlist3.size());
 
				String mpri4query="select changeId from CRinfo where   priority like '%4%' AND phase in ('Accept RfC & Prepare Approval','Validate & Assign','CI: Validate & Assign') and assignee= :Name";
				Query mquery4 = getSession().createQuery(mpri4query);
				mquery4.setParameter("Name", Username);
				List<String> mlist4 = mquery4.list();
	
				severityMap.put("mprio4", mlist4.size());
	
				int totalMetcr=mlist1.size()+mlist2.size()+mlist3.size()+mlist4.size();
	
				//for CR not met 		 
	
				String pri1query="select changeId from CRinfo where   priority like '%1%' AND phase in ('Accept RfC & Prepare Approval','Validate & Assign','CI: Validate & Assign') and CURRENT_DATE-to_date(open_date, 'DD/MM/YYYY HH24:MI:SS')>4 and assignee= :Name";
				Query query1 = getSession().createQuery(pri1query);
				query1.setParameter("Name", Username);
				List<String> list1 = query1.list();
				
				severityMap.put("nmprio1", list1.size());
				
	
				String pri2query="select changeId from CRinfo where   priority like '%2%' AND phase in ('Accept RfC & Prepare Approval','Validate & Assign','CI: Validate & Assign') and CURRENT_DATE-to_date(open_date, 'DD/MM/YYYY HH24:MI:SS')>4 and assignee= :Name";
				Query query2 = getSession().createQuery(pri2query);
				query2.setParameter("Name", Username);
				List<String> list2 = query2.list();
				
				severityMap.put("nmprio2", list2.size());
 
				String pri3query="select changeId from CRinfo where   priority like '%3%' AND phase in ('Accept RfC & Prepare Approval','Validate & Assign','CI: Validate & Assign') and CURRENT_DATE-to_date(open_date, 'DD/MM/YYYY HH24:MI:SS')>17 and assignee= :Name";
				Query query3 = getSession().createQuery(pri3query);
				query3.setParameter("Name", Username);
				List<String> list3 = query3.list();
	
				severityMap.put("nmprio3", list3.size());
				
				String pri4query="select changeId from CRinfo where   priority like '%4%' AND phase in ('Accept RfC & Prepare Approval','Validate & Assign','CI: Validate & Assign') and CURRENT_DATE-to_date(open_date, 'DD/MM/YYYY HH24:MI:SS')>17 and assignee= :Name";
				Query query4 = getSession().createQuery(pri4query);
				query4.setParameter("Name", Username);
				List<String> list4 = query4.list();
				
				severityMap.put("nmprio4", list4.size());
	 
				return severityMap;
	
}

		public List<CRinfo> getCRSLADetailedList(String time_sla,	String type, String phase, String priority, User user,String location) {
			

			lindeLogMgr.logMessage("DEBUG", "Before call to  getCRSLADetailedList()");
			
			LindeList crInfoList =new LindeList();
			Query sqlCriteria = null;
			
			List<CRinfo> crList = new ArrayList<CRinfo>();
			try
			{
			
			String tableName = "CRinfo";
			
			
			if(type!=null && type.equals("month"))
			{
				
			  String startDate="'01-"+time_sla+"'";
			  String endDate="'25-"+time_sla+"'";
	           StringBuffer monthSqlQuery = new StringBuffer("from CRinfo crinfo where to_date(crinfo.openDate, 'DD/MM/YYYY HH24:MI:SS') BETWEEN TO_DATE(");
	           monthSqlQuery.append(startDate).append(" , 'DD-MONTH-YYYY') and  LAST_DAY(TO_DATE(").append(endDate).append(" , 'DD-MONTH-YYYY'))");
				
			    if(user.getRoleId()==1)
			    	monthSqlQuery.append("and crinfo.assignee ='").append(user.getLastName().toUpperCase()).append(", ").append(user.getFirstName().toUpperCase()).append("'");
				
				if(user.getRoleId()==2)
					monthSqlQuery.append("and crinfo.assignee in (select UPPER(users1.lastName||', '||users1.firstName) from User users1 where users1.location ='").append(user.getLocation()).append("')");
				
				if(user.getRoleId() ==3)
					monthSqlQuery.append(" and crinfo.assignee in (select UPPER(users1.lastName||', '||users1.firstName) from User users1 )");// where users1.location='").append(location).append("')");
				
				monthSqlQuery.append(" and crinfo.phase in ( '").append(phase).append("')");
				
				sqlCriteria = getSession().createQuery(monthSqlQuery.toString());
			
			}
			else if(type !=null && type.equals("sla"))
			{
				lindeLogMgr.logMessage("INFO", "type is sla");
				StringBuilder slaQuery=new StringBuilder("from CRinfo crinfo where crinfo.priority in ('");
				slaQuery.append(priority);
				slaQuery.append("') AND crinfo.phase ");
				
				if(time_sla.equalsIgnoreCase("m") && phase.equals("open"))
					slaQuery.append(" NOT");
			//	else if(time_sla.equalsIgnoreCase("nm") && phase.equals("open"))
				
				if(phase.equalsIgnoreCase("open"))
				      slaQuery.append(" in ('Accept RfC & Prepare Approval','Validate & Assign','CI: Validate & Assign') ");
				else if(phase.equalsIgnoreCase("impl"))
				      slaQuery.append(" in ('Implementation','Post Implementation Review','Correct Implementation') ");
				
			//	else if(phase.equalsIgnoreCase("closed") && time_sla.equalsIgnoreCase("m"))
			//	      slaQuery.append(" in ('Closed') ");
				else if(phase.equalsIgnoreCase("closed") )//&& time_sla.equalsIgnoreCase("nm"))
				      slaQuery.append(" not in ('Closed') ");
				
				if(!priority.equalsIgnoreCase(TrackingToolConstant.PRIORITY_4) && phase.equalsIgnoreCase("open") )
					  slaQuery.append(" and CURRENT_DATE-to_date(crinfo.openDate, 'DD/MM/YYYY HH24:MI:SS') ");
				 else if((phase.equalsIgnoreCase("impl") || phase.equalsIgnoreCase("closed")) && time_sla.equalsIgnoreCase("m") )
						slaQuery.append(" and to_date(crinfo.requestedDate, 'DD/MM/YYYY HH24:MI:SS') > CURRENT_DATE ");
				 else if(phase.equalsIgnoreCase("impl") && time_sla.equalsIgnoreCase("nm") )
						slaQuery.append(" and CURRENT_DATE > to_date(crinfo.requestedDate, 'DD/MM/YYYY HH24:MI:SS') ");
				 else if( phase.equalsIgnoreCase("closed") && time_sla.equalsIgnoreCase("nm") )
						slaQuery.append(" and to_date(crinfo.requestedDate, 'DD/MM/YYYY HH24:MI:SS') < CURRENT_DATE ");
				
				
				if(phase != null && phase.equals("open"))
				{
					if(time_sla.equalsIgnoreCase("m") && !priority.equalsIgnoreCase(TrackingToolConstant.PRIORITY_4) )
						slaQuery.append("<=");
					else if(time_sla.equalsIgnoreCase("nm") && !priority.equalsIgnoreCase(TrackingToolConstant.PRIORITY_4) )
						slaQuery.append(">");
					
					if(priority.equalsIgnoreCase(TrackingToolConstant.PRIORITY_1))
					  slaQuery.append(TrackingToolConstant.TIME_INTERVAL_PRIORITY_1);
					else if(priority.equalsIgnoreCase(TrackingToolConstant.PRIORITY_2))
						  slaQuery.append(TrackingToolConstant.TIME_INTERVAL_PRIORITY_2);
					else if(priority.equalsIgnoreCase(TrackingToolConstant.PRIORITY_3))
						  slaQuery.append(TrackingToolConstant.TIME_INTERVAL_PRIORITY_3);
					
					lindeLogMgr.logMessage("DEBUG", "priority.split().length "+priority.split(",").length);
					if( priority.split(",").length > 1 && time_sla.equalsIgnoreCase("m"))
					{
						slaQuery.append(TrackingToolConstant.MAX_TIME_INTERVAL_PRIORITY_4);
					}
					else if( priority.split(",").length > 1 && time_sla.equalsIgnoreCase("nm"))
					{
						slaQuery.append(TrackingToolConstant.MIN_TIME_INTERVAL_PRIORITY_4);
					}
				
				}
				
			//	and assignee= :Name ");
				
				 if(user.getRoleId()==1)
					 slaQuery.append("and crinfo.assignee ='").append(user.getLastName().toUpperCase()).append(", ").append(user.getFirstName().toUpperCase()).append("'");
					
					if(user.getRoleId()==2)
						slaQuery.append("and crinfo.assignee in (select UPPER(users1.lastName||', '||users1.firstName) from User users1 where users1.location ='").append(user.getLocation()).append("')");
					
					if(user.getRoleId() ==3)
						slaQuery.append(" and crinfo.assignee in (select UPPER(users1.lastName||', '||users1.firstName) from User users1 where users1.location='").append(location).append("')");
					
				//	slaQuery.append(" and crinfo.priority in ( '").append(priority).append("')");
				
					lindeLogMgr.logMessage("DEBUG", "slaQuery in getCRSLADetailedList  !!!!!!!! "+slaQuery.toString());
				sqlCriteria = getSession().createQuery(slaQuery.toString());
			
			}
		
			
			crList = (ArrayList<CRinfo>) sqlCriteria.list();
		lindeLogMgr.logMessage("DEBUG", "Size of the list is :: "+crList.size());
			
		
			for (CRinfo cRinfo : crList) {
				lindeLogMgr.logMessage("DEBUG", "cRinfo 1 ");
		       					
				long differInDays = countAge(cRinfo.getOpenDate());		
				lindeLogMgr.logMessage("DEBUG", "differInDays "+differInDays);
				
				if(TrackingToolConstant.ACCEPT_PHASE.equalsIgnoreCase(cRinfo.getPhase()) || TrackingToolConstant.CI_VALIDATE_PHASE.equalsIgnoreCase(cRinfo.getPhase())
						|| TrackingToolConstant.VALIDATE_PHASE.equalsIgnoreCase(cRinfo.getPhase()))
				{
					cRinfo.setTargetDate(calculateTargetDate(cRinfo.getOpenDate(),4));
				}
				cRinfo.setAgeing(differInDays+"");
				crInfoList.add(cRinfo);
			}
			}
			catch(Exception ex)
			{
				lindeLogMgr.logStackTrace("ERROR", "Error in getCRSLADetailedList ", ex);
			}
			
			
			return crInfoList;
		
		}

		public boolean saveCRSLAList(List<CRinfo> crlist, User user) {
			lindeLogMgr.logMessage("INFO", "Enter into saveCRList() ");
			List<CRinfo> tempLst =null;
			boolean status=false;
			try{
				for(int i=0;i<crlist.size();i++)
				{
					CRinfo crInfo = (CRinfo)crlist.get(i);
					
					CRinfo record =(CRinfo) getSession().get(CRinfo.class, crInfo.getChangeId());
					lindeLogMgr.logMessage("DEBUG", "record  "+record.getChangeId()+" record :: "+record.getPhase());
					record.setPhase(crInfo.getPhase());
					record.setAffectedCIs(crInfo.getAffectedCIs());
					getSession().update(record);
					status=true;
				}
				
			
				}
				
				catch (Exception e) {
					lindeLogMgr.logStackTrace("ERROR","Error in saveCRList is: "+e.getMessage(),e);
					status=false;
				}
			
			return status;
				
		}
	
	public List<CRinfo> getCRData(String fromDate,String toDate,String moduleName) throws ParseException
	{
			
		lindeLogMgr.logMessage("INFO", "getCRData()");
		StringBuilder sqlBuilder = new StringBuilder("from CRinfo crinfo where crinfo.affectedCIs='");
		sqlBuilder.append(moduleName).append("' and to_date(crinfo.openDate,'dd/MM/yyyy HH24:MI:ss') BETWEEN to_date('");
		sqlBuilder.append(fromDate).append("','yyyy-MM-dd HH24:MI:ss') and to_date('").append(toDate).append("','yyyy-MM-dd HH24:MI:ss')"); 
		
		List<CRinfo> crList = getSession().createQuery(sqlBuilder.toString()).list();
		
		for (CRinfo cRinfo : crList) {
			
		
		long differInDays = countAge(cRinfo.getOpenDate());		
		//lindeLogMgr.logMessage("DEBUG", "differInDays "+differInDays);
		
		if(TrackingToolConstant.ACCEPT_PHASE.equalsIgnoreCase(cRinfo.getPhase()) || TrackingToolConstant.CI_VALIDATE_PHASE.equalsIgnoreCase(cRinfo.getPhase())
				|| TrackingToolConstant.VALIDATE_PHASE.equalsIgnoreCase(cRinfo.getPhase()))
		{
			if(cRinfo.getPriority().equalsIgnoreCase(TrackingToolConstant.PRIORITY_1) || cRinfo.getPriority().equalsIgnoreCase(TrackingToolConstant.PRIORITY_2))
			  cRinfo.setTargetDate(calculateTargetDate(cRinfo.getOpenDate(),4));
			else
				cRinfo.setTargetDate(calculateTargetDate(cRinfo.getOpenDate(),17));
		}
		else
		{
			cRinfo.setTargetDate(cRinfo.getRequestedDate());
		}
			
		cRinfo.setAgeing(differInDays+"");
		}
		
		return crList;
	}
	
	
	
	
	
	/*private LindeMap getCRPriorityInfo(User user){
	
	LindeMap priorityMap = new LindeMap();
	String tableBeanName = "CRinfo";
	try {
	    StringBuffer prioritySqlQuery = new StringBuffer("select replace(crinfo.priority,' ',''),count(*) from "+tableBeanName+" crinfo where crinfo.phase in ('Accept RfC & Prepare Approval','CI: Validate & Assign','Validate & Assign','Approval','Implementation','Post Implementation Review','Correct Implementation','Closed')");
		if(user.getRoleId()==1)
			prioritySqlQuery.append("and crinfo.assignee ='").append(user.getFirstName()).append(",").append(user.getLastName()).append("'");
		
		if(user.getRoleId()==2)
			prioritySqlQuery.append("and crinfo.assignee in (select users1.firstName||','||users1.lastName from User users1 where users1.location ='").append(user.getLocation()).append("')");
		
		prioritySqlQuery.append(" group by priority  ");
	    Query query = getSession().createQuery(prioritySqlQuery.toString());
	   
	    
	    long sum = 0;
	    
	    if(user.getLocation() !=null)
    	   {
    		   priorityMap.put("Location", user.getLocation());
    	   }
	    
	    for (Iterator it = query.iterate(); it.hasNext();) {
    	   Object[] row = (Object[]) it.next();
    	   sum += (Long) row[1];
    	   
    	   String priorityStr =(String) row[0];
    	   lindeLogMgr.logMessage("DEBUG", "priorityStr "+priorityStr);
    	   String valueStr =  row[1].toString();
    	   
    	   lindeLogMgr.logMessage("DEBUG", "priorityStr value "+valueStr);
    	   
    	   if(priorityStr.contains("1-"))
    		   priorityMap.put("P1", valueStr);
    	   else if(priorityStr.contains("2-"))
    		   priorityMap.put("P2", valueStr);
    	   else if(priorityStr.contains("3-"))
    		   priorityMap.put("P3",valueStr);
    	   else if(priorityStr.contains("4-"))
    		   priorityMap.put("P4", valueStr);

	    }
    	   lindeLogMgr.logMessage("DEBUG", "Total Number of Records::: "+sum);
    	   priorityMap.put("Total", sum);

	} catch(Exception e){
		lindeLogMgr.logStackTrace("ERROR", "SQLException in getCRPriorityInfo" +e.getMessage(),e);			
	}
	
	return priorityMap;
}*/

	/*private LindeMap getCRStatus(User user){
	
	LindeMap statusMap = new LindeMap(); 
	LindeMap innerMap = new LindeMap();
	
	String tableBeanName = "CRinfo";
	try{
	     // StringBuffer SQL_QUERY = new StringBuffer(" select replace(ttinfo.status,'Work In Progress','WIP'),app.region,count(*) from "+tableObjectName+" ttinfo,Application app where ttinfo.application=app.applicationname ");
	      StringBuffer statusSqlQuery = new StringBuffer("select crinfo.phase,module.modulename,count(*) from "+tableBeanName+" crinfo,OwnerModule owner,Module module,User user where ");
	     // statusSqlQuery.append("crinfo.assignee in (user.firstName||','||user.lastName) and module.moduleid=owner.moduleId and user.userID=owner.userId");
	      if(user.getRoleId()==1)
				statusSqlQuery.append(" crinfo.assignee ='").append(user.getFirstName()).append(",").append(user.getLastName()).append("'").append("and user.userID='").append(user.getUserID()).append("'");
	      
	      if(user.getRoleId()==2)
	    	  statusSqlQuery.append(" crinfo.assignee in (user.firstName||','||user.lastName) and user.location ='").append(user.getLocation()).append("'");
	      
	      statusSqlQuery.append(" and module.moduleid = owner.moduleId and user.userID = owner.userId");
		  statusSqlQuery.append(" group by crinfo.phase , module.modulename order by crinfo.phase");
	    	   ArrayList query = (ArrayList) getSession().createQuery(statusSqlQuery.toString()).list();
	    	   Object[] temp = new Object[1];
	    	   Object[] row;
	    	   
	    	   
	    	  boolean isRowInserted =false;		    	   
	    	   long groupSum=0,total=0;
	    	   long tempothers=0;

	    	   for (int i=0;i<query.size();i++) {
		    	   row = (Object[]) query.get(i);
		    	   if(row[0].equals("Accept RfC & Prepare Approval")) 
		    	    {
		    		   row[0]="AccRfcAndPrepApp";
		    	    }
		    	   else if(row[0].equals("CI: Validate & Assign")) 
		    	    {
		    		   row[0]="CIValidAndAssign";
		    	    }
		    	   else if(row[0].equals("Validate & Assign")) 
		    	    {
		    		   row[0]="ValidAndAssign";
		    	    }
		    	   else if(row[0].equals("Post Implementation Review")) 
		    	    {
		    		   row[0]="PostImpReview";
		    	    }
		    	   else if(row[0].equals("Correct Implementation")) 
		    	    {
		    		   row[0]="CorrectImp";
		    	    }
		    	   lindeLogMgr.logMessage("DEBUG", "Values++++++++++++:::"+row[0]+"  "+row[1]+" "+row[2]);
		    	   
		    	   if (i!=0){
		    		   if(!row[0].toString().equals(temp[0].toString()))
		    		   {
		    			   innerMap = new LindeMap();
		    			   lindeLogMgr.logMessage("DEBUG", "Resetting Tempother value : ");
		    			   groupSum=0;
		    			   lindeLogMgr.logMessage("DEBUG", "Different status:"+row[0]+":temp:"+temp[0]);
		    		   }
		    	   }
		    	   groupSum +=(Long)row[2];
		    	   total+=(Long)row[2];

		    		   innerMap.put(row[1], row[2]);
		    	   innerMap.put("Total", groupSum);
		    	   
		    	   if(i==0 || !row[0].toString().equals(temp[0].toString()))
		    		    statusMap.put(row[0], innerMap);
		    	   		
		    	   		temp[0] = row[0];
	    	   }
	    	  statusMap.put("Total", total);
	    	  statusMap.put("Location", user.getLocation());
	    	  
	    	  
	    	  if(statusMap.containsKey("CIValidAndAssign"))
	    	  {
	    		  LindeMap ciValMap = (LindeMap)statusMap.get("CIValidAndAssign");
	    		  LindeMap valMap = (LindeMap)statusMap.get("ValidAndAssign");
	    		  
	    		  if(!ciValMap.isEmpty())
	    		  {
	    			  if(!valMap.isEmpty())
	    			  {
	    				  Set keySet = ciValMap.keySet();
	    				  Iterator<String> itr = keySet.iterator();
	    				  while(itr.hasNext())
	    				  {
	    					String key = (String)itr.next();
	    					long keyValue = (Long)ciValMap.get(key);
	    		 			if(valMap.containsKey(key))
							{  
								long lValue = (Long)valMap.get(key);
								 lValue+=keyValue;
								 valMap.put(key, lValue);		    					  
							} 
	    				  }
	    				  statusMap.put("ValidAndAssign", valMap);
	    			  }
	    			  else
	    			  {
	    				  statusMap.put("ValidAndAssign", ciValMap);
	    			  }
	    		  }
	    	  }
	    	  if(statusMap.containsKey("CorrectImp"))
	    	  {
	    		  LindeMap correctImpMap = (LindeMap)statusMap.get("CorrectImp");
	    		  LindeMap postImpMap = (LindeMap)statusMap.get("PostImpReview");
	    		  
	    		  if(!correctImpMap.isEmpty())
	    		  {
	    			  if(!postImpMap.isEmpty())
	    			  {
	    				  Set keySet = correctImpMap.keySet();
	    				  Iterator<String> itr = keySet.iterator();
	    				  while(itr.hasNext())
	    				  {
	    					String key = (String)itr.next();
	    					long keyValue = (Long)correctImpMap.get(key);
	    		 			if(postImpMap.containsKey(key))
							{  
								long lValue = (Long)postImpMap.get(key);
								 lValue+=keyValue;
								 postImpMap.put(key, lValue);		    					  
							} 
	    				  }
	    				  statusMap.put("PostImpReview", postImpMap);
	    			  }
	    			  else
	    			  {
	    				  statusMap.put("PostImpReview", correctImpMap);
	    			  }
	    		  }
	    	  }
	    	  
	    	   
	    	   lindeLogMgr.logMessage("DEBUG", "statusMap Map size:"+statusMap.size());

		}catch(Exception e){
			lindeLogMgr.logMessage("ERROR", "SQLException in getCRStatus : " +e.getMessage());
		}
		
	    	return statusMap;	
	
	}*/
	/*private LindeMap getCRInfoByAge(User user){
	
	LindeMap crbyAgeMap = new LindeMap();
	String tableBeanName = "CRinfo";
	try {
	    StringBuffer crAgeSqlQuery = new StringBuffer("select ageGrp.ageGroup,count(*) from "+tableBeanName+" crinfo,CRAge ageGrp where crinfo.phase in ('Accept RfC & Prepare Approval','CI: Validate & Assign','Validate & Assign','Approval','Implementation','Post Implementation Review','Correct Implementation','Closed')");
	    crAgeSqlQuery.append(" AND CURRENT_DATE BETWEEN (to_date(crinfo.openDate,'dd/MM/yyyy HH24:MI:ss') + ageGrp.rangeStart) and (to_date(crinfo.openDate,'dd/MM/yyyy HH24:MI:ss') + ageGrp.rangeEnd) ");
	   
	    //crAgeSqlQuery.append(" AND (SYSDATE - (to_date(crinfo.openDate,'dd/MM/yyyy HH24:MI:ss'))) >= ageGrp.rangeStart and (SYSDATE - (to_date(crinfo.openDate,'dd/MM/yyyy HH24:MI:ss'))) <= ageGrp.rangeEnd ");
	    
		if(user.getRoleId()==1)
			crAgeSqlQuery.append(" AND crinfo.assignee ='").append(user.getFirstName()).append(",").append(user.getLastName()).append("'");
		
		if(user.getRoleId()==2)
			crAgeSqlQuery.append("and crinfo.assignee in (select users1.firstName||','||users1.lastName from User users1 where users1.location ='").append(user.getLocation()).append("')");
			//crAgeSqlQuery.append(" crinfo.assignee in (user.firstName||','||user.lastName) and user.location ='").append(user.getLocation()).append("'");
			
	      
		crAgeSqlQuery.append(" group by ageGrp.ageGroup  ");
	    Query query = getSession().createQuery(crAgeSqlQuery.toString());
	    long sum = 0;
	    if(user.getLocation() !=null)
    	   {
	    	crbyAgeMap.put("Location", user.getLocation());
    	   }
	    lindeLogMgr.logMessage("DEBUG", "user.getLocation()  : "+user.getLocation());
	    
	    for (Iterator it = query.iterate(); it.hasNext();) {
    	   Object[] row = (Object[]) it.next();
    	   sum += (Long) row[1];
    	   crbyAgeMap.put(row[0], row[1]);
	    }
    	   lindeLogMgr.logMessage("DEBUG", "Total Number of Records::: "+sum);
    	   crbyAgeMap.put("Total", sum);

	} catch(Exception e){
		lindeLogMgr.logMessage("ERROR", "SQLException in getCRInfoByAge" +e.getMessage());			
	}
	
	return crbyAgeMap;

}



private LindeMap getModuleCRInfoByAge(User user){
		
		LindeMap crModulebyAgeMap = new LindeMap();
		String tableBeanName = "CRinfo";
		try {
		    StringBuffer crModuleAgeSqlQuery = new StringBuffer("select ageGrp.ageGroup,module.modulename,count(*) from "+tableBeanName+" crinfo,CRAge ageGrp,OwnerModule owner,Module module, User user where ");
		  //  crModuleAgeSqlQuery.append("(crinfo.assignee in (user.firstName||','||user.lastName)) and module.moduleid=owner.moduleId and user.userID=owner.userId and");
		  //  crModuleAgeSqlQuery.append(" crinfo.phase in ('Accept RfC & Prepare Approval','CI: Validate & Assign','Validate & Assign','Approval','Implementation','Post Implementation Review','Correct Implementation','Closed')");
		   
		    //crAgeSqlQuery.append(" AND (SYSDATE - (to_date(crinfo.openDate,'dd/MM/yyyy HH24:MI:ss'))) >= ageGrp.rangeStart and (SYSDATE - (to_date(crinfo.openDate,'dd/MM/yyyy HH24:MI:ss'))) <= ageGrp.rangeEnd ");
			if(user.getRoleId()==1)
				crModuleAgeSqlQuery.append(" crinfo.assignee ='").append(user.getFirstName()).append(",").append(user.getLastName()).append("'").append("and user.userID='").append(user.getUserID()).append("'");
			
			//	crModuleAgeSqlQuery.append(" crinfo.assignee ='").append(user.getFirstName()).append(",").append(user.getLastName()).append("'");
			
			if(user.getRoleId()==2)
				crModuleAgeSqlQuery.append(" crinfo.assignee in (user.firstName||','||user.lastName) and user.location ='").append(user.getLocation()).append("'");
			 
			 crModuleAgeSqlQuery.append(" AND module.moduleid=owner.moduleId and user.userID=owner.userId  ");
			 crModuleAgeSqlQuery.append(" AND crinfo.phase in ('Accept RfC & Prepare Approval','CI: Validate & Assign','Validate & Assign','Approval','Implementation','Post Implementation Review','Correct Implementation','Closed')");
			 crModuleAgeSqlQuery.append(" AND CURRENT_DATE BETWEEN (to_date(crinfo.openDate,'dd/MM/yyyy HH24:MI:ss') + ageGrp.rangeStart) and (to_date(crinfo.openDate,'dd/MM/yyyy HH24:MI:ss') + ageGrp.rangeEnd) ");
			
			crModuleAgeSqlQuery.append(" group by ageGrp.ageGroup,module.modulename order by ageGrp.ageGroup");
			ArrayList query = (ArrayList)getSession().createQuery(crModuleAgeSqlQuery.toString()).list();
    	    long sum = 0;
    	    if(user.getLocation() !=null)
	    	   {
    	    	crModulebyAgeMap.put("Location", user.getLocation());
	    	   }
    	    lindeLogMgr.logMessage("DEBUG", "user.getLocation()  : "+user.getLocation());
    	    Object[] temp = new Object[1];
    		   Object[] row;
    		   long groupSum=0,total=0;
    		   LindeMap innerMap = new LindeMap();
    		   for (int i=0;i<query.size();i++) {
 	    	   row = (Object[]) query.get(i);
 	    	  lindeLogMgr.logMessage("DEBUG", "Values++++++++++++:::"+row[0]+"  "+row[1]+" "+row[2]);
 	    	   if (i!=0){
 	    		   
 	    		   if(!row[0].toString().equals(temp[0].toString()))
 	    		   {
 	    			   innerMap = new LindeMap();
 	    			   lindeLogMgr.logMessage("DEBUG", "Resetting Tempother value : ");
 	    			   groupSum =0;
 	    			   lindeLogMgr.logMessage("DEBUG", "Differnt status:"+row[0]+":temp:"+temp[0]);
 	    		   }
 	    	   }
 	    	  		   innerMap.put(row[1], row[2]); 
 	    	    	   groupSum += (Long) row[2];	
 	    	           total += (Long) row[2];	
 	    	           innerMap.put("Total", groupSum);
 	    	       	   if(i==0 || !row[0].toString().equals(temp[0].toString()))
 	    	       		crModulebyAgeMap.put(row[0], innerMap);
     	    	   	   
 	    	       	   temp[0] = row[0];
 	    	  }
	    	   lindeLogMgr.logMessage("DEBUG", "Total Number of Records::: "+sum);
	    	   crModulebyAgeMap.put("Total", total);

		} catch(Exception e){
			lindeLogMgr.logMessage("ERROR", "SQLException in getModuleCRInfoByAge" +e.getMessage());			
		}
		return crModulebyAgeMap;
	}


*/
}
