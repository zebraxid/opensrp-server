package org.opensrp.register.encounter.sync;

import org.opensrp.register.encounter.sync.forms.ChildVaccineFollowup;
import org.opensrp.register.encounter.sync.forms.WomanTTForm;
import org.opensrp.register.encounter.sync.interfaces.FormsType;

public class FormFatcory {
	
	private FormFatcory(){
		
	}
	@SuppressWarnings("rawtypes")
	public static FormsType getFormsTypeInstance(String form){
		if(form.equalsIgnoreCase("CVF")){
			return  ChildVaccineFollowup.getInstance();
		}else if(form.equalsIgnoreCase("WTT")){
			return  WomanTTForm.getInstance();
		}else{
			return null;
		}
		
		
	}

}
