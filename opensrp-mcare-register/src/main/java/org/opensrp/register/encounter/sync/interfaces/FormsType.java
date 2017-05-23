package org.opensrp.register.encounter.sync.interfaces;

import org.opensrp.form.domain.FormSubmission;


public interface FormsType<T> {
	public FormSubmission makeForm(String formDir,String vaccineDate,int vaccineDose,String patientId);
	public void submit();
	public void delete();
	public T get(String id);

}
