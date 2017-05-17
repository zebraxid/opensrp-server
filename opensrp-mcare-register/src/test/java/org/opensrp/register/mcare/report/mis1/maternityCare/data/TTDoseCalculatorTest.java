package org.opensrp.register.mcare.report.mis1.maternityCare.data;


import org.junit.Before;
import org.junit.Test;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.MIS1Report;
import org.opensrp.register.mcare.report.mis1.MIS1TestData;
import org.opensrp.register.mcare.report.mis1.maternityCare.TTDoseCalculationTestData;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TTDoseCalculatorTest {
    public String unionName = "union";
    private TTDoseCalculationTestData ttDoseCalculationTestData;
    private long startDateTime;
    private long endDateTime;

    @Before
    public void setUp() throws Exception {
        int totalCount = 1000;
        int validCount = MIS1TestData.getRandomNumberBetween(0, totalCount);
        startDateTime = MIS1TestData.getRandomNumberBetween(120000, 140000);
        endDateTime = MIS1TestData.getRandomNumberBetween(170000, 200000);
        ttDoseCalculationTestData = new TTDoseCalculationTestData(totalCount, validCount, startDateTime, endDateTime);
    }

    @Test
    public void testCountOfTTDoseOne() {
        List<Members> membersList = ttDoseCalculationTestData.createTTOneDoseCountTestData();
        MIS1Report mis1Report = new MIS1Report(unionName, membersList, startDateTime, endDateTime);
        long ttDoseOneCount = mis1Report.getMaternityCareReport().getTTDoseReportCalculator().getDoseOneCount();
        assertEquals(ttDoseCalculationTestData.validCount, ttDoseOneCount);
    }

}
