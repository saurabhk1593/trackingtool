package com.techm.trackingtool.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;


//import com.techm.trackingtool.services.ContiSchedulerService;
import com.techm.trackingtool.services.UserdetailsService;

public class ContiSchdeuler {
	
	@Autowired
	UserdetailsService servicee;
	//ContiSchedulerService contiSchedulerService;
	
//	@Scheduled(fixedDelay=60*60*1000)
	public void sendCREmailNotifications()
	{
	//	EmailDAO ldao = new EmailDAO();
		try {
			servicee.sendCREmailNotifications();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
