/**
 * @author Asifur
 */

package org.opensrp.register.mcare.service.scheduling;

import static java.text.MessageFormat.format;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.WomanScheduleConstants.*;
import static org.opensrp.common.AllConstants.MEMBERSRegistrationFields.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

import java.util.Map;

import org.joda.time.LocalDate;

import org.opensrp.scheduler.HealthSchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MembersScheduleService {

	private static Logger logger = LoggerFactory.getLogger(MembersScheduleService.class.toString());
	private HealthSchedulerService scheduler;
	
	@Autowired
	public MembersScheduleService(HealthSchedulerService scheduler)
	{
		this.scheduler = scheduler;	
	}
	
	public void enrollWomanMeaslesVisit(String entityId,String provider,String instWomaneId,LocalDate referenceDateForSchedule) {
		
        logger.info(format("Enrolling Woman with Entity id:{0} to Woman schedule Woman Vaccination, milestone: {1}.", entityId, SCHEDULE_Woman_Measles));
        scheduler.enrollIntoSchedule(entityId, SCHEDULE_Woman, SCHEDULE_Woman_Measles, referenceDateForSchedule.toString());
    }
	
	public void enrollWomanTTVisit(String entityId,String provider,String instWomaneId,Map<String, String> membersFields) {

        Date date1 = null;        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        
    	try {
			date1 = format.parse(membersFields.get(Date_of_TT1));		
		} catch (ParseException e) {
			e.printStackTrace();
		}
        
        logger.info(format("Enrolling Woman with Entity id:{0} to Woman schedule Woman Vaccination, milestone: {1}.", entityId, SCHEDULE_Woman_1));
        scheduler.enrollIntoSchedule(entityId, SCHEDULE_Woman, SCHEDULE_Woman_1, LocalDate.parse(membersFields.get(Date_of_TT1)).toString());
  
    }
	
	public void enrollTTVisit(String entityId,String provider,String instWomaneId, String date) {

        Date date1 = null;        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        
    	try {
			date1 = format.parse(date);		
		} catch (ParseException e) {
			e.printStackTrace();
		}
        
        logger.info(format("Enrolling Woman with Entity id:{0} to Woman schedule Woman Vaccination, milestone: {1}.", entityId, SCHEDULE_Woman_1));
        scheduler.enrollIntoSchedule(entityId, SCHEDULE_Woman, SCHEDULE_Woman_1, LocalDate.parse(date).toString());
  
    }
	
	public void enrollTT1_Visit(String entityId,String provider,String instWomaneId,String date) {

        Date date2 = null;    
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        
    	try {	
			date2 = format.parse(date);		
		} catch (ParseException e) {
			e.printStackTrace();
		}
        
        logger.info(format("Enrolling Woman with Entity id:{0} to Woman schedule Woman Vaccination, milestone: {1}.", entityId, SCHEDULE_Woman_2));
        scheduler.enrollIntoSchedule(entityId, SCHEDULE_Woman, SCHEDULE_Woman_2, LocalDate.parse(date).toString());
  
    }
	
	public void enrollTT2_Visit(String entityId,String provider,String instWomaneId,String date) {

        Date date3 = null;    
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        
    	try {	
			date3 = format.parse(date);		
		} catch (ParseException e) {
			e.printStackTrace();
		}
        
        logger.info(format("Enrolling Woman with Entity id:{0} to Woman schedule Woman Vaccination, milestone: {1}.", entityId, SCHEDULE_Woman_3));
        scheduler.enrollIntoSchedule(entityId, SCHEDULE_Woman, SCHEDULE_Woman_3, LocalDate.parse(date).toString());
  
    }
	
	public void enrollTT3_Visit(String entityId,String provider,String instWomaneId,String date) {

        Date date4 = null;    
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        
    	try {	
			date4 = format.parse(date);		
		} catch (ParseException e) {
			e.printStackTrace();
		}
        
        logger.info(format("Enrolling Woman with Entity id:{0} to Woman schedule Woman Vaccination, milestone: {1}.", entityId, SCHEDULE_Woman_4));
        scheduler.enrollIntoSchedule(entityId, SCHEDULE_Woman, SCHEDULE_Woman_4, LocalDate.parse(date).toString());
  
    }
	
	public void enrollTT4_Visit(String entityId,String provider,String instWomaneId,String date) {

        Date date5 = null;    
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        
    	try {	
			date5 = format.parse(date);		
		} catch (ParseException e) {
			e.printStackTrace();
		}
        
        logger.info(format("Enrolling Woman with Entity id:{0} to Woman schedule Woman Vaccination, milestone: {1}.", entityId, SCHEDULE_Woman_5));
        scheduler.enrollIntoSchedule(entityId, SCHEDULE_Woman, SCHEDULE_Woman_5, LocalDate.parse(date).toString());
  
    }
	
	public void enrollTT5_Visit(String entityId,String provider) {
        unEnrollFromSchedule(entityId, provider, SCHEDULE_Woman);
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

