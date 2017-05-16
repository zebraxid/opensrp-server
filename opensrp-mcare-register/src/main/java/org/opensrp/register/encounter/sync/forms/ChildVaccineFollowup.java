package org.opensrp.register.encounter.sync.forms;

import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.encounter.sync.interfaces.FormsType;
import org.opensrp.register.mcare.domain.Members;

public class ChildVaccineFollowup implements FormsType<Members> {

	private ChildVaccineFollowup(){
		
	}
	@Override
	public FormSubmission makeForm(String formDir) {
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
	public Members get(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	public static ChildVaccineFollowup getInstance(){
		return new ChildVaccineFollowup();
	}

}
