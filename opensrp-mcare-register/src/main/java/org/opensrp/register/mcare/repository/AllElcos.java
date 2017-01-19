package org.opensrp.register.mcare.repository;

import java.util.Calendar;
import java.util.List;

import org.ektorp.ComplexKey;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewResult;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.motechproject.dao.MotechBaseRepository;
import org.opensrp.common.AllConstants;
import org.opensrp.register.mcare.domain.Elco;
import org.opensrp.register.mcare.domain.HouseHold;
import org.opensrp.register.mcare.domain.Mother;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class AllElcos extends MotechBaseRepository<Elco> {

	@Autowired
	public AllElcos(@Value("#{opensrp['couchdb.atomfeed-db.revision-limit']}") int revisionLimit,
			@Qualifier(AllConstants.OPENSRP_DATABASE_CONNECTOR) CouchDbConnector db) {
		super(Elco.class, db);
		this.db.setRevisionLimit(revisionLimit);
	}

	@GenerateView
	public Elco findByCaseId(String caseId) {
		List<Elco> elcos = queryView("by_caseId", caseId);
		if (elcos == null || elcos.isEmpty()) {
			return null;
		}
		return elcos.get(0);
	}

	public boolean exists(String caseId) {
		return findByCaseId(caseId) != null;
	}
	public void close(String caseId) {
		Elco elco = findByCaseId(caseId);
		update(elco.setIsClosed(true));
	}
	@View(name = "all_elcos", map = "function(doc) { if (doc.type === 'Elco') { emit(doc.PROVIDERID, doc.caseId); } }")
	public List<Elco> findAllELCOs() {
		return db.queryView(createQuery("all_elcos").includeDocs(true),
				Elco.class);
	}
	@View(name = "all_open_elcos_for_provider", map = "function(doc) { if (doc.type === 'Elco' && doc.PROVIDERID) { emit(doc.PROVIDERID); } }")
	public List<Elco> allOpenELCOsForProvider(String providerId) {
		return db.queryView(
				createQuery("all_open_elcos_for_provider").key(providerId)
						.includeDocs(true), Elco.class);
	}
	@View(name = "all_open_elcos_for_provider", map = "function(doc) { if (doc.type === 'Elco' && doc.PROVIDERID) { emit(doc.PROVIDERID); } }")
	public List<Elco> allOpenELCOs() {
		return db.queryView(
				createQuery("all_open_elcos_for_provider")
						.includeDocs(true), Elco.class);
	}
	
	@View(name = "created_in_last_4_months_by_provider_and_location", map = "function(doc) { if(doc.type === 'Elco' && doc.SUBMISSIONDATE && doc.PROVIDERID && doc.FWWOMDISTRICT && doc.FWWOMUPAZILLA && doc.FWWOMUNION) { var x = new Date(); var y = new Date(x.getFullYear(), x.getMonth()-3, 0); var time = y.getTime(); if(doc.SUBMISSIONDATE > time){ emit([doc.PROVIDERID,doc.FWWOMDISTRICT,doc.FWWOMUPAZILLA,doc.FWWOMUNION], doc.SUBMISSIONDATE)} } }")
	public List<Elco> allElcosCreatedLastFourMonthsByProviderAndLocation(String startKey, String endKey){
		List<Elco> elcos =  db.queryView(
				createQuery("created_in_last_4_months_by_provider_and_location")
				.rawStartKey(startKey)
				.rawEndKey(endKey)
				.includeDocs(true), Elco.class);
			
		return elcos;
	}
	
	@View(name = "created_in_last_4_months_by_location", map = "function(doc) { if(doc.type === 'Elco' && doc.SUBMISSIONDATE && doc.FWWOMDISTRICT && doc.FWWOMUPAZILLA && doc.FWWOMUNION) { var x = new Date(); var y = new Date(x.getFullYear(), x.getMonth()-3, 0); var time = y.getTime(); if(doc.SUBMISSIONDATE > time){ emit([doc.FWWOMDISTRICT,doc.FWWOMUPAZILLA,doc.FWWOMUNION], doc.SUBMISSIONDATE)} } }")
	public List<Elco> allElcosCreatedLastFourMonthsByLocation(String startKey, String endKey){
		List<Elco> elcos =  db.queryView(
				createQuery("created_in_last_4_months_by_location")
				.rawStartKey(startKey)
				.rawEndKey(endKey)
				.includeDocs(true), Elco.class);
		
		return elcos;
	}
	
	@View(name = "created_in_last_4_months", map = "function(doc) { if(doc.type === 'Elco' && doc.SUBMISSIONDATE && doc.caseId) { emit([doc.caseId], doc.SUBMISSIONDATE) } }")
	public List<Elco> allElcosCreatedLastFourMonths(){
		Calendar cal = Calendar.getInstance();
		String startKey = Long.toString(cal.getTimeInMillis());
		cal.add(Calendar.DAY_OF_YEAR, -120);
		String endKey = Long.toString(cal.getTimeInMillis());
		
		List<Elco> elcos =  db.queryView(
				createQuery("created_in_last_4_months")
				.rawStartKey(startKey)
				.rawEndKey(endKey)
				.includeDocs(true), Elco.class);
		
		return elcos;
	}
	
	@View(name = "created_in_between_2_dates", map = "function(doc) { if(doc.type === 'Elco' && doc.type && doc.SUBMISSIONDATE) { emit( [doc.type, doc.SUBMISSIONDATE], null); } }")
	public List<Elco> allElcosCreatedBetween2Dates(String type, long startKey, long endKey){
		ComplexKey start = ComplexKey.of(type,startKey);
		ComplexKey end = ComplexKey.of(type,endKey);
		List<Elco> elcos =  db.queryView(
				createQuery("created_in_between_2_dates")
				.startKey(start)
				.endKey(end)
				.includeDocs(true), Elco.class);
		
		return elcos;
	}
	
	public ViewResult allElcosCreatedLastFourMonthsViewResult(){
		
		ViewResult vr = db.queryView(
				createQuery("created_in_last_4_months")
				.includeDocs(false));
		
		return vr;
	}
	
	public ViewResult allElcosCreatedLastFourMonthsByLocationViewResult(String startKey, String endKey){
		
		ViewResult vr = db.queryView(
				createQuery("created_in_last_4_months_by_location")
				.rawStartKey(startKey)
				.rawEndKey(endKey)
				.includeDocs(false));
		
		return vr;
	}
	
	public ViewResult allElcosCreatedLastFourMonthsByProviderAndLocationViewResult(String startKey, String endKey){
		
		ViewResult vr = db.queryView(
				createQuery("created_in_last_4_months_by_provider_and_location")
				.rawStartKey(startKey)
				.rawEndKey(endKey)
				.includeDocs(false));
		
		return vr;
	}
	
	@View(name = "mother_created_in_last_4_months_by_provider_and_location", map = "function(doc) { if(doc.type === 'Elco' && doc.SUBMISSIONDATE && doc.PROVIDERID && doc.FWWOMDISTRICT && doc.FWWOMUPAZILLA && doc.FWWOMUNION) { var x = new Date(); var y = new Date(x.getFullYear(), x.getMonth()-3, 0); var time = y.getTime(); if(doc.SUBMISSIONDATE > time && doc.PSRFDETAILS != '' && doc.details.FWPSRPREGSTS == 1){ emit([doc.PROVIDERID,doc.FWWOMDISTRICT,doc.FWWOMUPAZILLA,doc.FWWOMUNION], doc.SUBMISSIONDATE)} } }")
	public List<Elco> allMothersCreatedLastFourMonthsByProviderAndLocation(String startKey, String endKey){
		List<Elco> elcos =  db.queryView(
				createQuery("mother_created_in_last_4_months_by_provider_and_location")
				.rawStartKey(startKey)
				.rawEndKey(endKey)
				.includeDocs(true), Elco.class);
			
		return elcos;
	}
	
	@View(name = "mother_created_in_last_4_months_by_location", map = "function(doc) { if(doc.type === 'Elco' && doc.SUBMISSIONDATE && doc.FWWOMDISTRICT && doc.FWWOMUPAZILLA && doc.FWWOMUNION) { var x = new Date(); var y = new Date(x.getFullYear(), x.getMonth()-3, 0); var time = y.getTime(); if(doc.SUBMISSIONDATE > time && doc.PSRFDETAILS != '' && doc.details.FWPSRPREGSTS == 1){ emit([doc.FWWOMDISTRICT,doc.FWWOMUPAZILLA,doc.FWWOMUNION], doc.SUBMISSIONDATE)} } }")
	public List<Elco> allMothersCreatedLastFourMonthsByLocation(String startKey, String endKey){
		List<Elco> elcos =  db.queryView(
				createQuery("mother_created_in_last_4_months_by_location")
				.rawStartKey(startKey)
				.rawEndKey(endKey)
				.includeDocs(true), Elco.class);
			
		return elcos;
	}
	
	@View(name = "mother_created_in_last_4_months", map = "function(doc) { if(doc.type === 'Elco' && doc.SUBMISSIONDATE && doc.PROVIDERID && doc.PSRFDETAILS != '' && doc.details.FWPSRPREGSTS == 1) { emit([doc.PROVIDERID], doc.SUBMISSIONDATE)} }")
	public List<Elco> allMothersCreatedLastFourMonths(){
		Calendar cal = Calendar.getInstance();
		String startKey = Long.toString(cal.getTimeInMillis());
		cal.add(Calendar.DAY_OF_YEAR, -120);
		String endKey = Long.toString(cal.getTimeInMillis());
		
		List<Elco> elcos =  db.queryView(
				createQuery("mother_created_in_last_4_months")
				.rawStartKey(startKey)
				.rawEndKey(endKey)
				.includeDocs(true), Elco.class);
			
		return elcos;
	}
	
	public ViewResult allMothersCreatedLastFourMonthsViewResult(){
		
		ViewResult vr = db.queryView(
				createQuery("mother_created_in_last_4_months")
				.includeDocs(false));
		
		return vr;
	}
	
	public ViewResult allMothersCreatedLastFourMonthsByLocationViewResult(String startKey, String endKey){
		
		ViewResult vr = db.queryView(
				createQuery("mother_created_in_last_4_months_by_location")
				.rawStartKey(startKey)
				.rawEndKey(endKey)
				.includeDocs(false));
		
		return vr;
	}
	
	public ViewResult allMothersCreatedLastFourMonthsByProviderAndLocationViewResult(String startKey, String endKey){
		
		ViewResult vr = db.queryView(
				createQuery("mother_created_in_last_4_months_by_provider_and_location")
				.rawStartKey(startKey)
				.rawEndKey(endKey)
				.includeDocs(false));
		
		return vr;
	}
	
	@View(name = "elcoCount", map = "function(doc) { if (doc.type === 'Elco') { emit(doc.id); } }",reduce="_count")
    public int countElcos() {
        System.out.println("Elco ALL time start:"+System.currentTimeMillis());
        return db.queryView(createQuery("elcoCount")).getRows().get(0).getValueAsInt();
    }
}
