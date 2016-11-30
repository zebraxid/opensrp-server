package org.opensrp.service;

import org.opensrp.camp.dao.CampDate;
import org.opensrp.register.mcare.domain.Members;


public class ChildRemainderMessage implements Message {

	@Override
    public String message(Members member, CampDate campDate) {
	    // TODO Auto-generated method stub
		String message = member.getMem_F_Name() +
				"  ER BACHCHAKE TIKA DEYAR SOMOY HOYESE. " +
				campDate.getSession_date() +
				"  TARIKHE BACHCHAKE " +
				campDate.getSession_name() +
				" -TIKADAN KENDRE NIYE ASUN";
		return message;
    }

	
	
}
