/***
 * @author proshanto
 * */
package org.opensrp.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.ektorp.ViewResult;
import org.opensrp.common.util.DateUtil;
import org.opensrp.common.util.WeekBoundariesAndTimestamps;
import org.opensrp.dto.CountServiceDTO;
import org.opensrp.dto.FormCountDTO;
import org.opensrp.dto.CountServiceDTOForChart;
import org.opensrp.form.repository.AllFormSubmissions;
import org.opensrp.register.mcare.repository.AllElcos;
import org.opensrp.register.mcare.repository.AllHouseHolds;
import org.opensrp.register.mcare.repository.AllMothers;
import org.opensrp.rest.services.LuceneElcoService;
import org.opensrp.rest.services.LuceneFormService;
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
	private LuceneFormService luceneFormService;
	private LuceneElcoService luceneElcoService;
	private LuceneMotherService luceneMotherService;
	private AllMothers allMothers;
	private AllFormSubmissions allFormSubmissions;
	@Autowired
	public DataCountService(AllHouseHolds allHouseHolds,LuceneHouseHoldService luceneHouseHoldService, 
			LuceneFormService luceneFormService,LuceneElcoService luceneElcoService,AllElcos allElcos,
			AllMothers allMothers,LuceneMotherService luceneMotherService,AllFormSubmissions allFormSubmissions){
		this.allHouseHolds = allHouseHolds;
		this.luceneHouseHoldService = luceneHouseHoldService;
		this.luceneElcoService = luceneElcoService;
		this.allElcos = allElcos;
		this.allMothers = allMothers;
		this.luceneMotherService = luceneMotherService;
		this.luceneFormService = luceneFormService;
		this.allFormSubmissions = allFormSubmissions;
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
	
	public FormCountDTO getFormCount(String provider){
		FormCountDTO formCountDTO = new FormCountDTO();
		
		Calendar c = Calendar.getInstance();   // this takes current date
	    c.set(Calendar.DAY_OF_MONTH,
                c.getActualMinimum(Calendar.DAY_OF_MONTH));
	     
	    String week1Start= new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	    System.out.println("week1Start: " + week1Start);
	    
	    c.add(Calendar.DATE, 6);
	    String week1End= new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	    System.out.println("week1End: " + week1End);
	    
	    c.add(Calendar.DATE, 1);
	    String week2Start= new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	    System.out.println("week2Start: " + week2Start);
	    
	    c.add(Calendar.DATE, 6);
	    String week2End= new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	    System.out.println("week2End: " + week2End);
	    
	    c.add(Calendar.DATE, 1);
	    String week3Start= new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	    System.out.println("week3Start: " + week3Start);
	    
	    c.add(Calendar.DATE, 6);
	    String week3End= new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	    System.out.println("week3End: " + week3End);
	    
	    c.add(Calendar.DATE, 1);
	    String week4Start= new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	    System.out.println("week4Start: " + week4Start);
		
	    c.set(Calendar.DAY_OF_MONTH,
                c.getActualMaximum(Calendar.DAY_OF_MONTH));
	    
	    String week4End= new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	    System.out.println("week4Start: " + week4End);
		
		/*System.out.println("Total "+luceneFormService.getFormCount("","",provider,formName));
		System.out.println("ThisMonth "+luceneFormService.getFormCount(week1Start, week4End, provider, formName));
		System.out.println("Week1 "+luceneFormService.getFormCount(week1Start, week1End, provider, formName));	
		System.out.println("Week2 "+luceneFormService.getFormCount(week2Start, week2End, provider, formName));	
		System.out.println("Week3 "+luceneFormService.getFormCount(week3Start, week3End, provider, formName));	
		System.out.println("Week4 "+luceneFormService.getFormCount(week4Start, week4End, provider, formName));*/	
		
		formCountDTO.setHouseholdTotalCount(luceneFormService.getFormCount("","",provider,"new_household_registration")); 
		formCountDTO.setHouseholdThisMonthCount(luceneFormService.getFormCount(week1Start, week4End, provider, "new_household_registration"));
		formCountDTO.setHouseholdWeek1Count(luceneFormService.getFormCount(week1Start, week1End, provider, "new_household_registration"));
		formCountDTO.setHouseholdWeek2Count(luceneFormService.getFormCount(week2Start, week2End, provider, "new_household_registration"));
		formCountDTO.setHouseholdWeek3Count(luceneFormService.getFormCount(week3Start, week3End, provider, "new_household_registration"));
		formCountDTO.setHouseholdWeek4Count(luceneFormService.getFormCount(week4Start, week4End, provider, "new_household_registration"));
		
		formCountDTO.setPsrfTotalCount(luceneFormService.getFormCount("","",provider, "psrf_form")); 
		formCountDTO.setPsrfThisMonthCount(luceneFormService.getFormCount(week1Start, week4End, provider, "psrf_form"));
		formCountDTO.setPsrfWeek1Count(luceneFormService.getFormCount(week1Start, week1End, provider, "psrf_form"));
		formCountDTO.setPsrfWeek2Count(luceneFormService.getFormCount(week2Start, week2End, provider, "psrf_form"));
		formCountDTO.setPsrfWeek3Count(luceneFormService.getFormCount(week3Start, week3End, provider, "psrf_form"));
		formCountDTO.setPsrfWeek4Count(luceneFormService.getFormCount(week4Start, week4End, provider, "psrf_form"));
		
		formCountDTO.setCensusTotalCount(luceneFormService.getFormCount("","",provider, "census_enrollment_form")); 
		formCountDTO.setCensusThisMonthCount(luceneFormService.getFormCount(week1Start, week4End, provider, "census_enrollment_form"));
		formCountDTO.setCensusWeek1Count(luceneFormService.getFormCount(week1Start, week1End, provider, "census_enrollment_form"));
		formCountDTO.setCensusWeek2Count(luceneFormService.getFormCount(week2Start, week2End, provider, "census_enrollment_form"));
		formCountDTO.setCensusWeek3Count(luceneFormService.getFormCount(week3Start, week3End, provider, "census_enrollment_form"));
		formCountDTO.setCensusWeek4Count(luceneFormService.getFormCount(week4Start, week4End, provider, "census_enrollment_form"));
		
		
		int anc1TotalCount = luceneFormService.getFormCount("","",provider, "anc_reminder_visit_1"); 
		int anc1ThisMonthCount = luceneFormService.getFormCount(week1Start, week4End, provider, "anc_reminder_visit_1");
		int anc1Week1Count = luceneFormService.getFormCount(week1Start, week1End, provider, "anc_reminder_visit_1");
		int anc1Week2Count = luceneFormService.getFormCount(week2Start, week2End, provider, "anc_reminder_visit_1");
		int anc1Week3Count = luceneFormService.getFormCount(week3Start, week3End, provider, "anc_reminder_visit_1");
		int anc1Week4Count = luceneFormService.getFormCount(week4Start, week4End, provider, "anc_reminder_visit_1");
		
		int anc2TotalCount = luceneFormService.getFormCount("","",provider, "anc_reminder_visit_2"); 
		int anc2ThisMonthCount = luceneFormService.getFormCount(week1Start, week4End, provider, "anc_reminder_visit_2");
		int anc2Week1Count = luceneFormService.getFormCount(week1Start, week1End, provider, "anc_reminder_visit_2");
		int anc2Week2Count = luceneFormService.getFormCount(week2Start, week2End, provider, "anc_reminder_visit_2");
		int anc2Week3Count = luceneFormService.getFormCount(week3Start, week3End, provider, "anc_reminder_visit_2");
		int anc2Week4Count = luceneFormService.getFormCount(week4Start, week4End, provider, "anc_reminder_visit_2");
		
		int anc3TotalCount = luceneFormService.getFormCount("","",provider, "anc_reminder_visit_3"); 
		int anc3ThisMonthCount = luceneFormService.getFormCount(week1Start, week4End, provider, "anc_reminder_visit_3");
		int anc3Week1Count = luceneFormService.getFormCount(week1Start, week1End, provider, "anc_reminder_visit_3");
		int anc3Week2Count = luceneFormService.getFormCount(week2Start, week2End, provider, "anc_reminder_visit_3");
		int anc3Week3Count = luceneFormService.getFormCount(week3Start, week3End, provider, "anc_reminder_visit_3");
		int anc3Week4Count = luceneFormService.getFormCount(week4Start, week4End, provider, "anc_reminder_visit_3");
		
		int anc4TotalCount = luceneFormService.getFormCount("","",provider, "anc_reminder_visit_4"); 
		int anc4ThisMonthCount = luceneFormService.getFormCount(week1Start, week4End, provider, "anc_reminder_visit_4");
		int anc4Week1Count = luceneFormService.getFormCount(week1Start, week1End, provider, "anc_reminder_visit_4");
		int anc4Week2Count = luceneFormService.getFormCount(week2Start, week2End, provider, "anc_reminder_visit_4");
		int anc4Week3Count = luceneFormService.getFormCount(week3Start, week3End, provider, "anc_reminder_visit_4");
		int anc4Week4Count = luceneFormService.getFormCount(week4Start, week4End, provider, "anc_reminder_visit_4");
		
		int ancTotalCount = anc1TotalCount + anc2TotalCount + anc3TotalCount + anc4TotalCount;
		int ancThisMonthCount = anc1ThisMonthCount + anc2ThisMonthCount + anc3ThisMonthCount + anc4ThisMonthCount;
		int ancWeek1Count = anc1Week1Count + anc2Week1Count + anc3Week1Count + anc4Week1Count;
		int ancWeek2Count = anc1Week2Count + anc2Week2Count + anc3Week2Count + anc4Week2Count;
		int ancWeek3Count = anc1Week3Count + anc2Week3Count + anc3Week3Count + anc4Week3Count;
		int ancWeek4Count = anc1Week4Count + anc2Week4Count + anc3Week4Count + anc4Week4Count;
		
		formCountDTO.setAncTotalCount(ancTotalCount); 
		formCountDTO.setAncThisMonthCount(ancThisMonthCount);
		formCountDTO.setAncWeek1Count(ancWeek1Count);
		formCountDTO.setAncWeek2Count(ancWeek2Count);
		formCountDTO.setAncWeek3Count(ancWeek3Count);
		formCountDTO.setAncWeek4Count(ancWeek4Count);
		
		
		int pnc1TotalCount = luceneFormService.getFormCount("","",provider, "pnc_reminder_visit_1"); 
		int pnc1ThisMonthCount = luceneFormService.getFormCount(week1Start, week4End, provider, "pnc_reminder_visit_1");
		int pnc1Week1Count = luceneFormService.getFormCount(week1Start, week1End, provider, "pnc_reminder_visit_1");
		int pnc1Week2Count = luceneFormService.getFormCount(week2Start, week2End, provider, "pnc_reminder_visit_1");
		int pnc1Week3Count = luceneFormService.getFormCount(week3Start, week3End, provider, "pnc_reminder_visit_1");
		int pnc1Week4Count = luceneFormService.getFormCount(week4Start, week4End, provider, "pnc_reminder_visit_1");
		
		int pnc2TotalCount = luceneFormService.getFormCount("","",provider, "pnc_reminder_visit_2"); 
		int pnc2ThisMonthCount = luceneFormService.getFormCount(week1Start, week4End, provider, "pnc_reminder_visit_2");
		int pnc2Week1Count = luceneFormService.getFormCount(week1Start, week1End, provider, "pnc_reminder_visit_2");
		int pnc2Week2Count = luceneFormService.getFormCount(week2Start, week2End, provider, "pnc_reminder_visit_2");
		int pnc2Week3Count = luceneFormService.getFormCount(week3Start, week3End, provider, "pnc_reminder_visit_2");
		int pnc2Week4Count = luceneFormService.getFormCount(week4Start, week4End, provider, "pnc_reminder_visit_2");
		
		int pnc3TotalCount = luceneFormService.getFormCount("","",provider, "pnc_reminder_visit_3"); 
		int pnc3ThisMonthCount = luceneFormService.getFormCount(week1Start, week4End, provider, "pnc_reminder_visit_3");
		int pnc3Week1Count = luceneFormService.getFormCount(week1Start, week1End, provider, "pnc_reminder_visit_3");
		int pnc3Week2Count = luceneFormService.getFormCount(week2Start, week2End, provider, "pnc_reminder_visit_3");
		int pnc3Week3Count = luceneFormService.getFormCount(week3Start, week3End, provider, "pnc_reminder_visit_3");
		int pnc3Week4Count = luceneFormService.getFormCount(week4Start, week4End, provider, "pnc_reminder_visit_3");
		
		int pncTotalCount = pnc1TotalCount + pnc2TotalCount + pnc3TotalCount ;
		int pncThisMonthCount = pnc1ThisMonthCount + pnc2ThisMonthCount + pnc3ThisMonthCount ;
		int pncWeek1Count = pnc1Week1Count + pnc2Week1Count + pnc3Week1Count ;
		int pncWeek2Count = pnc1Week2Count + pnc2Week2Count + pnc3Week2Count ;
		int pncWeek3Count = pnc1Week3Count + pnc2Week3Count + pnc3Week3Count ;
		int pncWeek4Count = pnc1Week4Count + pnc2Week4Count + pnc3Week4Count ;
		
		formCountDTO.setPncTotalCount(pncTotalCount); 
		formCountDTO.setPncThisMonthCount(pncThisMonthCount);
		formCountDTO.setPncWeek1Count(pncWeek1Count);
		formCountDTO.setPncWeek2Count(pncWeek2Count);
		formCountDTO.setPncWeek3Count(pncWeek3Count);
		formCountDTO.setPncWeek4Count(pncWeek4Count);
		
		
		int encc1TotalCount = luceneFormService.getFormCount("","",provider, "encc_visit_1"); 
		int encc1ThisMonthCount = luceneFormService.getFormCount(week1Start, week4End, provider, "encc_visit_1");
		int encc1Week1Count = luceneFormService.getFormCount(week1Start, week1End, provider, "encc_visit_1");
		int encc1Week2Count = luceneFormService.getFormCount(week2Start, week2End, provider, "encc_visit_1");
		int encc1Week3Count = luceneFormService.getFormCount(week3Start, week3End, provider, "encc_visit_1");
		int encc1Week4Count = luceneFormService.getFormCount(week4Start, week4End, provider, "encc_visit_1");
		
		int encc2TotalCount = luceneFormService.getFormCount("","",provider, "encc_visit_2"); 
		int encc2ThisMonthCount = luceneFormService.getFormCount(week1Start, week4End, provider, "encc_visit_2");
		int encc2Week1Count = luceneFormService.getFormCount(week1Start, week1End, provider, "encc_visit_2");
		int encc2Week2Count = luceneFormService.getFormCount(week2Start, week2End, provider, "encc_visit_2");
		int encc2Week3Count = luceneFormService.getFormCount(week3Start, week3End, provider, "encc_visit_2");
		int encc2Week4Count = luceneFormService.getFormCount(week4Start, week4End, provider, "encc_visit_2");
		
		int encc3TotalCount = luceneFormService.getFormCount("","",provider, "encc_visit_3"); 
		int encc3ThisMonthCount = luceneFormService.getFormCount(week1Start, week4End, provider, "encc_visit_3");
		int encc3Week1Count = luceneFormService.getFormCount(week1Start, week1End, provider, "encc_visit_3");
		int encc3Week2Count = luceneFormService.getFormCount(week2Start, week2End, provider, "encc_visit_3");
		int encc3Week3Count = luceneFormService.getFormCount(week3Start, week3End, provider, "encc_visit_3");
		int encc3Week4Count = luceneFormService.getFormCount(week4Start, week4End, provider, "encc_visit_3");
		
		int enccTotalCount = encc1TotalCount + encc2TotalCount + encc3TotalCount ;
		int enccThisMonthCount = encc1ThisMonthCount + encc2ThisMonthCount + encc3ThisMonthCount ;
		int enccWeek1Count = encc1Week1Count + encc2Week1Count + encc3Week1Count ;
		int enccWeek2Count = encc1Week2Count + encc2Week2Count + encc3Week2Count ;
		int enccWeek3Count = encc1Week3Count + encc2Week3Count + encc3Week3Count ;
		int enccWeek4Count = encc1Week4Count + encc2Week4Count + encc3Week4Count ;
		
		formCountDTO.setEnccTotalCount(enccTotalCount); 
		formCountDTO.setEnccThisMonthCount(enccThisMonthCount);
		formCountDTO.setEnccWeek1Count(enccWeek1Count);
		formCountDTO.setEnccWeek2Count(enccWeek2Count);
		formCountDTO.setEnccWeek3Count(enccWeek3Count);
		formCountDTO.setEnccWeek4Count(enccWeek4Count);

		
		formCountDTO.setPregnancyTotalCount(luceneMotherService.getMotherCount(provider));
		formCountDTO.setPregnancyThisMonthCount(luceneMotherService.getMotherCount(week1Start, week4End, provider));
		formCountDTO.setPregnancyWeek1Count(luceneMotherService.getMotherCount(week1Start, week1End, provider));
		formCountDTO.setPregnancyWeek2Count(luceneMotherService.getMotherCount(week2Start, week2End, provider));
		formCountDTO.setPregnancyWeek3Count(luceneMotherService.getMotherCount(week3Start, week3End, provider));
		formCountDTO.setPregnancyWeek4Count(luceneMotherService.getMotherCount(week4Start, week4End, provider));
		
	    
	    /*formCountDTO.setHouseholdTotalCount(luceneFormService.getFormCount("","",provider,formName)); 
		formCountDTO.setHouseholdThisMonthCount(luceneFormService.getFormCount("2016-11-01", "2016-11-30", provider, formName));
		formCountDTO.setHouseholdWeek1Count(luceneFormService.getFormCount("2016-11-01", "2016-11-07", provider, formName));
		formCountDTO.setHouseholdWeek2Count(luceneFormService.getFormCount("2016-11-08", "2016-11-14", provider, formName));
		formCountDTO.setHouseholdWeek3Count(luceneFormService.getFormCount("2016-11-15", "2016-11-21", provider, formName));
		formCountDTO.setHouseholdWeek4Count(luceneFormService.getFormCount("2016-11-22", "2016-11-30", provider, formName));*/
	    
		return formCountDTO;
	}

}
