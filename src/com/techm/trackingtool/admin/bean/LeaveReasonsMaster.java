package com.techm.trackingtool.admin.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TBL_LEAVE_REASON_MASTER")
public class LeaveReasonsMaster {
	
	@Id
	@Column(name="ID")
	String leaveId;
	
	@Column(name="LEAVE_REASON_CODE")
	String leaveReasonCode;
	
	@Column(name="LEAVE_REASON_DESC")
	String leaveReasonDesc;

	
	public String getLeaveId() {
		return leaveId;
	}

	
	public void setLeaveId(String leaveId) {
		this.leaveId = leaveId;
	}

	
	public String getLeaveReasonCode() {
		return leaveReasonCode;
	}

	
	public void setLeaveReasonCode(String leaveReasonCode) {
		this.leaveReasonCode = leaveReasonCode;
	}

	
	public String getLeaveReasonDesc() {
		return leaveReasonDesc;
	}

	
	public void setLeaveReasonDesc(String leaveReasonDesc) {
		this.leaveReasonDesc = leaveReasonDesc;
	}

}
