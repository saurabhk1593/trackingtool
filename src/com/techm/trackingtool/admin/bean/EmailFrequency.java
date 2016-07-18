package com.techm.trackingtool.admin.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name="TBL_EMAILFREQUENCY")
@NamedQuery(name="selSeveritySetting",query="FROM EmailFrequency")		
public class EmailFrequency {

	@Id
	@Column(name = "id", nullable = false)
	public String id;
	
	@Column(name = "PRIORITY_DESC")
	public String priority;
	
	@Column(name = "MONDAY_EMAIL")
	public String monday_email="";
	
	@Column(name = "TUESDAY_EMAIL")
	public String tuesday_email="";
	
	@Column(name = "WEDNESDAY_EMAIL")
	public String wednesday_email="";
	
	@Column(name = "THURSDAY_EMAIL")
	public String thursday_email="";
	
	@Column(name = "FRIDAY_EMAIL")
	public String friday_email="";
	
	@Column(name = "TM_CC")
	public String tm_cc="";
	
	@Column(name = "PM_CC")
	public String pm_cc="";
	
	@Column(name = "PGM_CC")
	public String pgm_cc="";
	
	
	public String getTm_cc() {
		return tm_cc;
	}
	public void setTm_cc(String tm_cc) {
		this.tm_cc = tm_cc;
	}
	public String getPgm_cc() {
		return pgm_cc;
	}
	public void setPgm_cc(String pgm_cc) {
		this.pgm_cc = pgm_cc;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getMonday_email() {
		return monday_email;
	}
	public void setMonday_email(String monday_email) {
		this.monday_email = monday_email;
	}
	public String getTuesday_email() {
		return tuesday_email;
	}
	public void setTuesday_email(String tuesday_email) {
		this.tuesday_email = tuesday_email;
	}
	public String getWednesday_email() {
		return wednesday_email;
	}
	public void setWednesday_email(String wednesday_email) {
		this.wednesday_email = wednesday_email;
	}
	public String getThursday_email() {
		return thursday_email;
	}
	public void setThursday_email(String thursday_email) {
		this.thursday_email = thursday_email;
	}
	public String getFriday_email() {
		return friday_email;
	}
	public void setFriday_email(String friday_email) {
		this.friday_email = friday_email;
	}
	public String getPm_cc() {
		return pm_cc;
	}
	public void setPm_cc(String pm_cc) {
		this.pm_cc = pm_cc;
	}
	public EmailFrequency(){}
	public String getId() {
		return id;
	}
	public void setId(String severityid) {
		this.id = severityid;
	}
	public EmailFrequency(String id, String priority, String monday_email,
			String tuesday_email, String wednesday_email,
			String thursday_email, String friday_email,
			String owners_cc, String tm_cc, String pm_cc, String pgm_cc) {
		super();
		this.id = id;
		this.priority = priority;
		this.monday_email = monday_email;
		this.tuesday_email = tuesday_email;
		this.wednesday_email = wednesday_email;
		this.thursday_email = thursday_email;
		this.friday_email = friday_email;
		//this.owners_cc = owners_cc;
		this.tm_cc = tm_cc;
		this.pgm_cc = pgm_cc;
		this.pm_cc = pm_cc;
	}
	public EmailFrequency(String id) {
		
		this.id = id;
			}
}
