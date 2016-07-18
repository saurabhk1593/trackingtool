package com.techm.trackingtool.admin.controller;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.techm.trackingtool.admin.bean.Application;
import com.techm.trackingtool.admin.bean.Module;
import com.techm.trackingtool.admin.bean.User;
import com.techm.trackingtool.admin.form.MapUserForm;
import com.techm.trackingtool.services.UserdetailsService;
import com.techm.trackingtool.util.LindeList;
import com.techm.trackingtool.util.LindeMap;
import com.techm.trackingtool.util.LindeToolLogManager;
import com.techm.trackingtool.util.ResetableIterator;

@Controller
public class MapUserController {

	private LindeList applicationList;
	private LindeList userList;
	LindeMap model=new LindeMap();
	
	LindeToolLogManager lindeLogMgr = new LindeToolLogManager(MapUserController.class.getName());
	
	@Autowired
	UserdetailsService service;
	
	
	@RequestMapping(value = { "/mapUser.spring" }, method = RequestMethod.GET)
	public ModelAndView getUserDetail(HttpServletRequest request,MapUserForm mapUserForm,ModelMap model1) {
		
		
	//	int applicationid = mapUserForm.getApplicationid();
		//MapUserDAO mapUserDAO=new MapUserDAO();
	//	int appid=mapUserForm.getApplicationid();
		lindeLogMgr.logMessage("DEBUG","---mapusercontroller: Inside get user Details--------->");
		
		applicationList = service.getApplicationInfo();
		
	//	MapUserForm userForm=(MapUserForm) applicationList.get(appid);
		
		userList = service.getUserInfo();
		model.put("applicationList", applicationList);
		model.put("userList", userList);
		model1.addAttribute(model);
				
		lindeLogMgr.logMessage("DEBUG","---B4 calling JSP page");
		return new ModelAndView("MapUser", "model", model);
	}
	
	
	@RequestMapping(value = { "/mapUser_info.spring" }, method = RequestMethod.POST)
	public ModelAndView getUserInfo(HttpServletRequest request,
            HttpServletResponse response,MapUserForm mapUserForm) {
		
		
		int applicationid = mapUserForm.getApplicationid();
		//MapUserDAO mapUserDAO=new MapUserDAO();
		int appid=mapUserForm.getApplicationid();
		boolean removeCollectionStatus=false;
		System.out.println("---mapusercontroller: Inside getUserInfo--------->"+mapUserForm.getApplicationid());
			
		Module application = service.getApplication(appid);
		Set userSet=application.getUserid();
		applicationList = service.getApplicationInfo();
		LindeList existingUsersList=new LindeList();
		LindeList tempList=new LindeList();
		LindeList unmappedUsersList=new LindeList();
		unmappedUsersList = service.getUserInfo();
		
		if(!userSet.isEmpty()){
		for(Iterator iter=userSet.iterator();iter.hasNext();)
		{
			User user=(User)iter.next();
			existingUsersList.add(user);
			for(Iterator iterator=unmappedUsersList.iterator();iterator.hasNext();)
			{
				User userUnmapped=(User)iterator.next();	
				if(user.getUserID().equals(userUnmapped.getUserID()))
				{
					System.out.println("User ID existing is "+user.getUserID());
					tempList.add(userUnmapped);
					
				}
				
			}
			
		}
		}
		unmappedUsersList.removeAll(tempList);
		System.out.println("status"+removeCollectionStatus);
		model.put("applicationList", applicationList);
		model.put("userList", unmappedUsersList);
		model.put("usersList", existingUsersList);
		model.put("appid", String.valueOf(appid));
				
		return new ModelAndView("MapUser", "model", model);
	}
	
	@RequestMapping(value = { "/mapUser.spring" }, method = RequestMethod.POST)
	public ModelAndView submitUserMapping(HttpServletRequest request,
            HttpServletResponse response,MapUserForm mapUserForm) {
		
		try{
		int applicationid = mapUserForm.getApplicationid();
		String applicationname = mapUserForm.getApplicationname();
		Set userid = mapUserForm.getUserid();
		
	//	MapUserDAO mapUserDAO=new MapUserDAO();
		
		System.out.println("---mapusercontroller: ---Inside get user Details--------->"+mapUserForm.getApplicationid()+mapUserForm.getUserid().toString());
		
		Set userSetSubmitted = service.getUserSet(userid);
		
		Module application=service.getApplication(applicationid);
		Set userSetExisting = application.getUserid();
		Iterator iterSubmitted = userSetSubmitted.iterator();
		Set temp=new HashSet();
		Iterator iterExisting= userSetExisting.iterator();
		
		Iterator iterFinal=userSetSubmitted.iterator();
		ResetableIterator resetableIterator=new ResetableIterator(userSetSubmitted);
		
		
 		if(userSetSubmitted!=null){
			while (iterExisting.hasNext())
		{		
				System.out.println("Existing data loop");
				boolean notexisting=true;
				User userExisting= (User)iterExisting.next();
				System.out.println("Existing data loop");
		    while(resetableIterator.hasNext()){
				User userSubmitted=(User)resetableIterator.next();
				System.out.println("User id submitted"+userSubmitted.getUserID()+"User ID Existing "+userExisting.getUserID());
				if(userExisting.getUserID().equals(userSubmitted.getUserID())){
					System.out.println("User ID matched and not adding to submitted list");
					notexisting=false;
					//userSetSubmitted.add(userExisting);
				}} resetableIterator.reset();
		    if(notexisting){
		    	//temp.add(userExisting);
		   
		    }
		    }     
		}		
 		Iterator iterTemp= temp.iterator();
		System.out.println("Temporary set size is "+temp.size());
		while(iterTemp.hasNext()){
			System.out.println("Temporary set entered");
			User user=(User)iterTemp.next();
			System.out.println("User ID Temp"+user.getUserID());
			userSetSubmitted.add(user);
		
		}
		//userSetSubmitted.addAll(temp);
		
		
		//application=mapUserDAO.deleteApplication(application);
		
		application.setModuleid(applicationid);
		
		application.setUserid(userSetSubmitted);
		
		service.submitUserMapping(application);
		
		
		model.put("applicationList", applicationList);
		model.put("userList", userList);
		}
		catch(Exception e)
		{
			lindeLogMgr.logStackTrace("ERROR", "Exception in MapUserController ", e);
		}
		return new ModelAndView("User_Mapped", "model", model);
	}
	
	
	

}
