package org.opensrp.register.mcare.encounter.sync;

import junit.framework.Assert;

import org.json.JSONException;
import org.junit.Test;
import org.opensrp.register.encounter.sync.forms.ChildVaccineFollowup;
import org.opensrp.register.mcare.domain.Members;
public class FileReaderUnitTest extends TestConfig{	
	@Test
	public void shouldGetFieldName() throws NoSuchFieldException, IllegalAccessException, JSONException{		
		ChildVaccineFollowup childVaccineFollowup = ChildVaccineFollowup.getInstance();		
		Assert.assertEquals("final_opv0", childVaccineFollowup.getFieldName("OPVFinalMapping", 0));
	}
	@Test(expected=NoSuchFieldException.class)
	public void shouldGetNoSuchFieldException() throws NoSuchFieldException, IllegalAccessException, JSONException{		
		ChildVaccineFollowup childVaccineFollowup = ChildVaccineFollowup.getInstance();		
		Assert.assertEquals("final_opv0", childVaccineFollowup.getFieldName("OPVFinalMapping_Not", 0));
	}	
	@Test
	public void shouldCheckingVaccineGivenOrNot() throws NoSuchFieldException, IllegalAccessException, JSONException{		
		
		Child child = new Child();
		Members member = child.getChildMember();
		member.child_vaccine().add(child.getChildVaccine());
		ChildVaccineFollowup childVaccineFollowup = ChildVaccineFollowup.getInstance();		
		Assert.assertTrue(childVaccineFollowup.checkingVaccineGivenOrNot(member, 0, "BCG"));
		Assert.assertFalse(childVaccineFollowup.checkingVaccineGivenOrNot(member, 1, "OPV"));
	}
}
