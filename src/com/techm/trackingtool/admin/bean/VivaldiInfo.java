package com.techm.trackingtool.admin.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="TBL_VIVALDI_INFO")
public class VivaldiInfo {
	
		@Id
		@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VIVALDI_ID_SEQ")
		@SequenceGenerator(name= "VIVALDI_ID_SEQ",initialValue=1,allocationSize=1,sequenceName="VIVALDIID_SEQUENCE")
		@Column(name = "SNO")
		public int sno;
		
		@Column(name = "PROJECT", nullable = false)
		public String project;
		
		@Column(name = "ACTIVITY_ID")
		public String activityId; 
				
		@Column(name = "ACTIVITY_DESC")
		public String activityDesc;
		
		@Column(name = "BUPO")
		public String bupo;
		
		@Column(name = "BUPO_TYPE")
		public String bupoType;
		
		@Column(name = "NUMBERS")
		public String number;
		
		@Column(name = "BUPO_TEXT")
		public String bupoText;
		
		@Column(name = "DATES")
		public String date;
		
		//@Column(name = "EFFORTS")
		@Transient
		public String effort;
		
		@Column(name = "SYSTEMS")
		public String system;
		
		@Column(name = "COMMENTS")
		public String comment;
		
		//@Column(name = "REASONS")
		@Transient
		public String reason;
		
		@Column(name = "USERS")
		public String user;

		/**
		 * @return the sno
		 */
		public int getSno() {
			return sno;
		}

		/**
		 * @param sno the sno to set
		 */
		public void setSno(int sno) {
			this.sno = sno;
		}

		/**
		 * @return the project
		 */
		public String getProject() {
			return project;
		}

		/**
		 * @param project the project to set
		 */
		public void setProject(String project) {
			this.project = project;
		}

		/**
		 * @return the activityId
		 */
		public String getActivityId() {
			return activityId;
		}

		/**
		 * @param activityId the activityId to set
		 */
		public void setActivityId(String activityId) {
			this.activityId = activityId;
		}

		/**
		 * @return the activityDesc
		 */
		public String getActivityDesc() {
			return activityDesc;
		}

		/**
		 * @param activityDesc the activityDesc to set
		 */
		public void setActivityDesc(String activityDesc) {
			this.activityDesc = activityDesc;
		}

		/**
		 * @return the bupo
		 */
		public String getBupo() {
			return bupo;
		}

		/**
		 * @param bupo the bupo to set
		 */
		public void setBupo(String bupo) {
			this.bupo = bupo;
		}

		/**
		 * @return the bupoType
		 */
		public String getBupoType() {
			return bupoType;
		}

		/**
		 * @param bupoType the bupoType to set
		 */
		public void setBupoType(String bupoType) {
			this.bupoType = bupoType;
		}

		/**
		 * @return the number
		 */
		public String getNumber() {
			return number;
		}

		/**
		 * @param number the number to set
		 */
		public void setNumber(String number) {
			this.number = number;
		}

		/**
		 * @return the bupoText
		 */
		public String getBupoText() {
			return bupoText;
		}

		/**
		 * @param bupoText the bupoText to set
		 */
		public void setBupoText(String bupoText) {
			this.bupoText = bupoText;
		}

		/**
		 * @return the date
		 */
		public String getDate() {
			return date;
		}

		/**
		 * @param date the date to set
		 */
		public void setDate(String date) {
			this.date = date;
		}

		/**
		 * @return the effort
		 */
		public String getEffort() {
			return effort;
		}

		/**
		 * @param effort the effort to set
		 */
		public void setEffort(String effort) {
			this.effort = effort;
		}

		/**
		 * @return the system
		 */
		public String getSystem() {
			return system;
		}

		/**
		 * @param system the system to set
		 */
		public void setSystem(String system) {
			this.system = system;
		}

		/**
		 * @return the comment
		 */
		public String getComment() {
			return comment;
		}

		/**
		 * @param comment the comment to set
		 */
		public void setComment(String comment) {
			this.comment = comment;
		}

		/**
		 * @return the reason
		 */
		public String getReason() {
			return reason;
		}

		/**
		 * @param reason the reason to set
		 */
		public void setReason(String reason) {
			this.reason = reason;
		}

		/**
		 * @return the user
		 */
		public String getUser() {
			return user;
		}

		/**
		 * @param user the user to set
		 */
		public void setUser(String user) {
			this.user = user;
		}

		
	
		
	
	

}
