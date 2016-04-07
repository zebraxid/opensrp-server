package org.opensrp.register.mcare.service;

import java.awt.AlphaComposite;
import java.util.ArrayList;
import java.util.List;

import org.opensrp.dto.AclDTO;
import org.opensrp.dto.PrivilegeDTO;
import org.opensrp.register.mcare.domain.Acl;
import org.opensrp.register.mcare.domain.Privilege;
import org.opensrp.register.mcare.domain.Role;
import org.opensrp.register.mcare.repository.AllAcls;
import org.opensrp.register.mcare.repository.AllPrivileges;
import org.opensrp.register.mcare.repository.AllRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class PrivilegeService {

	private static Logger logger = LoggerFactory.getLogger(PrivilegeService.class);
	private AllPrivileges allPrivileges;
	private AllAcls allAcls;
	
	@Autowired
	//public PrivilegeService(AllRoles allRoles, AllAcls allAcls)
	public PrivilegeService(AllPrivileges allPrivileges)
	{
		this.allPrivileges = allPrivileges;
		//this.allAcls = allAcls;
	}
	
	public String addPrivilege(PrivilegeDTO privilegeDTO)	{		
		//Acl acls = allAcls.findByRoleName(privilegeDTO.getPrivilegeName());
		Privilege existingPrivilege = allPrivileges.privilegeByName(privilegeDTO.getName());
		if (existingPrivilege == null) {
			try{
				Privilege privilege = new Privilege();
				privilege.withName(privilegeDTO.getName());
				privilege.withStatus(privilegeDTO.getStatus() == null ? "Active" : privilegeDTO.getStatus());
				allPrivileges.add(privilege);
				return "1";
			}catch(Exception e){
				return "0";
			}
		}else{
			return "2";
		}
	}
	
	public PrivilegeDTO getPrivilegeByName(String privilegeName)	{		
		//Acl acls = allAcls.findByRoleName(privilegeDTO.getPrivilegeName());
		logger.info("inside service");
		Privilege privilegeByName = allPrivileges.privilegeByName(privilegeName);
		logger.info("returned from repository class");
		if (privilegeByName != null) {
			logger.info("privilege found");
			PrivilegeDTO privilegeDTO = new PrivilegeDTO();
			privilegeDTO.withPrivilegeName(privilegeByName.getName());
			return privilegeDTO;
		}else{
			logger.info("privilege not found");
			return null;
		}
		//return privilegeName;
	}
	
	public ArrayList<PrivilegeDTO> getAllPrivileges(){
		List<Privilege> privileges = allPrivileges.privileges();
		ArrayList<PrivilegeDTO> privilegesDTO = new ArrayList<PrivilegeDTO>();
		for(Privilege privilege:privileges){
			PrivilegeDTO tempDTO = new PrivilegeDTO();
			tempDTO.withPrivilegeName(privilege.getName());
			tempDTO.withPrivilegeStatus(privilege.getStatus());
			privilegesDTO.add(tempDTO);
		}
		return privilegesDTO;
	}
	public String editPrivilege(PrivilegeDTO privilegeDTO) {
		Privilege privilege = allPrivileges.get(privilegeDTO.getId());
		
		try{
			Privilege updatedPrivilege = new Privilege();
			updatedPrivilege.withName(privilegeDTO.getName());
			updatedPrivilege.withStatus(privilegeDTO.getStatus() == null ? privilege.getStatus() : privilegeDTO.getStatus());
			updatedPrivilege.setId(privilegeDTO.getId());
			updatedPrivilege.setRevision(privilege.getRevision());
			allPrivileges.update(updatedPrivilege);
			return "1";
		}catch(Exception e){
			return "0";
		}
		
	}
	
	/*public AclDTO getRoleAndAccessTokens(String userName)
	{
		Role role = allRoles.findByUserName(userName);
		if(role != null){
			Acl acl = allAcls.findByRoleName(role.getRoleName());
			if(acl != null){
				AclDTO aclDTO = new AclDTO()
				.withRoleName(acl.getRoleName())
				.withRoleId(acl.getId())
				.withStatus(acl.getStatus())
				.withAccessTokens(acl.getAccessTokens());
			return aclDTO;				
			}else{
				return null;
			}
			
		}else{
			return null;
		}
	}
	public ArrayList<AclDTO> getRolesAndAccessTokens(){
		List<Acl> acls = allAcls.roles();		//Acl ac = allAcls.get("");
		ArrayList<AclDTO> aclList = new ArrayList<AclDTO>();
		for (Acl acl : acls) {
			AclDTO aclDTO = new AclDTO()
			.withRoleName(acl.getRoleName())
			.withRoleId(acl.getId())
			.withStatus(acl.getStatus())
			.withAccessTokens(acl.getAccessTokens());			
			aclList.add(aclDTO);			
		}		
		return aclList;
	}
	
	public ArrayList<AclDTO> getActiveRolesAndAccessTokens(){
		List<Acl> acls = allAcls.allActiveRoles();		//Acl ac = allAcls.get("");
		ArrayList<AclDTO> aclList = new ArrayList<AclDTO>();
		for (Acl acl : acls) {
			AclDTO aclDTO = new AclDTO()
			.withRoleName(acl.getRoleName())
			.withRoleId(acl.getId())
			.withStatus(acl.getStatus())
			.withAccessTokens(acl.getAccessTokens());			
			aclList.add(aclDTO);			
		}		
		return aclList;
	}
	public AclDTO getRoleAndAccessTokensByName(String roleId){
		Acl acl = allAcls.get(roleId);		
		AclDTO aclDTO = new AclDTO()
		 .withRoleName(acl.getRoleName())
		 .withRoleId(acl.getId())
		 .withStatus(acl.getStatus())
		 .withAccessTokens(acl.getAccessTokens());
		return aclDTO;
	}*/
}
