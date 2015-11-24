package org.opensrp.register.mcare.service;

import org.opensrp.dto.RoleDTO;
import org.opensrp.register.mcare.domain.Role;
import org.opensrp.register.mcare.repository.AllRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

	private static Logger logger = LoggerFactory.getLogger(RoleService.class
			.toString());
	private AllRoles allRoles;
	
	@Autowired
	public RoleService(AllRoles allRoles)
	{
		this.allRoles = allRoles;
	}
	
	public String addRole(RoleDTO roleDTO)
	{
		Role roleByUserName = allRoles.findByUserName(roleDTO.getUserName());
		if (roleByUserName == null) {
			try{
				Role role = new Role();
				role.withUserName(roleDTO.getUserName());
				role.withRoleName(roleDTO.getRoleName());				
				allRoles.add(role);
				return "1";
			}catch(Exception e){
				return "0";
			}
		}else{
			return "2";
		}
	}
}
