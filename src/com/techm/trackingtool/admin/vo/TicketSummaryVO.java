package com.techm.trackingtool.admin.vo;

import com.techm.trackingtool.util.LindeMap;

public class TicketSummaryVO {
	
	private LindeMap bySeverity;
	private LindeMap byAge;
	private LindeMap byAgeRegionWise;
	private LindeMap byStatus;
	private String uploadDate;
	public LindeMap getBySeverity() {
		return bySeverity;
	}
	public void setBySeverity(LindeMap bySeverity) {
		this.bySeverity = bySeverity;
	}
	public LindeMap getByAge() {
		return byAge;
	}
	public void setByAge(LindeMap byAge) {
		this.byAge = byAge;
	}
	public LindeMap getByAgeRegionWise() {
		return byAgeRegionWise;
	}
	public void setByAgeRegionWise(LindeMap byAgeRegionWise) {
		this.byAgeRegionWise = byAgeRegionWise;
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

}
