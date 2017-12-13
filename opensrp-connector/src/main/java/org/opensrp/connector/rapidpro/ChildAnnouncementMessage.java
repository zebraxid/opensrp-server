package org.opensrp.connector.rapidpro;

import java.util.Map;

import org.opensrp.domain.Camp;
import org.opensrp.domain.Client;

public class ChildAnnouncementMessage implements Message {
	
	@Override
	public String message(Client client, Camp camp, Map<String, String> data) {
		String message = " APNAR ELAKAR TIKADAN KENDRO EKHON KHOLA ASE " + client.fullName() + " -ER BACHCHAKE  "
		        + data.get("scheduleName") + " TIKA DEYAR JONNO NIYE ASUN";
		
		return message;
		
	}
	
}
