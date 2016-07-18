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

import com.techm.trackingtool.admin.bean.Application;
import com.techm.trackingtool.admin.bean.Module;
import com.techm.trackingtool.admin.bean.User;
import com.techm.trackingtool.admin.dao.DeleteUserDAO;
import com.techm.trackingtool.admin.dao.TicketInfoDAO;
import com.techm.trackingtool.admin.form.AddUserToolForm;
import com.techm.trackingtool.admin.form.DeleteUserForm;
import com.techm.trackingtool.services.UserdetailsService;
import com.techm.trackingtool.util.HashMap;
import com.techm.trackingtool.util.LindeList;
import com.techm.trackingtool.util.LindeMap;
import com.techm.trackingtool.util.LindeToolLogManager;

@Controller
public class DeleteUserController {
	
	
	private LindeMap userMap;
	private LindeList rolesList;
	private User user;
	LindeMap model = new LindeMap();
	private static final LindeToolLogManager lindeLogMgr = new LindeToolLogManager(DeleteUserController.class.getName());
	
	@Autowired
	UserdetailsService service;
	
	
	@RequestMapping(value = { "/deleteUser.spring" }, method = RequestMethod.GET)
	public ModelAndView userPage(HttpServletRequest req,HttpServletResponse res, AddUserToolForm addUserToolForm,BindingResult result)
		{
		
		lindeLogMgr.logMessage("DEBUG", "Inside userPage");
	//	DeleteUserDAO deleteUserDAO=new DeleteUserDAO();
		LindeList userlist=new LindeList();
		LindeList applicationlist=new LindeList();
		LindeMap model=new LindeMap();
		userlist=service.getDelUserInfo();
			model.put("userlist",userlist);
		
		
			applicationlist=service.getDelApplicationInfo();
			model.put("applicationlist",applicationlist);
		
		return new ModelAndView("DeleteUser","model",model);
		
	}
	
	@RequestMapping(value = { "/deleteUser_details.spring" }, method = RequestMethod.GET)
	public ModelAndView getDetails(HttpServletRequest request,
            HttpServletResponse response,DeleteUserForm deleteUserForm,BindingResult result) {
		
		String category=deleteUserForm.getCategory();
	//	DeleteUserDAO deleteUserDAO=new DeleteUserDAO();
		LindeList userlist=new LindeList();
		LindeList applicationlist=new LindeList();
		LindeMap model=new LindeMap();
		if(category.equals("user"))
		{
			userlist=service.getDelUserInfo();
			model.put("userlist",userlist);
		}
		
		if(category.equals("application"))
		{
			applicationlist=service.getDelApplicationInfo();
			model.put("aplicationlist",applicationlist);
		}
		
		return new ModelAndView("UserDeleted", "model", model);
	}
	
	@RequestMapping(value = { "/deleteUser.spring" }, method = RequestMethod.POST)
		public ModelAndView deleteUser(HttpServletRequest request,
            HttpServletResponse response,DeleteUserForm deleteUserForm,BindingResult result) {
		
		//DeleteUserDAO deleteUserDAO =new DeleteUserDAO();
		String category = deleteUserForm.getCategory();
		String applicationid = deleteUserForm.getApplication();
		String userid = deleteUserForm.getUsername();
		HttpSession httpSession=request.getSession();
		User creator=(User)httpSession.getAttribute("userInformation");
		lindeLogMgr.logMessage("DEBUG","Inside delete user/application Details------------------>"+deleteUserForm.getApplication()+" "+deleteUserForm.getUsername());
		boolean successFlag = true;
		if(category.equals("username"))
		{
			User user=service.getUser(userid);
			try{
				service.deleteUser(user,creator);
			}
			catch (Exception e) {
				successFlag=false;
			}
		}
		
		if(category.equals("applicationid"))
		{
			
			Module application=service.getApplication(applicationid);
			try {
				service.deleteApplication(application);
			} catch (Exception ex) {
				lindeLogMgr.logStackTrace("ERROR", ex.getMessage(), ex);
			}
		}
		
		
		
		HashMap message = new HashMap();
		if(successFlag)
			message.put("messageCont","User has been provided access to the system");
		else
			message.put("messageCont","User access approval failed. Please try again...");
		return new ModelAndView("UserDeleted", "message", message);
	}
		
	
	public ModelAndView checkUserTickets(HttpServletRequest request,
            HttpServletResponse response,DeleteUserForm deleteUserForm,BindingResult result) {
		
		String category=deleteUserForm.getCategory();
		String userid = deleteUserForm.getUsername();
		String deletionStatus="";
		HttpSession userSession = request.getSession();
		String year="";
		userSession.setAttribute("archivalyear",(String)userSession.getAttribute("archivalyear"));
		year=(String)userSession.getAttribute("archivalyear");
		
		String month="ALL";
	//	DeleteUserDAO deleteUserDAO=new DeleteUserDAO();
	//	TicketInfoDAO ticketInfoDAO=new TicketInfoDAO();
		User user=service.getUser(userid);
		LindeList userlist=new LindeList();
		LindeList applicationlist=new LindeList();
		LindeMap model=new LindeMap();
		if(category.equals("user"))
		{
			userlist=service.getDelUserInfo();
			model.put("userlist",userlist);
		}
		
		if(category.equals("application"))
		{
			applicationlist=service.getDelApplicationInfo();
			model.put("aplicationlist",applicationlist);
		}
		LindeList ticketList=(LindeList)service.getTTDetailedInfoBySeverity("Severity 1','Severity 2','Severity 3','Severity 4','Severity 5", "severity","", user,month,year);
		if(ticketList.size()!=0){deletionStatus="NO";}
		model.put("deletionStatus", deletionStatus);
		return new ModelAndView("DeleteUser", "model", model);
	}
}



