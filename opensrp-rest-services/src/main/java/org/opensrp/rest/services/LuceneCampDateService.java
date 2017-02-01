package org.opensrp.rest.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONObject;
import org.opensrp.rest.register.DTO.CampDateEntryDTO;
import org.opensrp.rest.register.DTO.CommonDTO;
import org.opensrp.rest.repository.LuceneCampDateRepository;
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
public class LuceneCampDateService {

	private LuceneCampDateRepository luceneCampDateRepository;
	
	@Autowired
	private DynamicQueryString dynamicQueryString;
	@Autowired
	public LuceneCampDateService(
			LuceneCampDateRepository luceneCampDateRepository) {
		this.luceneCampDateRepository = luceneCampDateRepository;		
	}

	public CommonDTO<CampDateEntryDTO> getData(MultiValueMap<String, String> queryParameters,int p,int limit) throws JsonParseException, JsonMappingException,
			IOException {
		ObjectMapper mapper = new ObjectMapper();		
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.setVisibilityChecker(VisibilityChecker.Std.defaultInstance()
				.withFieldVisibility(JsonAutoDetect.Visibility.ANY));		
		LuceneResult luceneResult = luceneCampDateRepository
				.getData(dynamicQueryString.makeDynamicQueryAsString(queryParameters), p,limit);
		List<Row> rows = luceneResult.getRows();
		 
		List<CampDateEntryDTO> dataList = new ArrayList<CampDateEntryDTO>();

		for (Row row : rows) {
			LinkedHashMap<String, Object> fields = row.getFields();			
			String jsonString = new JSONObject(fields).toString();
			dataList.add(mapper.readValue(jsonString.getBytes(),
					CampDateEntryDTO.class));
		}
		return new CommonDTO<CampDateEntryDTO>(dataList);
	}

	
	
}
