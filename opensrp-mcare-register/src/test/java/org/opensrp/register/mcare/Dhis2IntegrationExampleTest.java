package org.opensrp.register.mcare;

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
import org.opensrp.register.mcare.report.mis1.familyPlanning.eligibleCoupleCount.EligibleCoupleCountTestData;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Dhis2IntegrationExampleTest {
    public String unionName = "union";
    private EligibleCoupleCountTestData eligibleCoupleCountTestData;
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
    public void exampleTest() throws IOException, JSONException {
        DateTime period = new DateTime().minusYears(1);
        List<Members> members = eligibleCoupleCountTestData.getTotalEligibleCoupleData();
        MIS1Report mis1Report = new MIS1Report(unionName, members, startDateTime, endDateTime);
        DHIS2ReportBuilder dhis2ReportBuilder = new DHIS2ReportBuilder("PKTk8zxbl0J", new DateTime(), period);
        List<DataValueSet> dataValueSets = dhis2ReportBuilder.build(mis1Report);
        DHIS2Service service = new DHIS2Service("http://123.200.18.20:8080", "dgfp", "Dgfp@123");
        for(DataValueSet dataValueSet : dataValueSets) {
             System.out.println(dataValueSet.send(service));
        }

    }
}
