package org.opensrp.connector.openmrs.service;

import static org.mockito.MockitoAnnotations.initMocks;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.ektorp.impl.StdObjectMapperFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.scheduler.domain.MotechEvent;
import org.opensrp.connector.openmrs.schedule.OpenmrsSyncerListener;
import org.opensrp.repository.AllAppStateTokens;
import org.opensrp.service.ConfigService;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class OpenmrsSyncerListenerTest {
	
	MotechEvent event = new MotechEvent("subject");
	
	private OpenmrsSyncerListener openmrsSyncerListener;
	
	private AllAppStateTokens allAppStateTokens;
	
	private CouchDbInstance dbInstance;
	
	private StdCouchDbConnector stdCouchDbConnector;
	
	@Before
	public void setup() throws InstantiationException, IllegalAccessException, IllegalArgumentException,
	    InvocationTargetException, NoSuchMethodException, SecurityException {
		initMocks(this);
		HttpClient httpClient = new StdHttpClient.Builder().host("localhost")
		//.host("192.168.19.55")
		        .port(5984).username("rootuser").password("adminpass").socketTimeout(1000).build();
		dbInstance = new StdCouchDbInstance(httpClient);
		stdCouchDbConnector = new StdCouchDbConnector("opensrp", dbInstance, new StdObjectMapperFactory());
		stdCouchDbConnector.createDatabaseIfNotExists();
		Constructor<AllAppStateTokens> constructor = AllAppStateTokens.class.getDeclaredConstructor(CouchDbConnector.class);
		
		constructor.setAccessible(true);
		AllAppStateTokens car = constructor.newInstance(stdCouchDbConnector);
		System.out.println("Car Id:" + car);
		
		//allAppStateTokens = new AllAppStateTokens(stdCouchDbConnector);
		ConfigService config = new ConfigService(car);
		
		openmrsSyncerListener = new OpenmrsSyncerListener(null, null, null, config, null, null, null, null, null);
		
	}
	
	@After
	public void tearDown() {
		
	}
	
	@Test
	public void test() {
		System.err.println("ddd");
		openmrsSyncerListener.pushToOpenMRS(event);
		/*openmrsSyncerListener.pushToOpenMRS(event);
		final TestLoggerAppender appender = new TestLoggerAppender();
		final Logger logger = Logger.getLogger(OpenmrsSyncerListener.class.toString());
		logger.setLevel(Level.ALL);
		logger.addAppender(appender);
		
		final List<LoggingEvent> log = appender.getLog();
		final LoggingEvent firstLogEntry = log.get(0);
		//assertEquals(firstLogEntry.getRenderedMessage(), "Unparseable date: \"Not A Valid Date\"");
		logger.removeAllAppenders();*/
	}
}
