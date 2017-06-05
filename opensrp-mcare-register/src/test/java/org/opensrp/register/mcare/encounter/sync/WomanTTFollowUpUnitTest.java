package org.opensrp.register.mcare.encounter.sync;

import junit.framework.Assert;
import org.junit.Test;
import org.opensrp.register.encounter.sync.forms.WomanTTFollowUp;
import org.opensrp.register.mcare.domain.Members;

public class WomanTTFollowUpUnitTest extends TestConfig{	
	@Test
	public void shouldCheckingVaccineGivenOrNot(){		
		Woman woman = new Woman();
		Members member = woman.getWomanMember();
		member.setTTVisit(woman.getTTVaccine());
		WomanTTFollowUp ttFollowUp = WomanTTFollowUp.getInstance();		
		Assert.assertTrue(ttFollowUp.checkingVaccineGivenOrNot(member, 1, "TT"));
		Assert.assertFalse(ttFollowUp.checkingVaccineGivenOrNot(member, 2, "TT"));
	}
}
