package com.techm.trackingtool.admin.form;

public class AddApplicationForm {
	

	
	public String username;
	public String password;
	public String applicationname;
	public String region;
	public String url;
	public String LOB;
	public String supporttime;
	public String priority;
	public String technology;
	public String OSVersion;
	public String description;
	public String applicationage;
	public String applicationsize;
	public String ticketspermonth;
	public String srcavailability;
	public String docavailability;
	public String stability;
	public String appintegration;
	public String intraorextra;
	public String stagingavailabilty;
	public String futuredirection;
	
	
	public String getApplicationname() {
		return applicationname;
	}
	public void setApplicationname(String applicationname) {
		this.applicationname = applicationname;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getTechnology() {
		return technology;
	}
	public void setTechnology(String technology) {
		this.technology = technology;
	}
	public String getOSVersion() {
		return OSVersion;
	}
	public void setOSVersion(String version) {
		OSVersion = version;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getApplicationage() {
		return applicationage;
	}
	public void setApplicationage(String applicationage) {
		this.applicationage = applicationage;
	}
	public String getApplicationsize() {
		return applicationsize;
	}
	public void setApplicationsize(String applicationsize) {
		this.applicationsize = applicationsize;
	}
	public String getTicketspermonth() {
		return ticketspermonth;
	}
	public void setTicketspermonth(String ticketspermonth) {
		this.ticketspermonth = ticketspermonth;
	}
	public String getSrcavailability() {
		return srcavailability;
	}
	public void setSrcavailability(String srcavailability) {
		this.srcavailability = srcavailability;
	}
	public String getDocavailability() {
		return docavailability;
	}
	public void setDocavailability(String docavailability) {
		this.docavailability = docavailability;
	}
	public String getStability() {
		return stability;
	}
	public void setStability(String stability) {
		this.stability = stability;
	}
	public String getAppintegration() {
		return appintegration;
	}
	public void setAppintegration(String appintegration) {
		this.appintegration = appintegration;
	}
	public String getIntraorextra() {
		return intraorextra;
	}
	public void setIntraorextra(String intraorextra) {
		this.intraorextra = intraorextra;
	}
	public String getStagingavailabilty() {
		return stagingavailabilty;
	}
	public void setStagingavailabilty(String stagingavailabilty) {
		this.stagingavailabilty = stagingavailabilty;
	}
	public String getFuturedirection() {
		return futuredirection;
	}
	public void setFuturedirection(String futuredirection) {
		this.futuredirection = futuredirection;
	}
	
	public AddApplicationForm(){
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLOB() {
		return LOB;
	}
	public void setLOB(String lob) {
		LOB = lob;
	}
	public String getSupporttime() {
		return supporttime;
	}
	public void setSupporttime(String supporttime) {
		this.supporttime = supporttime;
	}
	public AddApplicationForm(String username, String password,
			String applicationname, String region, String url, String lob,
			String supporttime, String priority, String technology,
			String version, String description, String applicationage,
			String applicationsize, String ticketspermonth,
			String srcavailability, String docavailability, String stability,
			String appintegration, String intraorextra,
			String stagingavailabilty, String futuredirection) {
		super();
		this.username = username;
		this.password = password;
		this.applicationname = applicationname;
		this.region = region;
		this.url = url;
		LOB = lob;
		this.supporttime = supporttime;
		this.priority = priority;
		this.technology = technology;
		OSVersion = version;
		this.description = description;
		this.applicationage = applicationage;
		this.applicationsize = applicationsize;
		this.ticketspermonth = ticketspermonth;
		this.srcavailability = srcavailability;
		this.docavailability = docavailability;
		this.stability = stability;
		this.appintegration = appintegration;
		this.intraorextra = intraorextra;
		this.stagingavailabilty = stagingavailabilty;
		this.futuredirection = futuredirection;
	}
	
	


}
