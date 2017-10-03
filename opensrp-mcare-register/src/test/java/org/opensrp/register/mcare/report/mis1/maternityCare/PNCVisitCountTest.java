package org.opensrp.register.mcare.report.mis1.maternityCare;


import org.junit.Before;
import org.junit.Test;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.MIS1Report;
import org.opensrp.register.mcare.report.mis1.MIS1TestData;
import org.opensrp.register.mcare.report.mis1.maternityCare.data.PNCVisitTestData;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class PNCVisitCountTest {
    public String unionName = "union";
    private PNCVisitTestData pncVisitTestData;
    private long startDateTime;
    private long endDateTime;

    @Before
    public void setUp() throws Exception {
        int totalCount = 1000;
        int validCount = MIS1TestData.getRandomNumberBetween(0, totalCount);
        startDateTime = MIS1TestData.getRandomNumberBetween(120000, 140000);
        endDateTime = MIS1TestData.getRandomNumberBetween(170000, 200000);
        pncVisitTestData =
                new PNCVisitTestData(totalCount, validCount, startDateTime, endDateTime);
    }

    @Test
    public void testPNCVisit1InformationCount() {
        List<Members> allMembers = pncVisitTestData.createPncVisit1InformationTestData();
        MIS1Report mis1Report = new MIS1Report(unionName, allMembers, startDateTime, endDateTime);
        long pncVisit1InformationCount = mis1Report.getMaternityCareReport().getPncReportCalculator().getPncOneVisitCalculator().getInformationCount();
        assertEquals(pncVisitTestData.validCount, pncVisit1InformationCount);
    }

    @Test
    public void testPNCVisit1ServiceCount() {
        List<Members> allMembers = pncVisitTestData.createPncVisit1ServiceTestData();
        MIS1Report mis1Report = new MIS1Report(unionName, allMembers, startDateTime, endDateTime);
        long pncVisit1InformationCount = mis1Report.getMaternityCareReport().getPncReportCalculator().getPncOneVisitCalculator().getServiceCount();
        assertEquals(pncVisitTestData.validCount, pncVisit1InformationCount);
    }

    @Test
    public void testPNCVisit2InformationCount() {
        List<Members> allMembers = pncVisitTestData.createPncVisit2InformationTestData();
        MIS1Report mis1Report = new MIS1Report(unionName, allMembers, startDateTime, endDateTime);
        long pncVisit2InformationCount = mis1Report.getMaternityCareReport().getPncReportCalculator().getPncTwoVisitCalculator().getInformationCount();
        assertEquals(pncVisitTestData.validCount, pncVisit2InformationCount);
    }

    @Test
    public void testPNCVisit2ServiceCount() {
        List<Members> allMembers = pncVisitTestData.createPncVisit2ServiceTestData();
        MIS1Report mis1Report = new MIS1Report(unionName, allMembers, startDateTime, endDateTime);
        long pncVisit2ServiceCount = mis1Report.getMaternityCareReport().getPncReportCalculator().getPncTwoVisitCalculator().getServiceCount();
        assertEquals(pncVisitTestData.validCount, pncVisit2ServiceCount);
    }

}
