package org.opensrp.repository;

import static org.opensrp.common.AllConstants.Client.FIRST_NAME;
import static org.opensrp.common.AllConstants.Client.GENDER;
import static org.opensrp.common.AllConstants.Client.LAST_NAME;

import com.github.ldriscoll.ektorplucene.LuceneQuery;
import com.github.ldriscoll.ektorplucene.LuceneResult;
import com.mysql.jdbc.StringUtils;

import org.ektorp.ComplexKey;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.ektorp.util.Assert;
import org.ektorp.util.Documents;
import org.joda.time.DateTime;
import org.motechproject.dao.MotechBaseRepository;
import org.opensrp.common.AllConstants;
import org.opensrp.domain.Client;
import org.opensrp.repository.lucene.FilterType;
import org.opensrp.repository.lucene.LuceneClientRepository;
import org.opensrp.repository.lucene.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Repository
public class AllClients extends MotechBaseRepository<Client> {

	private LuceneClientRepository lcr;

	@Autowired
	protected AllClients(@Qualifier(AllConstants.OPENSRP_DATABASE_CONNECTOR) CouchDbConnector db,
	                     LuceneClientRepository lcr) {
		super(Client.class, db);
		this.lcr = lcr;
	}

	@GenerateView
	public Client findByBaseEntityId(String baseEntityId) {
		if (StringUtils.isEmptyOrWhitespaceOnly(baseEntityId))
			return null;
		List<Client> clients = queryView("by_baseEntityId", baseEntityId);
		if (clients == null || clients.isEmpty()) {
			return null;
		}
		return clients.get(0);
	}

	@GenerateView
	public Client findByBaseEntityId(CouchDbConnector targetDb, String baseEntityId) {
		if (StringUtils.isEmptyOrWhitespaceOnly(baseEntityId))
			return null;
		List<Client> clients = queryView(targetDb, "by_baseEntityId", baseEntityId);
		if (clients == null || clients.isEmpty()) {
			return null;
		}
		return clients.get(0);
	}

	@View(name = "all_clients", map = "function(doc) { if (doc.type === 'Client') { emit(doc.baseEntityId); } }")
	public List<Client> findAllClients() {
		return db.queryView(createQuery("all_clients").includeDocs(true), Client.class);
	}

	@View(name = "all_clients_by_identifier", map = "function(doc) {if (doc.type === 'Client') {for(var key in doc.identifiers) {emit(doc.identifiers[key]);}}}")
	public List<Client> findAllByIdentifier(String identifier) {
		return db.queryView(createQuery("all_clients_by_identifier").key(identifier).includeDocs(true), Client.class);
	}

	@View(name = "all_clients_by_attribute_and_timestamp", map = "function (doc) {  if(doc.type === 'Client'){   "
			+ " var modified = Date.parse(doc.dateCreated);   "
			+ " if(doc.dateEdited){ modified = Date.parse(doc.dateEdited); }   "
			+ " else if(doc.dateVoided && Date.parse(doc.dateVoided) > modified){ modified = Date.parse(doc.dateVoided); }   "
			+ " for(var att in doc.attributes){ var val = doc.attributes[att];     "
			+ " emit([att.toLowerCase(), val.toLowerCase(), modified]);   "
			+ " }  }}")
	public List<Client> findAllByAttribute(String attributeType, String attribute, DateTime from, DateTime to) {
		//couchdb does left to right match and also we want sort by timestamp
		ComplexKey skey = ComplexKey.of(attributeType.toLowerCase(), attribute.toLowerCase(), from.getMillis());
		ComplexKey ekey = ComplexKey.of(attributeType.toLowerCase(), attribute.toLowerCase(), to.getMillis());
		return db.queryView(createQuery("all_clients_by_attribute_and_timestamp").startKey(skey).endKey(ekey).includeDocs(true), Client.class);
	}
	
	
	/*@View(name = "all_clients_by_user_data", map = "function (doc) {  if(doc.type === 'Client'){"+
   "for(var att in doc.attributes){"+
    "var val = doc.attributes[att]; "+
    "if(att.toLowerCase().indexOf('primary contact') !== -1)"+
    "{ emit([doc.gender, val.toLowerCase()], doc);  }  }"+
    "for(var ide in doc.identifiers){"+
    " var val = doc.identifiers[ide]; "+
    "if(ide.toLowerCase().indexOf('tbreach id') !== -1){ emit([doc.gender, val.toLowerCase()], doc);  }}"+
	"emit([doc.gender, doc.firstName], doc); emit([doc.gender, doc.lastName], doc);"+
	"  var curdate = new Date(); "+
	" var dob = Date.parse(doc.birthdate);"+
	"var age=curdate-dob;"+
	"emit([doc.gender, age], doc);}}")
	public List<Client> findAllByUserData(String gender, String query1, String query2) {
		ComplexKey fkey = ComplexKey.of(gender.toLowerCase(), query1);
		ComplexKey lkey = ComplexKey.of(gender.toLowerCase(), query2);
		ViewQuery vq = createQuery("all_clients_by_user_data").startKey(fkey).endKey(lkey).includeDocs(true);
		List<Client> query = db.queryView(createQuery("all_clients_by_user_data").startKey(fkey).endKey(lkey).includeDocs(true), Client.class);
		return query;
	}*/
	
