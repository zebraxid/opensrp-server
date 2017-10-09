package org.opensrp.register.mcare.report.mis1.nutrition;

import org.junit.Before;
import org.junit.Test;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.MIS1Report;
import org.opensrp.register.mcare.report.mis1.MIS1TestData;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class WomanNutritionReportTest {
    public String unionName = "union";
    private PregnantWomanNutritionReportTestData pregnantWomanNutritionReportTestData;
    private MotherNutritionTestData motherNutritionTestData;
    private long startDateTime;
    private long endDateTime;

    @Before
    public void setUp() throws Exception {
        int totalCount = 1000;
        int validCount = MIS1TestData.getRandomNumberBetween(0, totalCount);
        startDateTime = MIS1TestData.getRandomNumberBetween(120000, 140000);
        endDateTime = MIS1TestData.getRandomNumberBetween(170000, 200000);
        pregnantWomanNutritionReportTestData =
                new PregnantWomanNutritionReportTestData(totalCount, validCount, startDateTime, endDateTime);
        motherNutritionTestData =
                new MotherNutritionTestData(totalCount,validCount,startDateTime,endDateTime);
    }

    @Test
    public void testCountOfCounsellingOnFolicAcidAndIronForPregnantWoman() {
        List<Members> members = pregnantWomanNutritionReportTestData.createDataForCountOfCounsellingOnFolicAcidAndIronPregnantWoman();
        MIS1Report mis1Report = new MIS1Report(unionName, members, startDateTime, endDateTime);
        long countOfCounsellingOnFolicAcidAndIron = mis1Report.getNutritionReport().getWomanNutritionCalculator().getCountOfCounsellingOnFolicAcidAndIronForPregWoman();
        assertEquals(pregnantWomanNutritionReportTestData.validCount, countOfCounsellingOnFolicAcidAndIron);

    }

    @Test
    public void testCountOfCounsellingOnFolicAcidAndIronForMother() {
        List<Members> members = motherNutritionTestData.createDataForCountOfCounsellingOnFolicAcidAndIron();
        MIS1Report mis1Report = new MIS1Report(unionName, members, startDateTime, endDateTime);
        long countOfCounsellingOnFolicAcidAndIron = mis1Report.getNutritionReport().getWomanNutritionCalculator().getCountOfCounsellingOnFolicAcidAndIronForMother();
        System.out.println(motherNutritionTestData.totalCount);
        assertEquals(motherNutritionTestData.validCount, countOfCounsellingOnFolicAcidAndIron);

    }

}
