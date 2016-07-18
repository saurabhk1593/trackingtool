package com.techm.trackingtool.admin.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TBL_OWNER_MODULE")
public class OwnerModule {
	
	@Id
	@Column(name = "MODULE_ID", nullable = true)
	private int moduleId;
	
	@Column(name = "USER_ID", nullable = false)
	private String userId;

	public int getModuleId() {
		return moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	

}
