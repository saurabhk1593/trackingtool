package com.techm.trackingtool.config;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity
@Table(name="TBL_OWNER_APPLICATION")
public class Tbl_Owner_Application {
	
	@Id
	@Column(name = "APPLICATION_ID", nullable = true)
	private int applicationId;
	
	@Column(name = "USER_ID", nullable = false)
	private String userID;
	
	
	
	
//	@ManyToMany(mappedBy="applicationSet")
//	private Set<Tbl_Users> employees = new HashSet<Tbl_Users>();
	

	public int getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}
	
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	

}
