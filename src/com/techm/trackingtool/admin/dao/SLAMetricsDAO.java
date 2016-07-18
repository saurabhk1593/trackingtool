package com.techm.trackingtool.admin.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.techm.trackingtool.admin.bean.SLAMetrics;
import com.techm.trackingtool.admin.bean.TicketInfo;
import com.techm.trackingtool.admin.bean.User;
import com.techm.trackingtool.admin.vo.MetricsVO;
import com.techm.trackingtool.util.LindeList;
import com.techm.trackingtool.util.LindeMap;
import com.techm.trackingtool.util.LindeToolLogManager;
import com.techm.trackingtool.util.TrackingToolConstant;

@Repository("slametricsdao")
public class SLAMetricsDAO {
	private static final LindeToolLogManager lindeLogMgr = new LindeToolLogManager(SLAMetricsDAO.class.getName());

	@Autowired
	private SessionFactory sessionFactory;
	// HashMap slaDetailsMap;

	public Session getSession() {
		Session session = null;
		try {

			session = sessionFactory.getCurrentSession();
		} catch (Exception ex) {
			lindeLogMgr.logStackTrace("ERROR", ex.getMessage(), ex);
		}
		return session;
	}
	
	public MetricsVO getIncidentSlaMetrics(User user){
		MetricsVO metricsVO = new MetricsVO();
		LindeMap ackSlaMap = getIncidentAcknowledgmentSLA(user);
		LindeMap resolutionSlaMap = getIncidentResolutionSLA(user);
		LindeList onDateTicketVolume = getOnDateVolTickets();
		metricsVO.setIncidentResolutionSlaMap(resolutionSlaMap);
		metricsVO.setIncidentAckSlaMap(ackSlaMap);
		metricsVO.setAsOnDateTicketVolume(onDateTicketVolume);
		return metricsVO;
	}
	
	public LindeList getOnDateVolTickets(){
		LindeList onDateTicketVolumeList = new LindeList();
		for(int i=0; i<6; i++){
			SLAMetrics ticketVolume = new SLAMetrics();
			ticketVolume = getTicketVolume(i);
			onDateTicketVolumeList.add(ticketVolume);
		}
		lindeLogMgr.logMessage("DEBUG", "onDateTicketVolumeList size is: "+onDateTicketVolumeList.size());
		return onDateTicketVolumeList;
	}


