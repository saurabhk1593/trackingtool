package com.techm.trackingtool.admin.form;

public class ArchivedDataForm {

	public String year;
	public String selectedMonth="ALL";
	public String editPrivelege;
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getEditPrivelege() {
		return editPrivelege;
	}
	public void setEditPrivelege(String editPrivelege) {
		this.editPrivelege = editPrivelege;
	}
	
	public String getSelectedMonth() {
		return selectedMonth;
	}
	
	public void setSelectedMonth(String selectedMonth) {
		this.selectedMonth = selectedMonth;
	}
	
}
