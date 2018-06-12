package org.opensrp.register.mcare.visit.activity.service.impl;

import org.opensrp.connector.etl.service.VisitActivityApiService;
import org.opensrp.register.mcare.domain.Child;
import org.opensrp.register.mcare.repository.AllChilds;
import org.opensrp.register.mcare.visit.activity.service.ActionAndScheduleActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BNFVisitActivityServiceImpl extends ActionAndScheduleActivityService {
	
	private static Logger logger = LoggerFactory.getLogger(BNFVisitActivityServiceImpl.class.toString());
	
	@Autowired
	private AllChilds allChilds;
	
	@Autowired
	private PSRFVisitActivityServiceImpl psrfVisitActivityServiceImpl;
	
	@Autowired
	private VisitActivityApiService visitActivityApiService;
	
	public void deleteMotherAndChildWithRelatedActionByCaseId(String provider, String caseId, String visitCode) {
		boolean isVisitActivityApiServiceCall = false;
		
		Child child = allChilds.findByRelationalId(caseId);
		if (child != null) {
			logger.error("child found at case id: " + caseId);
			allChilds.remove(child);
			deleteAllActionAndUnenrollScheduleByCaseId(child.caseId());
		} else {
			
			logger.error("no child found at case id: " + caseId);
		}
		
		psrfVisitActivityServiceImpl.deleteMotherWithPSRFAndANCAndPNCAndBNFActionByCaseId(provider, caseId,
		    isVisitActivityApiServiceCall);
		
		visitActivityApiService.deleteChildAndRelatedInformation(caseId, visitCode);
		
	}
	
	public void deleteChildByCaseId(String provider, String caseId) {
		
	}
	
	public void deleteMotherAndChildAndRelatedActionExceptPSRFByCaseId(String provider, String caseId, String visitCode) {
		
		Child child = allChilds.findByRelationalId(caseId);
		if (child != null) {
			logger.error("child found at case id: " + caseId);
			allChilds.remove(child);
			deleteAllActionAndUnenrollScheduleByCaseId(child.caseId());
		} else {
			logger.error("no child found at case id: " + caseId);
		}
		psrfVisitActivityServiceImpl.deleteMotherWithANCAndPNCAndBNFActionByCaseId(provider, caseId);
		visitActivityApiService.deleteChildAndRelatedInformation(caseId, visitCode);
	}
	
}
