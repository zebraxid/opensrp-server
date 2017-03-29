package org.opensrp.repository;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;
import org.motechproject.dao.MotechBaseRepository;
import org.opensrp.common.AllConstants;
import org.opensrp.domain.Drug;
import org.opensrp.domain.DrugOrder;
import org.opensrp.util.DateTimeTypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Repository
public class AllDrugOrders extends MotechBaseRepository<DrugOrder> {
	@Autowired
	protected AllDrugOrders(@Qualifier(AllConstants.DRUG_ORDER_DATABASE_CONNECTOR) CouchDbConnector db) {
		super(DrugOrder.class, db);
	}

	@GenerateView
	public List<DrugOrder> findByBaseEntityId(String baseEntityId) {
		return queryView("by_baseEntityId", baseEntityId);
	}
	
	@GenerateView
	public List<DrugOrder> findByOrderNumber(String orderNumber) {
		return queryView("by_orderNumber", orderNumber);
	}

	@View(name = "all_drugorders_by_drug", map = "function(doc) {if (doc.type === 'DrugOrder') {emit(doc.drug);}}")
	public List<DrugOrder> findAllByDrug(String key) {
		return db.queryView(createQuery("all_drugorders_by_drug").key(key).includeDocs(true), DrugOrder.class);
	}
	 
	@View(name = "all_drugorders_by_code", map = "function(doc) {if (doc.type === 'DrugOrder') {for(var key in doc.codes) {emit(doc.codes[key]);}}}")
	public List<Drug> findAllByCode(String code) {
		return db.queryView(createQuery("all_drugorders_by_code").key(code).includeDocs(true), Drug.class);
	}
	
	public DrugOrder merge(String drugOrderId, DrugOrder updatedOrder) 
	{
		try{
			DrugOrder original = get(drugOrderId);
			if(original == null){
				throw new IllegalArgumentException("No drug order found with given list of identifiers. Consider adding new!");
			}
			
			Gson gs = new GsonBuilder().registerTypeAdapter(DateTime.class, new DateTimeTypeConverter()).create();
			JSONObject originalJo = new JSONObject(gs.toJson(original));
	
			JSONObject updatedJo = new JSONObject(gs.toJson(updatedOrder));
			List<Field> fn = Arrays.asList(DrugOrder.class.getDeclaredFields());
	
			JSONObject mergedJson = new JSONObject();
			if (originalJo.length() > 0) {
				mergedJson = new JSONObject(originalJo, JSONObject.getNames(originalJo));
			}
			if (updatedJo.length() > 0) {
				for (Field key : fn) {
					String jokey = key.getName();
					if(updatedJo.has(jokey)) mergedJson.put(jokey, updatedJo.get(jokey));
				}
			
				original = gs.fromJson(mergedJson.toString(), DrugOrder.class);
				
				original.getCodes().putAll(updatedOrder.getCodes());
			}
	
			original.setDateEdited(DateTime.now());
			update(original);
			return original;
		}
		catch(JSONException e){
			throw new RuntimeException(e);
		}
	}
}
