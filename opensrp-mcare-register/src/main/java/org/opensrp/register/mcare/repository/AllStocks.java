/**
 * @author proshanto
 */

package org.opensrp.register.mcare.repository;

import java.util.List;

import org.ektorp.ComplexKey;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
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

	
	@View(name = "stock_by_clientVersion_provider", map = "function(doc) { if(doc.type === 'Stock') { var x = new Date(); var y = new Date(x.getFullYear(), x.getMonth()-3, 0); var time = y.getTime(); if(doc.clientVersion > time){ emit([doc.provider,doc.clientVersion], null)} } }")
	public List<Stock> allStocksByProviderClientVersion(String provider, String clientVersion){
		ComplexKey start = ComplexKey.of(provider,clientVersion);
		ComplexKey end = ComplexKey.of(provider,clientVersion);
		List<Stock> stocks =  db.queryView(
				createQuery("stock_by_clientVersion_provider")
				.startKey(start)
				.endKey(end)
				.includeDocs(true), Stock.class);
		
		return stocks;
	}


}
