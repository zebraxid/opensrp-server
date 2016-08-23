package org.opensrp.camp.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.xml.ws.Action;

import org.opensrp.camp.dao.Camp;
import org.opensrp.camp.dao.CampDate;
import org.opensrp.camp.dto.CampDTO;
import org.opensrp.camp.dto.CampDateDTO;
import org.opensrp.camp.interfaces.CampInterface;
import org.opensrp.camp.repository.CampDateRepository;
import org.opensrp.camp.repository.CampRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class CampService implements CampInterface<CampDTO> {
	
	private static Logger logger = LoggerFactory.getLogger(CampService.class);
	
	private CampRepository campRepository;	
	private CampDateRepository campDateRepository;
	private CampDateService campDateService;
	public CampService() {
		
	}
	@Autowired
    public void setCampDateService(CampDateService campDateService) {
    	this.campDateService = campDateService;
    }

	@Autowired
	public void setCampDateRepository(CampDateRepository campDateRepository) {
		this.campDateRepository = campDateRepository;
	}
	
	@Autowired
	public void setCampRepository(CampRepository campRepository) {
		this.campRepository = campRepository;
	}
	
	public String getCampById(String id) {
		logger.info("campRepository: "+campRepository);
		List<Camp> campDates =  campRepository.findById(id);
		Gson gson = new Gson();		
		String jsonCartList = gson.toJson(campDates);		
		logger.info("Camp : " + jsonCartList.toString());
		return jsonCartList.toString();
	}
	
	@Override
	public String add(CampDTO campDTO) {		
		Camp camp = addCamp(campDTO);
		List<CampDateDTO> campDateDTOs = (List<CampDateDTO>) campDTO.getCamp_dates();		
		try {
			campRepository.add(camp);
			if (camp.getId() != null) {
				for (CampDateDTO campDateDTO : campDateDTOs) {
					CampDate campDate = campDateService.addCampDate(campDateDTO, camp);					
					campDateRepository.add(campDate);
				}
			}
		}
		catch (Exception e) {
			logger.debug("Message: " + e);
		}		
		return camp.getId();
	}
	
	@Override
	public List<CampDTO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String delete(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public CampDTO findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Camp addCamp(CampDTO campDTO){
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
		return camp;		
	}
	@Override
    public String edit(CampDTO onject) {
	    // TODO Auto-generated method stub
	    return null;
    }
	
}
