package org.opensrp.etl.service;

import org.opensrp.etl.entity.HousoholdEntity;
import org.opensrp.etl.repository.HouseholdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HouseholdServices {
	private HouseholdRepository householdDAO;
	public HouseholdServices() {
		// TODO Auto-generated constructor stub
	}
	@Autowired
	 public void setHouseholdDAO(HouseholdRepository householdDAO) {
		this.householdDAO = householdDAO;
	}

	@Transactional
	public void addHousehold(HousoholdEntity p) {		
        householdDAO.addHousehold(p);
	 }

}