	/***
	 * 
	 * 		Modified findByUserData
	 * @param addressType
	 * @param addressField
	 * @param value
	 * @param from
	 * @param to
	 * @return
	 */
	@View(name = "all_clients_by_gender_ageGroup_firstName", map = "function (doc) {  if(doc.type === 'Client'){"+
			"var status = '';"+
			"if((Object.keys(doc.attributes).length > 0)){"+
			"if(!('dateRemoved' in doc.attributes)){"+
			"if (('baseline' in doc.attributes)) {"+
			"status = 'in-treatment';"+
			"} else if (('diagnosis_date' in doc.attributes)) {"+
			"status = 'positive';"+
			"} else if (('Primary Contact Number' in doc.attributes)) {"+
			"status = 'presumptive';"+
			"}"+
			"else status = 'non-presumtpive';"+
			"}"+
			"else status = 'patient_removed'"+
			"}"+
			"else status = 'undefined';"+
			"var curdate = new Date();"+
				"var dob = new Date(doc.birthdate);"+
				"var age = curdate.getFullYear() - dob.getFullYear();"+
				"if(age >=0 && age <= 15){"+
				"emit([doc.gender, status, '0to15', doc.firstName], doc);"+
				"}"+
				"else if(age >=16 && age <=30){"+
				"emit([doc.gender.toLowerCase(), status, '16to30', doc.firstName], doc);"+
				"}"+
				"else if(age >=31 && age <=45){"+
				"emit([doc.gender.toLowerCase(), status, '31to45', doc.firstName], doc);"+
				"}"+
				"else if(age >=46 && age <=60){"+
				"emit([doc.gender.toLowerCase(), status, '46to60', doc.firstName], doc);"+
				"}"+
				"else if(age >=61 && age <=75){"+
				"emit([doc.gender.toLowerCase(), status, '61to75', doc.firstName], doc);"+
				"}"+
				"else if(age >= 76){"+
				"emit([doc.gender.toLowerCase(), status, '76+', doc.firstName], doc);"+
				"}}}")
	public List<Client> findCLientsByGenderAgeGroupFirstName(String gender, String status, String ageGroup, String firstName) {
		ComplexKey fkey = ComplexKey.of(gender.toLowerCase(),status, ageGroup, firstName);
		ComplexKey lkey = ComplexKey.of(gender.toLowerCase(),status, ageGroup, firstName+"zzz");
		List<Client> query = db.queryView(createQuery("all_clients_by_gender_ageGroup_firstName").startKey(fkey).endKey(lkey).includeDocs(true), Client.class);
		return query;
	}
	
