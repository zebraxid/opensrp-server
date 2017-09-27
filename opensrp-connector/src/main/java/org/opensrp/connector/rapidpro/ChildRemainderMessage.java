package org.opensrp.connector.rapidpro;

import org.opensrp.domain.Client;

public class ChildRemainderMessage implements Message {
	
	@Override
	public String message(Client client) {
		// TODO Auto-generated method stub
		String message = client.fullName() + "  ER BACHCHAKE TIKA DEYAR SOMOY HOYESE. " + "campDate.getSession_date()"
		        + "  TARIKHE BACHCHAKE  TIKADAN KENDRE NIYE ASUN";
		return message;
	}
	
}
