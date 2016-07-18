package com.techm.trackingtool.admin.vo;

public class AdminVO {
	
	private long usersAwaitingApproval;
	private long appWithMinUser;
	public long getUsersAwaitingApproval() {
		return usersAwaitingApproval;
	}
	public void setUsersAwaitingApproval(long usersAwaitingApproval) {
		this.usersAwaitingApproval = usersAwaitingApproval;
	}
	public long getAppWithMinUser() {
		return appWithMinUser;
	}
	public void setAppWithMinUser(long appWithMinUser) {
		this.appWithMinUser = appWithMinUser;
	}

}
