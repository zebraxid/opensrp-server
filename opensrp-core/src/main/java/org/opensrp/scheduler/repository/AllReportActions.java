package org.opensrp.scheduler.repository;

import java.text.MessageFormat;
import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.GenerateView;
import org.motechproject.dao.MotechBaseRepository;
import org.opensrp.common.AllConstants;
import org.opensrp.scheduler.ReportAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class AllReportActions extends MotechBaseRepository<ReportAction> {
	
	private static Logger logger = LoggerFactory.getLogger(AllReportActions.class.toString());
	
	@Autowired
	public AllReportActions(@Qualifier(AllConstants.OPENSRP_DATABASE_CONNECTOR) CouchDbConnector db)
	{
		 super(ReportAction.class, db);
	}
	
	@GenerateView
    private List<ReportAction> findByCaseID(String caseId) {
        return queryView("by_caseID", caseId);
    }
	
	public void addAlert(ReportAction alertAction) {
        add(alertAction);
    }
	

}
