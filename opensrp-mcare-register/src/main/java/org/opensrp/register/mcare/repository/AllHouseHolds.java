package org.opensrp.register.mcare.repository;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.motechproject.dao.MotechBaseRepository;
import org.opensrp.common.AllConstants;
import org.opensrp.register.mcare.domain.HouseHold;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class AllHouseHolds extends MotechBaseRepository<HouseHold> {

	private static Logger logger = LoggerFactory.getLogger(AllHouseHolds.class);

	@Autowired
	public AllHouseHolds(@Qualifier(AllConstants.OPENSRP_DATABASE_CONNECTOR) CouchDbConnector db) {
		super(HouseHold.class, db);
	}

	@GenerateView
	public HouseHold findByCASEID(String caseId) {
		List<HouseHold> houseHolds = queryView("by_cASEID", caseId);
		if (houseHolds == null || houseHolds.isEmpty()) {
			return null;
		}
		return houseHolds.get(0);
	}
	
	 @View(name = "all_households",
	            map = "function(doc) { if (doc.type === 'HouseHold' && doc.ELCODETAILS.FWWOMBID) { emit(doc.PROVIDERID, doc.CASEID); } }")
	    public List<HouseHold> findAllHouseHolds() {
	        return db.queryView(createQuery("all_households")
	                .includeDocs(true), 
	                 HouseHold.class);
	    }
	
}
