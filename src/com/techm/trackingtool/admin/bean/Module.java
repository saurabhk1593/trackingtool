package com.techm.trackingtool.admin.bean;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="TBL_MODULE")
@NamedQueries({@NamedQuery(name="selModules",query="FROM Module order by modulename"),
	@NamedQuery(name="selModulebyname",query="FROM Module WHERE modulename = :appname")
		
})
public class Module {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MODULE_ID_SEQ")
	@SequenceGenerator(name= "MODULE_ID_SEQ",initialValue=1,allocationSize=50,sequenceName="MODULEID_SEQUENCE")
    //@SequenceGenerator(name = "MODULE_ID_SEQ", sequenceName = "MODULEID_SEQUENCE")
	@Column(name = "MODULE_ID", nullable = false)
	public int moduleid;

	@Column(name = "MODULE_NAME")
	public String modulename;
	
	@Column(name = "DESCRIPTION")
	public String description;
	
	@Column(name = "PRIORITY")
	public String priority;
	
	@Column(name = "TICKETSPERMONTH")
	public String crspermonth;
	

	@Column(name = "DOCAVAILABILITY")
	public String docavailability;
	
	/*@OneToOne(cascade=CascadeType.ALL)
	@JoinTable(name="TBL_OWNER_MODULE", joinColumns={@JoinColumn(name="MODULE_ID")},inverseJoinColumns={@JoinColumn(name="USER_ID")})
	private User user;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	} */

	@ManyToMany  
	@JoinTable(name="TBL_OWNER_MODULE", joinColumns={@JoinColumn(name="MODULE_ID")},inverseJoinColumns={@JoinColumn(name="USER_ID")})
	public Set<User> userid=new HashSet<User>();

	public Set<User> getUserid() {
		return userid;
	}


	public void setUserid(Set<User> userid) {
		this.userid = userid;
	}


	public String getCrspermonth() {
		return crspermonth;
	}


	public void setCrspermonth(String crspermonth) {
		this.crspermonth = crspermonth;
	}

	public int getModuleid() {
		return moduleid;
	}


	public void setModuleid(int moduleid) {
		this.moduleid = moduleid;
	}


	


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


	public String getDocavailability() {
		return docavailability;
	}


	public void setDocavailability(String docavailability) {
		this.docavailability = docavailability;
	}
	
}