	public LindeMap getIncidentAcknowledgmentSLA(User user){
		lindeLogMgr.logMessage("INFO","Inside getIncidentAcknowledgmentSLA");
		LindeMap ackSlaMap = new LindeMap();
		MetricsVO metricsVO = new MetricsVO();		
		Object[] row;		
		String[] priority1SlaMet;
		String[] priority1SlaNotMet;
		String[] priority2SlaMet;
		String[] priority2SlaNotMet;
		String[] priority3SlaMet;
		String[] priority3SlaNotMet;
		String[] priority4SlaMet;
		String[] priority4SlaNotMet;
		lindeLogMgr.logMessage("DEBUG", "user name is: "+user.getFirstName()+" "+user.getLastName());
		
		if(user.getRoleId()==1 || user.getRoleId()==2 ){
			priority1SlaMet = slaCalculations(user, "<=15", "Assigned", "1 - Critical");
			priority1SlaNotMet = slaCalculations(user, ">15", "Assigned", "1 - Critical");
			priority2SlaMet = slaCalculations(user, "<=60", "Assigned", "2 - High");
			priority2SlaNotMet = slaCalculations(user, ">60", "Assigned", "2 - High");
			priority3SlaMet = slaCalculations(user, "<=240", "Assigned", "3 - Medium");
			priority3SlaNotMet = slaCalculations(user, ">240", "Assigned", "3 - Medium");
			priority4SlaMet = slaCalculations(user, "<=240", "Assigned", "4 - Low");
			priority4SlaNotMet = slaCalculations(user, ">240", "Assigned", "4 - Low");		
			ackSlaMap.put("ackpriority1met",priority1SlaMet[0]);
			ackSlaMap.put("priority_1_sla_met_percent",priority1SlaMet[1]);
			ackSlaMap.put("ackpriority1notmet",priority1SlaNotMet[0]);
			ackSlaMap.put("priority_1_sla_not_met_percent",priority1SlaNotMet[1]);
			ackSlaMap.put("ackpriority2met",priority2SlaMet[0]);
			ackSlaMap.put("priority_2_sla_met_percent",priority2SlaMet[1]);
			ackSlaMap.put("ackpriority2notmet",priority2SlaNotMet[0]);
			ackSlaMap.put("priority_2_sla_not_met_percent",priority2SlaNotMet[1]);
			ackSlaMap.put("ackpriority3met",priority3SlaMet[0]);
			ackSlaMap.put("priority_3_sla_met_percent",priority3SlaMet[1]);
			ackSlaMap.put("ackpriority3notmet",priority3SlaNotMet[0]);
			ackSlaMap.put("priority_3_sla_not_met_percent",priority3SlaNotMet[1]);
			ackSlaMap.put("ackpriority4met",priority4SlaMet[0]);
			ackSlaMap.put("priority_4_sla_met_percent",priority4SlaMet[1]);
			ackSlaMap.put("ackpriority4notmet",priority4SlaNotMet[0]);
			ackSlaMap.put("priority_4_sla_not_met_percent",priority4SlaNotMet[1]);
		} else if(user.getRoleId()==3){
			priority1SlaMet = slaCalculationsForPgM(user, "<=15", "Assigned", "1 - Critical");
			priority1SlaNotMet = slaCalculationsForPgM(user, ">15", "Assigned", "1 - Critical");
			priority2SlaMet = slaCalculationsForPgM(user, "<=60", "Assigned", "2 - High");
			priority2SlaNotMet = slaCalculationsForPgM(user, ">60", "Assigned", "2 - High");
			priority3SlaMet = slaCalculationsForPgM(user, "<=240", "Assigned", "3 - Medium");
			priority3SlaNotMet = slaCalculationsForPgM(user, ">240", "Assigned", "3 - Medium");
			priority4SlaMet = slaCalculationsForPgM(user, "<=240", "Assigned", "4 - Low");
			priority4SlaNotMet = slaCalculationsForPgM(user, ">240", "Assigned", "4 - Low");	
			/* Priority 1 -Start*/
			ackSlaMap.put("ackpriority1met_offshore",priority1SlaMet[0].toString());
			ackSlaMap.put("ackpriority1met_onshore",priority1SlaMet[1].toString());
			ackSlaMap.put("ackpriority1met",priority1SlaMet[2].toString());
			ackSlaMap.put("priority_1_sla_met_percent_offshore",priority1SlaMet[3]);
			ackSlaMap.put("priority_1_sla_met_percent_onshore",priority1SlaMet[4]);
			ackSlaMap.put("priority_1_sla_met_percent",priority1SlaMet[5]);
			ackSlaMap.put("ackpriority1notmet_offshore",priority1SlaNotMet[0].toString());
			ackSlaMap.put("ackpriority1notmet_onshore",priority1SlaNotMet[1].toString());
			ackSlaMap.put("ackpriority1notmet",priority1SlaNotMet[2].toString());
			ackSlaMap.put("priority_1_sla_not_met_percent_offshore",priority1SlaNotMet[3]);
			ackSlaMap.put("priority_1_sla_not_met_percent_onshore",priority1SlaNotMet[4]);
			ackSlaMap.put("priority_1_sla_not_met_percent",priority1SlaNotMet[5]);
			/* Priority 1 -End*/			
			
			
			/* Priority 2 -Start*/
			ackSlaMap.put("ackpriority2met_offshore",priority2SlaMet[0].toString());
			ackSlaMap.put("ackpriority2met_onshore",priority2SlaMet[1].toString());
			ackSlaMap.put("ackpriority2met",priority2SlaMet[2].toString());
			ackSlaMap.put("priority_2_sla_met_percent_offshore",priority2SlaMet[3]);
			ackSlaMap.put("priority_2_sla_met_percent_onshore",priority2SlaMet[4]);
			ackSlaMap.put("priority_2_sla_met_percent",priority2SlaMet[5]);
			ackSlaMap.put("ackpriority2notmet_offshore",priority2SlaNotMet[0].toString());
			ackSlaMap.put("ackpriority2notmet_onshore",priority2SlaNotMet[1].toString());
			ackSlaMap.put("ackpriority2notmet",priority2SlaNotMet[2].toString());
			ackSlaMap.put("priority_2_sla_not_met_percent_offshore",priority2SlaNotMet[3]);
			ackSlaMap.put("priority_2_sla_not_met_percent_onshore",priority2SlaNotMet[4]);
			ackSlaMap.put("priority_2_sla_not_met_percent",priority2SlaNotMet[5]);
			/* Priority 2 -End*/
			
			/* Priority 3 -Start*/
			ackSlaMap.put("ackpriority3met_offshore",priority3SlaMet[0].toString());
			ackSlaMap.put("ackpriority3met_onshore",priority3SlaMet[1].toString());
			ackSlaMap.put("ackpriority3met",priority3SlaMet[2].toString());
			ackSlaMap.put("priority_3_sla_met_percent_offshore",priority3SlaMet[3]);
			ackSlaMap.put("priority_3_sla_met_percent_onshore",priority3SlaMet[4]);
			ackSlaMap.put("priority_3_sla_met_percent",priority3SlaMet[5]);
			ackSlaMap.put("ackpriority3notmet_offshore",priority3SlaNotMet[0].toString());
			ackSlaMap.put("ackpriority3notmet_onshore",priority3SlaNotMet[1].toString());
			ackSlaMap.put("ackpriority3notmet",priority3SlaNotMet[2].toString());
			ackSlaMap.put("priority_3_sla_not_met_percent_offshore",priority3SlaNotMet[3]);
			ackSlaMap.put("priority_3_sla_not_met_percent_onshore",priority3SlaNotMet[4]);
			ackSlaMap.put("priority_3_sla_not_met_percent",priority3SlaNotMet[5]);
			/* Priority 3 -End*/
			
			/* Priority 4 -Start*/
			ackSlaMap.put("ackpriority4met_offshore",priority4SlaMet[0].toString());
			ackSlaMap.put("ackpriority4met_onshore",priority4SlaMet[1].toString());
			ackSlaMap.put("ackpriority4met",priority4SlaMet[2].toString());
			ackSlaMap.put("priority_4_sla_met_percent_offshore",priority4SlaMet[3]);
			ackSlaMap.put("priority_4_sla_met_percent_onshore",priority4SlaMet[4]);
			ackSlaMap.put("priority_4_sla_met_percent",priority4SlaMet[5]);
			ackSlaMap.put("ackpriority4notmet_offshore",priority4SlaNotMet[0].toString());
			ackSlaMap.put("ackpriority4notmet_onshore",priority4SlaNotMet[1].toString());
			ackSlaMap.put("ackpriority4notmet",priority4SlaNotMet[2].toString());
			ackSlaMap.put("priority_4_sla_not_met_percent_offshore",priority4SlaNotMet[3]);
			ackSlaMap.put("priority_4_sla_not_met_percent_onshore",priority4SlaNotMet[4]);
			ackSlaMap.put("priority_4_sla_not_met_percent",priority4SlaNotMet[5]);
			/* Priority 4 -End*/
		}
		
		return ackSlaMap;		
	}
	
