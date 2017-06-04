package org.opensrp.register.mcare.encounter.sync;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.opensrp.form.repository.AllFormSubmissions;
import org.opensrp.register.encounter.sync.FeedHandler;
import org.opensrp.register.mcare.repository.AllMembers;




public class FeedHandlerUnitTest extends TestConfig{	
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
	public void shuoldStringFilter(){
		String str = "Immunization Incident Template: BCG (Tuberculosis, live attenuated), 2016-08-25, true, 0.0";
		Assert.assertEquals("BCG , 2016-08-25, true, 0.0", feedHandler.stringFilter(str).trim());
	}
	
	@Test(expected=ParseException.class)
	public void shouldParseDateFromString() throws ParseException{		
		feedHandler.getDateFromString("BCG , 2016-08-25, true, 0.0");
		Assert.assertEquals("2016-08-25", feedHandler.getDateFromString("BCG , 2016-08-25, true, 0.0").trim());
	}	
	
	@Test
	public void shouldGetVaccinationName() throws Exception{
		feedHandler.getVaccinationName("BCG , 2016-08-25, true, 0.0");
		Assert.assertEquals("BCG", feedHandler.getVaccinationName("BCG , 2016-08-25, true, 0.0").trim());
	}
	
	
	@Test
	public void shouldgetTTFromString(){		
		String StringAfterFilter = feedHandler.stringFilter("Immunization Incident Template: BCG (Tuberculosis, live attenuated), 2017-01-25, true, 0.0");
		assertEquals(feedHandler.getTTFromString(StringAfterFilter, "BCG"),true);
		
	}
	
	@Test(expected=Exception.class)
	public void shouldGetDoseFromString() throws Exception{
		Assert.assertEquals("0.0", feedHandler.getDoseFromString("BCG , 2016-08-25, true, 0.0"));
	}
}
