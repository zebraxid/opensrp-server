package org.opensrp.register.mcare.service;

import java.util.ArrayList;
import java.util.List;

import org.opensrp.register.mcare.HHRegisterEntry;
import org.opensrp.register.mcare.domain.HouseHold;
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
		ArrayList<HHRegisterEntry> hhRegisterEntries = new ArrayList<>();
        List<HouseHold> hhs = allHouseHolds.allOpenHHsForProvider(providerId);
        
	}
}
