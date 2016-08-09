package org.opensrp.register.service.handler;

import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.service.VaksinatorService;
import org.opensrp.service.formSubmission.handler.FormSubmissionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VaksinatorHandler implements FormSubmissionHandler {
    private VaksinatorService vaksinatorService;

    @Autowired
    public VaksinatorHandler(VaksinatorService vaksinatorservice) {
        this.vaksinatorService = vaksinatorservice;
    }

    @Override
    public void handle(FormSubmission submission) {
        vaksinatorService.registerVaksinator(submission);
    }
}
