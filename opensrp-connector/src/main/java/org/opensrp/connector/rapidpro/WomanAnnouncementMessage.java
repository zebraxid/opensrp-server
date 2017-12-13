package org.opensrp.connector.rapidpro;

import java.util.Map;

import org.opensrp.domain.Camp;
import org.opensrp.domain.Client;

public class WomanAnnouncementMessage implements Message {
	
	@Override
	public String message(Client client, Camp camp, Map<String, String> data) {
		String message = " APNAR ELAKAR TIKADAN KENDRO EKHON KHOLA ASE " + client.fullName() + "  TIKA DEYAR JONNO ASUN";
		
		return message;
		
	}
	
}
