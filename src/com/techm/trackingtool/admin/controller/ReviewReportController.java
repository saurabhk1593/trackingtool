
package com.techm.trackingtool.admin.controller;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.techm.trackingtool.admin.bean.CRinfo;
import com.techm.trackingtool.admin.bean.Module;
import com.techm.trackingtool.admin.bean.TicketInfo;
import com.techm.trackingtool.admin.bean.User;
import com.techm.trackingtool.admin.dao.MapUserDAO;
import com.techm.trackingtool.admin.dao.TicketInfoDAO;
import com.techm.trackingtool.admin.form.CrForm;
import com.techm.trackingtool.admin.form.ModuleForm;
import com.techm.trackingtool.admin.form.RemarksForm;
import com.techm.trackingtool.admin.form.ReviewReportForm;
import com.techm.trackingtool.services.CRInfoService;
import com.techm.trackingtool.services.TicketInfoServices;
import com.techm.trackingtool.services.UserdetailsService;
import com.techm.trackingtool.util.LindeList;
import com.techm.trackingtool.util.LindeMap;
import com.techm.trackingtool.util.LindeToolLogManager;
import com.techm.trackingtool.util.SortObjects;

@Controller
public class ReviewReportController {
	
	private static LindeToolLogManager lindeLogMgr = new LindeToolLogManager(ReviewReportController.class.getName());
	LindeMap reportformat=new LindeMap();
	
	@Autowired
	UserdetailsService service;
	
	@Autowired
	CRInfoService crInfoservice;
	@Autowired
	TicketInfoServices incidentService;
	
	@RequestMapping(value = { "/reviewReport.spring" }, method = RequestMethod.GET)
	public ModelAndView applicationPage(HttpServletRequest req,
			HttpServletResponse res,   @ModelAttribute("reviewReportForm") ReviewReportForm reviewReportForm,BindingResult result)
		{
		
		
		lindeLogMgr.logMessage("DEBUG", "Inside applicationPage");
		
		LindeList userlist=new LindeList();
		LindeList applicationlist=new LindeList();
		LindeMap model=new LindeMap();
	//	TicketInfoDAO ticketInfoDAO=new TicketInfoDAO();
	//	LindeList uploaddates=service.getUploadInfo();
	//	model.put("uploadDateList",uploaddates);
		List<Module> moduleList = service.getModuleList();
		model.put("moduleList", moduleList);
		reviewReportForm.setModuleList(moduleList);
		HttpSession session=req.getSession(false);
		session.setAttribute("model",model);
		return new ModelAndView("ReportFormat","reviewReportForm",reviewReportForm); 
		
	}
	
	
	@RequestMapping(value = { "/generateReport.spring" }, method = RequestMethod.POST)	
	public ModelAndView generateReviewReport(HttpServletRequest request,
			HttpServletResponse response, ReviewReportForm reviewReportForm) {
	
		LindeMap model = new LindeMap();
		List<CRinfo> crDataList = null;
		List<CRinfo> incidentDataList = null;
		String reportType = null;
		
		
		HttpSession userSession = request.getSession(false);
		User user=(User)userSession.getAttribute("userInformation");
		String year="";
		userSession.setAttribute("archivalyear",(String)userSession.getAttribute("archivalyear"));
		year=(String)userSession.getAttribute("archivalyear");
		LindeList userList=new LindeList();
	//	MapUserDAO mapUserDAO=new MapUserDAO();
		userList=service.getUserInfo();
		lindeLogMgr.logMessage("DEBUG","Report Format setting started");
		int count=0;
	
		try
		{
			String fromDate = reviewReportForm.getFrom_dt();
			String toDate = reviewReportForm.getTo_dt();
			String moduleNAme = reviewReportForm.getSelectmodule();
			 reportType = reviewReportForm.getReportType();
			lindeLogMgr.logMessage("DEBUG", "reportType "+reportType);
			lindeLogMgr.logMessage("DEBUG", "fromDate "+fromDate);
			lindeLogMgr.logMessage("DEBUG", "moduleNAme :: "+moduleNAme);
			userSession.setAttribute("reportType", reportType);
			
			if(reportType.equalsIgnoreCase("CR"))
			{
			String[] listOfFields = reviewReportForm.getCrfields();
			lindeLogMgr.logMessage("DEBUG", "size of the listOfFields :: "+listOfFields.length);
			for (String string : listOfFields) {
				lindeLogMgr.logMessage("DEBUG", "listOfFields getCrfields:: "+string);
				model.put(string, string);
				
			}
			 crDataList = crInfoservice.getCRData(fromDate, toDate, moduleNAme);
			 
			 
			}
			else
			{
				String[] listOfFields = reviewReportForm.getIncidentfields();
				lindeLogMgr.logMessage("DEBUG", "size of the listOfFields :: "+listOfFields.length);
				for (String string : listOfFields) {
					lindeLogMgr.logMessage("DEBUG", "listOfFields getIncidentfields :: "+string);
					model.put(string, string);
					
				}

				 incidentDataList = incidentService.getIncidentData(user,fromDate,toDate, moduleNAme);
				
			}
			
		
		}
		catch(Exception pe)
		{
			lindeLogMgr.logStackTrace("ERROR", pe.getMessage(), pe);
		}
		
		List<Module> moduleList = service.getModuleList();
		if(reportType.equalsIgnoreCase("CR"))
			{
				
	     	CrForm crform = new CrForm();
			String urlparameter="";
			crform.setCrList(crDataList);
			crform.setUrlInfo(urlparameter);
			crform.setModuleList(moduleList);
			//crform.setUsers(users);
			crform.setPageType("ReviewReportPage");
			crform.setSelectedFlds(model);
			userSession.setAttribute("crform", crform);
			return new ModelAndView("ReviewReport","crform",crform);
			}
		else
		{
		
			LindeMap incidentMap = new LindeMap();
			incidentMap.put("selectedfields", model);
			 
			 
			 incidentMap.put("incidentList", incidentDataList);
			 incidentMap.put("moduleList", moduleList);
			//String urlparameter="";
		//	crform.setCrList(crDataList);
		//	crform.setUrlInfo(urlparameter);
		//	crform.setModuleList(moduleList);
			//crform.setUsers(users);
		//	crform.setPageType("ReviewReportPage");
		//	crform.setSelectedFlds(model);
			 userSession.setAttribute("incidentMap", incidentMap);
			return new ModelAndView("incidentReviewReport","model",incidentMap);
			
		}
		// return new ModelAndView("ReviewReport","model",model);
		//return new ModelAndView("ReportFormat","reviewReportForm",reviewReportForm); 
	}
	
