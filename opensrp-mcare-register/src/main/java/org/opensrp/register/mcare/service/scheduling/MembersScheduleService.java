/**
 * @author Asifur
 */

package org.opensrp.register.mcare.service.scheduling;

import static java.text.MessageFormat.format;
import static org.opensrp.dto.BeneficiaryType.members;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.DateTimeDuration.duration;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MembersSchedulesConstants.Members_SCHEDULE;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MembersSchedulesConstantsImediate.IMD_Members_SCHEDULE;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.HHSchedulesConstants.HH_SCHEDULE_CENSUS;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.motechproject.scheduletracking.api.service.ScheduleTrackingService;
import org.opensrp.common.AllConstants.MembersSchedulesConstantsImediate;
import org.opensrp.dto.ActionData;
import org.opensrp.dto.AlertStatus;
import org.opensrp.dto.BeneficiaryType;
import org.opensrp.scheduler.Action;
import org.opensrp.scheduler.HealthSchedulerService;
import org.opensrp.scheduler.repository.AllActions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MembersScheduleService {

	private static Logger logger = LoggerFactory.getLogger(MembersScheduleService.class.toString());
	private final ScheduleTrackingService scheduleTrackingService;
	private HealthSchedulerService scheduler;
	private AllActions allActions;
	private ScheduleLogService scheduleLogService;
	
	@Autowired
	public MembersScheduleService(HealthSchedulerService scheduler,ScheduleTrackingService scheduleTrackingService,AllActions allActions,ScheduleLogService scheduleLogService)
	{
		this.scheduler = scheduler;
		this.scheduleTrackingService = scheduleTrackingService;
		this.allActions = allActions;
		this.scheduleLogService = scheduleLogService;
		
	}
	
	public void enrollIntoMilestoneOfPSRF(String caseId, String date,String provider,String instanceId)
	{
	    logger.info(format("Enrolling Members into PSRF schedule. Id: {0}", caseId));
	    
		scheduler.enrollIntoSchedule(caseId, Members_SCHEDULE, date);
		scheduleLogService.createNewScheduleLogandUnenrollImmediateSchedule(caseId, date, provider, instanceId, IMD_Members_SCHEDULE, Members_SCHEDULE, members, duration);

	}
	public void unEnrollFromScheduleCensus(String caseId, String providerId, String scheduleName){
		//scheduler.unEnrollFromScheduleCensus(caseId, providerId, HH_SCHEDULE_CENSUS);
		try{
			scheduler.fullfillMilestoneAndCloseAlert(caseId, providerId, HH_SCHEDULE_CENSUS, new LocalDate());
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		
	}
	
	public void unEnrollFromScheduleOfPSRF(String caseId, String providerId, String scheduleName)
    {
        logger.info(format("Unenrolling Members from PSRF schedule. Id: {0}", caseId));
        try{
        	//scheduler.unEnrollFromSchedule(caseId, providerId, Members_SCHEDULE);
        	scheduler.fullfillMilestoneAndCloseAlert(caseId, providerId, Members_SCHEDULE, new LocalDate());
        }catch(Exception e){
        	logger.info(format("Failed to UnEnrollFromSchedule PSRF"));
        }
        try{
        	//scheduler.unEnrollFromScheduleimediate(caseId, providerId, IMD_Members_SCHEDULE);
        	scheduler.fullfillMilestoneAndCloseAlert(caseId, providerId, IMD_Members_SCHEDULE, new LocalDate());
        }catch(Exception e){
        	logger.info(e.getMessage());
        }
        
    }

	public void imediateEnrollIntoMilestoneOfPSRF(String caseId, String date,String provider,String instanceId)	
	{
	    logger.info(format("Enrolling Members_SCHEDULE into PSRF schedule. Id: {0}", caseId));	  
	    scheduler.enrollIntoSchedule(caseId, IMD_Members_SCHEDULE, date);	 
	    scheduleLogService.createImmediateScheduleAndScheduleLog(caseId, date, provider, instanceId, BeneficiaryType.members, Members_SCHEDULE, duration,IMD_Members_SCHEDULE);
	    
	}
}
