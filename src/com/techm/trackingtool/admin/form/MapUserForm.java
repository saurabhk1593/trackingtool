package com.techm.trackingtool.admin.form;

import java.util.List;
import java.util.Set;

public class MapUserForm {

	public Set userid;
	public String applicationname;
	public String region;
	public int applicationid;
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
	public MapUserForm(Set userid, String applicationname, String region,
			int applicationid) {
		super();
		this.userid = userid;
		this.applicationname = applicationname;
		this.region = region;
		this.applicationid = applicationid;
	}
	public MapUserForm(){			
	}
	
}
