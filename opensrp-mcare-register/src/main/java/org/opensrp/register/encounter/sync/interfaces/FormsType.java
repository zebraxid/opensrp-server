package org.opensrp.register.encounter.sync.interfaces;

import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.mcare.domain.Members;


public interface FormsType<T> {
	public FormSubmission makeForm(String formDir,String vaccineDate,int vaccineDose,String patientId,Members member);
	public boolean isThisVaccineGiven(T t,int dose);

}