	@RequestMapping(value = { "/download1.spring" }, method = RequestMethod.POST)	
	public ModelAndView downloadtoexcel(HttpServletRequest request,
			HttpServletResponse response) {
		
		HttpSession userSession = request.getSession(false);
		User user=(User)userSession.getAttribute("userInformation");
		String reportType = (String)userSession.getAttribute("reportType");
		if(reportType.equalsIgnoreCase("CR")){
			
			return new ModelAndView("ReviewReport","crform",userSession.getAttribute("crform"));			
		}
		else
		{
			return new ModelAndView("incidentReviewReport","model",userSession.getAttribute("incidentMap"));
		}
	}
	
	

	@RequestMapping(value = { "/reportsorting.spring" }, method = RequestMethod.POST)	
	public ModelAndView getSortedData(HttpServletRequest request,
			HttpServletResponse response, ReviewReportForm reviewReportForm){
	
		LindeMap model = new LindeMap();
		HttpSession session=request.getSession(false);
		LindeList ttInfo=new LindeList();
	//	TicketInfoDAO ticketInfoDAO=new TicketInfoDAO();
		HttpSession userSession = request.getSession();
		String year="";
		userSession.setAttribute("archivalyear",(String)userSession.getAttribute("archivalyear"));
		year=(String)userSession.getAttribute("archivalyear");
		User user=(User)userSession.getAttribute("userInformation");
		lindeLogMgr.logMessage("DEBUG","Report Format setting started");
		LindeList userList=new LindeList();
	//	MapUserDAO mapUserDAO=new MapUserDAO();
		userList=service.getUserInfo();
		
		
		String uploaddate="";
		String regionselection="";
		String count=(String)request.getParameter("count");
		String field=(String)request.getParameter("field");
		String from_dt="";
		String to_dt="";
		String criteria="";
		
		if(request.getParameter("from_dt")==null){from_dt="";}
		if(request.getParameter("to_dt")==null){to_dt="";}
		if(request.getParameter("criteria")==null){criteria="";}
		if(request.getParameter("from_dt")!=null){from_dt=(String)request.getParameter("from_dt");}
		if(request.getParameter("to_dt")!=null){to_dt=(String)request.getParameter("to_dt");}
		if(request.getParameter("criteria")!=null){criteria=(String)request.getParameter("criteria");}
		
		if(from_dt.equals(""))
		{from_dt="01/01/1000";
		}
		if(to_dt.equals(""))
		{
			to_dt="01/01/3000";
		}
		System.out.println(" Report Format "+from_dt+" to date "+to_dt);
		
		String type=(String)request.getParameter("type");
		uploaddate=(String)request.getParameter("uploaddate");
		regionselection=(String)request.getParameter("regionselection");
		LindeList ttList=service.getTTInfo(user,uploaddate,regionselection,from_dt,to_dt,criteria,year);
		
		SortObjects sortObjects=new SortObjects(field,type);
		try{
		
		ttInfo=(LindeList)sortObjects.getSortedPrograms(ttList, field, type);
		System.out.println("type"+type);
		
		}catch (Exception e) {
			System.out.println("error is "+e.getMessage());
		}
		
		
		model.put("ttInfo",ttList);
		model.put("uploaddate",uploaddate);
		model.put("regionselection",regionselection);
		model.put("reportFormat",reportformat);
		model.put("count",count);
		model.put("userList",userList);
		model.put("from_dt",from_dt);
		model.put("to_dt",to_dt);
		model.put("criteria",criteria);
		
		return new ModelAndView("ReviewReport","model",model);
	}
	
	
	
	
	
