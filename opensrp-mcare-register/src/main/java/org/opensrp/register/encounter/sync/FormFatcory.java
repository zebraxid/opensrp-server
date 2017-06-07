package org.opensrp.register.encounter.sync;

import org.opensrp.register.encounter.sync.forms.ChildVaccineFollowup;
import org.opensrp.register.encounter.sync.forms.WomanTTFollowUp;
import org.opensrp.register.encounter.sync.interfaces.FormsType;

public class FormFatcory {
	
	private FormFatcory(){
		
	}
	/**
	 * <code>Generate new instance</code>
	 * @param form a vaccine form type.
	 * @return new Instance according to form type. 
	 */
	@SuppressWarnings("rawtypes")
	public static FormsType getFormsTypeInstance(String form){
		if(form.equalsIgnoreCase("CVF")){
			return  ChildVaccineFollowup.getInstance();
		}else if(form.equalsIgnoreCase("WTT")){
			return  WomanTTFollowUp.getInstance();
		}else{
			return null;
		}
		
		
	}

}
