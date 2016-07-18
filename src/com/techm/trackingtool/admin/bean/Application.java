package com.techm.trackingtool.admin.bean;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.techm.trackingtool.config.Tbl_Users;

@Entity
@Table(name="TBL_APPLICATION")
@NamedQueries({@NamedQuery(name="selApplications",query="FROM Application order by applicationname"),
	@NamedQuery(name="selApplicationbyname",query="FROM Application WHERE applicationname = :appname")
		
})

public class Application {
	
	@Id
	@Column(name = "APPLICATION_ID", nullable = false)
	public int applicationid;
	
	@ManyToMany(cascade=CascadeType.ALL)  
	@JoinTable(name="TBL_OWNER_APPLICATION", joinColumns={@JoinColumn(name="APPLICATION_ID")},inverseJoinColumns={@JoinColumn(name="USER_ID")})
	public Set<User> userid=new HashSet<User>();
	
	@Column(name = "APPLICATION_NAME")
	public String applicationname;
	
		
	@Column(name = "REGION")
	public String region;
	
	@Column(name = "LOB")
	public String LOB;
	
	
	@Column(name = "url")
	public String url;
	
	@Column(name = "priority")
	public String priority;
	
	@Column(name = "technology")
	public String technology;
	
	@Column(name = "OSVersion")
	public String OSVersion;
	
	@Column(name = "supporttime")
	public String supporttime;
	
	@Column(name = "description")
	public String description;
	
	@Column(name = "applicationage")
	public String applicationage;
	
	@Column(name = "applicationsize")
	public String applicationsize;
	
	@Column(name = "ticketspermonth")
	public String ticketspermonth;
	
	@Column(name = "srcavailability")
	public String srcavailability;
	
	@Column(name = "docavailability")
	public String docavailability;
	
	@Column(name = "stability")
	public String stability;
	
	@Column(name = "appintegration")
	public String appintegration;
	
	@Column(name = "intraorextra")
	public String intraorextra;
	
	@Column(name = "stagingavailability")
	public String stagingavailability;
	
	@Column(name = "futuredirection")
	public String futuredirection;
	
	
	
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
	public String getStagingavailability() {
		return stagingavailability;
	}
	public void setStagingavailability(String stagingavailability) {
		this.stagingavailability = stagingavailability;
	}
	public String getFuturedirection() {
		return futuredirection;
	}
	public void setFuturedirection(String futuredirection) {
		this.futuredirection = futuredirection;
	}
	public Set getUserid() {
		return userid;
	}
	public void setUserid(Set userid) {
		this.userid = userid;
	}
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
	public int getApplicationid() {
		return applicationid;
	}
	public void setApplicationid(int applicationid) {
		this.applicationid = applicationid;
	}
	
	
	public Application() {
		
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
	public Application(Set userid, String applicationname, String region,
			String lob, int applicationid, String url, String priority,
			String technology, String version, String supporttime,
			String description, String applicationage, String applicationsize,
			String ticketspermonth, String srcavailability,
			String docavailability, String stability, String appintegration,
			String intraorextra, String stagingavailabilty,
			String futuredirection) {
		super();
		this.userid = userid;
		this.applicationname = applicationname;
		this.region = region;
		LOB = lob;
		this.applicationid = applicationid;
		this.url = url;
		this.priority = priority;
		this.technology = technology;
		OSVersion = version;
		this.supporttime = supporttime;
		this.description = description;
		this.applicationage = applicationage;
		this.applicationsize = applicationsize;
		this.ticketspermonth = ticketspermonth;
		this.srcavailability = srcavailability;
		this.docavailability = docavailability;
		this.stability = stability;
		this.appintegration = appintegration;
		this.intraorextra = intraorextra;
		this.stagingavailability = stagingavailabilty;
		this.futuredirection = futuredirection;
	}

}
