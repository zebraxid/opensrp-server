package org.opensrp.connector.DHIS2;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.repository.AllEvents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.opensrp.connector.DHIS2.DHIS2Constants.*;
@Service
public class DHIS2AggregateConnector extends DHIS2Service {
	
	@Autowired
	private AllEvents allEvents;
	public DHIS2AggregateConnector(){
		
	}
	
	public JSONObject getAggregateDataCount() throws JSONException{
		JSONObject vaccineCountObj =	new JSONObject();
		JSONArray vaccineCountArray =	new JSONArray();		
		
		Date date = new Date();
		String modifiedDate= new SimpleDateFormat("yyyy-MM-dd").format(date);
		
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH);
		String periodTime =  Integer.toString(year)+Integer.toString(month);
		
		JSONObject vaccineAttrObj1 = new JSONObject();
		vaccineAttrObj1.put("dataElement", "bDl4fsu1QIj");//Bcg given (0-11m)
		//vaccineAttrObj1.put("period", "201701");
		//vaccineAttrObj1.put("orgUnit", "IDc0HEyjhvL");
		vaccineAttrObj1.put("value", 53);
		
		JSONObject vaccineAttrObj2 = new JSONObject();
		vaccineAttrObj2.put("dataElement", "Ar5v2MYP3EU");//Penta 1given (0-11m)
		//vaccineAttrObj2.put("period", "201701");
		//vaccineAttrObj2.put("orgUnit", "IDc0HEyjhvL");
		vaccineAttrObj2.put("value", 45);
		
		vaccineCountArray.put(vaccineAttrObj1);
	    vaccineCountArray.put(vaccineAttrObj2);
	    
	    vaccineCountObj.put("dataSet", "wn53Io9MM6B");
		vaccineCountObj.put("completeData", modifiedDate);
		vaccineCountObj.put("period", 201610);
		vaccineCountObj.put("orgUnit", "IDc0HEyjhvL");
		vaccineCountObj.put("dataValues", vaccineCountArray);
	 return vaccineCountObj;
	
	}
	
	
	public void getAggregatedDataCount() throws JSONException{
			
		
		Date date = new Date();
		String modifiedDate= new SimpleDateFormat("yyyy-MM-dd").format(date);
		
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MONTH, -1);
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH)+1;
		
		int length = (int)(Math.log10(month)+1);
		String formatted ;
		System.out.println(length);
		if(length<2){			
		 formatted = String.format("%02d", month);
		}else{
			formatted =Integer.toString(month);
		}
		
		String periodTime =  Integer.toString(year)+formatted;
		
	   	
	   	System.out.println();
   	 	JSONArray eventDataValues =	new JSONArray();		
		
		
		/**
		 * Count for Birth place of Health_Facility of current month
		 * */
		JSONObject birhtPlaceInHealthFacility = new JSONObject();
		birhtPlaceInHealthFacility.put("dataElement", "Kt4PihqO6KL");
		birhtPlaceInHealthFacility.put("categoryOptionCombo", "DHJ5tZVSSsl");
		birhtPlaceInHealthFacility.put("value", "6");
		eventDataValues.put(birhtPlaceInHealthFacility);
		
		
	    JSONObject eventDataSet =	new JSONObject();
	    eventDataSet.put("dataSet", "Z5WPr2zconV");
	    eventDataSet.put("completeData", "2017-03-09");
	    eventDataSet.put("period", "201602");
	    eventDataSet.put("orgUnit", "PKTk8zxbl0J");
	    eventDataSet.put("dataValues", eventDataValues);
	    System.out.println(this.aggredateDataSendToDHIS2(eventDataSet));
	    
	 
	
	}
	public JSONObject aggredateDataSendToDHIS2(JSONObject aggregateData) throws JSONException{		
		return new JSONObject(Dhis2HttpUtils.post("http://dhis2.mpower-social.com:8080/api/".replaceAll("\\s+","")+"dataValueSets", "", aggregateData.toString(),"dgfp".replaceAll("\\s+",""), "Dgfp@123".replaceAll("\\s+","")).body());
	}
}
