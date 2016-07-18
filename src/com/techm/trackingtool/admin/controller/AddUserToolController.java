package com.techm.trackingtool.admin.controller;

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
import com.techm.trackingtool.admin.dao.UserDetailDAO;
import com.techm.trackingtool.admin.form.AddUserToolForm;
import com.techm.trackingtool.services.UserdetailsService;
import com.techm.trackingtool.util.LindeToolLogManager;
@Controller
public class AddUserToolController {

	LindeToolLogManager logMgr = new LindeToolLogManager(AddUserToolController.class.getName());

	@Autowired
	UserdetailsService service;
	
	@Autowired
	MessageSource messageSource;
	
	@RequestMapping(value = { "/addUser.spring" }, method = RequestMethod.GET)
	public ModelAndView userPage(HttpServletRequest req,HttpServletResponse res, AddUserToolForm addUserToolForm,BindingResult result)
		{
		
		
		logMgr.logMessage("DEBUG", "Inside userPage");
		
		return new ModelAndView("Add_User");
		
	}
	
	@RequestMapping(value = { "/addUser.spring" }, method = RequestMethod.POST)	
	public ModelAndView addUserTool(HttpServletRequest req,@ModelAttribute("addUserToolForm") AddUserToolForm addUserToolForm,BindingResult result) 
		{
		logMgr.logMessage("INFO", "Inside addUserTool");
		
		HttpSession httpSession=req.getSession(false);
		User creator=(User)httpSession.getAttribute("userInformation");
		//UserDetailDAO userDetailDAO=new UserDetailDAO();
		User user=new User();
		user.setFirstName(addUserToolForm.getFirstname());
		user.setLastName(addUserToolForm.getLastname());
		user.setUserID(addUserToolForm.getUserid().toUpperCase());
		user.setPassword(addUserToolForm.getPassword());
		user.setEmailId(addUserToolForm.getMailid1());
		user.setRoleId((int)(Integer.parseInt(addUserToolForm.getRole())));
		if(!addUserToolForm.getLocation().equalsIgnoreCase("select"))
		  user.setLocation(addUserToolForm.getLocation());

		if(creator.getRoleId().intValue()==3){ 
		user.setApprovalStatus("Approved");
		}
		else{
			user.setApprovalStatus("Pending");
			}
		logMgr.logMessage("DEBUG","user id is "+addUserToolForm.getUserid()+" ");
		try{
			service.addUser(user,creator); 
		}catch (Exception e) {
			logMgr.logStackTrace("ERROR", "Exception in addUserTool()", e);
			logMgr.logMessage("DEBUG","user id is :::: "+addUserToolForm.getUserid()+" ");
			return new ModelAndView("Add_User","addUserToolForm",addUserToolForm).addObject("message", messageSource.getMessage("error.msg", null, null));
		}
		
	
		
		
		return new ModelAndView("Info_Saved", "message",addUserToolForm.getFirstname()+","+addUserToolForm.getLastname()+" "+messageSource.getMessage("msg.user", null, null));
		
	}
	
	
	@RequestMapping(value = { "/addUser_reg.spring" }, method = RequestMethod.POST)
	public ModelAndView registerUserTool(HttpServletRequest req,
			HttpServletResponse res, AddUserToolForm addUserToolForm) 
		{
		
		LindeToolLogManager lindeLogMgr = new LindeToolLogManager(LoginController.class.getName());
		UserDetailDAO userDetailDAO=new UserDetailDAO();
		User user=new User();
		user.setFirstName(addUserToolForm.getFirstname());
		user.setLastName(addUserToolForm.getLastname());
		user.setUserID(addUserToolForm.getUserid().toUpperCase());
		user.setEmailId(addUserToolForm.getMailid1());
		user.setRoleId((int)(Integer.parseInt(addUserToolForm.getRole())));
		//user.setDomain("SATYAM");
		
		user.setApprovalStatus("Pending");
		
		System.out.println("user id is "+addUserToolForm.getUserid()+" ");
		try{
			userDetailDAO.registerUser(user); 
		}catch (Exception e) {
			
			lindeLogMgr.logStackTrace("ERROR", "Exception in registerUserTool()", e);
			return new ModelAndView("Info_Saved", "message","Data not saved due to error in the Input");
		}
		
		lindeLogMgr.logMessage("DEBUG", "Inside AddUserToolController:Login Id");
		
		
		return new ModelAndView("Info_Saved", "message","The Information has been saved successfully");
		
	}
	
	
}
