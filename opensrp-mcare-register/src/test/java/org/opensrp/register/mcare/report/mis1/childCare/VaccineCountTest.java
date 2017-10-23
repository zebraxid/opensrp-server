package org.opensrp.register.mcare.report.mis1.childCare;

import org.junit.Before;
import org.junit.Test;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.MIS1Report;
import org.opensrp.register.mcare.report.mis1.MIS1TestData;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class VaccineCountTest {
    public String unionName = "union";
    private VaccineCountTestData vaccineCountTestData;
    private long startDateTime;
    private long endDateTime;

    @Before
    public void setUp() throws Exception {
        int totalCount = 1000;
        int validCount = MIS1TestData.getRandomNumberBetween(0, totalCount);
        startDateTime = MIS1TestData.getRandomNumberBetween(120000, 140000);
        endDateTime = MIS1TestData.getRandomNumberBetween(170000, 200000);
        vaccineCountTestData =
                new VaccineCountTestData(totalCount, validCount, startDateTime, endDateTime);
    }

    @Test
    public void testCountOfBCGCount() {
        List<Members> members = vaccineCountTestData.createBCGVaccineTestDate();
        MIS1Report mis1Report = new MIS1Report(unionName, members, startDateTime, endDateTime);
        long countOfBcg = mis1Report.getChildCareReport().getVaccinationReportCalculator().getBcgCount();
        assertEquals(vaccineCountTestData.validCount, countOfBcg);
    }
}