	public LindeMap getIncidentResolutionSLA(User user){
		lindeLogMgr.logMessage("INFO","Inside getIncidentResolutionSLA");
		LindeMap resolutionSlaMap = new LindeMap();
		MetricsVO metricsVO = new MetricsVO();		
		Object[] row;
		String userrole = "";
		String[] priority1SlaMet;
		String[] priority1SlaNotMet;
		String[] priority2SlaMet;
		String[] priority2SlaNotMet;
		String[] priority3SlaMet;
		String[] priority3SlaNotMet;
		String[] priority4SlaMet;
		String[] priority4SlaNotMet;
		
		/* The SLA for resolution has been converted from Hours to Minutes to reuse the SLA calculations method. The Resolution SLA is:
		 * Priority-1 - 4 hrs
		 * Priority-2 - 16 hrs
		 * Priority-3 - 24 hrs
		 * Priority-4 - 24 hrs
		 */
		if(user.getRoleId()==1 || user.getRoleId()==2 ){
			priority1SlaMet = slaCalculations(user, "<=240", "Assigned", "1 - Critical");
			priority1SlaNotMet = slaCalculations(user, ">240", "Assigned", "1 - Critical");
			priority2SlaMet = slaCalculations(user, "<=960", "Assigned", "2 - High");
			priority2SlaNotMet = slaCalculations(user, ">960", "Assigned", "2 - High");
			priority3SlaMet = slaCalculations(user, "<=1440", "Assigned", "3 - Medium");
			priority3SlaNotMet = slaCalculations(user, ">1440", "Assigned", "3 - Medium");
			priority4SlaMet = slaCalculations(user, "<=1440", "Assigned", "4 - Low");
			priority4SlaNotMet = slaCalculations(user, ">1440", "Assigned", "4 - Low");		
			resolutionSlaMap.put("resolutionpriority1met",priority1SlaMet[0]);
			resolutionSlaMap.put("resolution_priority_1_sla_met_percent",priority1SlaMet[1]);
			resolutionSlaMap.put("resolutionpriority1notmet",priority1SlaNotMet[0]);
			resolutionSlaMap.put("resolution_priority_1_sla_not_met_percent",priority1SlaNotMet[1]);
			resolutionSlaMap.put("resolutionpriority2met",priority2SlaMet[0]);
			resolutionSlaMap.put("resolution_priority_2_sla_met_percent",priority2SlaMet[1]);
			resolutionSlaMap.put("resolutionpriority2notmet",priority2SlaNotMet[0]);
			resolutionSlaMap.put("resolution_priority_2_sla_not_met_percent",priority2SlaNotMet[1]);
			resolutionSlaMap.put("resolutionpriority3met",priority3SlaMet[0]);
			resolutionSlaMap.put("resolution_priority_3_sla_met_percent",priority3SlaMet[1]);
			resolutionSlaMap.put("resolutionpriority3notmet",priority3SlaNotMet[0]);
			resolutionSlaMap.put("resolution_priority_3_sla_not_met_percent",priority3SlaNotMet[1]);
			resolutionSlaMap.put("resolutionpriority4met",priority4SlaMet[0]);
			resolutionSlaMap.put("resolution_priority_4_sla_met_percent",priority4SlaMet[1]);
			resolutionSlaMap.put("resolutionpriority4notmet",priority4SlaNotMet[0]);
			resolutionSlaMap.put("resolution_priority_4_sla_not_met_percent",priority4SlaNotMet[1]);
		} else if(user.getRoleId()==3){
			priority1SlaMet = slaCalculationsForPgM(user, "<=240", "Work In Progress", "1 - Critical");
			priority1SlaNotMet = slaCalculationsForPgM(user, ">240", "Work In Progress", "1 - Critical");
			priority2SlaMet = slaCalculationsForPgM(user, "<=960", "Work In Progress", "2 - High");
			priority2SlaNotMet = slaCalculationsForPgM(user, ">960", "Work In Progress", "2 - High");
			priority3SlaMet = slaCalculationsForPgM(user, "<=1440", "Work In Progress", "3 - Medium");
			priority3SlaNotMet = slaCalculationsForPgM(user, ">1440", "Work In Progress", "3 - Medium");
			priority4SlaMet = slaCalculationsForPgM(user, "<=1440", "Work In Progress", "4 - Low");
			priority4SlaNotMet = slaCalculationsForPgM(user, ">1440", "Work In Progress", "4 - Low");	
			/* Priority 1 -Start*/
			resolutionSlaMap.put("resolutionpriority1met_offshore",priority1SlaMet[0].toString());
			resolutionSlaMap.put("resolutionpriority1met_onshore",priority1SlaMet[1].toString());
			resolutionSlaMap.put("resolutionpriority1met",priority1SlaMet[2].toString());
			resolutionSlaMap.put("resolution_priority_1_sla_met_percent_offshore",priority1SlaMet[3]);
			resolutionSlaMap.put("resolution_priority_1_sla_met_percent_onshore",priority1SlaMet[4]);
			resolutionSlaMap.put("resolution_priority_1_sla_met_percent",priority1SlaMet[5]);
			resolutionSlaMap.put("resolutionpriority1notmet_offshore",priority1SlaNotMet[0].toString());
			resolutionSlaMap.put("resolutionpriority1notmet_onshore",priority1SlaNotMet[1].toString());
			resolutionSlaMap.put("resolutionpriority1notmet",priority1SlaNotMet[2].toString());
			resolutionSlaMap.put("resolution_priority_1_sla_not_met_percent_offshore",priority1SlaNotMet[3]);
			resolutionSlaMap.put("resolution_priority_1_sla_not_met_percent_onshore",priority1SlaNotMet[4]);
			resolutionSlaMap.put("resolution_priority_1_sla_not_met_percent",priority1SlaNotMet[5]);
			/* Priority 1 -End*/			
			
			
			/* Priority 2 -Start*/
			resolutionSlaMap.put("resolutionpriority2met_offshore",priority2SlaMet[0].toString());
			resolutionSlaMap.put("resolutionpriority2met_onshore",priority2SlaMet[1].toString());
			resolutionSlaMap.put("resolutionpriority2met",priority2SlaMet[2].toString());
			resolutionSlaMap.put("resolution_priority_2_sla_met_percent_offshore",priority2SlaMet[3]);
			resolutionSlaMap.put("resolution_priority_2_sla_met_percent_onshore",priority2SlaMet[4]);
			resolutionSlaMap.put("resolution_priority_2_sla_met_percent",priority2SlaMet[5]);
			resolutionSlaMap.put("resolutionpriority2notmet_offshore",priority2SlaNotMet[0].toString());
			resolutionSlaMap.put("resolutionpriority2notmet_onshore",priority2SlaNotMet[1].toString());
			resolutionSlaMap.put("resolutionpriority2notmet",priority2SlaNotMet[2].toString());
			resolutionSlaMap.put("resolution_priority_2_sla_not_met_percent_offshore",priority2SlaNotMet[3]);
			resolutionSlaMap.put("resolution_priority_2_sla_not_met_percent_onshore",priority2SlaNotMet[4]);
			resolutionSlaMap.put("resolution_priority_2_sla_not_met_percent",priority2SlaNotMet[5]);
			/* Priority 2 -End*/
			
			/* Priority 3 -Start*/
			resolutionSlaMap.put("resolutionpriority3met_offshore",priority3SlaMet[0].toString());
			resolutionSlaMap.put("resolutionpriority3met_onshore",priority3SlaMet[1].toString());
			resolutionSlaMap.put("resolutionpriority3met",priority3SlaMet[2].toString());
			resolutionSlaMap.put("resolution_priority_3_sla_met_percent_offshore",priority3SlaMet[3]);
			resolutionSlaMap.put("resolution_priority_3_sla_met_percent_onshore",priority3SlaMet[4]);
			resolutionSlaMap.put("resolution_priority_3_sla_met_percent",priority3SlaMet[5]);
			resolutionSlaMap.put("resolutionpriority3notmet_offshore",priority3SlaNotMet[0].toString());
			resolutionSlaMap.put("resolutionpriority3notmet_onshore",priority3SlaNotMet[1].toString());
			resolutionSlaMap.put("resolutionpriority3notmet",priority3SlaNotMet[2].toString());
			resolutionSlaMap.put("resolution_priority_3_sla_not_met_percent_offshore",priority3SlaNotMet[3]);
			resolutionSlaMap.put("resolution_priority_3_sla_not_met_percent_onshore",priority3SlaNotMet[4]);
			resolutionSlaMap.put("resolution_priority_3_sla_not_met_percent",priority3SlaNotMet[5]);
			/* Priority 3 -End*/
			
			/* Priority 4 -Start*/
			resolutionSlaMap.put("resolutionpriority4met_offshore",priority4SlaMet[0].toString());
			resolutionSlaMap.put("resolutionpriority4met_onshore",priority4SlaMet[1].toString());
			resolutionSlaMap.put("resolutionpriority4met",priority4SlaMet[2].toString());
			resolutionSlaMap.put("resolution_priority_4_sla_met_percent_offshore",priority4SlaMet[3]);
			resolutionSlaMap.put("resolution_priority_4_sla_met_percent_onshore",priority4SlaMet[4]);
			resolutionSlaMap.put("resolution_priority_4_sla_met_percent",priority4SlaMet[5]);
			resolutionSlaMap.put("resolutionpriority4notmet_offshore",priority4SlaNotMet[0].toString());
			resolutionSlaMap.put("resolutionpriority4notmet_onshore",priority4SlaNotMet[1].toString());
			resolutionSlaMap.put("resolutionpriority4notmet",priority4SlaNotMet[2].toString());
			resolutionSlaMap.put("resolution_priority_4_sla_not_met_percent_offshore",priority4SlaNotMet[3]);
			resolutionSlaMap.put("resolution_priority_4_sla_not_met_percent_onshore",priority4SlaNotMet[4]);
			resolutionSlaMap.put("resolution_priority_4_sla_not_met_percent",priority4SlaNotMet[5]);
			/* Priority 4 -End*/
		}		
		return resolutionSlaMap;		
	}	
	
