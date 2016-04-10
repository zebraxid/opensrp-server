package org.opensrp.scheduler.service;

import static org.opensrp.common.AllConstants.CommonFormFields.ID;
import static org.opensrp.common.AllConstants.PSRFFields.FW_PSRDATE;
import static org.opensrp.common.util.EasyMap.create;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.opensrp.dto.ActionData;
import org.opensrp.dto.AlertStatus;
import org.opensrp.dto.BeneficiaryType;
import org.opensrp.dto.ScheduleData;

import org.opensrp.scheduler.ScheduleLog;
import org.opensrp.scheduler.repository.AllReportActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class ReportActionService {
	
	private AllReportActions allReportActions;	
	@Autowired
	public ReportActionService(AllReportActions allReportActions)
	{
		this.allReportActions = allReportActions;	
	}
	public void updateScheduleLog(BeneficiaryType beneficiaryType, String caseID, String instanceId, String anmIdentifier, String scheduleName, String visitCode, AlertStatus alertStatus, DateTime startDate, DateTime expiryDate, DateTime currentWindowCloseDate,String trackId){
		System.out.println("beneficiaryType:"+beneficiaryType+"caseID:"+caseID+"instanceId:"+instanceId);
		ScheduleLog  schedule = allReportActions.findByInstanceId(caseID);
		   if(schedule != null){
			   System.out.println("cwindow:"+schedule.getCurrentWindow()+"this:"+alertStatus.value() + ":"+alertStatus);
			   if(!schedule.getCurrentWindow().equals(alertStatus)){				   
			    	Map<String, String > mapData = new HashMap<>();
			    	mapData.put("beneficiaryType", beneficiaryType.value());
			    	mapData.put("scheduleName", scheduleName);
			    	mapData.put("visitCode", visitCode);
			    	mapData.put("alertStatus", alertStatus.value());
			    	mapData.put("startDate", startDate.toLocalDate().toString());
			    	mapData.put("expiryDate", expiryDate.toLocalDate().toString());	    	
			    	schedule.data().add(mapData);
			    	schedule.setRevision(schedule.getRevision());
			    	schedule.currentWindow(alertStatus);
			    	schedule.currentWindowStartDate(startDate);
			    	schedule.currentWindowEndDate(expiryDate);
			    	long millis = Calendar.getInstance().getTimeInMillis();
			    	schedule.timestamp(millis);
			    	allReportActions.update(schedule);
			   }
			   
		   }
	}
	public void alertForReporting(BeneficiaryType beneficiaryType, String caseID, String instanceId, String anmIdentifier, String scheduleName, String visitCode, AlertStatus alertStatus, DateTime startDate, DateTime expiryDate, DateTime currentWindowCloseDate,String trackId)
	{	  
		
		 allReportActions.addAlert(new ScheduleLog(caseID, instanceId, anmIdentifier, ScheduleData.createAlert(beneficiaryType, scheduleName, visitCode, alertStatus, startDate, expiryDate),trackId,alertStatus,currentWindowCloseDate,startDate,expiryDate,scheduleName));
	   
	}

		
}
