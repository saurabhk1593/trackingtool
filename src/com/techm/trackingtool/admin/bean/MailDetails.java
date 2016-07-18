package com.techm.trackingtool.admin.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/*@Entity
@Table(name="TBL_APPLICATION")
@NamedQueries({@NamedQuery(name="selApplications",query="FROM Application order by applicationname"),
	@NamedQuery(name="selApplicationbyname",query="FROM Application WHERE applicationname = :appname")
		
})*/
@Entity
@Table(name="TBL_MAIL_DETAILS")

public class MailDetails {
	
	
	@Id
	@Column(name="INCIDENT_ID", nullable = false)
	public String incidentID;
	
	@Column(name="PRIORITY")
	public String priority;
	
	@Column(name="MAIL_DATE")
	public String maildate;
	
	public String getIncidentID() {
		return incidentID;
	}

	public void setIncidentID(String incidentID) {
		this.incidentID = incidentID;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getMaildate() {
		return maildate;
	}

	public void setMaildate(String maildate) {
		this.maildate = maildate;
	}

}
