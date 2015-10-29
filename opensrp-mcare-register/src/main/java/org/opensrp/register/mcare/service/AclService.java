package org.opensrp.register.mcare.service;

import org.opensrp.dto.AclDTO;
import org.opensrp.register.mcare.domain.Acl;
import org.opensrp.register.mcare.domain.Role;
import org.opensrp.register.mcare.repository.AllAcls;
import org.opensrp.register.mcare.repository.AllRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AclService {

	private static Logger logger = LoggerFactory.getLogger(AclService.class
			.toString());
	private AllRoles allRoles;
	private AllAcls allAcls;
	
	@Autowired
	public AclService(AllRoles allRoles, AllAcls allAcls)
	{
		this.allRoles = allRoles;
		this.allAcls = allAcls;
	}
	
	public void addAcl(AclDTO aclDTO)
	{
		Acl acl = new Acl();
		acl.withRoleName(aclDTO.getRoleName());
		acl.withAccessTokens(aclDTO.getAccessTokens());
		allAcls.add(acl);
	}
	
	public AclDTO getRoleAndAccessTokens(String userName)
	{
		Role role = allRoles.findByUserName(userName);
		Acl acl = allAcls.findByRoleName(role.getRoleName());
		
		AclDTO aclDTO = new AclDTO()
						.withRoleName(acl.getRoleName())
						.withAccessTokens(acl.getAccessTokens());
		
		return aclDTO;
	}
}
