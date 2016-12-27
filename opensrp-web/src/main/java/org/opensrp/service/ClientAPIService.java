package org.opensrp.service;

import java.util.ArrayList;
import java.util.List;

import org.opensrp.domain.Client;
import org.opensrp.repository.lucene.LuceneClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientAPIService {
	@Autowired
	private LuceneClientRepository luceneClientRepository;
	
	public ClientAPIService(){
		
	}
	public List<HealthId> getHealthIdForAllClient(String providerId,long timeStamp){
		List<HealthId> healthIds = new ArrayList<>();
		List<Client> clients = luceneClientRepository.getclient(providerId, timeStamp);
		
		if (clients == null || clients.isEmpty()) {			
			return null;
		}
		
		for (Client client : clients) {			
			HealthId health_id = new HealthId();
			health_id.setEntityId(client.getBaseEntityId());
			try {
				health_id.setHealthId(client.getAttributes().get("healthId").toString());
            } catch (Exception Ex) {
            	health_id.setHealthId("");
                //System.out.println(Ex);
            }
			
			health_id.setTimeStamp(client.getTimeStamp());
			healthIds.add(health_id);
        }
		return healthIds;		
	}
	
}
