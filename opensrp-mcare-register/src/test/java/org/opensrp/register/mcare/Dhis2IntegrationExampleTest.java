package org.opensrp.register.mcare;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.ObjectMapper;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;
import org.joda.time.DateTime;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.opensrp.connector.DHIS2.DHIS2ReportBuilder;
import org.opensrp.connector.DHIS2.DHIS2Service;
import org.opensrp.connector.DHIS2.dxf2.DataValueSet;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.MIS1Report;
import org.opensrp.register.mcare.report.mis1.MIS1ReportGenerator;
import org.opensrp.register.mcare.report.mis1.birthAndDeath.AliveDeathCountTestData;
import org.opensrp.register.mcare.report.mis1.familyPlanning.eligibleCoupleCount.EligibleCoupleCountTestData;
import org.opensrp.register.mcare.repository.AllMembers;

import javax.print.attribute.standard.Fidelity;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Dhis2IntegrationExampleTest {
    public String unionName = "union";
    private EligibleCoupleCountTestData eligibleCoupleCountTestData;
    private AliveDeathCountTestData birthControlMethodTestData;
    private long startDateTime;
    private long endDateTime;

    @Before
    public void setUp() throws Exception {
        int totalCount = 1000;
        int validCount = ThreadLocalRandom.current().nextInt(0, totalCount);
        startDateTime = ThreadLocalRandom.current().nextLong(120000, 140000);
        endDateTime = ThreadLocalRandom.current().nextLong(170000, 200000);
        eligibleCoupleCountTestData =
                new EligibleCoupleCountTestData(totalCount, validCount, startDateTime, endDateTime);
        birthControlMethodTestData = new AliveDeathCountTestData(totalCount, validCount, startDateTime, endDateTime);
    }

    /**
     * orgUnit : dgfp
     * period: (default) 201612
     * dataSet : family planning
     * data element: eligible couple
     * category option com : total
     *
     * After running the test log in to dhis2 instance.
     * Run `Analatics` in report module.
     * Generate `standard report` of mis1(or any report containing the data set).
     * Use above org unit and period (dec, 2016). You can find the value in generated pdf.
     *
     * Pivot table and report generated in report module sometime doesn't reflect updated value.
     * In this case use a new period.
     *
     *
     * @throws IOException
     * @throws JSONException
     */

    @Test
    @Ignore
    public void exampleTest() throws IOException, JSONException, IllegalAccessException {
        DateTime period = new DateTime().minusYears(2);
        org.ektorp.http.HttpClient httpClient = new StdHttpClient.Builder().build();
        CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
// if the second parameter is true, the database will be created if it doesn't exists
        CouchDbConnector couchDbConnector = dbInstance.createConnector("opensrp", true);

        AllMembers allMembers = new AllMembers(1, couchDbConnector );
        List<Members> members = allMembers.allMembersBasedOnDistrictUpazillaUnionAndUpdateTimeStamp("Gaibandha", "Gaibandha Sadar", "Kuptala", "opensrp", null, null);
        System.out.println("members " + members.size());
       /* MIS1Report mis1Report = new MIS1Report(unionName, members, new DateTime(0l).getMillis(), new DateTime().getMillis());
        //System.out.println(new ObjectMapper().setVisibility(JsonMethod.FIELD, JsonAutoDetect.Visibility.ANY).enableDefaultTyping().writeValueAsString(mis1Report));
        DHIS2ReportBuilder dhis2ReportBuilder = new DHIS2ReportBuilder("PKTk8zxbl0J", new DateTime(), period);
        List<DataValueSet> dataValueSets = dhis2ReportBuilder.build(mis1Report);
        DHIS2Service service = new DHIS2Service("http://123.200.18.20:1971", "dgfp", "Dgfp@123");
        for(DataValueSet dataValueSet : dataValueSets) {
             System.out.println(dataValueSet.send(service));
        }*/

        MIS1ReportGenerator.Filter filter = new MIS1ReportGenerator.Filter("Gaibandha", "Gaibandha Sadar", "Kuptala", "opensrp", 2017, 10);
        MIS1Report mis1Report = new MIS1ReportGenerator().getReportBasedOn(filter);
        System.out.println(new ObjectMapper().setVisibility(JsonMethod.FIELD, JsonAutoDetect.Visibility.ANY).enableDefaultTyping().writeValueAsString(mis1Report));

    }
}
