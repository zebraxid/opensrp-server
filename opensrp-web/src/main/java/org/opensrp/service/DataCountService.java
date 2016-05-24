/***
 * @author proshanto
 * */
package org.opensrp.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.opensrp.common.util.DateUtil;
import org.opensrp.dto.CountServiceDTO;
import org.opensrp.dto.CountServiceDTOForChart;
import org.opensrp.register.mcare.repository.AllElcos;
import org.opensrp.register.mcare.repository.AllHouseHolds;
import org.opensrp.register.mcare.repository.AllMothers;
import org.opensrp.rest.services.LuceneElcoService;
import org.opensrp.rest.services.LuceneHouseHoldService;
import org.opensrp.rest.services.LuceneMotherService;
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
	private LuceneMotherService luceneMotherService;
	private AllMothers allMothers;
	@Autowired
	public DataCountService(AllHouseHolds allHouseHolds,LuceneHouseHoldService luceneHouseHoldService,
			LuceneElcoService luceneElcoService,AllElcos allElcos,AllMothers allMothers,LuceneMotherService luceneMotherService){
		this.allHouseHolds = allHouseHolds;
		this.luceneHouseHoldService = luceneHouseHoldService;
		this.luceneElcoService = luceneElcoService;
		this.allElcos = allElcos;
		this.allMothers = allMothers;
		this.luceneMotherService = luceneMotherService;
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
			this.getElcoCount(provider, startMonth, endMonth, startWeek, endWeek, commonServiceDTO);
			this.getMotherCount(provider, startMonth, endMonth, startWeek, endWeek, commonServiceDTO);
					
		}else if(type.equalsIgnoreCase("household")){
			this.getHouseholdCount(provider, startMonth, endMonth, startWeek, endWeek, commonServiceDTO);
			
		}else if(type.equalsIgnoreCase("elco")){
			this.getElcoCount(provider, startMonth, endMonth, startWeek, endWeek, commonServiceDTO);
			
		}else if(type.equalsIgnoreCase("mother")){
			this.getMotherCount(provider, startMonth, endMonth, startWeek, endWeek, commonServiceDTO);
			
		}else{
			
		}		
		commonServiceDTOs.add(commonServiceDTO);
		return commonServiceDTOs;
		
	}
	
	public List<CountServiceDTOForChart> getHHCountInformationForChart(){
		List<CountServiceDTOForChart> DTOs= new ArrayList<CountServiceDTOForChart>();
		CountServiceDTOForChart newDTO = new CountServiceDTOForChart();
		List<String> startAndEndOfWeeks = DateUtil.getWeekBoundariesForDashboard();
		this.getHouseholdCountForChart(startAndEndOfWeeks, newDTO);
		DTOs.add(newDTO);
		return DTOs;
	}
	
	/*private CountServiceDTOForChart loadChartDataForAMonth(int beginIndex, int endIndex, CountServiceDTOForChart dto){
		return dto;
	}*/
		
	private CountServiceDTOForChart getHouseholdCountForChart(List<String> dates, CountServiceDTOForChart dto){
		//dto.setCurrentMonthWeek1(luceneHouseHoldService.getHouseholdCountForChart("2016-05-01","2016-05-07"));
		dto.setCurrentMonthWeek1(luceneHouseHoldService.getHouseholdCountForChart(dates.get(0), dates.get(1)));
		dto.setCurrentMonthWeek2(luceneHouseHoldService.getHouseholdCountForChart(dates.get(2), dates.get(3)));
		dto.setCurrentMonthWeek3(luceneHouseHoldService.getHouseholdCountForChart(dates.get(4), dates.get(5)));
		dto.setCurrentMonthWeek4(luceneHouseHoldService.getHouseholdCountForChart(dates.get(6), dates.get(7)));
		Calendar calendar = GregorianCalendar.getInstance();
		if(calendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH) > 28){
			dto.setCurrentMonthWeek5(luceneHouseHoldService.getHouseholdCountForChart(dates.get(8), dates.get(9)));
		}
		else{
			dto.setCurrentMonthWeek5(0);
		}
		
		dto.setCurrentMonth_1Week1(luceneHouseHoldService.getHouseholdCountForChart(dates.get(10), dates.get(11)));
		dto.setCurrentMonth_1Week2(luceneHouseHoldService.getHouseholdCountForChart(dates.get(12), dates.get(13)));
		dto.setCurrentMonth_1Week3(luceneHouseHoldService.getHouseholdCountForChart(dates.get(14), dates.get(15)));
		dto.setCurrentMonth_1Week4(luceneHouseHoldService.getHouseholdCountForChart(dates.get(16), dates.get(17)));
		calendar.add(GregorianCalendar.MONTH, -1);
		if(calendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH) > 28){
			dto.setCurrentMonth_1Week5(luceneHouseHoldService.getHouseholdCountForChart(dates.get(18), dates.get(19)));
		}
		else{
			dto.setCurrentMonth_1Week5(0);
		}
		
		dto.setCurrentMonth_2Week1(luceneHouseHoldService.getHouseholdCountForChart(dates.get(20), dates.get(21)));
		dto.setCurrentMonth_2Week2(luceneHouseHoldService.getHouseholdCountForChart(dates.get(22), dates.get(23)));
		dto.setCurrentMonth_2Week3(luceneHouseHoldService.getHouseholdCountForChart(dates.get(24), dates.get(25)));
		dto.setCurrentMonth_2Week4(luceneHouseHoldService.getHouseholdCountForChart(dates.get(26), dates.get(27)));
		calendar.add(GregorianCalendar.MONTH, -1);
		if(calendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH) > 28){
			dto.setCurrentMonth_2Week5(luceneHouseHoldService.getHouseholdCountForChart(dates.get(28), dates.get(29)));
		}
		else{
			dto.setCurrentMonth_2Week5(0);
		}
		
		dto.setCurrentMonth_3Week1(luceneHouseHoldService.getHouseholdCountForChart(dates.get(30), dates.get(31)));
		dto.setCurrentMonth_3Week2(luceneHouseHoldService.getHouseholdCountForChart(dates.get(32), dates.get(33)));
		dto.setCurrentMonth_3Week3(luceneHouseHoldService.getHouseholdCountForChart(dates.get(34), dates.get(35)));
		dto.setCurrentMonth_3Week4(luceneHouseHoldService.getHouseholdCountForChart(dates.get(36), dates.get(37)));
		calendar.add(GregorianCalendar.MONTH, -1);
		if(calendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH) > 28){
			dto.setCurrentMonth_3Week5(luceneHouseHoldService.getHouseholdCountForChart(dates.get(38), dates.get(39)));
		}
		else{
			dto.setCurrentMonth_3Week5(35);
		}
		
		return dto;
	}
	
	private CountServiceDTO getHouseholdCount(String provider,String startMonth,String endMonth,String startWeek,String endWeek,CountServiceDTO commonServiceDTO){
		commonServiceDTO.setHouseholdTotalCount(allHouseHolds.findAllHouseHolds().size()) ;  //this should be improved using count(*) style query
		commonServiceDTO.setHouseholdTodayCount(luceneHouseHoldService.getHouseholdCount("",""));
		commonServiceDTO.setHouseholdThisMonthCount(luceneHouseHoldService.getHouseholdCount(startMonth, endMonth));
		commonServiceDTO.setHouseholdThisWeekCount(luceneHouseHoldService.getHouseholdCount(startWeek, endWeek));
		return commonServiceDTO;
	}
	private CountServiceDTO getElcoCount(String provider,String startMonth,String endMonth,String startWeek,String endWeek,CountServiceDTO commonServiceDTO){
		commonServiceDTO.setElcoTotalCount(allElcos.allOpenELCOs().size());
		commonServiceDTO.setElcoThisMonthCount(luceneElcoService.getElcoCount(startMonth, endMonth));
		commonServiceDTO.setElcoThisWeekCount(luceneElcoService.getElcoCount(startMonth, endMonth));
		commonServiceDTO.setElcoTodayCount(luceneElcoService.getElcoCount("", ""));
		return commonServiceDTO;
	}
	private CountServiceDTO getMotherCount(String provider,String startMonth,String endMonth,String startWeek,String endWeek,CountServiceDTO commonServiceDTO){
		commonServiceDTO.setPwTotalCount(allMothers.allOpenMothers().size());
		commonServiceDTO.setPwThisMonthCount(luceneMotherService.getMotherCount(startMonth, endMonth));
		commonServiceDTO.setPwThisWeekCount(luceneMotherService.getMotherCount(startMonth, endMonth));
		commonServiceDTO.setPwTodayCount(luceneMotherService.getMotherCount("", ""));
		return commonServiceDTO;
	}

}
