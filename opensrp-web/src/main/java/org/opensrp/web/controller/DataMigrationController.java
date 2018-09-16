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
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpStatus;

import org.opensrp.common.util.HttpResponse;
import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.common.util.TurnOffCertificateValidation;
import org.opensrp.domain.Address;
import org.opensrp.domain.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
		
		return new ModelAndView("redirect:/data/migration.html");
	}
	
	public class Location {
		
	}
	
	@SuppressWarnings("resource")
	public String uploadLocation(File csvFile) throws Exception {
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
				Client client = new Client(null);
				
				client.addIdentifier("Patient_Identifier", generateID());
				//member[35]  DOB
				client.withFirstName(member[15]).withLastName("").withGender(member[9]).withBirthdate(new DateTime(), false)
				        .withDeathdate(null, false);
				
				/// attribute
				client.addAttribute("MaritalStatus", member[10]);
				client.addAttribute("education", member[11]);
				client.addAttribute("occupation", member[12]);
				client.addAttribute("Religion", member[13]);
				client.addAttribute("Religion", member[13]);
				client.addAttribute("nationalId", member[16]);
				client.addAttribute("nationalId", member[16]);
				client.addAttribute("nationalId", member[16]);
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
			}
			
		}
		catch (Exception e) {
			logger.info("Some problem occured, please contact with admin..");
			msg = "Some problem occured, please contact with admin..";
		}
		return msg;
	}
	
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
