package org.opensrp.etl.service;

import org.opensrp.etl.entity.ElcoEntity;
import org.opensrp.etl.repository.ElcoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ElcoServices {
	
	private ElcoRepository elcoDAO;
	public ElcoServices() {
		// TODO Auto-generated constructor stub
	}	
    
	@Autowired
	public void setElcoDAO(ElcoRepository elcoDAO) {
		this.elcoDAO = elcoDAO;
	}

	@Transactional
	public void addElco(ElcoEntity p) {
		elcoDAO.addelco(p);
	 }

}
