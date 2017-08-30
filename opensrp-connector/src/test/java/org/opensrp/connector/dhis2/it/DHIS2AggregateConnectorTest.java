package org.opensrp.connector.dhis2.it;

import static junit.framework.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opensrp.connector.dhis2.DHIS2AggregateConnector;
import org.opensrp.connector.dhis2.Dhis2HttpUtils;
import org.opensrp.connector.openmrs.service.TestResourceLoader;
import org.opensrp.domain.Event;
import org.opensrp.domain.Obs;
import org.opensrp.repository.AllEvents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-applicationContext-opensrp-connector.xml")
public class DHIS2AggregateConnectorTest extends TestResourceLoader {
	
	public DHIS2AggregateConnectorTest() throws IOException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	private DHIS2AggregateConnector dhis2AggregateConnector;
	
	@Autowired
	private AllEvents allEvents;
	
	@Autowired
	private DHIS2AggregateConnector dHIS2AggregateConnector;
	
	@Autowired
	private Dhis2HttpUtils dhis2HttpUtils;
	
	@Before
	public void setup() {
		allEvents.removeAll();
	}
	
	@Test
	public void testDeleteDHIS2Data() throws JSONException, IOException {
		delete("IDc0HEyjhvL", "xMlVHstzOgC");
		delete("IDc0HEyjhvL", "yNWOJ0OOOQD");
		delete("IDc0HEyjhvL", "ii7lOGQqEq5");
		delete("IDc0HEyjhvL", "Wtf7iSiQdUJ");
		
		delete("IDc0HEyjhvL", "XYqYdPiapTB");
		delete("IDc0HEyjhvL", "ghHOqHNST3Z");
		delete("IDc0HEyjhvL", "jY9SUZVxPHZ");
		delete("IDc0HEyjhvL", "MNe2NbiMPi4");
		
		delete("IDc0HEyjhvL", "DF4I78hJCyE");
		delete("IDc0HEyjhvL", "IMh3lVLICJM");
		delete("IDc0HEyjhvL", "belqjUALCbL");
		delete("IDc0HEyjhvL", "dYqIehgysyx");
		delete("IDc0HEyjhvL", "MR1zrXS829u");
		delete("IDc0HEyjhvL", "dxH32jHc21V");
	}
	
