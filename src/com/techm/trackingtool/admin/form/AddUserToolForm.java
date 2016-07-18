package com.techm.trackingtool.admin.form;

public class AddUserToolForm {

	String userid="";
	String password="";	
	String firstname="";
	String lastname="";
	String mailid1="";
	String role="";
	String location="";
	
	public String getMailid1() {
		return mailid1;
	}
	public void setMailid1(String mailid1) {
		this.mailid1 = mailid1;
	}
	
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
