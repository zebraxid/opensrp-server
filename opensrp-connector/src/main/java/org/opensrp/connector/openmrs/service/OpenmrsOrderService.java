package org.opensrp.connector.openmrs.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.common.util.HttpUtil;
import org.opensrp.domain.*;
import org.opensrp.repository.AllDrugs;
import org.opensrp.repository.AllDrugOrders;
import org.opensrp.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class OpenmrsOrderService extends OpenmrsService{
	private static final String ORDER_URL = "ws/rest/v1/order";
	private static final String ORDER_TYPE_URL = "ws/rest/v1/ordertype";
	private static final String USER_URL = "ws/rest/v1/user";
	private static final String DRUG_URL = "ws/rest/v1/drug";
	public static final String OPENMRS_UUID_IDENTIFIER_TYPE = "OPENMRS_UUID";
	
	private ClientService clientService;
	private AllDrugs allDrugs;
	
	public OpenmrsOrderService() {
	
	}

	public OpenmrsOrderService(String openmrsUrl, String user, String password) {
    	super(openmrsUrl, user, password);
	}
	
	public OpenmrsOrderService(ClientService clientService, AllDrugs allDrugs) {
		this.clientService = clientService;
		this.allDrugs = allDrugs;
	}
	public JSONObject getDrug(String uuid) throws JSONException
    {
    	return new JSONObject(HttpUtil.get(getURL()+"/"+DRUG_URL+"/"+uuid, "v=full", OPENMRS_USER, OPENMRS_PWD).body());
    }
	
	public JSONArray getAllDrugs() throws JSONException
    {
		JSONArray res = new JSONObject(HttpUtil.get(getURL()+"/"+DRUG_URL, "v=full", OPENMRS_USER, OPENMRS_PWD).body()).getJSONArray("results");
		return res;
    }
	
	public JSONObject getDrugOrder(String uuid) throws JSONException
	{
		JSONObject jsonObject=new JSONObject(HttpUtil.get(getURL()+"/"+ORDER_URL+"/"+uuid, "v=full", OPENMRS_USER, OPENMRS_PWD).body());
		return jsonObject;
	}
	
	public static Drug toDrug(JSONObject drug) throws JSONException
	{
		String drugUuid = drug.getString("uuid");
		String drugName = drug.getString("name");
		
		String conceptName = null;
		String conceptUuid = null;
		if(drug.has("concept")){
			JSONObject c = drug.getJSONObject("concept");
			conceptName = c.getString("display");
			conceptUuid = c.getString("uuid");
		}
		
		String dosageForm = null;
		String dosageFormUuid = null;
		if(drug.has("dosageForm")){
			JSONObject dc = drug.getJSONObject("dosageForm");
			dosageForm = dc.getString("display");
			dosageFormUuid = dc.getString("uuid");
		}
		
		String strength = drug.getString("doseStrength");
		String mini = drug.getString("minimumDailyDose");
		String max = drug.getString("maximumDailyDose");
		String description = drug.getString("description");
		String units = drug.getString("units");
		String combination = drug.getString("combination");

		String route = null;
		String routeUuid = null;
		if(drug.has("route")){
			JSONObject r = drug.getJSONObject("dosageForm");
			route = r.getString("display");
			routeUuid = r.getString("uuid");
		}
		
		Map<String, String> codes= new HashMap<>();
		codes.put("openmrs_uuid", drugUuid);
		codes.put("openmrs_concept_uuid", conceptUuid);
		codes.put("openmrs_route_uuid", routeUuid);
		codes.put("openmrs_dosage_form_uuid", dosageFormUuid);
		
		Drug d = new Drug(drugName, conceptName, codes, route, dosageForm, strength, units, max, mini, description, combination);
		if(drug.has("retired")){
			d.setVoided(drug.getBoolean("retired"));
		}

		if(drug.has("auditInfo")){
			JSONObject audit = drug.getJSONObject("auditInfo");
			d.setCreator(new User(audit.getJSONObject("creator").getString("uuid"), 
					audit.getJSONObject("creator").getString("display"), null, null));
			d.setDateCreated(DateTime.parse(audit.getString("dateCreated")));
			if(audit.has("dateChanged")){
				d.setEditor(new User(audit.getJSONObject("changedBy").getString("uuid"), 
					audit.getJSONObject("changedBy").getString("display"), null, null));
				d.setDateEdited(DateTime.parse(audit.getString("dateChanged")));
			}
		}
		return d;
	}
	
	public DrugOrder toDrugOrder(JSONObject drugOrder) throws JSONException
	{
		Map<String, String> codes = new HashMap<String, String>();
			
		codes.put("openmrs_uuid", drugOrder.getString("uuid"));
		
		String patientUuid = drugOrder.getJSONObject("patient").getString("uuid");
		List<Client> cl = clientService.findAllByIdentifier(patientUuid);
		String baseEntityId = cl.size()>0?cl.get(0).getBaseEntityId():null;
		if(baseEntityId == null){
			throw new IllegalAccessError("Patient was not found in opensrp while importing drug order "+drugOrder);
		}

		codes.put("openmrs_patient_uuid", patientUuid);
		
		String drugUuid = drugOrder.getJSONObject("drug").getString("uuid");
		List<Drug> drugL = allDrugs.findAllByCode(drugUuid);
		String drugId = drugL.size()>0?drugL.get(0).getId():null;
		if(drugId == null){
			throw new IllegalAccessError("Drug was not found in opensrp while importing drug order "+drugOrder);
		}
		codes.put("openmrs_drug_uuid", drugUuid);

		DateTime dateActivated = null;
		if(drugOrder.has("dateActivated")){
			dateActivated = DateTime.parse(drugOrder.getString("dateActivated"));
		}
		DateTime autoExpireDate = null;
		if(drugOrder.has("autoExpireDate")){
			autoExpireDate = DateTime.parse(drugOrder.getString("autoExpireDate"));
		}
		
		String frequency = null;
		if(drugOrder.has("frequency")){
			frequency = drugOrder.getJSONObject("frequency").getString("display");
		}
		
		String quantity = drugOrder.has("quantity")?drugOrder.getString("quantity"):null;
		String quantityUnits = null;
		if(drugOrder.has("quantityUnits")){
			quantityUnits = drugOrder.getJSONObject("quantityUnits").getString("display");
		}

		String orderNumber = drugOrder.getString("orderNumber");
		String action = drugOrder.getString("action");
		String urgency = drugOrder.getString("urgency");
		String instructions = drugOrder.getString("instructions");
		
		String drugName = drugOrder.getString("display");
		String dose = drugOrder.getString("dose");
		String discontinuedBy = drugOrder.has("discontinuedBy")?drugOrder.getString("discontinuedBy"):null;
		DateTime discontinuedDate = null;
		if(drugOrder.has("discontinuedDate")){
			discontinuedDate = DateTime.parse(drugOrder.getString("discontinuedDate"));
		}
		String discontinuedReason = drugOrder.has("discontinuedReason")?drugOrder.getString("discontinuedReason"):null;
		
		String orderReason = drugOrder.getString("orderReason");
		String orderer = drugOrder.getString("orderer");
		
		String route = null;
		if(drugOrder.has("route")){
			route = drugOrder.getJSONObject("route").getString("display");
		}
		
		DrugOrder drugOrderO = new DrugOrder(baseEntityId, drugOrder.getString("type"), drugId, codes, 
				dateActivated, autoExpireDate, frequency, quantity, quantityUnits, orderNumber, action, 
				urgency, instructions, dose, null, null, discontinuedBy , discontinuedDate, discontinuedReason, 
				orderReason, orderer, route, null);
		return drugOrderO;
	}
}
