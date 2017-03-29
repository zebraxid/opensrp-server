package org.opensrp.repository.it;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.Random;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opensrp.domain.ErrorTrace;
import org.opensrp.repository.AllErrorTrace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-opensrp.xml")
public class AllErrorTraceIntegrationTest {

	@Autowired
	private AllErrorTrace allErrorTrace;
	
	@Before
	public void setUp() throws Exception {
		initMocks(this);
		allErrorTrace.removeAll();
	}
	
	@Test
	public void shouldAddError()throws Exception
	{
		//ErrorTrace error=new ErrorTrace(new Date(), "Error Testing" , "not availalbe","this is an Testing Error", "unsolved");
		Random ran=new Random();
		try
		{
			throw new RuntimeException("My Test Msg");
		}
		catch(Exception e){
			for(int i=0;i<15;i++)
			{
		ErrorTrace error=new ErrorTrace();
		error.setErrorType("error loggging test");
		error.setDate(DateTime.now());
		error.setStackTrace("Complete Stack Trace :");
		error.setStatus("unsolved");
		error.setDocumentType("Test Document");
		error.setRecordId(String.valueOf(ran.nextInt(100000)+ran.nextInt(1002)));
		allErrorTrace.add(error);
		
			}
			assertTrue(allErrorTrace.findErrorsByStatus("unsolved").size() == 15);
			assertTrue(allErrorTrace.findErrorsByStatus("solved").size() <15);
				
			}
	}
	
	
	
}
