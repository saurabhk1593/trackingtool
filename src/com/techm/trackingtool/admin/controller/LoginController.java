package com.techm.trackingtool.admin.controller;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.techm.trackingtool.admin.bean.User;
import com.techm.trackingtool.admin.form.CrForm;
import com.techm.trackingtool.admin.form.LoginForm;
import com.techm.trackingtool.admin.vo.AdminVO;
import com.techm.trackingtool.admin.vo.CrSummaryVO;
import com.techm.trackingtool.admin.vo.HomePageVO;
import com.techm.trackingtool.admin.vo.LoginDetails;
import com.techm.trackingtool.admin.vo.TicketSummaryVO;
import com.techm.trackingtool.services.CRInfoService;
import com.techm.trackingtool.services.UserdetailsService;
import com.techm.trackingtool.util.LindeMap;
import com.techm.trackingtool.util.LindeToolLogManager;

@Controller
@RequestMapping("/")
public class LoginController {
	
	@Autowired
	UserdetailsService service;
	
	@Autowired
	CRInfoService crInfoservice;
	
	@Autowired
	MessageSource messageSource;
	
	HomePageVO homeVO = new HomePageVO();
	
	private static LindeToolLogManager lindeLogMgr = new LindeToolLogManager(LindeToolLogManager.class.getName());
	
	@RequestMapping(value = { "/login.spring" }, method = RequestMethod.GET)
	public ModelAndView applicationPage()
	{
		LoginForm loginform = new LoginForm();
		return new ModelAndView("login","LoginForm",loginform);
		
	}
	
	
	
	@RequestMapping(value = { "/login.spring" }, method = RequestMethod.POST)
	//public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object command, BindException arg3) throws Exception {
	public ModelAndView handle(@ModelAttribute("loginform") LoginForm loginform,HttpServletRequest request){
		lindeLogMgr.logMessage("INFO", "Inside LoginController");
		System.out.println("Inside LoginController");
		User userInfo=null;
		try
		{
		//	TicketSummaryVO ttSummary;
			
		//	AdminVO adminInfo = new AdminVO();
			LindeMap homeMap = new LindeMap();
			LoginForm loginfrm = loginform;
			HttpSession session = request.getSession(true);
			
			lindeLogMgr.logMessage("DEBUG", "*****session***"+session.getId());
			lindeLogMgr.logMessage("DEBUG", "******Domain Name***"+loginfrm.getPassword());
			lindeLogMgr.logMessage("DEBUG", "Inside LoginController:Login Id"+loginfrm.getLoginId());
			
			String userID = loginfrm.getLoginId();
			lindeLogMgr.logMessage("DEBUG", "User ID is "+userID);
			
			userInfo = service.checkUser(userID, loginfrm.getPassword());
			session.setAttribute("userInformation", userInfo);
			
			lindeLogMgr.logMessage("DEBUG", "User object value is ::: "+userInfo.toString());
			
			boolean userAvailable = userInfo.isAvailable();
			lindeLogMgr.logMessage("DEBUG", "User Available??????"+userAvailable);
			session.setAttribute("archivalyear", "2016");
		
			if (userAvailable){
				session.setAttribute("userInformation", userInfo);
			/*	if(!userInfo.getApprovalStatus().equals("Pending")){
					userSession.setAttribute("userInformation", userInfo);
					return new ModelAndView("ArchivedData","homeVO",homeVO);
				}
				else
				{
					lindeLogMgr.logMessage("DEBUG", "credentials available but pending");
					return new ModelAndView("nextPage","userPending",userInfo);
				} */
				
				
			}
			else 
				return new ModelAndView("login","errormsg","Please enter valid credentials");
			//CrForm crForm = new CrForm();
			 homeVO = getCRSummaryDetails(request,"ALL");
			 session.setAttribute("selectedMonth", "ALL");
			 
			 session.setAttribute("homepagedata", homeVO);
			 
		}
		catch(Exception ex)
		{
			lindeLogMgr.logStackTrace("ERROR", "LoginController ::"+ex.getMessage(), ex);
			//return new ModelAndView("crdashboard","homeVO",homeVO);
			return new ModelAndView("login","errormsg",messageSource.getMessage("valid.login.msg", null, null));
		}
		return new ModelAndView("crdashboard","homeVO",homeVO);
	}
	
