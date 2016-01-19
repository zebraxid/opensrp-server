/**
 * @author proshanto
 * */
package org.opensrp.register.mcare.service.scheduling;

import java.util.List;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.ELCOSchedulesConstants.ELCO_SCHEDULE_PSRF;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;
import org.motechproject.scheduletracking.api.domain.Enrollment;
import org.motechproject.scheduletracking.api.domain.MilestoneFulfillment;
import org.opensrp.common.AllConstants.ELCOSchedulesConstantsImediate;
import org.opensrp.connector.HttpUtil;
import org.opensrp.connector.openmrs.service.OpenmrsSchedulerService;
import org.opensrp.connector.openmrs.service.OpenmrsService;
import org.opensrp.connector.openmrs.service.OpenmrsUserService;
import org.opensrp.dto.AlertStatus;
import org.opensrp.dto.BeneficiaryType;
import org.opensrp.scheduler.Action;
import org.opensrp.scheduler.service.AllEnrollmentWrapper;
import org.opensrp.scheduler.service.ReportActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ScheduleLogService extends OpenmrsService{
	private static final String TRACK_URL = "ws/rest/v1/scheduletracker/track";
	private static final String TRACK_MILESTONE_URL = "ws/rest/v1/scheduletracker/trackmilestone";
	private static final String SCHEDULE_URL = "ws/rest/v1/scheduletracker/schedule";
	private static final String MILESTONE_URL = "ws/rest/v1/scheduletracker/milestone";
	
	private ReportActionService reportActionService;
	private final AllEnrollmentWrapper allEnrollments;

	
	@Autowired
	public ScheduleLogService(ReportActionService reportActionService,AllEnrollmentWrapper allEnrollments){
		this.reportActionService = reportActionService;
		this.allEnrollments = allEnrollments;
	}
	
	
	public void saveScheduleLog(BeneficiaryType beneficiaryType, String caseID, String instanceId, String anmIdentifier, String scheduleName, String visitCode, AlertStatus alertStatus, DateTime startDate, DateTime expiryDate,String immediateScheduleName){
		List<Enrollment> el =this.findEnrollmentByCaseIdAndScheduleName(caseID,immediateScheduleName);
		String trackId = "";		
		for (Enrollment e : el){
			//trackId = this.saveEnrollDataToOpenMRSTrack(e);
		}		
		if(trackId.equalsIgnoreCase("")){
			trackId = "TR";
		}		
		DateTime scheduleCloseDate = null;
		reportActionService.alertForReporting(beneficiaryType, caseID, instanceId, anmIdentifier, scheduleName, visitCode, alertStatus, startDate, expiryDate,scheduleCloseDate,trackId);
		
	}
	
	public  List<Enrollment> findEnrollmentByCaseIdAndScheduleName(String caseID,String scheduleName ){
		return  allEnrollments.findByEnrollmentByExternalIdAndScheduleName(caseID,scheduleName);
	}
	
	public String saveEnrollDataToOpenMRSTrack(Enrollment e){
		JSONObject t = new JSONObject();
		String trackuuid = null;
		try {
			//t.put("beneficiary", e.getExternalId());
			t.put("beneficiary", 123456789);
			t.put("beneficiaryRole", "elco");
			t.put("schedule", e.getScheduleName().replace(ELCOSchedulesConstantsImediate.IMD_ELCO_SCHEDULE_PSRF,ELCO_SCHEDULE_PSRF));
			String hr = StringUtils.leftPad(e.getPreferredAlertTime().getHour().toString(),2,"0");
			String mn = StringUtils.leftPad(e.getPreferredAlertTime().getMinute().toString(),2,"0");
			t.put("preferredAlertTime", hr+":"+mn+":00");
			t.put("referenceDate", OPENMRS_DATE.format(e.getStartOfSchedule().toDate()));
			t.put("referenceDateType", "MANUAL");
			t.put("dateEnrolled", OPENMRS_DATE.format(e.getEnrolledOn().toDate()));			
			t.put("currentMilestone", e.getCurrentMilestoneName().replace(ELCOSchedulesConstantsImediate.IMD_ELCO_SCHEDULE_PSRF,ELCO_SCHEDULE_PSRF));
			t.put("status", e.getStatus().name());
			System.out.println("OpenMRS sent data:"+t.toString());
			JSONObject to = new JSONObject(HttpUtil.post(getURL()+"/"+TRACK_URL, "", t.toString(), OPENMRS_USER, OPENMRS_PWD).body());
			trackuuid = to.getString("uuid");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return trackuuid;
		
	}
	
	
	private Action getClosedAction(String milestone, List<Action> actions){
		for (Action a : actions) {
			if(a.data().get("visitCode") != null && a.data().get("visitCode").equalsIgnoreCase(milestone)
					&& a.actionType().equalsIgnoreCase("closeAlert")){
				return a;
			}
		}
		return null;
	}
	private MilestoneFulfillment getMilestone(String milestone, Enrollment e) {
		for (MilestoneFulfillment m : e.getFulfillments()) {
			if(m.getMilestoneName().equalsIgnoreCase(milestone)){
				return m;
			}
		}
		return null;
	}
}
