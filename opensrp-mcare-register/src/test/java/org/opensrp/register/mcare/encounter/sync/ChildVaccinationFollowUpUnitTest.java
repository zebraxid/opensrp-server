package org.opensrp.register.mcare.encounter.sync;

import junit.framework.Assert;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.opensrp.form.repository.AllFormSubmissions;
import org.opensrp.register.encounter.sync.FeedHandler;
import org.opensrp.register.encounter.sync.forms.ChildVaccineFollowup;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.repository.AllMembers;
public class ChildVaccinationFollowUpUnitTest extends TestConfig{	
	@Mock
    private AllFormSubmissions formSubmissions;
	@Mock
	private AllMembers allMembers;
	@Mock
	private FeedHandler feedHandler;
	@Before
	public void setUp() throws Exception
	{
		feedHandler = new FeedHandler();
		//formSubmissions = Mockito.mock(AllFormSubmissions.class);
		formSubmissions = new AllFormSubmissions(getStdCouchDbConnectorForOpensrpForm());
		allMembers = new AllMembers(1,getStdCouchDbConnectorForOpensrp());		
	}
	
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
	public void shouldReturnTrueWhenTestCheckingVaccineGivenOrNot(){		
		Child child = new Child();
		Members member = child.getChildMember();
		member.child_vaccine().add(child.getChildVaccine());
		ChildVaccineFollowup childVaccineFollowup = ChildVaccineFollowup.getInstance();		
		Assert.assertTrue(childVaccineFollowup.checkingVaccineGivenOrNot(member, 0, "BCG"));		
	}
	
	@Test
	public void shouldReturnFalseWhenTestCheckingVaccineGivenOrNot(){		
		Child child = new Child();
		Members member = child.getChildMember();
		member.child_vaccine().add(child.getChildVaccine());
		ChildVaccineFollowup childVaccineFollowup = ChildVaccineFollowup.getInstance();		
		Assert.assertFalse(childVaccineFollowup.checkingVaccineGivenOrNot(member, 1, "OPV"));
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldReturnNullPointerExceptionWhenTestCheckingVaccineGivenOrNot() throws NullPointerException{		
		Child child = new Child();
		Members member = child.getChildMember();		
		member.child_vaccine().add(child.getChildVaccineWithNoBCGFinalField());
		ChildVaccineFollowup childVaccineFollowup = ChildVaccineFollowup.getInstance();		
		Assert.assertFalse(childVaccineFollowup.checkingVaccineGivenOrNot(member, 0, "BCG"));
	}
}
