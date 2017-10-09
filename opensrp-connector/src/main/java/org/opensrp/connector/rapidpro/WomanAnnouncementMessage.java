package org.opensrp.connector.rapidpro;

import org.opensrp.domain.Client;

public class WomanAnnouncementMessage implements Message {
	
	@Override
	public String message(Client client) {
		String message = " APNAR ELAKAR TIKADAN KENDRO EKHON KHOLA ASE " + "member.Member_Fname()"
		        + "  TIKA DEYAR JONNO ASUN";
		
		return message;
		
	}
	
}
