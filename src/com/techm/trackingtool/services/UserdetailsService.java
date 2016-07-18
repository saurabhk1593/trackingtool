package com.techm.trackingtool.services;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techm.trackingtool.admin.bean.CrEmailFrequency;
import com.techm.trackingtool.admin.bean.EmailFrequency;
import com.techm.trackingtool.admin.bean.Holidays;
import com.techm.trackingtool.admin.bean.Leave;
import com.techm.trackingtool.admin.bean.Module;
import com.techm.trackingtool.admin.bean.User;
import com.techm.trackingtool.admin.dao.AddApplicationDAO;
import com.techm.trackingtool.admin.dao.AdminDAO;
import com.techm.trackingtool.admin.dao.ApplicationMappingsDAO;
import com.techm.trackingtool.admin.dao.CrEmailDAO;
import com.techm.trackingtool.admin.dao.DeleteUserDAO;
import com.techm.trackingtool.admin.dao.EmailDAO;
import com.techm.trackingtool.admin.dao.HolidayDAO;
import com.techm.trackingtool.admin.dao.LeavePlanDAO;
import com.techm.trackingtool.admin.dao.MapUserDAO;
import com.techm.trackingtool.admin.dao.TicketInfoDAO;
import com.techm.trackingtool.admin.dao.TicketInfoMonthDAO;
import com.techm.trackingtool.admin.dao.UserDetailDAO;
import com.techm.trackingtool.admin.vo.AdminVO;
import com.techm.trackingtool.admin.vo.TicketSummaryVO;
import com.techm.trackingtool.util.HashMap;
import com.techm.trackingtool.util.LindeList;
import com.techm.trackingtool.util.LindeMap;

@Service("userdetailsService")
@Transactional
public class UserdetailsService {
	
	@Autowired
    private UserDetailDAO dao;
	
	@Autowired
    private AdminDAO adminDAO;
	
	@Autowired
    private TicketInfoDAO ticketInfoDao;
	
	@Autowired
    private TicketInfoMonthDAO monthwiseticketdao;
	
	@Autowired
	AddApplicationDAO addApplicationDAO;
	
	@Autowired
	MapUserDAO mapUserDAO;
	
	@Autowired
	DeleteUserDAO deleteUserDAO;
	
	@Autowired
	EmailDAO emailDAO;
	
	@Autowired
	ApplicationMappingsDAO applicationMappingsDAO;
	
	@Autowired
    private HolidayDAO holidayDAO;
	
	@Autowired
	CrEmailDAO cremailDAO;
	
	@Autowired
	LeavePlanDAO leaveplanDAO;
	
	
	
	public LindeList getApplicationZeroOwnersInfo()
	{
		
		return applicationMappingsDAO.getApplicationZeroOwnersInfo();
	}
	
	public LindeList getUserModuleInfo()
	{
		
		return applicationMappingsDAO.getUserModuleInfo();
	}
	
	
	public LindeList getModuleUserInfo()
	{
		
		return applicationMappingsDAO.getModuleUserInfo();
	}
	
	public User checkUser(String userId,String pwd)
	{
		
		return dao.checkUser(userId,pwd);
	}
	
	public LindeMap getUsers()
	{
		return dao.getUsers();
	}
	
	public LindeMap checkUsersAwaitingApproval()
	{
		
		return dao.checkUsersAwaitingApproval();
	}
	
	
	public boolean addUser(User user,User creator) throws Exception
	{
		
		return dao.addUser(user,creator);
	}
	
	

	public LindeList getApplZeroOwnersCount()
	{
		
		return adminDAO.getApplZeroOwnersCount();
	}
	
	public int getAdminInfo(AdminVO adminInfo)
	{
		
		return adminDAO.getAdminInfo(adminInfo);
	}
	
	
	public LindeList getRoles()
	{
		
		return adminDAO.getRoles();
	}
	
	public TicketSummaryVO getTTInformations(User userInfo,String month,String year) throws ParseException
	{
		
		return ticketInfoDao.getTTInformations(userInfo,month,year);
	}
	
	
	public LindeList getTTDetailedInfoBySeverity(String Severity1,String severity,String str,User user,String month,String year)
	{
		
		return ticketInfoDao.getTTDetailedInfoBySeverity(Severity1,severity,str, user,month,year);
	}
	
	
	public String uploadInfo(List ticketList,User user) throws SQLException, Exception
	{
		
		return ticketInfoDao.uploadInfo(ticketList,user);
	}
	
	
	public LindeList getTTInfo(User user,String uploaddate,String regionselection,String fromDate,String toDate, String criteria,String year)
	{
		return ticketInfoDao.getTTInfo(user,uploaddate, regionselection, fromDate, toDate,  criteria, year);
	}
	
	
	public LindeList saveTTInfo(LindeList ttList)
	{
		return ticketInfoDao.saveTTInfo(ttList);
	}
	
