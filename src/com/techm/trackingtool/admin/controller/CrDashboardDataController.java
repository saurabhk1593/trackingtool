package com.techm.trackingtool.admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.techm.trackingtool.admin.bean.CRinfo;
import com.techm.trackingtool.admin.bean.Module;
import com.techm.trackingtool.admin.bean.User;
import com.techm.trackingtool.admin.form.CrForm;
import com.techm.trackingtool.admin.vo.HomePageVO;
import com.techm.trackingtool.services.CRInfoService;
import com.techm.trackingtool.services.UserdetailsService;
import com.techm.trackingtool.util.LindeMap;
import com.techm.trackingtool.util.LindeToolLogManager;
import com.techm.trackingtool.util.TrackingToolConstant;

@Controller
public class CrDashboardDataController {
	
	private static LindeToolLogManager logMgr = new LindeToolLogManager(CrDashboardDataController.class.getName());

	LindeMap modelMap = new LindeMap();
	HomePageVO homeVO = new HomePageVO();
	static List<CRinfo> crlist = new ArrayList<CRinfo>();
	
	
	@Autowired
	CRInfoService crInfoservice;
	
	@Autowired
	UserdetailsService service;
	
	@RequestMapping(value = { "/crsummaryreport.spring" }, method = RequestMethod.GET)
	public ModelAndView getCRList(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("crform") CrForm crform) 
	{
		logMgr.logMessage("INFO", "getCRList :  RequestMethod.GET");  
		String urlparameter="";
		try{
		HttpSession userSession = request.getSession();
		User user=(User)userSession.getAttribute("userInformation");
		logMgr.logMessage("DEBUG", "session user id is : "+user.getFirstName());
		String year="";
		userSession.setAttribute("archivalyear",(String)userSession.getAttribute("archivalyear"));
		String priority=request.getParameter("priority");
		String age=request.getParameter("age");
		String phase=request.getParameter("phase");
		String module=request.getParameter("module");
		
		String selectedMonth = request.getParameter("selectedMonth");
		
		if(selectedMonth == null)
		{
			selectedMonth = (String)userSession.getAttribute("selectedMonth");
			crform.setSelectedMonth(selectedMonth);
		}
		
		logMgr.logMessage("DEBUG", "selectedMonth in getCRList is : "+selectedMonth);
		
		logMgr.logMessage("DEBUG", "crform.getSelectedMonth() in getCRList is : "+crform.getSelectedMonth());
		
		List<CRinfo> crList = new ArrayList<CRinfo>();
		LindeMap users=service.getUsers();
		
		
		if(request.getParameter("priority")==null)
		{
			priority="";
		}
		else priority=request.getParameter("priority");
			
		if(request.getParameter("age")==null){age="";}
		else age=request.getParameter("age");
		
		
		if(request.getParameter("phase")==null){phase="";}
		else phase=request.getParameter("phase");
		
		if(request.getParameter("module")==null){module="";}
		else module=request.getParameter("module");
		
		
		if(!priority.equals("") && age.equals("") && phase.equals("") && module.equals("")){
			logMgr.logMessage("DEBUG", "in severity block");
			String pri="All";
			if(priority.equals("p1")){priority="1 - Critical";pri="1 - Critical";}
			if(priority.equals("p2")){priority="2 - High";pri="2 - High";}
			if(priority.equals("p3")){priority="3 - Medium";pri="3 - Medium";}
			if(priority.equals("p4")){priority="4 - Low";pri="4 - Low";}
			
			if(priority.equals("ALL"))
			{
				priority="1 - Critical','2 - High','3 - Medium','4 - Low";
			}
			
			crList=crInfoservice.getCRDetailedList(priority, "priority","",user,year,selectedMonth);
			urlparameter=pri+" Open";
		}
		
		else if(priority.equals("") && !age.equals("") && phase.equals("") && module.equals("")){
			
			logMgr.logMessage("DEBUG", "in age block");
			String ageing="";
			if(age.equals("GRP_1")){age="GRP_1";ageing=">2wk <=3wk";};
			if(age.equals("GRP_2")){age="GRP_2";ageing=">3wk <=8wk";};
			if(age.equals("GRP_3")){age="GRP_3";ageing=">8wk";};
			if(age.equals("ALL")){age="GRP_1','GRP_2','GRP_3";};
			
			crList=crInfoservice.getCRDetailedList(age, "age","",user,year,selectedMonth);
			urlparameter=ageing+" Ageing Open";
			}
		
		
		else if(priority.equals("") && age.equals("") && !phase.equals("") && !module.equals("")){
			logMgr.logMessage("DEBUG", "in status block IN hOMEPAGE");
			String sts="";
			if(phase.equals("AccRfcAndPrepApp")){phase="Accept RfC & Prepare Approval";sts="Accept RfC & Prepare Approval";}
			if(phase.equals("CIValidAndAssign")){phase="CI: Validate & Assign";sts="CI: Validate & Assign";}
			if(phase.equals("Closed")){phase="Closed";sts="Closed";}
			if(phase.equals("ValidAndAssign")){phase="Validate & Assign','CI: Validate & Assign";sts="Validate & Assign";}
			if(phase.equals("PostImpReview")){phase="Post Implementation Review','Correct Implementation";sts="Post Implementation Review";}
			if(phase.equals("CorrectImp")){phase="Correct Implementation";sts="Correct Implementation";}
			if(phase.equals("Approval")){phase="Approval";sts="Approval";}
			if(phase.equals("Implementation")){phase="Implementation";sts="Implementation";}
			if(phase.equals("ALL")){
				
				phase="Accept RfC & Prepare Approval','CI: Validate & Assign','Validate & Assign','Approval','Implementation','Post Implementation Review','Correct Implementation','Closed";
				
			}
			
			if(module.equals("ALL")){
				module=TrackingToolConstant.ALL_MODULES;
			}
			logMgr.logMessage("DEBUG", "STATUS FILTERS IS "+phase);
			
			crList=crInfoservice.getCRDetailedList(phase, "phase",module,user,year,selectedMonth);
			urlparameter=sts+" "+module+" module";
			
			logMgr.logMessage("DEBUG", "URL PARAMETER FILTERS IS "+urlparameter); 
			}
	
		else{
			logMgr.logMessage("DEBUG", "in module block");
			if(priority.equals("") && !age.equals("") && phase.equals("")&& !module.equals(""))
			{
				String ageing="";
				if(age.equals("GRP_1")){ageing=">2wk <=3wk";};
				if(age.equals("GRP_2")){ageing=">3wk <=4wk";};
				if(age.equals("GRP_3")){ageing=">4wk <=8wk";};
				if(age.equals("ALL")){
					age="GRP_1','GRP_2','GRP_3";
					ageing=">2wk <=8wk";
					
				}
				if(module.equals("ALL")){
					module=TrackingToolConstant.ALL_MODULES;	
				}
				
				crList=crInfoservice.getCRDetailedList(age, "age",module,user,year,selectedMonth);
			urlparameter=ageing+" Ageing "+module+" module Open";
			}
			
		}
		
		List<Module> moduleLst = service.getModuleList();
		
		modelMap.put("crInfo",crList);
		modelMap.put("urlInfo",urlparameter);
		modelMap.put("users",users);
		
		crform.setCrList(crList);
		crform.setUrlInfo(urlparameter);
		crform.setUsers(users);
		crform.setPageType("CRPage");
		crform.setModuleList(moduleLst);
		
		if(selectedMonth!=null)
		  crform.setSelectedMonth(selectedMonth);
		
		logMgr.logMessage("DEBUG", "searching jsp file and forwarding");
		}
		catch (Exception e) {
		System.out.println("exception caught is "+e.getMessage());
		}
		return new ModelAndView("crdetailedpagereport","crform",crform); 
	}
	
