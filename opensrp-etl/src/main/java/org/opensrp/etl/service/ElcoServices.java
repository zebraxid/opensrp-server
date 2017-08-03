package org.opensrp.etl.service;

import org.opensrp.etl.entity.ElcoEntity;
import org.opensrp.etl.repository.ElcoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ElcoServices {
	
	private ElcoRepository elcoRepository;
	public ElcoServices() {
		// TODO Auto-generated constructor stub
	}	
    
	@Autowired
	public void setElcoRepository(ElcoRepository elcoRepository) {
		this.elcoRepository = elcoRepository;
	}

	@Transactional
	public void addElco(ElcoEntity p) {
		elcoRepository.addelco(p);
	 }

}
