package com.techm.trackingtool.admin.controller;


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




import com.techm.trackingtool.admin.bean.Module;
import com.techm.trackingtool.admin.form.ModuleForm;
import com.techm.trackingtool.services.UserdetailsService;
import com.techm.trackingtool.util.LindeToolLogManager;

@Controller
public class AddModuleController {

	LindeToolLogManager lindeLogMgr = new LindeToolLogManager(AddModuleController.class.getName());
	
	@Autowired
	UserdetailsService service;
	
	@RequestMapping(value = { "/addModule.spring" }, method = RequestMethod.GET)
	public ModelAndView applicationPage(HttpServletRequest req,
			HttpServletResponse res, ModuleForm moduleForm)
		{
		
		
		lindeLogMgr.logMessage("DEBUG", "Inside applicationPage");
		
		return new ModelAndView("addmodule");
		
	}
	
	@RequestMapping(value = { "/addModule.spring" }, method = RequestMethod.POST)
	public ModelAndView addApplication(HttpServletRequest req,	HttpServletResponse res,@ModelAttribute("moduleForm") ModuleForm moduleForm,BindingResult result)
		{
		lindeLogMgr.logMessage("INFO", "Inside addApplication");
		
		Module module = new Module();
		
		try
		{
			module.setModulename(moduleForm.getModulename());
			module.setDescription(moduleForm.getDescription());
			module.setCrspermonth(moduleForm.getCrspermonth());
			module.setDocavailability(moduleForm.getDocavailability());
			module.setPriority(moduleForm.getPriority());
			service.SubmitApplication(module);
			
		}
		catch(Exception ex)
		{
			
			lindeLogMgr.logStackTrace("ERROR","Exception in AddModuleController "+ex.getMessage(),ex);
			return new ModelAndView("addmodule", "message",ex.getMessage());
		}
		return new ModelAndView("Info_Saved", "message","The Information has been saved successfully");
		
	}
	
	
}
