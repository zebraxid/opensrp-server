package org.opensrp.register.mcare.service.reporting;

import org.opensrp.common.domain.Location;
import org.opensrp.register.mcare.domain.Child;
import org.opensrp.register.mcare.repository.AllChilds;
import org.opensrp.service.reporting.IReporter;
import org.opensrp.util.SafeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChildReporter implements IReporter {
	
	private AllChilds allChilds;
	
	@Autowired
	public ChildReporter(AllChilds allChilds) {
		this.allChilds = allChilds;
	}
	
	@Override
	public void report(String entityId, String reportIndicator, Location location, String serviceProvidedDate,
	                   SafeMap reportData) {
		Child child = allChilds.findByCaseId(entityId);
		
	}
	
}
