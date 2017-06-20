package org.opensrp.register.encounter.sync.interfaces;

import java.io.IOException;

import org.opensrp.form.domain.FormSubmission;
import org.opensrp.form.repository.AllFormSubmissions;
import org.opensrp.register.encounter.sync.VaccineParamsBuilder;
import org.opensrp.register.encounter.sync.mapping.domain.EncounterSyncMapping;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.repository.AllMembers;


public interface FormsType<T> {
	public FormSubmission getFormSubmission(VaccineParamsBuilder params) throws IOException;
	public boolean isVaccineGiven(T t,int dose,String vaccineName);

}
