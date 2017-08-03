package org.opensrp.etl.service;

import org.opensrp.etl.entity.MarkerEntity;
import org.opensrp.etl.repository.MarkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MarkerService {
	
	private MarkerRepository markerRepository;
	public MarkerService() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	public void setMarkerRepository(MarkerRepository markerRepository) {
		this.markerRepository = markerRepository;
	}
	
	@Transactional
	public void save(MarkerEntity markerEntity) {		
		markerRepository.save(markerEntity);
	 }
	
	@Transactional
	public void update(MarkerEntity markerEntity){
		markerRepository.update(markerEntity);
		
	}
	
	@Transactional
	public MarkerEntity getMarkerByNameAndType(String name,String type){
		return markerRepository.getMarkerByNameAndType(name,type);
	}

}
