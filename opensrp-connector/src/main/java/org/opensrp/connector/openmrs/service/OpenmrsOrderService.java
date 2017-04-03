package org.opensrp.connector.openmrs.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.opensrp.connector.JsonUtil.*;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.common.util.HttpUtil;
import org.opensrp.domain.Client;
import org.opensrp.domain.Drug;
import org.opensrp.domain.DrugOrder;
import org.opensrp.domain.User;
import org.opensrp.repository.AllDrugs;
import org.opensrp.service.ClientService;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.StringUtils;



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
		String drugName = getValue(drug, "name");
		
		String conceptName = null;
		String conceptUuid = null;
		if(drug.optJSONObject("concept") != null){
			JSONObject c = drug.getJSONObject("concept");
			conceptName = getValue(c, "display");
			conceptUuid = getValue(c, "uuid");
		}
		
		String dosageForm = null;
		String dosageFormUuid = null;
		if(drug.optJSONObject("dosageForm") != null){
			JSONObject dc = drug.getJSONObject("dosageForm");
			dosageForm = getValue(dc, "display");
			dosageFormUuid = getValue(dc, "uuid");
		}
		
		String strength = getValue(drug, "doseStrength");
		String mini = getValue(drug, "minimumDailyDose");
		String max = getValue(drug, "maximumDailyDose");
		String description = getValue(drug, "description");
		String units = getValue(drug, "units");
		String combination = getValue(drug, "combination");

		String route = null;
		String routeUuid = null;
		if(drug.optJSONObject("route") != null){
			JSONObject r = drug.getJSONObject("dosageForm");
			route = getValue(r, "display");
			routeUuid = getValue(r, "uuid");
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

		if(drug.optJSONObject("auditInfo") != null){
			JSONObject audit = drug.getJSONObject("auditInfo");
			
			if(audit.optJSONObject("creator") != null){
				d.setCreator(new User(getValue(audit.getJSONObject("creator"), "uuid"), 
						getValue(audit.getJSONObject("creator"), "display"), null, null));
			}
			d.setDateCreated(DateTime.parse(audit.getString("dateCreated")));
			
			if(getValue(audit, "dateChanged") != null){
				if(audit.optJSONObject("changedBy") != null){
					d.setEditor(new User(getValue(audit.getJSONObject("changedBy"), "uuid"), 
							getValue(audit.getJSONObject("changedBy"), "display"), null, null));					
				}
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
		codes.put("openmrs_patient_uuid", patientUuid);
		
		List<Client> cl = clientService.findAllByIdentifier(patientUuid);
		String baseEntityId = cl.size()>0?cl.get(0).getBaseEntityId():null;
		if(baseEntityId == null){
			throw new IllegalAccessError("Patient was not found in opensrp while importing drug order "+drugOrder);
		}
		
		String drugUuid = drugOrder.getJSONObject("drug").getString("uuid");
		codes.put("openmrs_drug_uuid", drugUuid);
		
		List<Drug> drugL = allDrugs.findAllByCode(drugUuid);
		String drugId = drugL.size()>0?drugL.get(0).getId():null;
		if(drugId == null){
			throw new IllegalAccessError("Drug was not found in opensrp while importing drug order "+drugOrder);
		}

		DateTime dateActivated = null;
		if(getValue(drugOrder, "dateActivated") != null){
			dateActivated = DateTime.parse(drugOrder.getString("dateActivated"));
		}
		DateTime autoExpireDate = null;
		if(getValue(drugOrder, "autoExpireDate") != null){
			autoExpireDate = DateTime.parse(drugOrder.getString("autoExpireDate"));
		}
		
		String frequency = null;
		if(drugOrder.optJSONObject("frequency") != null){
			frequency = getValue(drugOrder.getJSONObject("frequency"), "display");
		}
		
		String quantity = getValue(drugOrder, "quantity");
		String quantityUnits = null;
		if(drugOrder.optJSONObject("quantityUnits") != null){
			quantityUnits = getValue(drugOrder.getJSONObject("quantityUnits"), "display");
		}

		String orderNumber = getValue(drugOrder, "orderNumber");
		String action = getValue(drugOrder, "action");
		String urgency = getValue(drugOrder, "urgency");
		String instructions = getValue(drugOrder, "instructions");
		
		String dose = getValue(drugOrder, "dose");
		String discontinuedBy = getValue(drugOrder, "discontinuedBy");
		String discontinuedReason = getValue(drugOrder, "discontinuedReason");
		
		DateTime discontinuedDate = null;
		if(getValue(drugOrder, "discontinuedDate") != null){
			discontinuedDate = DateTime.parse(drugOrder.getString("discontinuedDate"));
		}
		
		String orderReason = getValue(drugOrder, "orderReason");
		String orderer = getValue(drugOrder, "orderer");
		
		String route = null;
		if(drugOrder.optJSONObject("route") != null){
			route = drugOrder.getJSONObject("route").getString("display");
		}
		
		DrugOrder drugOrderO = new DrugOrder(baseEntityId, getValue(drugOrder, "type"), drugId, codes, 
				dateActivated, autoExpireDate, frequency, quantity, quantityUnits, orderNumber, action, 
				urgency, instructions, dose, null, null, discontinuedBy , discontinuedDate, discontinuedReason, 
				orderReason, orderer, route, null);
		
		if(drugOrder.optJSONObject("auditInfo") != null){
			JSONObject audit = drugOrder.getJSONObject("auditInfo");
			
			if(audit.optJSONObject("creator") != null){
				drugOrderO.setCreator(new User(getValue(audit.getJSONObject("creator"), "uuid"), 
						getValue(audit.getJSONObject("creator"), "display"), null, null));
			}
			drugOrderO.setDateCreated(DateTime.parse(audit.getString("dateCreated")));
			
			if(getValue(audit, "dateChanged") != null){
				if(audit.optJSONObject("changedBy") != null){
					drugOrderO.setEditor(new User(getValue(audit.getJSONObject("changedBy"), "uuid"), 
							getValue(audit.getJSONObject("changedBy"), "display"), null, null));					
				}
				drugOrderO.setDateEdited(DateTime.parse(audit.getString("dateChanged")));
			}
		}
		
		return drugOrderO;
	}
}
