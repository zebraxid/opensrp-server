/**
 * @author julkar nain 
 */
package org.opensrp.register.mcare.service;

import static java.text.MessageFormat.format;
import static org.opensrp.common.AllConstants.HHRegistrationFields.ELCO_REGISTRATION_SUB_FORM_NAME;
import static org.opensrp.common.AllConstants.HHRegistrationFields.MOTHER_REFERENCE_DATE;
import static org.opensrp.common.AllConstants.PSRFFields.FW_PSRDATE;
import static org.opensrp.common.util.EasyMap.create;

import java.util.Map;

import org.joda.time.LocalDate;
import org.opensrp.common.AllConstants;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.form.domain.SubFormData;
import org.opensrp.register.mcare.domain.Mother;
import org.opensrp.register.mcare.repository.AllElcos;
import org.opensrp.register.mcare.repository.AllMothers;
import org.opensrp.register.mcare.service.scheduling.ANCSchedulesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ANCService {

	private static Logger logger = LoggerFactory.getLogger(ANCService.class
			.toString());
	private AllElcos allElcos;
	private AllMothers allMothers;
	private ANCSchedulesService ancSchedulesService;

	@Autowired
	public ANCService(AllElcos allElcos, AllMothers allMothers,
			ANCSchedulesService ancSchedulesService) {
		this.allElcos = allElcos;
		this.allMothers = allMothers;
		this.ancSchedulesService = ancSchedulesService;
	}

	public void registerANC(FormSubmission submission) {

		String motherId = submission
				.getField(AllConstants.ANCFormFields.MCARE_MOTHER_ID);

		Mother mother = allMothers.findByCaseId(submission.entityId());

		if (!allElcos.exists(submission.entityId())) {
			logger.warn(format(
					"Found mother without registered eligible couple. Ignoring: {0} for mother with id: {1} for ANM: {2}",
					submission.entityId(), motherId, submission.anmId()));
			return;
		}

		SubFormData subFormData = null;

		subFormData = submission
				.getSubFormByName(ELCO_REGISTRATION_SUB_FORM_NAME);

		mother.withPROVIDERID(submission.anmId());
		mother.withINSTANCEID(submission.instanceId());
		mother.withTODAY(submission.getField(MOTHER_REFERENCE_DATE));
		allMothers.update(mother);

		ancSchedulesService.enrollMother(submission.entityId(),
				LocalDate.parse(submission.getField(MOTHER_REFERENCE_DATE)));
	}

	public void ancVisitOne(FormSubmission submission) {
		Mother mother = allMothers.findByCaseId(submission.entityId());

		if (mother == null) {
			logger.warn(format(
					"Failed to handle ANC-1 as there is no Mother enroll with ID: {0}",
					submission.entityId()));
			return;
		}
		Map<String, String> ancVisitOne = create(FW_PSRDATE, submission.getField(FW_PSRDATE))
											.put("", "")
											.map();
		mother.withANCVisitOne(ancVisitOne);
		allMothers.update(mother);
	}

	public void ancVisitTwo(FormSubmission submission) {
		Mother mother = allMothers.findByCaseId(submission.entityId());

		if (mother == null) {
			logger.warn(format(
					"Failed to handle ANC-2 as there is no Mother enroll with ID: {0}",
					submission.entityId()));
			return;
		}
		Map<String, String> ancVisitTwo = create(FW_PSRDATE, submission.getField(FW_PSRDATE)).map();
		mother.withANCVisitTwo(ancVisitTwo);
		allMothers.update(mother);

	}

	public void ancVisitThree(FormSubmission submission) {
		Mother mother = allMothers.findByCaseId(submission.entityId());

		if (mother == null) {
			logger.warn(format(
					"Failed to handle ANC-3 as there is no Mother enroll with ID: {0}",
					submission.entityId()));
			return;
		}
		Map<String, String> ancVisitThree = create(FW_PSRDATE, submission.getField(FW_PSRDATE)).map();
		mother.withANCVisitThree(ancVisitThree);

		allMothers.update(mother);
	}

	public void ancVisitFour(FormSubmission submission) {
		Mother mother = allMothers.findByCaseId(submission.entityId());

		if (mother == null) {
			logger.warn(format(
					"Failed to handle ANC-4 as there is no Mother enroll with ID: {0}",
					submission.entityId()));
			return;
		}
		
		Map<String, String> ancVisitFour = create(FW_PSRDATE, submission.getField(FW_PSRDATE)).map();
		mother.withANCVisitFour(ancVisitFour);
		allMothers.update(mother);
	}

	public void bnfFollowUpVisit(FormSubmission submission) {
		Mother mother = allMothers.findByCaseId(submission.entityId());

		if (mother == null) {
			logger.warn(format(
					"Failed to handle BNF as there is no Mother enroll with ID: {0}",
					submission.entityId()));
			return;
		}
		
		Map<String, String> bnfVisit = create(FW_PSRDATE, submission.getField(FW_PSRDATE)).map();
		mother.withBNFVisit(bnfVisit);
		
		allMothers.update(mother);
	}

	public void ancClose(String entityId) {
		
		Mother mother = allMothers.findByCaseId(entityId);
		
		 if (mother == null) {
	            logger.warn("Tried to close case without registered mother for case ID: " + entityId);
	            return;
	        }

		 allMothers.close(entityId);
		
		 ancSchedulesService.unEnrollFromAllSchedules(entityId);
	}

}
