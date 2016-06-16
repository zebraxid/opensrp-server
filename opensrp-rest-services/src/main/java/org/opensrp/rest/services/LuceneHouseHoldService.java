package org.opensrp.rest.services;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONObject;
import org.opensrp.dto.register.HHRegisterDTO;
import org.opensrp.dto.register.HHRegisterEntryDTO;
import org.opensrp.register.mcare.repository.AllPrivileges;
import org.opensrp.rest.repository.LuceneHouseHoldRepository;
import org.opensrp.rest.util.ConvertDateStringToTimestampMills;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.github.ldriscoll.ektorplucene.LuceneResult;
import com.github.ldriscoll.ektorplucene.LuceneResult.Row;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@Service
public class LuceneHouseHoldService {
	private static Logger logger = LoggerFactory.getLogger(LuceneHouseHoldService.class);

	private LuceneHouseHoldRepository luceneHouseHoldRepository;
	private ConvertDateStringToTimestampMills convertDateStringToTimestampMills;
	@Autowired
	public LuceneHouseHoldService(
			LuceneHouseHoldRepository luceneHouseHoldRepository,ConvertDateStringToTimestampMills convertDateStringToTimestampMills) {
		this.luceneHouseHoldRepository = luceneHouseHoldRepository;
		this.convertDateStringToTimestampMills = convertDateStringToTimestampMills;
	}

	public HHRegisterDTO findLuceneResult(MultiValueMap<String, String> queryParameters) throws JsonParseException, JsonMappingException,
			IOException {
		ObjectMapper mapper = new ObjectMapper();
		// mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
		// false);
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.setVisibilityChecker(VisibilityChecker.Std.defaultInstance()
				.withFieldVisibility(JsonAutoDetect.Visibility.ANY));
		Map<String, String> preparedParameters = prepareParameters(queryParameters);
		String makeQueryString = "";
		int paramCounter = 1;
		for(Entry<String, String> entry : preparedParameters.entrySet())
		{
			makeQueryString+=entry.getKey()+":"+entry.getValue();
			
			if(preparedParameters.size()>paramCounter)
				makeQueryString+=" AND ";
			
			paramCounter++;
		}
		
		LuceneResult luceneResult = luceneHouseHoldRepository
				.findDocsByProvider(makeQueryString);
		List<Row> rows = luceneResult.getRows();
		List<HHRegisterEntryDTO> hhRegisterEntryDTOList = new ArrayList<HHRegisterEntryDTO>();

		for (Row row : rows) {
			LinkedHashMap<String, Object> fields = row.getFields();
			String jsonString = new JSONObject(fields).toString();
			hhRegisterEntryDTOList.add(mapper.readValue(jsonString.getBytes(),
					HHRegisterEntryDTO.class));
		}
		return new HHRegisterDTO(hhRegisterEntryDTOList);
	}

	/**
	 * This method return Household count as today, this month or this week
	 * @param start this may be start date of a month or week
	 * @param end   this may be end date of a month or week
	 * */
	public int getHouseholdCount(String start,String end){
		if(start.equalsIgnoreCase("")){
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    	Date today = Calendar.getInstance().getTime();    	
			String makeQueryString ="type:Household" + " AND "+ "SUBMISSIONDATE:["+convertDateStringToTimestampMills.convertDateToTimestampMills(dateFormat.format(today))+" TO "+convertDateStringToTimestampMills.convertDateToTimestampMills(dateFormat.format(today))+"]" ;
	    	LuceneResult result = luceneHouseHoldRepository.findDocsByProvider(makeQueryString);
			return result.getTotalRows();
		}else{
			String makeQueryString ="type:Household" + " AND " + "SUBMISSIONDATE:["+convertDateStringToTimestampMills.convertDateToTimestampMills(start)+" TO "+convertDateStringToTimestampMills.convertDateToTimestampMills(end)+"]" ;
	    	LuceneResult result = luceneHouseHoldRepository.findDocsByProvider(makeQueryString);
			return result.getTotalRows();//.size();
		}	
	}
	
	public int getHouseholdCountForChart(String start,String end){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	//Date today = Calendar.getInstance().getTime();    	
		String makeQueryString ="type:Household" + " AND "+ "SUBMISSIONDATE:["+convertDateStringToTimestampMills.convertDateToTimestampMills(start)+" TO "+convertDateStringToTimestampMills.convertDateToTimestampMills(end)+"]" ;
    	LuceneResult result = luceneHouseHoldRepository.findDocsByProvider(makeQueryString);
    	int size = result.getRows().size();//getTotalRows();
    	logger.info("start- " + start + " -end- " + end + " -size- " + size);
		return size;
		/*logger.info("here couch lucene will be used.");
		return 1;*/
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
	
	public void someFunc(){
		logger.info("simple function call");
	}
	
	
}
