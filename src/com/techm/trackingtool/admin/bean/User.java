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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="TBL_USERS")
@NamedQueries(  
        {  
            @NamedQuery(  
            name = "selUser",  
            query = "FROM User WHERE userID = :userId and password = :password order by firstName"  
            ),
            @NamedQuery(  
                    name = "selUserWithUserName",  
                    query = "FROM User WHERE firstName = :userName  order by firstName"  
                    ),
           @NamedQuery(  
                      name = "selPendingUser",  
                       query = "FROM User WHERE approvalStatus = :statusVal  order by firstName"  
                     ),
          @NamedQuery(  
                 name = "selUsers",  
                  query = "FROM User order by firstName"  
                )                     
        }  
    )  
public class User {
	
	@Id
	@Column(name = "USER_ID", nullable = false)
	private String userID;	
	
	@Column(name = "LOCATION", nullable = false)
	private String location;
	
	@Column(name = "PASSWORD", nullable = false)
	private String password;
	
	@Column(name = "FIRST_NAME", nullable = false)
	private String firstName;
	
	@Column(name = "LAST_NAME", nullable = false)
	private String lastName;
	
	@Column(name = "EMAIL_ID", nullable = false)
	public String emailId;
	
	@Column(name = "ROLE_ID", nullable = false)
	private Integer roleId;	
	
	@Column(name = "APPROVAL_STATUS", nullable = false)
	private String approvalStatus;
	
	@ManyToMany(cascade=CascadeType.ALL)  
	@JoinTable(name="TBL_OWNER_MODULE", joinColumns={@JoinColumn(name="USER_ID")},inverseJoinColumns={@JoinColumn(name="MODULE_ID")})
	private Set<Module> moduleSet = new HashSet<Module>();
	
	@Transient
	public boolean isAvailable;
	
	@Transient
	public Roles userRole;
	
	
/*	@OneToOne(cascade=CascadeType.ALL)
	@JoinTable(name="TBL_OWNER_MODULE", joinColumns={@JoinColumn(name="USER_ID")},inverseJoinColumns={@JoinColumn(name="MODULE_ID")})
	private Module module;
	
	public Module getModule() {
		return module;
	}
	public void setModule(Module module) {
		this.module = module;
	}*/
	
	public Set<Module> getModuleSet() {
		return moduleSet;
	}
	public void setModuleSet(Set<Module> moduleSet) {
		this.moduleSet = moduleSet;
	}
	
		
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
   
	
	public Roles getUserRole() {
		return userRole;
	}
	public void setUserRole(Roles userRole) {
		this.userRole = userRole;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
		
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	} 
	
	
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	
	public String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	
	
	
		
	public String toString()
	{
		return "User[userID :" + userID + " Location :"+location+" firstName :"+firstName +" lastName :"+lastName+" emailId :"+emailId+" roleId :"+roleId+" approvalStatus :" +
				"approvalStatus ]";
	}
	public boolean isAvailable() {
		return isAvailable;
	}
	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	

}
