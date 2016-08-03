package org.opensrp.register.mcare.repository.it;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.opensrp.register.mcare.repository.AllChilds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-applicationContext-opensrp-register-mcare.xml")
public class AllChildsIntegrationTest {
	
	@Autowired
	private AllChilds allChilds;
	
	@Before
	public void setUp() throws Exception {
		allChilds.removeAll();
	}
	
	/*  @Test
	  public void shouldRegisterEligibleCouple() throws Exception {
	  	Child houseHold = new Child().withFWHOHNAME("Child-1").withPROVIDERID("Provider-I");

	  	allChilds.add(houseHold);

	      List<Child> allChildsInDB = allChilds.getAll();
	      assertThat(allChildsInDB, is(asList(houseHold)));
	      assertThat(allChildsInDB.get(0).FWHOHNAME(), is("Child-1"));
	  }*/
	
}
