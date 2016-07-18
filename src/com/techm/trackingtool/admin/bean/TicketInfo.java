package com.techm.trackingtool.admin.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="TBL_INCIDENT_INFO")
public class TicketInfo {

	@Id
	@Column(name = "INCIDENT_ID", nullable = false)
	public String incidentID;
	
	@Column(name = "OPEN_TIME")
	public String openTime;
	
	@Column(name = "PRIORITY")
	public String priority;
	
	@Column(name = "STATUS")
	public String status;
	
	@Column(name = "ASSIGNEE_FULLNAME")
	public String assigneeFullname;
	
	@Column(name = "CONTACT_FULLNAME")
	public String contactFullname;
	
	@Column(name = "TITLE")
	public String title;
	
	@Column(name = "MODULE")
	public String module;
	
	@Column(name = "UPDATE_TIME")
	public String updateTime;
	
	@Column(name = "REOPEN_TIME")
	public String reopenTime;
	
	@Column(name = "ASSIGNMENT_GROUP")
	public String assignmentGroup;
	
	@Column(name = "CHANGE_DATE")
	public String changeDate;
	
	/*
	public float effort_netmeeting=0;
	public float effort_discussion=0;
	public float effort_ktdoc=0;
	public float effort_gsd=0;
	*/
	
	//public String workaround;
	
	@Column(name = "CLOSE_TIME")
	public String closeTime;
	
	
	@Column(name = "RESOLVED_BY")
	public String resolvedBy;
	
	@Column(name="AGE")
	public int age;
	
/*	@Column(name = "UploadDate")
	public String uploadDate;*/
	
	
	//@Column(name = "CLASSIFICATION", nullable = false)
	
	@Transient
	public String aging;
	
	@Transient
	public String targetdate;
	
	
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getAging() {
		return aging;
	}
	public void setAging(String aging) {
		this.aging = aging;
	}
	public String getTargetdate() {
		return targetdate;
	}
	public void setTargetdate(String targetdate) {
		this.targetdate = targetdate;
	}
	public String getIncidentID() {
		return incidentID;
	}
	public void setIncidentID(String incidentID) {
		this.incidentID = incidentID;
	}
	public String getOpenTime() {
		return openTime;
	}
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAssigneeFullname() {
		return assigneeFullname;
	}
	public void setAssigneeFullname(String assigneeFullname) {
		this.assigneeFullname = assigneeFullname;
	}


	public String getContactFullname() {
		return contactFullname;
	}
	public void setContactFullname(String contactFullname) {
		this.contactFullname = contactFullname;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	
	
	
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	
	public String getReopenTime() {
		return reopenTime;
	}
	public void setReopenTime(String ReopenTime) {
		this.reopenTime = reopenTime;
	}
	public String getAssignmentGroup() {
		return assignmentGroup;
	}
	public void setAssignmentGroup(String assignmentGroup) {
		this.assignmentGroup = assignmentGroup;
	}
	public String getChangeDate() {
		return changeDate;
	}
	public void setChangeDate(String changeDate) {
		this.changeDate = changeDate;
	}
	
	public String getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}
	
	
	public String getResolvedBy() {
		return resolvedBy;
	}
	public void setResolvedBy(String resolvedBy) {
		this.resolvedBy = resolvedBy;
	}
	
	public int getAgeBy() {
		return age;
	}
	public void setAgeBy(int age) {
		this.age = age;
	}
	
	
}
