
package com.techm.trackingtool.admin.controller;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.techm.trackingtool.admin.bean.Module;
import com.techm.trackingtool.admin.bean.TicketInfo;
import com.techm.trackingtool.admin.bean.User;
import com.techm.trackingtool.admin.dao.DeleteUserDAO;
import com.techm.trackingtool.admin.dao.TicketInfoDAO;
import com.techm.trackingtool.admin.dao.UserDetailDAO;
import com.techm.trackingtool.services.UserdetailsService;
import com.techm.trackingtool.util.LindeList;
import com.techm.trackingtool.util.LindeMap;
import com.techm.trackingtool.util.LindeToolLogManager;
import com.techm.trackingtool.util.SortObjects;

@Controller
public class HomepageReportController {
	
	private static LindeToolLogManager lindeLogMgr = new LindeToolLogManager(HomepageReportController.class.getName());
	private LindeMap userMap;
	private LindeList rolesList;
	private User user;
	LindeMap model = new LindeMap();
	String urlparameter="";
	
	@Autowired
	UserdetailsService service;
	
	
		public ModelAndView getDetails(HttpServletRequest request,
            HttpServletResponse response) {
		
		DeleteUserDAO deleteUserDAO =new DeleteUserDAO();
		String category = request.getParameter("severity");
		
		lindeLogMgr.logMessage("DEBUG", "Inside delete user/application Details------------------>"+category);
		
		return new ModelAndView("HomePageReport", "model", model);
	}
		@RequestMapping(value = { "/homepagereport.spring" }, method = RequestMethod.GET)
		public ModelAndView getTTInfo(HttpServletRequest request,
	            HttpServletResponse response) 
		{
			
			try{
			//TicketInfoDAO ticketInfoDAO=new TicketInfoDAO();
			HttpSession userSession = request.getSession(false);
			User user=(User)userSession.getAttribute("userInformation");
			lindeLogMgr.logMessage("DEBUG", "session user id is : "+user.getFirstName());
			String year="";
			userSession.setAttribute("archivalyear",(String)userSession.getAttribute("archivalyear"));
			year=(String)userSession.getAttribute("archivalyear");
			String severity=request.getParameter("severity");
			String age=request.getParameter("age");
			String status=request.getParameter("status");
			String module=request.getParameter("module");
			String month=request.getParameter("month");
			
			LindeList ttinfo=new LindeList();
		   // UserDetailDAO userDetailDAO=new UserDetailDAO();
			LindeMap users=service.getUsers();
			
			
			if(request.getParameter("severity")==null){severity="";}
			else severity=request.getParameter("severity");
				
			if(request.getParameter("age")==null){age="";}
			else age=request.getParameter("age");
			
			
			if(request.getParameter("status")==null){status="";}
			else status=request.getParameter("status");
			
			if(request.getParameter("module")==null){module="";}
			else module=request.getParameter("module");
			
			if(request.getParameter("month")==null){month="ALL";}
			else month=request.getParameter("month");
			
			if(!severity.equals("")&&age.equals("")&&status.equals("")&& module.equals("")&& !month.equals("")){
				lindeLogMgr.logMessage("DEBUG", "in severity block");
				String sev="All";
				if(severity.equals("p1")){severity="1 - Critical";sev="1 - Critical";}
				if(severity.equals("p2")){severity="2 - High";sev="2 - High";}
				if(severity.equals("p3")){severity="3 - Medium";sev="3 - Medium";}
				if(severity.equals("p4")){severity="4 - Low";sev="4 - low";}
				//if(severity.equals("Severity5")){severity="Severity 5";sev="Severity 5";}
				if(severity.equals("ALL")){severity="1 - Critical','2 - High','3 - Medium','4 - Low";}
				
			ttinfo=service.getTTDetailedInfoBySeverity(severity, "priority","",user,month,year);
			lindeLogMgr.logMessage("DEBUG", "value of TT Block"+ttinfo);
				urlparameter=sev+" Open";
				
			}
			
			else if(severity.equals("")&&!age.equals("")&&status.equals("")&& module.equals("")){
				lindeLogMgr.logMessage("DEBUG", "in age block");
				String ageing="";
				if(age.equals("grp1")){age="GRP_1";ageing="<=1wk";};
				if(age.equals("grp2")){age="GRP_2";ageing=">1wk <=3wk";};
				if(age.equals("grp3")){age="GRP_3";ageing=">3wk <=8wk";};
				if(age.equals("grp4")){age="GRP_4";ageing=">8wk";};
				if(age.equals("ALL")){age="GRP_1','GRP_2','GRP_3','GRP_4";};
				
			ttinfo=service.getTTDetailedInfoByAge(age, "age","",user,month,year);
				urlparameter=ageing+" Ageing Open";
				}
			
			
			else if(severity.equals("")&&age.equals("") && !status.equals("") && !module.equals("")){
				lindeLogMgr.logMessage("DEBUG", "in status block IN hOMEPAGE");
				String sts="";
				if(status.equals("assigned")){status="Assigned";sts="Assigned";}
				if(status.equals("pending")){status="Pending Customer";sts="Pending";}
				
				if(status.equals("closed")){status="Closed";sts="Closed";}
				if(status.equals("WIP")){status="Work In Progress";sts="Work In Progress";}
				if(module.equals("total")){module="ABAP','MM','SD','PI','PP','FI','CO','TSIM','Transport','WM','ITS Mobile','QM','PM";}
				if(status.equals("ALL")){status="Pending Customer','Assigned','Closed','Work In Progress";}
				lindeLogMgr.logMessage("DEBUG", "STATUS FILTERS IS "+status);
				ttinfo=service.getTTDetailedInfoByStatus(status, "status",module,user,month,year);
				urlparameter=sts+" "+module+" Region";
				lindeLogMgr.logMessage("DEBUG", "URL PARAMETER FILTERS IS "+urlparameter); 
				}
			
			
			else{
				lindeLogMgr.logMessage("DEBUG", "in region block");
				if(severity.equals("") && !age.equals("") && status.equals("")&& !module.equals(""))
				{
					String ageing="";
					if(age.equals("GRP_1")){ageing="<=1wk";};
					if(age.equals("GRP_2")){ageing=">1wk <=3wk";};
					if(age.equals("GRP_3")){ageing=">3wk <=8wk";};
					if(module.equals("total")){module="ABAP','MM','SD','PI','PP','FI','CO','TSIM','Transport','WM','ITS Mobile','QM','PM";}
					if(age.equals("GRP_4")){ageing=">8wk";};
					if(age.equals("all")){age="GRP_1','GRP_2','GRP_3','GRP_4";}
					
					
				ttinfo=service.getTTDetailedInfoModulewiseAge(age, "module",module,user,month,year);
				urlparameter=ageing+" Ageing "+module+" Module Open";
				}
				
			}
			
			model.put("ttInfo",ttinfo);
			model.put("urlInfo",urlparameter);
			model.put("users",users);
			lindeLogMgr.logMessage("DEBUG", "searching jsp file and forwarding");
			}
			catch (Exception e) {
			System.out.println("exception caught is "+e.getMessage());
			}
			return new ModelAndView("HomePageReport","model",model); 
		}
		
