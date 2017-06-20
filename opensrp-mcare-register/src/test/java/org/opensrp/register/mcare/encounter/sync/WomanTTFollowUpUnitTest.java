package org.opensrp.register.mcare.encounter.sync;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.opensrp.register.encounter.sync.forms.WomanTTFollowUp;
import org.opensrp.register.mcare.domain.Members;

public class WomanTTFollowUpUnitTest extends TestConfig{	
	
	@Before
	public void setUp() throws Exception
	{
		
	}
	
	@Test
	public void shouldReturnTrueWhenTestCheckingVaccineGivenOrNot(){		
		Woman woman = new Woman();
		Members member = woman.getWomanMember();
		member.setTTVisit(woman.getTTVaccine());
		WomanTTFollowUp ttFollowUp = WomanTTFollowUp.getInstance();		
		Assert.assertTrue(ttFollowUp.isVaccineGiven(member, 1, "TT"));		
	}
	
	@Test
	public void shouldReturnFalseWhenTestCheckingVaccineGivenOrNot(){		
		Woman woman = new Woman();
		Members member = woman.getWomanMember();
		member.setTTVisit(woman.getTTVaccine());
		WomanTTFollowUp ttFollowUp = WomanTTFollowUp.getInstance();		
		Assert.assertFalse(ttFollowUp.isVaccineGiven(member, 3, "TT"));
	}
}
