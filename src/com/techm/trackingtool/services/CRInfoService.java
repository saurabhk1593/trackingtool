package com.techm.trackingtool.services;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techm.trackingtool.admin.bean.CRinfo;
import com.techm.trackingtool.admin.bean.User;
import com.techm.trackingtool.admin.dao.CRInfoDAO;
import com.techm.trackingtool.admin.dao.TicketInfoDAO;
import com.techm.trackingtool.admin.vo.CrSummaryVO;
import com.techm.trackingtool.util.LindeList;


@Service("crinfoService")
@Transactional
public class CRInfoService {
	
	@Autowired
    private CRInfoDAO crinfoDAO;
	
	public String uploadInfo(List crList,User user) throws SQLException, Exception
	{
		
		return crinfoDAO.uploadInfo(crList,user);
	}
	

	public CrSummaryVO getCRDetails(User user,String selectedMonth) throws SQLException, Exception
	{
		
		return crinfoDAO.getCRDetails(user, selectedMonth);
	}
	
	public List<CRinfo> getCRDetailedList(String pri_age_pha,String type,String str,User user,String year,String selectedMonth)
	{
		return crinfoDAO.getCRDetailedList(pri_age_pha, type, str, user, year,selectedMonth);
	}


	public List<CRinfo> saveCRList(List<CRinfo> crlist, String priority_age_module, String type,String module, User user,String selectedMonth) {
		return crinfoDAO.saveCRList(crlist, priority_age_module, type, module, user,selectedMonth);
	}
	
	public CrSummaryVO getCRSLADetails(User user) throws SQLException, Exception
	{
		
		return crinfoDAO.getCRSLADetails(user);
	}
	
	public List<CRinfo> getCRSLADetailedList(String date_sla,String type,String phase, String priority,User user,String location)
	{
		return crinfoDAO.getCRSLADetailedList(date_sla, type, phase,priority, user,location);
	}


	public boolean saveCRSLAList(List<CRinfo> crlist,User user) {
		return crinfoDAO.saveCRSLAList(crlist, user);
	}
	
	public List<CRinfo> getCRData(String fromDate,String toDate,String moduleName) throws ParseException{
		return crinfoDAO.getCRData( fromDate, toDate, moduleName);
	}
}
