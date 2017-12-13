package org.opensrp.connector.rapidpro;

import java.util.Map;

import org.opensrp.domain.Camp;
import org.opensrp.domain.Client;

public class ChildRemainderMessage implements Message {
	
	@Override
	public String message(Client client, Camp camp, Map<String, String> data) {
		// TODO Auto-generated method stub
		String message = client.fullName() + "  ER BACHCHAKE TIKA DEYAR SOMOY HOYESE. " + camp.getDate() + "  TARIKHE  "
		        + data.get("scheduleName") + " DEYAR JONNO BACHCHAKE  TIKADAN KENDRE NIYE ASUN";
		return message;
	}
	
}
