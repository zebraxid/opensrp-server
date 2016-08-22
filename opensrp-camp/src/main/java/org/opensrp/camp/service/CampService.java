package org.opensrp.camp.service;

import java.util.Date;
import java.util.List;

import org.opensrp.camp.dao.Camp;
import org.opensrp.camp.dto.CampDTO;
import org.opensrp.camp.repository.CampRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class CampService {
	private static Logger logger = LoggerFactory.getLogger(CampService.class);
	private CampRepository campRepository;	
    
    public CampService() {
	    
    }
    @Autowired
    public void setCampRepository(CampRepository campRepository) {
    	this.campRepository = campRepository;
    }
    
	public String add(CampDTO campDTO){ 
		Camp camp = new Camp();
    	camp.setUsername(campDTO.getUsername());
    	camp.setSession_name(campDTO.getSession_name());
    	camp.setSession_location(campDTO.getSession_location());
    	camp.setContact(campDTO.getContact());
    	camp.setCreated(new Date().toString());
    	camp.setTotal_adolescent(campDTO.getTotal_adolescent());
    	camp.setTotal_child0(campDTO.getTotal_child0());
    	camp.setTotal_child1(campDTO.getTotal_child1());
    	camp.setTotal_child2(campDTO.getTotal_child2());
    	camp.setTotal_hh(campDTO.getTotal_hh());
    	camp.setUser(campDTO.getUser());
    	camp.setTotal_women(campDTO.getTotal_women());
    	camp.setTotal_population(campDTO.getTotal_population());
    	
    	
    	
    	try{
    		campRepository.add(camp);
    	}catch(Exception e){
    		logger.debug("Message: "+e);
    	}
    	
		return camp.getId();
    	
    }
    
	public List<Camp> getCampById(String id){
		List<Camp> campDates =  campRepository.findById(id);
		Gson gson = new Gson();
	     // convert your list to json
	     String jsonCartList = gson.toJson(campDates);
		
		logger.info("Camp : "+ jsonCartList.toString());
		return campDates;
		
	}
	
	
}
