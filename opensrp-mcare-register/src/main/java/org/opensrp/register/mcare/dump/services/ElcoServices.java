package org.opensrp.register.mcare.dump.services;

import org.opensrp.register.mcare.dump.dao.ElcoDAO;
import org.opensrp.register.mcare.dump.entities.ElcoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ElcoServices {
	
	private ElcoDAO elcoDAO;
	public ElcoServices() {
		// TODO Auto-generated constructor stub
	}	
    
	@Autowired
	public void setElcoDAO(ElcoDAO elcoDAO) {
		this.elcoDAO = elcoDAO;
	}

	@Transactional
	public void addElco(ElcoEntity p) {
		elcoDAO.addelco(p);
	 }

}
