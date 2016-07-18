package com.techm.trackingtool.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techm.trackingtool.admin.bean.User;
import com.techm.trackingtool.admin.dao.CRInfoDAO;
import com.techm.trackingtool.admin.dao.SLAMetricsDAO;

import com.techm.trackingtool.admin.dao.TicketInfoDAO;
import com.techm.trackingtool.admin.vo.MetricsVO;


@Service("ticketinfoservices")
@Transactional
public class TicketInfoServices {
	@Autowired
	private TicketInfoDAO ticketinfoDao;
	
	@Autowired
	private SLAMetricsDAO sLAMetricsDAO;

	
	public String uploadInfo(List ticketList,User user) throws SQLException, Exception
	{
		
		return ticketinfoDao.uploadInfo(ticketList,user);
	}
	
	public MetricsVO getIncidentSlaMetrics(User user)throws SQLException, Exception{
		return sLAMetricsDAO.getIncidentSlaMetrics(user);
	}
	public List getIncidentData(User userInfo,String fromDate,String toDate,String moduleNAme){
		return ticketinfoDao.getIncidentData(userInfo,fromDate,toDate, moduleNAme);
	}

}
