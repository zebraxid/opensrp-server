package org.opensrp.register.mcare.visit.activity.service.impl;

import org.opensrp.connector.etl.service.VisitActivityApiService;
import org.opensrp.register.mcare.domain.Mother;
import org.opensrp.register.mcare.repository.AllMothers;
import org.opensrp.register.mcare.visit.activity.service.ActionAndScheduleActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PSRFVisitActivityServiceImpl extends ActionAndScheduleActivityService {
	
	@Autowired
	private AllMothers allMothers;
	
	@Autowired
	private VisitActivityApiService registerApiService;
	
	public void deleteMotherWithPSRFAndANCAndPNCAndBNFActionByCaseId(String provider, String caseId) {
		Mother mother = allMothers.findByCaseId(caseId);
		
		try {
			if (mother != null) {
				allMothers.remove(mother);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
		try {
			deleteAllActionAndUnenrollScheduleByCaseId(caseId);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
		registerApiService.deleteMotherAndRelatedInformation(caseId);
		
	}
	
	void deleteMotherWithANCAndPNCAndBNFActionByCaseId(String provider, String caseId) {
		Mother mother = allMothers.findByCaseId(caseId);
		try {
			if (mother != null) {
				allMothers.remove(mother);
				deleteANCActionAndUnenrollScheduleByCaseId(provider, caseId);
				deleteBNFActionAndUnenrollScheduleByCaseId(provider, caseId);
				
			}
		}
		catch (Exception e) {
			
		}
		try {
			deletePNCActionAndUnenrollScheduleByCaseId(provider, caseId);
		}
		catch (Exception e) {
			
		}
		
	}
}
