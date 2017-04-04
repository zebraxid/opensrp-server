/**
 * @author proshanto
 */

package org.opensrp.register.mcare.repository;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.GenerateView;
import org.motechproject.dao.MotechBaseRepository;
import org.opensrp.common.AllConstants;
import org.opensrp.register.mcare.domain.Stock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class AllStocks extends MotechBaseRepository<Stock> {

	private static Logger logger = LoggerFactory.getLogger(AllStocks.class);

	@Autowired
	public AllStocks(@Value("#{opensrp['couchdb.atomfeed-db.revision-limit']}") int revisionLimit,
			@Qualifier(AllConstants.OPENSRP_DATABASE_CONNECTOR) CouchDbConnector db) {
		super(Stock.class, db);
		this.db.setRevisionLimit(revisionLimit);
	}

	@GenerateView
	public Stock findByCaseId(String caseId) {
		List<Stock> stocks = queryView("by_caseId", caseId);
		if (stocks == null || stocks.isEmpty()) {
			return null;
		}
		return stocks.get(0);
	}

	
	


}
