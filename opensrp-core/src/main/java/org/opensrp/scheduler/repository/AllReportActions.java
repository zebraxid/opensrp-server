package org.opensrp.scheduler.repository;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opensrp.dto.AlertStatus;
import org.opensrp.dto.BeneficiaryType;
import org.ektorp.ComplexKey;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewResult;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.motechproject.dao.MotechBaseRepository;
import org.opensrp.common.AllConstants;
import org.opensrp.dto.ActionData;
import org.opensrp.scheduler.ReportAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import static org.opensrp.dto.AlertStatus.normal;
import static org.opensrp.dto.BeneficiaryType.elco;

@Repository
public class AllReportActions extends MotechBaseRepository<ReportAction> {
	
	private static Logger logger = LoggerFactory.getLogger(AllReportActions.class.toString());
	
	@Autowired
	public AllReportActions(@Qualifier(AllConstants.OPENSRP_DATABASE_CONNECTOR) CouchDbConnector db)
	{
		 super(ReportAction.class, db);
	}
	
	@GenerateView
    private List<ReportAction> findByCaseId(String caseId) {
        return queryView("by_caseId", caseId);
    }
	
	public void addAlert(ReportAction alertAction) {
        add(alertAction);
    }
	
	@View(name = "all_visits_scheduled_by_server" , 
			 map = "function(doc) { " +
	                    "if(doc.type === 'ReportAction' && doc.actionTarget === 'alert' && doc.anmIdentifier && doc.caseID && doc.data && doc.data.scheduleName) {" +
	                    "emit([doc.anmIdentifier, doc.data.scheduleName], null)} " +
	                    "}")
	public Map<String, ReportAction> findAllSchedulesForVisits(String anmIdentifier, String scheduleName, LocalDate startDate, LocalDate endDate)
	{
		Map<String, ReportAction> schedulesMap = new HashMap<String,ReportAction>();
		 ComplexKey key = ComplexKey.of(anmIdentifier, scheduleName);
		
		 List<ViewResult.Row> rows = db.queryView(createQuery("all_visits_scheduled_by_server").key(key)
				 .cacheOk(true)).getRows();
		 
		 for(ViewResult.Row row : rows)
		 {
			 if(!schedulesMap.containsKey(row.getKey()))
			 {
				 
				 ActionData actionData = ActionData.createAlert(
						 				 BeneficiaryType.from(row.getValueAsNode().findValue("data").get("beneficiaryType").asText()),
										 row.getValueAsNode().findValue("data").get("scheduleName").toString(),
										 row.getValueAsNode().findValue("data").get("visitCode").toString(),
										 AlertStatus.from(row.getValueAsNode().findValue("data").get("alertStatus").asText()),
										 DateTime.parse(row.getValueAsNode().findValue("data").get("startDate").asText()),
										 DateTime.parse(row.getValueAsNode().findValue("data").get("expiryDate").asText())
						 				);
				 
				 schedulesMap.put(row.getKey(), new ReportAction(row.getValueAsNode().findValue("caseID").toString(), row.getValueAsNode().findValue("instanceId").toString(),row.getValueAsNode().findValue("anmIdentifier").toString(), actionData));
			 }
		 }
				
		 return schedulesMap;
	}
	
	

}
