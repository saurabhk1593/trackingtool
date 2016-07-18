
package com.techm.trackingtool.admin.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.techm.trackingtool.admin.bean.User;
import com.techm.trackingtool.admin.dao.ApplicationMappingsDAO;
import com.techm.trackingtool.admin.form.ApplicationMapForm;
import com.techm.trackingtool.services.UserdetailsService;
import com.techm.trackingtool.util.LindeList;
import com.techm.trackingtool.util.LindeMap;
import com.techm.trackingtool.util.LindeToolLogManager;

@Controller
public class ApplicationMapController {
	
	
	private LindeMap userMap;
	private LindeList rolesList;
	private User user;
	LindeMap model = new LindeMap();
	
	
	private static LindeToolLogManager logMgr = new LindeToolLogManager(ApplicationMapController.class.getName());
	@Autowired
	UserdetailsService service;
	
	@RequestMapping(value = { "/applicationMap.spring" }, method = RequestMethod.GET)
	public ModelAndView getAppInfo(HttpServletRequest request,
			HttpServletResponse response,
			ApplicationMapForm applicationMapForm) {
		
	//	ApplicationMappingsDAO applicationMappingsDAO=new ApplicationMappingsDAO();
		
		LindeList applicationlist=new LindeList();
		model=new LindeMap();
		applicationlist=service.getApplicationZeroOwnersInfo();
		model.put("applicationlist",applicationlist);
			
		return new ModelAndView("Application_With_OneUser","model",model); 	
		
		
	}
	
	@RequestMapping(value = { "/applicationMap.spring" }, method = RequestMethod.POST)
	public ModelAndView getAppInfoDetails(HttpServletRequest request,
			HttpServletResponse response,
			ApplicationMapForm applicationMapForm) {
		
	//	ApplicationMappingsDAO applicationMappingsDAO=new ApplicationMappingsDAO();
		
		LindeList applicationlist=new LindeList();
		model=new LindeMap();
		applicationlist=service.getApplicationZeroOwnersInfo();
		model.put("applicationlist",applicationlist);
			
		return new ModelAndView("Application_With_OneUser","model",model); 	
		
		
	}
	
	@RequestMapping(value = { "/moduleMappings.spring" }, method = RequestMethod.GET)
	public ModelAndView referenceData(HttpServletRequest request) throws Exception
	{
	//	ApplicationMappingsDAO applicationMappingsDAO=new ApplicationMappingsDAO();
		LindeList userlist=new LindeList();
		LindeList applicationlist=new LindeList();
		LindeMap model=new LindeMap();
		userlist=service.getUserModuleInfo();
			model.put("userlist",userlist);
			System.out.println("user bean "+userlist.get(1));
		
			applicationlist=service.getModuleUserInfo();
			model.put("applicationlist",applicationlist);
			
		//return model;
		
		return new ModelAndView("ApplicationMappingsReport","model",model); 	
	}
	
}