	@RequestMapping(value = { "/reviewReport_submit.spring" }, method = RequestMethod.POST)
	public ModelAndView submitRemarks(HttpServletRequest request,
			HttpServletResponse response, ReviewReportForm reviewReportForm){
		
		LindeMap model = new LindeMap();
	//	TicketInfoDAO ticketInfoDAO=new TicketInfoDAO();
		HttpSession userSession = request.getSession();
		User user=(User)userSession.getAttribute("userInformation");
		String year="";
		userSession.setAttribute("archivalyear",(String)userSession.getAttribute("archivalyear"));
		year=(String)userSession.getAttribute("archivalyear");
		LindeList userList=new LindeList();
	//	MapUserDAO mapUserDAO=new MapUserDAO();
		userList=service.getUserInfo();
		System.out.println("session user id is : "+user.getFirstName());
		String count=(String)request.getParameter("count");
		String uploaddate="";
		String regionselection="";
		String from_dt="";
		String to_dt="";
		String criteria="";
		
		if(request.getParameter("from_dt")==null){from_dt="";}
		if(request.getParameter("to_dt")==null){to_dt="";}
		if(request.getParameter("criteria")==null){criteria="";}
		if(request.getParameter("from_dt")!=null){from_dt=(String)request.getParameter("from_dt");}
		if(request.getParameter("to_dt")!=null){to_dt=(String)request.getParameter("to_dt");}
		if(request.getParameter("criteria")!=null){criteria=(String)request.getParameter("criteria");}
	
		
		if(from_dt.equals(""))
		{
			from_dt="01/01/1000";
		}
		if(to_dt.equals(""))
		{
			to_dt="01/01/3000";
		}
		System.out.println(" Report Format "+from_dt+" to date "+to_dt);
		
		/*if(reviewReportForm.getUploaddate()==null){uploaddate="";}
		if(reviewReportForm.getUploaddate()!=null){uploaddate=reviewReportForm.getUploaddate();}*/
		uploaddate=(String)request.getParameter("uploaddate");
		regionselection=(String)request.getParameter("regionselection");
		LindeList ttList_temp=service.getTTInfo(user,uploaddate,regionselection,from_dt,to_dt,criteria,year);
		LindeList ttList=new LindeList();
		for(int i=0;i<ttList_temp.size();i++){
		
			TicketInfo ticketInfo=(TicketInfo)ttList_temp.get(i);
			String remarks=(String)request.getParameter("remarks_"+ticketInfo.getIncidentID())!=null?(String)request.getParameter("remarks_"+ticketInfo.getIncidentID()):"";
			String appname=(String)request.getParameter("application_"+ticketInfo.getIncidentID())!=null?(String)request.getParameter("application_"+ticketInfo.getIncidentID()):"";
			String status=(String)request.getParameter("status_"+ticketInfo.getIncidentID())!=null?(String)request.getParameter("status_"+ticketInfo.getIncidentID()):"";
			String individual=(String)request.getParameter("individual_"+ticketInfo.getIncidentID())!=null?(String)request.getParameter("individual_"+ticketInfo.getIncidentID()):"";
			String classification=(String)request.getParameter("class_"+ticketInfo.getIncidentID())!=null?(String)request.getParameter("class_"+ticketInfo.getIncidentID()):"";
			/*
			String effort_netmeeting=(String)request.getParameter("effort_netmeeting_"+ticketInfo.getTTNo())!=null?(String)request.getParameter("effort_netmeeting_"+ticketInfo.getTTNo()):"";
			String effort_discussion=(String)request.getParameter("effort_discussion_"+ticketInfo.getTTNo())!=null?(String)request.getParameter("effort_discussion_"+ticketInfo.getTTNo()):"";
			String effort_ktdoc=(String)request.getParameter("effort_ktdoc_"+ticketInfo.getTTNo())!=null?(String)request.getParameter("effort_ktdoc_"+ticketInfo.getTTNo()):"";
			String effort_gsd=(String)request.getParameter("effort_gsd_"+ticketInfo.getTTNo())!=null?(String)request.getParameter("effort_gsd_"+ticketInfo.getTTNo()):"";
			*/
			String effort_total=(String)request.getParameter("effort_total_"+ticketInfo.getIncidentID())!=null?(String)request.getParameter("effort_total_"+ticketInfo.getIncidentID()):"";
			//String workaround=(String)request.getParameter("workaround_"+ticketInfo.getTTNo())!=null?(String)request.getParameter("workaround_"+ticketInfo.getTTNo()):"";
			String resolution=(String)request.getParameter("resolution_"+ticketInfo.getIncidentID())!=null?(String)request.getParameter("resolution_"+ticketInfo.getIncidentID()):"";
			String resolutionUpdation=(String)request.getParameter("resolutionUpdation")!=null?(String)request.getParameter("resolutionUpdation"):"NO";
			String effortsUpdation=(String)request.getParameter("effortsUpdation")!=null?(String)request.getParameter("effortsUpdation"):"NO";
			String classificationUpdation=(String)request.getParameter("classificationUpdation")!=null?(String)request.getParameter("classificationUpdation"):"NO";
			String remarksUpdation=(String)request.getParameter("remarksUpdation")!=null?(String)request.getParameter("remarksUpdation"):"NO";
			String applicationUpdation=(String)request.getParameter("applicationUpdation")!=null?(String)request.getParameter("applicationUpdation"):"NO";
			String individualUpdation=(String)request.getParameter("individualUpdation")!=null?(String)request.getParameter("individualUpdation"):"NO";
			String statusUpdation=(String)request.getParameter("statusUpdation")!=null?(String)request.getParameter("statusUpdation"):"NO";
			//System.out.println(""+effort_discussion+""+effort_netmeeting+""+effort_ktdoc+""+effort_gsd+""+effort_total+"");
			
			try{
				if(effortsUpdation.equals("YES") && effortsUpdation!= null && !effortsUpdation.equals("NO"))
			{
					/*
					if(effort_netmeeting!=null)
					{
					if(effort_netmeeting.equals("")){ticketInfo.setEffort_netmeeting(0);}
					else
					ticketInfo.setEffort_netmeeting(Float.parseFloat(effort_netmeeting));
					}
					
					if(effort_discussion!=null)
					{
					if(effort_discussion.equals("")){ticketInfo.setEffort_discussion(0);}
					else
					ticketInfo.setEffort_discussion(Float.parseFloat(effort_discussion));
					}
					
					if(effort_ktdoc!=null)
					{
					if(effort_ktdoc.equals("")){ticketInfo.setEffort_ktdoc(0);}
					else
					ticketInfo.setEffort_ktdoc(Float.parseFloat(effort_ktdoc));
					}
					
					if(effort_gsd!=null)
					{
					if(effort_gsd.equals("")){ticketInfo.setEffort_gsd(0);}
					else	
					ticketInfo.setEffort_gsd(Float.parseFloat(effort_gsd));
					}
					
					if(effort_total!=null)
					{
					if(effort_total.equals("")){ticketInfo.setEffort_total(0);}
					else
					ticketInfo.setEffort_total(Float.parseFloat(effort_total));
					}*/
			}
			
			if(resolutionUpdation.equals("YES") && resolutionUpdation!= null && !resolutionUpdation.equals("NO"))
			{
					/*
					if(workaround!=null)
					{
					if(workaround.equals("")){ticketInfo.setWorkaround("");}
					else
					ticketInfo.setWorkaround(workaround);
					}
					
				
					if(resolution!=null)
					{
					if(resolution.equals("")){ticketInfo.setResolution("");}
					else
					ticketInfo.setResolution(resolution);
					}*/
			}
			
			if(individualUpdation.equals("YES") && individualUpdation!= null && !individualUpdation.equals("NO"))
			{
					if(individual!=null)
					{
					if(individual.equals("")){ticketInfo.setAssigneeFullname("");}
					else
					ticketInfo.setAssigneeFullname(individual);
					}
			}
			if(statusUpdation.equals("YES") && statusUpdation!= null && !statusUpdation.equals("NO"))
			{
					if(status!=null)
					{
					if(status.equals("")){ticketInfo.setStatus("");}
					else
					ticketInfo.setStatus(status);
					}
			}
			/*if(remarksUpdation.equals("YES") && remarksUpdation!= null && !remarksUpdation.equals("NO"))
			{
					if(remarks!=null)
					{
					if(remarks.equals("")){ticketInfo.setRemarks("");}
					else
					ticketInfo.setRemarks(remarks);
					}
			}*/
			if(applicationUpdation.equals("YES") && applicationUpdation!= null && !applicationUpdation.equals("NO"))
			{
					if(appname!=null)
					{
					if(appname.equals("")){ticketInfo.setModule("");}
					else
					ticketInfo.setModule(appname);
					}
			}
			/*if(classificationUpdation.equals("YES") && classificationUpdation!= null && !classificationUpdation.equals("NO"))
			{
					if(classification!=null)
					{
					if(classification.equals("")){ticketInfo.setClassification("");}
					else
					ticketInfo.setClassification(classification);
					}
			}*/
			}
			catch(Exception e){System.out.println("error occurred in reviewreport updation is : "+e.getMessage());}
			
			ttList.add(ticketInfo);
			
			
		}
		
		LindeList ttList_final=service.saveTTInfo(ttList);
				
		
		model.put("ttInfo",ttList_final);
		model.put("count",count);
		model.put("uploaddate",uploaddate);
		model.put("regionselection",regionselection);
		model.put("reportFormat",reportformat);
		model.put("userList",userList);
		model.put("from_dt",from_dt);
		model.put("to_dt",to_dt);
		model.put("criteria",criteria);
		
		model.put("message","The Information has been saved Successfully");
		
		return new ModelAndView("ReviewReport","model",model);
	}

