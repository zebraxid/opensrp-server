package org.opensrp.connector.openmrs.service.it;

import java.io.IOException;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.scheduler.domain.MotechEvent;
import org.opensrp.connector.openmrs.schedule.OpenmrsSyncerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-applicationContext-opensrp-connector.xml")
public class OpenmrsSyncerListenerTest extends OpenmrsApiService {
	
	public OpenmrsSyncerListenerTest() throws IOException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	private OpenmrsSyncerListener openmrsSyncerListener;
	
	//@Autowired
	MotechEvent event = new MotechEvent("subject");
	
	@Before
	public void setup() {
		
	}
	
	@Test
	public void testPushToOpenMRS() throws JSONException {
		System.err.println("event" + event);
		//openmrsSyncerListener.pushToOpenMRS(event);
	}
}
