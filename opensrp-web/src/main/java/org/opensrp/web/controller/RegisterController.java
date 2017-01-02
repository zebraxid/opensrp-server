package org.opensrp.web.controller;

import java.util.List;

import org.opensrp.dto.CountServiceDTO;
import org.opensrp.dto.CountServiceDTOForChart;
import org.opensrp.dto.FormCountDTO;
import org.opensrp.dto.register.ANC_RegisterDTO;
import org.opensrp.dto.register.Child_RegisterDTO;
import org.opensrp.dto.register.ELCORegisterDTO;
import org.opensrp.dto.register.HHRegisterDTO;
import org.opensrp.register.mcare.ANCRegister;
import org.opensrp.register.mcare.ChildRegister;
import org.opensrp.register.mcare.ELCORegister;
import org.opensrp.register.mcare.HHRegister;
import org.opensrp.register.mcare.mapper.ANCRegisterMapper;
import org.opensrp.register.mcare.mapper.ChildRegisterMapper;
import org.opensrp.register.mcare.mapper.ELCORegisterMapper;
import org.opensrp.register.mcare.mapper.HHRegisterMapper;
import org.opensrp.register.mcare.service.ANCRegisterService;
import org.opensrp.register.mcare.service.ChildRegisterService;
import org.opensrp.register.mcare.service.ELCORegisterService;
import org.opensrp.register.mcare.service.HHRegisterService;
import org.opensrp.register.mcare.service.MultimediaRegisterService;
import org.opensrp.service.DataCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RegisterController {

	private ELCORegisterService ecRegisterService;
	private HHRegisterService hhRegisterService;
	private ANCRegisterService ancRegisterService;
	private ChildRegisterService childRegisterService;
	private ELCORegisterMapper ecRegisterMapper;
	private HHRegisterMapper hhRegisterMapper;
	private ANCRegisterMapper ancRegisterMapper;
	private ChildRegisterMapper childRegisterMapper;
	private MultimediaRegisterService multimediaRegisterService;
	private DataCountService dataCountService;

	@Autowired
	public RegisterController(ELCORegisterService ecRegisterService, ELCORegisterMapper ecRegisterMapper, HHRegisterService hhRegisterService,
			HHRegisterMapper hhRegisterMapper, ANCRegisterService ancRegisterService, ANCRegisterMapper ancRegisterMapper,
			ChildRegisterService childRegisterService, ChildRegisterMapper childRegisterMapper, MultimediaRegisterService multimediaRegisterService,
			DataCountService dataCountService) {
		this.ecRegisterService = ecRegisterService;
		this.hhRegisterService = hhRegisterService;
		this.ecRegisterMapper = ecRegisterMapper;
		this.hhRegisterMapper = hhRegisterMapper;
		this.ancRegisterService = ancRegisterService;
		this.ancRegisterMapper = ancRegisterMapper;
		this.childRegisterService = childRegisterService;
		this.childRegisterMapper = childRegisterMapper;
		this.multimediaRegisterService = multimediaRegisterService;
		this.dataCountService = dataCountService;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/registers/hh")
	@ResponseBody
	public ResponseEntity<HHRegisterDTO> hhRegister(@RequestParam("anm-id") String anmIdentifier) {
		HHRegister hhRegister = hhRegisterService.getHHRegisterForProvider(anmIdentifier);
		return new ResponseEntity<>(hhRegisterMapper.mapToDTO(hhRegister), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/registers/ec")
	@ResponseBody
	public ResponseEntity<ELCORegisterDTO> ecRegister(@RequestParam("anm-id") String anmIdentifier) {
		ELCORegister ecRegister = ecRegisterService.getELCORegisterForProvider(anmIdentifier);
		return new ResponseEntity<>(ecRegisterMapper.mapToDTO(ecRegister), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/registers/household")
	@ResponseBody
	public ResponseEntity<HHRegisterDTO> householdRegister(@RequestParam("start-date") String startdate, @RequestParam("end-date") String enddate) {
		HHRegister hhRegister = hhRegisterService.getHHRegister("HouseHold", startdate, enddate);
		return new ResponseEntity<>(hhRegisterMapper.mapToDTO(hhRegister), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/registers/elco")
	@ResponseBody
	public ResponseEntity<ELCORegisterDTO> elcoRegister(@RequestParam("start-date") String startdate, @RequestParam("end-date") String enddate) {
		ELCORegister ecRegister = ecRegisterService.getELCORegister("Elco", startdate, enddate);
		return new ResponseEntity<>(ecRegisterMapper.mapToDTO(ecRegister), HttpStatus.OK);
	}


	@RequestMapping(method = RequestMethod.GET, value = "/registers/anc")
	@ResponseBody
	public ResponseEntity<ANC_RegisterDTO> ancRegister(@RequestParam("start-date") String startdate, @RequestParam("end-date") String enddate) {
		ANCRegister ancRegister = ancRegisterService.getANCRegister("Mother", startdate, enddate);
		return new ResponseEntity<>(ancRegisterMapper.mapToDTO(ancRegister), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/registers/ancs")
	@ResponseBody
	public ResponseEntity<ANC_RegisterDTO> ancsRegister(@RequestParam("anm-id") String anmIdentifier) {
		ANCRegister ancRegister = ancRegisterService.getANCRegisterForProvider(anmIdentifier);
		return new ResponseEntity<>(ancRegisterMapper.mapToDTO(ancRegister), HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/registers/child")
	@ResponseBody
	public ResponseEntity<Child_RegisterDTO> childRegister(@RequestParam("start-date") String startdate, @RequestParam("end-date") String enddate) {
		ChildRegister childRegister = childRegisterService.getChildRegister("Child", startdate, enddate);
		return new ResponseEntity<>(childRegisterMapper.mapToDTO(childRegister), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/registers/childs")
	@ResponseBody
	public ResponseEntity<Child_RegisterDTO> childsRegister(@RequestParam("anm-id") String anmIdentifier) {
		ChildRegister childRegister = childRegisterService.getChildRegisterForProvider(anmIdentifier);
		return new ResponseEntity<>(childRegisterMapper.mapToDTO(childRegister), HttpStatus.OK);

	}
    
    @RequestMapping(method = RequestMethod.GET, value = "/registers/data-count")
    @ResponseBody
    public ResponseEntity<List<CountServiceDTO>>  getHouseHoldInformation(@RequestParam("anm-id") String provider,@RequestParam("start-month") String startMonth,@RequestParam("end-month") String endMonth,
    		@RequestParam("start-week") String startWeek,@RequestParam("end-week") String endtWeek,@RequestParam("type") String type){
    	dataCountService.getHHCountInformation(provider,startMonth,endMonth,startWeek,endtWeek,type);
    	return new ResponseEntity<>(dataCountService.getHHCountInformation(provider,startMonth,endMonth,startWeek,endtWeek,type), HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/registers/form-count")
    @ResponseBody
    public ResponseEntity<FormCountDTO>  getFormCountInformation(@RequestParam("anm-id") String provider){
    	return new ResponseEntity<>(dataCountService.getFormCount(provider), HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/registers/hh-count")
    @ResponseBody
    public ResponseEntity<List<CountServiceDTOForChart>>  getHouseHoldInformation(@RequestParam("provider") String provider, @RequestParam("district") String district,
    														@RequestParam("upazilla") String upazilla, @RequestParam("union") String union){
    
    	return new ResponseEntity<>(dataCountService.getHHCountInformation(), HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/registers/hh-data-count")
    @ResponseBody
    public ResponseEntity<List<CountServiceDTOForChart>>  getHouseHoldInformationForChart(@RequestParam("provider") String provider, @RequestParam("district") String district,
    														@RequestParam("upazilla") String upazilla, @RequestParam("union") String union){
    
    	return new ResponseEntity<>(dataCountService.getHHCountInformationForChart(provider, district, upazilla, union), HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/registers/elco-count")
    @ResponseBody
    public ResponseEntity<List<CountServiceDTOForChart>>  getElcoInformation(@RequestParam("provider") String provider, @RequestParam("district") String district,
									@RequestParam("upazilla") String upazilla, @RequestParam("union") String union){
    	return new ResponseEntity<>(dataCountService.getElcoCountInformation(), HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/registers/elco-data-count")
    @ResponseBody
    public ResponseEntity<List<CountServiceDTOForChart>>  getElcoInformationForChart(@RequestParam("provider") String provider, @RequestParam("district") String district,
									@RequestParam("upazilla") String upazilla, @RequestParam("union") String union){
    	return new ResponseEntity<>(dataCountService.getElcoCountInformationForChart(provider, district, upazilla, union), HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/registers/pw-count")
    @ResponseBody
    public ResponseEntity<List<CountServiceDTOForChart>>  getPWInformation(@RequestParam("provider") String provider, @RequestParam("district") String district,
			@RequestParam("upazilla") String upazilla, @RequestParam("union") String union){
    	return new ResponseEntity<>(dataCountService.getMotherCountInformation(), HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/registers/pw-data-count")
    @ResponseBody
    public ResponseEntity<List<CountServiceDTOForChart>>  getPWInformationForChart(@RequestParam("provider") String provider, @RequestParam("district") String district,
			@RequestParam("upazilla") String upazilla, @RequestParam("union") String union){
    	return new ResponseEntity<>(dataCountService.getMotherCountInformationForChart(provider, district, upazilla, union), HttpStatus.OK);
    }

	/*
	 * private ANCRegisterService ancRegisterService; private PNCRegisterService
	 * pncRegisterService; private ECRegisterService ecRegisterService; private
	 * ChildRegisterService childRegisterService; private FPRegisterService
	 * fpRegisterService;e private ANCRegisterMapper ancRegisterMapper; private
	 * ECRegisterMapper ecRegisterMapper; private ChildRegisterMapper
	 * childRegisterMapper; private FPRegisterMapper fpRegisterMapper; private
	 * PNCRegisterMapper pncRegisterMapper;
	 * 
	 * @Autowired public RegisterController(ANCRegisterService
	 * ancRegisterService, PNCRegisterService pncRegisterService,
	 * ECRegisterService ecRegisterService, ChildRegisterService
	 * childRegisterService, FPRegisterService fpRegisterService,
	 * ANCRegisterMapper ancRegisterMapper, ECRegisterMapper ecRegisterMapper,
	 * ChildRegisterMapper childRegisterMapper, FPRegisterMapper
	 * fpRegisterMapper, PNCRegisterMapper pncRegisterMapper) {
	 * this.ancRegisterService = ancRegisterService; this.ecRegisterService =
	 * ecRegisterService; this.pncRegisterService = pncRegisterService;
	 * this.childRegisterService = childRegisterService; this.fpRegisterService
	 * = fpRegisterService; this.ancRegisterMapper = ancRegisterMapper;
	 * this.ecRegisterMapper = ecRegisterMapper; this.childRegisterMapper =
	 * childRegisterMapper; this.fpRegisterMapper = fpRegisterMapper;
	 * this.pncRegisterMapper = pn@RequestMapping(method = GET, value =
	 * "/registers/ec")
	 * 
	 * @ResponseBody public ResponseEntity<ECRegisterDTO>
	 * ecRegister(@RequestParam("anm-id") String anmIdentifier) { ECRegister
	 * ecRegister = ecRegisterService.getRegisterForANM(anmIdentifier); return
	 * new ResponseEntity<>(ecRegisterMapper.mapToDTO(ecRegister),
	 * HttpStatus.OK); }
	 * 
	 * @RequestMapping(method = GET, value = "/registers/anc")
	 * 
	 * @ResponseBody public ResponseEntity<ANCRegisterDTO>
	 * ancRegister(@RequestParam("anm-id") String anmIdentifier) { ANCRegister
	 * ancRegister = ancRegisterService.getRegisterForANM(anmIdentifier); return
	 * new ResponseEntity<>(ancRegisterMapper.mapToDTO(ancRegister),
	 * HttpStatus.OK); }cRegisterMapper; }
	 * 
	 * @RequestMapping(method = GET, value = "/registers/ec")
	 * 
	 * @ResponseBody public ResponseEntity<ECRegisterDTO>
	 * ecRegister(@RequestParam("anm-id") String anmIdentifier) { ECRegister
	 * ecRegister = ecRegisterService.getRegisterForANM(anmIdentifier); return
	 * new ResponseEntity<>(ecRegisterMapper.mapToDTO(ecRegister),
	 * HttpStatus.OK); }
	 */

	@RequestMapping(method = RequestMethod.GET, value = "/getMultimedia")
	@ResponseBody
	public ResponseEntity<String> getMultimedia() {
		// multimediaRegisterService.getMultimedia();
		return new ResponseEntity<>("Welcome to multimedia service", HttpStatus.OK);
	}

	/*
	 * @RequestMapping(method = GET, value = "/registers/child")
	 * 
	 * @ResponseBody public ResponseEntity<ChildRegisterDTO>
	 * childRegister(@RequestParam("anm-id") String anmIdentifier) {
	 * ChildRegister childRegister =
	 * childRegisterService.getRegisterForANM(anmIdentifier); return new
	 * ResponseEntity<>(childRegisterMapper.mapToDTO(childRegister),
	 * HttpStatus.OK); }
	 * 
	 * @RequestMapping(method = GET, value = "/registers/fp")
	 * 
	 * @ResponseBody public ResponseEntity<FPRegisterDTO>
	 * fpRegister(@RequestParam("anm-id") String anmIdentifier) { FPRegister
	 * fpRegister = fpRegisterService.getRegisterForANM(anmIdentifier); return
	 * new ResponseEntity<>(fpRegisterMapper.mapToDTO(fpRegister),
	 * HttpStatus.OK);
	 * 
	 * }
	 * 
	 * @RequestMapping(method = GET, value = "/registers/pnc")
	 * 
	 * @ResponseBody public ResponseEntity<PNCRegisterDTO>
	 * pncRegister(@RequestParam("anm-id") String anmIdentifier) { PNCRegister
	 * pncRegister = pncRegisterService.getRegisterForANM(anmIdentifier); return
	 * new ResponseEntity<>(pncRegisterMapper.mapToDTO(pncRegister),
	 * HttpStatus.OK); }
	 */

}
