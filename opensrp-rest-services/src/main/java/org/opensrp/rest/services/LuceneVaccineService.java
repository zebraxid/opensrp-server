package org.opensrp.rest.services;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.opensrp.rest.register.DTO.VaccineDTO;
import org.opensrp.rest.register.DTO.VaccineEntryDTO;
import org.opensrp.rest.repository.LuceneVaccineRepository;
import org.opensrp.rest.util.ConvertDateStringToTimestampMills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.github.ldriscoll.ektorplucene.LuceneResult;
import com.github.ldriscoll.ektorplucene.LuceneResult.Row;

@Service
public class LuceneVaccineService {

	private LuceneVaccineRepository luceneVaccineRepository;
	private ConvertDateStringToTimestampMills convertDateStringToTimestampMills;
	@Autowired
	private DynamicQueryString dynamicQueryString;
	@Autowired
	public LuceneVaccineService(
			LuceneVaccineRepository luceneVaccineRepository,ConvertDateStringToTimestampMills convertDateStringToTimestampMills) {
		this.luceneVaccineRepository = luceneVaccineRepository;
		this.convertDateStringToTimestampMills = convertDateStringToTimestampMills;
	}

	public VaccineDTO getVaccine(MultiValueMap<String, String> queryParameters) throws JsonParseException, JsonMappingException,
			IOException {
		ObjectMapper mapper = new ObjectMapper();
		// mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
		// false);
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.setVisibilityChecker(VisibilityChecker.Std.defaultInstance()
				.withFieldVisibility(JsonAutoDetect.Visibility.ANY));
			
		LuceneResult luceneResult = luceneVaccineRepository
				.findDocsByProvider(dynamicQueryString.makeDynamicQueryAsString(queryParameters));
		List<Row> rows = luceneResult.getRows();
		List<VaccineEntryDTO> hhRegisterEntryDTOList = new ArrayList<VaccineEntryDTO>();

		for (Row row : rows) {
			LinkedHashMap<String, Object> fields = row.getFields();
			System.err.println(fields.toString());
			String jsonString = new JSONObject(fields).toString();
			hhRegisterEntryDTOList.add(mapper.readValue(jsonString.getBytes(),
				VaccineEntryDTO.class));
		}
		return new VaccineDTO(hhRegisterEntryDTOList);
	}

	/**
	 * This method return Vaccine count as today, this month or this week
	 * @param start this may be start date of a month or week
	 * @param end   this may be end date of a month or week
	 * */
	public int getVaccineCount(String type,String start,String end){
		if(start.equalsIgnoreCase("")){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	Date today = Calendar.getInstance().getTime();    	
		String makeQueryString ="type:Vaccine" + " AND " + "vaccineName:" + type + " AND " + "timeStamp:["+convertDateStringToTimestampMills.convertDateToTimestampMills(dateFormat.format(today))+" TO "+convertDateStringToTimestampMills.convertDateToTimestampMills(dateFormat.format(today))+"]" ;
    	LuceneResult result = luceneVaccineRepository.findDocsByProvider(makeQueryString);
		return result.getRows().size();
		}else{
			String makeQueryString ="type:Vaccine" + " AND " + "vaccineName:" + type + " AND " + "timeStamp:["+convertDateStringToTimestampMills.convertDateToTimestampMills(start)+" TO "+convertDateStringToTimestampMills.convertDateToTimestampMills(end)+"]" ;
	    	LuceneResult result = luceneVaccineRepository.findDocsByProvider(makeQueryString);
			return result.getRows().size();
		}
		
		
	}
	private Map<String, String> prepareParameters(MultiValueMap<String, String> queryParameters) {

		Map<String, String> parameters = new HashMap<String, String>();

		Iterator<String> it = queryParameters.keySet().iterator();

		while (it.hasNext()) {
			String theKey = (String) it.next();
			parameters.put(theKey, queryParameters.getFirst(theKey));
		}

		return parameters;

	}
	
}
