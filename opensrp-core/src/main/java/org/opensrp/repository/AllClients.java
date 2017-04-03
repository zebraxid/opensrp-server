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
}
