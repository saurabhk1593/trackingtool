package com.techm.trackingtool.admin.bean;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TBL_CRMAIL_DETAILS")
public class CrMailDetails {
	
	@Id
	@Column(name="CHANGE_ID", nullable = false)
	public String changeID;
	
	@Column(name="PRIORITY")
	public String priority;
	
	@Column(name="CR_MAIL_TIME")
	public Timestamp mailtime;

	public String getChangeID() {
		return changeID;
	}

	public void setChangeID(String changeID) {
		this.changeID = changeID;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Timestamp getMailtime() {
		return mailtime;
	}

	public void setMailtime(Timestamp mailtime) {
		this.mailtime = mailtime;
	}

}
