package org.opensrp.connector.openmrs;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.ict4h.atomfeed.client.AtomFeedProperties;
import org.ict4h.atomfeed.client.domain.Event;
import org.ict4h.atomfeed.client.repository.AllFailedEvents;
import org.ict4h.atomfeed.client.repository.AllFeeds;
import org.ict4h.atomfeed.client.repository.AllMarkers;
import org.ict4h.atomfeed.client.service.AtomFeedClient;
import org.ict4h.atomfeed.client.service.EventWorker;
import org.ict4h.atomfeed.transaction.AFTransactionManager;
import org.ict4h.atomfeed.transaction.AFTransactionWork;
import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.connector.atomfeed.AtomfeedService;
import org.opensrp.connector.openmrs.constants.OpenmrsConstants;
import org.opensrp.connector.openmrs.service.OpenmrsOrderService;
import org.opensrp.connector.openmrs.service.OpenmrsService;
import org.opensrp.domain.Drug;
import org.opensrp.domain.DrugOrder;
import org.opensrp.repository.AllDrugOrders;
import org.opensrp.service.ErrorTraceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DrugOrderAtomfeed extends OpenmrsService implements EventWorker, AtomfeedService
{
	private Logger log = Logger.getLogger(getClass().getSimpleName());
	public static final String CATEGORY_URL = "/OpenSRP_DrugOrder/recent.form";
	
	private AtomFeedProperties atomFeedProperties;
	private AFTransactionManager transactionManager;
	private AtomFeedClient client;

	private OpenmrsOrderService orderService;
	private AllDrugOrders allDrugOrders;
	private ErrorTraceService errorTraceService;

	@Autowired
	public DrugOrderAtomfeed(AllMarkers allMarkers, AllFailedEvents allFailedEvents, 
			@Value("#{opensrp['openmrs.url']}") String baseUrl, 
			OpenmrsOrderService orderService, AllDrugOrders allDrugOrders, 
			ErrorTraceService errorTraceService) throws URISyntaxException {
		if(baseUrl != null){
			OPENMRS_BASE_URL = baseUrl;
		}

		this.atomFeedProperties = new AtomFeedProperties();
		this.transactionManager = new AFTransactionManager(){
			@Override
			public <T> T executeWithTransaction(AFTransactionWork<T> action) throws RuntimeException {
				return action.execute();
			}
		};
		WebClient webClient = new WebClient();
		
		URI uri = new URI(OPENMRS_BASE_URL+OpenmrsConstants.ATOMFEED_URL+CATEGORY_URL);
		this.client = new AtomFeedClient(new AllFeeds(webClient), allMarkers, allFailedEvents, atomFeedProperties, transactionManager, uri, this);
	
		this.orderService = orderService;
		this.allDrugOrders = allDrugOrders;
		this.errorTraceService = errorTraceService;
	}
	
	@Override
	public void process(Event event) {
		log.info("Processing item : "+event.getContent());
		try {
			String content = event.getContent().substring(event.getContent().lastIndexOf("/")+1);
			JSONObject o = orderService.getDrugOrder(content, true);
			if(o == null){
				throw new RuntimeException("DrugOrder uuid specified in atomfeed content ("+content+") did not return any drug order.");
			}
			DrugOrder drugO = orderService.toDrugOrder(o);
			List<Drug> existingL = allDrugOrders.findAllByCode(content);
			if(existingL.isEmpty()){
				allDrugOrders.add(drugO);
			}
			else {
				allDrugOrders.merge(existingL.get(0).getId(), drugO);
			}
		} catch (JSONException e) {
			errorTraceService.log("DRUG ORDER ATOMFEED PROCESS FAIL", DrugOrder.class.getName(), event.getContent(), ExceptionUtils.getStackTrace(e), null);
		}
	}

	@Override
	public void cleanUp(Event event) {
		
	}

	@Override
	public void processEvents() {
		Logger.getLogger(getClass().getName()).info("Processing PatientAtomfeeds");
		client.processEvents();
	}

	@Override
	public void processFailedEvents() {
		client.processFailedEvents();
	}
	
	void setUrl(String url) {
		OPENMRS_BASE_URL = url;
	}
}