package org.opensrp.register.mcare.service.reporting;

import static org.opensrp.common.AllConstants.FormEntityTypes.Child_TYPE;

import org.opensrp.common.domain.Location;
import org.opensrp.register.mcare.domain.Child;
import org.opensrp.register.mcare.repository.AllChilds;
import org.opensrp.service.reporting.ILocationLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocationLoader implements ILocationLoader {
	
	private AllChilds allChilds;
	
	@Autowired
	public LocationLoader(AllChilds allChilds) {
		this.allChilds = allChilds;
	}
	
	@Override
	public Location loadLocationFor(String bindType, String caseId) {
		if (bindType.equalsIgnoreCase(Child_TYPE))
			return loadLocationForChild(caseId);
		
		return null;
	}
	
	private Location loadLocationForChild(String caseId) {
		Child child = allChilds.findByCaseId(caseId);
		//return couple.location();
		return new Location(child.LOCATIONID(), child.LOCATIONID(), child.LOCATIONID());
	}
	
	/* private Location loadLocationForEC(String caseId) {
	     EligibleCouple couple = allEligibleCouples.findByCaseId(caseId);
	     //return couple.location();
	     return new Location(couple.locationId(), couple.locationId(), couple.locationId());
	 }

	 private Location loadLocationForMother(String caseId) {
	     Mother mother = allMothers.findByCaseId(caseId);
	     return loadLocationForEC(mother.ecCaseId());
	 }

	 private Location loadLocationForChild(String caseId) {
	     Child child = allChildren.findByCaseId(caseId);
	     return loadLocationForMother(child.motherCaseId());
	 }*/
}
