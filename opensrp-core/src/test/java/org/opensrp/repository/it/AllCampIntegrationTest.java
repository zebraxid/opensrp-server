package org.opensrp.repository.it;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opensrp.domain.Camp;
import org.opensrp.repository.AllCamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-applicationContext-opensrp.xml")
public class AllCampIntegrationTest {
	
	@Autowired
	AllCamp allCamp;
	
	@Before
	public void setUp() throws Exception {
		
	}
	
	@Test
	public void addCamptest() {
		Camp camp = new Camp();
		camp.setCampName("Test");
		camp.setProviderName("ANM 1");
		camp.setDate("2017-12-12");
		camp.setStatus(true);
		allCamp.add(camp);
	}
	
}