	@View(name = "baseEntityIds_by_status", map = "function (doc) {  if(doc.type === 'Client'){"+
			"if( (Object.keys(doc.attributes).length > 0) && ('baseline' in doc.attributes)){"+
			      "emit('in-treatment',doc);"+
			    "}"+
			    "else if((Object.keys(doc.attributes).length > 0) && ('diagnosis_date' in doc.attributes)){"+
			      "emit('positive',doc);"+
			    "}"+
			    "else if( (Object.keys(doc.attributes).length > 0) && ('Primary Contact Number' in doc.attributes)){"+
			      "emit('presumptive', doc);"+
			    "}"+
			"}}")
public List<Client> findBaseEntityIdsByStatus(String status) {
	List<Client> query = db.queryView(createQuery("baseEntityIds_by_status").key(status).includeDocs(true), Client.class);
	return query;
}
	   
	
	@View(name = "all_clients_by_address_and_timestamp", map = "function (doc) {  if(doc.type === 'Client'){   "
			+ " var keys = ['subTown','town','subDistrict','countyDistrict','cityVillage','stateProvince','country'];   "
			+ " var modified = Date.parse(doc.dateCreated);   if(doc.dateEdited){ modified = Date.parse(doc.dateEdited); } else if(doc.dateVoided && Date.parse(doc.dateVoided) > modified){ modified = Date.parse(doc.dateVoided); }   "
			+ " for(var i in doc.addresses){     var addr = doc.addresses[i];     "
			+ " for (var j in keys){      var fld = keys[j];      if(addr[fld]){       "
			+ " emit([addr.addressType.toLowerCase(), fld.toLowerCase(), addr[fld].toLowerCase(), modified]);      "
			+ " }     }   }  }}")	
	public List<Client> findByAddress(String addressType, String addressField, String value, DateTime from, DateTime to) {
		//couchdb does left to right match and also we want sort by timestamp
		ComplexKey skey = ComplexKey.of(addressType.toLowerCase(), addressField.toLowerCase(), value.toLowerCase(), from.getMillis());
		ComplexKey ekey = ComplexKey.of(addressType.toLowerCase(), addressField.toLowerCase(), value.toLowerCase(), to.getMillis());
		return db.queryView(createQuery("all_clients_by_address_and_timestamp").startKey(skey).endKey(ekey).includeDocs(true), Client.class);		
	}
	
	@View(name = "all_clients_by_timestamp", map = "function (doc) {  if(doc.type === 'Client'){   "
			+ " var modified = Date.parse(doc.dateCreated);   "
			+ " if(doc.dateEdited){ modified = Date.parse(doc.dateEdited); } "
			+ " else if(doc.dateVoided && Date.parse(doc.dateVoided) > modified){ modified = Date.parse(doc.dateVoided); }   "
			+ " emit(modified);"
			+ " }}")	
	public List<Client> findByTimestamp(DateTime from, DateTime to) {
		return db.queryView(createQuery("all_clients_by_timestamp").startKey(from.getMillis()).endKey(to.getMillis()).includeDocs(true), Client.class);		
	}
	
	public List<Client> findByDynamicQuery(String query, String sort, Integer limit, Integer skip) {
		return lcr.query(query, sort, limit, skip);//db.queryView(q.includeDocs(true), Client.class);
	}
	
	@View(name = "all_clients_by_identifier", map = "function(doc) {if (doc.type === 'Client') {for(var key in doc.identifiers) {emit(doc.identifiers[key]);}}}")
	public List<Client> findAllByIdentifier(CouchDbConnector targetDb, String identifier) {
		return targetDb.queryView(createQuery("all_clients_by_identifier").key(identifier).includeDocs(true), Client.class);
	}

	@View(name = "all_clients_by_identifier_of_type", map = "function(doc) {if (doc.type === 'Client') {for(var key in doc.identifiers) {emit([key, doc.identifiers[key]]);}}}")
	public List<Client> findAllByIdentifier(String identifierType, String identifier) {
		ComplexKey ckey = ComplexKey.of(identifierType, identifier);
		return db.queryView(createQuery("all_clients_by_identifier_of_type").key(ckey).includeDocs(true), Client.class);
	}

