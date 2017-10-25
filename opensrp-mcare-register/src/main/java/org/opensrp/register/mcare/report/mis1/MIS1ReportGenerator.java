package org.opensrp.register.mcare.report.mis1;

import java.util.List;

import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.repository.AllMembers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class MIS1ReportGenerator {

	public static long start = 1506816000;
	public static long end = 1508092200;

	@Autowired
	private AllMembers allMembers;

	public List<Members> getAllCalculatorValue() {

		List<Members> getAllMembers = allMembers.allMembersCreatedBetweenTwoDateBasedOnUpdatedTimeStamp(1504224000000L);
		
		long value = getAllMembers.size();

		return getAllMembers;
	}

}
