package org.opensrp.register.mcare.service.reporting;

import static org.opensrp.common.AllConstants.FormEntityTypes.Child_TYPE;

import org.opensrp.service.reporting.IReporter;
import org.opensrp.service.reporting.IReporterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReporterFactory implements IReporterFactory {
	
	private ChildReporter childReporter;
	
	@Autowired
	public ReporterFactory(ChildReporter childReporter) {
		this.childReporter = childReporter;
	}
	
	public IReporter reporterFor(String entityType) {
		if (entityType.equalsIgnoreCase(Child_TYPE))
			return childReporter;
		/*
		 * if (entityType.equalsIgnoreCase(MOTHER_TYPE)) return motherReporter;
		 * if (entityType.equalsIgnoreCase(ELIGIBLE_COUPLE_TYPE)) return
		 * eligibleCoupleReporter;
		 */
		return null;
	}
}