	@View(name = "all_clients_by_attribute_of_type", map = "function(doc) {if (doc.type === 'Client') {for(var key in doc.attributes) {emit([key, doc.attributes[key]]);}}}")
	public List<Client> findAllByAttribute(String attributeType, String attribute) {
		ComplexKey ckey = ComplexKey.of(attributeType, attribute);
		return db.queryView(createQuery("all_clients_by_attribute_of_type").key(ckey).includeDocs(true), Client.class);
	}
	
	@View(name = "all_clients_by_matching_name", map = "function(doc) {if(doc.type === 'Client'){emit(doc.firstName, doc);emit(doc.lastName, doc);}}")
	public List<Client> findAllByMatchingName(String nameMatches) {
		return db.queryView(createQuery("all_clients_by_matching_name").startKey(nameMatches).endKey(nameMatches + "z")
				.includeDocs(true), Client.class);
	}

	/**
	 * Find a client based on the relationship id and between a range of date created dates e.g given mother's id get children born at a given time
	 *
	 * @param relationalId
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	@View(name = "client_by_relationship_id_and_date_created", map = "function(doc) { if (doc.type === 'Client' && doc.relationships) {for (var key in doc.relationships) { var entityid=doc.relationships[key][0]; emit([entityid, doc.dateCreated.substring(0,10)], null); }} }")
	public List<Client> findByRelationshipIdAndDateCreated(String relationalId, String dateFrom, String dateTo) {
		ComplexKey startKey = ComplexKey.of(relationalId, dateFrom);
		ComplexKey endKey = ComplexKey.of(relationalId, dateTo);
		List<Client> clients = db.queryView(
				createQuery("client_by_relationship_id_and_date_created").startKey(startKey).endKey(endKey)
						.includeDocs(true), Client.class);
		return clients;
	}

	//	@View(name = "client_by_relationship", map = "function(doc) {if (doc.type === 'Client') {for(var key in doc.relationships) {emit([key, doc.relationships[key]]);}}}")
	//	@View(name = "client_by_relationship", map = "function(doc) { if(doc.type == 'Client' && doc.relationships.mother[0]) {emit(null, doc._id)} }")
	@View(name = "client_by_relationship", map = "function(doc) { if(doc.type === 'Client' && doc.relationships) { for (var key in doc.relationships) { var entityid = doc.relationships[key][0]; if (key === 'mother') {emit([key, entityid], doc);}}}}")

	public List<Client> findByRela3tionshipId(String relationshipType, String entityId) {
		return db.queryView(createQuery("client_by_relationship").startKey(entityId).endKey(entityId).includeDocs(true),
				Client.class);
	}
	

	//	@View(name = "clients_by_relationship", map = "function(doc) {if (doc.type === 'Client' && doc.relationships.mother) {for(var key in doc.relationships) {emit(doc.relationships.mother[key]);}}}")
	//	public List<Client> findByRelationshipId(String identifier) {
	//		return db.queryView(createQuery("clients_by_relationship").key(identifier).includeDocs(true), Client.class);
	//	}
	public List<Client> findByCriteria(String nameLike, String gender, DateTime birthdateFrom, DateTime birthdateTo,
	                                   DateTime deathdateFrom, DateTime deathdateTo, String attributeType,
	                                   String attributeValue, String addressType, String country, String stateProvince,
	                                   String cityVillage, String countyDistrict, String subDistrict, String town,
	                                   String subTown, DateTime lastEditFrom, DateTime lastEditTo) {
		return lcr.getByCriteria(nameLike, gender, birthdateFrom, birthdateTo, deathdateFrom, deathdateTo, attributeType,
				attributeValue, addressType, country, stateProvince, cityVillage, countyDistrict, subDistrict, town, subTown,
				lastEditFrom, lastEditTo, null);//db.queryView(q.includeDocs(true), Client.class);
	}

	public List<Client> findByDynamicQuery(String query) {
		return lcr.getByCriteria(query);//db.queryView(q.includeDocs(true), Client.class);
	}

	public List<Client> findByCriteria(String nameLike, String gender, DateTime birthdateFrom, DateTime birthdateTo,
	                                   DateTime deathdateFrom, DateTime deathdateTo, String attributeType,
	                                   String attributeValue, DateTime lastEditFrom, DateTime lastEditTo) {
		return lcr.getByCriteria(nameLike, gender, birthdateFrom, birthdateTo, deathdateFrom, deathdateTo, attributeType,
				attributeValue, null, null, null, null, null, null, null, null, lastEditFrom, lastEditTo, null);
	}

	public List<Client> findByCriteria(String addressType, String country, String stateProvince, String cityVillage,
	                                   String countyDistrict, String subDistrict, String town, String subTown,
	                                   DateTime lastEditFrom, DateTime lastEditTo) {
		return lcr.getByCriteria(null, null, null, null, null, null, null, null, addressType, country, stateProvince,
				cityVillage, countyDistrict, subDistrict, town, subTown, lastEditFrom, lastEditTo, null);
	}
	
	public List<Client> findByRelationShip(String motherIndentier) {
		return lcr.getByClientByMother("mother", motherIndentier);
	}

	/**
	 * Query view from the specified db
	 *
	 * @param targetDb
	 * @param viewName
	 * @param key
	 * @return
	 */
	public List<Client> queryView(CouchDbConnector targetDb, String viewName, String key) {
		return targetDb.queryView(createQuery(viewName).includeDocs(true).key(key), Client.class);
	}