		@RequestMapping(value = { "/homepagereport.spring" }, method = RequestMethod.POST)
		public ModelAndView submitTTRemarks(HttpServletRequest request,  HttpServletResponse response) 
		{
			lindeLogMgr.logMessage("DEBUG", "inside saveTTinfo method of controller class");
			try{
			
			
			HttpSession userSession = request.getSession(false);
			User user=(User)userSession.getAttribute("userInformation");
			String year="";
			userSession.setAttribute("archivalyear",(String)userSession.getAttribute("archivalyear"));
			
			
			
			LindeList ttinfo=new LindeList();
	
			LindeMap users=service.getUsers();
			String severity=request.getParameter("severity");
			String age=request.getParameter("age");
			String status=request.getParameter("status");
			String module=request.getParameter("module");
			String month=request.getParameter("month");
			
			
		   // UserDetailDAO userDetailDAO=new UserDetailDAO();
			
			
			
			if(request.getParameter("severity")==null){severity="";}
			else severity=request.getParameter("severity");
				
			if(request.getParameter("age")==null){age="";}
			else age=request.getParameter("age");
			
			
			if(request.getParameter("status")==null){status="";}
			else status=request.getParameter("status");
			
			if(request.getParameter("module")==null){module="";}
			else module=request.getParameter("module");
			
			if(request.getParameter("month")==null){month="";}
			else month=request.getParameter("month");
			
			lindeLogMgr.logMessage("DEBUG", "Month before severity block:::::"+month);
			if(!severity.equals("")&&age.equals("")&&status.equals("")&& module.equals("")&& !month.equals("")){
				lindeLogMgr.logMessage("DEBUG", "in severity block");
				String sev="All";
				if(severity.equals("p1")){severity="1 - Critical";sev="1 - Critical";}
				if(severity.equals("p2")){severity="2 - High";sev="2 - High";}
				if(severity.equals("p3")){severity="3 - Medium";sev="3 - Medium";}
				if(severity.equals("p4")){severity="4 - Low";sev="4 - low";}
				//if(severity.equals("Severity5")){severity="Severity 5";sev="Severity 5";}
				if(severity.equals("ALL")){severity="1 - Critical','2 - High','3 - Medium','4 - Low";}
				
			ttinfo=service.getTTDetailedInfoBySeverity(severity, "priority","",user,month,year);
			lindeLogMgr.logMessage("DEBUG", "value of TT Block"+ttinfo);
				urlparameter=sev+" Open";
			}
			
			else if(severity.equals("")&&!age.equals("")&&status.equals("")&& module.equals("")&& !month.equals("")){
				lindeLogMgr.logMessage("DEBUG", "in age block");
				String ageing="";
				if(age.equals("grp1")){age="GRP_1";ageing="<=1wk";};
				if(age.equals("grp2")){age="GRP_2";ageing=">1wk <=3wk";};
				if(age.equals("grp3")){age="GRP_3";ageing=">3wk <=8wk";};
				if(age.equals("grp4")){age="GRP_4";ageing=">8wk";};
				if(age.equals("ALL")){age="GRP_1','GRP_2','GRP_3','GRP_4";};
				
			ttinfo=service.getTTDetailedInfoByAge(age, "age","",user,month,year);
				urlparameter=ageing+" Ageing Open";
				}
			
			
			else if(severity.equals("")&&age.equals("") && !status.equals("") && !module.equals("")&& !month.equals("")){
				lindeLogMgr.logMessage("DEBUG", "in status block IN hOMEPAGE");
				String sts="";
				if(status.equals("assigned")){status="Assigned";sts="Assigned";}
				if(status.equals("pending")){status="Pending Customer";sts="Pending";}
				
				if(status.equals("closed")){status="Closed";sts="Closed";}
				if(status.equals("WIP")){status="Work In Progress";sts="Work In Progress";}
				if(module.equals("total")){module="ABAP','MM','SD','PI','PP','FI','CO','TSIM','Transport','WM','ITS Mobile','QM','PM";}
				if(status.equals("ALL")){status="Pending Customer','Assigned','Closed','Work In Progress";}
				lindeLogMgr.logMessage("DEBUG", "STATUS FILTERS IS "+status);
				ttinfo=service.getTTDetailedInfoByStatus(status, "status",module,user,month,year);
				urlparameter=sts+" "+module+" Region";
				lindeLogMgr.logMessage("DEBUG", "URL PARAMETER FILTERS IS "+urlparameter); 
				}
			
			
			else{
				lindeLogMgr.logMessage("DEBUG", "in region block");
				if(severity.equals("") && !age.equals("") && status.equals("")&& !module.equals("")&& !month.equals(""))
				{
					String ageing="";
					if(age.equals("GRP_1")){ageing="<=1wk";};
					if(age.equals("GRP_2")){ageing=">1wk <=3wk";};
					if(age.equals("GRP_3")){ageing=">3wk <=8wk";};
					if(module.equals("total")){module="ABAP','MM','SD','PI','PP','FI','CO','TSIM','Transport','WM','ITS Mobile','QM','PM";}
					if(age.equals("GRP_4")){ageing=">8wk";};
					if(age.equals("all")){age="GRP_1','GRP_2','GRP_3','GRP_4";}
					
					
				ttinfo=service.getTTDetailedInfoModulewiseAge(age, "module",module,user,month,year);
				urlparameter=ageing+" Ageing "+module+" Module Open";
				}
				
			}
			
			
			
			
			
			
			
			
			LindeList ttList_temp=ttinfo;
			LindeList ttList=new LindeList();
			for(int i=0;i<ttList_temp.size();i++){
			
				System.out.println("in getting efforts and remarks");
				
				TicketInfo ticketInfo=(TicketInfo)ttList_temp.get(i);
				
				String ttstatus=(String)request.getParameter("status_"+ticketInfo.getIncidentID())!=null?(String)request.getParameter("status_"+ticketInfo.getIncidentID()):"";
				lindeLogMgr.logMessage("DEBUG", "Hello value of ttstatus is:::: "+ttstatus);	
				
				if(ttstatus!=null)
				{
					Date currentDate =new Date();
					if(ttstatus.equals("Closed"))
					{
						ticketInfo.setStatus(ttstatus);
						ticketInfo.setCloseTime(currentDate.toString());
					}
					else{
					
						ticketInfo.setStatus(ttstatus);
					}
					
					lindeLogMgr.logMessage("DEBUG", "inside saveTTinfo method inside for loop of controller class "+ttstatus);	
				}
				ttList.add(ticketInfo);
				lindeLogMgr.logMessage("DEBUG", "inside saveTTinfo method of controller class"+ticketInfo);
			}
			
			LindeList ttList_final=service.saveTTInfo(ttList);
			
			if(!severity.equals("")&&age.equals("")&&status.equals("")&& module.equals("")&& !month.equals("")){
				lindeLogMgr.logMessage("DEBUG", "in severity block");
				String sev="All";
				if(severity.equals("p1")){severity="1 - Critical";sev="1 - Critical";}
				if(severity.equals("p2")){severity="2 - High";sev="2 - High";}
				if(severity.equals("p3")){severity="3 - Medium";sev="3 - Medium";}
				if(severity.equals("p4")){severity="4 - Low";sev="4 - low";}
				//if(severity.equals("Severity5")){severity="Severity 5";sev="Severity 5";}
				if(severity.equals("ALL")){severity="1 - Critical','2 - High','3 - Medium','4 - Low";}
				
			ttinfo=service.getTTDetailedInfoBySeverity(severity, "priority","",user,month,year);
			lindeLogMgr.logMessage("DEBUG", "value of TT Block"+ttinfo);
				urlparameter=sev+" Open";
			}
			
			else if(severity.equals("")&&!age.equals("")&&status.equals("")&& module.equals("")&& !month.equals("")){
				lindeLogMgr.logMessage("DEBUG", "in age block");
				String ageing="";
				if(age.equals("grp1")){age="GRP_1";ageing="<=1wk";};
				if(age.equals("grp2")){age="GRP_2";ageing=">1wk <=3wk";};
				if(age.equals("grp3")){age="GRP_3";ageing=">3wk <=8wk";};
				if(age.equals("grp4")){age="GRP_4";ageing=">8wk";};
				if(age.equals("ALL")){age="GRP_1','GRP_2','GRP_3','GRP_4";};
				
			ttinfo=service.getTTDetailedInfoByAge(age, "age","",user,month,year);
				urlparameter=ageing+" Ageing Open";
				}
			
			
			else if(severity.equals("")&&age.equals("") && !status.equals("") && !module.equals("")&& !month.equals("")){
				lindeLogMgr.logMessage("DEBUG", "in status block IN hOMEPAGE");
				String sts="";
				if(status.equals("assigned")){status="Assigned";sts="Assigned";}
				if(status.equals("pending")){status="Pending Customer";sts="Pending";}
				
				if(status.equals("closed")){status="Closed";sts="Closed";}
				if(status.equals("WIP")){status="Work In Progress";sts="Work In Progress";}
				if(module.equals("total")){module="ABAP','MM','SD','PI','PP','FI','CO','TSIM','Transport','WM','ITS Mobile','QM','PM";}
				if(status.equals("ALL")){status="Pending Customer','Assigned','Closed','Work In Progress";}
				lindeLogMgr.logMessage("DEBUG", "STATUS FILTERS IS "+status);
				ttinfo=service.getTTDetailedInfoByStatus(status, "status",module,user,month,year);
				urlparameter=sts+" "+module+" Region";
				lindeLogMgr.logMessage("DEBUG", "URL PARAMETER FILTERS IS "+urlparameter); 
				}
			
			
			else{
				lindeLogMgr.logMessage("DEBUG", "in region block");
				if(severity.equals("") && !age.equals("") && status.equals("")&& !module.equals("")&& !month.equals(""))
				{
					String ageing="";
					if(age.equals("GRP_1")){ageing="<=1wk";};
					if(age.equals("GRP_2")){ageing=">1wk <=3wk";};
					if(age.equals("GRP_3")){ageing=">3wk <=8wk";};
					if(module.equals("total")){module="ABAP','MM','SD','PI','PP','FI','CO','TSIM','Transport','WM','ITS Mobile','QM','PM";}
					if(age.equals("GRP_4")){ageing=">8wk";};
					if(age.equals("all")){age="GRP_1','GRP_2','GRP_3','GRP_4";}
					
					
				ttinfo=service.getTTDetailedInfoModulewiseAge(age, "module",module,user,month,year);
				urlparameter=ageing+" Ageing "+module+" Module Open";
				}
				
			}
					
			
			model.put("ttInfo",ttinfo);
			model.put("urlInfo",urlparameter);
			model.put("users",users);
			model.put("message","The Information has been Successfully Updated");
			//System.out.println("ttinfoMap value is "+ttinfo);
			}catch (Exception e) {
				System.out.println("error occurred in submitting remarks is "+e.getMessage());
				lindeLogMgr.logStackTrace("ERROR", "In Submit TT controller ", e);
			}
			return new ModelAndView("HomePageReport","model",model); 
		}
		
		
		
		
		/*	public ModelAndView getSortedTickets(HttpServletRequest request,
	            HttpServletResponse response,IncidentForm incidentForm,BindException errors)
		{
			
			TicketInfoDAO ticketInfoDAO=new TicketInfoDAO();
			HttpSession userSession = request.getSession();
			String year="";
			userSession.setAttribute("archivalyear",(String)userSession.getAttribute("archivalyear"));
			year=(String)userSession.getAttribute("archivalyear");
			String type=(String)request.getParameter("type");
			String field=(String)request.getParameter("field");
			UserDetailDAO userDetailDAO=new UserDetailDAO();
			LindeMap users=userDetailDAO.getUsers();
			
			User user=(User)userSession.getAttribute("userInformation");
			System.out.println("session user id is : "+user.getFirstName());
			String severity="";
			String age="";
			String status=request.getParameter("status");
			String region=request.getParameter("region");
			LindeList ttinfo=new LindeList();
			LindeList ttinfofinal=new LindeList();
			
			if(request.getParameter("severity")==null){severity="";}
			else severity=request.getParameter("severity");
				
			if(request.getParameter("age")==null){age="";}
			else age=request.getParameter("age");
			
			
			if(request.getParameter("status")==null){status="";}
			else status=request.getParameter("status");
			
			if(request.getParameter("region")==null){region="";}
			else region=request.getParameter("region");
			
			
			
			
			
			
			if(!severity.equals("")&&age.equals("")&&status.equals("")&& region.equals("")){
			
				String sev="All";
				if(severity.equals("Severity1")){severity="Severity 1";sev="Severity 1";}
				if(severity.equals("Severity2")){severity="Severity 2";sev="Severity 2";}
				if(severity.equals("Severity3")){severity="Severity 3";sev="Severity 3";}
				if(severity.equals("Severity4")){severity="Severity 4";sev="Seveity 4";}
				if(severity.equals("Severity5")){severity="Severity 5";sev="Severity 5";}
				if(severity.equals("ALL")){severity="Severity 1','Severity 2','Severity 3','Severity 4','Severity 5";}
				
//				ttinfo=ticketInfoDAO.getTTDetailedInfoBySeverity(severity, "severity","",user,year);
				urlparameter=sev+" Open";
			}
			
			else if(severity.equals("")&&!age.equals("")&&status.equals("")&& region.equals("")){
				//System.out.println("in age block");
				String ageing="All";
				if(age.equals("grp1")){age="GRP_1";ageing="<=1wk";};
				if(age.equals("grp2")){age="GRP_2";ageing=">1wk <=3wk";};
				if(age.equals("grp3")){age="GRP_3";ageing=">3wk <=8wk";};
				if(age.equals("grp4")){age="GRP_4";ageing=">8wk";};
				if(age.equals("ALL")){age="GRP_1','GRP_2','GRP_3','GRP_4";};
				
//				ttinfo=ticketInfoDAO.getTTDetailedInfoBySeverity(age, "age","",user,year);
				urlparameter=ageing+" Ageing Open";
				}
			
			
			else if(severity.equals("")&&age.equals("") && !status.equals("") && !region.equals("")){
				//System.out.println("in status block");
				String sts="";
				if(status.equals("pending")){status="Pending";sts="Pending";}
				if(status.equals("resolved")){status="Resolved";sts="Resolved";}
				if(status.equals("closed")){status="Closed";sts="Closed";}
				if(status.equals("WIP")){status="Work In Progress";sts="Work In Progress";}
				if(status.equals("reassigned")){status="Re-Assigned','ReAssigned";sts="ReAssigned";}
				if(status.equals("ALL")){status="Pending','Resolved','Closed','Work In Progress','Assigned','Re-Assigned','ReAssigned";}
//				ttinfo=ticketInfoDAO.getTTDetailedInfoBySeverity(status, "status",region,user,year);
				urlparameter=sts+" "+region+" Region";
				}
			
			
			else{
				//System.out.println("in region block");
				if(severity.equals("") && !age.equals("") && status.equals("")&& !region.equals(""))
				{
					String ageing="All";
					if(age.equals("grp1")){ageing="<=1wk";};
					if(age.equals("grp2")){ageing=">1wk <=3wk";};
					if(age.equals("grp3")){ageing=">3wk <=8wk";};
					if(age.equals("grp4")){ageing=">8wk";};
					
					
//				ttinfo=ticketInfoDAO.getTTDetailedInfoBySeverity(age, "region",region,user,year);
				urlparameter=ageing+" Ageing "+region+" Region Open";
				}
				
			}
			
			SortObjects sortObjects=new SortObjects(field,type);
			try{
			
			ttinfofinal=(LindeList)sortObjects.getSortedPrograms(ttinfo, field, type);
			System.out.println("type"+type);
			
			}catch (Exception e) {
				System.out.println("error is "+e.getMessage());
			}
			
			model.clear();
			model.put("ttInfo",ttinfofinal);
			model.put("urlInfo",urlparameter);
			model.put("users",users);
			
			System.out.println("ttinfoMap value is "+ttinfo);
			
			return new ModelAndView("HomePageReport","model",model); 
			
		}
*/		
}



