/***
 * @author proshanto
 * */
package org.opensrp.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.ektorp.ViewResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.common.util.DateUtil;
import org.opensrp.common.util.HttpResponse;
import org.opensrp.common.util.WeekBoundariesAndTimestamps;
import org.opensrp.connector.Dhis2HttpUtils;
import org.opensrp.dto.CountServiceDTO;
import org.opensrp.dto.VaccineCountDTO;
import org.opensrp.dto.WeeklyDataCount;
import org.opensrp.register.mcare.repository.AllHouseHolds;
import org.opensrp.register.mcare.repository.AllMembers;
import org.opensrp.repository.AllVaccine;
import org.opensrp.rest.services.LuceneHouseHoldService;
import org.opensrp.rest.services.LuceneMembersService;
import org.opensrp.rest.services.LuceneVaccineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataCountService {
	private static Logger logger = LoggerFactory.getLogger(DataCountService.class);
	private final AllHouseHolds allHouseHolds;
	private AllVaccine allVaccine;
	private LuceneHouseHoldService luceneHouseHoldService;
	private LuceneMembersService luceneMembersService;
	private LuceneVaccineService luceneVaccineService;
	private AllMembers allMembers;
	private final String url = "http://103.247.238.74:8081/dhis22/api/dataValueSets";
	@Autowired
	private Dhis2HttpUtils dhis2HttpUtils;
	@Autowired
	public DataCountService(AllHouseHolds allHouseHolds,LuceneHouseHoldService luceneHouseHoldService,
				AllMembers allMembers,LuceneMembersService luceneMembersService,
				AllVaccine allVaccine,LuceneVaccineService luceneVaccineService){
		this.allHouseHolds = allHouseHolds;
		this.luceneHouseHoldService = luceneHouseHoldService;
		this.allMembers = allMembers;
		this.luceneMembersService = luceneMembersService;
		this.allVaccine = allVaccine;
		this.luceneVaccineService = luceneVaccineService;
	}
	
	
	
	public List<VaccineCountDTO> getVaccineCountInformation(String type,String startMonth,String endMonth) throws JSONException{
		List<VaccineCountDTO> commonServiceDTOs = new ArrayList<VaccineCountDTO>();
		
		VaccineCountDTO commonServiceDTO = new VaccineCountDTO();
		
		this.getVaccineCount(type, startMonth, endMonth, commonServiceDTO);
		
			
		
		JSONObject vaccineCountObj =	new JSONObject();
		
		JSONArray vaccineCountArray =	new JSONArray();
		
		Date date = new Date();
		String modifiedDate= new SimpleDateFormat("yyyy-MM-dd").format(date);
		
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH);
		String periodTime =  Integer.toString(year)+Integer.toString(month);
		
		JSONObject vaccineAttrObj1 = new JSONObject();
		vaccineAttrObj1.put("dataElement", "nbQTnNFs1I8");//Bcg given (0-11m)
		vaccineAttrObj1.put("value", commonServiceDTO.getChild_bcgCount());
		
		JSONObject vaccineAttrObj2 = new JSONObject();
		vaccineAttrObj2.put("dataElement", "Na5rrDNtwOW");//Penta 1given (0-11m)
		vaccineAttrObj2.put("value", commonServiceDTO.getChild_penta1Count());
		
		JSONObject vaccineAttrObj3 = new JSONObject();
		vaccineAttrObj3.put("dataElement", "zGQIRoCQIcK");//Penta 2 given (0-11m)
		vaccineAttrObj3.put("value", commonServiceDTO.getChild_penta2Count());
		
		JSONObject vaccineAttrObj4 = new JSONObject();
		vaccineAttrObj4.put("dataElement", "cOP5mAREs38");//Penta 3 given (0-11m)
		vaccineAttrObj4.put("value", commonServiceDTO.getChild_penta3Count());
		
		JSONObject vaccineAttrObj5 = new JSONObject();
		vaccineAttrObj5.put("dataElement", "TzbgFs3CSyp");//OPV 0 given (0-11m)
		vaccineAttrObj5.put("value", commonServiceDTO.getChild_opv0Count());
		
		JSONObject vaccineAttrObj6 = new JSONObject();
		vaccineAttrObj6.put("dataElement", "eYJ3MgWzghH");//OPV 1 given (0-11m)
		vaccineAttrObj6.put("value", commonServiceDTO.getChild_opv1Count());
		
		JSONObject vaccineAttrObj7 = new JSONObject();
		vaccineAttrObj7.put("dataElement", "YkajaYobus9");//OPV 2 given (0-11m)
		vaccineAttrObj7.put("value", commonServiceDTO.getChild_opv2Count());
		
		JSONObject vaccineAttrObj8 = new JSONObject();
		vaccineAttrObj8.put("dataElement", "AFIo5tpZjyr");//OPV 3 given (0-11m)
		vaccineAttrObj8.put("value", commonServiceDTO.getChild_opv3Count());
		
		vaccineCountArray.put(vaccineAttrObj1);
        vaccineCountArray.put(vaccineAttrObj2);
		vaccineCountArray.put(vaccineAttrObj3);
		vaccineCountArray.put(vaccineAttrObj4);
		vaccineCountArray.put(vaccineAttrObj5);
		vaccineCountArray.put(vaccineAttrObj6);
		vaccineCountArray.put(vaccineAttrObj7);
		vaccineCountArray.put(vaccineAttrObj8);
		
		vaccineCountObj.put("dataSet", "pEaVGTvSsQn");
		vaccineCountObj.put("completeData", modifiedDate);
		vaccineCountObj.put("period", periodTime);
		vaccineCountObj.put("orgUnit", "SkiBAS3qNA6");
		vaccineCountObj.put("dataValues", vaccineCountArray);
		
		System.out.println(vaccineCountObj.toString());
		
		dhis2HttpUtils.post(url, "", vaccineCountObj.toString(), "mpower", "mPower4321");
		
		//////////////////////////////////////////////////////////////////////
		
		commonServiceDTOs.add(commonServiceDTO);
		return commonServiceDTOs;
		
	}
	
	
	public HttpResponse getVaccineCountForSendingToDHIS2(String type,String startMonth,String endMonth) throws JSONException{
		
		VaccineCountDTO commonServiceDTO = new VaccineCountDTO();
		
		Calendar c = Calendar.getInstance();   // this takes current date
	    c.set(Calendar.DAY_OF_MONTH,
                c.getActualMinimum(Calendar.DAY_OF_MONTH));
	     
	    String startMon= new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	    System.out.println("startMon: " + startMon);
	    c.set(Calendar.DAY_OF_MONTH,
                c.getActualMaximum(Calendar.DAY_OF_MONTH));
	    
	    String endMon= new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	    System.out.println("endMon: " + endMon); 
	    
	    System.out.println("startMonth: " + startMonth);
	    System.out.println("endMonth: " + endMonth);
	    
		//this.getVaccineCount(type, startMonth, endMonth, commonServiceDTO);
	    this.getVaccineCount(type, startMon, endMon, commonServiceDTO);
		
		JSONObject vaccineCountObj =	new JSONObject();
		
		JSONArray vaccineCountArray =	new JSONArray();
		
		Date date = new Date();
		String modifiedDate= new SimpleDateFormat("yyyy-MM-dd").format(date);
		
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH);
		String periodTime =  Integer.toString(year)+Integer.toString(month);
		
		JSONObject vaccineAttrObj1 = new JSONObject();
		vaccineAttrObj1.put("dataElement", "nbQTnNFs1I8");//Bcg given (0-11m)
		vaccineAttrObj1.put("value", commonServiceDTO.getChild_bcgCount());
		
		JSONObject vaccineAttrObj2 = new JSONObject();
		vaccineAttrObj2.put("dataElement", "Na5rrDNtwOW");//Penta 1given (0-11m)
		vaccineAttrObj2.put("value", commonServiceDTO.getChild_penta1Count());
		
		JSONObject vaccineAttrObj3 = new JSONObject();
		vaccineAttrObj3.put("dataElement", "zGQIRoCQIcK");//Penta 2 given (0-11m)
		vaccineAttrObj3.put("value", commonServiceDTO.getChild_penta2Count());
		
		JSONObject vaccineAttrObj4 = new JSONObject();
		vaccineAttrObj4.put("dataElement", "cOP5mAREs38");//Penta 3 given (0-11m)
		vaccineAttrObj4.put("value", commonServiceDTO.getChild_penta3Count());
		
		JSONObject vaccineAttrObj5 = new JSONObject();
		vaccineAttrObj5.put("dataElement", "TzbgFs3CSyp");//OPV 0 given (0-11m)
		vaccineAttrObj5.put("value", commonServiceDTO.getChild_opv0Count());
		
		JSONObject vaccineAttrObj6 = new JSONObject();
		vaccineAttrObj6.put("dataElement", "eYJ3MgWzghH");//OPV 1 given (0-11m)
		vaccineAttrObj6.put("value", commonServiceDTO.getChild_opv1Count());
		
		JSONObject vaccineAttrObj7 = new JSONObject();
		vaccineAttrObj7.put("dataElement", "YkajaYobus9");//OPV 2 given (0-11m)
		vaccineAttrObj7.put("value", commonServiceDTO.getChild_opv2Count());
		
		JSONObject vaccineAttrObj8 = new JSONObject();
		vaccineAttrObj8.put("dataElement", "AFIo5tpZjyr");//OPV 3 given (0-11m)
		vaccineAttrObj8.put("value", commonServiceDTO.getChild_opv3Count());
		
		vaccineCountArray.put(vaccineAttrObj1);
        vaccineCountArray.put(vaccineAttrObj2);
		vaccineCountArray.put(vaccineAttrObj3);
		vaccineCountArray.put(vaccineAttrObj4);
		vaccineCountArray.put(vaccineAttrObj5);
		vaccineCountArray.put(vaccineAttrObj6);
		vaccineCountArray.put(vaccineAttrObj7);
		vaccineCountArray.put(vaccineAttrObj8);
		
		vaccineCountObj.put("dataSet", "pEaVGTvSsQn");
		vaccineCountObj.put("completeData", modifiedDate);
		vaccineCountObj.put("period", periodTime);
		vaccineCountObj.put("orgUnit", "SkiBAS3qNA6");
		vaccineCountObj.put("dataValues", vaccineCountArray);
		
		System.out.println(vaccineCountObj.toString());
		
		HttpResponse result = dhis2HttpUtils.post(url, "", vaccineCountObj.toString(), "mpower", "mPower4321");
		
		return result;	
	}
	
	
	private CountServiceDTO getHouseholdCount(String provider,String startMonth,String endMonth,String startWeek,String endWeek,CountServiceDTO commonServiceDTO){
		commonServiceDTO.setHouseholdTotalCount(allHouseHolds.findAllHouseHolds().size()) ;
		commonServiceDTO.setHouseholdTodayCount(luceneHouseHoldService.getHouseholdCount("",""));
		commonServiceDTO.setHouseholdThisMonthCount(luceneHouseHoldService.getHouseholdCount(startMonth, endMonth));
		commonServiceDTO.setHouseholdThisWeekCount(luceneHouseHoldService.getHouseholdCount(startWeek, endWeek));
		return commonServiceDTO;
	}
	private CountServiceDTO getMembersCount(String provider,String startMonth,String endMonth,String startWeek,String endWeek,CountServiceDTO commonServiceDTO){
		commonServiceDTO.setPwTotalCount(allMembers.allOpenMembers().size());
		commonServiceDTO.setPwThisMonthCount(luceneMembersService.getMembersCount(startMonth, endMonth));
		commonServiceDTO.setPwThisWeekCount(luceneMembersService.getMembersCount(startMonth, endMonth));
		commonServiceDTO.setPwTodayCount(luceneMembersService.getMembersCount("", ""));
		return commonServiceDTO;
	}
	private VaccineCountDTO getVaccineCount(String type,String startMonth,String endMonth,VaccineCountDTO commonServiceDTO){
		commonServiceDTO.setWoman_measlesCount(luceneVaccineService.getVaccineCount("Woman_measles",startMonth, endMonth));
		commonServiceDTO.setWoman_TT1Count(luceneVaccineService.getVaccineCount("Woman_TT1",startMonth, endMonth));
		commonServiceDTO.setWoman_TT2Count(luceneVaccineService.getVaccineCount("Woman_TT2",startMonth, endMonth));
		commonServiceDTO.setWoman_TT3Count(luceneVaccineService.getVaccineCount("Woman_TT3",startMonth, endMonth));
		commonServiceDTO.setWoman_TT4Count(luceneVaccineService.getVaccineCount("Woman_TT4",startMonth, endMonth));
		commonServiceDTO.setWoman_TT5Count(luceneVaccineService.getVaccineCount("Woman_TT5",startMonth, endMonth));
		commonServiceDTO.setChild_bcgCount(luceneVaccineService.getVaccineCount("child_bcg",startMonth, endMonth));
		commonServiceDTO.setChild_ipvCount(luceneVaccineService.getVaccineCount("child_ipv",startMonth, endMonth));
		commonServiceDTO.setChild_measles1Count(luceneVaccineService.getVaccineCount("child_measles1",startMonth, endMonth));
		commonServiceDTO.setChild_measles2Count(luceneVaccineService.getVaccineCount("child_measles2",startMonth, endMonth));
		commonServiceDTO.setChild_opv0Count(luceneVaccineService.getVaccineCount("child_opv0",startMonth, endMonth));
		commonServiceDTO.setChild_opv1Count(luceneVaccineService.getVaccineCount("child_opv1",startMonth, endMonth));
		commonServiceDTO.setChild_opv2Count(luceneVaccineService.getVaccineCount("child_opv2",startMonth, endMonth));
		commonServiceDTO.setChild_opv3Count(luceneVaccineService.getVaccineCount("child_opv3",startMonth, endMonth));
		commonServiceDTO.setChild_pcv1Count(luceneVaccineService.getVaccineCount("child_pcv1",startMonth, endMonth));
		commonServiceDTO.setChild_pcv2Count(luceneVaccineService.getVaccineCount("child_pcv2",startMonth, endMonth));
		commonServiceDTO.setChild_pcv3Count(luceneVaccineService.getVaccineCount("child_pcv3",startMonth, endMonth));
		commonServiceDTO.setChild_penta1Count(luceneVaccineService.getVaccineCount("child_penta1",startMonth, endMonth));
		commonServiceDTO.setChild_penta2Count(luceneVaccineService.getVaccineCount("child_penta2",startMonth, endMonth));
		commonServiceDTO.setChild_penta3Count(luceneVaccineService.getVaccineCount("child_penta3",startMonth, endMonth));
		return commonServiceDTO;
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
			this.getHouseholdCountForHomePage(provider, startMonth, endMonth, startWeek, endWeek, commonServiceDTO);
			this.getElcoCountForHomePage(provider, startMonth, endMonth, startWeek, endWeek, commonServiceDTO);
			
					
		}else{
			
		}
		commonServiceDTOs.add(commonServiceDTO);
		return commonServiceDTOs;
		
	}
	
	public List<WeeklyDataCount> getHHCountInformationForLastFourMonthAsWeekWise(){
		ViewResult hhViewResult;
		Long startTime =DateUtil.getStartTimeStampOfAMonth(3);
		hhViewResult = allHouseHolds.HouseholdBetweenTwoDatesAsViewResult(startTime);
		List<WeeklyDataCount> DTOs= new ArrayList<WeeklyDataCount>();
		WeeklyDataCount newDTO = new WeeklyDataCount();
		newDTO.setCounts(this.convertViewResultToWeekWiseCount(hhViewResult));
		DTOs.add(newDTO);
		return DTOs;
	}
	
	public List<WeeklyDataCount> getHHCountInformationForChart(String provider, String district, String upazilla, String union){
		ViewResult hhViewResult;		
		String key = this.createRawStartKey(provider, district, upazilla, union);
		if(!provider.equalsIgnoreCase("")){
			hhViewResult = allHouseHolds.allHHsCreatedLastFourMonthsByProviderAndLocationViewResult(key,key.substring(0, key.length()-1) + ",{}]");
		}
		else{
			hhViewResult = allHouseHolds.allHHsCreatedLastFourMonthsByLocationViewResult(key,key.substring(0, key.length()-1) + ",{}]");
		}		
		List<WeeklyDataCount> DTOs= new ArrayList<WeeklyDataCount>();
		WeeklyDataCount newDTO = new WeeklyDataCount();
		newDTO.setCounts(this.convertViewResultToWeekWiseCount(hhViewResult));
		DTOs.add(newDTO);
		return DTOs;
	}
	
	public List<WeeklyDataCount> getElcoCountInformationForLastFourMonthAsWeekWise(){
		ViewResult elcoViewResult;	
		Long startTime =DateUtil.getStartTimeStampOfAMonth(3);		
		elcoViewResult = allMembers.elcoBetweenTwoDatesAsViewResult(startTime);		
		List<WeeklyDataCount> DTOs= new ArrayList<WeeklyDataCount>();
		WeeklyDataCount newDTO = new WeeklyDataCount();
		newDTO.setCounts(this.convertViewResultToWeekWiseCount(elcoViewResult));
		DTOs.add(newDTO);
		return DTOs;
	}
	
	public List<WeeklyDataCount> getElcoCountInformationForChart(String provider, String district, String upazilla, String union){
		ViewResult elcoViewResult;		
		String key = this.createRawStartKey(provider, district, upazilla, union);
		if(!provider.equalsIgnoreCase("")){
			elcoViewResult = allMembers.allElcosCreatedLastFourMonthsByProviderAndLocationViewResult(key,key.substring(0, key.length()-1) + ",{}]");
		}
		else{
			elcoViewResult = allMembers.allElcosCreatedLastFourMonthsByLocationViewResult(key,key.substring(0, key.length()-1) + ",{}]");
		}
		List<WeeklyDataCount> DTOs= new ArrayList<WeeklyDataCount>();
		WeeklyDataCount newDTO = new WeeklyDataCount();
		newDTO.setCounts(this.convertViewResultToWeekWiseCount(elcoViewResult));
		DTOs.add(newDTO);
		return DTOs;
	}
	
	
	
	
		
	private List<Integer> convertViewResultToWeekWiseCount( ViewResult vr){		
		List<Integer> seperateWeeklyCountDataForRegisterFromViewResult = new ArrayList<>();
		for (int index = 0; index < 23; index++) {
			seperateWeeklyCountDataForRegisterFromViewResult.add(index,0);
		}
		WeekBoundariesAndTimestamps boundaries = DateUtil.getWeekBoundariesForDashboard();
    	List<Long> startAndEndOfWeeksAsTimestamp = boundaries.weekBoundariesAsTimeStamp;     	
    	int todaysCount=0;
    	long todayTimeStamp = DateUtil.getTimestampToday() ; 
    	long oldTimeStamp=0;
    	int oldPosition=0;
    	int position = 0;
    	for (ViewResult.Row row : vr.getRows()) {
    		String stringValue = row.getValue(); 
    		long value = Long.parseLong(stringValue);			
			if(todayTimeStamp==value){
				todaysCount++;
			}			
    		try{ 
    			if(Long.parseLong(stringValue) == oldTimeStamp){
    				Integer existingCount = seperateWeeklyCountDataForRegisterFromViewResult.get(oldPosition);
    				seperateWeeklyCountDataForRegisterFromViewResult.set(oldPosition, existingCount+1);
    			}else{
	    			position = DateUtil.binarySearch(Long.parseLong(stringValue), startAndEndOfWeeksAsTimestamp);
	    			Integer existingCount = seperateWeeklyCountDataForRegisterFromViewResult.get(position);
	    			seperateWeeklyCountDataForRegisterFromViewResult.set(position, existingCount+1);
    			}    			
    			oldTimeStamp = Long.parseLong(stringValue);
    			oldPosition = position;
    			   			
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    	}    	
    	
    	for(int monthIndex = 3; monthIndex >= 0; monthIndex--){
			int month = DateUtil.getMontNumber(monthIndex);    		
    		if(monthIndex ==3 && month==1 ){
    			seperateWeeklyCountDataForRegisterFromViewResult.add(4, 0);    			
    		}else if(monthIndex ==2 && month==1){
    			seperateWeeklyCountDataForRegisterFromViewResult.add(9, 0);    			
    		}else if(monthIndex ==1 && month==1){
    			seperateWeeklyCountDataForRegisterFromViewResult.add(14, 0);    			
    		}else if(monthIndex ==0 && month==1){
    			seperateWeeklyCountDataForRegisterFromViewResult.add(19, 0);    			
    		}else{
    			
    		}
    	}
    	seperateWeeklyCountDataForRegisterFromViewResult.set(20, todaysCount);
		return seperateWeeklyCountDataForRegisterFromViewResult;
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
		
	private CountServiceDTO getHouseholdCountForHomePage(String provider,String startMonth,String endMonth,String startWeek,String endWeek,CountServiceDTO commonServiceDTO){
		Long startTime =DateUtil.getStartTimeStampOfAMonth(0);
		ViewResult vr = allHouseHolds.HouseholdBetweenTwoDatesAsViewResult(startTime);
		commonServiceDTO.setHouseholdTotalCount(allHouseHolds.totalHousehold()) ;
		List<Integer> countData = this.convertViewResultToWeekWiseCount(vr);		
		commonServiceDTO.setHouseholdTodayCount(countData.get(20));	
		
		commonServiceDTO.setHouseholdThisMonthCount(countData.get(19)+countData.get(18)+countData.get(17)+countData.get(16)+countData.get(15));
		commonServiceDTO.setHouseholdThisWeekCount(countData.get(15+DateUtil.getCurrentMonthCurrentweek()));
		return commonServiceDTO;
	}
	private CountServiceDTO getElcoCountForHomePage(String provider,String startMonth,String endMonth,String startWeek,String endWeek,CountServiceDTO commonServiceDTO){
		Long startTime =DateUtil.getStartTimeStampOfAMonth(0);
		ViewResult vr = allMembers.elcoBetweenTwoDatesAsViewResult(startTime);
		List<Integer> countData = this.convertViewResultToWeekWiseCount(vr);
		commonServiceDTO.setElcoTotalCount(allMembers.totalElco());				
		commonServiceDTO.setElcoThisMonthCount(countData.get(19)+countData.get(18)+countData.get(17)+countData.get(16)+countData.get(15));
		commonServiceDTO.setElcoThisWeekCount(countData.get(15+DateUtil.getCurrentMonthCurrentweek()));
		commonServiceDTO.setElcoTodayCount(countData.get(20));
		return commonServiceDTO;
	}
	

}
