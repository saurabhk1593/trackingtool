package com.techm.trackingtool.admin.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.techm.trackingtool.admin.bean.Leave;
import com.techm.trackingtool.admin.bean.User;
import com.techm.trackingtool.admin.form.EmailFrequencyForm;
import com.techm.trackingtool.admin.form.LeavePlanForm;
import com.techm.trackingtool.services.UserdetailsService;
import com.techm.trackingtool.util.LindeMap;
import com.techm.trackingtool.util.LindeToolLogManager;


@Controller
public class LeavePlanController {
	@Autowired
	UserdetailsService service;
	
	LindeMap model = new LindeMap();
	LindeToolLogManager lindeLogMgr = new LindeToolLogManager(LeavePlanController.class.getName());
		
	@RequestMapping(value = { "/Leave.spring" }, method = RequestMethod.GET )
	public ModelAndView leavepage(HttpServletRequest req, HttpServletResponse res, LeavePlanForm leaveplanform)
	{
		ArrayList leaveList=new ArrayList();
		leaveList=service.getLeaveDetails();
		model.put("Leaves", leaveList);
		lindeLogMgr.logMessage("DEBUG", "Inside applicationPage");
		return new ModelAndView("LeavePlans","model",model);
		
		
	}
	
	@RequestMapping(value = { "/Leave.spring" }, method = RequestMethod.POST )
	public ModelAndView saveleave(HttpServletRequest req, HttpServletResponse res, LeavePlanForm leaveplanform)
	{
		
		HttpSession userSession = req.getSession();
		User user=(User)userSession.getAttribute("userInformation");
		Leave leave=new Leave();
		boolean leaveoverlap=false;
		String startDate=leaveplanform.getStartDate();
		String endDate=leaveplanform.getEndDate();
		String reasons=leaveplanform.getReasons();
		String remarks=leaveplanform.getRemarks();
		
		leave.setLeaveStartDate(startDate);
		leave.setLeaveEndDate(endDate);
		leave.setReasons(reasons);
		leave.setRemarks(remarks);
		
		leaveoverlap=service.checkleaves(user,startDate,endDate);
		if(leaveoverlap)
		{
			service.submitLeaves(user,leave);
		}
		else{
			return new ModelAndView("Info_Saved", "message","Already applied leaved for the specified dates");
		}
		
		
		lindeLogMgr.logMessage("DEBUG", "Inside applicationPage");
		return new ModelAndView("Info_Saved", "message","The Information has been saved successfully");
		
		
	}

}
