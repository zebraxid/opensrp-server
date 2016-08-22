package org.opensrp.camp.service;

import java.util.List;

import org.opensrp.camp.dao.Camp;
import org.opensrp.camp.dao.CampDate;
import org.opensrp.camp.dto.CampDateDTO;
import org.opensrp.camp.interfaces.CampInterface;
import org.opensrp.camp.repository.CampDateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class CampDateService implements CampInterface<CampDateDTO> {
	
	private static Logger logger = LoggerFactory.getLogger(CampDateService.class);
	
	private CampDateRepository campDateRepository;
	
	public CampDateService() {
		
	}
	
	@Autowired
	public void setCampDateRepository(CampDateRepository campDateRepository) {
		this.campDateRepository = campDateRepository;
	}
	
	public List<CampDate> getCampDateBySessionId(String session_id) {
		List<CampDate> campDates = campDateRepository.findBySessionId(session_id);
		Gson gson = new Gson();
		// convert your list to json
		String jsonCartList = gson.toJson(campDates);
		
		logger.info("Camp : " + jsonCartList.toString());
		return campDates;
		
	}
	
	@Override
	public String add(CampDateDTO object) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<CampDateDTO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String delete(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public CampDateDTO findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	public CampDate addCampDate(CampDateDTO campDateDTO,Camp camp){
		CampDate campDate = new CampDate();
		campDate.setDate(campDateDTO.getDate());
		campDate.setSession_id(camp.getId());
		campDate.setContact(camp.getContact());
		campDate.setUsername(camp.getUsername());
		campDate.setStatus(campDateDTO.getStatus());
		return campDate;
		
	}

	@Override
    public String edit(CampDateDTO onject) {
	    // TODO Auto-generated method stub
	    return null;
    }
	
}
