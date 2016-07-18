package com.techm.trackingtool.admin.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name="TBL_CREMAILFREQUENCY")
@NamedQuery(name="selCrSeveritySetting",query="FROM CrEmailFrequency")		

public class CrEmailFrequency {

	@Id
	@Column(name = "id", nullable = false)
	public String id;
		
	@Column(name = "PRIORITY_DESC")
	public String priority;
		
	@Column(name = "INTERVAL_P1")
	public String p1_interval="";
		
	
	/**
	 * @return the p3_interval
	 */
	public String getP3_interval() {
		return p3_interval;
	}
	/**
	 * @param p3_interval the p3_interval to set
	 */
	public void setP3_interval(String p3_interval) {
		this.p3_interval = p3_interval;
	}
	/**
	 * @return the p4_interval
	 */
	public String getP4_interval() {
		return p4_interval;
	}
	/**
	 * @param p4_interval the p4_interval to set
	 */
	public void setP4_interval(String p4_interval) {
		this.p4_interval = p4_interval;
	}
	/**
	 * @param p2_interval the p2_interval to set
	 */
	public void setP2_interval(String p2_interval) {
		this.p2_interval = p2_interval;
	}
	@Column(name = "INTERVAL_P2")
	public String p2_interval="";
		
	@Column(name = "INTERVAL_P3")
	public String p3_interval="";
		
	@Column(name = "INTERVAL_P4")
	public String p4_interval="";
		
	
	@Column(name = "TM_CC")
	public String tm_cc="";
		
	@Column(name = "PM_CC")
	public String pm_cc="";
		
	@Column(name = "PGM_CC")
	public String pgm_cc="";
		
		
	public String getTm_cc() {
		return tm_cc;
	}
	public void setTm_cc(String tm_cc) {
		this.tm_cc = tm_cc;
	}
	public String getPgm_cc() {
		return pgm_cc;
	}
	public void setPgm_cc(String pgm_cc) {
		this.pgm_cc = pgm_cc;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	
	/**
	 * @return the p1_interval
	 */
	public String getP1_interval() {
		return p1_interval;
	}
	/**
	 * @param p1_interval the p1_interval to set
	 */
	public void setP1_interval(String p1_interval) {
		this.p1_interval = p1_interval;
	}
	
	public String getP2_interval() {
		return p2_interval;
	}
	
	
	public String getPm_cc() {
		return pm_cc;
	}
	public void setPm_cc(String pm_cc) {
		this.pm_cc = pm_cc;
	}
	public CrEmailFrequency(){}
		public String getId() {
			return id;
		}
		public void setId(String severityid) {
			this.id = severityid;
		}
		public CrEmailFrequency(String id, String priority, String p1_interval,
				String p2_interval, String p3_interval,
				String p4_interval, String owners_cc, String tm_cc, String pm_cc, String pgm_cc) {
			super();
			this.id = id;
			this.priority = priority;
			this.p1_interval = p1_interval;
			this.p2_interval = p2_interval;
			this.p3_interval = p3_interval;
			this.p4_interval = p4_interval;
		
			//this.owners_cc = owners_cc;
			this.tm_cc = tm_cc;
			this.pgm_cc = pgm_cc;
			this.pm_cc = pm_cc;
		}
		public CrEmailFrequency(String id) {
			
			this.id = id;
				}
	}

