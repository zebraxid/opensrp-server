package org.opensrp.register.service;

import org.opensrp.common.AllConstants;
import org.opensrp.common.util.IntegerUtil;
import org.opensrp.register.domain.EligibleCouple;
import org.opensrp.register.domain.Mother;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.service.scheduling.KbSchedulesService;
import org.opensrp.util.SafeMap;
import org.opensrp.register.repository.AllEligibleCouples;
import org.opensrp.register.repository.AllMothers;
import org.opensrp.service.formSubmission.handler.ReportFieldsDefinition;
import org.opensrp.register.service.reporting.MotherReportingService;
import org.opensrp.register.service.reporting.rules.IsHypertensionDetectedRule;
import org.opensrp.register.service.scheduling.ANCSchedulesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.opensrp.common.AllConstants.FamilyPlanningFormFields.CURRENT_FP_METHOD_FIELD_NAME;
import static org.opensrp.common.AllConstants.KbFormFields.*;

@Service
public class KbService {

    private static Logger logger = LoggerFactory.getLogger(KbService.class.toString());

    private AllMothers allMothers;
    private AllEligibleCouples eligibleCouples;
    private KbSchedulesService kbSchedulesService;
    private MotherReportingService reportingService;
    private ReportFieldsDefinition reportFieldsDefinition;

    @Autowired
    public KbService(AllMothers allMothers,
                      AllEligibleCouples eligibleCouples,
                      KbSchedulesService kbSchedulesService,
                      MotherReportingService reportingService,
                      ReportFieldsDefinition reportFieldsDefinition) {
        this.allMothers = allMothers;
        this.eligibleCouples = eligibleCouples;
        this.kbSchedulesService = kbSchedulesService;
        this.reportingService = reportingService;
        this.reportFieldsDefinition = reportFieldsDefinition;
    }

    public void registerKB(FormSubmission submission) {
        EligibleCouple ki = eligibleCouples.findByCaseId(submission.entityId());
        if (ki == null) {
            logger.warn("Tried register KB of a non-existing KI, with submission: " + submission);
            return;
        }

        String kbMethod = submission.getField(JENIS_KONTRASEPSI);
        ki.details().put(CURRENT_FP_METHOD_FIELD_NAME, kbMethod);
        ki.details().put(KETERANGAN_PESERTA_KB, submission.getField(KETERANGAN_PESERTA_KB));

        eligibleCouples.update(ki);
        kbSchedulesService.kbHasHappen(submission.entityId(),
                submission.anmId(),
                kbMethod,
                submission.getField(TANGGAL_KUNJUNGAN));
    }

    public void KbFollowUp(FormSubmission submission) {
        String motherId = submission.getField(AllConstants.ANCFormFields.MOTHER_ID);

        Mother mother = allMothers.findByCaseId(motherId);

        if (mother == null) {
            logger.warn("Tried to handle KB provided without registered mother. Submission: " + submission);
            return;
        }
        String jenisKontrasepsiYangDigunakan = submission.getField("jenisKontrasepsi");
        kbSchedulesService.kbFollowUpHasHappen(submission.entityId(),
                submission.anmId(),
                jenisKontrasepsiYangDigunakan,
                submission.getField("tanggalkunjungan"));

    }
}

