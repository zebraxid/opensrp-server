/**
 * @author julkar nain
 */

package org.opensrp.register.mcare.service;

import static java.text.MessageFormat.format;
import static org.opensrp.common.AllConstants.BnfFollowUpVisitFields.FWBNFDTOO;
import static org.opensrp.common.AllConstants.BnfFollowUpVisitFields.FWBNFSTS;
import static org.opensrp.common.AllConstants.BnfFollowUpVisitFields.STS_WD;
import static org.opensrp.common.AllConstants.BnfFollowUpVisitFields.STS_LB;
import static org.opensrp.common.AllConstants.BnfFollowUpVisitFields.STS_SB;

import org.joda.time.LocalDate;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.mcare.domain.Elco;
import org.opensrp.register.mcare.domain.Mother;
import org.opensrp.register.mcare.repository.AllElcos;
import org.opensrp.register.mcare.repository.AllMothers;
import org.opensrp.register.mcare.service.scheduling.ChildSchedulesService;
import org.opensrp.register.mcare.service.scheduling.PNCSchedulesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PNCService {

	private static Logger logger = LoggerFactory.getLogger(ANCService.class
			.toString());
	private AllElcos allElcos;
	private AllMothers allMothers;
	private PNCSchedulesService pncSchedulesService;
	private ChildSchedulesService childSchedulesService;
	
	public PNCService(AllElcos allElcos, AllMothers allMothers, PNCSchedulesService pncSchedulesService, ChildSchedulesService childSchedulesService)
	{
		this.allElcos = allElcos;
		this.allMothers = allMothers;
		this.pncSchedulesService = pncSchedulesService; 
		this.childSchedulesService = childSchedulesService;
	}
	
	public void deliveryOutcome(FormSubmission submission)
	{
		Mother mother = allMothers.findByCaseId(submission.entityId());

		if (mother == null) {
			logger.warn(format(
					"Failed to handle PNC as there is no Mother enroll with ID: {0}",
					submission.entityId()));
			return;
		}

		if(submission.getField(FWBNFSTS).equals(STS_WD))
		{
			logger.info("Closing Mother as the mother died during delivery. Mother Id: " + mother.caseId());
			closeMother(mother);
		}
		else if(submission.getField(FWBNFSTS).equals(STS_LB))
		{
			logger.info("Generating schedule for Mother when Child is Live Birth. Mother Id: " + mother.caseId());
			pncSchedulesService.enrollPNCRVForMother(submission.entityId(), LocalDate.parse(submission.getField(FWBNFDTOO)));
			logger.info("Generating schedule for Child when Child is Live Birth. Mother Id: " + mother.caseId());
			childSchedulesService.enrollENCCForChild(submission.entityId(), LocalDate.parse(submission.getField(FWBNFDTOO)));
		}
		if(submission.getField(FWBNFSTS).equals(STS_SB))
		{
			logger.info("Generating schedule for Mother when Child is Still Birth. Mother Id: " + mother.caseId());
			pncSchedulesService.enrollPNCRVForMother(submission.entityId(), LocalDate.parse(submission.getField(FWBNFDTOO)));
		}
	}
	public void pncVisitOne(FormSubmission submission) {
		
		Mother mother = allMothers.findByCaseId(submission.entityId());

		if (mother == null) {
			logger.warn(format(
					"Failed to handle PNC-1 as there is no Mother enroll with ID: {0}",
					submission.entityId()));
			return;
		}
		allMothers.update(mother);
	}

	public void pncVisitTwo(FormSubmission submission) {
		Mother mother = allMothers.findByCaseId(submission.entityId());

		if (mother == null) {
			logger.warn(format(
					"Failed to handle PNC-2 as there is no Mother enroll with ID: {0}",
					submission.entityId()));
			return;
		}
		
		allMothers.update(mother);

	}

	public void pncVisitThree(FormSubmission submission) {
		
		Mother mother = allMothers.findByCaseId(submission.entityId());

		if (mother == null) {
			logger.warn(format(
					"Failed to handle PNC-3 as there is no Mother enroll with ID: {0}",
					submission.entityId()));
			return;
		}

		allMothers.update(mother);
	}
	private void closeMother(Mother mother) {
		
		mother.setIsClosed(true);
		pncSchedulesService.unEnrollFromSchedules(mother.caseId());
		
		Elco elco = allElcos.findByCaseId(mother.relationalid());
        logger.info("Closing EC case along with PNC case. Ec Id: " + elco.CASEID());
        elco.setIsClosed(true);
        allElcos.update(elco);
	}
}
