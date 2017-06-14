package org.opensrp.register.mcare.encounter.sync;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.opensrp.register.encounter.sync.FeedHandler;
import org.opensrp.register.encounter.sync.SyncConstant;
public class FeedHandlerUnitTest{	
	
	@Before
	public void setUp() throws Exception
	{
		String str = "TT 2 , 2016-02-28, true, 2.0";		
	}
	@Test
	public void shouldGetDoseFromString(){
		FeedHandler feedHandler = new FeedHandler();
		String str = "TT 2 , 2016-02-28, true, 2.0";
		Assert.assertEquals("Should equal",2.0, feedHandler.getDoseFromString(str));
	}
	
	@Test
	public void shouldNotGetDoseFromString(){
		FeedHandler feedHandler = new FeedHandler();
		String str = "TT 2 , 2016-02-28, true, f.0";
		Assert.assertEquals("Should equal",0.0, feedHandler.getDoseFromString(str));
	}
	
	
	@Test
	public void shouldGetDateFromString(){
		FeedHandler feedHandler = new FeedHandler();
		String str = "TT 2 , 2016-02-28, true, 2.0";
		Assert.assertEquals("Should equal","2016-02-28", feedHandler.getDateFromString(str));
	}
	
	@Test
	public void shouldNotGetDateFromString(){
		FeedHandler feedHandler = new FeedHandler();
		String str = "TT 2 , 201602-28, true, 2.0";
		Assert.assertNull(feedHandler.getDateFromString(str));
	}
	
	@Test
	public void shouldGetVaccinationName() throws Exception{
		FeedHandler feedHandler = new FeedHandler();
		String str = "BCG, 2016-02-28, true, 0.0";		
		Assert.assertEquals("Should equal","BCG", feedHandler.getVaccinationName(str));
	}
	
	@Test
	public void shouldNotGetVaccinationName() throws Exception{
		FeedHandler feedHandler = new FeedHandler();
		String str = "TT 2, 2016-02-28, true, 0.0";		
		Assert.assertNotSame("Should not equal","BCG", feedHandler.getVaccinationName(str));
	}
	
	@Test
	public void shouldStringFilter(){
		FeedHandler feedHandler = new FeedHandler();
		String str ="Immunization Incident Template: TT 2 (Tetanus toxoid), 2016-02-28, true, 2.0";
		Assert.assertEquals("Should equal these two string","TT 2 , 2016-02-28, true, 2.0", feedHandler.stringFilter(str));
	}
	
	@Test
	public void shouldNotStringFilter(){
		FeedHandler feedHandler = new FeedHandler();
		String str ="Evil Immunization Incident Template: TT 2 (Tetanus toxoid), 2016-02-28, true, 2.0";
		Assert.assertNotSame("Should not equal these two string","TT 2 , 2016-02-28, true, 2.0", feedHandler.stringFilter(str));
	}
	
	@Test
	public void shouldCheckTTFVaccineFromAString(){
		FeedHandler feedHandler = new FeedHandler();
		String str ="TT 2 , 2016-02-28, true, 2.0";		 
		Assert.assertTrue(feedHandler.checkTTFVaccineFromAString(str, SyncConstant.TT));
	}
	@Test
	public void shouldNotCheckTTFVaccineFromAString(){
		FeedHandler feedHandler = new FeedHandler();
		String str ="BCG , 2016-02-28, true, 2.0";		 
		Assert.assertFalse(feedHandler.checkTTFVaccineFromAString(str, SyncConstant.TT));
	}
	
	@Test
	public void shouldGetScheduleName(){
		FeedHandler feedHandler = new FeedHandler();
		String s =feedHandler.getScheduleName("PCV", 1);
		System.out.println(s);
	}
}
