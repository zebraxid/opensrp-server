package org.opensrp.register.service.handler;

import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.service.KIService;
import org.opensrp.service.formSubmission.handler.FormSubmissionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Iq on 01/10/15.
 */
@Component
public class KartuIbuHandler implements FormSubmissionHandler {
    private KIService kiService;

    @Autowired
    public KartuIbuHandler(KIService kiService) {
        this.kiService = kiService;
    }

    @Override
    public void handle(FormSubmission submission) {
        kiService.registerKartuIbu(submission);
    }
}
