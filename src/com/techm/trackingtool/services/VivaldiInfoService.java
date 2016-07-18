package com.techm.trackingtool.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techm.trackingtool.admin.bean.User;
import com.techm.trackingtool.admin.dao.VivaldiInfoDAO;


@Service("VivaldiInfoService")
@Transactional
public class VivaldiInfoService {

	@Autowired
	private VivaldiInfoDAO vivaldiinfoDao;

	public String uploadInfo(List ticketList,User user) throws SQLException, Exception
	{
		
		return vivaldiinfoDao.uploadInfo(ticketList,user);
	}
	
	
}


