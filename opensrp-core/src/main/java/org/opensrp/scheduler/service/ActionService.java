package org.opensrp.scheduler.service;

import static org.opensrp.dto.BeneficiaryType.child;
import static org.opensrp.dto.BeneficiaryType.ec;
import static org.opensrp.dto.BeneficiaryType.mother;
import static org.opensrp.dto.BeneficiaryType.household;
import static org.opensrp.dto.BeneficiaryType.elco;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.opensrp.dto.ActionData;
import org.opensrp.dto.AlertStatus;
import org.opensrp.dto.BeneficiaryType;
import org.opensrp.scheduler.Action;
import org.opensrp.scheduler.repository.AllActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActionService {
    private AllActions allActions;
    private ReportActionService reportActionService;

    @Autowired
    public ActionService(AllActions allActions, ReportActionService reportActionService) {
        this.allActions = allActions;
        this.reportActionService = reportActionService;
    }

    public List<Action> getNewAlertsForANM(String anmIdentifier, long timeStamp) {
        return allActions.findByANMIDAndTimeStamp(anmIdentifier, timeStamp);
    }

    public List<Action> findByCaseIdScheduleAndTimeStamp(String caseId, String schedule, DateTime start, DateTime end) {
		return allActions.findByCaseIdScheduleAndTimeStamp(caseId, schedule, start, end);
	}
    public void alertForBeneficiary(BeneficiaryType beneficiaryType, String caseID, String instanceId,  String anmIdentifier, String scheduleName, String visitCode, AlertStatus alertStatus, DateTime startDate, DateTime expiryDate) {
    	if (!(mother.equals(beneficiaryType)||child.equals(beneficiaryType)||ec.equals(beneficiaryType)||household.equals(beneficiaryType) || elco.equals(beneficiaryType))) {
            throw new IllegalArgumentException("Beneficiary Type : " + beneficiaryType + " is of unknown type");
        }
    	List<Action> existingAlerts = allActions.findAlertByANMIdEntityIdScheduleName(anmIdentifier, caseID, scheduleName);
    	
    	
    	if(existingAlerts.size() > 0){ 
    		long beforTimeStamp = existingAlerts.get(0).timestamp();
        	Map<String,String> data =existingAlerts.get(0).data(); 	      
 	      if(!data.get("alertStatus").equals(alertStatus)){
 	    	  existingAlerts.get(0).setRevision(existingAlerts.get(0).getRevision());
 	    	  existingAlerts.get(0).data().put("alertStatus", alertStatus.toString());
 	    	  existingAlerts.get(0).data().put("expiryDate", expiryDate.toLocalDate().toString());
 	    	  existingAlerts.get(0).data().put("startDate", startDate.toLocalDate().toString());
 	    	  existingAlerts.get(0).data().put("visitCode", visitCode);
 	    	  existingAlerts.get(0).timestamp(Calendar.getInstance().getTimeInMillis());
 	    	  Action action = existingAlerts.get(0);
 	    	 allActions.update(action);
 	    	  
 	      }else{
 	    	 existingAlerts.get(0).setRevision(existingAlerts.get(0).getRevision());
 	    	 existingAlerts.get(0).timestamp(Calendar.getInstance().getTimeInMillis()); 	    	 
 	    	 existingAlerts.get(0).data().put("visitCode", visitCode);
 	    	 existingAlerts.get(0).data().put("expiryDate", expiryDate.toLocalDate().toString());
 	    	 existingAlerts.get(0).data().put("startDate", startDate.toLocalDate().toString());
 	    	 Action action = existingAlerts.get(0);
 	    	 
 	    	 allActions.update(action);
 	      }
 	      
 	     List<Action> existingAlert = allActions.findAlertByANMIdEntityIdScheduleName(anmIdentifier, caseID, scheduleName);
 	     reportActionService.updateScheduleLog(beneficiaryType, caseID, instanceId, anmIdentifier, scheduleName, visitCode, alertStatus, startDate, expiryDate, null,null,beforTimeStamp,existingAlert.get(0).timestamp());
 	       
        }else{
        	allActions.addOrUpdateAlert(new Action(caseID, anmIdentifier, ActionData.createAlert(beneficiaryType, scheduleName, visitCode, alertStatus, startDate, expiryDate)));
        }
    	
    }

    public void markAllAlertsAsInactive(String entityId) {
        allActions.markAllAsInActiveFor(entityId);
    }

    public void markAlertAsInactive(String anmId, String entityId, String scheduleName) {
        allActions.markAlertAsInactiveFor(anmId, entityId, scheduleName);
    }

    public void markAlertAsClosed(String caseId, String anmIdentifier, String visitCode, String completionDate) {
        allActions.add(new Action(caseId, anmIdentifier, ActionData.markAlertAsClosed(visitCode, completionDate)));
    }
    
    public void closeBeneficiary(BeneficiaryType beneficiary, String caseId, String anmIdentifier, String reasonForClose) {
        allActions.add(new Action(caseId, anmIdentifier, ActionData.closeBeneficiary(beneficiary.name(), reasonForClose)));
    }

    public void reportForIndicator(String anmIdentifier, ActionData actionData) {
        allActions.add(new Action("", anmIdentifier, actionData));
    }

    public void deleteReportActions() {
        allActions.deleteAllByTarget("report");
    }
}
