package org.opensrp.service;

import org.opensrp.camp.dao.CampDate;
import org.opensrp.register.mcare.domain.Members;


public class ChildAnnouncementMessage implements Message {

	@Override
    public String message(Members member, CampDate campDate) {
		String message = " APNAR ELAKAR TIKADAN KENDRO EKHON KHOLA ASE " +
				member.getMem_F_Name() +
				"  -ER BACHCHAKE TIKA DEYAR JONNO NIYE ASUN" ;
				
		return message;
		
    }

	
	
}
