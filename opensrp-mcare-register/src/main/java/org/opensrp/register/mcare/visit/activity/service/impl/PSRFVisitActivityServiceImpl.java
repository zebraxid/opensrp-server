package org.opensrp.register.mcare.visit.activity.service.impl;

import org.opensrp.connector.etl.service.VisitActivityApiService;
import org.opensrp.register.mcare.domain.Mother;
import org.opensrp.register.mcare.repository.AllMothers;
import org.opensrp.register.mcare.visit.activity.service.ActionAndScheduleActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PSRFVisitActivityServiceImpl extends ActionAndScheduleActivityService {
	
	private static Logger logger = LoggerFactory.getLogger(PSRFVisitActivityServiceImpl.class.toString());
	
	@Autowired
	private AllMothers allMothers;
	
	@Autowired
	private VisitActivityApiService visitActivityApiService;
	
	public void deleteMotherWithPSRFAndANCAndPNCAndBNFActionByCaseId(String provider, String caseId,
	                                                                 boolean isVisitActivityApiServiceCall) {
		Mother mother = allMothers.findByCaseId(caseId);
		if (mother != null) {
			logger.error("mother found at case id: " + caseId);
			allMothers.remove(mother);
		} else {
			logger.error("no mother found at case id: " + caseId);
		}
		
		deleteAllActionAndUnenrollScheduleByCaseId(caseId);
		if (isVisitActivityApiServiceCall) {
			visitActivityApiService.deleteMotherAndRelatedInformation(caseId);
		}
		
	}
	
	void deleteMotherWithANCAndPNCAndBNFActionByCaseId(String provider, String caseId) {
		Mother mother = allMothers.findByCaseId(caseId);
		if (mother != null) {
			logger.error("mother found at case id: " + caseId);
			allMothers.remove(mother);
			deleteANCActionAndUnenrollScheduleByCaseId(provider, caseId);
			deleteBNFActionAndUnenrollScheduleByCaseId(provider, caseId);
			deletePNCActionAndUnenrollScheduleByCaseId(provider, caseId);
			
		} else {
			
			logger.error("no mother found at case id: " + caseId);
		}
		
	}
}
