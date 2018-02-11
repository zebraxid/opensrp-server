package org.opensrp.connector.rapidpro;

import java.util.Map;

import org.opensrp.domain.Camp;
import org.opensrp.domain.Client;

public class ChildAnnouncementMessage implements Message {
	
	@Override
	public String message(Client client, Camp camp, Map<String, String> data) {
		String message = camp.getDate() + " tarik " + camp.getCenterName()
		        + " te Shishu O Mohilader tika nebar jonno bela 9ta theke 2tar moddhe asar jonno onurodh kora holo";
		return message;
		
	}
	
}
