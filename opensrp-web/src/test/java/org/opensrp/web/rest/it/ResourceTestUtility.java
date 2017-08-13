package org.opensrp.web.rest.it;

import org.opensrp.domain.Client;
import org.opensrp.repository.AllClients;

import java.util.List;

public final class ResourceTestUtility {

	private ResourceTestUtility() {
	}


	public static void createClient(List<Client> allClient, AllClients allClients) {
		for (Client client : allClient) {
			allClients.add(client);
		}
	}
}