	private int calculatePercentage(int ticketCount, int totalPriorityTickets){
		int percentage= 0;
		percentage = Math.round(ticketCount*100/totalPriorityTickets);
		lindeLogMgr.logMessage("DEBUG", "percentage -- > "+percentage);
		return percentage;		
	}

	private String[] slaCalculations(User user, String slaCriteria, String status, String priority){
		String[] slaResults = new String[10];
		StringBuffer query = new StringBuffer(5000);
		StringBuffer total_count_query = new StringBuffer(5000);
		query.append("from TicketInfo ticketInfo where priority like '%"+priority+"%' and (CURRENT_DATE - TO_DATE(ticketInfo.openTime,'DD/MM/YYYY HH24:MI:SS'))*24*60 "+slaCriteria+" AND ticketInfo.status='"+status+"'");
		if(user.getRoleId()==1){
			query.append(" AND ticketInfo.assigneeFullname='"+user.getLastName()+", "+user.getFirstName()+"'");
		} else if(user.getRoleId()==2 && user.getLocation().equals(TrackingToolConstant.LOCATION_OFFSHORE)){
			query.append(" AND ticketInfo.assignee_Fullname IN (Select last_name||', '||first_name from  TBL_USERS users where users.LOCATION='"+TrackingToolConstant.LOCATION_OFFSHORE+"')) ");
		} else if(user.getRoleId()==2 && user.getLocation().equals(TrackingToolConstant.LOCATION_ONSHORE)){
			query.append(" AND ticketInfo.assignee_Fullname IN (Select last_name||', '||first_name from  TBL_USERS users where users.LOCATION='"+TrackingToolConstant.LOCATION_OFFSHORE+"')) ");
		}
		
		ArrayList ticketList = (ArrayList) getSession().createQuery(query.toString()).list();
		
		total_count_query.append("from TicketInfo where priority like '%"+priority+"%' AND status='"+status+"'");
		ArrayList totalTicketCount = (ArrayList) getSession().createQuery(total_count_query.toString()).list();
		lindeLogMgr.logMessage("DEBUG", "totalTicketCount size is: " + totalTicketCount.size());
		int percentage= 0;		
		if(totalTicketCount.size() > 0) {		
			percentage = calculatePercentage(ticketList.size() , totalTicketCount.size());			
		} 
		
		lindeLogMgr.logMessage("DEBUG", "slaCalculations QUERY size is: " + ticketList.size());
		slaResults[0] = String.valueOf(ticketList.size());
		slaResults[1] = String.valueOf(percentage);		
		return slaResults;
	}
	
