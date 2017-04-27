package org.opensrp.register.mcare.report.mis1.maternityCare;


import org.junit.Before;
import org.junit.Test;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.MIS1Report;
import org.opensrp.register.mcare.report.mis1.MIS1TestData;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ANCVisitCountTest {
    public String unionName = "union";
    private ANCVisitTestData ancVisitTestData;
    private long startDateTime;
    private long endDateTime;

    @Before
    public void setUp() throws Exception {
        int totalCount = 1000;
        int validCount = MIS1TestData.getRandomNumberBetween(0, totalCount);
        startDateTime = MIS1TestData.getRandomNumberBetween(120000, 140000);
        endDateTime = MIS1TestData.getRandomNumberBetween(170000, 200000);
        ancVisitTestData =
                new ANCVisitTestData(totalCount, validCount, startDateTime, endDateTime);
    }

    @Test
    public void countANC1Visit() {
        List<Members> membersList = ancVisitTestData.createANCVisit1TestData();
        MIS1Report mis1Report = new MIS1Report(unionName, membersList, startDateTime, endDateTime);
        long anc1VisitCount = mis1Report.getMaternityCareReport().getANCReportCalculator().getVisitOneCount();
        assertEquals(anc1VisitCount, ancVisitTestData.validCount);
    }
}
