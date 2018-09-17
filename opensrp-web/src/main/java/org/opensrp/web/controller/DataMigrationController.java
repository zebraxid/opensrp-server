package org.opensrp.web.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpStatus;

import org.opensrp.common.util.HttpResponse;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.common.util.TurnOffCertificateValidation;
import org.opensrp.domain.Address;
import org.opensrp.domain.Client;
import org.opensrp.domain.Event;
import org.opensrp.domain.Obs;
import org.opensrp.service.ClientService;
import org.opensrp.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.mysql.jdbc.StringUtils;

@Controller
@RequestMapping("/data/")
public class DataMigrationController {
	
	private static Logger logger = LoggerFactory.getLogger(DataMigrationController.class.toString());
	
	private static final String GEN_ID_URL = "ws/rest/v1/idgen";
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private ClientService clientService;
	
	@RequestMapping(value = "migration.html", method = RequestMethod.GET)
	public String csvUpload(ModelMap model, HttpSession session) throws JSONException {
		model.addAttribute("location", new Location());
		return "/upload_csv";
	}
	
	@RequestMapping(value = "/migration.html", method = RequestMethod.POST)
	public ModelAndView csvUpload(@RequestParam MultipartFile file, HttpServletRequest request, ModelMap model)
	    throws Exception {
		if (file.isEmpty()) {
			model.put("msg", "failed to upload file because its empty");
			model.addAttribute("msg", "failed to upload file because its empty");
			return new ModelAndView("/location/upload_csv");
		} else if (!"text/csv".equalsIgnoreCase(file.getContentType())) {
			model.addAttribute("msg", "file type should be '.csv'");
			return new ModelAndView("/upload_csv");
		}
		
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		File dir = new File(rootPath + File.separator + "uploadedfile");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		File csvFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
		
		try {
			try (InputStream is = file.getInputStream();
			        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(csvFile))) {
				int i;
				
				while ((i = is.read()) != -1) {
					stream.write(i);
				}
				stream.flush();
			}
		}
		catch (IOException e) {
			model.put("msg", "failed to process file because : " + e.getMessage());
			return new ModelAndView("/upload_csv");
		}
		logger.info("CSV FIle:" + csvFile);
		addMember(csvFile);
		return new ModelAndView("redirect:/data/migration.html");
	}
	
	public class Location {
		
	}
	
	@SuppressWarnings("resource")
	public String addMember(File csvFile) throws Exception {
		String msg = "";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		
		int position = 0;
		String[] tags = null;
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				String tag = "";
				String code = "";
				String name = "";
				String parent = "";
				String[] member = line.split(cvsSplitBy);
				System.err.println("member:" + member[1] + ",generateID:" + generateID());
				Client client = new Client(null);
				client.addIdentifier("Patient_Identifier", generateID());
				String baseEntityId = UUID.randomUUID().toString().trim();
				client.setBaseEntityId(baseEntityId);
				//member[35]  DOB
				client.withFirstName(member[15]).withLastName("").withGender(member[9]).withBirthdate(new DateTime(), false)
				        .withDeathdate(null, false);
				client.setServerVersion(System.currentTimeMillis());
				/// attribute
				client.addAttribute("MaritalStatus", member[10]);
				client.addAttribute("education", member[11]);
				client.addAttribute("occupation", member[12]);
				client.addAttribute("Religion", member[13]);
				client.addAttribute("Religion", member[13]);
				client.addAttribute("nationalId", member[16]);
				client.withIsSendToOpenMRS("yes");
				String FamilyDiseaseHistory = "";
				String diabetes = member[17];
				String hypertension = member[18];
				String cancer = member[19];
				String respiratoryDisease = member[20];
				String phycologicalDisease = member[21];
				String obesity = member[22];
				if (!diabetes.equalsIgnoreCase("NULL")) {
					FamilyDiseaseHistory = diabetes;
				} else if (!hypertension.equalsIgnoreCase("NULL")) {
					FamilyDiseaseHistory = hypertension;
				} else if (!cancer.equalsIgnoreCase("NULL")) {
					FamilyDiseaseHistory = cancer;
				} else if (!respiratoryDisease.equalsIgnoreCase("NULL")) {
					FamilyDiseaseHistory = respiratoryDisease;
				} else if (!phycologicalDisease.equalsIgnoreCase("NULL")) {
					FamilyDiseaseHistory = phycologicalDisease;
				} else if (!obesity.equalsIgnoreCase("NULL")) {
					FamilyDiseaseHistory = obesity;
				} else {
					
				}
				if (!FamilyDiseaseHistory.isEmpty()) {
					client.addAttribute("Family Disease History", FamilyDiseaseHistory);
				}
				
				// address put
				Map<String, String> addressFields = new HashMap<String, String>();
				addressFields.put("cityVillage", member[3]);// upazilla
				addressFields.put("country", "BANGLADESH"); // country
				addressFields.put("address1", member[4]); // union
				addressFields.put("address2", member[5]); // ward
				addressFields.put("address3", null);
				addressFields.put("address4", null);
				addressFields.put("address5", null);
				addressFields.put("address6", null);
				addressFields.put("stateProvince", member[1]); // division
				addressFields.put("countyDistrict", member[2]); // district
				addressFields.put("gps", null + " " + null);
				Address address = new Address();
				address.setAddressFields(addressFields);
				address.setAddressType("usual_residence");
				client.addAddress(address);
				position++;
				System.err.println("Client:" + client.toString());
				
				//clientService.addorUpdate(client);
				
				Event event = new Event();
				event.setServerVersion(System.currentTimeMillis());
				event.setTeam("");
				event.setTeamId("");
				event.setBaseEntityId(baseEntityId);
				event.setDateCreated(new DateTime());
				event.setEventDate(new DateTime());
				event.withProviderId("ftp");
				event.setVersion(System.currentTimeMillis());
				event.withIsSendToOpenMRS("yes").withEventType("Woman Member Registration").withEntityType("ec_woman");
				
				List<Object> values = new ArrayList<Object>();
				values.add("13-09-2018");
				String fieldDataType = "text";
				event.addObs(new Obs("formsubmissionField", fieldDataType, "Date_Of_Reg", "" /*//TODO handle parent*/,
				        values, ""/*comments*/, "Date_Of_Reg"/*formSubmissionField*/));
				//eventService.addorUpdateEvent(event);
				
			}
			
		}
		catch (Exception e) {
			logger.info("Some problem occured, please contact with admin..");
			msg = "Some problem occured, please contact with admin..";
		}
		return msg;
	}
	
	@SuppressWarnings("resource")
	public String addHousehold(File csvFile) throws Exception {
		String msg = "";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		
		int position = 0;
		String[] tags = null;
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				
				String[] member = line.split(cvsSplitBy);
				System.err.println("member:" + member[1] + ",generateID:" + generateID());
				Client client = new Client(null);
				client.addIdentifier("Patient_Identifier", generateID());
				String baseEntityId = UUID.randomUUID().toString().trim();
				client.setBaseEntityId(baseEntityId);
				//member[35]  DOB
				client.withFirstName(member[15]).withLastName("").withGender(member[9]).withBirthdate(new DateTime(), false)
				        .withDeathdate(null, false);
				client.setServerVersion(System.currentTimeMillis());
				/// attribute
				
				client.addAttribute("householdCode", member[6]);
				client.addAttribute("familyIncome", member[28]);
				client.addAttribute("phoneNumber", member[29]);
				
				// address put
				Map<String, String> addressFields = new HashMap<String, String>();
				addressFields.put("cityVillage", member[3]);// upazilla
				addressFields.put("country", "BANGLADESH"); // country
				addressFields.put("address1", member[4]); // union
				addressFields.put("address2", member[5]); // ward
				addressFields.put("address3", null);
				addressFields.put("address4", null);
				addressFields.put("address5", null);
				addressFields.put("address6", null);
				addressFields.put("stateProvince", member[1]); // division
				addressFields.put("countyDistrict", member[2]); // district
				addressFields.put("gps", null + " " + null);
				Address address = new Address();
				address.setAddressFields(addressFields);
				address.setAddressType("usual_residence");
				client.addAddress(address);
				position++;
				System.err.println("Client:" + client.toString());
				
				//clientService.addorUpdate(client);
				
				Event event = new Event();
				event.setServerVersion(System.currentTimeMillis());
				event.setTeam("");
				event.setTeamId("");
				event.setBaseEntityId(baseEntityId);
				event.setDateCreated(new DateTime());
				event.setEventDate(new DateTime());
				event.withProviderId("ftp");
				event.setVersion(System.currentTimeMillis());
				event.withIsSendToOpenMRS("yes").withEventType("Woman Member Registration").withEntityType("ec_woman");
				// drinking water source 
				
				String TubewellRed = member[8];
				String TubewellGreen = member[9];
				String TubewellNotTested = member[10];
				String RainWater = member[11];
				String RiverCanal = member[12];
				String Tap = member[13];
				String Pond = member[15];
				String WaterOthers = member[16];
				String dws = "";
				String dwsConceptId = "";
				if (!TubewellRed.equalsIgnoreCase("NULL")) {
					dws = TubewellRed;
					dwsConceptId = "";
				} else if (!TubewellGreen.equalsIgnoreCase("NULL")) {
					dws = TubewellGreen;
					dwsConceptId = "";
				} else if (!TubewellNotTested.equalsIgnoreCase("NULL")) {
					dws = TubewellNotTested;
					dwsConceptId = "";
				} else if (!RainWater.equalsIgnoreCase("NULL")) {
					dws = RainWater;
					dwsConceptId = "";
				} else if (!RiverCanal.equalsIgnoreCase("NULL")) {
					dws = RiverCanal;
					dwsConceptId = "";
				} else if (!Tap.equalsIgnoreCase("NULL")) {
					dws = Tap;
					dwsConceptId = "";
				} else if (!Pond.equalsIgnoreCase("NULL")) {
					dws = Pond;
					dwsConceptId = "";
				} else if (!WaterOthers.equalsIgnoreCase("NULL")) {
					dws = WaterOthers;
					dwsConceptId = "";
				}
				
				List<Object> values = new ArrayList<Object>();
				values.add(dwsConceptId);
				List<Object> humanReadableValues = new ArrayList<Object>();
				humanReadableValues.add(dws);
				String fieldDataType = "text";
				event.addObs(new Obs("concept", fieldDataType, "163137AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
				        "" /*//TODO handle parent*/, values, humanReadableValues, ""/*comments*/, "source_of_water"/*formSubmissionField*/));
				String latrine = "";
				String Sanitary = member[17];
				String Construction = member[18];
				String UnderConstruction = member[19];
				String OpenArea = member[20];
				String Bush = member[21];
				String LatrineOthers = member[22];
				
				String latrine_value = "";
				String latrine_valueConceptId = "";
				if (!Sanitary.equalsIgnoreCase("NULL")) {
					latrine_value = Sanitary;
					latrine_valueConceptId = "";
				} else if (!Construction.equalsIgnoreCase("NULL")) {
					latrine_value = Construction;
					latrine_valueConceptId = "";
				} else if (!UnderConstruction.equalsIgnoreCase("NULL")) {
					latrine_value = UnderConstruction;
					latrine_valueConceptId = "";
				} else if (!OpenArea.equalsIgnoreCase("NULL")) {
					latrine_value = OpenArea;
					latrine_valueConceptId = "";
				} else if (!Bush.equalsIgnoreCase("NULL")) {
					latrine_value = Bush;
					latrine_valueConceptId = "";
				} else if (!LatrineOthers.equalsIgnoreCase("NULL")) {
					latrine_value = LatrineOthers;
					latrine_valueConceptId = "";
				}
				
				List<Object> latrine_values = new ArrayList<Object>();
				values.add(latrine_valueConceptId);
				List<Object> latrine_humanReadableValues = new ArrayList<Object>();
				humanReadableValues.add(latrine_value);
				String latrine_fieldDataType = "text";
				event.addObs(new Obs("concept", latrine_fieldDataType, "163137AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
				        "" /*//TODO handle parent*/, latrine_values, latrine_humanReadableValues, ""/*comments*/,
				        "latrine_structure"/*formSubmissionField*/));
				
				String LowerMiddleClass = member[23];
				String UpperMiddleClass = member[24];
				String MiddleClass = member[25];
				String Solvent = member[26];
				String Rich = member[27];
				String financial_value = "";
				String financial_valueConceptId = "";
				
				if (!LowerMiddleClass.equalsIgnoreCase("NULL")) {
					financial_value = LowerMiddleClass;
					financial_valueConceptId = "";
				} else if (!UpperMiddleClass.equalsIgnoreCase("NULL")) {
					financial_value = UpperMiddleClass;
					financial_valueConceptId = "";
				} else if (!MiddleClass.equalsIgnoreCase("NULL")) {
					financial_value = MiddleClass;
					financial_valueConceptId = "";
				} else if (!Solvent.equalsIgnoreCase("NULL")) {
					financial_value = Solvent;
					financial_valueConceptId = "";
				} else if (!Rich.equalsIgnoreCase("NULL")) {
					financial_value = Rich;
					financial_valueConceptId = "";
				}
				
				List<Object> financial_values = new ArrayList<Object>();
				values.add(financial_valueConceptId);
				List<Object> financial_humanReadableValues = new ArrayList<Object>();
				humanReadableValues.add(financial_value);
				String financial_fieldDataType = "text";
				event.addObs(new Obs("concept", financial_fieldDataType, "163137AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
				        "" /*//TODO handle parent*/, financial_values, financial_humanReadableValues, ""/*comments*/,
				        "financial_status"/*formSubmissionField*/));
				
				//eventService.addorUpdateEvent(event);
				
			}
			
		}
		catch (Exception e) {
			logger.info("Some problem occured, please contact with admin..");
			msg = "Some problem occured, please contact with admin..";
		}
		return msg;
	}
	
	/*Event addObervation(String fieldType, String fieldDataType, String fieldCode, List<Object> values,
	                    String formSubmissionField, Event event) {
		
		return event.addObs(new Obs(fieldType, fieldDataType, fieldCode, "" //TODO handle parent, values,
		        ""comments, formSubmissionFieldformSubmissionField));
	}*/
	
	public String generateID() throws JSONException {
		String prefix = "ZEIR_ID Generator";
		JSONObject data = new JSONObject();
		data.put("identifierSourceName", prefix);
		
		return post("https://192.168.19.44/openmrs" + "/" + GEN_ID_URL, "", data.toString(), "sohel", "Sohel@123").body();
	}
	
	public static HttpResponse post(String url, String payload, String data, String username, String password) {
		new TurnOffCertificateValidation().ForHTTPSConnections();
		String output = null;
		if (url.endsWith("/")) {
			url = url.substring(0, url.lastIndexOf("/"));
		}
		url = (url + (StringUtils.isEmptyOrWhitespaceOnly(payload) ? "" : ("?" + payload))).replaceAll(" ", "%20");
		try {
			URL urlo = new URL(url);
			HttpURLConnection con = (HttpURLConnection) urlo.openConnection();
			con.setRequestProperty("Content-Type", "application/json");
			String charset = "UTF-8";
			con.setRequestProperty("Accept-Charset", charset);
			String encoded = new String(Base64.encodeBase64((username + ":" + password).getBytes()));
			con.setRequestProperty("Authorization", "Basic " + encoded);
			con.setRequestMethod(HttpMethod.POST.name());
			con.setFixedLengthStreamingMode(data.toString().getBytes().length);
			con.setDoOutput(true);
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(con.getOutputStream(), charset), true); // true = autoFlush, important!
			writer.print(data.toString());
			if (writer != null)
				writer.close();
			int statusCode = con.getResponseCode();
			if (statusCode != HttpURLConnection.HTTP_OK) {
				// throw some exception
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
			StringBuilder sb = new StringBuilder();
			
			while ((output = br.readLine()) != null) {
				sb.append(output);
			}
			//System.out.println(sb.toString());	
			return new HttpResponse(con.getResponseCode() == HttpStatus.SC_OK, sb.toString());
		}
		catch (FileNotFoundException e) {
			return new HttpResponse(true, "");
		}
		catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
