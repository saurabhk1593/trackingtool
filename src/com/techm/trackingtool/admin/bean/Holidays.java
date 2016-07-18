package com.techm.trackingtool.admin.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TBL_HOLIDAY_DETAILS")

public class Holidays {
	
	@Id
	@Column(name="H_DATE")
	String holidayDate;
	
	@Column(name="HOLIDAY_DESC")
	String description;

	
	public String getHolidayDate() {
		return holidayDate;
	}

	
	public void setHolidayDate(String holidayDate) {
		this.holidayDate = holidayDate;
	}

	
	public String getDescription() {
		return description;
	}

	
	public void setDescription(String description) {
		this.description = description;
	}

}
