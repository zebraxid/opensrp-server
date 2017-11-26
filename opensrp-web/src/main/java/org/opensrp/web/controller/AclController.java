package org.opensrp.web.controller;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONException;
import org.motechproject.scheduletracking.api.domain.Enrollment;
import org.opensrp.common.AllConstants.ScheduleNames;
import org.opensrp.connector.openmrs.service.OpenmrsUserService;
import org.opensrp.dashboard.dto.PrivilegeDTO;
import org.opensrp.dashboard.dto.RoleDTO;
import org.opensrp.dashboard.dto.UserDTO;
import org.opensrp.dashboard.service.PrivilegeService;
import org.opensrp.dashboard.service.RoleService;
import org.opensrp.dashboard.service.UsersService;
import org.opensrp.dto.AclDTO;
import org.opensrp.register.mcare.domain.Child;
import org.opensrp.register.mcare.domain.Mother;
import org.opensrp.register.mcare.repository.AllChilds;
import org.opensrp.register.mcare.repository.AllMothers;
import org.opensrp.register.mcare.service.AclService;
import org.opensrp.register.mcare.service.scheduling.ChildSchedulesService;
import org.opensrp.register.mcare.service.scheduling.PNCSchedulesService;
import org.opensrp.scheduler.Action;
import org.opensrp.scheduler.HealthSchedulerService;
import org.opensrp.scheduler.repository.AllActions;
import org.opensrp.scheduler.service.AllEnrollmentWrapper;
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
	
	private AclService aclService;
	
	private OpenmrsUserService openmrsUserService;
	
	private PrivilegeService privilegeService;
	
	private UsersService userService;
	
	private static Logger logger = LoggerFactory.getLogger(AclController.class);
	
	@Autowired
	private AllEnrollmentWrapper allEnrollmentWrapper;
	
	@Autowired
	private HealthSchedulerService scheduler;
	
	@Autowired
	private AllActions allActions;
	
	@Autowired
	private AllMothers allMothers;
	
	@Autowired
	private AllChilds allChilds;
	
	@Autowired
	private PNCSchedulesService pncSchedulesService;
	
	@Autowired
	private ChildSchedulesService childSchedulesService;
	
	@Autowired
	public AclController(RoleService roleService, AclService aclService, OpenmrsUserService openmrsUserService,
	    PrivilegeService privilegeService, UsersService userService) {
		this.roleService = roleService;
		this.aclService = aclService;
		this.openmrsUserService = openmrsUserService;
		this.privilegeService = privilegeService;
		this.userService = userService;
	}
	
	/*@RequestMapping(headers = { "Accept=application/json" }, method = POST, value = "/add-user")
	public ResponseEntity<String> addRole(@RequestBody RoleDTO roleDTO) {
		String message = roleService.addRole(roleDTO);
		return new ResponseEntity<>(message,OK);
	}*/
	
	/*@RequestMapping(headers = { "Accept=application/json" }, method = POST, value = "/edit-user")
	public ResponseEntity<String> editRole(@RequestBody RoleDTO roleDTO) {
		String message = roleService.editRole(roleDTO);
		return new ResponseEntity<>(message,OK);
	}*/
	
	@RequestMapping(headers = { "Accept=application/json" }, method = POST, value = "/add-acl")
	public ResponseEntity<String> addAcl(@RequestBody AclDTO aclDTO) {
		String message = aclService.addAcl(aclDTO);
		return new ResponseEntity<>(message, OK);
	}
	
	@RequestMapping(headers = { "Accept=application/json" }, method = POST, value = "/edit-acl")
	public ResponseEntity<String> editAcl(@RequestBody AclDTO aclDTO) {
		String message = aclService.editAcl(aclDTO);
		return new ResponseEntity<>(message, OK);
	}
	
	@RequestMapping(method = GET, value = "/all-user-name")
	@ResponseBody
	public ResponseEntity<String> getAllUserName() throws JSONException {
		return new ResponseEntity<>(new Gson().toJson(openmrsUserService.getAllUsers()), OK);
	}
	
	/*@RequestMapping(method = GET, value = "/role-access-tokens")
	@ResponseBody
	public AclDTO getRoleAndAccessTokens(@RequestParam String userName) {
		AclDTO tempDTO = new AclDTO();
		tempDTO.withRoleName("Admin");
		tempDTO.withRoleId("2ba3698706c7527a7a4b78546d011f1c");
		tempDTO.withStatus("Active");
		Map<String, String> tokens = new HashMap<String, String>();
		tokens.put("0", "Household");
		tokens.put("1", "Household Details");
		tokens.put("2", "Elco");
		tokens.put("3", "Elco Details");
		tokens.put("4", "PW");
		tokens.put("5", "PW Details");
		tokens.put("6", "Data Export");
		tokens.put("7", "User List");
		tokens.put("8", "User Assign");
		tokens.put("9", "User Assign Edit");
		tokens.put("10", "Role Edit");
		tokens.put("11", "Add Role");
		tokens.put("12", "Acl");
		tempDTO.withAccessTokens(tokens);
		//return aclService.getRoleAndAccessTokens(userName);
		return tempDTO;
	}*/
	
	// new one
	@RequestMapping(method = GET, value = "/role-access-token")
	@ResponseBody
	public List<String> getPrivileges(@RequestParam String userName) {
		return userService.getPrivilegesOfAUser(userName);
	}
	
	@RequestMapping(method = GET, value = "/role-access-tokens-by-name")
	@ResponseBody
	public AclDTO getRoleAndAccessTokensByRoleName(@RequestParam String roleName) {
		return aclService.getRoleAndAccessTokensByName(roleName);
	}
	
	@RequestMapping(method = GET, value = "/all-roles-access-tokens")
	@ResponseBody
	public ArrayList<AclDTO> getRolesAndAccessTokens() {
		return (ArrayList<AclDTO>) aclService.getRolesAndAccessTokens();
	}
	
	@RequestMapping(method = GET, value = "/all-active-roles-access-tokens")
	@ResponseBody
	public ArrayList<AclDTO> getActiveRolesAndAccessTokens() {
		return (ArrayList<AclDTO>) aclService.getActiveRolesAndAccessTokens();
	}
	
	@RequestMapping(method = GET, value = "/all-roles-with-user")
	@ResponseBody
	public ArrayList<RoleDTO> getRolesAndUser() {
		return (ArrayList<RoleDTO>) roleService.getRolesAndUser();
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
		/*List<PrivilegeDTO> privileges = roleDTO.getPrivileges();
		for(int i = 0 ; i < privileges.size(); i++){
			logger.info("Privilege name - " + privileges.get(i).getName() + " - id - " + privileges.get(i).getId());
		}*/
		String message = roleService.addRole(roleDTO);//privilegeService.addPrivilege(privilegeDTO);		
		return new ResponseEntity<>(message, OK);
		//return new ResponseEntity<>("1",OK);
		//return "1";
	}
	
	@RequestMapping(headers = { "Accept=application/json" }, method = POST, value = "/edit-role")
	public ResponseEntity<String> editRole(@RequestBody RoleDTO roleDTO) {
		logger.info("create request received for role - " + roleDTO.getName());
		String message = roleService.editRole(roleDTO);//privilegeService.addPrivilege(privilegeDTO);		
		return new ResponseEntity<>(message, OK);
		//return new ResponseEntity<>("1",OK);
		//return "1";
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
	
	@RequestMapping(method = GET, value = "/pe")
	public void pncencc() {
		scheduler.fullfillMilestoneAndCloseAlert("06072b3a-8fe7-4b44-a9c6-308397b35cf1", "rojina", ScheduleNames.CHILD,
		    new LocalDate());
		scheduler.fullfillMilestoneAndCloseAlert("e85d12d7-cce5-443e-8e18-0a79fe249f8b", "rojina", ScheduleNames.PNC,
		    new LocalDate());
		
	}
	
	@RequestMapping(method = GET, value = "/pnc")
	public void pnc() {
		String pattern = "yyyy-MM-dd";
		
		String csvFile = "/opt/multimedia/pnc.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		String id = "";
		
		try {
			
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				String[] csvData = line.split(cvsSplitBy);
				id = csvData[0].trim();
				System.err.println("Id:" + id);
				Mother mother = allMothers.findByCaseId(id);
				
				try {
					List<Action> actions = allActions.findAlertByANMIdEntityIdScheduleName(mother.PROVIDERID(), id,
					    ScheduleNames.PNC);
					System.err.println("actions:" + actions);
					if (!actions.isEmpty() && actions != null) {
						allActions.remove(actions.get(0));
					}
					
					List<Map<String, String>> bnfs = mother.bnfVisitDetails();
					int psrfsCount = bnfs.size() - 1;
					Map<String, String> bnf = bnfs.get(psrfsCount);
					String doo = bnf.get("FWBNFDTOO");
					DateTime dateTime = DateTime.parse(doo);
					DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
					String referenceDate = fmt.print(dateTime);
					
					try {
						scheduler.unEnrollFromSchedule(id, mother.PROVIDERID(), ScheduleNames.PNC);
					}
					catch (Exception e) {
						// TODO: handle exception
					}
					List<Enrollment> enrolments = allEnrollmentWrapper.getByEid(id);
					
					if (enrolments != null && !enrolments.isEmpty()) {
						for (Enrollment enrollment : enrolments) {
							if (enrollment.getScheduleName().equalsIgnoreCase(ScheduleNames.PNC)) {
								System.err.println("enrolments:" + enrolments.get(0).getScheduleName());
								allEnrollmentWrapper.remove(enrollment);
								System.err.println("enrolments:" + enrolments.get(0).getScheduleName());
							} else {
								System.err.println("Not PNC");
							}
						}
						
					} else {
						
					}
					pncSchedulesService.enrollPNCRVForMother(id, "", mother.PROVIDERID(), LocalDate.parse(referenceDate),
					    referenceDate);
					
				}
				catch (Exception ee) {
					System.err.println("NO data foubd");
				}
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping(method = GET, value = "/encc")
	public void encc() {
		String pattern = "yyyy-MM-dd";
		String csvFile = "/opt/multimedia/encc.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		String id = "";
		try {
			
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				String[] csvData = line.split(cvsSplitBy);
				id = csvData[0].trim();
				Child child = allChilds.findByCaseId(id);
				try {
					List<Action> actions = allActions.findAlertByANMIdEntityIdScheduleName(child.PROVIDERID(), id,
					    ScheduleNames.CHILD);
					if (!actions.isEmpty() && actions != null) {
						allActions.remove(actions.get(0));
					}
					
					String doo = child.details().get("FWBNFDOB");
					DateTime dateTime = DateTime.parse(doo);
					DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
					String referenceDate = fmt.print(dateTime);
					
					try {
						scheduler.unEnrollFromSchedule(id, child.PROVIDERID(), ScheduleNames.CHILD);
					}
					catch (Exception e) {
						
					}
					List<Enrollment> enrolments = allEnrollmentWrapper.getByEid(id);
					if (enrolments != null && !enrolments.isEmpty()) {
						for (Enrollment enrollment : enrolments) {
							
							if (enrollment.getScheduleName().equalsIgnoreCase(ScheduleNames.CHILD)) {
								System.err.println("enrolments:" + enrolments.get(0).getScheduleName());
								allEnrollmentWrapper.remove(enrollment);
							} else {
								System.err.println("Not ENCC");
							}
						}
						
					} else {
						
					}
					childSchedulesService.enrollENCCForChild(id, "", child.PROVIDERID(), LocalDate.parse(referenceDate),
					    referenceDate);
				}
				catch (Exception e) {
					System.err.println("NO data foubd");
				}
			}
		}
		catch (Exception e) {
			
		}
	}
}
