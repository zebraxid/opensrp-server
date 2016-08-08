/***
 * @author proshanto
 * */
package org.opensrp.service;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.opensrp.dto.CountServiceDTO;
import org.opensrp.register.mcare.repository.AllHouseHolds;
import org.opensrp.register.mcare.repository.AllWoman;
import org.opensrp.rest.services.LuceneHouseHoldService;
import org.opensrp.rest.services.LuceneWomanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataCountService {
	private static Logger logger = LoggerFactory.getLogger(DataCountService.class);
	private final AllHouseHolds allHouseHolds;
	private LuceneHouseHoldService luceneHouseHoldService;
	private LuceneWomanService luceneWomanService;
	private AllWoman allWoman;
	@Autowired
	public DataCountService(AllHouseHolds allHouseHolds,LuceneHouseHoldService luceneHouseHoldService,
				AllWoman allWoman,LuceneWomanService luceneWomanService){
		this.allHouseHolds = allHouseHolds;
		this.luceneHouseHoldService = luceneHouseHoldService;
		this.allWoman = allWoman;
		this.luceneWomanService = luceneWomanService;
	}
	/**
	 * This method return count data of registers.
	 * @param provider    who sent data
	 * @param startMonth  means start date of a month
	 * @param endMonth    means end day of a month
	 * @param startWeek   means start day of a week
	 * @param endWeek     means end day of a week
	 * */
	public List<CountServiceDTO> getHHCountInformation(String provider,String startMonth,String endMonth,String startWeek,String endWeek,String type){
		List<CountServiceDTO> commonServiceDTOs = new ArrayList<CountServiceDTO>();
		
		CountServiceDTO commonServiceDTO = new CountServiceDTO();
		if(type.equalsIgnoreCase("all")){
			this.getHouseholdCount(provider, startMonth, endMonth, startWeek, endWeek, commonServiceDTO);
			this.getWomanCount(provider, startMonth, endMonth, startWeek, endWeek, commonServiceDTO);
					
		}else if(type.equalsIgnoreCase("household")){
			this.getHouseholdCount(provider, startMonth, endMonth, startWeek, endWeek, commonServiceDTO);
			
		}else if(type.equalsIgnoreCase("mother")){
			this.getWomanCount(provider, startMonth, endMonth, startWeek, endWeek, commonServiceDTO);
			
		}else{
			
		}		
		commonServiceDTOs.add(commonServiceDTO);
		return commonServiceDTOs;
		
	}
	
	private CountServiceDTO getHouseholdCount(String provider,String startMonth,String endMonth,String startWeek,String endWeek,CountServiceDTO commonServiceDTO){
		commonServiceDTO.setHouseholdTotalCount(allHouseHolds.findAllHouseHolds().size()) ;
		commonServiceDTO.setHouseholdTodayCount(luceneHouseHoldService.getHouseholdCount("",""));
		commonServiceDTO.setHouseholdThisMonthCount(luceneHouseHoldService.getHouseholdCount(startMonth, endMonth));
		commonServiceDTO.setHouseholdThisWeekCount(luceneHouseHoldService.getHouseholdCount(startWeek, endWeek));
		return commonServiceDTO;
	}
	private CountServiceDTO getWomanCount(String provider,String startMonth,String endMonth,String startWeek,String endWeek,CountServiceDTO commonServiceDTO){
		commonServiceDTO.setPwTotalCount(allWoman.allOpenWoman().size());
		commonServiceDTO.setPwThisMonthCount(luceneWomanService.getWomanCount(startMonth, endMonth));
		commonServiceDTO.setPwThisWeekCount(luceneWomanService.getWomanCount(startMonth, endMonth));
		commonServiceDTO.setPwTodayCount(luceneWomanService.getWomanCount("", ""));
		return commonServiceDTO;
	}

}
