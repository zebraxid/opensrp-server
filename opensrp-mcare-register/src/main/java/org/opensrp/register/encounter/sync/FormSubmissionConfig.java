/**
 * @author proshanto
 * */
package org.opensrp.register.encounter.sync;

import org.springframework.beans.factory.annotation.Value;

public abstract class FormSubmissionConfig {
	@Value("#{opensrp['form.directory']}")
	protected String formDirectory;

	public FormSubmissionConfig(){
		
	}
	public FormSubmissionConfig(@Value("#{opensrp['form.directory']}") String formDirectory){
		this.formDirectory = formDirectory;
	}
}
