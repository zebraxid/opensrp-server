package org.opensrp.register.mcare.repository.it;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.opensrp.common.util.EasyMap.create;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opensrp.register.mcare.domain.HouseHold;
import org.opensrp.register.mcare.repository.AllElcos;
import org.opensrp.register.mcare.repository.AllHouseHolds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-applicationContext-opensrp-register-mcare.xml")
public class AllHouseHoldsIntegrationTest {

	@Autowired
    private AllHouseHolds allHouseHolds;
	@Autowired
    private AllElcos allElcos;
	
    @Before
    public void setUp() throws Exception {
    	allHouseHolds.removeAll();
    	allElcos.removeAll();
    }
    
    @Test
    public void shouldRegisterEligibleCouple() throws Exception {
    	HouseHold houseHold = new HouseHold().withFWHOHNAME("HouseHold-1").withPROVIDERID("Provider-I");

    	allHouseHolds.add(houseHold);

        List<HouseHold> allHouseHoldsInDB = allHouseHolds.getAll();
        assertThat(allHouseHoldsInDB, is(asList(houseHold)));
        assertThat(allHouseHoldsInDB.get(0).FWHOHNAME(), is("HouseHold-1"));
    }

}
