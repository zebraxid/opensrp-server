package org.opensrp.register.service;

import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.domain.EligibleCouple;
import org.opensrp.register.repository.AllEligibleCouples;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by user on 10/8/15.
 */
@Service
public class KIService {

    private AllEligibleCouples allEligibleCouples;

    @Autowired
    public KIService(AllEligibleCouples allEligibleCouples) {
        this.allEligibleCouples = allEligibleCouples;
    }

    public void registerKartuIbu(FormSubmission submission) {
        EligibleCouple eligibleCouple = allEligibleCouples.findByCaseId(submission.entityId());
        allEligibleCouples.update(eligibleCouple.withANMIdentifier(submission.anmId()));
    }
}