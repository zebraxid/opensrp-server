/***
 * @author proshanto
 * */
package org.opensrp.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.ektorp.ViewResult;
import org.opensrp.common.util.DateUtil;
import org.opensrp.common.util.WeekBoundariesAndTimestamps;
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
	
	public List<CountServiceDTOForChart> getHHCountInformation(){
		ViewResult hhViewResult;	
		hhViewResult = allHouseHolds.allHHsCreatedLastFourMonthsViewResult();		
		
		List<CountServiceDTOForChart> DTOs= new ArrayList<CountServiceDTOForChart>();
		CountServiceDTOForChart newDTO = new CountServiceDTOForChart();
		newDTO.setCounts(this.convertViewResultToCount(hhViewResult));
		DTOs.add(newDTO);
		return DTOs;
	}
	
	public List<CountServiceDTOForChart> getHHCountInformationForChart(String provider, String district, String upazilla, String union){
		ViewResult hhViewResult;		
		String key = this.createRawStartKey(provider, district, upazilla, union);
		if(!provider.equalsIgnoreCase("")){
			hhViewResult = allHouseHolds.allHHsCreatedLastFourMonthsByProviderAndLocationViewResult(key,key.substring(0, key.length()-1) + ",{}]");
		}
		else{
			hhViewResult = allHouseHolds.allHHsCreatedLastFourMonthsByLocationViewResult(key,key.substring(0, key.length()-1) + ",{}]");
		}		
		
		List<CountServiceDTOForChart> DTOs= new ArrayList<CountServiceDTOForChart>();
		CountServiceDTOForChart newDTO = new CountServiceDTOForChart();
		newDTO.setCounts(this.convertViewResultToCount(hhViewResult));
		DTOs.add(newDTO);
		return DTOs;
	}
	
	public List<CountServiceDTOForChart> getElcoCountInformation(){
		ViewResult elcoViewResult;	
		elcoViewResult = allElcos.allElcosCreatedLastFourMonthsViewResult();		
		
		List<CountServiceDTOForChart> DTOs= new ArrayList<CountServiceDTOForChart>();
		CountServiceDTOForChart newDTO = new CountServiceDTOForChart();
		newDTO.setCounts(this.convertViewResultToCount(elcoViewResult));
		DTOs.add(newDTO);
		return DTOs;
	}
	
	public List<CountServiceDTOForChart> getElcoCountInformationForChart(String provider, String district, String upazilla, String union){
		ViewResult elcoViewResult;		
		String key = this.createRawStartKey(provider, district, upazilla, union);
		if(!provider.equalsIgnoreCase("")){
			elcoViewResult = allElcos.allElcosCreatedLastFourMonthsByProviderAndLocationViewResult(key,key.substring(0, key.length()-1) + ",{}]");
		}
		else{
			elcoViewResult = allElcos.allElcosCreatedLastFourMonthsByLocationViewResult(key,key.substring(0, key.length()-1) + ",{}]");
		}		
		
		List<CountServiceDTOForChart> DTOs= new ArrayList<CountServiceDTOForChart>();
		CountServiceDTOForChart newDTO = new CountServiceDTOForChart();
		newDTO.setCounts(this.convertViewResultToCount(elcoViewResult));
		DTOs.add(newDTO);
		return DTOs;
	}
	
	public List<CountServiceDTOForChart> getMotherCountInformation(){
		ViewResult elcoViewResult;		

		elcoViewResult = allElcos.allMothersCreatedLastFourMonthsViewResult();		
		
		List<CountServiceDTOForChart> DTOs= new ArrayList<CountServiceDTOForChart>();
		CountServiceDTOForChart newDTO = new CountServiceDTOForChart();
		newDTO.setCounts(this.convertViewResultToCount(elcoViewResult));
		DTOs.add(newDTO);
		return DTOs;
	}
	
	public List<CountServiceDTOForChart> getMotherCountInformationForChart(String provider, String district, String upazilla, String union){
		ViewResult elcoViewResult;		
		String key = this.createRawStartKey(provider, district, upazilla, union);
		if(!provider.equalsIgnoreCase("")){
			elcoViewResult = allElcos.allMothersCreatedLastFourMonthsByProviderAndLocationViewResult(key,key.substring(0, key.length()-1) + ",{}]");
		}
		else{
			elcoViewResult = allElcos.allMothersCreatedLastFourMonthsByLocationViewResult(key,key.substring(0, key.length()-1) + ",{}]");
		}		
		
		List<CountServiceDTOForChart> DTOs= new ArrayList<CountServiceDTOForChart>();
		CountServiceDTOForChart newDTO = new CountServiceDTOForChart();
		newDTO.setCounts(this.convertViewResultToCount(elcoViewResult));
		DTOs.add(newDTO);
		return DTOs;
	}
	
	public int[] getMotherCountInformationForHomePage(){
		ViewResult elcoViewResult;		
		String key = this.createRawStartKey("", "Gaibandha", "", "");
		//elcoViewResult = allElcos.allMothersCreatedLastFourMonthsByLocationViewResult(key,key.substring(0, key.length()-1) + ",{}]");		
		elcoViewResult = allElcos.allMothersCreatedLastFourMonthsViewResult();
		
		return this.convertViewResultToCount(elcoViewResult);
	}
	
	private int[] convertViewResultToCount( ViewResult vr){
		List<Long> timestamps = new ArrayList<Long>();
		int count = 0;
		int[] countsForChart = new int[23];
		Long todayTimestamp = DateUtil.getTimestampToday();
		List<Long> weekBoundaries = DateUtil.getCurrentWeekBoundaries();
		List<Long> montthBoundaries = DateUtil.getMonthBoundaries();
		WeekBoundariesAndTimestamps boundaries = DateUtil.getWeekBoundariesForDashboard();
    	int todayCountIndex = 20, weekCountIndex = 21, monthCountIndex = 22;
    	List<String> startAndEndOfWeeks = boundaries.weekBoundariesAsString;
    	List<Long> startAndEndOfWeeksAsTimestamp = boundaries.weekBoundariesAsTimeStamp;

    	for (ViewResult.Row row : vr.getRows()) {
    		String stringValue = row.getValue(); 
    		count++;
    		timestamps.add(Long.parseLong(stringValue));
    	}
    	System.out.println("number of rows found - " + count);    	
    	
    	//this segment will do the counting
    	for(int i = 0; i < timestamps.size(); i++){
    		countsForChart[DateUtil.dateInsideWhichWeek(timestamps.get(i), startAndEndOfWeeksAsTimestamp)]++;
    		if(DateUtil.ifDateInsideAWeek(timestamps.get(i), todayTimestamp, todayTimestamp)){
    			countsForChart[todayCountIndex]++;
    		}
    		if(DateUtil.ifDateInsideAWeek(timestamps.get(i), weekBoundaries.get(0), weekBoundaries.get(1))){
    			countsForChart[weekCountIndex]++;
    		}
    		if(DateUtil.ifDateInsideAWeek(timestamps.get(i), montthBoundaries.get(0), montthBoundaries.get(0))){    			
    			countsForChart[monthCountIndex]++;
    		}
    	}        
    	int foundCount = 0;
    	for(int i =0; i<countsForChart.length; i++){
        	if(countsForChart[i] != 0){
        		System.out.println("count for weekIndex - " + i + " is " + countsForChart[i]);
        		foundCount += countsForChart[i]; 
        	}        	
        }
    	System.out.println("foundCount - " + foundCount);
		return countsForChart;
	}
	private String createRawStartKey(String provider, String district, String upazilla, String union){
		String key = "";
		if(provider.equalsIgnoreCase("")){
			if(union.equalsIgnoreCase("")){
				if(upazilla.equalsIgnoreCase("")){
					key = "[\"" + district + "\"]";
				}
				else{
					key = "[\"" + district + "\",\"" + upazilla + "\"]";
				}
			}
			else{
				key = "[\"" + district + "\",\"" + upazilla + "\",\"" + union + "\"]";
			}
		}
		else{
			if(union.equalsIgnoreCase("")){
				if(upazilla.equalsIgnoreCase("")){
					if(district.equalsIgnoreCase("")){
						key = "[\"" + provider + "\"]";
					}
					else{
						key = "[\"" + provider + "\",\"" + district + "\"]";
					}
					
				}
				else{
					key = "[\"" + provider + "\",\"" + district + "\",\"" + upazilla + "\"]";
				}
			}
			else{
				key = "[\"" + provider + "\",\"" + district + "\",\"" + upazilla + "\",\"" + union + "\"]";
			}
		
		}
		
		return key;
	}
	
	
	private CountServiceDTOForChart getHouseholdCountForChart(CountServiceDTOForChart dto){		
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
		commonServiceDTO.setElcoTotalCount(allElcos.findAllELCOs().size());
		commonServiceDTO.setElcoThisMonthCount(luceneElcoService.getElcoCount(startMonth, endMonth));
		commonServiceDTO.setElcoThisWeekCount(luceneElcoService.getElcoCount(startWeek, endWeek));
		commonServiceDTO.setElcoTodayCount(luceneElcoService.getElcoCount("", ""));
		return commonServiceDTO;
	}
	private CountServiceDTO getMotherCount(String provider,String startMonth,String endMonth,String startWeek,String endWeek,CountServiceDTO commonServiceDTO){
		commonServiceDTO.setPwTotalCount(allMothers.allOpenMothers().size());
		int[] countsForChart = new int[23];
		countsForChart = this.getMotherCountInformationForHomePage();
		commonServiceDTO.setPwThisMonthCount(countsForChart[15] + countsForChart[16] + countsForChart[17] + countsForChart[18] + countsForChart[19]);//(luceneMotherService.getMotherCount(startMonth, endMonth));
		commonServiceDTO.setPwThisWeekCount(countsForChart[21]);
		commonServiceDTO.setPwTodayCount(countsForChart[20]);
		return commonServiceDTO;
	}

}