	@Test
	public void testGetAggregatedDataCount() throws JSONException, IOException {
		Event expectedEvent = new Event("049e6b44-a9b5-4553-b463-004fa6743dc2", "Birth Registration", new DateTime(0l,
		        DateTimeZone.UTC), "child", "provider", "5bf3b4ca-9482-4e85-ab7a-0c44e4edb329", "formSubmissionId");
		expectedEvent.addIdentifier("key", "value");
		List<Obs> firstObservations = new ArrayList<>();
		
		Obs placeBirth = new Obs();
		placeBirth.setFieldCode("1572AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		placeBirth.setFieldDataType("select one");
		placeBirth.setFieldType("concept");
		placeBirth.setParentCode("");
		placeBirth.setFormSubmissionField("Place_Birth");
		List<Object> values = new ArrayList<Object>();
		values.add("1536AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		placeBirth.setValues(values);
		
		List<Object> humanReadableValues = new ArrayList<Object>();
		humanReadableValues.add("Health_Facility");
		placeBirth.setHumanReadableValues(humanReadableValues);
		
		Obs birthWeight = new Obs();
		birthWeight.setFieldCode("5916AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		birthWeight.setFieldDataType("text");
		birthWeight.setFieldType("concept");
		birthWeight.setParentCode("");
		birthWeight.setFormSubmissionField("Birth_Weight");
		List<Object> valuesOFbirthWeight = new ArrayList<Object>();
		valuesOFbirthWeight.add("4");
		birthWeight.setValues(valuesOFbirthWeight);
		
		firstObservations.add(birthWeight);
		firstObservations.add(placeBirth);
		expectedEvent.setObs(firstObservations);
		allEvents.add(expectedEvent);
		
		Event anotherExpectedEvent = new Event("049e6b44-a9b5-4553-b463-004fa6743d34", "Birth Registration", new DateTime(
		        0l, DateTimeZone.UTC), "child", "provider", "5453b4ca-9482-4e85-ab7a-0c44e4edb329", "formSubmissionId");
		anotherExpectedEvent.addIdentifier("key", "value");
		List<Obs> secondObservations = new ArrayList<>();
		
		Obs anotherPlaceBirth = new Obs();
		anotherPlaceBirth.setFieldCode("1572AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		anotherPlaceBirth.setFieldDataType("select one");
		anotherPlaceBirth.setFieldType("concept");
		anotherPlaceBirth.setParentCode("");
		anotherPlaceBirth.setFormSubmissionField("Place_Birth");
		List<Object> anotherPlaceBirthValues = new ArrayList<Object>();
		anotherPlaceBirthValues.add("1536AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		anotherPlaceBirth.setValues(values);
		
		List<Object> anotherHumanReadableValues = new ArrayList<Object>();
		anotherHumanReadableValues.add("Home");
		anotherPlaceBirth.setHumanReadableValues(anotherHumanReadableValues);
		secondObservations.add(anotherPlaceBirth);
		
		Obs anotherBirthWeight = new Obs();
		anotherBirthWeight.setFieldCode("5916AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		anotherBirthWeight.setFieldDataType("text");
		anotherBirthWeight.setFieldType("concept");
		anotherBirthWeight.setParentCode("");
		anotherBirthWeight.setFormSubmissionField("Birth_Weight");
		List<Object> anotherValuesOFbirthWeight = new ArrayList<Object>();
		anotherValuesOFbirthWeight.add("3");
		anotherBirthWeight.setValues(anotherValuesOFbirthWeight);
		
		secondObservations.add(anotherBirthWeight);
		anotherExpectedEvent.setObs(secondObservations);
		
		allEvents.add(anotherExpectedEvent);
		
		Event expectedVaccineEvent = new Event("049e6b4r-a9b5-4553-b463-004fa6743d34", "Vaccination", new DateTime(0l,
		        DateTimeZone.UTC), "child", "provider", "7453b4ca-9482-4e85-ab7a-0c44e4edb329", "formSubmissionId");
		expectedVaccineEvent.addIdentifier("key", "value");
		List<Obs> vaccineObservations = new ArrayList<>();
		
		Obs opv_1 = new Obs();
		opv_1.setFieldCode("1410AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		opv_1.setFieldDataType("date");
		opv_1.setFieldType("concept");
		opv_1.setParentCode("783AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		opv_1.setFormSubmissionField("opv_1");
		List<Object> opv_1_values = new ArrayList<Object>();
		opv_1_values.add("2016-12-07");
		opv_1.setValues(opv_1_values);
		vaccineObservations.add(opv_1);
		
		Obs opv_2 = new Obs();
		opv_2.setFieldCode("1410AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		opv_2.setFieldDataType("date");
		opv_2.setFieldType("concept");
		opv_2.setParentCode("783AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		opv_2.setFormSubmissionField("opv_2");
		vaccineObservations.add(opv_2);
		
		Obs bcg = new Obs();
		bcg.setFieldCode("1410AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		bcg.setFieldDataType("date");
		bcg.setFieldType("concept");
		bcg.setParentCode("783AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		bcg.setFormSubmissionField("bcg");
		vaccineObservations.add(bcg);
		
		Obs pcv_1 = new Obs();
		pcv_1.setFieldCode("1410AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		pcv_1.setFieldDataType("date");
		pcv_1.setFieldType("concept");
		pcv_1.setParentCode("783AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		
		pcv_1.setFormSubmissionField("pcv_1");
		vaccineObservations.add(pcv_1);
		
		Obs pcv_2 = new Obs();
		pcv_2.setFieldCode("1410AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		pcv_2.setFieldDataType("date");
		pcv_2.setFieldType("concept");
		pcv_2.setParentCode("783AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		pcv_2.setFormSubmissionField("pcv_2");
		vaccineObservations.add(pcv_2);
		
		Obs penta_1 = new Obs();
		penta_1.setFieldCode("1410AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		penta_1.setFieldDataType("date");
		penta_1.setFieldType("concept");
		penta_1.setParentCode("783AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		penta_1.setFormSubmissionField("penta_1");
		vaccineObservations.add(penta_1);
		
		Obs penta_2 = new Obs();
		penta_2.setFieldCode("1410AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		penta_2.setFieldDataType("date");
		penta_2.setFieldType("concept");
		penta_2.setParentCode("783AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		penta_2.setFormSubmissionField("penta_2");
		vaccineObservations.add(penta_2);
		
		Obs rota_1 = new Obs();
		rota_1.setFieldCode("1410AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		rota_1.setFieldDataType("date");
		rota_1.setFieldType("concept");
		rota_1.setParentCode("783AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		rota_1.setFormSubmissionField("rota_1");
		vaccineObservations.add(rota_1);
		
		Obs rota_2 = new Obs();
		rota_2.setFieldCode("1410AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		rota_2.setFieldDataType("date");
		rota_2.setFieldType("concept");
		rota_2.setParentCode("783AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		rota_2.setFormSubmissionField("rota_2");
		vaccineObservations.add(rota_2);
		
		expectedVaccineEvent.setObs(vaccineObservations);
		allEvents.add(expectedVaccineEvent);
		JSONObject aggregatedDataSet = null;
		
		aggregatedDataSet = dHIS2AggregateConnector.getAggregatedDataCount();
		JSONObject response = dHIS2AggregateConnector.aggredateDataSendToDHIS2(aggregatedDataSet);
		String expectedImportedCount = "14";
		JSONObject importCount = response.getJSONObject("importCount");
		String actualImportedCount = importCount.getString("imported");
		String expectedStatus = "SUCCESS";
		String actualdStatus = response.getString("status");
		assertEquals(expectedImportedCount, actualImportedCount);
		assertEquals(expectedStatus, actualdStatus);
		
		delete("IDc0HEyjhvL", "xMlVHstzOgC");
		delete("IDc0HEyjhvL", "yNWOJ0OOOQD");
		delete("IDc0HEyjhvL", "ii7lOGQqEq5");
		delete("IDc0HEyjhvL", "Wtf7iSiQdUJ");
		
		delete("IDc0HEyjhvL", "XYqYdPiapTB");
		delete("IDc0HEyjhvL", "ghHOqHNST3Z");
		delete("IDc0HEyjhvL", "jY9SUZVxPHZ");
		delete("IDc0HEyjhvL", "MNe2NbiMPi4");
		
		delete("IDc0HEyjhvL", "DF4I78hJCyE");
		delete("IDc0HEyjhvL", "IMh3lVLICJM");
		delete("IDc0HEyjhvL", "belqjUALCbL");
		delete("IDc0HEyjhvL", "dYqIehgysyx");
		delete("IDc0HEyjhvL", "MR1zrXS829u");
		delete("IDc0HEyjhvL", "dxH32jHc21V");
		
	}
	
	@Test(expected = Exception.class)
	public void testException() throws JSONException {
		JSONObject aggregatedDataSet = null;
		dHIS2AggregateConnector.aggredateDataSendToDHIS2(aggregatedDataSet);
	}
	
	public void delete(String ou, String de) throws JSONException, IOException {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MONTH, -1);
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH) + 1;
		int length = (int) (Math.log10(month) + 1);
		String formatted;
		
		if (length < 2) {
			formatted = String.format("%02d", month);
		} else {
			formatted = Integer.toString(month);
		}
		String periodTime = Integer.toString(year) + formatted;
		String url = "dataValues?pe=" + periodTime + "&ou=" + ou + "&de=" + de;
		dhis2HttpUtils.delete(url, "", "");
		
	}
	
}
