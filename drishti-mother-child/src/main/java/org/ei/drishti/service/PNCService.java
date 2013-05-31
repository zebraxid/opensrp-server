package org.ei.drishti.service;

import org.ei.drishti.contract.ChildCloseRequest;
import org.ei.drishti.contract.ChildImmunizationUpdationRequest;
import org.ei.drishti.contract.PostNatalCareCloseInformation;
import org.ei.drishti.contract.PostNatalCareInformation;
import org.ei.drishti.domain.Child;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.dto.BeneficiaryType;
import org.ei.drishti.form.domain.FormSubmission;
import org.ei.drishti.repository.AllChildren;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.repository.AllMothers;
import org.ei.drishti.service.reporting.ChildReportingService;
import org.ei.drishti.service.reporting.MotherReportingService;
import org.ei.drishti.service.scheduling.ChildSchedulesService;
import org.ei.drishti.util.SafeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static java.text.MessageFormat.format;
import static org.ei.drishti.common.AllConstants.ANCFormFields.REFERENCE_DATE;
import static org.ei.drishti.common.AllConstants.ChildBirthCommCareFields.BF_POSTBIRTH_FIELD_NAME;
import static org.ei.drishti.common.AllConstants.DeliveryOutcomeFields.DID_BREAST_FEEDING_START;
import static org.ei.drishti.common.AllConstants.DeliveryOutcomeFields.DID_WOMAN_SURVIVE;
import static org.ei.drishti.common.AllConstants.Form.BOOLEAN_FALSE_VALUE;
import static org.ei.drishti.common.AllConstants.Form.ID;
import static org.ei.drishti.common.AllConstants.PNCCloseCommCareFields.DEATH_OF_MOTHER_COMMCARE_VALUE;
import static org.ei.drishti.common.AllConstants.PNCCloseCommCareFields.PERMANENT_RELOCATION_COMMCARE_VALUE;
import static org.ei.drishti.common.AllConstants.Report.REPORT_EXTRA_DATA_KEY_NAME;
import static org.ei.drishti.dto.BeneficiaryType.mother;

@Service
public class PNCService {
    private static Logger logger = LoggerFactory.getLogger(PNCService.class.toString());

    private ActionService actionService;
    private ChildSchedulesService childSchedulesService;
    private PNCSchedulesService pncSchedulesService;
    private AllEligibleCouples allEligibleCouples;
    private AllMothers allMothers;
    private AllChildren allChildren;
    private MotherReportingService motherReportingService;
    private ChildReportingService childReportingService;

    @Autowired
    public PNCService(ActionService actionService,
                      ChildSchedulesService childSchedulesService,
                      PNCSchedulesService pncSchedulesService,
                      AllEligibleCouples allEligibleCouples,
                      AllMothers allMothers,
                      AllChildren allChildren,
                      MotherReportingService motherReportingService,
                      ChildReportingService childReportingService) {
        this.actionService = actionService;
        this.childSchedulesService = childSchedulesService;
        this.pncSchedulesService = pncSchedulesService;
        this.allEligibleCouples = allEligibleCouples;
        this.allMothers = allMothers;
        this.allChildren = allChildren;
        this.motherReportingService = motherReportingService;
        this.childReportingService = childReportingService;
    }

    public void deliveryOutcome(FormSubmission submission) {
        if (!allMothers.exists(submission.entityId())) {
            logger.warn("Failed to handle delivery outcome as there is no mother registered with id: " + submission.entityId());
            return;
        }

        if (!BOOLEAN_FALSE_VALUE.equals(submission.getField(DID_WOMAN_SURVIVE))) {
            pncSchedulesService.deliveryOutcome(submission.entityId(), submission.getField(REFERENCE_DATE));
        }
    }

    public void registerChildren(FormSubmission submission) {
        Mother mother = allMothers.findByCaseId(submission.entityId());
        if (mother == null) {
            logger.warn("Failed to handle children registration as there is no mother registered with id: " + submission.entityId());
            return;
        }

        List<Child> children = allChildren.findByMotherId(submission.entityId());

        for (Child child : children) {
            child = child.withAnm(submission.anmId()).withDateOfBirth(submission.getField(REFERENCE_DATE)).withThayiCard(mother.thaayiCardNo());
            allChildren.update(child);

            SafeMap reportingData = new SafeMap();
            reportingData.put(ID, child.caseId());
            reportingData.put(BF_POSTBIRTH_FIELD_NAME, submission.getField(DID_BREAST_FEEDING_START));
            childReportingService.registerChild(reportingData);

            childSchedulesService.enrollChild(child);
        }
    }