	@RequestMapping(value = { "/savecrreport.spring" }, method = RequestMethod.POST)
	public ModelAndView submitCRData(@ModelAttribute("crform") CrForm crform,HttpServletRequest request,BindingResult result) 
	{
		logMgr.logMessage("INFO", " submitCRData ::");
		String priority="";
		String age="";
		String phase="";
		String priority_age_module ="";
		String type ="";
		boolean status=false;
		
		String module =null;
		
		String urlparameter="";
		if(request.getParameter("priority")==null)
			priority="";
		else 
			priority=request.getParameter("priority");
		
		if(request.getParameter("age")==null)
		    age="";
		else 
			age=request.getParameter("age");
		
		if(request.getParameter("phase")==null)
			phase="";
		else
			phase=request.getParameter("phase");
		
		logMgr.logMessage("INFO", "b4 if in  submitCRData :: "+request.getParameter("module"));
		
		if(request.getParameter("module")==null)
			module="";
		else 
			module=request.getParameter("module");
		
		logMgr.logMessage("INFO", "module in  submitCRData :: "+module);
		
		HttpSession userSession = request.getSession();
		User user=(User)userSession.getAttribute("userInformation");
		try{
		
		 crlist = crform.getCrList();
		logMgr.logMessage("DEBUG", "crlist size is in submitCRData :: "+crlist.size());
		
		logMgr.logMessage("DEBUG", "crform.getUrlInfo()  "+crform.getUrlInfo());
		
		String pageType= crform.getPageType();
		
		logMgr.logMessage("DEBUG", "pageType is   "+pageType);
		
		String month=request.getParameter("month");
		//String phase=request.getParameter("phase");
	//	CrDashboardDataController.crlist = crlist;	
		List<CRinfo> crlist = crform.getCrList();
		
		String selectedMonth = crform.getSelectedMonth();		
		logMgr.logMessage("DEBUG", "selectedMonth in submitCRData() is   "+selectedMonth);
		
		for (CRinfo cRinfo : crlist) {
			logMgr.logMessage("DEBUG", "changeid crlist   "+cRinfo.getChangeId());
			logMgr.logMessage("DEBUG", "getBriefDescription crlist   "+cRinfo.getBriefDescription());
			logMgr.logMessage("DEBUG", "getAffectedCIs crlist   "+cRinfo.getAffectedCIs());
		}
		
		if(!pageType.equals("") && pageType.equalsIgnoreCase("CRPage"))
		{
		
		if(!priority.equals("")){
			logMgr.logMessage("DEBUG", "in severity block");
			String pri="All";
			if(priority.equals("p1")){priority="1 - Critical";pri="1 - Critical";}
			if(priority.equals("p2")){priority="2 - High";pri="2 - High";}
			if(priority.equals("p3")){priority="3 - Medium";pri="3 - Medium";}
			if(priority.equals("p4")){priority="4 - Low";pri="4 - Low";}
			
			if(priority.equals("ALL"))
			{
				priority="1 - Critical','2 - High','3 - Medium','4 - Low";
			}
			priority_age_module=priority;
			type="priority";
			urlparameter=pri+" Open";
		}
		else  if(priority.equals("") && !age.equals("") && phase.equals("") && module.equals("")){
			
			logMgr.logMessage("DEBUG", "in age block");
			String ageing="";
			if(age.equals("GRP_1")){age="GRP_1";ageing=">2wk <=3wk";};
			if(age.equals("GRP_2")){age="GRP_2";ageing=">3wk <=8wk";};
			if(age.equals("GRP_3")){age="GRP_3";ageing=">8wk";};
			if(age.equals("ALL")){age="GRP_1','GRP_2','GRP_3";};
			
			priority_age_module=age;
			type="age";
			urlparameter=ageing+" Ageing Open";
			}
		else if(priority.equals("") && age.equals("") && !phase.equals("") && !module.equals("")){
			String sts="";
			if(phase.equals("AccRfcAndPrepApp")){phase="Accept RfC & Prepare Approval";sts="Accept RfC & Prepare Approval";}
			if(phase.equals("CIValidAndAssign")){phase="CI: Validate & Assign";sts="CI: Validate & Assign";}
			if(phase.equals("Closed")){phase="Closed";sts="Closed";}
			if(phase.equals("ValidAndAssign")){phase="Validate & Assign";sts="Validate & Assign";}
			if(phase.equals("PostImpReview")){phase="Post Implementation Review";sts="Post Implementation Review";}
			if(phase.equals("CorrectImp")){phase="Correct Implementation";sts="Correct Implementation";}
			if(phase.equals("Approval")){phase="Approval";sts="Approval";}
			if(phase.equals("Implementation")){phase="Implementation";sts="Implementation";}
			
			if(phase.equals("ALL"))
				phase="Accept RfC & Prepare Approval','CI: Validate & Assign','Validate & Assign','Approval','Implementation','Post Implementation Review','Correct Implementation','Closed";
			
			if(module.equals("ALL"))
				module="ABAP','MM','SD','FICO','PI";
			
			priority_age_module=phase;
			type="phase";

			urlparameter=sts+" "+module+" module";
			logMgr.logMessage("DEBUG", "URL PARAMETER FILTERS IS "+urlparameter); 
			}
	
		else{
			logMgr.logMessage("DEBUG", "in module block");
			if(priority.equals("") && !age.equals("") && phase.equals("")&& !module.equals(""))
			{
				String ageing="";
				if(age.equals("GRP_1")){ageing=">2wk <=3wk";};
				if(age.equals("GRP_2")){ageing=">3wk <=4wk";};
				if(age.equals("GRP_3")){ageing=">4wk <=8wk";};
				if(age.equals("ALL")){
					age="GRP_1','GRP_2','GRP_3";
					ageing=">2wk <=8wk";
				};
				if(module.equals("ALL")){
					module="ABAP','MM','SD','FICO','PI";	
				};
				
				
				priority_age_module=age;
				type="age";
			urlparameter=ageing+" Ageing "+module+" Module Open";
			}			
		}
			
		crlist = crInfoservice.saveCRList(crlist,priority_age_module,type,module,user,selectedMonth);
		crform.setMessage("Data Saved Succesfully");
		}
		
		else
		{
			logMgr.logMessage("INFO", "Else block in submitCR() ");
			if(month != null && phase != null){
				logMgr.logMessage("DEBUG", "month "+month);
				String pri="All";
				if(phase.equals("AcceptRfCPrepareApp")){phase=TrackingToolConstant.ACCEPT_PHASE;pri=TrackingToolConstant.ACCEPT_PHASE;}
				if(phase.equals("ValidateAss")){phase=TrackingToolConstant.VALIDATE_PHASE+"','"+TrackingToolConstant.CI_VALIDATE_PHASE;pri=TrackingToolConstant.VALIDATE_PHASE;}
				if(phase.equals("PostImplementation")){phase=TrackingToolConstant.POST_IMPL+"','"+TrackingToolConstant.CORRECT_IMPL;pri=TrackingToolConstant.POST_IMPL;}
				
				
				if(phase.equals("Total"))
				{
					phase=TrackingToolConstant.ACCEPT_PHASE+"','"+TrackingToolConstant.VALIDATE_PHASE+"','"+TrackingToolConstant.CI_VALIDATE_PHASE+"','"+TrackingToolConstant.APPROVAL+"','"+TrackingToolConstant.IMPL+"','"+TrackingToolConstant.POST_IMPL+"','"+TrackingToolConstant.CORRECT_IMPL+"','"+TrackingToolConstant.CLOSED;
				}
				
				// crList=crInfoservice.getCRSLADetailedList(month, "month",phase,user);
				urlparameter=pri+" Open";
			}
			else
			{
				
			}
			
			status = crInfoservice.saveCRSLAList(crlist,user);
			if(status)
			  crform.setMessage("Data Saved Succesfully");
			else
				crform.setMessage("Data Not Saved Succesfully");
			
		}
		
		
		LindeMap users=service.getUsers();
		List<Module> moduleLst = service.getModuleList();
		crform.setModuleList(moduleLst);
		crform.setCrList(crlist);
		crform.setUrlInfo(urlparameter);
		crform.setUsers(users);
	}
		catch(Exception e)
		{
			logMgr.logStackTrace("ERROR", "Error in submitCRData", e);
		}		
		return new ModelAndView("crdetailedpagereport","crform",crform);
		
	}
	
	@RequestMapping(value = { "/crsummaryreport.spring" }, method = RequestMethod.POST)
	public ModelAndView getCRListIntoExcel(HttpServletRequest request,
            HttpServletResponse response,@ModelAttribute("crform") CrForm crform) 
	{
		logMgr.logMessage("INFO", "getCRListIntoExcel : RequestMethod.POST");
		try
		{
		String urlparameter=crform.getUrlInfo();
		List<CRinfo> crList = crform.getCrList();
		List<Module> moduleLst = crform.getModuleList();
		
		logMgr.logMessage("DEBUG", "urlparametersdfsdf  is : "+urlparameter);
		modelMap.put("crInfo",crList);
		modelMap.put("moduleList",moduleLst);
		modelMap.put("urlInfo",urlparameter);
		//modelMap.put("users",users);
		logMgr.logMessage("DEBUG", "searching jsp file and forwarding");
		}
		catch (Exception e) {
			logMgr.logMessage("ERROR","exception in getCRListIntoExcel "+e.getMessage());
		}
		return new ModelAndView("crdetailedpagereport","model",modelMap); 
	}
	

}
