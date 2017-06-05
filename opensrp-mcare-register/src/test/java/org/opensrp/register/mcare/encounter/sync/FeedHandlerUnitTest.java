package org.opensrp.register.mcare.encounter.sync;

import java.io.IOException;

import org.codehaus.jackson.JsonNode;
import org.junit.Assert;
import org.junit.Test;
import org.opensrp.register.encounter.sync.FileReader;
import org.opensrp.register.encounter.sync.SyncConstant;
public class FeedHandlerUnitTest extends FileReader{	
	@Test
	public void shuoldGetFile() throws IOException{
		JsonNode file = FeedHandlerUnitTest.getFile("./../assets/form", SyncConstant.CHILDACCINATIONFORMNAME);
		Assert.assertNotNull(file);
	}	
	@Test(expected=IOException.class)
	public void shuoldNotGetFile() throws IOException{
		JsonNode file = FeedHandlerUnitTest.getFile("./../assets/forms", SyncConstant.CHILDACCINATIONFORMNAME);
		Assert.assertNull(file);
	}
	
	
}
