package org.opensrp.register.mcare.dump.services;

import org.opensrp.register.mcare.dump.dao.HouseholdDAO;
import org.opensrp.register.mcare.dump.entities.HousoholdEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HouseholdServices {
	private HouseholdDAO householdDAO;
	public HouseholdServices() {
		// TODO Auto-generated constructor stub
	}
	@Autowired
	 public void setHouseholdDAO(HouseholdDAO householdDAO) {
		this.householdDAO = householdDAO;
	}

	@Transactional
	public void addHousehold(HousoholdEntity p) {
		System.err.println("householdDAO:"+householdDAO);
		 householdDAO.addHousehold(p);
	 }

}
