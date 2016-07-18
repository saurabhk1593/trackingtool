package com.techm.trackingtool.admin.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import antlr.collections.List;

import com.techm.trackingtool.admin.bean.CrEmailFrequency;
import com.techm.trackingtool.admin.bean.EmailFrequency;
import com.techm.trackingtool.admin.dao.EmailDAO;
import com.techm.trackingtool.admin.form.AddApplicationForm;
import com.techm.trackingtool.admin.form.CrEmailFrequencyForm;
import com.techm.trackingtool.admin.form.EmailFrequencyForm;
import com.techm.trackingtool.services.UserdetailsService;
import com.techm.trackingtool.util.LindeMap;
import com.techm.trackingtool.util.LindeToolLogManager;
import com.techm.trackingtool.util.TrackingToolConstant;

@Controller
public class CrEmailFrequencyController {

	@Autowired
	UserdetailsService service;
		
	LindeMap model = new LindeMap();
	LindeToolLogManager lindeLogMgr = new LindeToolLogManager(CrEmailFrequencyController.class.getName());
		
		
		
	@RequestMapping(value = { "/crconfigureEmailFrequency.spring" }, method = RequestMethod.GET)
	public ModelAndView applicationPage(HttpServletRequest req, HttpServletResponse res, CrEmailFrequencyForm cremailFrequencyForm)
			{
					
			lindeLogMgr.logMessage("DEBUG", "Inside applicationPage");
			
			return new ModelAndView("CREmailFrequency");
			
		}
		
		
		@RequestMapping(value = { "/crconfigEmailFrequency.spring" }, method = RequestMethod.POST)
		public ModelAndView configureEmail(HttpServletRequest req,HttpServletResponse res, CrEmailFrequencyForm cremailFrequencyForm)
		{
			
			CrEmailFrequency emailFrequency=new CrEmailFrequency();
			lindeLogMgr.logMessage("INFO", "Value of severity is:::::"+cremailFrequencyForm.getSeverity());
			String severityid=cremailFrequencyForm.getSeverity().toString();
			String severitydesc="";
			
			//List freq=emailFrequencyForm.getFrequency();
			//List cclist=emailFrequencyForm.getCclist();
			if(severityid.contains("1")){severitydesc="1 - Critical";}
			if(severityid.contains("2")){severitydesc="2 - High";}
			if(severityid.contains("3")){severitydesc="3 - Medium";}
			if(severityid.contains("4")){severitydesc="4 - Low";}
		
			
			String p1_interval="Y";
			String p2_interval="Y";
			String p3_interval="Y";
			String p4_interval="Y";
			
			String owners_cc="Y";
			String tm_cc="Y";
			String pgm_cc="Y";
			String pm_cc="Y";
			
			if(cremailFrequencyForm.getFrequency1()==null||cremailFrequencyForm.getFrequency1().equals("")){p1_interval="N";}
			if(cremailFrequencyForm.getFrequency2()==null||cremailFrequencyForm.getFrequency2().equals("")){p2_interval="N";}
			if(cremailFrequencyForm.getFrequency3()==null||cremailFrequencyForm.getFrequency3().equals("")){p3_interval="N";}
			if(cremailFrequencyForm.getFrequency4()==null||cremailFrequencyForm.getFrequency4().equals("")){p4_interval="N";}
			
			if(cremailFrequencyForm.getCclist1()==null||cremailFrequencyForm.getCclist1().equals("")){tm_cc="N";}
			if(cremailFrequencyForm.getCclist2()==null||cremailFrequencyForm.getCclist2().equals("")){pm_cc="N";}
			if(cremailFrequencyForm.getCclist3()==null||cremailFrequencyForm.getCclist3().equals("")){pgm_cc="N";}
			
			lindeLogMgr.logMessage("DEBUG","pgm_cc :::  "+cremailFrequencyForm.getCclist3());
			lindeLogMgr.logMessage("DEBUG","cremailFrequencyForm.getCclist3() :::  "+cremailFrequencyForm.getCclist3());
			
//			if(emailFrequencyForm.getCclist4()==null||emailFrequencyForm.getCclist4().equals("")){pm_cc="N";}
			
			
			emailFrequency.setId(severityid);
			emailFrequency.setPriority(severitydesc);
			if(cremailFrequencyForm.getFrequency1() !=null && cremailFrequencyForm.getFrequency1().equalsIgnoreCase("Y"))
			   emailFrequency.setP1_interval(String.valueOf(TrackingToolConstant.EMAIL_INTRVAL_PRIORITY_1));
			if(cremailFrequencyForm.getFrequency2() !=null &&cremailFrequencyForm.getFrequency2().equalsIgnoreCase("Y"))
				emailFrequency.setP2_interval(String.valueOf(TrackingToolConstant.EMAIL_INTRVAL_PRIORITY_2));
			if(cremailFrequencyForm.getFrequency3() !=null && cremailFrequencyForm.getFrequency3().equalsIgnoreCase("Y"))
				emailFrequency.setP3_interval(String.valueOf(TrackingToolConstant.EMAIL_INTRVAL_PRIORITY_3));
			if(cremailFrequencyForm.getFrequency4() !=null && cremailFrequencyForm.getFrequency4().equalsIgnoreCase("Y"))
				emailFrequency.setP4_interval(String.valueOf(TrackingToolConstant.EMAIL_INTRVAL_PRIORITY_4));
		
			//emailFrequency.setOwners_cc(owners_cc);
			emailFrequency.setTm_cc(tm_cc);
			emailFrequency.setPgm_cc(pgm_cc);
			emailFrequency.setPm_cc(pm_cc);
		//	EmailDAO emailDAO=new EmailDAO();
			//EmailFrequency emailFrequency2=emailDAO.getEmailSetting(severityid);
			service.deleteCrEmailSetting(severityid);
			service.submitCrEmailSetting(emailFrequency);
			System.out.println("Severity "+cremailFrequencyForm.getSeverity()+"Frequency "+cremailFrequencyForm.getFrequency1()+cremailFrequencyForm.getFrequency2()+cremailFrequencyForm.getFrequency3()+cremailFrequencyForm.getFrequency4()+cremailFrequencyForm.getCclist1()+cremailFrequencyForm.getCclist2()+cremailFrequencyForm.getCclist3());
			
			lindeLogMgr.logMessage("DEBUG", "Inside LoginController:Login Id");
			
			
			return new ModelAndView("Info_Saved", "message","The Information has been saved successfully");
			
		}

		@RequestMapping(value = { "/crconfigureEmailFrequency.spring" }, method = RequestMethod.POST)
		public ModelAndView getEmailSetting(HttpServletRequest request,	HttpServletResponse response,	CrEmailFrequencyForm CrEmailFrequency) {
			//EmailDAO emailDAO=new EmailDAO();
			String severity=(String)request.getParameter("severity");
			System.out.println("Severity "+severity);
			CrEmailFrequency cremailFrequency=(CrEmailFrequency)service.getCrEmailSetting(severity);
			model.put("application","");
			model.put("emailFrequency",cremailFrequency);
			model.put("severity",severity);
			System.out.println("Severity "+severity);

			return new ModelAndView("CREmailFrequency", "model",model);
			
		}
	}

	
	
	


