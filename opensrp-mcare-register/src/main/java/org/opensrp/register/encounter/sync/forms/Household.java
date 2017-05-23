package org.opensrp.register.encounter.sync.forms;

import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.encounter.sync.interfaces.FormsType;

public class Household implements FormsType<Household> {

	@Override
	public FormSubmission makeForm(String formDir,String vaccineDate,int vaccineDose,String patientId) {
		return null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public void submit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Household get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
