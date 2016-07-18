package com.techm.trackingtool.admin.form;

public class DeleteUserForm {

	public String username;
	public String application;
	public String category;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getApplication() {
		return application;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public DeleteUserForm(String username, String application, String category) {
		super();
		this.username = username;
		this.application = application;
		this.category = category;
	}
	public DeleteUserForm() {
	}
	
	
	
	
	
}
