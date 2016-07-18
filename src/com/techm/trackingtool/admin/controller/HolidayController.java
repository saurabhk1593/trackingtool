package com.techm.trackingtool.admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.techm.trackingtool.admin.bean.Holidays;
import com.techm.trackingtool.admin.bean.User;
import com.techm.trackingtool.admin.form.HolidayForm;
import com.techm.trackingtool.services.UserdetailsService;
import com.techm.trackingtool.util.HashMap;
import com.techm.trackingtool.util.LindeMap;
import com.techm.trackingtool.util.LindeToolLogManager;

@Controller
public class HolidayController {
	
	
	@Autowired
	UserdetailsService service;
	
	//LindeMap model = new LindeMap();
	
	LindeMap model = new LindeMap();
	LindeToolLogManager lindeLogMgr = new LindeToolLogManager(LeavePlanController.class.getName());
		
	@RequestMapping(value = { "/Holiday.spring" }, method = RequestMethod.GET )
	public ModelAndView holidaypage(HttpServletRequest req, HttpServletResponse res, HolidayForm holidayform)
	{
		lindeLogMgr.logMessage("DEBUG", "Inside applicationPage");
		return new ModelAndView("HolidayPlans");
		
		
	}
	
	@RequestMapping(value = { "/Holiday.spring" }, method = RequestMethod.POST )
	public ModelAndView saveHoliday(HttpServletRequest req, HttpServletResponse res, HolidayForm holidayform)
	{
		
		HttpSession userSession = req.getSession();
		//User user=(User)userSession.getAttribute("userInformation");
		Holidays holiday=new Holidays();
		String holidayDate=holidayform.getHolidayDate();
		String description=holidayform.getDescription();
		
		
		holiday.setHolidayDate(holidayDate);
		holiday.setDescription(description);
		
		
		service.submitHolidays(holiday);
		
		lindeLogMgr.logMessage("DEBUG", "Inside applicationPage");
		return new ModelAndView("Info_Saved", "message","The Information has been saved successfully");
		
		
	}
	
	@RequestMapping(value = { "/displayHoliday.spring" }, method = RequestMethod.GET )
	public ModelAndView getHolidayList(HttpServletRequest req, HttpServletResponse res)
	{
		HashMap holidayList=new HashMap();
		holidayList=service.getHolidayDetails();
		model.put("Holiday", holidayList);
		lindeLogMgr.logMessage("DEBUG", "Inside applicationPage");
		return new ModelAndView("DisplayHoliday","model",model);
		
		
	}
	
	


}
