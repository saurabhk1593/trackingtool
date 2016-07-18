package com.techm.trackingtool.admin.bean;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="TBL_CR_INFO")
public class CRinfo {
	
	@Id
	@Column(name = "CHANGE_ID", nullable = false)
	public String changeId;
	
	@Column(name = "BRIEF_DESCRIPTION")
	public String briefDescription;
	
	@Column(name = "ASSIGNEE")
	public String assignee;
	
	@Column(name = "CONTI_ASSIGNED_TO")
	public String contiAssignedto;
	
	@Column(name = "PHASE")
	public String phase;
	
	@Column(name = "PRIORITY")
	public String priority;
	
	@Column(name = "OPEN_DATE")
	public String openDate;
	
	@Column(name = "REQUESTED_DATE")
	public String requestedDate;
	
	//@Column(name = "PLANNED_START")
	@Transient
	public String plannedStart;
	
	//@Column(name = "PLANNED_END")
	@Transient
	public String plannedEnd;
	
	@Column(name = "EFFORT")
	public String estimateEffort;
	
	@Column(name = "MODULE_NAME")
	public String affectedCIs;
	
	@Transient
	public String ageing;
	
	
	
	@Transient
	public String targetDate;
	
	
	public String getAffectedCIs() {
		return affectedCIs;
	}

	public void setAffectedCIs(String affectedCIs) {
		this.affectedCIs = affectedCIs;
	}

	

	public String getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(String targetDate) {
		this.targetDate = targetDate;
	}

	

	public String getAgeing() {
		return ageing;
	}

	public void setAgeing(String ageing) {
		this.ageing = ageing;
	}

	public String getChangeId() {
		return changeId;
	}

	public void setChangeId(String changeId) {
		this.changeId = changeId;
	}

	
	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getOpenDate() {
		return openDate;
	}

	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}

	public String getBriefDescription() {
		return briefDescription;
	}

	public void setBriefDescription(String briefDescription) {
		this.briefDescription = briefDescription;
	}

	public String getContiAssignedto() {
		return contiAssignedto;
	}

	public void setContiAssignedto(String contiAssignedto) {
		this.contiAssignedto = contiAssignedto;
	}

	public String getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(String requestedDate) {
		this.requestedDate = requestedDate;
	}

	public String getPlannedStart() {
		return plannedStart;
	}

	public void setPlannedStart(String plannedStart) {
		this.plannedStart = plannedStart;
	}

	public String getPlannedEnd() {
		return plannedEnd;
	}

	public void setPlannedEnd(String plannedEnd) {
		this.plannedEnd = plannedEnd;
	}

	public String getEstimateEffort() {
		return estimateEffort;
	}

	public void setEstimateEffort(String estimateEffort) {
		this.estimateEffort = estimateEffort;
	}

	
	
	
	

}
