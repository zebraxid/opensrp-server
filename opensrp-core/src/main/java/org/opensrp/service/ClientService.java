package org.opensrp.service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.domain.ActivityLog;
import org.opensrp.domain.Address;
import org.opensrp.domain.Client;
import org.opensrp.domain.RelationShip;
import org.opensrp.repository.AllActivityLogs;
import org.opensrp.repository.AllClients;
import org.opensrp.util.DateTimeTypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@Service
public class ClientService {
	private final AllClients allClients;
	private final AllActivityLogs allLogs;
	
	@Autowired
	public ClientService(AllClients allClients, AllActivityLogs allLogs)
	{
		this.allClients = allClients;
		this.allLogs = allLogs;
	}
	
	public Client getByBaseEntityId(String baseEntityId)
	{
		return allClients.findByBaseEntityId(baseEntityId);
	}
	
	public List<Client> findAllClients() {
		return allClients.findAllClients();
	}
	
	public List<Client> findAllByIdentifier(String identifier) {
		return allClients.findAllByIdentifier(identifier);
	}
	
	public List<Client> findAllByAttribute(String attributeType, String attribute, DateTime from, DateTime to) {
		return allClients.findAllByAttribute(attributeType, attribute, from, to);
	}
	
	public List<Client> findAllByAddress(String addressType, String addressField, String value, DateTime from, DateTime to) {
		return allClients.findByAddress(addressType, addressField, value, from, to);
	}
	
	public List<Client> findAllByTimestamp(DateTime from, DateTime to) {
		return allClients.findByTimestamp(from, to);
	}
	
	public List<Client> findByDynamicQuery(String query, String sort, Integer limit, Integer skip) {
		return allClients.findByDynamicQuery(query, sort, limit, skip);
	}
	
	public Client addClient(Client client, String activityCategory)
	{
		if(client.getBaseEntityId() == null){
			throw new RuntimeException("No baseEntityId");
		}
		Client c = findClient(client);
		if(c != null){
			throw new IllegalArgumentException("A client already exists with given list of identifiers. Consider updating data.["+c+"]");
		}
		
		client.setDateCreated(DateTime.now());
		allClients.add(client);
		
		allLogs.add(new ActivityLog("Client Added from Service", client.getId(), Client.class.getName(), client.getBaseEntityId(), "CLIENT_ADD", activityCategory, null, DateTime.now(), null));
		
		return client;
	}
	
	public Client findClient(Client client){
		// find by auto assigned entity id
		Client c = allClients.findByBaseEntityId(client.getBaseEntityId());
		if(c != null){
			return c;
		}
		
		//still not found!! search by generic identifiers
		
		for (String idt : client.getIdentifiers().keySet()) {
			List<Client> cl = allClients.findAllByIdentifier(client.getIdentifier(idt));
			if(cl.size() > 1){
				throw new IllegalArgumentException("Multiple clients with identifier type "+idt+" and ID "+client.getIdentifier(idt)+" exist.");
			}
			else if(cl.size() != 0){
				return cl.get(0); 
			}
		}
		return c;
	}
	
	public Client find(String uniqueId){
		// find by document id
		Client c = allClients.findByBaseEntityId(uniqueId);
		if(c != null){
			return c;
		}
		
		// if not found find if it is in any identifiers TODO refactor it later
		List<Client> cl = allClients.findAllByIdentifier(uniqueId);
		if(cl.size() > 1){
			throw new IllegalArgumentException("Multiple clients with identifier "+uniqueId+" exist.");
		}
		else if(cl.size() != 0){
			return cl.get(0);
		}
		
		return c;
	}
	
	public void updateClient(Client updatedClient, String activityCategory) throws JSONException
	{
		// If update is on original entity
		if(updatedClient.isNew()){
			throw new IllegalArgumentException("Client to be updated is not an existing and persisting domain object. Update database object instead of new pojo");
		}
		
		if(findClient(updatedClient) == null){
			throw new IllegalArgumentException("No client found with given list of identifiers. Consider adding new!");
		}
		
		updatedClient.setDateEdited(DateTime.now());
		allClients.update(updatedClient);
		
		allLogs.add(new ActivityLog("Client Updated from Service", updatedClient.getId(), Client.class.getName(), updatedClient.getBaseEntityId(), "CLIENT_EDITED", activityCategory, null, DateTime.now(), null));
	}
	
	public Client mergeClient(Client updatedClient, String activityCategory) 
	{
		try{
		Client original = findClient(updatedClient);
		if(original == null){
			throw new IllegalArgumentException("No client found with given list of identifiers. Consider adding new!");
		}
		
		Gson gs = new GsonBuilder().registerTypeAdapter(DateTime.class, new DateTimeTypeConverter()).create();
		JSONObject originalJo = new JSONObject(gs.toJson(original));

		JSONObject updatedJo = new JSONObject(gs.toJson(updatedClient));
		List<Field> fn = Arrays.asList(Client.class.getDeclaredFields());

		JSONObject mergedJson = new JSONObject();
		if (originalJo.length() > 0) {
			mergedJson = new JSONObject(originalJo, JSONObject.getNames(originalJo));
		}
		if (updatedJo.length() > 0) {
			for (Field key : fn) {
				String jokey = key.getName();
				if(updatedJo.has(jokey)) mergedJson.put(jokey, updatedJo.get(jokey));
			}
		
			original = gs.fromJson(mergedJson.toString(), Client.class);
			
			for (Address a : updatedClient.getAddresses()) {
				if(original.getAddress(a.getAddressType()) == null) {
					original.addAddress(a);
				}
				else {
					original.removeAddress(a.getAddressType());
					original.addAddress(a);
				}
			}
			for (String k : updatedClient.getIdentifiers().keySet()) {
				original.addIdentifier(k, updatedClient.getIdentifier(k));
			}
			for (String k : updatedClient.getAttributes().keySet()) {
				original.addAttribute(k, updatedClient.getAttribute(k));
			}
			
			for (RelationShip r : updatedClient.getRelationships()) {
				original.addRelationship(r);
			}
		}

		original.setDateEdited(DateTime.now());
		allClients.update(original);
		
		allLogs.add(new ActivityLog("Client Merged from Service", original.getId(), Client.class.getName(), original.getBaseEntityId(), "CLIENT_MERGED", activityCategory, null, DateTime.now(), null));

		return original;
		}
		catch(JSONException e){
			throw new RuntimeException(e);
		}
	}
}
