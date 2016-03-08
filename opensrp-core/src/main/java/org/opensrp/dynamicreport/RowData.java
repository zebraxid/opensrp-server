package org.opensrp.dynamicreport;

import java.util.HashMap;

import org.opensrp.domain.Client;

public class RowData {
	
	private Client client;
	private HashMap<String,String> details;
	
	public RowData(Client client, HashMap<String, String> details) {
		this.client = client;
		this.details = details;
	}

	public Client getClient() {
		return client;
	}

	public HashMap<String, String> getDetails() {
		return details;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public void setDetails(HashMap<String, String> details) {
		this.details = details;
	}
	
	
	
	
	
	
}
