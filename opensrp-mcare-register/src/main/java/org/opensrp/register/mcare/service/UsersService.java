package org.opensrp.register.mcare.service;

import java.util.ArrayList;
import java.util.List;

import org.opensrp.dto.AclDTO;
import org.opensrp.dto.LocationDTO;
import org.opensrp.dto.PrivilegeDTO;
import org.opensrp.dto.RoleDTO;
import org.opensrp.dto.UserDTO;
import org.opensrp.register.mcare.domain.Acl;
import org.opensrp.register.mcare.domain.Privilege;
import org.opensrp.register.mcare.domain.Role;
import org.opensrp.register.mcare.domain.SimplifiedLocation;
import org.opensrp.register.mcare.domain.SimplifiedRole;
import org.opensrp.register.mcare.domain.SimplifiedUser;
import org.opensrp.register.mcare.domain.User;
import org.opensrp.register.mcare.domain.SimplifiedPrivilege;
import org.opensrp.register.mcare.repository.AllUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class UsersService {

	private static Logger logger = LoggerFactory.getLogger(UsersService.class);
	private AllUser allUsers;
	private RoleService roleService;
	
	@Autowired
	public UsersService(AllUser allUsers, RoleService roleService)
	{
		this.allUsers = allUsers;
		this.roleService = roleService;
	}
	public List<String> getPrivilegesOfAUser(String userName){
		User userByUserName = allUsers.findUserByUserName(userName);
		if(userByUserName == null && userByUserName.getRoles() == null && userByUserName.getRoles().size() < 1)
			return null;
		else{
			List<String> privileges = new ArrayList<String>();
			for(int i = 0; i< userByUserName.getRoles().size(); i++){
				logger.info("entered for role - " + userByUserName.getRoles().get(i).getName());
				List<String> roleSpecificPrivileges = roleService.getPrivilegesOfASpecificRole(userByUserName.getRoles().get(i).getId());
				if(roleSpecificPrivileges != null){
					for(int j = 0; j<roleSpecificPrivileges.size(); j++){
						privileges.add(roleSpecificPrivileges.get(j));
					}
				}
			}
			logger.info("privilege list constructed");
			return privileges;
		}
	}
	
	public String addUser(UserDTO userDTO)
	{		
		logger.info("inside UserService.addUser");
		User userByUserName = allUsers.findUserByUserName(userDTO.getUserName());
		if (userByUserName == null) {
			logger.info("No Such User with given userName Found");
			try{
				User user = new User();
				//role.withUserName(roleDTO.getUserName());
				user.withStatus(userDTO.getStatus());
				user.withGivenName(userDTO.getGivenName());
				user.withFamilyName(userDTO.getFamilyName());
				user.withMiddleName(userDTO.getMiddleName());
				user.withContactNumber(userDTO.getContactNumber());
				user.withPersonalAddress(userDTO.getPersonalAddress());
				user.withEmail(userDTO.getEmail());
				user.withPassword(userDTO.getPassword());
				user.withUserName(userDTO.getUserName());
				user.withGender(userDTO.getGender());
				//parent, children, roles, locations
				UserDTO parentDTO =  userDTO.getParent();
				List<UserDTO> childrenDTOs = userDTO.getChildren();
				List<RoleDTO> roleDTOs = userDTO.getRoles();
				List<LocationDTO> locationDTOs = userDTO.getLocation();
				//
				SimplifiedUser parent= null;  
				if(parentDTO != null){
					parent = new SimplifiedUser();
					parent.withUsername(parentDTO.getUserName());
					parent.withId(parentDTO.getId());
					user.withParent(parent);
					logger.info("parent added in user.");
				}				
				List<SimplifiedUser> children = null;
				if(childrenDTOs != null && childrenDTOs.size() > 0){
					children = new ArrayList<SimplifiedUser>();
					for(int i =0 ; i < childrenDTOs.size(); i++){
						SimplifiedUser tempUser = new SimplifiedUser();
						tempUser.withUsername(childrenDTOs.get(i).getUserName());
						tempUser.withId(childrenDTOs.get(i).getId());
						children.add(tempUser);
					}
					user.withChildren(children);
					logger.info("children added in user.");
				}
				List<SimplifiedRole> roles =  null;
				if(roleDTOs != null && roleDTOs.size() > 0){
					roles = new ArrayList<SimplifiedRole>();
					for(int i =0 ; i < roleDTOs.size(); i++){
						SimplifiedRole tempRole = new SimplifiedRole();
						tempRole.withName(roleDTOs.get(i).getName());
						tempRole.withId(roleDTOs.get(i).getRoleId());
						roles.add(tempRole);
					}
					user.withRoles(roles);
					logger.info("roles added in user.");
				}
				List<SimplifiedLocation> locations = null;
				if(locationDTOs != null && locationDTOs.size() > 0){
					locations = new ArrayList<SimplifiedLocation>();
					for(int i = 0; i < locationDTOs.size(); i++){
						SimplifiedLocation tempLocation = new SimplifiedLocation();
						tempLocation.withName(locationDTOs.get(i).getName());
						tempLocation.withId(locationDTOs.get(i).getId());
						locations.add(tempLocation);
						//logger.info("location name - " + tempLocation.getName() + " - " + tempLocation.getId());
					}
					user.withLocation(locations);
					logger.info("loactions added in user.");
				}
				allUsers.add(user);
				return "1";
			}catch(Exception e){
				return "0";
			}
		}else{
			logger.info("User with given userName already exists.");
			return "2";
		}
	}
	
	/*public String editRole(RoleDTO roleDTO) {		
		Role roles = allRoles.get(roleDTO.getRoleId() );  // null checking isn't done here, should be done rigorously on client-side
		
		if(roles != null){
			try{
				Role role = new Role();
				role.withName(roleDTO.getName());
				role.setId(roleDTO.getRoleId());
				role.setRevision(roles.getRevision());			
				//role.withUserName(roleDTO.getUserName());
				role.withStatus(roleDTO.getStatus());
				List<PrivilegeDTO> privilegeDTOs = roleDTO.getPrivileges();
				List<SimplifiedPrivilege> privileges = new ArrayList<SimplifiedPrivilege>();
				for(int i = 0; i < privilegeDTOs.size(); i++){					
					SimplifiedPrivilege tempSimplifiedPrivilege = new SimplifiedPrivilege();
					tempSimplifiedPrivilege.withName(privilegeDTOs.get(i).getName() == null ? "" : privilegeDTOs.get(i).getName());
					tempSimplifiedPrivilege.withId(privilegeDTOs.get(i).getId() == null ? "" : privilegeDTOs.get(i).getId());
					privileges.add(tempSimplifiedPrivilege);
					logger.info("privilege with name - " +  tempSimplifiedPrivilege.getName() + "added in list");
				}				
				role.withPrivileges(privileges);
				allRoles.update(role);
				return "1";
			}catch(Exception e){
				return "0";
			}			
		}
		else{
			return "2";
		}		
	}
	
	public ArrayList<RoleDTO> getRolesAndUser(){
		List<Role> roles = allRoles.roles();		
		ArrayList<RoleDTO> roleList = new ArrayList<RoleDTO>();
		for (Role role : roles) {
			RoleDTO roleDTO = new RoleDTO()
			.withName(role.getName())
			.withRoleId(role.getId())
			.withStatus(role.getStatus());
			//.withUserName(role.getUserName());			
			roleList.add(roleDTO);			
		}		
		return roleList;
	}
	
	public RoleDTO getRoleByName(String roleName){
		Role role = allRoles.findRoleByName(roleName);
		if(role != null){
			RoleDTO roleDTO = new RoleDTO();
			roleDTO.withName(role.getName());
			roleDTO.withStatus(role.getStatus());
			//roleDTO.wi
			//covert privileges to privilegeDTOs
			return roleDTO;
		}
		else
			return null;
	}*/
}
