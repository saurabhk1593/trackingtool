package com.techm.trackingtool.admin.vo;

import com.techm.trackingtool.util.LindeList;
import com.techm.trackingtool.util.LindeMap;

public class MetricsVO {

	private LindeMap incidentAckSlaMap;
	private LindeMap incidentResolutionSlaMap;
	private LindeList asOnDateTicketVolume;
	

	public LindeList getAsOnDateTicketVolume() {
		return asOnDateTicketVolume;
	}

	public void setAsOnDateTicketVolume(LindeList asOnDateTicketVolume) {
		this.asOnDateTicketVolume = asOnDateTicketVolume;
	}

	public LindeMap getIncidentResolutionSlaMap() {
		return incidentResolutionSlaMap;
	}

	public void setIncidentResolutionSlaMap(LindeMap incidentResolutionSlaMap) {
		this.incidentResolutionSlaMap = incidentResolutionSlaMap;
	}

	public LindeMap getIncidentAckSlaMap() {
		return incidentAckSlaMap;
	}

	public void setIncidentAckSlaMap(LindeMap incidentAckSlaMap) {
		this.incidentAckSlaMap = incidentAckSlaMap;
	}
	
}
