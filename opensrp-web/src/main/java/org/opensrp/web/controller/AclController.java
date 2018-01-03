package org.opensrp.web.controller;

import static org.opensrp.register.mcare.OpenSRPScheduleConstants.ChildScheduleConstants.SCHEDULE_ENCC_1;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.ChildScheduleConstants.SCHEDULE_ENCC_2;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.ChildScheduleConstants.SCHEDULE_ENCC_3;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC_1;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC_2;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC_3;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC_4;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_PNC_1;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_PNC_2;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_PNC_3;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Weeks;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONException;
import org.motechproject.scheduletracking.api.domain.Enrollment;
import org.opensrp.common.AllConstants.ScheduleNames;
import org.opensrp.common.util.DateUtil;
import org.opensrp.connector.openmrs.service.OpenmrsUserService;
import org.opensrp.dashboard.dto.PrivilegeDTO;
import org.opensrp.dashboard.dto.RoleDTO;
import org.opensrp.dashboard.dto.UserDTO;
import org.opensrp.dashboard.service.PrivilegeService;
import org.opensrp.dashboard.service.RoleService;
import org.opensrp.dashboard.service.UsersService;
import org.opensrp.dto.AclDTO;
import org.opensrp.register.mcare.OpenSRPScheduleConstants;
import org.opensrp.register.mcare.domain.Child;
import org.opensrp.register.mcare.domain.Mother;
import org.opensrp.register.mcare.repository.AllChilds;
import org.opensrp.register.mcare.repository.AllMothers;
import org.opensrp.register.mcare.service.AclService;
import org.opensrp.register.mcare.service.scheduling.ANCSchedulesService;
import org.opensrp.register.mcare.service.scheduling.ChildSchedulesService;
import org.opensrp.register.mcare.service.scheduling.PNCSchedulesService;
import org.opensrp.register.mcare.service.scheduling.ScheduleLogService;
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
	private ANCSchedulesService ancSchedulesService;
	
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
	
	private boolean isANCSubmited(Mother mother, String visitCode) {
		Map<String, String> anc = null;
		
		if (SCHEDULE_ANC_1.equalsIgnoreCase(visitCode)) {
			anc = mother.ancVisitOne();
		} else if (SCHEDULE_ANC_2.equalsIgnoreCase(visitCode)) {
			anc = mother.ancVisitTwo();
		} else if (SCHEDULE_ANC_3.equalsIgnoreCase(visitCode)) {
			anc = mother.ancVisitThree();
		} else if (SCHEDULE_ANC_4.equalsIgnoreCase(visitCode)) {
			anc = mother.ancVisitFour();
		}
		if (!anc.isEmpty()) {
			return true;//submitted
		}
		return false;
		
	}
	
	private boolean isPNCSubmited(Mother mother, String visitCode) {
		Map<String, String> pnc = null;
		
		if (SCHEDULE_PNC_1.equalsIgnoreCase(visitCode)) {
			pnc = mother.pncVisitOne();
		} else if (SCHEDULE_PNC_2.equalsIgnoreCase(visitCode)) {
			pnc = mother.pncVisitTwo();
		} else if (SCHEDULE_PNC_3.equalsIgnoreCase(visitCode)) {
			pnc = mother.pncVisitThree();
		}
		if (!pnc.isEmpty()) {
			return true;//submitted
		}
		return false;
		
	}
	
	private boolean isENNCSubmited(Child child, String visitCode) {
		Map<String, String> pnc = null;
		
		if (SCHEDULE_ENCC_1.equalsIgnoreCase(visitCode)) {
			pnc = child.enccVisitOne();
		} else if (SCHEDULE_ENCC_2.equalsIgnoreCase(visitCode)) {
			pnc = child.enccVisitTwo();
		} else if (SCHEDULE_ENCC_3.equalsIgnoreCase(visitCode)) {
			pnc = child.enccVisitThree();
		}
		if (!pnc.isEmpty()) {
			return true;//submitted
		}
		return false;
		
	}
	
	private String checkENCC(LocalDate referenceDateForSchedule, String referenceDate) {
		
		String milestone = null;
		
		Date date = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = format.parse(referenceDate);
			
		}
		catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DateTime FWBNFDTOO = new DateTime(date);
		long datediff = ScheduleLogService.getDaysDifference(FWBNFDTOO);
		System.err.println("datediff:" + datediff);
		if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Days.ONE.toPeriod())) {
			milestone = SCHEDULE_ENCC_1;
			
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Days.FIVE.toPeriod())) {
			
			milestone = SCHEDULE_ENCC_2;
			
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Days.SEVEN.plus(2).toPeriod())) {
			
			milestone = SCHEDULE_ENCC_3;
			
		} else {
			milestone = "expired";
			
		}
		return milestone;
		
	}
	
	private String checkPNC(LocalDate referenceDateForSchedule, String referenceDate) {
		String milestone = null;
		
		Date date = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = format.parse(referenceDate);
			
		}
		catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DateTime FWBNFDTOO = new DateTime(date);
		long datediff = ScheduleLogService.getDaysDifference(FWBNFDTOO);
		
		if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Days.ONE.toPeriod())) {
			milestone = SCHEDULE_PNC_1;
			
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Days.FIVE.toPeriod())) {
			
			milestone = SCHEDULE_PNC_2;
			
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Days.SEVEN.plus(2).toPeriod())) {
			
			milestone = SCHEDULE_PNC_3;
			
		} else {
			milestone = "expired";
			
		}
		return milestone;
		
	}
	
	private String checkANC(LocalDate referenceDateForSchedule, String startDate) {
		String milestone = null;
		
		Date date = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = format.parse(startDate);
		}
		catch (ParseException e) {
			logger.info("Date parse exception:" + e.getMessage());
		}
		System.err.println("startDate:" + startDate);
		DateTime start = new DateTime(date);
		
		long datediff = ScheduleLogService.getDaysDifference(start);
		System.err.println("start:" + start + " datediff:" + datediff);
		if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(23).toPeriod())) {
			//161
			milestone = SCHEDULE_ANC_1;
			
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(31).toPeriod())) {
			//217
			milestone = SCHEDULE_ANC_2;
			
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(35).toPeriod())) {
			//245
			milestone = SCHEDULE_ANC_3;
			
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(44).toPeriod()
		        .minusDays(1))) {
			// 307
			milestone = SCHEDULE_ANC_4;
			
		} else {
			milestone = "expired";
			
		}
		return milestone;
		
	}
	
	@RequestMapping(method = GET, value = "/anc")
	public void anc() {
		List<Enrollment> enrollments = allEnrollmentWrapper.all(); ///function(doc) { if(doc.type === 'Action' && doc.caseID && doc.data.scheduleName=='Ante Natal Care Reminder Visit') {emit(doc.caseID, doc._id)} }
		for (Enrollment enrollment : enrollments) {
			if ("DEFAULTED".equalsIgnoreCase(enrollment.getStatus().name())) {
				List<Action> actions = allActions.findByCaseID(enrollment.getExternalId());//function(doc) { if(doc.type === 'Action' && doc.data.alertStatus !=='expired' && (doc.data.scheduleName =='Essential Newborn Care Checklist' || doc.data.scheduleName ==='Post Natal Care Reminder Visit' || doc.data.scheduleName ==='Ante Natal Care Reminder Visit')) {emit(null, doc._id)} }
				if (actions.size() != 0) {
					allEnrollmentWrapper.remove(enrollment);
					allActions.remove(actions.get(0));
					Mother mother = allMothers.findByCaseId(enrollment.getExternalId());
					System.err.println("" + actions.get(0).data().get("scheduleName"));
					if (mother != null) {
						try {
							String lmp = mother.details().get("LMP");
							ancSchedulesService.enrollMother(enrollment.getExternalId(), LocalDate.parse(lmp),
							    mother.PROVIDERID(), mother.INSTANCEID(), lmp);
						}
						catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			
		}
	}
	
	/////////////////////////////////////
	@RequestMapping(method = GET, value = "/schedule-refresh-anc")
	public void ancScheduleRefresh() {
		int i = 0;
		String visitCode = "";
		List<Action> actions = allActions.getAll();
		System.err.println("" + actions.size());
		String lmp = "";
		String currentVisitiCode = "";
		int m = 0;
		int acn4 = 0;
		int unenroll = 0;
		int currentVisitiCodec = 0;
		int notCurrentVisitiCodec = 0;
		int expired = 0;
		int enroll = 0;
		int notsubSame = 0;
		int notsubNotSame = 0;
		boolean isEnrolled = false;
		for (Action action : actions) {
			visitCode = action.data().get("visitCode");
			Mother mother = allMothers.findByCaseId(action.caseId());
			//System.err.println("Mother;" + mother);
			
			if (mother != null) {
				lmp = mother.details().get("LMP");
				currentVisitiCode = checkANC(LocalDate.parse(lmp), lmp);
				boolean ancStatus = isANCSubmited(mother, visitCode);
				List<Enrollment> enrollments = allEnrollmentWrapper.getByEid(action.caseId());
				if (ancStatus) {// if submitted //780
				
					if (SCHEDULE_ANC_4.equalsIgnoreCase(visitCode)) {
						//should false all scedule; // problem in tab synced schedule
						// unenroll all schedule
						acn4++;//196
						makeScheduleFalse(action);
					} else {
						if (!currentVisitiCode.equalsIgnoreCase(visitCode)) {
							
							notCurrentVisitiCodec++; //168
							scheduleRefresh(enrollments, action, lmp, action.anmIdentifier(), "", ScheduleNames.ANC);
							// refresh schedule
						} else {
							currentVisitiCodec++; //416
							makeScheduleFalse(action);
							// false all schedule
						}
					}
					
				} else { // not submitted
					i++; //1875
					if (currentVisitiCode.equalsIgnoreCase("expired")) {
						
						for (Enrollment enrollment : enrollments) {
							if (!"ACTIVE".equalsIgnoreCase(enrollment.getStatus().name())
							        && OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC
							                .equalsIgnoreCase(enrollment.getScheduleName())) {
								//System.err.println("Sataus:" + enrollment.getStatus());
								isEnrolled = true;
							}
						}
						if (isEnrolled) {
							unenroll++;//311
							// nothing to do OR refresh all unenroled/completed
						} else {
							enroll++;
						}
						
					} else {
						if (!currentVisitiCode.equalsIgnoreCase(visitCode)) {
							notsubNotSame++; //203
							// must need to refresh
							scheduleRefresh(enrollments, action, lmp, action.anmIdentifier(), "", ScheduleNames.ANC);
						} else {
							notsubSame++;//1038/ may be nothing to do
							
						}
					}
					
				}
			} else {
				m++;
			}
			
		}
		System.err.println("CNT:" + i + "Not Mother:" + m + "acn4:" + acn4 + "currentVisitiCodec:" + currentVisitiCodec
		        + "   notCurrentVisitiCodec:" + notCurrentVisitiCodec + "  expired:" + expired + "unenroll: " + unenroll
		        + "  enroll:" + enroll + " notsubSame  :" + notsubSame + " notsubNotSame;" + notsubNotSame);
	}
	
	@RequestMapping(method = GET, value = "/schedule-refresh-pnc")
	public void pncScheduleRefresh() {
		String pattern = "yyyy-MM-dd";
		int i = 0;
		String visitCode = "";
		List<Action> actions = allActions.getAll();
		System.err.println("" + actions.size());
		String doo = "";
		String currentVisitiCode = "";
		int m = 0;
		int acn4 = 0;
		int unenroll = 0;
		int currentVisitiCodec = 0;
		int notCurrentVisitiCodec = 0;
		int expired = 0;
		int enroll = 0;
		int notsubSame = 0;
		int notsubNotSame = 0;
		boolean isEnrolled = false;
		for (Action action : actions) {
			visitCode = action.data().get("visitCode");
			Mother mother = allMothers.findByCaseId(action.caseId());
			//System.err.println("Mother;" + mother);
			
			if (mother != null) {
				List<Enrollment> enrollments = allEnrollmentWrapper.getByEid(action.caseId());
				List<Map<String, String>> bnfVisitDetails = mother.bnfVisitDetails();
				doo = getBnfDate(bnfVisitDetails);
				DateTime dateTime = DateTime.parse(doo);
				DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
				String referenceDate = fmt.print(dateTime);
				currentVisitiCode = checkPNC(LocalDate.parse(referenceDate), referenceDate);
				boolean pncStatus = isPNCSubmited(mother, visitCode);
				if (pncStatus) {// if submitted //780
				
					if (SCHEDULE_PNC_3.equalsIgnoreCase(visitCode)) {
						//should false all scedule; // problem in tab synced schedule
						// unenroll all schedule
						makeScheduleFalse(action);
					} else {
						if (!currentVisitiCode.equalsIgnoreCase(visitCode)) {
							
							notCurrentVisitiCodec++; //7
							scheduleRefresh(enrollments, action, doo, action.anmIdentifier(), "", ScheduleNames.PNC);
							// refresh schedule
						} else {
							currentVisitiCodec++; //0
							makeScheduleFalse(action);
						}
					}
					
				} else { // not submitted
				
					if (currentVisitiCode.equalsIgnoreCase("expired")) {
						
						for (Enrollment enrollment : enrollments) {
							if (!"ACTIVE".equalsIgnoreCase(enrollment.getStatus().name())
							        && ScheduleNames.PNC.equalsIgnoreCase(enrollment.getScheduleName())) {
								
								isEnrolled = true;
							}
						}
						if (isEnrolled) {
							unenroll++;//69
							// nothing to do OR refresh all unenroled/completed
						} else {
							enroll++;
						}
						
					} else {
						if (!currentVisitiCode.equalsIgnoreCase(visitCode)) {
							notsubNotSame++; //0
							scheduleRefresh(enrollments, action, doo, action.anmIdentifier(), "", ScheduleNames.PNC);
							// must need to refresh
							
						} else {
							notsubSame++;//6/ may be nothing to do
							
						}
					}
					
				}
			} else {
				m++;
			}
			
		}
		System.err.println("Not Mother:" + m + "acn4:" + acn4 + "currentVisitiCodec:" + currentVisitiCodec
		        + "   notCurrentVisitiCodec:" + notCurrentVisitiCodec + "  expired:" + expired + "unenroll: " + unenroll
		        + "  enroll:" + enroll + " notsubSame  :" + notsubSame + " notsubNotSame;" + notsubNotSame);
	}
	
	@RequestMapping(method = GET, value = "/schedule-refresh-encc")
	public void enncScheduleRefresh() {
		String pattern = "yyyy-MM-dd";
		int i = 0;
		String visitCode = "";
		List<Action> actions = allActions.getAll();
		
		String doo = "";
		String currentVisitiCode = "";
		int m = 0;
		int acn4 = 0;
		int unenroll = 0;
		int currentVisitiCodec = 0;
		int notCurrentVisitiCodec = 0;
		int expired = 0;
		int enroll = 0;
		int notsubSame = 0;
		int notsubNotSame = 0;
		boolean isEnrolled = false;
		for (Action action : actions) {
			visitCode = action.data().get("visitCode");
			Child child = allChilds.findByCaseId(action.caseId());
			//System.err.println("Mother;" + mother);
			
			if (child != null) {
				doo = child.details().get("FWBNFDOB");
				DateTime dateTime = DateTime.parse(doo);
				DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
				String referenceDate = fmt.print(dateTime);
				currentVisitiCode = checkENCC(LocalDate.parse(referenceDate), referenceDate);
				boolean enccStatus = isENNCSubmited(child, visitCode);
				List<Enrollment> enrollments = allEnrollmentWrapper.getByEid(action.caseId());
				if (enccStatus) {// if submitted //780
				
					if (SCHEDULE_ENCC_3.equalsIgnoreCase(visitCode)) {
						//should false all scedule; // problem in tab synced schedule
						// unenroll all schedule
						//10
						makeScheduleFalse(action);
					} else {
						if (!currentVisitiCode.equalsIgnoreCase(visitCode)) {
							notCurrentVisitiCodec++; //7							
							scheduleRefresh(enrollments, action, doo, action.anmIdentifier(), "", ScheduleNames.CHILD);
						} else {
							currentVisitiCodec++; //0
							makeScheduleFalse(action);
							//// false all schedule
						}
					}
					
				} else { // not submitted
				
					if (currentVisitiCode.equalsIgnoreCase("expired")) {
						
						for (Enrollment enrollment : enrollments) {
							if (!"ACTIVE".equalsIgnoreCase(enrollment.getStatus().name())
							        && ScheduleNames.CHILD.equalsIgnoreCase(enrollment.getScheduleName())) {
								//System.err.println("Sataus:" + enrollment.getStatus());
								isEnrolled = true;
							}
						}
						if (isEnrolled) {
							unenroll++;//0
							// nothing to do OR refresh all unenroled/completed
						} else {
							enroll++;//29
						}
						
					} else {
						if (!currentVisitiCode.equalsIgnoreCase(visitCode)) {
							notsubNotSame++; //0
							// must need to refresh
							scheduleRefresh(enrollments, action, doo, action.anmIdentifier(), "", ScheduleNames.CHILD);
							
						} else {
							notsubSame++;//6/ may be nothing to do
							
						}
					}
					
				}
			} else {
				m++;
			}
			
		}
		System.err.println("Not Mother:" + m + "acn4:" + acn4 + "currentVisitiCodec:" + currentVisitiCodec
		        + "   notCurrentVisitiCodec:" + notCurrentVisitiCodec + "  expired:" + expired + "unenroll: " + unenroll
		        + "  enroll:" + enroll + " notsubSame  :" + notsubSame + " notsubNotSame;" + notsubNotSame);
	}
	
	private void scheduleRefresh(List<Enrollment> enrollments, Action action, String refDate, String provider,
	                             String instance, String scheduleName) {
		try {
			allEnrollmentWrapper.remove(enrollments.get(0));
			allActions.remove(action);
			scheduler.unEnrollFromSchedule(action.caseId(), action.anmIdentifier(), scheduleName);
		}
		catch (Exception e) {
			
		}
		
		try {
			ancSchedulesService.enrollMother(enrollments.get(0).getExternalId(), LocalDate.parse(refDate), provider,
			    instance, refDate);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void makeScheduleFalse(Action action) {
		action.markAsInActive();
		allActions.update(action);
	}
	
	private String getBnfDate(List<Map<String, String>> bnfVisitDetails) {
		String user_type = "";
		String date = "";
		for (Map<String, String> map : bnfVisitDetails) {
			user_type = map.get("user_type");
			date = map.get("FWBNFDTOO");
			if ((!"".equalsIgnoreCase(date) || !date.isEmpty()) && "FD".equalsIgnoreCase(user_type)) {
				//System.err.println("Date:" + date);
				return date;
			}
		}
		
		return null;
		
	}
}
