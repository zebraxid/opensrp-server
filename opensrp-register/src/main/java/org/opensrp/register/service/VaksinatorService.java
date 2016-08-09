package org.opensrp.register.service;

import org.opensrp.common.AllConstants;

import org.opensrp.register.domain.Child;

import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.service.scheduling.VaksinatorSchedulesService;

import org.opensrp.register.repository.AllEligibleCouples;
import org.opensrp.register.repository.AllChildren;
import org.opensrp.service.formSubmission.handler.ReportFieldsDefinition;
import org.opensrp.register.service.reporting.MotherReportingService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class VaksinatorService {

    private static Logger logger = LoggerFactory.getLogger(KbService.class.toString());

    private AllChildren allChildren;
    private AllEligibleCouples eligibleCouples;
    private VaksinatorSchedulesService vaksinatorSchedulesService;
    private MotherReportingService reportingService;
    private ReportFieldsDefinition reportFieldsDefinition;

    @Autowired
    public VaksinatorService(AllChildren allChildren,
                     AllEligibleCouples eligibleCouples,
                     VaksinatorSchedulesService vaksinatorSchedulesService,
                     MotherReportingService reportingService,
                     ReportFieldsDefinition reportFieldsDefinition) {
        this.allChildren = allChildren;
        this.eligibleCouples = eligibleCouples;
        this.vaksinatorSchedulesService = vaksinatorSchedulesService;
        this.reportingService = reportingService;
        this.reportFieldsDefinition = reportFieldsDefinition;
    }

    public void registerVaksinator(FormSubmission submission) {
        String childId = submission.getField(AllConstants.ANCFormFields.MOTHER_ID);

//        if (!eligibleCouples.exists(submission.entityId())) {
//            logger.warn(format("Found mother without registered eligible couple. Ignoring: {0} for mother with id: {1} for ANM: {2}",
//                    submission.entityId(), motherId, submission.anmId()));
//            return;
//        }

        Child child = allChildren.findByCaseId(childId);
        allChildren.update(child.withAnm(submission.anmId()));

//        KbInjection(submission);

//        List<String> reportFields = reportFieldsDefinition.get(submission.formName());
//        reportingService.registerANC(new SafeMap(submission.getFields(reportFields)));
        vaksinatorSchedulesService.registered(submission.entityId(),submission.anmId(),submission.getField("tanggal_lahir"));
    }

    public void immunizationGiven(FormSubmission submission, String form) {
        String childID = submission.getField(AllConstants.ANCFormFields.MOTHER_ID);

        Child child = allChildren.findByCaseId(childID);

        //    ancSchedulesService.enrollMother(motherId, parse(submission.getField(REFERENCE_DATE)));

//        List<String> reportFields = reportFieldsDefinition.get(submission.formName());
        if (child == null) {
            logger.warn("Tried to handle Vaccination provided without registered child. Submission: " + submission);
            return;
        }
        vaksinatorSchedulesService.hasGiven(form, submission.entityId(),submission.anmId());
    }
}
