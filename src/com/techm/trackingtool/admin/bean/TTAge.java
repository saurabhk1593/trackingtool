package com.techm.trackingtool.admin.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TBL_MST_AGEGROUP")
public class TTAge {
	
	@Id
	@Column(name = "AGE_GROUP", nullable = false)
	private String ageGroup;
	
	@Column(name = "RANGE_START", nullable = false)
	private String rangeStart;
	
	@Column(name = "RANGE_END", nullable = false)
	private String rangeEnd;
	
	
	public String getAgeGroup() {
		return ageGroup;
	}
	public void setAgeGroup(String ageGroup) {
		this.ageGroup = ageGroup;
	}
	public String getRangeStart() {
		return rangeStart;
	}
	public void setRangeStart(String rangeStart) {
		this.rangeStart = rangeStart;
	}
	public String getRangeEnd() {
		return rangeEnd;
	}
	public void setRangeEnd(String rangeEnd) {
		this.rangeEnd = rangeEnd;
	}
}