	private String[] slaCalculationsForPgM(User user, String slaCriteria, String status, String priority){
		String[] slaResults = new String[10];
		Object[] row;
		StringBuffer query = new StringBuffer(5000);
		StringBuffer total_count_query = new StringBuffer(5000);
		query.append("SELECT Set_A.offshore_count, Set_B.onshore_count, Set_C.total_count from  ");
		query.append("(Select count(*) as offshore_count from TBL_INCIDENT_INFO inciInfo where inciInfo.priority like '%"+priority+"%' and (CURRENT_DATE - TO_DATE(inciInfo.open_Time,'DD/MM/YYYY HH24:MI:SS'))*24*60 "+slaCriteria+" AND inciInfo.status='"+status+"' ");
		query.append("AND inciInfo.assignee_Fullname IN (Select last_name||', '||first_name from  TBL_USERS users where users.LOCATION='"+TrackingToolConstant.LOCATION_OFFSHORE+"'))  Set_A, ");
		query.append("(Select count(*) as onshore_count from TBL_INCIDENT_INFO inciInfo where inciInfo.priority like '%"+priority+"%' and (CURRENT_DATE - TO_DATE(inciInfo.open_Time,'DD/MM/YYYY HH24:MI:SS'))*24*60 "+slaCriteria+" AND inciInfo.status='"+status+"' ");
		query.append("AND inciInfo.assignee_Fullname IN (Select last_name||', '||first_name from  TBL_USERS users where users.LOCAtION='"+TrackingToolConstant.LOCATION_ONSHORE+"')) Set_B, ");
		query.append("(Select count(*) as total_count from TBL_INCIDENT_INFO inciInfo where inciInfo.priority like '%"+priority+"%' and (CURRENT_DATE - TO_DATE(inciInfo.open_Time,'DD/MM/YYYY HH24:MI:SS'))*24*60 "+slaCriteria+" AND inciInfo.status='"+status+"') Set_C ");
		
		lindeLogMgr.logMessage("DEBUG", "slaCalculationsForPgM QUERY is =======> "+query.toString());
		
		ArrayList ticketList = (ArrayList) getSession().createSQLQuery(query.toString()).list();
		lindeLogMgr.logMessage("DEBUG", "slaCalculationsForPgM QUERY size is: " + ticketList.size());
		
		for (int i=0;i<ticketList.size();i++) { 
			row = (Object[]) ticketList.get(i);
			slaResults[0]= row[0].toString();
			slaResults[1]= row[1].toString();
			slaResults[2] = row[2].toString();
			lindeLogMgr.logMessage("DEBUG", "PgM_QUERY ROW data is: " + row[0].toString()+":"+ row[1].toString()+":"+ row[2].toString());
		}	
		
		total_count_query.append("from TicketInfo where priority like '%"+priority+"%' AND status='"+status+"'");
		ArrayList totalTicketCount = (ArrayList) getSession().createQuery(total_count_query.toString()).list();
		
		int percentage= 0;	
		int percentage_offshore= 0;
		int percentage_onshore= 0;
		
		if(totalTicketCount.size() > 0) {		
			percentage = calculatePercentage(Integer.valueOf(slaResults[2].toString()) , totalTicketCount.size());			
		} 
		
		if(Integer.valueOf(slaResults[2].toString()) > 0) {		
			percentage_offshore = calculatePercentage(Integer.valueOf(slaResults[0].toString()) , Integer.valueOf(totalTicketCount.size()));	
			percentage_onshore = calculatePercentage(Integer.valueOf(slaResults[1].toString()) , Integer.valueOf(totalTicketCount.size()));
		} 
		
		slaResults[3] = String.valueOf(percentage_offshore);
		slaResults[4] = String.valueOf(percentage_onshore);		
		slaResults[5] = String.valueOf(percentage);
		lindeLogMgr.logMessage("DEBUG", "PgM_QUERY percentage data is: " + slaResults[3].toString()+":"+ slaResults[4].toString()+":"+ slaResults[5].toString());
		return slaResults;
	}
	
