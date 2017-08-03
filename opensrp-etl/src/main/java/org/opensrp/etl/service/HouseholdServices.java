package org.opensrp.etl.service;

import org.opensrp.etl.entity.HousoholdEntity;
import org.opensrp.etl.repository.HouseholdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HouseholdServices {
	private HouseholdRepository householdRepository;
	public HouseholdServices() {
		// TODO Auto-generated constructor stub
	}
	
    @Autowired
	public void setHouseholdRepository(HouseholdRepository householdRepository) {
		this.householdRepository = householdRepository;
	}


	@Transactional
	public void save(HousoholdEntity p) {		
		householdRepository.addHousehold(p);
	 }

}
