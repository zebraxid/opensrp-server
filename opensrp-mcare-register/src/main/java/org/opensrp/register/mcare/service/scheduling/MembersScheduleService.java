/**
 * @author Asifur
 */

package org.opensrp.register.mcare.service.scheduling;

import static java.text.MessageFormat.format;
import static org.opensrp.dto.BeneficiaryType.members;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.DateTimeDuration.duration;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.WomanScheduleConstants.*;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Weeks;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.common.util.DateUtil;
import org.opensrp.dto.ActionData;
import org.opensrp.dto.AlertStatus;
import org.opensrp.dto.BeneficiaryType;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.mcare.OpenSRPScheduleConstants.DateTimeDuration;
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
	private HealthSchedulerService scheduler;
	private AllActions allActions;
	private ScheduleLogService scheduleLogService;
	
	@Autowired
	public MembersScheduleService(HealthSchedulerService scheduler, AllActions allActions, ScheduleLogService scheduleLogService)
	{
		this.scheduler = scheduler;
		this.allActions = allActions;
		this.scheduleLogService = scheduleLogService;		
	}
	
	public void enrollWoman(String entityId,String provider,String instWomaneId,Map<String, String> membersFields) {
    
    	String milestone=null;    
    	DateTime start = null;
        DateTime WomanStartDate = null;
        DateTime WomanExpireDate = null;
        AlertStatus alertStaus = null;
        Date date1 = null, date2 = null, date3 = null, date4 = null, date5 = null;        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        
    	try {
			date1 = format.parse(membersFields.get(Date_of_TT1));	
			date2 = format.parse(membersFields.get(Date_of_TT2));	
			date3 = format.parse(membersFields.get(Date_of_TT3));	
			date4 = format.parse(membersFields.get(Date_of_TT4));	
			date5 = format.parse(membersFields.get(Date_of_TT5));	
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	
    	start = new DateTime(date1);
        milestone = SCHEDULE_Woman_1;   
        alertStaus = AlertStatus.upcoming;
        WomanStartDate = new DateTime(start);
        WomanExpireDate = new DateTime(start).plusDays(DateTimeDuration.Woman1Start);
        
        logger.info(format("Enrolling Woman with Entity id:{0} to Woman schedule, milestone: {1}.", entityId, milestone));
        scheduler.enrollIntoSchedule(entityId, SCHEDULE_Woman, SCHEDULE_Woman_1, date1.toString());
        scheduler.enrollIntoSchedule(entityId, SCHEDULE_Woman, SCHEDULE_Woman_2, date2.toString());
        scheduler.enrollIntoSchedule(entityId, SCHEDULE_Woman, SCHEDULE_Woman_3, date3.toString());
        scheduler.enrollIntoSchedule(entityId, SCHEDULE_Woman, SCHEDULE_Woman_4, date4.toString());
        scheduler.enrollIntoSchedule(entityId, SCHEDULE_Woman, SCHEDULE_Woman_5, date5.toString());
  
    }
    
    public void fullfillSchedule(String caseID, String scheduleName, String instWomaneId, long timestamp){
    	try{
	    	scheduleLogService.fullfillSchedule(caseID, scheduleName, instWomaneId, timestamp);
	    	logger.info("fullfillSchedule a Schedule with id : "+caseID);
    	}catch(Exception e){
    		logger.info("Does not fullfill a schedule:"+e.getMessage());
    	}
    }
    
    public void unEnrollFromAllSchedules(String entityId) {
        scheduler.unEnrollFromAllSchedules(entityId);
    }

    private void unEnrollFromSchedule(String entityId, String anmId, String scheduleName) {
        logger.info(format("Un-enrolling Woman with Entity id:{0} from schedule: {1}", entityId, scheduleName));
        scheduler.unEnrollFromSchedule(entityId, anmId, scheduleName);
    }
    
    public void fullfillMilestone(String entityId, String providerId, String scheduleName,LocalDate completionDate ){
    	try{
    		scheduler.fullfillMilestone(entityId, providerId, scheduleName, completionDate);
    		logger.info("fullfillMilestone with id: :"+entityId);
    	}catch(Exception e){
    		logger.info("Does not a fullfillMilestone :"+e.getMessage());
    	}
    }

}

