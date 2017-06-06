package org.opensrp.register.mcare.encounter.sync;

import junit.framework.Assert;

import org.junit.Test;
import org.opensrp.register.encounter.sync.FeedHandler;
public class FeedHandlerUnitTest{	
	
	@Test
	public void shouldGetDoseFromString(){
		FeedHandler feedHandler = new FeedHandler();
		String str = "TT 2 , 2016-02-28, true, 2.0";
		Assert.assertEquals("Should equal",2.0, feedHandler.getDoseFromString(str));
	}
	
	@Test
	public void shouldGetDateFromString(){
		FeedHandler feedHandler = new FeedHandler();
		String str = "TT 2 , 2016-02-28, true, 2.0";
		Assert.assertEquals("Should equal","2016-02-28", feedHandler.getDateFromString(str));
	}
	
	@Test
	public void shouldGetVaccinationName() throws Exception{
		FeedHandler feedHandler = new FeedHandler();
		String str = "BCG, 2016-02-28, true, 0.0";		
		Assert.assertEquals("Should equal","BCG", feedHandler.getVaccinationName(str));
	}
	
	@Test
	public void shouldStringFilter(){
		FeedHandler feedHandler = new FeedHandler();
		String str ="Immunization Incident Template: TT 2 (Tetanus toxoid), 2016-02-28, true, 2.0";
		Assert.assertEquals("Should equal these two string","TT 2 , 2016-02-28, true, 2.0", feedHandler.stringFilter(str));
	}
	
}
