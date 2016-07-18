package com.techm.trackingtool.admin.form;

import javax.persistence.Column;

public class ModuleForm {
	
	private String modulename;
	private String description;
	private String priority;
	private String crspermonth;
	private String docavailability;

	
	public String getModulename() {
		return modulename;
	}
	public void setModulename(String modulename) {
		this.modulename = modulename;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getCrspermonth() {
		return crspermonth;
	}
	public void setCrspermonth(String crspermonth) {
		this.crspermonth = crspermonth;
	}
	public String getDocavailability() {
		return docavailability;
	}
	public void setDocavailability(String docavailability) {
		this.docavailability = docavailability;
	}
	
}
