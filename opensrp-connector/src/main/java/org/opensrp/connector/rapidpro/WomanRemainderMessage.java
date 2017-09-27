package org.opensrp.connector.rapidpro;

import org.opensrp.domain.Client;

public class WomanRemainderMessage implements Message {
	
	@Override
	public String message(Client client) {
		// TODO Auto-generated method stub
		String message = "  AGAMIKAL " + "campDate.getSession_date()"
		        + "  APNAR ELAKAY TIKADAN KENDRO BOSHBE. SOMOY MOTO TT TIKA PETE " + "campDate.getSession_name()"
		        + " KENDRE CHOLE ASUN";
		return message;
	}
	
}
