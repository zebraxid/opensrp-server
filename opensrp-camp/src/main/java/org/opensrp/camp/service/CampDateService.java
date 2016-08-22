package org.opensrp.camp.service;


import java.util.Iterator;
import java.util.List;

import org.opensrp.camp.dao.CampDate;
import org.opensrp.camp.repository.CampDateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class CampDateService {
	private static Logger logger = LoggerFactory.getLogger(CampDateService.class);
	private CampDateRepository campDateRepository;	
    
    public CampDateService() {
	    
    }
	@Autowired
    public void setCampDateRepository(CampDateRepository campDateRepository) {
    	this.campDateRepository = campDateRepository;
    }
	
	public List<CampDate> getCampDateBySessionId(String session_id){
		List<CampDate> campDates = campDateRepository.findBySessionId(session_id);
		Gson gson = new Gson();
	     // convert your list to json
	     String jsonCartList = gson.toJson(campDates);
		
		logger.info("Camp : "+ jsonCartList.toString());
		return campDates;
		
	}
	
	
}
