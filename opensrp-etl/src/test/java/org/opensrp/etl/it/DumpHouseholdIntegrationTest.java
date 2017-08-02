package org.opensrp.etl.it;

import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.ektorp.impl.StdObjectMapperFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opensrp.etl.document.HouseholdDocument;
import org.opensrp.etl.entity.HousoholdEntity;
import org.opensrp.etl.service.HouseholdServices;
import org.opensrp.register.mcare.domain.HouseHold;
import org.opensrp.register.mcare.repository.AllHouseHolds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-applicationContext-opensrp-etl.xml")
public class DumpHouseholdIntegrationTest {

	@Autowired
	private HouseholdServices householdServices; 
	@Autowired
	private HousoholdEntity housoholdEntity;
	
    private AllHouseHolds allHouseHolds;
	
	private CouchDbInstance dbInstance;
	private StdCouchDbConnector stdCouchDbConnector;
    @Before
    public void setUp() throws Exception {
    	HttpClient httpClient = new StdHttpClient.Builder() 
        //.host("localhost") 
       	.host("localhost")
        .port(5984)
        .username("Admin").password("mPower@1234")
        .socketTimeout(1000) 
        .build(); 
		dbInstance = new StdCouchDbInstance(httpClient);
		stdCouchDbConnector = new StdCouchDbConnector("opensrp", dbInstance, new StdObjectMapperFactory());		 
		stdCouchDbConnector.createDatabaseIfNotExists(); 
		allHouseHolds = new AllHouseHolds(2, stdCouchDbConnector);
    }
    
    @Test
    public void testAddHousehold(){
    	HouseHold houseHold = allHouseHolds.findByCaseId("00004627-2f6d-443d-a162-4bbce38661fb");
    	housoholdEntity.setCaseId(houseHold.caseId());
    	housoholdEntity.setCreated();
    	housoholdEntity.setTimeStamp();
    	housoholdEntity.setUpdated();
    	housoholdEntity.setDocumentId(houseHold.getId());
    	housoholdEntity.setTimeStamp();
    	housoholdEntity.setRevId(houseHold.getRevision());
    	housoholdEntity.setStatus(true);
    	
    	HouseholdDocument doc = new HouseholdDocument();
    	doc.withCASEID(houseHold.caseId());
    	doc.withINSTANCEID(houseHold.INSTANCEID());
    	doc.withClientVersion(houseHold.get());
    	doc.withDetails(houseHold.details());
    	doc.withPROVIDERID(houseHold.PROVIDERID());
    	doc.withTODAY(houseHold.TODAY());
    	doc.withSTART(houseHold.START());
    	doc.withEND(houseHold.END());
    	doc.withFWNHREGDATE(houseHold.FWNHREGDATE());
    	doc.withFWGOBHHID(houseHold.FWGOBHHID());
    	doc.withFWJIVHHID(houseHold.FWJIVHHID());
    	doc.withFWCOUNTRY(houseHold.FWCOUNTRY());
    	doc.withFWDIVISION(houseHold.FWDIVISION());
    	doc.withFWDISTRICT(houseHold.FWDISTRICT());
    	doc.withFWUPAZILLA(houseHold.FWUPAZILLA());
    	doc.withFWUNION(houseHold.FWUNION());
    	doc.withFWWARD(houseHold.FWWARD());
    	doc.withFWSUBUNIT(houseHold.FWSUBUNIT());
    	doc.withFWMAUZA_PARA(houseHold.FWMAUZA_PARA());
    	doc.withFWNHHHGPS(houseHold.FWNHHHGPS());
    	doc.withform_name(houseHold.form_name());
    	doc.withFWHOHFNAME(houseHold.FWHOHFNAME());
    	doc.withFWHOHLNAME(houseHold.FWHOHLNAME());
    	doc.withFWHOHBIRTHDATE(houseHold.FWHOHBIRTHDATE());
    	doc.withFWHOHGENDER(houseHold.FWHOHGENDER());
    	doc.withFWNHHMBRNUM(houseHold.FWNHHMBRNUM());
    	doc.withFWNHHMWRA(houseHold.FWNHHMWRA());
    	doc.withELCO(houseHold.ELCO());
    	doc.withuser_type(houseHold.user_type());
    	doc.withexternal_user_ID(houseHold.external_user_ID());
    	doc.withcurrent_formStatus(houseHold.current_formStatus());
    	doc.withELCODETAILS(houseHold.ELCODETAILS());
    	doc.withmultimediaAttachments(houseHold.multimediaAttachments());
    	doc.withDetails(houseHold.details());
    	doc.withSUBMISSIONDATE(houseHold.SUBMISSIONDATE());
    	
    	housoholdEntity.setDoc(doc);
    	
    	householdServices.addHousehold(housoholdEntity);
    	
    }
   
  
}
