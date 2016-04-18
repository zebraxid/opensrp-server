package org.opensrp.service;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.opensrp.dto.CountServiceDTO;
import org.opensrp.register.mcare.repository.AllElcos;
import org.opensrp.register.mcare.repository.AllHouseHolds;
import org.opensrp.rest.services.LuceneElcoService;
import org.opensrp.rest.services.LuceneHouseHoldService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataCountService {
	private static Logger logger = LoggerFactory.getLogger(DataCountService.class);
	private final AllHouseHolds allHouseHolds;
	private final AllElcos allElcos;
	private LuceneHouseHoldService luceneHouseHoldService;
	private LuceneElcoService luceneElcoService;
	@Autowired
	public DataCountService(AllHouseHolds allHouseHolds,LuceneHouseHoldService luceneHouseHoldService,
			LuceneElcoService luceneElcoService,AllElcos allElcos){
		this.allHouseHolds = allHouseHolds;
		this.luceneHouseHoldService = luceneHouseHoldService;
		this.luceneElcoService = luceneElcoService;
		this.allElcos = allElcos;
	}
	public List<CountServiceDTO> getHHCountInformation(String provider,String startMonth,String endMonth,String startWeek,String endWeek){
		List<CountServiceDTO> commonServiceDTOs = new ArrayList<CountServiceDTO>();
		CountServiceDTO commonServiceDTO = new CountServiceDTO();
		commonServiceDTO.householdTotalCount = allHouseHolds.findAllHouseHolds().size();
		commonServiceDTO.householdTodayCount = luceneHouseHoldService.getHouseholdCount("","");
		commonServiceDTO.householdThisMonthCount = luceneHouseHoldService.getHouseholdCount(startMonth, endMonth);
		commonServiceDTO.householdThisWeekCount = luceneHouseHoldService.getHouseholdCount(startWeek, endWeek);
		commonServiceDTO.elcoTotalCount = allElcos.allOpenELCOs().size();
		commonServiceDTO.elcoThisMonthCount = luceneElcoService.getElcoCount(startMonth, endMonth);
		commonServiceDTO.elcoThisWeekCount = luceneElcoService.getElcoCount(startMonth, endMonth);
		commonServiceDTO.elcoTodayCount = luceneElcoService.getElcoCount("", "");
		logger.info(allHouseHolds.allOpenHHsForProvider(provider).toString());
		//commonServiceDTO.
		//commonServiceDTO. //= luceneHouseHoldService.getHouseholdCount();
		commonServiceDTOs.add(commonServiceDTO);
		return commonServiceDTOs;
		
	}

}
