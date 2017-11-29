package org.opensrp.register.mcare.report.mis1.maternityCare;


import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.MIS1Report;
import org.opensrp.register.mcare.report.mis1.MIS1TestData;
import org.opensrp.register.mcare.report.mis1.maternityCare.data.ANCVisitTestData;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

@Ignore
public class ANCVisitCountTest {
 /*   public String unionName = "union";
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
    public void testANC1VisitCount() {
        List<Members> membersList = ancVisitTestData.createANCVisit1TestData();
        MIS1Report mis1Report = new MIS1Report(unionName, membersList, startDateTime, endDateTime);
        long anc1VisitCount = mis1Report.getMaternityCareReport().getAncReportCalculator().getVisitOneCount();
        assertEquals(ancVisitTestData.validCount, anc1VisitCount);
    }


    @Test
    public void testANC2VisitCount() {
        List<Members> membersList = ancVisitTestData.createANCVisit2TestData();
        MIS1Report mis1Report = new MIS1Report(unionName, membersList, startDateTime, endDateTime);
        long anc2visitCount = mis1Report.getMaternityCareReport().getAncReportCalculator().getVisitTwoCount();
        assertEquals(ancVisitTestData.validCount, anc2visitCount);
    }

    @Test
    public void testANC3VisitCount() {
        List<Members> membersList = ancVisitTestData.createANCVisit3TestData();
        MIS1Report mis1Report = new MIS1Report(unionName, membersList, startDateTime, endDateTime);
        long anc3visitCount = mis1Report.getMaternityCareReport().getAncReportCalculator().getVisitThreeCount();
        assertEquals(ancVisitTestData.validCount, anc3visitCount);
    }

    @Test
    public void testANC4VisitCount() {
        List<Members> membersList = ancVisitTestData.createANCVisit4TestData();
        MIS1Report mis1Report = new MIS1Report(unionName, membersList, startDateTime, endDateTime);
        long anc4visitCount = mis1Report.getMaternityCareReport().getAncReportCalculator().getVisitFourCount();
        assertEquals(ancVisitTestData.validCount, anc4visitCount);
    }

    @Test
    public void testANCVisitIsReferredCount() {
        List<Members> membersList = ancVisitTestData.createANCVisitIsReferredData();
        MIS1Report mis1Report = new MIS1Report(unionName, membersList, startDateTime, endDateTime);
        long isReferredANCVisit = mis1Report.getMaternityCareReport().getAncReportCalculator().getIsReferredCount();
        assertEquals(ancVisitTestData.validCount, isReferredANCVisit);
    }

    @Test
    public void testANCVisitIsReferredCountForEmptyMemberList() {
        List<Members> membersList = Collections.emptyList();
        MIS1Report mis1Report = new MIS1Report(unionName, membersList, startDateTime, endDateTime);
        long isReferredANCVisit = mis1Report.getMaternityCareReport().getAncReportCalculator().getIsReferredCount();
        assertEquals(0 , isReferredANCVisit);
    }

    @Test
    public void testANCVisitMisoprostolReceivedCount() {
        List<Members> membersList = ancVisitTestData.createANCVisitMisoprostolReceivedData();
        MIS1Report mis1Report = new MIS1Report(unionName, membersList, startDateTime, endDateTime);
        long misoprostolRecivedCount = mis1Report.getMaternityCareReport().getAncReportCalculator().getMisoprostolRecivedCount();
        assertEquals(ancVisitTestData.validCount, misoprostolRecivedCount);
    }*/
}