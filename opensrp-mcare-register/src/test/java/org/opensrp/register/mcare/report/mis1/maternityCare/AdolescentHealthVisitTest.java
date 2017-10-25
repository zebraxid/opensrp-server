package org.opensrp.register.mcare.report.mis1.maternityCare;


import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.MIS1Report;
import org.opensrp.register.mcare.report.mis1.MIS1TestData;
import org.opensrp.register.mcare.report.mis1.maternityCare.data.AdolescentHealthVisitTestData;

import java.util.List;

import static org.junit.Assert.assertEquals;

@Ignore
public class AdolescentHealthVisitTest {
    public String unionName = "union";
    private AdolescentHealthVisitTestData adolescentHealthVisitTestData;
    private long startDateTime;
    private long endDateTime;

    @Before
    public void setUp() throws Exception {
        int totalCount = 1000;
        int validCount = MIS1TestData.getRandomNumberBetween(0, totalCount);
        startDateTime = MIS1TestData.getRandomNumberBetween(120000, 140000);
        endDateTime = MIS1TestData.getRandomNumberBetween(170000, 200000);
        adolescentHealthVisitTestData =
                new AdolescentHealthVisitTestData(totalCount, validCount, startDateTime, endDateTime);
    }

    @Test
    public void testCountOfCounsellingOnChangesOfAdolescent() {
        List<Members> membersList = adolescentHealthVisitTestData.createCountOfCounsellingOnChangesOfAdolescentTestData();
        MIS1Report mis1Report = new MIS1Report(unionName, membersList, startDateTime, endDateTime);
        long countOfCounsellingOnChangesOfAdolescent = mis1Report.getMaternityCareReport().getAdolescentHealthReportCalculator().getCountOfCounsellingOnChangesOfAdolescent();
        assertEquals(adolescentHealthVisitTestData.validCount, countOfCounsellingOnChangesOfAdolescent);
    }

    @Test
    public void testCountOfCounsellingBadEffectOnChildMarriageAndTeenPregnency() {
        List<Members> members = adolescentHealthVisitTestData.createCountOfCounsellingOnBadEffectOnChildMarriageAnTeenPregnancy();
        MIS1Report mis1Report = new MIS1Report(unionName, members, startDateTime, endDateTime);
        long countOfCounsellingBadEffectOnChildMarriageAndTeenPregnency = mis1Report.getMaternityCareReport().getAdolescentHealthReportCalculator().getCountOfCounsellingBadEffectOnChildMarriageAndTeenPregnancy();
        assertEquals(adolescentHealthVisitTestData.validCount, countOfCounsellingBadEffectOnChildMarriageAndTeenPregnency);
    }
}
