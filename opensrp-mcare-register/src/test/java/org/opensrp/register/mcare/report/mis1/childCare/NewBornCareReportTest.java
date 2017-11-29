package org.opensrp.register.mcare.report.mis1.childCare;

import org.junit.Before;
import org.junit.Test;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.MIS1Report;
import org.opensrp.register.mcare.report.mis1.MIS1TestData;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class NewBornCareReportTest {
   /* public String unionName = "union";
    private NewBornCareTestData newBornCareReportTest;
    private long startDateTime;
    private long endDateTime;

    @Before
    public void setUp() throws Exception {
        int totalCount = 1000;
        int validCount = MIS1TestData.getRandomNumberBetween(0, totalCount);
        startDateTime = MIS1TestData.getRandomNumberBetween(120000, 140000);
        endDateTime = MIS1TestData.getRandomNumberBetween(170000, 200000);
        newBornCareReportTest =
                new NewBornCareTestData(totalCount, validCount, startDateTime, endDateTime);
    }

    @Test
    public void testCountOfIsCleanedOnPNCVisit() {
        List<Members> members = newBornCareReportTest.createIsCleanOnPNCVisitTestData();
        MIS1Report mis1Report = new MIS1Report(unionName, members, startDateTime, endDateTime);
        long newBornCleanedCount = mis1Report.getChildCareReport().getNewBornCareReportCalculator().getIsCleanedCount();
        assertEquals(newBornCareReportTest.validCount, newBornCleanedCount);
    }

    @Test
    public void testCountOfUsedChlorhexidinOnPNCVisit() {
        List<Members> members = newBornCareReportTest.createUseOfChlorhexidinOnPNCVisitTestData();
        MIS1Report mis1Report = new MIS1Report(unionName, members, startDateTime, endDateTime);
        long usedChlorhexidinCount = mis1Report.getChildCareReport().getNewBornCareReportCalculator().getUsedChlorhexidinCount();
        assertEquals(newBornCareReportTest.validCount, usedChlorhexidinCount);
    }*/
}