	private SLAMetrics getTicketVolume(int month){
		
		Object[] row;
		SLAMetrics sLAMetrics = new SLAMetrics();
		StringBuffer ticketVolQuery = new StringBuffer(5000);
		ticketVolQuery.append("SELECT Set_A.Resolved_count, Set_B.Assigned_Count, Set_C.Pending_count, Set_D.Suspended_Count, Set_E.WIP_Count,Set_F.month FROM  ");
		ticketVolQuery.append("(SELECT COUNT(*) Resolved_count from tbl_incident_info ticketInfo WHERE ticketInfo.status='Resolved' AND ");
		ticketVolQuery.append("TO_DATE(ticketInfo.Open_Time,'DD/MM/YYYY HH24:MI:SS') ");
		ticketVolQuery.append("BETWEEN TO_DATE(ticketInfo.Open_Time,'DD/MM/YYYY HH24:MI:SS') AnD ADD_MONTHS (TRUNC (SYSDATE, 'MM'), -"+month+")) Set_A, ");
		ticketVolQuery.append("(SELECT COUNT(*) Assigned_Count from tbl_incident_info ticketInfo WHERE ticketInfo.status='Assigned' AND ");
		ticketVolQuery.append("TO_DATE(ticketInfo.Open_Time,'DD/MM/YYYY HH24:MI:SS') BETWEEN TO_DATE(ticketInfo.Open_Time,'DD/MM/YYYY HH24:MI:SS') ");
		ticketVolQuery.append("AND ADD_MONTHS (TRUNC (SYSDATE, 'MM'), -"+month+")) Set_B, (SELECT COUNT(*) Pending_count from tbl_incident_info ticketInfo WHERE ");
		ticketVolQuery.append("ticketInfo.status='Pending' And TO_DATE(ticketInfo.Open_Time,'DD/MM/YYYY HH24:MI:SS') BETWEEN ");
		ticketVolQuery.append("TO_DATE(ticketInfo.Open_Time,'DD/MM/YYYY HH24:MI:SS') AND ADD_MONTHS (TRUNC (SYSDATE, 'MM'), -"+month+")) Set_C, ");
		ticketVolQuery.append("(SELECT COUNT(*) Suspended_Count from tbl_incident_info ticketInfo WHERE ticketInfo.status='Suspended' AND ");
		ticketVolQuery.append("TO_DATE(ticketInfo.Open_Time,'DD/MM/YYYY HH24:MI:SS') BETWEEN TO_DATE(ticketInfo.Open_Time,'DD/MM/YYYY HH24:MI:SS') AND ");
		ticketVolQuery.append(" ADD_MONTHS (TRUNC (SYSDATE, 'MM'), -"+month+")) Set_D, (SELECT COUNT(*) WIP_Count from tbl_incident_info ticketInfo WHERE ");
		ticketVolQuery.append("ticketInfo.status='Work In Progress' And TO_DATE(ticketInfo.Open_Time,'DD/MM/YYYY HH24:MI:SS') BETWEEN ");
		ticketVolQuery.append("TO_DATE(ticketInfo.Open_Time,'DD/MM/YYYY HH24:MI:SS') AND ADD_MONTHS (TRUNC (SYSDATE, 'MM'), -"+month+")) Set_E, ");
		ticketVolQuery.append("(SELECT to_char(ADD_MONTHS(SYSDATE, -"+month+"), 'MON-YYYY') as month from dual) Set_F");
		
		ArrayList ticketVolumeList = (ArrayList) getSession().createSQLQuery(ticketVolQuery.toString()).list();
		lindeLogMgr.logMessage("DEBUG", "ticket volume size is: " + ticketVolumeList.size());
		
		
		
		for (int i=0;i<ticketVolumeList.size();i++) { 
			row = (Object[]) ticketVolumeList.get(i);
			lindeLogMgr.logMessage("DEBUG", "ticket volume in row["+i+"]" + row[i].toString());
			sLAMetrics = new SLAMetrics();
			sLAMetrics.setResolvedCount(row[0].toString());
			sLAMetrics.setAssignedCount(row[1].toString());
			sLAMetrics.setPendingCount(row[2].toString());
			sLAMetrics.setSuspendedCount(row[3].toString());
			sLAMetrics.setWipCount(row[4].toString());		
			sLAMetrics.setMonth(row[5].toString());
		}
		
		return sLAMetrics;
		
	} 
}
