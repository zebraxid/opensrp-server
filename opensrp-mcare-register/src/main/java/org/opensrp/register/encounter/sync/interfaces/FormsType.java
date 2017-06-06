package org.opensrp.register.encounter.sync.interfaces;

import java.io.IOException;

import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.mcare.domain.Members;


public interface FormsType<T> {
	public FormSubmission getFormSubmission(String formDir,String vaccineDate,int vaccineDose,String memberEntityId,Members member,String vaccineName) throws IOException;
	public boolean checkingVaccineGivenOrNot(T t,int dose,String vaccineName);

}