    @Deprecated
    public void pncVisitHappened(PostNatalCareInformation info, Map<String, Map<String, String>> extraData) {
        if (!allMothers.exists(info.caseId())) {
            logger.warn("Found PNC visit without registered mother for case ID: " + info.caseId());
            return;
        }

        Map<String, String> details = extraData.get("details");

        Mother updatedMother = allMothers.updateDetails(info.caseId(), details);
        actionService.pncVisitHappened(mother, info.caseId(), info.anmIdentifier(), info.visitDate(), info.visitNumber(), info.numberOfIFATabletsProvided(), updatedMother.details());
        motherReportingService.pncVisitHappened(new SafeMap(extraData.get("reporting")));

        Child child = allChildren.findByMotherCaseId(info.caseId());
        if (child != null) {
            Child updatedChild = allChildren.update(child.caseId(), details);
            actionService.pncVisitHappened(BeneficiaryType.child, child.caseId(), info.anmIdentifier(), info.visitDate(), info.visitNumber(), info.numberOfIFATabletsProvided(), updatedChild.details());
        }
    }

    @Deprecated
    public void closePNCCase(PostNatalCareCloseInformation closeInformation, Map<String, Map<String, String>> extraData) {
        if (!allMothers.exists(closeInformation.caseId())) {
            logger.warn("Found PNC Close visit without registered mother for it: " + closeInformation.caseId());
            return;
        }

        logger.info("Closing PNC case. Details: " + closeInformation);
        allMothers.close(closeInformation.caseId());
        motherReportingService.closePNC(new SafeMap(extraData.get(REPORT_EXTRA_DATA_KEY_NAME)));
        actionService.markAllAlertsAsInactive(closeInformation.caseId());

        if (DEATH_OF_MOTHER_COMMCARE_VALUE.equalsIgnoreCase(closeInformation.closeReason())
                || PERMANENT_RELOCATION_COMMCARE_VALUE.equalsIgnoreCase(closeInformation.closeReason())) {
            logger.info("Closing EC case along with PNC case. Details: " + closeInformation);
            allEligibleCouples.close(closeInformation.caseId());
        }
    }

    public void autoClosePNCCase(String entityId) {
        Mother mother = allMothers.findByCaseId(entityId);
        if (mother == null) {
            logger.warn(format("Failed to auto close PNC as there is no mother registered with ID: {0}", entityId));
            return;
        }

        logger.info(format("Auto closing mother case with entity id {0} as the Post-pregnancy period has elapsed.", entityId));
        allMothers.close(entityId);
        actionService.markAllAlertsAsInactive(entityId);
    }

    @Deprecated
    public void updateChildImmunization(ChildImmunizationUpdationRequest updationRequest, Map<String, Map<String, String>> extraData) {
        if (!allChildren.childExists(updationRequest.caseId())) {
            logger.warn("Found immunization update without registered child for case ID: " + updationRequest.caseId());
            return;
        }

        List<String> previousImmunizations = allChildren.findByCaseId(updationRequest.caseId()).immunizationsProvided();

        Child updatedChild = allChildren.update(updationRequest.caseId(), extraData.get("details"));
        actionService.updateImmunizations(updationRequest.caseId(), updationRequest.anmIdentifier(), updatedChild.details(), updationRequest.immunizationsProvided(),
                updationRequest.immunizationsProvidedDate(), updationRequest.vitaminADose());

        childReportingService.immunizationProvided(new SafeMap(extraData.get(REPORT_EXTRA_DATA_KEY_NAME)), previousImmunizations);

        childSchedulesService.updateEnrollments(updationRequest);
        closeAlertsForProvidedImmunizations(updationRequest);
    }

    @Deprecated
    public void closeChildCase(ChildCloseRequest childCloseRequest, Map<String, Map<String, String>> extraData) {
        if (!allChildren.childExists(childCloseRequest.caseId())) {
            logger.warn("Found close child request without registered child for case ID: " + childCloseRequest.caseId());
            return;
        }

        allChildren.close(childCloseRequest.caseId());
        actionService.closeChild(childCloseRequest.caseId(), childCloseRequest.anmIdentifier());
        childReportingService.closeChild(new SafeMap(extraData.get(REPORT_EXTRA_DATA_KEY_NAME)));
        childSchedulesService.unenrollChild(childCloseRequest.caseId());
    }

    private void closeAlertsForProvidedImmunizations(ChildImmunizationUpdationRequest updationRequest) {
        for (String immunization : updationRequest.immunizationsProvidedList()) {
            actionService.markAlertAsClosed(updationRequest.caseId(), updationRequest.anmIdentifier(), immunization, updationRequest.immunizationsProvidedDate().toString());
        }
    }
}
