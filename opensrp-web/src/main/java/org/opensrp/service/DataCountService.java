/***
 * @author proshanto
 * */
package org.opensrp.service;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.opensrp.dto.CountServiceDTO;
import org.opensrp.register.mcare.repository.AllChilds;
import org.opensrp.rest.services.LuceneChildService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataCountService {
	
	private static Logger logger = LoggerFactory.getLogger(DataCountService.class);
	
	private final AllChilds allChilds;
	
	private LuceneChildService luceneChildService;
	
	@Autowired
	public DataCountService(AllChilds allChilds, LuceneChildService luceneChildService) {
		this.allChilds = allChilds;
		this.luceneChildService = luceneChildService;
	}
	
	/**
	 * This method return count data of registers.
	 * 
	 * @param provider who sent data
	 * @param startMonth means start date of a month
	 * @param endMonth means end day of a month
	 * @param startWeek means start day of a week
	 * @param endWeek means end day of a week
	 */
	public List<CountServiceDTO> getChildCountInformation(String provider, String startMonth, String endMonth,
	                                                      String startWeek, String endWeek, String type) {
		List<CountServiceDTO> commonServiceDTOs = new ArrayList<CountServiceDTO>();
		
		CountServiceDTO commonServiceDTO = new CountServiceDTO();
		if (type.equalsIgnoreCase("all")) {
			this.getChildCount(provider, startMonth, endMonth, startWeek, endWeek, commonServiceDTO);
		} else if (type.equalsIgnoreCase("household")) {
			this.getChildCount(provider, startMonth, endMonth, startWeek, endWeek, commonServiceDTO);
			
		} else {
			
		}
		commonServiceDTOs.add(commonServiceDTO);
		return commonServiceDTOs;
		
	}
	
	private CountServiceDTO getChildCount(String provider, String startMonth, String endMonth, String startWeek,
	                                      String endWeek, CountServiceDTO commonServiceDTO) {
		commonServiceDTO.setChildTotalCount(allChilds.findAllChilds().size());
		commonServiceDTO.setChildTodayCount(luceneChildService.getChildCount("", ""));
		commonServiceDTO.setChildThisMonthCount(luceneChildService.getChildCount(startMonth, endMonth));
		commonServiceDTO.setChildThisWeekCount(luceneChildService.getChildCount(startWeek, endWeek));
		return commonServiceDTO;
	}
	
}