	public LindeList getUploadInfo()
	{
		return ticketInfoDao.getUploadInfo();
	}
	
	public Module SubmitApplication(Module module)
	{
		
		return addApplicationDAO.SubmitApplication(module);
	}
	
	public Module getApplication(int appid)
	{
		
		return mapUserDAO.getApplication(appid);
	}
	
	public LindeList getApplicationInfo()
	{
		
		return mapUserDAO.getApplicationInfo();
	}
	
	public LindeList getUserInfo()
	{
		
		return mapUserDAO.getUserInfo();
	}
	
	
	public Set getUserSet(Set userid)
	{
		
		return mapUserDAO.getUserSet(userid);
	}
	
	public boolean submitUserMapping(Module application)
	{
		
		return mapUserDAO.submitUserMapping(application);
	}
	
	public User getUser(String userid)
	{
		
		return deleteUserDAO.getUser(userid);
	}	
	
	public void deleteUser(User user,User creator) throws Exception
	{
		
		deleteUserDAO.deleteUser(user,creator);
	}	

	public Module getApplication(String applicationid)
	{
		
		return deleteUserDAO.getApplication(applicationid);
	}	
	
	public void deleteApplication(Module application) throws Exception
	{
		
		deleteUserDAO.deleteApplication(application);
	}
	
	public LindeList getDelUserInfo()
	{
		
		return deleteUserDAO.getUserInfo();
	}
	
	public LindeList getDelApplicationInfo()
	{
		
		return deleteUserDAO.getApplicationInfo();
	}
	

	public void deleteEmailSetting(String severityid)
	{
		
		 emailDAO.deleteEmailSetting(severityid);
	}
	
	
	
	public void SubmitEmailSetting(EmailFrequency emailFrequency)
	{
		
		 emailDAO.SubmitEmailSetting(emailFrequency);
	}
	
	public EmailFrequency getEmailSetting(String severity)
	{
		
		return emailDAO.getEmailSetting(severity);
	}

	public ArrayList getEmailConfigList(String priority)
	{
		return emailDAO.getEmailConfigList(priority);
	}
	
	public void deleteCrEmailSetting(String severityid)
	{
		
		 cremailDAO.deleteCrEmailSetting(severityid);
	}
	
	public void submitCrEmailSetting(CrEmailFrequency cremailFrequency)
	{
		
		cremailDAO.submitCrEmailSetting(cremailFrequency);
	}
	
	
	
	
	public CrEmailFrequency getCrEmailSetting(String severity)
	{
		
		return cremailDAO.getCrEmailSetting(severity);
	}
	
	public void submitLeaves(User user,Leave leaveDetails)
	{
		leaveplanDAO.submitLeaves(user,leaveDetails);
	}
	
	public void submitHolidays(Holidays holiday)
	{
		holidayDAO.submitHolidays(holiday);				
	}
	
	public LindeList getTTDetailedInfoByAge(String age1, String age,String str,User user,String month,String year)
	{
		
		return ticketInfoDao.getTTDetailedInfoByAge(age1,age,"",user,month,year);
	}
	
	
	public LindeList getTTDetailedInfoByStatus(String status1, String status ,String module,User user,String month,String year)
	{
		
		return ticketInfoDao.getTTDetailedInfoByStatus(status1,status,module,user,month,year);
	}
	
	public LindeList getTTDetailedInfoModulewiseAge(String status1, String status ,String module,User user,String month,String year)
	{
		
		return ticketInfoDao.getTTDetailedInfoModulewiseAge(status1,status,module,user,month,year);
		
	}
	
	public List<Module> getModuleList()
	{
		
		return addApplicationDAO.getModuleList();
	}
	
	public boolean checkleaves(User user,String startDate,String endDate)
	{
		return leaveplanDAO.checkLeaves(user,startDate,endDate);
	}
	public ArrayList getLeaveDetails()
	{
		return leaveplanDAO.getLeaveDetails();
	}
	
	public TicketSummaryVO getTTinfoMonthwise(User userInfo,String month,String year) throws ParseException
	{
		return monthwiseticketdao.getTTInformations(userInfo,month,year);
	}
	
	public void sendCREmailNotifications()
	{
		
		 cremailDAO.sendCREmailNotifications();
	}
	
	public HashMap getHolidayDetails()
	{
		return holidayDAO.getHoliday();
	}
	
	public void sendEmailNotifications() throws Exception
	{
		emailDAO.sendNotification();
	}
}
