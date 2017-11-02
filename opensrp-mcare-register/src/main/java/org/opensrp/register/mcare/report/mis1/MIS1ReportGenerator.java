package org.opensrp.register.mcare.report.mis1;

import org.joda.time.DateTime;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.repository.AllMembers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MIS1ReportGenerator {

    public long start;
    public long end;

    @Autowired
    private AllMembers allMembers;

    public List<Members> getAllCalculatorValue() {

        List<Members> getAllMembers = allMembers.allMembersCreatedBetweenTwoDateBasedOnUpdatedTimeStamp(1504224000000L);

        long value = getAllMembers.size();

        return getAllMembers;
    }

    public MIS1Report getReportBasedOn(Filter filter) {
        DateTime startDateTime = new DateTime(filter.year, filter.month, 1, 0, 0, 0);
        DateTime endDateTime = startDateTime.dayOfMonth().withMaximumValue();    	
        List<Members> members = allMembers.allMembersBasedOnDistrictUpazillaUnionAndUpdateTimeStamp(filter.district, filter.subDistrict, filter.union, filter.worker, startDateTime.getMillis(), endDateTime.getMillis());
        MIS1Report mis1Report = new MIS1Report("not necessary", members, start, end);
        return mis1Report;
    }

  

}
