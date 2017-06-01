package org.opensrp.register.mcare.encounter.sync;

import java.text.ParseException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.opensrp.form.repository.AllFormSubmissions;
import org.opensrp.register.encounter.sync.FeedHandler;
import org.opensrp.register.mcare.repository.AllMembers;




public class FeedHandlerTest extends TestConfig{
	
	@Rule
    public ExpectedException exceptions = ExpectedException.none();
	@Mock
    private AllFormSubmissions formSubmissions;
	@Mock
	private AllMembers allMembers;
	
	@Before
	public void setUp() throws Exception
	{
		//formSubmissions = Mockito.mock(AllFormSubmissions.class);
		formSubmissions = new AllFormSubmissions(getStdCouchDbConnectorForOpensrpForm());
		allMembers = new AllMembers(1,getStdCouchDbConnectorForOpensrp());
		
	}
	
	@Test
	public void shuoldStringFilter(){
		String str = "Immunization Incident Template: BCG (Tuberculosis, live attenuated), 2016-08-25, true, 0.0";
		FeedHandler feedHandler = new FeedHandler();
		System.out.println(feedHandler.stringFilter(str));
		Assert.assertEquals("BCG , 2016-08-25, true, 0.0", feedHandler.stringFilter(str).trim());
	}
	
	@Test
	public void shouldParseDateFromString(){
		FeedHandler feedHandler = new FeedHandler();
		feedHandler.parseDateFromString("BCG , 2016-08-25, true, 0.0");
		Assert.assertEquals("2016-08-25", feedHandler.parseDateFromString("BCG , 2016-08-25, true, 0.0").trim());
	}
	
	/*@Test
	public void expectParseException(){
		FeedHandler feedHandler = new FeedHandler();
		feedHandler.parseDateFromString("BCG , 2016-08-25, true, 0.0");
		exceptions.expect(ParseException.class);
		//Assert.assertEquals("2016-08-25", feedHandler.parseDateFromString("BCG , 2016-08-25, true, 0.0").trim());
	}*/
	
}
