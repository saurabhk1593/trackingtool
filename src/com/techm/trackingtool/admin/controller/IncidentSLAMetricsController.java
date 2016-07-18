package com.techm.trackingtool.admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.techm.trackingtool.admin.bean.User;
import com.techm.trackingtool.admin.dao.SLAMetricsDAO;
import com.techm.trackingtool.admin.form.AddApplicationForm;
import com.techm.trackingtool.admin.vo.HomePageVO;
import com.techm.trackingtool.admin.vo.MetricsVO;
import com.techm.trackingtool.services.CRInfoService;
import com.techm.trackingtool.services.TicketInfoServices;
import com.techm.trackingtool.util.LindeMap;
import com.techm.trackingtool.util.LindeToolLogManager;

@Controller
public class IncidentSLAMetricsController {
	
	static LindeToolLogManager lindeLogMgr = new LindeToolLogManager(IncidentSLAMetricsController.class.getName());
	private LindeMap homeMap = new LindeMap();
	private MetricsVO metricsVO = new MetricsVO();
	HomePageVO homeVO = new HomePageVO();
	
	@Autowired
	TicketInfoServices ticketInfoService;
	
	@RequestMapping(value = { "/slaIncidentMetrics.spring" }, method = RequestMethod.GET)
	public ModelAndView defaultPage(HttpServletRequest request,
			HttpServletResponse res) {
			String errormessage = "";
			lindeLogMgr.logMessage("DEBUG", "Inside SLA Incident metrics Page");
			HttpSession userSession = request.getSession(false);
			User userInfo=(User)userSession.getAttribute("userInformation");
			lindeLogMgr.logMessage("DEBUG", "userInfo is: "+userInfo.getLocation());
			try{				
				metricsVO = ticketInfoService.getIncidentSlaMetrics(userInfo);				
				homeMap.put("metricsVO", metricsVO);
				homeMap.put("userInfo",userInfo);
				homeVO.setHomePageMap(homeMap); 				
			}
			catch(Exception e)
	  	    {
	          errormessage = e.getMessage();
	          lindeLogMgr.logStackTrace("ERROR", "Error in metrics controller",e);
	          return new ModelAndView("SLAIncident_Metrics", "message",errormessage);
	  	    }
			
			return new ModelAndView("SLAIncident_Metrics","homeVO",homeVO);		
	}
	
	

}
