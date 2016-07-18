package com.techm.trackingtool.admin.vo;

import com.techm.trackingtool.util.LindeMap;

public class CrSummaryVO {
	
	private LindeMap byPriority;
	private LindeMap byAge;
	private LindeMap byStatus;
	private String uploadDate;
	private LindeMap byAgeModuleWise;
	
	//added by Chandan
	private LindeMap byImpSlaDetails;
	private LindeMap byOpenSlaDetails;
	private LindeMap byClosedSlaDetails;
	private LindeMap byCRDetails;
	private LindeMap byPMOpenSlaDetails;
	
	private LindeMap byPMOnOpenSlaDetails;
	private LindeMap byPMClosedSlaDetails;
	private LindeMap byPMOnClosedSlaDetails;
	
	public LindeMap getByImpSlaDetails() {
		return byImpSlaDetails;
	}
	public void setByImpSlaDetails(LindeMap byImpSlaDetails) {
		this.byImpSlaDetails = byImpSlaDetails;
	}
	public LindeMap getByOpenSlaDetails() {
		return byOpenSlaDetails;
	}
	public void setByOpenSlaDetails(LindeMap byOpenSlaDetails) {
		this.byOpenSlaDetails = byOpenSlaDetails;
	}
	public LindeMap getByClosedSlaDetails() {
		return byClosedSlaDetails;
	}
	public void setByClosedSlaDetails(LindeMap byClosedSlaDetails) {
		this.byClosedSlaDetails = byClosedSlaDetails;
	}
	public LindeMap getByCRDetails() {
		return byCRDetails;
	}
	public void setByCRDetails(LindeMap byCRDetails) {
		this.byCRDetails = byCRDetails;
	}
	public LindeMap getByPMOpenSlaDetails() {
		return byPMOpenSlaDetails;
	}
	public void setByPMOpenSlaDetails(LindeMap byPMOpenSlaDetails) {
		this.byPMOpenSlaDetails = byPMOpenSlaDetails;
	}
	public LindeMap getByPMOnOpenSlaDetails() {
		return byPMOnOpenSlaDetails;
	}
	public void setByPMOnOpenSlaDetails(LindeMap byPMOnOpenSlaDetails) {
		this.byPMOnOpenSlaDetails = byPMOnOpenSlaDetails;
	}
	public LindeMap getByPMClosedSlaDetails() {
		return byPMClosedSlaDetails;
	}
	public void setByPMClosedSlaDetails(LindeMap byPMClosedSlaDetails) {
		this.byPMClosedSlaDetails = byPMClosedSlaDetails;
	}
	public LindeMap getByPMOnClosedSlaDetails() {
		return byPMOnClosedSlaDetails;
	}
	public void setByPMOnClosedSlaDetails(LindeMap byPMOnClosedSlaDetails) {
		this.byPMOnClosedSlaDetails = byPMOnClosedSlaDetails;
	}

	
	public LindeMap getByPriority() {
		return byPriority;
	}
	public void setByPriority(LindeMap byPriority) {
		this.byPriority = byPriority;
	}
	public LindeMap getByAge() {
		return byAge;
	}
	public void setByAge(LindeMap byAge) {
		this.byAge = byAge;
	}
	public LindeMap getByStatus() {
		return byStatus;
	}
	public void setByStatus(LindeMap byStatus) {
		this.byStatus = byStatus;
	}
	public String getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}
	public LindeMap getByAgeModuleWise() {
		return byAgeModuleWise;
	}
	public void setByAgeModuleWise(LindeMap byAgeModuleWise) {
		this.byAgeModuleWise = byAgeModuleWise;
	}

}