	/**
	 * Save client to the specified db
	 *
	 * @param targetDb
	 * @param client
	 */
	public void add(CouchDbConnector targetDb, Client client) {
		Assert.isTrue(Documents.isNew(client), "entity must be new");
		targetDb.create(client);
	}

	/**
	 * Get all clients without a server version
	 *
	 * @return
	 */
	@View(name = "clients_by_empty_server_version", map = "function(doc) { if ( doc.type == 'Client' && !doc.serverVersion) { emit(doc._id, doc); } }")
	public List<Client> findByEmptyServerVersion() {
		return db.queryView(createQuery("clients_by_empty_server_version").limit(200).includeDocs(true), Client.class);
	}

	@View(name = "events_by_version", map = "function(doc) { if (doc.type === 'Client') { emit([doc.serverVersion], null); } }")
	public List<Client> findByServerVersion(long serverVersion) {
		ComplexKey startKey = ComplexKey.of(serverVersion + 1);
		ComplexKey endKey = ComplexKey.of(System.currentTimeMillis());
		return db.queryView(createQuery("events_by_version").startKey(startKey).endKey(endKey).limit(1000).includeDocs(true),
				Client.class);
	}

	public List<Client> findByFieldValue(String field, List<String> ids) {
		return lcr.getByFieldValue(field, ids);
	}

	@View(name = "clients_not_in_OpenMRS", map = "function(doc) { if (doc.type === 'Client' && doc.serverVersion) { var noId = true; for(var key in doc.identifiers) {if(key == 'OPENMRS_UUID') {noId = false;}}if(noId){emit([doc.serverVersion],  null); }} }")
	public List<Client> notInOpenMRSByServerVersion(long serverVersion, Calendar calendar) {
		long serverStartKey = serverVersion + 1;
		long serverEndKey = calendar.getTimeInMillis();
		if (serverStartKey < serverEndKey) {
			ComplexKey startKey = ComplexKey.of(serverStartKey);
			ComplexKey endKey = ComplexKey.of(serverEndKey);
			return db.queryView(createQuery("clients_not_in_OpenMRS").startKey(startKey).endKey(endKey).limit(1000).includeDocs(true),
					Client.class);
		}
		return new ArrayList<>();
	}

	@View(name = "client_by_last_ten_phone_digits", map = "function (doc) {if(doc.type === 'Client'){  var phone = doc.attributes['Primary Contact Number']; emit([phone.substring(phone.length - 10, phone.length)],doc);}}")
	public List<Client> findAllByLastTenPhoneDigits(String phone) {
		ComplexKey ckey = ComplexKey.of(phone);
		return db.queryView(createQuery("client_by_last_ten_phone_digits").key(ckey).includeDocs(true), Client.class);
	}
}