	@RequestMapping(value = { "/download.spring" }, method = RequestMethod.POST)
	public ModelAndView downloadData(HttpServletRequest request,
			HttpServletResponse response, ReviewReportForm reviewReportForm){
		
		LindeMap model = new LindeMap();
	//	TicketInfoDAO ticketInfoDAO=new TicketInfoDAO();
		String uploaddate="";
		String regionselection="";
		HttpSession userSession = request.getSession();
		User user=(User)userSession.getAttribute("userInformation");
		String year="";
		userSession.setAttribute("archivalyear",(String)userSession.getAttribute("archivalyear"));
		year=(String)userSession.getAttribute("archivalyear");
		System.out.println("session user id is : "+user.getFirstName());
		uploaddate=(String)request.getParameter("uploaddate");
		regionselection=(String)request.getParameter("regionselection");
		String count=(String)request.getParameter("count");
		String from_dt="";
		String to_dt="";
		String criteria="";

		if(request.getParameter("from_dt")==null){from_dt="";}
		if(request.getParameter("to_dt")==null){to_dt="";}
		if(request.getParameter("criteria")==null){criteria="";}
		if(request.getParameter("from_dt")!=null){from_dt=(String)request.getParameter("from_dt");}
		if(request.getParameter("to_dt")!=null){to_dt=(String)request.getParameter("to_dt");}
		if(request.getParameter("criteria")!=null){criteria=(String)request.getParameter("criteria");}
	
		
		if(from_dt.equals(""))
		{from_dt="01/01/1000";
		}
		if(to_dt.equals(""))
		{
			to_dt="01/01/3000";
		}
		System.out.println(" Report Format "+from_dt+" to date "+to_dt);
		LindeList ttList=service.getTTInfo(user,uploaddate,regionselection,from_dt,to_dt,criteria,year);
		
		model.put("ttInfo",ttList);
		model.put("count",count);
		model.put("uploaddate",uploaddate);
		model.put("regionselection",regionselection);
		model.put("reportFormat",reportformat);
		return new ModelAndView("ReviewReport","model",model);
	}
	
	
}



