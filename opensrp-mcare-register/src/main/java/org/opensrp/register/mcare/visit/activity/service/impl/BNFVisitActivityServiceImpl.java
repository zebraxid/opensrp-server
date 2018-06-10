package org.opensrp.register.mcare.visit.activity.service.impl;

import org.opensrp.connector.etl.service.VisitActivityApiService;
import org.opensrp.register.mcare.domain.Child;
import org.opensrp.register.mcare.repository.AllChilds;
import org.opensrp.register.mcare.visit.activity.service.ActionAndScheduleActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BNFVisitActivityServiceImpl extends ActionAndScheduleActivityService {
	
	@Autowired
	private AllChilds allChilds;
	
	@Autowired
	private PSRFVisitActivityServiceImpl psrfVisitActivityServiceImpl;
	
	@Autowired
	private VisitActivityApiService registerApiService;
	
	public void deleteMotherAndChildWithRelatedActionByCaseId(String provider, String caseId, String visitCode) {
		System.err.println("CAESID:" + caseId);
		try {
			Child child = allChilds.findByRelationalId(caseId);
			if (child != null) {
				allChilds.remove(child);
				deleteAllActionAndUnenrollScheduleByCaseId(child.caseId());
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
		psrfVisitActivityServiceImpl.deleteMotherWithPSRFAndANCAndPNCAndBNFActionByCaseId(provider, caseId);
		
		registerApiService.deleteChildAndRelatedInformation(caseId, visitCode);
		
	}
	
	public void deleteChildByCaseId(String provider, String caseId) {
		
	}
	
	public void deleteMotherAndChildAndRelatedActionExceptPSRFByCaseId(String provider, String caseId, String visitCode) {
		
		try {
			Child child = allChilds.findByRelationalId(caseId);
			if (child != null) {
				allChilds.remove(child);
				deleteAllActionAndUnenrollScheduleByCaseId(child.caseId());
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		psrfVisitActivityServiceImpl.deleteMotherWithANCAndPNCAndBNFActionByCaseId(provider, caseId);
		registerApiService.deleteChildAndRelatedInformation(caseId, visitCode);
	}
	
}
