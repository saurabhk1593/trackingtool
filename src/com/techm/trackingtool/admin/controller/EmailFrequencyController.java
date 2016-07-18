package com.techm.trackingtool.admin.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import antlr.collections.List;

import com.techm.trackingtool.admin.bean.EmailFrequency;
import com.techm.trackingtool.admin.dao.EmailDAO;
import com.techm.trackingtool.admin.form.AddApplicationForm;
import com.techm.trackingtool.admin.form.EmailFrequencyForm;
import com.techm.trackingtool.services.UserdetailsService;
import com.techm.trackingtool.util.LindeMap;
import com.techm.trackingtool.util.LindeToolLogManager;

@Controller
public class EmailFrequencyController  {

	@Autowired
	UserdetailsService service;
	
	LindeMap model = new LindeMap();
	LindeMap modeldata=new LindeMap();
	LindeToolLogManager lindeLogMgr = new LindeToolLogManager(EmailFrequencyController.class.getName());
	
	
	
	
	
	
	@RequestMapping(value = { "/configureEmailFrequency.spring" }, method = RequestMethod.GET)
	public ModelAndView applicationPage(HttpServletRequest req, HttpServletResponse res, EmailFrequencyForm emailFrequencyForm)
		{
		
		
		lindeLogMgr.logMessage("DEBUG", "Inside applicationPage");
		return new ModelAndView("EmailFrequency");
		
	}
	
	
	/*@RequestMapping(value = { "/configureEmailFrequency.spring" }, method = RequestMethod.GET)
	public ModelAndView priorityApplicationPage(HttpServletRequest req, HttpServletResponse res, EmailFrequencyForm emailFrequencyForm)
		{
		
		String priority=req.getParameter("severity");
		if(priority==null||priority.equals(""))
		{
			priority="";
		}
		lindeLogMgr.logMessage("DEBUG", "Inside applicationPage");
		ArrayList emailconfiglist=new ArrayList();
		emailconfiglist=service.getEmailConfigList(priority);
		modeldata.put("priority", emailconfiglist);
		return new ModelAndView("EmailFrequency","modeldata",modeldata);
		
	}*/
	
	
	
	
	
	
	@RequestMapping(value = { "/configEmailFrequency.spring" }, method = RequestMethod.POST)
	public ModelAndView configureEmail(HttpServletRequest req,HttpServletResponse res, EmailFrequencyForm emailFrequencyForm)
	{
		
		EmailFrequency emailFrequency=new EmailFrequency();
		lindeLogMgr.logMessage("INFO", "Value of severity is:::::"+emailFrequencyForm.getSeverity());
		String severityid=emailFrequencyForm.getSeverity();
		String severitydesc="";
		
		//List freq=emailFrequencyForm.getFrequency();
		//List cclist=emailFrequencyForm.getCclist();
		if(severityid.contains("1")){severitydesc="1 - Critical";}
		if(severityid.contains("2")){severitydesc="2 - High";}
		if(severityid.contains("3")){severitydesc="3 - Medium";}
		if(severityid.contains("4")){severitydesc="4 - Low";}
	
		
		String monday_email="Y";
		String tuesday_email="Y";
		String wednesday_email="Y";
		String thursday_email="Y";
		String friday_email="Y";
		String saturday_email="";
		String owners_cc="Y";
		String tm_cc="Y";
		String pgm_cc="Y";
		String pm_cc="Y";
		
		if(emailFrequencyForm.getFrequency1()==null||emailFrequencyForm.getFrequency1().equals("")){monday_email="N";}
		if(emailFrequencyForm.getFrequency2()==null||emailFrequencyForm.getFrequency2().equals("")){tuesday_email="N";}
		if(emailFrequencyForm.getFrequency3()==null||emailFrequencyForm.getFrequency3().equals("")){wednesday_email="N";}
		if(emailFrequencyForm.getFrequency4()==null||emailFrequencyForm.getFrequency4().equals("")){thursday_email="N";}
		if(emailFrequencyForm.getFrequency5()==null||emailFrequencyForm.getFrequency5().equals("")){friday_email="N";}
		if(emailFrequencyForm.getCclist1()==null||emailFrequencyForm.getCclist1().equals("")){owners_cc="N";}
		if(emailFrequencyForm.getCclist2()==null||emailFrequencyForm.getCclist2().equals("")){tm_cc="N";}
		if(emailFrequencyForm.getCclist3()==null||emailFrequencyForm.getCclist3().equals("")){pgm_cc="N";}
//		if(emailFrequencyForm.getCclist4()==null||emailFrequencyForm.getCclist4().equals("")){pm_cc="N";}
		
		
		emailFrequency.setId(severityid);
		emailFrequency.setPriority(severitydesc);
		emailFrequency.setMonday_email(monday_email);
		emailFrequency.setTuesday_email(tuesday_email);
		emailFrequency.setWednesday_email(wednesday_email);
		emailFrequency.setThursday_email(thursday_email);
		emailFrequency.setFriday_email(friday_email);
		//emailFrequency.setOwners_cc(owners_cc);
		emailFrequency.setTm_cc(tm_cc);
		emailFrequency.setPgm_cc(pgm_cc);
		emailFrequency.setPm_cc(pm_cc);
	//	EmailDAO emailDAO=new EmailDAO();
		//EmailFrequency emailFrequency2=emailDAO.getEmailSetting(severityid);
		service.deleteEmailSetting(severityid);
		service.SubmitEmailSetting(emailFrequency);
		System.out.println("Severity "+emailFrequencyForm.getSeverity()+"Frequency "+emailFrequencyForm.getFrequency1()+emailFrequencyForm.getFrequency2()+emailFrequencyForm.getFrequency3()+emailFrequencyForm.getFrequency4()+emailFrequencyForm.getFrequency5()+"cc list"+emailFrequencyForm.getCclist1()+emailFrequencyForm.getCclist2()+emailFrequencyForm.getCclist3());
		
		lindeLogMgr.logMessage("DEBUG", "Inside LoginController:Login Id");
		
		
		return new ModelAndView("Info_Saved", "message","The Information has been saved successfully");
		
	}

	@RequestMapping(value = { "/configureEmailFrequency.spring" }, method = RequestMethod.POST)
	public ModelAndView getEmailSetting(HttpServletRequest request,	HttpServletResponse response,	EmailFrequencyForm emailFrequencyForm) {
		//EmailDAO emailDAO=new EmailDAO();
		String severity=(String)request.getParameter("severity");
		System.out.println("Severity "+severity);
		EmailFrequency emailFrequency=(EmailFrequency)service.getEmailSetting(severity);
		model.put("application","");
		model.put("emailFrequency",emailFrequency);
		model.put("severity",severity);
		System.out.println("Severity "+severity);

		return new ModelAndView("EmailFrequency", "model",model);
		
	}
}
