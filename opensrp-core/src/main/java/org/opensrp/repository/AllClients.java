package org.opensrp.repository;

import java.util.List;

import org.ektorp.ComplexKey;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.joda.time.DateTime;
import org.motechproject.dao.MotechBaseRepository;
import org.opensrp.common.AllConstants;
import org.opensrp.domain.Client;
import org.opensrp.repository.lucene.LuceneClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.StringUtils;

@Repository
public class AllClients extends MotechBaseRepository<Client> {
	
	private LuceneClientRepository lcr;

	@Autowired
	protected AllClients(@Value("#{opensrp['couchdb.opensrp-db.revision-limit']}") int revisionLimit, 
    		@Qualifier(AllConstants.CLIENT_DATABASE_CONNECTOR) CouchDbConnector db, 
			LuceneClientRepository lcr) {
		super(Client.class, db);
		this.lcr = lcr;
		db.setRevisionLimit(revisionLimit);
	}

	@GenerateView
	public Client findByBaseEntityId(String baseEntityId) {
		if(StringUtils.isEmptyOrWhitespaceOnly(baseEntityId))
			return null;
		List<Client> clients = queryView("by_baseEntityId", baseEntityId);
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
	
	@View(name = "all_clients_by_attribute_of_type", map = "function(doc) {if (doc.type === 'Client') {for(var key in doc.attributes) {emit([key, doc.attributes[key]]);}}}")
	public List<Client> findAllByAttribute(String attributeType, String attribute) {
		ComplexKey ckey = ComplexKey.of(attributeType, attribute);
		return db.queryView(createQuery("all_clients_by_attribute_of_type").key(ckey).includeDocs(true), Client.class);
	}
	
	public List<Client> findByCriteria(String nameLike, String gender, DateTime birthdateFrom, DateTime birthdateTo, 
			DateTime deathdateFrom, DateTime deathdateTo, String attributeType, String attributeValue, 
			String addressType, String country, String stateProvince, String cityVillage, String countyDistrict, 
			String  subDistrict, String town, String subTown, DateTime lastEditFrom, DateTime lastEditTo) {
		return lcr.getByCriteria(nameLike, gender, birthdateFrom, birthdateTo, deathdateFrom, deathdateTo, attributeType, attributeValue, addressType, country, stateProvince, cityVillage, countyDistrict, subDistrict, town, subTown, lastEditFrom, lastEditTo);//db.queryView(q.includeDocs(true), Client.class);
	}
	
	public List<Client> findByDynamicQuery(String query, String sort, Integer limit, Integer skip) {
		return lcr.query(query, sort, limit, skip);//db.queryView(q.includeDocs(true), Client.class);
	}
	
	public List<Client> findByCriteria(String nameLike, String gender, DateTime birthdateFrom, DateTime birthdateTo, 
			DateTime deathdateFrom, DateTime deathdateTo, String attributeType, String attributeValue, DateTime lastEditFrom, DateTime lastEditTo){
		return lcr.getByCriteria(nameLike, gender, birthdateFrom, birthdateTo, deathdateFrom, deathdateTo, attributeType, attributeValue, null, null, null, null, null, null, null, null, lastEditFrom, lastEditTo);
	}
	
	public List<Client> findByCriteria(String addressType, String country, String stateProvince, String cityVillage, String countyDistrict, 
			String  subDistrict, String town, String subTown, DateTime lastEditFrom, DateTime lastEditTo) {
		return lcr.getByCriteria(null, null, null, null, null, null, null, null, addressType, country, stateProvince, cityVillage, countyDistrict, subDistrict, town, subTown, lastEditFrom, lastEditTo);
	}
}
