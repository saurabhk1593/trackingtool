package com.techm.trackingtool.admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.techm.trackingtool.admin.bean.User;
import com.techm.trackingtool.admin.dao.CRInfoDAO;
import com.techm.trackingtool.admin.form.UploadCRForm;
import com.techm.trackingtool.admin.vo.CrSummaryVO;
import com.techm.trackingtool.admin.vo.HomePageVO;
import com.techm.trackingtool.services.CRInfoService;
import com.techm.trackingtool.util.LindeMap;
import com.techm.trackingtool.util.LindeToolLogManager;

@Controller
public class CrSLAController {
	
	private static final LindeToolLogManager logMgr = new LindeToolLogManager(CrSLAController.class.getName());
	
	@Autowired
	CRInfoService crinfoService;
	
	@RequestMapping(value = { "/crsla.spring" }, method = RequestMethod.GET)
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession userSession = request.getSession(false);
		User userInfo=(User)userSession.getAttribute("userInformation");
		CrSummaryVO crSummary=new CrSummaryVO();
		HomePageVO homeVO = new HomePageVO();
		 LindeMap homeMap = new LindeMap() ;
		 
		try
		{
		
		 crSummary = crinfoService.getCRSLADetails(userInfo);
  	     homeMap.put("ttSummary",crSummary);
  	     homeMap.put("userInfo",userInfo);
  	 
  	    }
  	    catch(Exception e)
  	    {
          String errormessage = e.getMessage(); 
          logMgr.logStackTrace("ERROR", errormessage, e);
          return new ModelAndView("SlaReportjsp", "message","Error in input");
  	    }

      // System.out.println("Upload Status:"+status); 
       homeVO.setHomePageMap(homeMap);
	
            return new ModelAndView("SlaReportjsp", "homeVO",homeVO);
		
	}
	
	@RequestMapping(value = { "/crsla.spring" }, method = RequestMethod.POST)
	public ModelAndView downloadexcelData(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession userSession = request.getSession(false);
		User userInfo=(User)userSession.getAttribute("userInformation");
		CrSummaryVO crSummary=new CrSummaryVO();
		HomePageVO homeVO = new HomePageVO();
		 LindeMap homeMap = new LindeMap() ;
		 
		try
		{
		
		 crSummary = crinfoService.getCRSLADetails(userInfo);
  	     homeMap.put("ttSummary",crSummary);
  	     homeMap.put("userInfo",userInfo);
  	 
  	    }
  	    catch(Exception e)
  	    {
          String errormessage = e.getMessage(); 
          logMgr.logStackTrace("ERROR", errormessage, e);
          return new ModelAndView("SlaReportjsp", "message","Error in input");
  	    }

      // System.out.println("Upload Status:"+status); 
       homeVO.setHomePageMap(homeMap);
	
            return new ModelAndView("SlaReportjsp", "homeVO",homeVO);
		
	}

}
