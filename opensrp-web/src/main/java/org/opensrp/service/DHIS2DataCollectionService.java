
package org.opensrp.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.common.util.HttpResponse;
import org.opensrp.connector.Dhis2HttpUtils;
import org.opensrp.dto.VaccineCountDTO;
import org.opensrp.rest.services.LuceneVaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DHIS2DataCollectionService extends DHIS2ConfigService {
	
	private Dhis2HttpUtils dhis2HttpUtils;	
	private LuceneVaccineService luceneVaccineService;
	
	@Autowired
	public DHIS2DataCollectionService( LuceneVaccineService luceneVaccineService,Dhis2HttpUtils dhis2HttpUtils){
		this.luceneVaccineService = luceneVaccineService;
		this.dhis2HttpUtils = dhis2HttpUtils;
	}
	
	public DHIS2DataCollectionService(String dhis2Url, String user,
			String password) {
		super(dhis2Url, user, password);
		
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
		
		@SuppressWarnings("static-access")
		HttpResponse result = dhis2HttpUtils.post(DHIS2_BASE_URL.replaceAll("\\s+","")+"dataValueSets", "", vaccineCountObj.toString(), DHIS2_USER.replaceAll("\\s+",""), DHIS2_PWD.replaceAll("\\s+",""));
		
		return result;	
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

}
