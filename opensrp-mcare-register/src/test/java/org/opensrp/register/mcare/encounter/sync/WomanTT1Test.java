package org.opensrp.register.mcare.encounter.sync;

import static org.junit.Assert.assertNotNull;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.opensrp.register.encounter.sync.*;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.form.repository.AllFormSubmissions;

public class WomanTT1Test extends TestConfig {	
	@Mock
	FormSubmission formSubmission;
	@Mock
    private AllFormSubmissions formSubmissions;
	
	@Before
	public void setUp() throws Exception
	{
		formSubmissions = new AllFormSubmissions(getStdCouchDbConnector());
	}
	@Test
	public void shuoldCreateWonamTT1(){
		String uuid = UUID.randomUUID().toString();
		System.out.println("uuid:"+uuid);
		MakeFormSubmission makeFormSubmission = new MakeFormSubmission(formSubmissions);
		formSubmission =makeFormSubmission.createFormSubmission();
		formSubmissions.remove(formSubmission);
		assertNotNull(formSubmission);
		
	}
	

}
