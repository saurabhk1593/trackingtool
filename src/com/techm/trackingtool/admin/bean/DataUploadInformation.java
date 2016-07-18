package com.techm.trackingtool.admin.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TBL_DATA_UPLOAD")
public class DataUploadInformation implements Serializable {
	
	@Id
	@Column(name = "UPLOAD_DATE", nullable = false)
	private Date uploadDate;
	
	@Id
	@Column(name = "UPLOADED_BY", nullable = false)
	private String uploader;
	
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	public String getUploader() {
		return uploader;
	}
	public void setUploader(String uploader) {
		this.uploader = uploader;
	}
	public DataUploadInformation(Date uploadDate, String uploader) {
		super();
		this.uploadDate = uploadDate;
		this.uploader = uploader;
	}
	public DataUploadInformation() {
		super();
	}
	


}
