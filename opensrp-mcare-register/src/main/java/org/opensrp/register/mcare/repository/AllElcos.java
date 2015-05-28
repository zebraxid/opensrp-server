package org.opensrp.register.mcare.repository;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.GenerateView;
import org.motechproject.dao.MotechBaseRepository;
import org.opensrp.common.AllConstants;
import org.opensrp.register.mcare.domain.Elco;
import org.opensrp.register.mcare.domain.HouseHold;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class AllElcos extends MotechBaseRepository<Elco>{

	@Autowired
	public AllElcos(@Qualifier(AllConstants.OPENSRP_DATABASE_CONNECTOR) CouchDbConnector db) {
		super(Elco.class, db);
	}

	@GenerateView
	public Elco findByCaseId(String caseId) {
		List<Elco> elcos = queryView("by_cASEID", caseId);
		if (elcos == null || elcos.isEmpty()) {
			return null;
		}
		return elcos.get(0);
	}
}
