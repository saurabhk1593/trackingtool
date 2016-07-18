package com.techm.trackingtool.admin.form;

import java.util.ArrayList;
import java.util.List;

import com.techm.trackingtool.admin.bean.Module;

public class ReviewReportForm {

	public String changeId="N";
	public String briefDescription="N";
	public String assignee="N";
	public String contiAssignedto="N";
	public String phase="N";
	public String priority="N";
	public String openDate="N";
	public String requestedDate="N";
	public String plannedStart="N";
	public String plannedEnd="N";
	public String estimateEffort="N";
	public String affectedCIs="N";
	public String ageing="N";
	public String targetDate="N";
	
		
	public String from_dt="";
	public String to_dt;
	//public String criteria;
	
	private String selectmodule="";	
	private List<Module> moduleList = new ArrayList<Module>();
	private String[] crfields;
	private String[] incidentfields;
	private String reportType;
	
	
	
	public String[] getIncidentfields() {
		return incidentfields;
	}
	public void setIncidentfields(String[] incidentfields) {
		this.incidentfields = incidentfields;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String[] getCrfields() {
		return crfields;
	}
	public void setCrfields(String[] crfields) {
		this.crfields = crfields;
	}
	public String getSelectmodule() {
		return selectmodule;
	}
	public void setSelectmodule(String selectmodule) {
		this.selectmodule = selectmodule;
	}
	public String getChangeId() {
		return changeId;
	}
	public void setChangeId(String changeId) {
		this.changeId = changeId;
	}
	public String getBriefDescription() {
		return briefDescription;
	}
	public void setBriefDescription(String briefDescription) {
		this.briefDescription = briefDescription;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public String getContiAssignedto() {
		return contiAssignedto;
	}
	public void setContiAssignedto(String contiAssignedto) {
		this.contiAssignedto = contiAssignedto;
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
	public String getAffectedCIs() {
		return affectedCIs;
	}
	public void setAffectedCIs(String affectedCIs) {
		this.affectedCIs = affectedCIs;
	}
	public String getAgeing() {
		return ageing;
	}
	public void setAgeing(String ageing) {
		this.ageing = ageing;
	}
	public String getTargetDate() {
		return targetDate;
	}
	public void setTargetDate(String targetDate) {
		this.targetDate = targetDate;
	}
	public String getFrom_dt() {
		return from_dt;
	}
	public void setFrom_dt(String from_dt) {
		this.from_dt = from_dt;
	}
	public String getTo_dt() {
		return to_dt;
	}
	public void setTo_dt(String to_dt) {
		this.to_dt = to_dt;
	}
	public List<Module> getModuleList() {
		return moduleList;
	}
	public void setModuleList(List<Module> moduleList) {
		this.moduleList = moduleList;
	}

	
	
	
	
}
