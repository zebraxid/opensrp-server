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
import org.opensrp.domain.Event;
import org.opensrp.repository.lucene.LuceneEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class AllEvents extends MotechBaseRepository<Event>{
	private LuceneEventRepository ler;

	@Autowired
	protected AllEvents(@Value("#{opensrp['couchdb.opensrp-db.revision-limit']}") int revisionLimit, 
    		@Qualifier(AllConstants.EVENT_DATABASE_CONNECTOR) CouchDbConnector db,
			LuceneEventRepository ler) {
		super(Event.class, db);
		this.ler = ler;
		db.setRevisionLimit(revisionLimit);
	}
	
	@View(name = "all_events_by_identifier", map = "function(doc) {if (doc.type === 'Event') {for(var key in doc.identifiers) {emit(doc.identifiers[key]);}}}")
	public List<Event> findAllByIdentifier(String identifier) {
		return db.queryView(createQuery("all_events_by_identifier").key(identifier).includeDocs(true), Event.class);
	}
	
	@GenerateView
	public List<Event> findByFormSubmissionId(String formSubmissionId) {
		List<Event> events = queryView("by_formSubmissionId", formSubmissionId);
		return events;
	}
	
	@GenerateView
	public List<Event> findByBaseEntityId(String baseEntityId) {
		return queryView("by_baseEntityId", baseEntityId);
	}
	
	@View(name = "all_events_by_location_or_provider_and_timestamp", map = "function (doc) {  if(doc.type === 'Event'){   "
			+ " var modified = Date.parse(doc.dateCreated);   "
			+ " if(doc.dateEdited){ modified = Date.parse(doc.dateEdited); }   "
			+ " else if(doc.dateVoided && Date.parse(doc.dateVoided) > modified){ modified = Date.parse(doc.dateVoided); }   "
			+ " emit([doc.locationId.toLowerCase(), modified]);   "
			+ " emit([doc.providerId.toLowerCase(), modified]);  "
			+ " }}")
	public List<Event> findAllByLocationOrProvider(String locationOrProvider, DateTime from, DateTime to) {
		//couchdb does left to right match and also we want sort by timestamp
		ComplexKey skey = ComplexKey.of(locationOrProvider.toLowerCase(), from.getMillis());
		ComplexKey ekey = ComplexKey.of(locationOrProvider.toLowerCase(), to.getMillis());

		return db.queryView(createQuery("all_events_by_location_or_provider_and_timestamp").startKey(skey).endKey(ekey).includeDocs(true), Event.class);
	}
	
	@View(name = "all_events_by_entity_or_event_type_and_timestamp", map = "function (doc) {  if(doc.type === 'Event'){   "
			+ " var modified = Date.parse(doc.dateCreated);   "
			+ " if(doc.dateEdited){ modified = Date.parse(doc.dateEdited); }   "
			+ " else if(doc.dateVoided && Date.parse(doc.dateVoided) > modified){ modified = Date.parse(doc.dateVoided); }   "
			+ " emit([doc.eventType.toLowerCase(), modified]);   "
			+ " emit([doc.entityType.toLowerCase(), modified]);  "
			+ " }}")
	public List<Event> findAllByEntityOrEventType(String entityOrEventType, DateTime from, DateTime to) {
		//couchdb does left to right match and also we want sort by timestamp
		ComplexKey skey = ComplexKey.of(entityOrEventType.toLowerCase(), from.getMillis());
		ComplexKey ekey = ComplexKey.of(entityOrEventType.toLowerCase(), to.getMillis());

		return db.queryView(createQuery("all_events_by_entity_or_event_type_and_timestamp").startKey(skey).endKey(ekey).includeDocs(true), Event.class);
	}
	
	@View(name = "all_events_by_timestamp", map = "function (doc) {  if(doc.type === 'Event'){   "
			+ " var modified = Date.parse(doc.dateCreated);   "
			+ " if(doc.dateEdited){ modified = Date.parse(doc.dateEdited); } "
			+ " else if(doc.dateVoided && Date.parse(doc.dateVoided) > modified){ modified = Date.parse(doc.dateVoided); }   "
			+ " emit(modified);"
			+ " }}")	
	public List<Event> findByTimestamp(DateTime from, DateTime to) {
		return db.queryView(createQuery("all_events_by_timestamp").startKey(from.getMillis()).endKey(to.getMillis()).includeDocs(true), Event.class);		
	}
	
	public List<Event> findEventsByDynamicQuery(String query, String sort, Integer limit, Integer skip){
		return ler.getByCriteria(query, sort, limit, skip);
	}
	
	
}
