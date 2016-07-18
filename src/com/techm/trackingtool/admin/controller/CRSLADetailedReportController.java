package com.techm.trackingtool.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.techm.trackingtool.admin.bean.CRinfo;
import com.techm.trackingtool.admin.bean.Module;
import com.techm.trackingtool.admin.bean.User;
import com.techm.trackingtool.admin.form.CrForm;
import com.techm.trackingtool.services.CRInfoService;
import com.techm.trackingtool.services.UserdetailsService;
import com.techm.trackingtool.util.LindeMap;
import com.techm.trackingtool.util.LindeToolLogManager;
import com.techm.trackingtool.util.TrackingToolConstant;


@Controller
public class CRSLADetailedReportController {
	
	@Autowired
	CRInfoService crInfoservice;
	
	@Autowired
	UserdetailsService service;
	
	private static LindeToolLogManager logMgr = new LindeToolLogManager(CrDashboardDataController.class.getName());
	
	@RequestMapping(value = { "/crsladetailreport.spring" }, method = RequestMethod.GET)
	public ModelAndView getCRSLAReportList(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("crform") CrForm crform) 
	{
		LindeMap modelMap = new LindeMap();
		
		String urlparameter="";
		List<CRinfo> crList=null;
		
		logMgr.logMessage("INFO", "getCRSLAReportList :  RequestMethod.GET");
		try
		{
		HttpSession userSession = request.getSession();
		User user=(User)userSession.getAttribute("userInformation");
		logMgr.logMessage("DEBUG", "session user id is : "+user.getFirstName());
		String year="";
		userSession.setAttribute("archivalyear",(String)userSession.getAttribute("archivalyear"));
		
		String month=request.getParameter("month");
		String phase=request.getParameter("phase");		
		String priority=request.getParameter("priority");
		String location=request.getParameter("loc");		
		String sla=request.getParameter("sla");
		
		String module=request.getParameter("module");
		
		
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
			
			 crList=crInfoservice.getCRSLADetailedList(month, "month",phase,priority,user,location);
			urlparameter=pri+" Open";
		}
		else if(priority != null && phase != null){
			logMgr.logMessage("DEBUG", "phase "+phase);
			String pri="All";
			
			if(priority.equals("p1")){priority="1 - Critical";pri="1 - Critical";}
			if(priority.equals("p2")){priority="2 - High";pri="2 - High";}
			if(priority.equals("p3")){priority="3 - Medium";pri="3 - Medium";}
			if(priority.equals("p4")){priority="4 - Low";pri="4 - Low";}
			
			if( priority.equals("ALL"))
			{
				priority="1 - Critical','2 - High','3 - Medium','4 - Low";
			}
			
			 crList=crInfoservice.getCRSLADetailedList(sla, "sla",phase,priority,user,location);
			 if(phase !=null)
				 urlparameter=pri+" Open CRs in "+phase;
			 else 
			  urlparameter=pri+" Open CRs ";
		}
		List<Module>  moduleList =service.getModuleList();
		
		modelMap.put("crInfo",crList);
		modelMap.put("urlInfo",urlparameter);
		modelMap.put("moduleList",moduleList);
		//modelMap.put("users",users);
		
		crform.setCrList(crList);
		crform.setUrlInfo(urlparameter);
		crform.setModuleList(moduleList);
		//crform.setUsers(users);
		crform.setPageType("SLA");
		
		logMgr.logMessage("DEBUG", "searching jsp file and forwarding");
		}
		catch (Exception e) {
		System.out.println("exception caught is "+e.getMessage());
		}
		return new ModelAndView("crdetailedpagereport","crform",crform); 
		
		
	}

}
