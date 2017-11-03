package org.opensrp.register.mcare.report.mis1;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;
import org.joda.time.DateTime;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.repository.AllMembers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MIS1ReportGenerator {

    private AllMembers allMembers;

    public MIS1ReportGenerator() {
    }

    @Autowired
    public MIS1ReportGenerator(AllMembers allMembers) {
        this.allMembers = allMembers;
    }


    public List<Members> getAllCalculatorValue() {

        List<Members> getAllMembers = allMembers.allMembersCreatedBetweenTwoDateBasedOnUpdatedTimeStamp(1504224000000L);

        long value = getAllMembers.size();

        return getAllMembers;
    }

    public MIS1Report getReportBasedOn(Filter filter) {
        DateTime startDateTime = new DateTime(filter.year, filter.month, 1, 0, 0, 0);
        DateTime endDateTime = startDateTime.dayOfMonth().withMaximumValue();
        if (allMembers == null) {
            org.ektorp.http.HttpClient httpClient = new StdHttpClient.Builder().username("opensrp").password("opensrp").build();
            CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
// if the second parameter is true, the database will be created if it doesn't exists
            CouchDbConnector couchDbConnector = dbInstance.createConnector("opensrp", true);
            allMembers = new AllMembers(1, couchDbConnector);
            System.out.println("****** NUll all members found. Created own.");
        }
        List<Members> members = allMembers.allMembersBasedOnDistrictUpazillaUnionAndUpdateTimeStamp(filter.district, filter.subDistrict, filter.union,filter.ward, filter.unit, filter.worker, startDateTime.getMillis(), endDateTime.getMillis());
        MIS1Report mis1Report = new MIS1Report("not necessary", members, startDateTime.getMillis(), endDateTime.getMillis());
        return mis1Report;
    }


}
