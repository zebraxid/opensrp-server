package org.opensrp.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.opensrp.common.util.HttpResponse;
import org.opensrp.dto.CountServiceDTO;
import org.opensrp.dto.VaccineCountDTO;
import org.opensrp.dto.WeeklyDataCount;
import org.opensrp.dto.register.HHRegisterDTO;
import org.opensrp.register.mcare.HHRegister;
import org.opensrp.register.mcare.mapper.HHRegisterMapper;
import org.opensrp.register.mcare.service.HHRegisterService;
import org.opensrp.register.mcare.service.MembersService;
import org.opensrp.register.mcare.service.MultimediaRegisterService;
import org.opensrp.rest.register.DTO.CommonDTO;
import org.opensrp.rest.register.DTO.HouseholdEntryDTO;
import org.opensrp.rest.register.DTO.MemberRegisterEntryDTO;
import org.opensrp.rest.services.LuceneHouseHoldService;
import org.opensrp.rest.services.LuceneMembersService;
import org.opensrp.service.DataCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;

@Controller
public class RegisterController {

	private HHRegisterService hhRegisterService;
	private HHRegisterMapper hhRegisterMapper;
	private MultimediaRegisterService multimediaRegisterService;
	private DataCountService dataCountService;
	@Autowired
	private LuceneHouseHoldService luceneHouseHoldService;
	@Autowired
	private LuceneMembersService luceneMembersService;
	@Autowired
	private MembersService membersService;
	@Autowired
	public RegisterController(HHRegisterService hhRegisterService, 
			HHRegisterMapper hhRegisterMapper,
			MultimediaRegisterService multimediaRegisterService,
			DataCountService dataCountService) {
		this.hhRegisterService = hhRegisterService;
		this.hhRegisterMapper = hhRegisterMapper;
		this.multimediaRegisterService = multimediaRegisterService;
		this.dataCountService = dataCountService;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/registers/hh")
    @ResponseBody
    public ResponseEntity<HHRegisterDTO> hhRegister(@RequestParam("anm-id") String anmIdentifier) {
        HHRegister hhRegister = hhRegisterService.getHHRegisterForProvider(anmIdentifier);
        return new ResponseEntity<>(hhRegisterMapper.mapToDTO(hhRegister), HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/getMultimedia")
    @ResponseBody
    public ResponseEntity<String>  getMultimedia()
    {
    	multimediaRegisterService.getMultimedia();
    	return new ResponseEntity<>("Welcome to multimedia service", HttpStatus.OK);
    }
    
   /* @RequestMapping(method = RequestMethod.GET, value = "/registers/data-count")
    @ResponseBody
    public ResponseEntity<List<CountServiceDTO>>  getHouseHoldInformation(@RequestParam("anm-id") String provider,@RequestParam("start-month") String startMonth,@RequestParam("end-month") String endMonth,
    		@RequestParam("start-week") String startWeek,@RequestParam("end-week") String endtWeek,@RequestParam("type") String type){
    	dataCountService.getHHCountInformation(provider,startMonth,endMonth,startWeek,endtWeek,type);
    	return new ResponseEntity<>(dataCountService.getHHCountInformation(provider,startMonth,endMonth,startWeek,endtWeek,type), HttpStatus.OK);
    }*/
    
    @RequestMapping(method = RequestMethod.GET, value = "/registers/vc-count")
    @ResponseBody
    public ResponseEntity<List<VaccineCountDTO>>  getVaccineInformation(@RequestParam("type") String type,@RequestParam("start-month") String startMonth,@RequestParam("end-month") String endMonth) throws JSONException{
    	return new ResponseEntity<>(dataCountService.getVaccineCountInformation(type,startMonth,endMonth), HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/registers/vaccine-count")
    @ResponseBody
    public ResponseEntity<HttpResponse> getVaccineInfo(@RequestParam("type") String type,@RequestParam("start-month") String startMonth,@RequestParam("end-month") String endMonth) throws JSONException{
    	return new ResponseEntity<>(dataCountService.getVaccineCountForSendingToDHIS2(type,startMonth,endMonth), HttpStatus.OK);
    }
    
    /**
	 * @param queryParameters is a list of key.
	 * @param p is a number of page.
	 * @param limit is a data limit.
	 * @return all households match with specified key.
	 * */
	@RequestMapping(method = GET, value="/household-search")
    @ResponseBody
	public ResponseEntity<CommonDTO<HouseholdEntryDTO>> getHouseholdByKeys(@RequestParam MultiValueMap<String, String> queryParameters,@RequestParam int p,@RequestParam int limit) throws JsonParseException, JsonMappingException, IOException
	{
		CommonDTO<HouseholdEntryDTO>  households  = luceneHouseHoldService.getData(queryParameters,p,limit);
		 return new ResponseEntity<>(households, HttpStatus.OK);
	}
	/**		 
	 * @param  @param queryParameters is a list of key.
	 * @return total count of households
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 * */
	@RequestMapping(headers = { "Accept=application/json" }, method = GET, value = "/get-household-count-by-keys")
	@ResponseBody
	public ResponseEntity<String> getHouseholdCountByKeys(@RequestParam MultiValueMap<String, String> queryParameters) throws JsonParseException, JsonMappingException, IOException {
		return new ResponseEntity<>(new Gson().toJson(luceneHouseHoldService.getDataCount(queryParameters)), HttpStatus.OK);
	}
	
	/**		 
	 
	 * @param id is a household id of couchdb auto incremented ID.
	 * @return household details of a specified @id 	 * 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 * */
	@RequestMapping(headers = { "Accept=application/json" }, method = GET, value = "/get-household-details")
	@ResponseBody
	public ResponseEntity<String> getHouseholdById(@RequestParam String id) throws JsonParseException, JsonMappingException, IOException {
		return new ResponseEntity<>(new Gson().toJson(hhRegisterService.getHouseholdById(id)), HttpStatus.OK);
	}
	
	
	/**
	 * @param queryParameters is a list of key.
	 * @param p is a number of page.
	 * @param limit is a data limit.
	 * @return all members match with specified key.
	 * */
	@RequestMapping(method = GET, value="/member-search")
    @ResponseBody
	public ResponseEntity<CommonDTO<MemberRegisterEntryDTO>> getMemberByKeys(@RequestParam MultiValueMap<String, String> queryParameters,@RequestParam int p,@RequestParam int limit) throws JsonParseException, JsonMappingException, IOException
	{
		CommonDTO<MemberRegisterEntryDTO>  members  = luceneMembersService.getData(queryParameters,p,limit);
		 return new ResponseEntity<>(members, HttpStatus.OK);
	}
	/**		 
	 * @param  @param queryParameters is a list of key.
	 * @return total count of members
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 * */
	@RequestMapping(headers = { "Accept=application/json" }, method = GET, value = "/get-member-count-by-keys")
	@ResponseBody
	public ResponseEntity<String> getmemberCountByKeys(@RequestParam MultiValueMap<String, String> queryParameters) throws JsonParseException, JsonMappingException, IOException {
		return new ResponseEntity<>(new Gson().toJson(luceneMembersService.getDataCount(queryParameters)), HttpStatus.OK);
	}
	
	/**		 
	 
	 * @param id is a member id of couchdb auto incremented ID.
	 * @return member details of a specified @id 	 * 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 * */
	@RequestMapping(headers = { "Accept=application/json" }, method = GET, value = "/get-member-details")
	@ResponseBody
	public ResponseEntity<String> getMemberById(@RequestParam String id) throws JsonParseException, JsonMappingException, IOException {
		return new ResponseEntity<>(new Gson().toJson(membersService.getmemberById(id)), HttpStatus.OK);
	}

	// Data count start
	@RequestMapping(method = RequestMethod.GET, value = "/registers/data-count")
    @ResponseBody
    public ResponseEntity<List<CountServiceDTO>>  getHouseHoldInformation(@RequestParam("anm-id") String provider,@RequestParam("start-month") String startMonth,@RequestParam("end-month") String endMonth,
    		@RequestParam("start-week") String startWeek,@RequestParam("end-week") String endtWeek,@RequestParam("type") String type){
    	return new ResponseEntity<>(dataCountService.getHHCountInformation(provider,startMonth,endMonth,startWeek,endtWeek,type), HttpStatus.OK);
    }
    
   
    
    @RequestMapping(method = RequestMethod.GET, value = "/registers/hh-count")
    @ResponseBody
    public ResponseEntity<List<WeeklyDataCount>>  getHouseHoldInformation(@RequestParam("provider") String provider, @RequestParam("district") String district,
    														@RequestParam("upazilla") String upazilla, @RequestParam("union") String union){    
    	return new ResponseEntity<>(dataCountService.getHHCountInformationForLastFourMonthAsWeekWise(), HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/registers/hh-data-count")
    @ResponseBody
    public ResponseEntity<List<WeeklyDataCount>>  getHouseHoldInformationForChart(@RequestParam("provider") String provider, @RequestParam("district") String district,
    														@RequestParam("upazilla") String upazilla, @RequestParam("union") String union){    
    	return new ResponseEntity<>(dataCountService.getHHCountInformationForChart(provider, district, upazilla, union), HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/registers/elco-count")
    @ResponseBody
    public ResponseEntity<List<WeeklyDataCount>>  getElcoInformation(@RequestParam("provider") String provider, @RequestParam("district") String district,
									@RequestParam("upazilla") String upazilla, @RequestParam("union") String union){
    	return new ResponseEntity<>(dataCountService.getElcoCountInformationForLastFourMonthAsWeekWise(), HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/registers/elco-data-count")
    @ResponseBody
    public ResponseEntity<List<WeeklyDataCount>>  getElcoInformationForChart(@RequestParam("provider") String provider, @RequestParam("district") String district,
									@RequestParam("upazilla") String upazilla, @RequestParam("union") String union){
    	return new ResponseEntity<>(dataCountService.getElcoCountInformationForChart(provider, district, upazilla, union), HttpStatus.OK);
    }
    
    
    
}