	@RequestMapping(value = { "/home.spring" }, method = RequestMethod.POST)
	public ModelAndView loadCRHomePageData(HttpServletRequest request) throws SQLException, Exception{
		lindeLogMgr.logMessage("INFO", " Entered into loadCRHomePageData() ");
		HttpSession session = request.getSession(false);
		HomePageVO homeVO1 =(HomePageVO) session.getAttribute("homepagedata");
		lindeLogMgr.logMessage("DEBUG", " request.getParameter(selectedMonth) "+request.getParameter("selectedMonth"));
		String selMonth =request.getParameter("selectedMonth");
		if(selMonth!=null && selMonth.equals(""))
		{
			selMonth="ALL";
			lindeLogMgr.logMessage("DEBUG", " selMonth in if condition "+selMonth);
		}
		try{
		//if(homeVO ==null)
	//	{
			homeVO1 = getCRSummaryDetails(request,selMonth);
			 session.setAttribute("selectedMonth", selMonth);
	//	}
	}
		catch(Exception ex)
		{
			lindeLogMgr.logStackTrace("ERROR", "loadCRHomePageData ::"+ex.getMessage(), ex);
			//return new ModelAndView("crdashboard","homeVO",homeVO);
			return new ModelAndView("crdashboard","errormsg","Error while loading the home page data");
		}
		
		return new ModelAndView("crdashboard","homeVO",homeVO1);
	}
	
	@RequestMapping(value = { "/home.spring" }, method = RequestMethod.GET)
	public ModelAndView loadCRHomePageDataGet(HttpServletRequest request) throws SQLException, Exception{
		lindeLogMgr.logMessage("INFO", " Entered into loadCRHomePageDataGet() ");
		HttpSession session = request.getSession(false);
		HomePageVO homeVO =(HomePageVO) session.getAttribute("homepagedata");
		lindeLogMgr.logMessage("DEBUG", "homeVO.getHomePageMap().get(selectedMonth)"+request.getParameter("selectedMonth"));
		try{
	//	if(homeVO ==null)
	//	{
			homeVO = getCRSummaryDetails(request,request.getParameter("selectedMonth"));
			 session.setAttribute("selectedMonth", request.getParameter("selectedMonth"));
	//	}
	}
		catch(Exception ex)
		{
			lindeLogMgr.logStackTrace("ERROR", "loadCRHomePageData ::"+ex.getMessage(), ex);
			//return new ModelAndView("crdashboard","homeVO",homeVO);
			return new ModelAndView("crdashboard","errormsg","Error while loading the home page data");
		}
		
		return new ModelAndView("crdashboard","homeVO",homeVO);
	}

	
	@RequestMapping(value = { "/excel.spring" }, method = RequestMethod.POST)
	public ModelAndView crDatadownload(HttpServletRequest request) throws SQLException, Exception{
	//	CrForm crForm = new CrForm();
		homeVO = getCRSummaryDetails(request,request.getParameter("selectedMonth"));
		request.getSession().setAttribute("selectedMonth", request.getParameter("selectedMonth"));
		return new ModelAndView("crdashboard","homeVO",homeVO);
	}
	
	
	public HomePageVO getCRSummaryDetails(HttpServletRequest request,String selectedMonth) throws SQLException, Exception {
		
		lindeLogMgr.logMessage("INFO", "getCRSummaryDetails");
		
		LindeMap homeMap = new LindeMap();		
		HttpSession userSession = request.getSession(false);
		User userInfo=(User)userSession.getAttribute("userInformation");
		CrSummaryVO crSummary =null;
		
		 if(!userInfo.getApprovalStatus().equals("Pending")){
			
			
			if(!userInfo.getUserRole().getRoleName().equals("TM"))
				{	
			//	adminInfo = getAdminInfo(adminInfo);
			//	homeMap.put("adminInfo",adminInfo);
				}
	} 
		Date sysDate = new Date();
		DateFormat formatter=DateFormat.getDateInstance();
		formatter.format(formatter.LONG);
		String currentDate = formatter.format(sysDate);
		
		 crSummary = getCRInfo(userInfo,selectedMonth);
		 
		
		homeMap.put("ttSummary",crSummary);
		homeMap.put("userInfo",userInfo);
		homeMap.put("currDate",currentDate);
		homeMap.put("selectedMonth", selectedMonth);
		
		HomePageVO homeVO = new HomePageVO();
		homeVO .setHomePageMap(homeMap);
//		return new ModelAndView("crdashboard","homeVO",homeVO);
		
		return homeVO;
	
		
	//	return new ModelAndView("crdashboard", "model", homeMap);
	}

	private CrSummaryVO getCRInfo(User user,String selectedMonth) throws SQLException, Exception
	{
		CrSummaryVO crSummary  = null;
		try{
		 crSummary = crInfoservice.getCRDetails(user,selectedMonth);
		lindeLogMgr.logMessage("INFO", "getCRInfo");
		
		LindeMap priorityMap = crSummary.getByStatus();
		lindeLogMgr.logMessage("INFO", "getCRInfo priorityMap sizze "+priorityMap.size());
		Set lset = priorityMap.keySet();
		Iterator itr = lset.iterator();
		while(itr.hasNext())
		{
			Object key1= itr.next();
			String key = (String)key1;
			System.out.println("Key Value is ::: "+key);
			
			System.out.println("Key Value is ::: "+priorityMap.get(key));
			
		}
		
		}
		catch(Exception e)
		{
			lindeLogMgr.logStackTrace("ERROR", "GetCRINFO", e);
		}
		
		return crSummary;
		
	}
	
}
