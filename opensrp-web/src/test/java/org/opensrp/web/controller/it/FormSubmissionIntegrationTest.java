package org.opensrp.web.controller.it;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opensrp.form.repository.AllFormSubmissions;
import org.opensrp.web.rest.it.BaseResourceTest;
import org.springframework.beans.factory.annotation.Autowired;

public class FormSubmissionIntegrationTest extends BaseResourceTest{

	@Autowired
	private AllFormSubmissions allFormSubmissions;

	@Before
	public void setUp() {
		allFormSubmissions.removeAll();
	}

	@After
	public void cleanUp() {
		//allFormSubmissions.removeAll();
	}

	@Test
	public void shouldFetchFormBasedOnAnmId() {

	}
}
