package org.opensrp.scheduler.service;

import org.joda.time.DateTime;
import org.opensrp.dto.ActionData;
import org.opensrp.dto.AlertStatus;
import org.opensrp.dto.BeneficiaryType;
import org.opensrp.scheduler.ReportAction;
import org.opensrp.scheduler.repository.AllReportActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportActionService {
	
	private AllReportActions allReportActions;
	
	@Autowired
	public ReportActionService(AllReportActions allReportActions)
	{
		this.allReportActions = allReportActions;
	}
	
	public void alertForReporting(BeneficiaryType beneficiaryType, String caseID, String anmIdentifier, String scheduleName, String visitCode, AlertStatus alertStatus, DateTime startDate, DateTime expiryDate)
	{
		allReportActions.addAlert(new ReportAction(caseID, anmIdentifier, ActionData.createAlert(beneficiaryType, scheduleName, visitCode, alertStatus, startDate, expiryDate)));
	}

}
