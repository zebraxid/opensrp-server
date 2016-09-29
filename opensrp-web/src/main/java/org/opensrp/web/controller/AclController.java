package org.opensrp.web.controller;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.opensrp.connector.openmrs.service.OpenmrsUserService;
import org.opensrp.dashboard.dto.PrivilegeDTO;
import org.opensrp.dashboard.dto.RoleDTO;
import org.opensrp.dashboard.dto.UserDTO;
import org.opensrp.dashboard.service.PrivilegeService;
import org.opensrp.dashboard.service.RoleService;
import org.opensrp.dashboard.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

@Controller
public class AclController {
	
	private RoleService roleService;
	
	private OpenmrsUserService openmrsUserService;
	
	private PrivilegeService privilegeService;
	
	private UsersService userService;
	
	private static Logger logger = LoggerFactory.getLogger(AclController.class);
	
	@Autowired
	public AclController(RoleService roleService, OpenmrsUserService openmrsUserService, PrivilegeService privilegeService,
	    UsersService userService) {
		this.roleService = roleService;
		this.openmrsUserService = openmrsUserService;
		this.privilegeService = privilegeService;
		this.userService = userService;
	}
	
	@RequestMapping(method = GET, value = "/all-user-name")
	@ResponseBody
	public ResponseEntity<String> getAllUserName() throws JSONException {
		return new ResponseEntity<>(new Gson().toJson(openmrsUserService.getAllUsers()), OK);
	}
	
	// new one
	@RequestMapping(method = GET, value = "/role-access-token")
	@ResponseBody
	public List<String> getPrivileges(@RequestParam String userName) {
		return userService.getPrivilegesOfAUser(userName);
	}
	
	@RequestMapping(headers = { "Accept=application/json" }, method = POST, value = "/add-privilege")
	public ResponseEntity<String> addPrivilege(@RequestBody PrivilegeDTO privilegeDTO) {
		logger.info("request received for creating privilege with name- " + privilegeDTO.getName());
		String message = privilegeService.addPrivilege(privilegeDTO);
		//return new ResponseEntity<>(message,OK);
		return new ResponseEntity<>(message, OK);
		//return "1";
	}
	
	@RequestMapping(method = GET, value = "/privilege-by-name")
	@ResponseBody
	public PrivilegeDTO getPrivilegeByName(@RequestParam String privilegeName) {
		logger.info("requeset reached with - " + privilegeName);
		return privilegeService.getPrivilegeByName(privilegeName);
	}
	
	@RequestMapping(method = GET, value = "/all-privileges")
	@ResponseBody
	public ArrayList<PrivilegeDTO> getPrivileges() {
		logger.info("getting all privileges ");
		return privilegeService.getAllPrivileges();
	}
	
	@RequestMapping(headers = { "Accept=application/json" }, method = POST, value = "/edit-privilege")
	public ResponseEntity<String> editPrivilege(@RequestBody PrivilegeDTO privilegeDTO) {
		logger.info("received status - " + privilegeDTO.getStatus());
		String message = privilegeService.editPrivilege(privilegeDTO);
		return new ResponseEntity<>(message, OK);
	}
	
	@RequestMapping(headers = { "Accept=application/json" }, method = POST, value = "/add-role")
	public ResponseEntity<String> addRole(@RequestBody RoleDTO roleDTO) {
		logger.info("create request received for role - " + roleDTO.getName());
		String message = roleService.addRole(roleDTO);//privilegeService.addPrivilege(privilegeDTO);		
		return new ResponseEntity<>(message, OK);
	}
	
	@RequestMapping(headers = { "Accept=application/json" }, method = POST, value = "/edit-role")
	public ResponseEntity<String> editRole(@RequestBody RoleDTO roleDTO) {
		logger.info("create request received for role - " + roleDTO.getName());
		String message = roleService.editRole(roleDTO);//privilegeService.addPrivilege(privilegeDTO);		
		return new ResponseEntity<>(message, OK);
	}
	
	@RequestMapping(method = GET, value = "role-by-name")
	@ResponseBody
	public RoleDTO getRoleByName(@RequestParam String roleName) {
		logger.info("requeset reached with - " + roleName);
		return roleService.getRoleByName(roleName);
	}
	
	@RequestMapping(headers = { "Accept=application/json" }, method = POST, value = "/add-user")
	public ResponseEntity<String> addUser(@RequestBody UserDTO userDTO) {
		logger.info("create request received for user - " + userDTO.getName());
		String message = userService.addUser(userDTO);
		return new ResponseEntity<>(message, OK);
	}
	
	@RequestMapping(headers = { "Accept=application/json" }, method = POST, value = "/edit-user")
	public ResponseEntity<String> editUser(@RequestBody UserDTO userDTO) {
		logger.info("update request received for user - " + userDTO.getUserName());
		
		String message = userService.editUser(userDTO);
		return new ResponseEntity<>(message, OK);
	}
	
	@RequestMapping(method = GET, value = "/valid-username")
	@ResponseBody
	public ResponseEntity<String> isUsernameAvailable(@RequestParam String userName) {
		logger.info("check if user with name -" + userName + " exists.");
		String message = userService.ifUserExists(userName);
		return new ResponseEntity<>(message, OK);
	}
	
	@RequestMapping(method = GET, value = "/user-by-role")
	@ResponseBody
	public ResponseEntity<String> getUserByRole(@RequestParam String role) {		
		return new ResponseEntity<>(new Gson().toJson(userService.getUserByRole(role)), OK);
	}
	
}
