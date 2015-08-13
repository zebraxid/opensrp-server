package org.opensrp.register.mcare.service;

import org.opensrp.register.mcare.repository.AllHouseHolds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HHRegisterService {
	
	private final AllHouseHolds allHouseHolds;
	
	@Autowired
	public HHRegisterService(AllHouseHolds allHouseHolds)
	{
		this.allHouseHolds = allHouseHolds;
		
	}

	public void getHHRegisterForProvider(String providerId)
	{
		
	}
}
