package com.techm.trackingtool.admin.vo;

import com.techm.trackingtool.util.LindeMap;

public class HomePageVO {
	
	/*private User UserInformation;
	private TicketSummaryVO TTInformation;
	private AdminVO AdminInformation;
	
	public User getUserInfomration(){
		return this.UserInformation;
	}
	
	public void setUserInformation(User userInformation){
		this.UserInformation = userInformation;
	}
	
	public TicketSummaryVO getTTInformation(){
		return this.TTInformation;
	}
	
	public void setTTInformation(TicketSummaryVO TTInformation){
		this.TTInformation = TTInformation;
	}
	
	public AdminVO getAdminInfomration(){
		return this.AdminInformation;
	}
	
	public void setAdminInformation(AdminVO adminInformation){
		this.AdminInformation = adminInformation;
	}*/
	
	private LindeMap homePageMap;

	public LindeMap getHomePageMap() {
		return homePageMap;
	}

	public void setHomePageMap(LindeMap homePageMap) {
		this.homePageMap = homePageMap;
	}

}
