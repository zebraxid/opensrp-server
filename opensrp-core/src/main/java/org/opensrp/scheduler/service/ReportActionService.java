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
import org.joda.time.LocalDate;
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
	public void updateScheduleLog(BeneficiaryType beneficiaryType, String caseID, String instanceId, String anmIdentifier, String scheduleName, String visitCode, AlertStatus alertStatus, DateTime startDate, DateTime expiryDate, DateTime currentWindowCloseDate,String trackId,long BTS,long timestamp){
		ScheduleLog  schedule = allReportActions.findByTimestampIdByCaseIdByname(BTS,caseID,scheduleName);
		if(schedule != null){			
			   if(!schedule.getCurrentWindow().equals(alertStatus) ){				   
				   this.updateDataScheduleLog(beneficiaryType, caseID, instanceId, anmIdentifier, scheduleName, visitCode, alertStatus, startDate, expiryDate, currentWindowCloseDate, trackId, BTS, timestamp, schedule);
			   }else{				   
				   schedule.setRevision(schedule.getRevision());				  
				   schedule.data().get(0).put("expiryDate", expiryDate.toLocalDate().toString());	
				   schedule.setVisitCode(visitCode);
				   schedule.timestamp(timestamp);
			       allReportActions.update(schedule);
			   }
		   }
	}
public void updateScheduleLogMotherType(BeneficiaryType beneficiaryType, String caseID, String instanceId, String anmIdentifier, String scheduleName, String visitCode, AlertStatus alertStatus, DateTime startDate, DateTime expiryDate, DateTime currentWindowCloseDate,String trackId,long BTS,long timestamp){
		ScheduleLog  schedule = allReportActions.findByTimestampIdByCaseIdByname(BTS,caseID,scheduleName);
		if(schedule != null){	
			System.out.println("schedule.getVisitCode():"+schedule.getVisitCode()+"visitCode:"+visitCode);
			   if(!schedule.getVisitCode().equalsIgnoreCase(visitCode) ){
				   this.updateDataScheduleLog(beneficiaryType, caseID, instanceId, anmIdentifier, scheduleName, visitCode, alertStatus, startDate, expiryDate, currentWindowCloseDate, trackId, BTS, timestamp, schedule);
			   }else if(schedule.getVisitCode().equalsIgnoreCase(visitCode)){				   
				   if(!schedule.getCurrentWindow().equals(alertStatus)){
					   this.updateDataScheduleLog(beneficiaryType, caseID, instanceId, anmIdentifier, scheduleName, visitCode, alertStatus, startDate, expiryDate, currentWindowCloseDate, trackId, BTS, timestamp, schedule);
				   }else{
					   schedule.setRevision(schedule.getRevision());				  
					   schedule.data().get(0).put("expiryDate", expiryDate.toLocalDate().toString());	
					   schedule.setVisitCode(visitCode);
					   schedule.timestamp(timestamp);
				       allReportActions.update(schedule);
			       }
			   }else{
				   
			   }
		   }
	}
	public void alertForReporting(BeneficiaryType beneficiaryType, String caseID, String instanceId, String anmIdentifier, String scheduleName, String visitCode, AlertStatus alertStatus, DateTime startDate, DateTime expiryDate, DateTime currentWindowCloseDate,String trackId,long timeStamp)
	{	  
		allReportActions.addAlert(new ScheduleLog(caseID, instanceId, anmIdentifier, ScheduleData.createAlert(beneficiaryType, scheduleName, visitCode, alertStatus, startDate, expiryDate),trackId,alertStatus,currentWindowCloseDate,startDate,expiryDate,scheduleName,timeStamp,visitCode));
	   
	}
	public void updateDataScheduleLog(BeneficiaryType beneficiaryType, String caseID, String instanceId, String anmIdentifier, String scheduleName, String visitCode, AlertStatus alertStatus, DateTime startDate, DateTime expiryDate, DateTime currentWindowCloseDate,String trackId,long BTS,long timestamp ,ScheduleLog  schedule){
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
    	schedule.timestamp(timestamp);
    	schedule.setVisitCode(visitCode);
    	allReportActions.update(schedule);
	}
	
	public void schedulefullfill(String caseID,String scheduleName,String instanceId,long timestamp){
		ScheduleLog  schedule = allReportActions.findByTimestampIdByCaseIdByname(timestamp,caseID,scheduleName);		
		int size = schedule.data().size();
		schedule.data().get(size-1).put("fullfillmentDate",new LocalDate().toString());		
		schedule.data().get(size-1).put("fullfuillBySubmission",instanceId);		
		schedule.setRevision(schedule.getRevision());
		schedule.timestamp(timestamp);
		allReportActions.update(schedule);
		
	}
		
}
