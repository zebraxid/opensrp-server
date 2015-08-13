package org.opensrp.register.mcare.service;

import org.opensrp.register.mcare.repository.AllElcos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ELCORegisterService {
	
	private final AllElcos allElcos;
	
	@Autowired
	public ELCORegisterService(AllElcos allElcos)
	{
		this.allElcos = allElcos;
	}

	public void getELCORegisterForProvider(String providerId)
	{
		
	}
}
