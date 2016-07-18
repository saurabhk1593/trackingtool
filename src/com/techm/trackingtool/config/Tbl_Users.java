package com.techm.trackingtool.config;

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


@Entity
@Table(name="TBL_USERS")
@NamedQueries(  
        {  
            @NamedQuery(  
            name = "selUser",  
            query = "FROM Tbl_Users WHERE userID = :userId  order by firstName"  
            ),
            @NamedQuery(  
                    name = "selUserWithUserName",  
                    query = "FROM Tbl_Users WHERE firstName = :userName  order by firstName"  
                    ),
           @NamedQuery(  
                      name = "selPendingUser",  
                       query = "FROM Tbl_Users WHERE approvalStatus = :statusVal  order by firstName"  
                     ),
          @NamedQuery(  
                 name = "selUsers",  
                  query = "FROM Tbl_Users order by firstName"  
                )                     
        }  
    )  

public class Tbl_Users {
	
	@Id
	@Column(name = "USER_ID", nullable = false)
	private String userID;	
	
	@Column(name = "DOMAIN", nullable = false)
	private String domain;	
	
	@Column(name = "FIRST_NAME", nullable = false)
	private String firstName;
	
	@Column(name = "LAST_NAME", nullable = false)
	private String lastName;
	
	@Column(name = "EMAIL_ID", nullable = false)
	private String emailId;
	
	@Column(name = "ROLE_ID", nullable = false)
	private int roleId;	
	
	@Column(name = "APPROVAL_STATUS", nullable = false)
	private String approvalStatus;
	
	@ManyToMany(cascade=CascadeType.ALL)  
	@JoinTable(name="TBL_OWNER_APPLICATION", joinColumns={@JoinColumn(name="USER_ID")},inverseJoinColumns={@JoinColumn(name="APPLICATION_ID")})
	private Set<Tbl_Application> applicationSet = new HashSet<Tbl_Application>();
			
   
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
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
	
	
	public int getRoleId() {
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
	
	public Set<Tbl_Application> getApplicationSet() {
		return applicationSet;
	}
	public void setApplicationSet(Set<Tbl_Application> applicationSet) {
		this.applicationSet = applicationSet;
	}
	
		
	public String toString()
	{
		return "Tbl_Users[userID :" + userID + " domain :"+domain+" firstName :"+firstName +" lastName :"+lastName+" emailId :"+emailId+" roleId :"+roleId+" approvalStatus :" +
				"approvalStatus ]";
	}

}
