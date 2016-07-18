package com.techm.trackingtool.admin.form;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.techm.trackingtool.admin.bean.CRinfo;
import com.techm.trackingtool.admin.bean.Module;
import com.techm.trackingtool.util.HashMap;
import com.techm.trackingtool.util.LindeMap;


public class CrForm {
	
	
	private List<CRinfo> crList = new ArrayList<CRinfo>();
	private String urlInfo;
	private LindeMap users;
	private List<Module> moduleList = new ArrayList<Module>();
	private String message;
	private String pageType;
	private LindeMap selectedFlds = new LindeMap();
	private String selectedMonth=null;
	
	public String getSelectedMonth() {
		return selectedMonth;
	}

	public void setSelectedMonth(String selectedMonth) {
		this.selectedMonth = selectedMonth;
	}

	public LindeMap getSelectedFlds() {
		return selectedFlds;
	}

	public void setSelectedFlds(LindeMap selectedFlds) {
		this.selectedFlds = selectedFlds;
	}

	public List<Module> getModuleList() {
		return moduleList;
	}

	public void setModuleList(List<Module> moduleList) {
		this.moduleList = moduleList;
	}

	

	public String getPageType() {
		return pageType;
	}

	public void setPageType(String pageType) {
		this.pageType = pageType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LindeMap getUsers() {
		return users;
	}

	public void setUsers(LindeMap users) {
		this.users = users;
	}

	public String getUrlInfo() {
		return urlInfo;
	}

	public void setUrlInfo(String urlInfo) {
		this.urlInfo = urlInfo;
	}

	public List<CRinfo> getCrList() {
		return crList;
	}

	public void setCrList(List<CRinfo> crList) {
		this.crList = crList;
	}

	
}
