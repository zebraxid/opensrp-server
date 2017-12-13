package org.opensrp.connector.rapidpro;

import java.util.Map;

import org.opensrp.domain.Camp;
import org.opensrp.domain.Client;

public class WomanRemainderMessage implements Message {
	
	@Override
	public String message(Client client, Camp camp, Map<String, String> data) {
		// TODO Auto-generated method stub
		String message = "  AGAMIKAL " + camp.getDate() + "  APNAR ELAKAY TIKADAN KENDRO BOSHBE. SOMOY MOTO TT TIKA PETE "
		        + camp.getCampName() + " KENDRE CHOLE ASUN";
		return message;
	}
	
}
