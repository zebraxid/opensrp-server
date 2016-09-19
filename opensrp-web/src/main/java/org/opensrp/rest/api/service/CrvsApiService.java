package org.opensrp.rest.api.service;

import org.opensrp.domain.Client;
import org.opensrp.service.ClientService;
import  org.opensrp.common.AllConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrvsApiService {
	private ClientService clientService;
	
	
	@Autowired
    public void setClientService(ClientService clientService) {
    	this.clientService = clientService;
    }

	public String getEntityId(String birsEventId, String birthRegistrationId){
		Client client = new Client();
		client.addIdentifier(AllConstants.BRISEVENTID, birsEventId);
		
		Client cl = clientService.findClient(client);
		return cl.getBaseEntityId();
		
	}
	
}
