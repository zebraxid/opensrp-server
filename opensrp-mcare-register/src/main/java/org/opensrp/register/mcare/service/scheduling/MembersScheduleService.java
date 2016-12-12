/**
 * @author Asifur
 */
package org.opensrp.register.mcare.service.scheduling;

import static java.text.MessageFormat.format;
import static org.opensrp.dto.BeneficiaryType.members;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.DateTimeDuration.duration;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MemberScheduleConstants.*;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.HHSchedulesConstants.HH_SCHEDULE_CENSUS;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.motechproject.scheduletracking.api.service.ScheduleTrackingService;
import org.opensrp.common.AllConstants.ScheduleNames;
import org.opensrp.common.util.DateUtil;
import org.opensrp.dto.BeneficiaryType;
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
	    logger.info(format("Enrolling Elco into PSRF schedule. Id: {0}", caseId));
	    
		scheduler.enrollIntoSchedule(caseId, ELCO_SCHEDULE_PSRF, date);
		scheduleLogService.createNewScheduleLogandUnenrollImmediateSchedule(caseId, date, provider, instanceId, IMD_ELCO_SCHEDULE_PSRF, ELCO_SCHEDULE_PSRF, members, duration);
	}
	
	public void enrollIntoMilestoneOfBNF(String caseId, String date,String provider,String instanceId)
	{
	    logger.info(format("Enrolling Elco into BNF schedule. Id: {0}", caseId));
	    
		scheduler.enrollIntoSchedule(caseId, SCHEDULE_Woman_BNF, date);
		scheduleLogService.createNewScheduleLogandUnenrollImmediateSchedule(caseId, date, provider, instanceId, IMD_SCHEDULE_Woman_BNF, SCHEDULE_Woman_BNF, members, duration);
	}
	
	public void enrollIntoMilestoneOfChild_vaccination(String caseId, String date,String provider,String instanceId)
	{
	    logger.info(format("Enrolling Elco into child_bcg schedule. Id: {0}", caseId));
	    
		scheduler.enrollIntoSchedule(caseId, child_bcg, date);
		scheduleLogService.createNewScheduleLogandUnenrollImmediateSchedule(caseId, date, provider, instanceId, IMD_child_bcg, child_bcg, members, duration);
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
        logger.info(format("Unenrolling Elco from PSRF schedule. Id: {0}", caseId));
        try{
        	//scheduler.unEnrollFromSchedule(caseId, providerId, ELCO_SCHEDULE_PSRF);
        	scheduler.fullfillMilestoneAndCloseAlert(caseId, providerId, ELCO_SCHEDULE_PSRF, new LocalDate());
        }catch(Exception e){
        	logger.info(format("Failed to UnEnrollFromSchedule PSRF"));
        }
        try{
        	//scheduler.unEnrollFromScheduleimediate(caseId, providerId, IMD_ELCO_SCHEDULE_PSRF);
        	scheduler.fullfillMilestoneAndCloseAlert(caseId, providerId, IMD_ELCO_SCHEDULE_PSRF, new LocalDate());
        }catch(Exception e){
        	logger.info(e.getMessage());
        }
        
    }
	
	public void unEnrollFromScheduleOfBNF(String caseId, String providerId, String scheduleName)
    {
        logger.info(format("Unenrolling Elco from BNF schedule. Id: {0}", caseId));
        try{
        	//scheduler.unEnrollFromSchedule(caseId, providerId, ELCO_SCHEDULE_BNF);
        	scheduler.fullfillMilestoneAndCloseAlert(caseId, providerId, SCHEDULE_Woman_BNF, new LocalDate());
        }catch(Exception e){
        	logger.info(format("Failed to UnEnrollFromSchedule BNF"));
        }
        try{
        	//scheduler.unEnrollFromScheduleimediate(caseId, providerId, IMD_ELCO_SCHEDULE_BNF);
        	scheduler.fullfillMilestoneAndCloseAlert(caseId, providerId, IMD_SCHEDULE_Woman_BNF, new LocalDate());
        }catch(Exception e){
        	logger.info(e.getMessage());
        }
        
    }
	
	public void unEnrollFromScheduleOfChild_vaccination(String caseId, String providerId, String scheduleName)
    {
        logger.info(format("Unenrolling from child_bcg schedule. Id: {0}", caseId));
        try{
        	//scheduler.unEnrollFromSchedule(caseId, providerId, child_bcg);
        	scheduler.fullfillMilestoneAndCloseAlert(caseId, providerId, child_bcg, new LocalDate());
        }catch(Exception e){
        	logger.info(format("Failed to UnEnrollFromSchedule child_bcg"));
        }
        try{
        	//scheduler.unEnrollFromScheduleimediate(caseId, providerId, IMD_child_bcg);
        	scheduler.fullfillMilestoneAndCloseAlert(caseId, providerId, IMD_child_bcg, new LocalDate());
        }catch(Exception e){
        	logger.info(e.getMessage());
        }
        
    }
	
	public void fullfillMilestone(String entityId, String providerId, String scheduleName,LocalDate completionDate ){
    	try{
    		scheduler.fullfillMilestone(entityId, providerId, scheduleName, completionDate);
    		logger.info("fullfillMilestone with id: :"+entityId);
    	}catch(Exception e){
    		logger.info("Does not a fullfillMilestone :"+e.getMessage());
    	}
    }
	
	private  Date getDateTime(){		
		InputStream input = MembersScheduleService.class.getClassLoader().getResourceAsStream("imdediate-elco-psrf.json");
		String result = "";
	    try {
	        BufferedReader br = new BufferedReader(new InputStreamReader(input));
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();
	        while (line != null) {
	            sb.append(line);
	            line = br.readLine();
	        }
	        result = sb.toString();
	        
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	    Date todayDate = new Date();
	    try {
			JSONObject jsonObj = new JSONObject(result);			
			JSONArray milestones = jsonObj.getJSONArray("milestones");
			for (int k = 0; k < milestones.length(); k++) {
				JSONObject jsonObjs = new JSONObject(milestones.getJSONObject(k).getString("scheduleWindows").toString());
				String max = jsonObjs.getString("due").replace("[", "");
				max = max.replace("]", "").replace("Weeks", "").replaceAll("\"", "").trim();
				int weeks = Integer.parseInt(max);
				Date today = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(today);
				calendar.add(Calendar.WEEK_OF_YEAR,weeks);
				todayDate = calendar.getTime();				
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	   
		return todayDate;
	}

	public void imediateEnrollIntoMilestoneOfPSRF(String caseId, String date,String provider,String instanceId)	
	{
	    logger.info(format("Enrolling Elco into PSRF schedule. Id: {0}", caseId));	  
	    scheduler.enrollIntoSchedule(caseId, ScheduleNames.IMD_ELCO_SCHEDULE_PSRF, date);	 
	    scheduleLogService.createImmediateScheduleAndScheduleLog(caseId, date, provider, instanceId, BeneficiaryType.members, ELCO_SCHEDULE_PSRF, duration,ScheduleNames.IMD_ELCO_SCHEDULE_PSRF);
	    
	}
	
	public void imediateEnrollIntoMilestoneOfBNF(String caseId, String date,String provider,String instanceId)	
	{
	    logger.info(format("Enrolling Elco into BNF schedule. Id: {0}", caseId));	  
	    scheduler.enrollIntoSchedule(caseId, ScheduleNames.IMD_SCHEDULE_Woman_BNF, date);	 
	    scheduleLogService.createImmediateScheduleAndScheduleLog(caseId, date, provider, instanceId, BeneficiaryType.members, SCHEDULE_Woman_BNF, duration,ScheduleNames.IMD_SCHEDULE_Woman_BNF);
	    
	}
	
	public void imediateEnrollIntoMilestoneOfChild_vaccination(String caseId, String date,String provider,String instanceId)	
	{
	    logger.info(format("Enrolling Elco into child_vaccination schedule. Id: {0}", caseId));	  
	    scheduler.enrollIntoSchedule(caseId, ScheduleNames.IMD_child_bcg, date);	 
	    scheduleLogService.createImmediateScheduleAndScheduleLog(caseId, date, provider, instanceId, BeneficiaryType.members, child_bcg, duration,ScheduleNames.IMD_child_bcg);
	    
	}
	
	public void enrollIntoSchedule(String entityId, String date,String scheduleName){
	    logger.info(format("Enrolling into {1} schedule. Id: {0}", scheduleName, entityId));	    
		scheduler.enrollIntoSchedule(entityId, scheduleName, date);	
	}
	
	public void enrollAfterimmediateVisit(String caseId,String provider,String date,String instanceId,String scheduleName,String ImmediateScheduleName) {
		logger.info(format("Enrolling Members into schedule. Id: {0}", caseId));	    
		scheduler.enrollIntoSchedule(caseId, scheduleName, date);
		scheduleLogService.createNewScheduleLogandUnenrollImmediateSchedule(caseId, date, provider, instanceId, ImmediateScheduleName, scheduleName, BeneficiaryType.members, duration);
	}
	
	public void fullfillSchedule(String caseID, String scheduleName, String instanceId, long timestamp){
    	try{
	    	scheduleLogService.fullfillSchedule(caseID, scheduleName, instanceId, timestamp);
	    	logger.info("fullfillSchedule a Schedule with id : "+caseID);
    	}catch(Exception e){
    		logger.info("Does not fullfill a schedule:"+e.getMessage());
    	}
    }
	
	public void unEnrollAndCloseSchedule(String entityId, String anmId, String scheduleName, LocalDate completionDate) {
        logger.info(format("Un-enrolling Members with Entity id:{0} from schedule: {1}", entityId, scheduleName));
        scheduler.unEnrollAndCloseSchedule(entityId, anmId, scheduleName, completionDate);
    }
	
	public void enrollIntoCorrectMilestoneOfANCRVCare(String entityId, LocalDate referenceDateForSchedule) {
        String milestone=null;

        if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Days.ZERO.toPeriod().plusDays(168))) {
            milestone = SCHEDULE_ANC_1;
        } else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Days.ZERO.toPeriod().plusDays(224))) {
            milestone = SCHEDULE_ANC_2;
        } else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Days.ZERO.toPeriod().plusDays(252))) {
            milestone = SCHEDULE_ANC_3;
        } else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Days.ZERO.toPeriod().plusDays(960))) {
            milestone = SCHEDULE_ANC_4;
        } else{
        	
        }

        logger.info(format("Enrolling ANC with Entity id:{0} to ANC schedule, milestone: {1}.", entityId, milestone));
        scheduler.enrollIntoSchedule(entityId, SCHEDULE_ANC, milestone, referenceDateForSchedule.toString());
    }
	
	public void enrollIntoCorrectMilestoneOfPNCRVCare(String entityId, LocalDate referenceDateForSchedule) {
        String milestone=null;

        if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Days.ONE.toPeriod())) {
            milestone = SCHEDULE_PNC_1;
        } else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Days.SIX.toPeriod())) {
            milestone = SCHEDULE_PNC_2;
        } else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Days.SEVEN.toPeriod().plusDays(34))) {
            milestone = SCHEDULE_PNC_3;
        } else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Days.SEVEN.toPeriod().plusDays(42))) {
            milestone = SCHEDULE_PNC_4;
        } else{
        	
        }

        logger.info(format("Enrolling PNC with Entity id:{0} to PNC schedule, milestone: {1}.", entityId, milestone));
        scheduler.enrollIntoSchedule(entityId, SCHEDULE_PNC, milestone, referenceDateForSchedule.toString());
    }
	
	 public void enrollPNCForMother(String entityId, String sch_name, LocalDate referenceDateForSchedule) {
	    logger.info(format("Enrolling PNC with Entity id:{0} to PNC schedule, milestone: {1}.", entityId, sch_name));
	    scheduler.enrollIntoSchedule(entityId, SCHEDULE_PNC, sch_name, referenceDateForSchedule.toString());
	 }
	 
	 public void unEnrollFromSchedule(String entityId, String anmId, String scheduleName) {
        logger.info(format("Un-enrolling with Entity id:{0} from schedule: {1}", entityId, scheduleName));
        scheduler.unEnrollFromSchedule(entityId, anmId, scheduleName);
     }
}
