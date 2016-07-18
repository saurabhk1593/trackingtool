package com.techm.trackingtool.admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.techm.trackingtool.admin.bean.User;
import com.techm.trackingtool.admin.dao.AdminDAO;
import com.techm.trackingtool.admin.dao.UserDetailDAO;
import com.techm.trackingtool.admin.form.AddApplicationForm;
import com.techm.trackingtool.admin.form.AddUserForm;
import com.techm.trackingtool.admin.form.AddUserToolForm;
import com.techm.trackingtool.services.UserdetailsService;
import com.techm.trackingtool.util.HashMap;
import com.techm.trackingtool.util.LindeList;
import com.techm.trackingtool.util.LindeMap;
import com.techm.trackingtool.util.LindeToolLogManager;

@Controller
public class AddUserController  {
	
	@Autowired
	UserdetailsService service;
	
	private LindeMap userMap;
	private LindeList rolesList;
	private User user;
	LindeMap model = new LindeMap();
	LindeToolLogManager lindeLogMgr = new LindeToolLogManager(AddUserController.class.getName());
	
	
	@RequestMapping(value = { "/approveUser.spring" }, method = RequestMethod.GET)
	public ModelAndView user1Page(HttpServletRequest req,HttpServletResponse res, AddUserToolForm addUserToolForm,BindingResult result)
		{
		
		
		lindeLogMgr.logMessage("DEBUG", "Inside user1Page");
		
		return new ModelAndView("Add_User1");
		
	}
	
	
	
	
	@RequestMapping(value = { "/approveUser_Details.spring" }, method = RequestMethod.POST)
	public ModelAndView getUserDetail(HttpServletRequest request,
            HttpServletResponse response,AddUserForm addUserForm,BindException errors) {
	//	UserDetailDAO userDAO = new UserDetailDAO();
	//	AdminDAO adminDAO = new AdminDAO();
		String selUserId = addUserForm.getUserId();		

		lindeLogMgr.logMessage("DEBUG","---Inside get user Details--------->"+addUserForm.getUserId());
		
		//AddUserForm addUserForm = (AddUserForm)command;
		//System.out.println("Inside Bind--->user:"+addUserForm.getUserId());
		//String view = getSuccessView();
		//System.out.println("Inside get user Details");
		userMap = service.checkUsersAwaitingApproval();
		User userDetails = (User) userMap.get(selUserId);
		rolesList = service.getRoles();
		model.put("userMap", userMap);
		
		model.put("userDetails", userDetails);
		model.put("roles", rolesList);
		//model.put("2", "Gano");
		return new ModelAndView("Add_User1", "model", model);
	}
	
	
	@RequestMapping(value = { "/approveUser.spring" }, method = RequestMethod.POST)		
	public ModelAndView approveUser(HttpServletRequest request,
            HttpServletResponse response,AddUserForm addUserForm,BindException errors) {
		
		UserDetailDAO userDAO = new UserDetailDAO();
		String selUserId = addUserForm.getUserId();
		int selRoleId = addUserForm.getRoleId();
		HttpSession httpSession=request.getSession();
		User creator=(User)httpSession.getAttribute("userInformation");
		boolean successFlag=false;
		HashMap message = new HashMap();
		//String test = null;
		//try{userMap = referenceData(request);}catch(Exception ex){}
		lindeLogMgr.logMessage("DEBUG","Inside get user Details------------------>"+addUserForm.getUserId());
		//userMap = userDAO.checkUsersAwaitingApproval();
		
		userMap = userDAO.checkUsersAwaitingApproval();
		User userDetails = (User) userMap.get(selUserId);
		userDetails.setRoleId(selRoleId);
		userDetails.setApprovalStatus("Approved");
		try
		{
			successFlag = userDAO.approveUser(userDetails,creator);
		}
		catch (Exception e) {
			message.put("messageCont","User access approval failed. Please try again...");
		}
		
		if(successFlag)
			message.put("messageCont","User has been provided access to the system");
		else
			message.put("messageCont","User access approval failed. Please try again...");
		return new ModelAndView("Info_Saved", "message", message);
	}
	
	public ModelAndView DenyUser(HttpServletRequest request,
            HttpServletResponse response,AddUserForm addUserForm,BindException errors) {
		
		UserDetailDAO userDAO = new UserDetailDAO();
		String selUserId = addUserForm.getUserId();
		int selRoleId = addUserForm.getRoleId();
		HttpSession httpSession=request.getSession();
		User creator=(User)httpSession.getAttribute("userInformation");
		boolean successFlag=false;
		HashMap message = new HashMap();
		//String test = null;
		//try{userMap = referenceData(request);}catch(Exception ex){}
		lindeLogMgr.logMessage("DEBUG","Inside get user Details------------------>"+addUserForm.getUserId());
		//userMap = userDAO.checkUsersAwaitingApproval();
		
		userMap = userDAO.checkUsersAwaitingApproval();
		User userDetails = (User) userMap.get(selUserId);
		userDetails.setRoleId(selRoleId);
		userDetails.setApprovalStatus("Denied");
		try
		{
			successFlag = userDAO.approveUser(userDetails,creator);
		}
		catch (Exception e) {
			message.put("messageCont","User access approval failed. Please try again...");
		}
		
		if(successFlag)
			message.put("messageCont","User has been provided access to the system");
		else
			message.put("messageCont","User access approval failed. Please try again...");
		return new ModelAndView("Info_Saved", "message", message);
	}
	
	

}

