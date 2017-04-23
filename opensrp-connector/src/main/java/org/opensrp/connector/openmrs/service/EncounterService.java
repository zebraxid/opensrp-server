package org.opensrp.connector.openmrs.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.MultiValueMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.api.domain.Event;
import org.opensrp.api.domain.Obs;
import org.opensrp.common.util.HttpResponse;
import org.opensrp.connector.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.StringUtils;

@Service
public class EncounterService extends OpenmrsService{
	private static final String ENCOUNTER_URL = "ws/rest/v1/encounter";
	private static final String ENCOUNTER__TYPE_URL = "ws/rest/v1/encountertype";
	private PatientService patientService;
	private OpenmrsUserService userService;

	@Autowired
	public EncounterService(PatientService patientService, OpenmrsUserService userService) {
		this.patientService = patientService;
		this.userService = userService;
	}
	
	public EncounterService(String openmrsUrl, String user, String password) {
    	super(openmrsUrl, user, password);
	}
	
	public PatientService getPatientService() {
		return patientService;
	}

	public void setPatientService(PatientService patientService) {
		this.patientService = patientService;
	}

	public OpenmrsUserService getUserService() {
		return userService;
	}

	public void setUserService(OpenmrsUserService userService) {
		this.userService = userService;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JSONObject createEncounter(Event e,String idGen) throws JSONException{
		JSONObject pt = patientService.getPatientByIdentifier(idGen);
		JSONObject enc = new JSONObject();
		JSONObject pr = userService.getPersonByUser(e.getProviderId());
		
		enc.put("encounterDatetime", OPENMRS_DATE.format(e.getEventDate()));
		// patient must be existing in OpenMRS before it submits an encounter. if it doesnot it would throw NPE
		if (pr.getString("uuid").isEmpty() || pr.getString("uuid")==null)
			System.out.println("Person or Patient does not exist or empty inside openmrs with identifier: " + pr.getString("uuid"));
		else 
			enc.put("patient", pt.getString("uuid"));
		enc.put("encounterType", e.getEventType());
		enc.put("location", e.getLocationId());
		if (pr.getString("uuid").isEmpty() || pr.getString("uuid")==null)
			System.out.println("Person or Patient does not exist or empty inside openmrs with identifier: " + pr.getString("uuid"));
		else 
			enc.put("provider", pr.getString("uuid"));

		try{
			List<Obs> ol = e.getObs();	
			System.err.println("ol:"+ol.toString());
			Map<String, List<JSONObject>> pc = new HashMap<>();
			MultiValueMap   obsMap = new MultiValueMap();
			String parentCode="";		
			for (Obs obs : ol) {	
				//if no parent simply make it root obs
				if(StringUtils.isEmptyOrWhitespaceOnly(obs.getParentCode())){
					obsMap.put(obs.getFieldCode(), convertObsToJson(obs));
				}
				else {	
					obsMap.put(obs.getParentCode(), convertObsToJson(getOrCreateParent(ol, obs)));
					// find if any other exists with same parent if so add to the list otherwise create new list
					List<JSONObject> obl = pc.get(obs.getParentCode());
					parentCode = obs.getParentCode();
					if(obl == null){
						obl = new ArrayList<>();
					}
					obl.add(convertObsToJson(obs));
					pc.put(obs.getParentCode(), obl);
				}
			}
			
			
			JSONArray obar = new JSONArray();
			List<JSONObject> list;	       
			Set <String> entrySet = obsMap.entrySet();	
			System.out.println("obsMap:"+obsMap.toString());
	        @SuppressWarnings("rawtypes")
			Iterator it = entrySet.iterator();
	        System.err.println("entrySet:"+entrySet.toString());
	        int i=0;
	        while (it.hasNext()) {
	        	
	            Map.Entry mapEntry = (Map.Entry) it.next();
	            System.out.println("mapEntry.getKey():"+mapEntry.getKey());
	            list = (List) obsMap.get(mapEntry.getKey());
	           // for (int j = 0; j < list.size(); j++) {  
	            	JSONObject obo = list.get(i); 	            	
	                List<JSONObject> cob = pc.get(mapEntry.getKey());	               
	    			if(cob != null && cob.size() > 0) {	    				
	    				obo.put("groupMembers", new JSONArray(cob));
	    			}
	    			obar.put(obo);
	            //}
	    			i++;
	    			 System.err.println("obar:"+obar);	
	        }
	       
	       System.out.println("parentCode:"+parentCode);
	      return  this.createVaccineEncounter(obar, enc,parentCode);
	       
		}catch(Exception ee){
			ee.printStackTrace();
		}
		System.out.println("Going to create Encounter: " + enc.toString());
		HttpResponse op = HttpUtil.post(HttpUtil.removeEndingSlash(OPENMRS_BASE_URL)+"/"+ENCOUNTER_URL, "", enc.toString(), OPENMRS_USER, OPENMRS_PWD);
		return new JSONObject(op.body());
		
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject createVaccineEncounter(JSONArray obar,JSONObject enc,String parentCode){
		try{
			System.out.println("obar:"+obar);
			String getVaccinesAsString =  obar.get(0).toString();
	        JSONObject convertVaccinesJsonObjectFromString = new JSONObject(getVaccinesAsString);
	        String convertVaccinesJsonObjectToRawString = convertVaccinesJsonObjectFromString.get("value").toString();
	        String groupMembers =  obar.get(1).toString();
	        System.out.println("groupMembers:"+groupMembers.toString());
	        JSONObject groupMembersObject = new JSONObject(groupMembers);
	        JSONArray groupMembersList =  (JSONArray) groupMembersObject.get("groupMembers");       
	        String[] vaccineList = convertVaccinesJsonObjectToRawString.split(" ");
	        System.out.println("vaccineList:"+vaccineList);
	        @SuppressWarnings("rawtypes")
			Map map=new HashMap();  
			map.put("TT1","c8e8ed67-d125-4c77-a8c7-2c9a5cf2c46b");  
		    map.put("TT2","14f59334-bde6-4512-bfae-830d5bd7da87");
		    map.put("TT3","8197f5b8-328c-402d-8128-fd04d9de8a94"); 
		    map.put("TT4","4914c323-d32d-4412-905f-c8c90f92136f"); 
		    map.put("TT5","4dd71b8e-8109-4eb3-b919-186f8236edb1");
		    
		    map.put("bcg","1cf49965-5130-4648-8473-37238d24b826");
		    map.put("opv0","548a2d2d-4803-4d9a-8d78-8a31741baccc");
		    map.put("pcv1","e3340e49-df17-4b86-841e-6a207db40e58");
		    map.put("opv1","548a2d2d-4803-4d9a-8d78-8a31741baccc");
		    map.put("penta1","e601701f-a9c5-4cea-a06e-4e5abacaf6e4");
		    map.put("pcv2","27c360d3-ce24-492d-a004-1d551dfd0933");
		    map.put("opv2","548a2d2d-4803-4d9a-8d78-8a31741baccc");
		    map.put("penta2","9fbd9d94-0634-4880-954e-502c8be1c2d4");
		    map.put("pcv3","b0735577-6747-4c3e-bb52-b2d9b0d2419a");
		    map.put("opv3","548a2d2d-4803-4d9a-8d78-8a31741baccc");
		    map.put("penta3","8c0268d5-befe-47e3-a98d-d7d13f214f5b");
		    map.put("ipv","caffb48d-4155-47a5-a81a-ef669f0585d0");
		    map.put("measles1","9c23d2fe-e2db-48ea-98ac-cfb832f7c0b6");
		    map.put("measles1","d2c8d3fa-495a-4e5f-8e8b-3c598725c0c3");

		    
		    int indicator=0;	        
		    for (int j = 0; j < vaccineList.length; j++) {				
	        	JSONArray gruopMember = new JSONArray();
	 	        JSONArray observation = new JSONArray();	 	       
	 	        JSONObject groupObservation = new JSONObject();	
	 	        JSONObject observationConcept = new JSONObject(); 
	 	        System.out.println("vaccineList[j]:"+vaccineList[j]);
	 	        observationConcept.put("concept", convertVaccinesJsonObjectFromString.get("concept"));
	 	        observationConcept.put("value", map.get(vaccineList[j]));
	        	groupMembersList.get(indicator);
	        	groupMembersList.get(indicator+1);
	        	gruopMember.put(groupMembersList.get(indicator));
	        	gruopMember.put(groupMembersList.get(indicator+1));
	        	groupObservation.put("groupMembers", gruopMember);
	        	groupObservation.put("concept", parentCode);
	        	indicator+=2;	        	
	        	observation.put(observationConcept);
	        	observation.put(groupObservation);	 	        
	 	        enc.put("obs", observation);
	 	        System.out.println("Going to create Encounter: " + enc.toString());
	 	        HttpResponse op = HttpUtil.post(HttpUtil.removeEndingSlash(OPENMRS_BASE_URL)+"/"+ENCOUNTER_URL, "", enc.toString(), OPENMRS_USER, OPENMRS_PWD);
	 	        System.out.println(new JSONObject(op.body()));
		    }
       
			}catch(Exception ee){
				ee.printStackTrace();
			}
		return null;
		
	}
	private JSONObject convertObsToJson(Obs o) throws JSONException{
		JSONObject obo = new JSONObject();
		obo.put("concept", o.getFieldCode());
		if(o.getValue() != null && !StringUtils.isEmptyOrWhitespaceOnly(o.getValue().toString())) {
			/*if(o.getFieldCode().toString().equalsIgnoreCase("163137AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA") || o.getFieldCode().toString().equalsIgnoreCase("163138AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA") || o.getFieldCode().toString().equalsIgnoreCase("5599AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA") || o.getFieldCode().toString().equalsIgnoreCase("5596AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"))
			{*/	
				//obo.put("value", OPENMRS_DATETime.format(o.getValue()));
				if (o.getValue().toString().length() >= 19)
					obo.put("value", (o.getValue().toString().substring(0, 19)).replace("T", " "));
				else 
					obo.put("value", o.getValue());
			/*}			
			else 
				obo.put("value", o.getValue());*/
		}
		
		return obo;
	}
	
	private Obs getOrCreateParent(List<Obs> obl, Obs o){
		for (Obs obs : obl) {
			if(o.getParentCode().equalsIgnoreCase(obs.getFieldCode())){
				return obs;
			}
		}
		return new Obs("concept", o.getParentCode(), null, null, null, null);
	}
	
	
	
    public JSONObject convertEncounterToOpenmrsJson(String name, String description) throws JSONException {
		JSONObject a = new JSONObject();
		a.put("name", name);
		a.put("description", description);
		return a;
	}
	

    public JSONObject getEncounterType(String encounterType) throws JSONException
    {
    	// we have to use this ugly approach because identifier not found throws exception and 
    	// its hard to find whether it was network error or object not found or server error
    	JSONArray res = new JSONObject(HttpUtil.get(getURL()+"/"+ENCOUNTER__TYPE_URL, "v=full", 
    			OPENMRS_USER, OPENMRS_PWD).body()).getJSONArray("results");
    	for (int i = 0; i < res.length(); i++) {
			if(res.getJSONObject(i).getString("display").equalsIgnoreCase(encounterType)){
				return res.getJSONObject(i);
			}
		}
    	return null;
    }
    public JSONObject createEncounterType(String name, String description) throws JSONException{
		JSONObject o = convertEncounterToOpenmrsJson(name, description);
		return new JSONObject(HttpUtil.post(getURL()+"/"+ENCOUNTER__TYPE_URL, "", o.toString(), OPENMRS_USER, OPENMRS_PWD).body());
	}
}
