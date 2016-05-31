package org.opensrp.register.mcare.repository;

import java.util.List;

import org.ektorp.ComplexKey;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewResult;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.motechproject.dao.MotechBaseRepository;
import org.opensrp.common.AllConstants;
import org.opensrp.register.mcare.domain.HouseHold;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class AllHouseHolds extends MotechBaseRepository<HouseHold> {

	private static Logger logger = LoggerFactory.getLogger(AllHouseHolds.class);

	@Autowired
	public AllHouseHolds(
			@Qualifier(AllConstants.OPENSRP_DATABASE_CONNECTOR) CouchDbConnector db) {
		super(HouseHold.class, db);
	}

	@GenerateView
	public HouseHold findByCaseId(String caseId) {
		List<HouseHold> houseHolds = queryView("by_caseId", caseId);
		if (houseHolds == null || houseHolds.isEmpty()) {
			return null;
		}
		return houseHolds.get(0);
	}

	@View(name = "all_households", map = "function(doc) { if (doc.type === 'HouseHold') { emit(doc.PROVIDERID, doc.caseId); } }")
	public List<HouseHold> findAllHouseHolds() {
		return db.queryView(createQuery("all_households").includeDocs(true),
				HouseHold.class);
	}
	@View(name = "get_all_household", map = "function(doc) { if (doc.type === 'HouseHold' && doc.FWNHHHGPS) { emit(doc._id, [doc.FWNHHHGPS.split(' ')[0],doc.FWNHHHGPS.split(' ')[1],doc.ELCO,doc.FWMAUZA_PARA,doc.FWDIVISION,doc.FWDISTRICT,doc.FWUPAZILLA,doc.FWUNION,doc.FWWARD]); } }")
	public List<HouseHold> allHouseHolds() {
		return db.queryView(createQuery("get_all_household").includeDocs(true),
				HouseHold.class);
	}

	@View(name = "all_open_hhs_for_provider", map = "function(doc) { if (doc.type === 'HouseHold' && doc.PROVIDERID) { emit(doc.PROVIDERID,doc._id ); } }")
	public List<HouseHold> allOpenHHsForProvider(String providerId) {
		return db.queryView(
				createQuery("all_open_hhs_for_provider").key(providerId)
						.includeDocs(true), HouseHold.class);
	}

	@View(name = "all_hhs_prev_7_days", map = "function(doc) { if (doc.type === 'HouseHold' && doc.PROVIDERID && doc.TODAY) { emit(doc.PROVIDERID, doc.TODAY); } }")
	public List<HouseHold> allHHsVisited7Days(String providerId) {

		LocalDate today = LocalDate.now();
		
		List<HouseHold> houseHolds = db.queryView(
				createQuery("all_hhs_prev_7_days")
						.startKey(today.minusDays(100))
						.endKey(today)
						//.key(providerId)
						.includeDocs(true), HouseHold.class);

		return houseHolds;
	}
	
	@View(name = "created_in_last_4_months_by_location", map = "function(doc) { if(doc.type === 'HouseHold' && doc.SUBMISSIONDATE && doc.FWDISTRICT && doc.FWUPAZILLA && doc.FWUNION) { var x = new Date(); var y = new Date(x.getFullYear(), x.getMonth()-3, 0); var time = y.getTime(); if(doc.SUBMISSIONDATE > time){ emit([doc.FWDISTRICT,doc.FWUPAZILLA,doc.FWUNION], doc.SUBMISSIONDATE)} } }")
	public List<HouseHold> allHHsCreatedLastFourMonthsByLocation(String startKey, String endKey){
		//ComplexKey startKey = ComplexKey.of(District, Upazilla, Union);
		List<HouseHold> households =  db.queryView(
				createQuery("created_in_last_4_months_by_location")
				.rawStartKey(startKey)
				.rawEndKey(endKey)
				.includeDocs(true), HouseHold.class);
		
		return households;
	}
	
	public ViewResult allHHsCreatedLastFourMonthsByLocationViewResult(String startKey, String endKey){
		
		ViewResult vr = db.queryView(
				createQuery("created_in_last_4_months_by_location")
				.rawStartKey(startKey)
				.rawEndKey(endKey)
				.includeDocs(false));
		
		return vr;
	}
	
	public ViewResult allHHsCreatedLastFourMonthsByProviderAndLocationViewResult(String startKey, String endKey){
		
		ViewResult vr = db.queryView(
				createQuery("created_in_last_4_months_by_provider_and_location")
				.rawStartKey(startKey)
				.rawEndKey(endKey)
				.includeDocs(false));
		
		return vr;
	}
	
	@View(name = "created_in_last_4_months_by_provider_and_location", map = "function(doc) { if(doc.type === 'HouseHold' && doc.SUBMISSIONDATE && doc.PROVIDERID && doc.FWDISTRICT && doc.FWUPAZILLA && doc.FWUNION) { var x = new Date(); var y = new Date(x.getFullYear(), x.getMonth()-3, 0); var time = y.getTime(); if(doc.SUBMISSIONDATE > time){ emit([doc.PROVIDERID,doc.FWDISTRICT,doc.FWUPAZILLA,doc.FWUNION], doc.SUBMISSIONDATE)} } }")
	public List<HouseHold> allHHsCreatedLastFourMonthsByProviderAndLocation(String startKey, String endKey){
		List<HouseHold> households =  db.queryView(
				createQuery("created_in_last_4_months_by_provider_and_location")
				.rawStartKey(startKey)
				.rawEndKey(endKey)
				.includeDocs(true), HouseHold.class);
			
		return households;
	}
	
	public List<HouseHold> allHHsCreatedLastFourMonthsByLocationAnother(String District, String Upazilla, String Union){
		ComplexKey complexKey = ComplexKey.of(District, Upazilla, ComplexKey.emptyObject());		
		List<HouseHold> households =  db.queryView(
				createQuery("created_in_last_4_months_by_location")
				.key(complexKey)
				.includeDocs(true), HouseHold.class);
		
		return households;
	}
}
