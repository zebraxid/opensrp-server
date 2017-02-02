package org.opensrp.camp.repository;

import java.util.List;

import org.ektorp.ComplexKey;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.ViewResult;
import org.ektorp.ViewResult.Row;
import org.ektorp.support.View;
import org.motechproject.dao.MotechBaseRepository;
import org.opensrp.camp.dao.Camp;
import org.opensrp.camp.dao.CampDate;
import org.opensrp.common.AllConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class CampDateRepository extends MotechBaseRepository<CampDate> {
	
	private static Logger logger = LoggerFactory.getLogger(CampDateRepository.class);
	
	@Autowired
	public CampDateRepository(@Value("#{opensrp['couchdb.atomfeed-db.revision-limit']}") int revisionLimit,
	    @Qualifier(AllConstants.OPENSRP_DATABASE_CONNECTOR) CouchDbConnector db) {
		super(CampDate.class, db);
		this.db.setRevisionLimit(revisionLimit);
	}
	
	/**
	 * @param session_id is the ID of a camp not camp date
	 * @return all camp dates with relates to the specified @param session_id
	 * */
	@View(name = "by_session_id", map = "function(doc) { if (doc.type === 'CampDate' && doc._id) { emit(doc.session_id, null); } }")
	public List<CampDate> findBySessionId(String session_id) {
		List<CampDate> campDates = db.queryView(createQuery("by_session_id").key(session_id).includeDocs(true), CampDate.class);
		return campDates;
	}
	
	/**
	 * @param session_name is the name of a camp.
	 * @param health_assistant is a user both of openSRP and openMRS user.
	 * @return all camp dates with relates to the specified @param session_name and @param health_assistant.
	 * */
	@View(name = "by_session_name_and_health_assistant", map = "function(doc) { if (doc.type === 'CampDate' && doc.health_assistant) { emit([doc.session_name,doc.health_assistant], null); } }")
	public CampDate findBySessionName(String session_name,String health_assistant) {	
		ComplexKey ckey = ComplexKey.of(session_name, health_assistant);
		List<CampDate> camps = db.queryView(createQuery("by_session_name_and_health_assistant").key(ckey).includeDocs(true), CampDate.class);
		if (camps == null || camps.isEmpty()) {			
			return null;
		}
		return camps.get(0);
	}
	
	/**
	 * @param id is the system generated _id  of a camp date
	 * @return a camp date with relates to the specified @param id
	 * */
	@View(name = "by_id", map = "function(doc) { if (doc.type === 'CampDate' && doc._id) { emit(doc._id, null); } }")
	public CampDate findById(String id) {		
		List<CampDate> camp = db.queryView(createQuery("by_id").key(id).includeDocs(true), CampDate.class);
		if (camp == null || camp.isEmpty()) {
			return null;
		}
		return camp.get(0);
	}
	
	/**
	 * @param timeStamp today's timeStamp.
	 * @return all camp dates in today's.
	 * */
	@View(name = "by_TimeStamp", map = "function(doc) { if (doc.type === 'CampDate' && doc._id && doc.status=='Active') { emit(doc.timestamp, null); } }")
	public List<CampDate> findByTimeStamp(long timeStamp) {		
		List<CampDate> camp = db.queryView(createQuery("by_TimeStamp").key(timeStamp).includeDocs(true), CampDate.class);
		if (camp == null || camp.isEmpty()) {
			return null;
		}
		return camp;
	}
	
	/**
	 * @param timeStamp today's timeStamp.
	 * @param HealthAssistant is a user both of openSRP and openMRS user.
	 * @return all camp dates in today's with specified user.
	 * */
	@View(name = "by_TimeStamp_Health_Assistant", map = "function(doc) { if (doc.type === 'CampDate' && doc._id && doc.status=='Active') { emit([doc.timestamp,doc.health_assistant], null); } }")
	public List<CampDate> findByTimeStampByHealthAssistant(long timeStamp,String HealthAssistant) {
		ComplexKey ckey = ComplexKey.of(timeStamp, HealthAssistant);
		List<CampDate> camp = db.queryView(createQuery("by_TimeStamp_Health_Assistant").key(ckey).includeDocs(true), CampDate.class);
		if (camp == null || camp.isEmpty()) {
			return null;
		}
		return camp;
	}
	
	/**	
	 * @param status is a camp date status.
	 * @param user is a user both of openSRP and openMRS user.
	 * @return count of camp dates with specified user and status active.
	 * */
	@View(name = "count_camp_with_username", map = "function(doc) { if (doc.type === 'CampDate' && doc.health_assistant) { emit([doc.health_assistant,doc.status],null); } }",reduce="_count")
	public int findCountCampByUserNameAndStatus(String user,String status) {
		ComplexKey ckey = ComplexKey.of(user,status);
		ViewResult result = db.queryView(createQuery("count_camp_with_username")
				.key(ckey)
				.includeDocs(false));		
		int  count = 0;
		if(!result.getRows().isEmpty()){
			count = result.getRows().get(0).getValueAsInt();
		}
		return count;
			
	}
	
	/**
	 * @param thana is a dashboard location id of a thana . 
	 * @param union is a dashboard location id of a union .
	 * @param ward is a dashboard location id of a ward .
	 * @param unit is a dashboard location id of a unit .
	 * @param healthAssistant is a user id which is created from dashboard and also needs to 
	 * exists in openMRS .
	 * 
	 * @return total count of camp dates
	 * */
	@View(name = "search", map = "function(doc) { if (doc.type === 'CampDate') { emit([doc.thana,doc.union,doc.ward,doc.unit,doc.health_assistant], null); } }",reduce="_count")
	public int findCountCampDateForSearch(String thana,String union,String ward,String unit,String healthAssistant) {
		Object thanaObj;
		Object unionObj;
		Object wardObj;
		Object unitObj;
		Object healthAssistantObj;		
		if(thana==""){
			 thanaObj = ComplexKey.emptyObject();
		}else{
			thanaObj = thana;
		}		
		if(union == ""){
			unionObj = ComplexKey.emptyObject();
		}else{
			unionObj = union;
		}
		
		if(ward ==""){
			wardObj = ComplexKey.emptyObject();
		}else{
			wardObj = ward;
		}
		
		if(unit ==""){
			unitObj = ComplexKey.emptyObject();
		}else{
			unitObj = unit;
		}
		
		if(healthAssistant ==""){			
			healthAssistantObj = ComplexKey.emptyObject();
		}else{
			healthAssistantObj = healthAssistant;			
		}		
		ComplexKey startkey = ComplexKey.of(thana, union,ward,unit,healthAssistant);
		ComplexKey endkey = ComplexKey.of(thanaObj, unionObj,wardObj,unitObj,healthAssistantObj);
		
		ViewResult result =  db.queryView(createQuery("search")
			.startKey(startkey)	
			.endKey(endkey)
			.includeDocs(false));
		
		int  count = 0;
		if(!result.getRows().isEmpty()){
			count = result.getRows().get(0).getValueAsInt();
		}
		return count;
		
	}
	
}
