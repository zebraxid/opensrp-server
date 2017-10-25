package org.opensrp.register.mcare.report.mis1.familyPlanning.eligibleCoupleCount;


import org.joda.time.DateTime;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.opensrp.connector.DHIS2.DHIS2ReportBuilder;
import org.opensrp.connector.DHIS2.DHIS2Service;
import org.opensrp.connector.DHIS2.dxf2.DataValue;
import org.opensrp.connector.DHIS2.dxf2.DataValueSet;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.MIS1Report;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertEquals;

@Ignore
public class EligibleCoupleCountCalculatorTest {
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

    @Test
    public void newEligibleCoupleVisitCountTest() {
        List<Members> members = eligibleCoupleCountTestData.getNewEligibleCoupleVisitData();
        MIS1Report mis1Report = new MIS1Report(unionName,members, startDateTime, endDateTime);

        int newEligibleCoupleVisitCount = mis1Report.getFamilyPlanningReport().getEligibleCoupleCountCalculator().
                getNewEligibleCoupleVisitCount();

        assertEquals(eligibleCoupleCountTestData.validCount, 1);
    }

    @Test
    public void totalEligibleCoupleTest() {
        List<Members> members = eligibleCoupleCountTestData.getTotalEligibleCoupleData();
        MIS1Report mis1Report = new MIS1Report(unionName, members, startDateTime, endDateTime);

        int totalEligibleCoupleCount = mis1Report.getFamilyPlanningReport().getEligibleCoupleCountCalculator().getTotalEligibleCouple();

        assertEquals(eligibleCoupleCountTestData.validCount, 1);
    }



}
