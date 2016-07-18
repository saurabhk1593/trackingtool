package com.techm.trackingtool.admin.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

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

import com.techm.trackingtool.admin.bean.User;
import com.techm.trackingtool.admin.form.ArchivedDataForm;
import com.techm.trackingtool.admin.vo.AdminVO;
import com.techm.trackingtool.admin.vo.HomePageVO;
import com.techm.trackingtool.admin.vo.TicketSummaryVO;
import com.techm.trackingtool.services.UserdetailsService;
import com.techm.trackingtool.util.LindeList;
import com.techm.trackingtool.util.LindeMap;
import com.techm.trackingtool.util.LindeToolLogManager;

@Controller
public class ArchivedDataController {
	
	private static LindeToolLogManager lindeLogMgr = new LindeToolLogManager(ArchivedDataController.class.getName());
	private TicketSummaryVO ttSummary;
	private AdminVO adminInfo;
	private LindeMap homeMap;
	HomePageVO homeVO = new HomePageVO();
	
	@Autowired
	UserdetailsService service;
	
	@RequestMapping(value = { "/archivedData.spring" }, method = RequestMethod.POST)
	public ModelAndView homePageReportCurrentSelection(HttpServletRequest request,	HttpServletResponse response) throws ParseException {
	
		HttpSession userSession = request.getSession(false);
		User userInfo=(User)userSession.getAttribute("userInformation");
		String month=request.getParameter("selectedMonth");
		if(month.equals(null))
		{
			month="ALL";
		}
		lindeLogMgr.logMessage("DEBUG", "User object value in homePageReportCurrentSelection ::: "+userInfo.toString());
		String year="";
		if((String)userSession.getAttribute("archivalyear")==null)
		{ 
			return new ModelAndView("generalError","","");
		}
		else
		{
			userSession.setAttribute("archivalyear",(String)userSession.getAttribute("archivalyear"));
			year=(String)userSession.getAttribute("archivalyear");
		}
		
		if(!userInfo.getApprovalStatus().equals("Pending")){
			ttSummary = getTTSummaryInfo(userInfo,month,year);
			if(!userInfo.getUserRole().getRoleName().equals("TM"))
				{	
				adminInfo = getAdminInfo(adminInfo);
				homeMap.put("adminInfo",adminInfo);
				}
	}
		Date sysDate = new Date();
		DateFormat formatter=DateFormat.getDateInstance();
		formatter.format(formatter.LONG);
		String currentDate = formatter.format(sysDate);
		
		homeMap.put("ttSummary",ttSummary);
		homeMap.put("userInfo",userInfo);
		homeMap.put("currDate",currentDate);
		
		homeVO.setHomePageMap(homeMap);
		return new ModelAndView("HomePage","homeVO",homeVO);
	}
	
	
	@RequestMapping(value = { "/archivedData_home.spring" }, method = RequestMethod.GET)
	public ModelAndView homePageReport(HttpServletRequest request,HttpServletResponse response, ArchivedDataForm archivedDataForm, BindingResult result) throws ParseException {
		homeMap= new LindeMap();
		HttpSession userSession = request.getSession(false);
		User userInfo=(User)userSession.getAttribute("userInformation");
		String month=request.getParameter("selectedMonth");
		lindeLogMgr.logMessage("DEBUG", "User object value in homePageReport ::: "+userInfo.toString());
		lindeLogMgr.logMessage("DEBUG", "Value of select month is:::::"+month);
		
		String year=archivedDataForm.getYear();
		lindeLogMgr.logMessage("DEBUG", "year::: "+year);
		
		if(month == null || month.equals(""))
		{
			month="ALL";
		}
		
		
		if((String)userSession.getAttribute("archivalyear")==null)
		{
		userSession.setAttribute("archivalyear",year);	
		}
		else
			userSession.setAttribute("archivalyear",year);
		
		if(!userInfo.getApprovalStatus().equals("Pending")){
			ttSummary = getTTSummaryInfo(userInfo,month,year);
			if(!userInfo.getUserRole().getRoleName().equals("TM"))
				{	
				adminInfo = getAdminInfo(adminInfo);
				homeMap= new LindeMap();
				homeMap.put("adminInfo",adminInfo);
				}
	}
		Date sysDate = new Date();
		DateFormat formatter=DateFormat.getDateInstance();
		formatter.format(formatter.LONG);
		String currentDate = formatter.format(sysDate);
		
		homeMap.put("ttSummary",ttSummary);
		homeMap.put("userInfo",userInfo);
		homeMap.put("currDate",currentDate);
		homeMap.put("selectedMonth",archivedDataForm.getSelectedMonth());
		homeVO.setHomePageMap(homeMap);
		return new ModelAndView("HomePage","homeVO",homeVO);
	}
	/*
	@RequestMapping(value = { "/archivedDataReportMonthWise.spring" }, method = RequestMethod.GET)
	public ModelAndView homePageReportMonthWise(HttpServletRequest request,HttpServletResponse response,@ModelAttribute ArchivedDataForm archivedDataForm, BindingResult result) throws ParseException {
		homeMap= new LindeMap();
		HttpSession userSession = request.getSession(false);
		User userInfo=(User)userSession.getAttribute("userInformation");
		String month=request.getParameter("selectedMonth");
		if(month.equals(""))
		{
			
			
		}
		
		lindeLogMgr.logMessage("DEBUG", "User object value in homePageReport ::: "+userInfo.toString());
		
		String year=archivedDataForm.getYear();
		lindeLogMgr.logMessage("DEBUG", "year::: "+year);
		
		
		if((String)userSession.getAttribute("archivalyear")==null)
		{
		userSession.setAttribute("archivalyear",year);	
		}
		else
			userSession.setAttribute("archivalyear",year);
		
		if(!userInfo.getApprovalStatus().equals("Pending")){
			ttSummary = getTTSummaryInfoMonthwise(userInfo,month,year);
			if(!userInfo.getUserRole().getRoleName().equals("TM"))
				{	
				adminInfo = getAdminInfo(adminInfo);
				homeMap= new LindeMap();
				homeMap.put("adminInfo",adminInfo);
				}
	}
		Date sysDate = new Date();
		DateFormat formatter=DateFormat.getDateInstance();
		formatter.format(formatter.LONG);
		String currentDate = formatter.format(sysDate);
		
		homeMap.put("ttSummary",ttSummary);
		homeMap.put("userInfo",userInfo);
		homeMap.put("currDate",currentDate);
		
		homeMap.put("selectedMonth",archivedDataForm.getSelectedMonth());
		lindeLogMgr.logMessage("DEBUG","archivedDataForm.getSelectedMonth() -> "+archivedDataForm.getSelectedMonth());
		homeVO.setHomePageMap(homeMap);
		return new ModelAndView("HomePage","homeVO",homeVO);
	}*/
	
	
	

	private TicketSummaryVO getTTSummaryInfo(User userInfo,String month,String year) throws ParseException{
		lindeLogMgr.logMessage("DEBUG","getTTSummaryInfo -> "+year);
		//TicketInfoDAO ttDAO = new TicketInfoDAO();
		TicketSummaryVO ttSummary;
		ttSummary = service.getTTInformations(userInfo,month,year);
		return ttSummary;
	}
	
	
	/*private TicketSummaryVO getTTSummaryInfoMonthwise(User userInfo,String month,String year) throws ParseException
	{
		TicketSummaryVO ttSummary;
		ttSummary=service.getTTinfoMonthwise(userInfo,month,year);
		return ttSummary;
	}*/
	
	private AdminVO getAdminInfo(AdminVO adminInfo){
		//AdminDAO adminDAO = new AdminDAO();
		LindeList lindeList=service.getApplZeroOwnersCount();
		int zeroowners=0;
		zeroowners=lindeList.size();
		int pendingusers = service.getAdminInfo(adminInfo);
		 adminInfo = new AdminVO();
		adminInfo.setAppWithMinUser(zeroowners);
		adminInfo.setUsersAwaitingApproval(pendingusers);
		return adminInfo;
		
	}
}
