package org.opensrp.register.encounter.sync.forms;

import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.encounter.sync.interfaces.FormsType;
import org.opensrp.register.mcare.domain.Members;

public class ChildVaccineFollowup implements FormsType<Members> {

	private ChildVaccineFollowup(){
		
	}
	@Override
	public FormSubmission makeForm(String formDir,String vaccineDate,int vaccineDose,String patientId,Members member) {
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

	
	public static ChildVaccineFollowup getInstance(){
		return new ChildVaccineFollowup();
	}
	@Override
	public boolean isThisVaccineGiven(Members member,int dose) {
		// TODO Auto-generated method stub
		return false;
	}

}
