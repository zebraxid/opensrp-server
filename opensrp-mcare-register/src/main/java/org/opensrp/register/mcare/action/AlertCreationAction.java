package org.opensrp.register.mcare.action;

import static org.opensrp.dto.BeneficiaryType.ec;
import static org.opensrp.dto.BeneficiaryType.elco;
import static org.opensrp.dto.BeneficiaryType.hh;

import java.util.Map;

import org.opensrp.dto.BeneficiaryType;
import org.opensrp.register.mcare.repository.AllElcos;
import org.opensrp.register.mcare.repository.AllHouseHolds;
import org.opensrp.scheduler.HealthSchedulerService;
import org.opensrp.scheduler.HookedEvent;
import org.opensrp.scheduler.MilestoneEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component
@Qualifier("AlertCreationAction")
public class AlertCreationAction implements HookedEvent {
    private HealthSchedulerService scheduler;
    private AllHouseHolds allHouseHolds;
    private AllElcos allElcos;

    @Autowired
    public AlertCreationAction(HealthSchedulerService scheduler, AllHouseHolds allHouseHolds, AllElcos allElcos) {
        this.scheduler = scheduler;
        this.allHouseHolds = allHouseHolds;
        this.allElcos = allElcos;
    }

    @Override
    public void invoke(MilestoneEvent event, Map<String, String> extraData) {
        BeneficiaryType beneficiaryType = BeneficiaryType.from(extraData.get("beneficiaryType"));

     // TODO: Get rid of this horrible if-else after Motech-Platform fixes the bug related to metadata in motech-schedule-tracking.
        String providerId;
        String caseID = event.externalId();
		if (hh.equals(beneficiaryType)) {
			providerId = allHouseHolds.findByCASEID(caseID ).PROVIDERID();
        } else if (elco.equals(beneficiaryType)) {
        	providerId = allElcos.findByCaseId(caseID).PROVIDERID();
        } 
		/*else if (ec.equals(beneficiaryType)) {
            providerId = allHouseHolds.findByCaseId(caseID).PROVIDERID();
        }*/ 
        else {
            throw new IllegalArgumentException("Beneficiary Type : " + beneficiaryType + " is of unknown type");
        }
        
		scheduler.alertFor(event.windowName(), beneficiaryType, caseID, providerId, event.scheduleName(), event.milestoneName(), event.startOfDueWindow(), event.startOfLateWindow(), event.startOfMaxWindow());
    }
}
