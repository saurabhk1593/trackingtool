package com.techm.trackingtool.admin.bean;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="TBL_LEAVE_DETAIL")

public class Leave {
	
	@Id
	@Column(name="USER_ID")
	String userID;
	
	@Column(name="LEAVE_START_DATE")
	String leaveStartDate;
	
	@Column(name="LEAVE_END_DATE")
	String leaveEndDate;
	
	@Column(name="REASONS")
	String reasons;
	
	@Column(name="REMARKS")
	String remarks;
	
	@Transient
	ArrayList daysList = new ArrayList();
	
	@Transient
	String name;
	
	@Transient
	String[] startDate;
	
	@Transient
	String[] endDate;
	
	@Transient
	String[] reasonCode;
	
	public String[] getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String[] reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getStartDate() {
		return startDate;
	}

	public void setStartDate(String[] startDate) {
		this.startDate = startDate;
	}

	public String[] getEndDate() {
		return endDate;
	}

	public void setEndDate(String[] endDate) {
		this.endDate = endDate;
	}

	public ArrayList getDaysList() {
		return daysList;
	}

	public void setDaysList(ArrayList daysList) {
		this.daysList = daysList;
	}
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getLeaveStartDate() {
		return leaveStartDate;
	}

	public void setLeaveStartDate(String leaveStartDate) {
		this.leaveStartDate = leaveStartDate;
	}

	public String getLeaveEndDate() {
		return leaveEndDate;
	}

	public void setLeaveEndDate(String leaveEndDate) {
		this.leaveEndDate = leaveEndDate;
	}

	public String getReasons() {
		return reasons;
	}

	public void setReasons(String reasons) {
		this.reasons = reasons;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	

}
