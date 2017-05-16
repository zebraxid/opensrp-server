package org.opensrp.register.encounter.sync.forms;

import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.encounter.sync.interfaces.FormsType;
import org.opensrp.register.mcare.domain.Members;

public class WomanTTForm implements FormsType<Members> {

	private WomanTTForm(){
		
	}
	@Override
	public FormSubmission makeForm(String formDir) {
		System.out.println("From Woman TT form");
		// TODO Auto-generated method stub
		return null;
		
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
	public static WomanTTForm getInstance(){
		return new WomanTTForm();
	}
}
