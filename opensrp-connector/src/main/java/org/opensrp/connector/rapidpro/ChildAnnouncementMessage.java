package org.opensrp.connector.rapidpro;

import org.opensrp.domain.Client;

public class ChildAnnouncementMessage implements Message {
	
	@Override
	public String message(Client client) {
		String message = " APNAR ELAKAR TIKADAN KENDRO EKHON KHOLA ASE " + "member.Child_mother_name()"
		        + "  -ER BACHCHAKE TIKA DEYAR JONNO NIYE ASUN";
		
		return message;
		
	}
	
}
