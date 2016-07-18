package com.techm.trackingtool.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="TBL_MST_ROLES")
@NamedQuery(name = "getRole", query = "FROM Tbl_Roles WHERE roleId = :roleID")
public class Tbl_Roles {
	
	private int roleId;	
	private String roleName;	
	private String roleDesc;
	
	@Id
	@Column(name = "ROLE_ID", nullable = false)
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	@Column(name = "ROLE_NAME", nullable = false)
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	@Column(name = "ROLE_DESC", nullable = false)
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
		
	
	

}
