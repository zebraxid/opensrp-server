/**
 * @author julkar nain 
 */
package org.opensrp.register.mcare.service;

import static java.text.MessageFormat.format;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.FWCONFIRMATION;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.FWEDD;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.FWGESTATIONALAGE;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.external_user_ID;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.relationalid;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.user_type;
import static org.opensrp.common.AllConstants.BnfFollowUpVisitFields.*;
import static org.opensrp.common.AllConstants.HHRegistrationFields.REFERENCE_DATE;
import static org.opensrp.common.AllConstants.HHRegistrationFields.MOTHER_REFERENCE_DATE;
import static org.opensrp.common.AllConstants.HHRegistrationFields.received_time;
import static org.opensrp.common.util.EasyMap.create;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_BNF;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.joda.time.LocalDate;
import org.opensrp.common.AllConstants;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.mcare.domain.Mother;
import org.opensrp.register.mcare.repository.AllElcos;
import org.opensrp.register.mcare.repository.AllMothers;
import org.opensrp.register.mcare.service.scheduling.BNFSchedulesService;
import org.opensrp.register.mcare.service.scheduling.ScheduleLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BNFService {
	
	private static Logger logger = LoggerFactory.getLogger(BNFService.class
			.toString());
	
	private AllElcos allElcos;
	private AllMothers allMothers;
	private BNFSchedulesService bnfSchedulesService;
	private PNCService pncService;
	private ScheduleLogService scheduleLogService;
	
	@Autowired
	public BNFService(AllElcos allElcos, AllMothers allMothers, BNFSchedulesService bnfSchedulesService, PNCService pncService,ScheduleLogService scheduleLogService)
	{
		this.allElcos = allElcos;
		this.allMothers = allMothers;
		this.bnfSchedulesService = bnfSchedulesService;
		this.pncService = pncService;
		this.scheduleLogService = scheduleLogService;
	}
	
	public void registerBNF(FormSubmission submission)
	{
		String motherId = submission
				.getField(AllConstants.ANCFormFields.MCARE_MOTHER_ID);
		
		Mother mother = allMothers.findByCaseId(motherId);
		
		if (!allElcos.exists(submission.entityId())) {
			logger.warn(format(
					"Found mother without registered eligible couple. Ignoring: {0} for mother with id: {1} for ANM: {2}",
					submission.entityId(), motherId, submission.anmId()));
			return;
		}

		mother.withPROVIDERID(submission.anmId());
		mother.withINSTANCEID(submission.instanceId());
		//mother.withTODAY(submission.getField(REFERENCE_DATE));
		allMothers.update(mother);		
		bnfSchedulesService.enrollBNF(motherId, LocalDate.parse(submission.getField(MOTHER_REFERENCE_DATE)),submission.anmId(),submission.instanceId(),submission.getField(MOTHER_REFERENCE_DATE));
		
	}
	
	public void bnfFollowUpVisit(FormSubmission submission) {
		
		Mother mother = allMothers.findByCaseId(submission.entityId());

		if (mother == null) {
			logger.warn(format(
					"Failed to handle BNF as there is no Mother enroll with ID: {0}",
					submission.entityId()));
			return;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();
		Map<String, String> bnfVisit = create(FWBNFDATE, submission.getField(FWBNFDATE))
											.put(bnf_current_formStatus, submission.getField(bnf_current_formStatus))
											.put(FWCONFIRMATION, submission.getField(FWCONFIRMATION))
											.put(FWGESTATIONALAGE, submission.getField(FWGESTATIONALAGE))
											.put(FWEDD, submission.getField(FWEDD))
											.put(FWBNFSTS, submission.getField(FWBNFSTS))
											.put(FWDISPLAYTEXT1, submission.getField(FWDISPLAYTEXT1))
											.put(FWBNFWOMVITSTS, submission.getField(FWBNFWOMVITSTS))
											.put(FWBNFDTOO, submission.getField(FWBNFDTOO))
											.put(FWBNFLB, submission.getField(FWBNFLB))
											.put(FWBNFGEN, submission.getField(FWBNFGEN))
											.put(FWBNFCHLDVITSTS, submission.getField(FWBNFCHLDVITSTS))
											.put(FWBNFSMSRSN, submission.getField(FWBNFSMSRSN))
											.put(user_type, submission.getField(user_type))
											.put(external_user_ID, submission.getField(external_user_ID))
											.put(received_time, format.format(today).toString())
											.put(relationalid, submission.getField(relationalid)).map();
		
		//mother.withTODAY(submission.getField(REFERENCE_DATE));
			
		mother.bnfVisitDetails().add(bnfVisit);
		
		allMothers.update(mother);
		logger.info("submission.getField(FWBNFSTS):"+submission.getField(FWBNFSTS));
		if(submission.getField(FWBNFSTS).equalsIgnoreCase(STS_LB) || submission.getField(FWBNFSTS).equalsIgnoreCase(STS_SB))
		{ 
			pncService.deliveryOutcome(submission); 
			bnfSchedulesService.unEnrollBNFSchedule(submission.entityId(), submission.anmId());
			scheduleLogService.closeScheduleAndScheduleLog( submission.entityId(),submission.instanceId(), SCHEDULE_BNF,submission.anmId());
			
		}else if(submission.getField(FWBNFSTS).equalsIgnoreCase(STS_GONE) || submission.getField(FWBNFSTS).equalsIgnoreCase(STS_WD) ){
			pncService.deleteBlankChild(submission);
			bnfSchedulesService.unEnrollBNFSchedule(submission.entityId(), submission.anmId());
			pncService.closeMother(mother);
			scheduleLogService.closeScheduleAndScheduleLog( submission.entityId(),submission.instanceId(), SCHEDULE_BNF,submission.anmId());
		}else{
			pncService.deleteBlankChild(submission);
			logger.info("Else Condition From BNF");
			bnfSchedulesService.enrollIntoMilestoneOfBNF(submission.entityId(),
		            submission.getField(REFERENCE_DATE),submission.anmId(),submission.instanceId());
		}
		
	}

}
