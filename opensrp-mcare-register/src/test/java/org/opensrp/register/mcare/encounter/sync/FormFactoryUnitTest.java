package org.opensrp.register.mcare.encounter.sync;

import static org.mockito.MockitoAnnotations.initMocks;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.opensrp.register.encounter.sync.FormFatcory;
import org.opensrp.register.encounter.sync.forms.ChildVaccineFollowup;
import org.opensrp.register.encounter.sync.forms.WomanTTFollowUp;
import org.opensrp.register.encounter.sync.interfaces.FormsType;
public class FormFactoryUnitTest {	
	
	@Before
	public void setUp() throws Exception
	{
		initMocks(this);
		
		
	}
	
	@Test
	public void shuoldGetWomanTTFormInstance(){
		
		@SuppressWarnings("unchecked")
		FormsType<WomanTTFollowUp> TTFormObj= FormFatcory.getFormsTypeInstance("WTT");		
		Assert.assertNotNull(TTFormObj);
	}
	
	@Test
	public void shuoldGetChildVaccineFormInstance(){
		
		@SuppressWarnings("unchecked")
		FormsType<ChildVaccineFollowup> childVaccineFormObj= FormFatcory.getFormsTypeInstance("CVF");
		Assert.assertNotNull(childVaccineFormObj);
	}
	@Test
	public void shuoldGetNullInstance(){
		
		@SuppressWarnings("unchecked")
		FormsType<ChildVaccineFollowup> childVaccineFormObj= FormFatcory.getFormsTypeInstance("NULL");
		Assert.assertEquals(null,childVaccineFormObj);
	}
	
}
