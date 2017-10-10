package org.opensrp.register.mcare.report.mis1.nutrition;

import org.junit.Before;
import org.junit.Test;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.MIS1Report;
import org.opensrp.register.mcare.report.mis1.MIS1TestData;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ChildNutritionTest {
    public String unionName = "union";
    private ChildNutritionTestData childNutritionTestData;
    private long startDateTime;
    private long endDateTime;

    @Before
    public void setUp() throws Exception {
        int totalCount = 1000;
        int validCount = MIS1TestData.getRandomNumberBetween(0, totalCount);
        startDateTime = MIS1TestData.getRandomNumberBetween(120000, 140000);
        endDateTime = MIS1TestData.getRandomNumberBetween(170000, 200000);
        childNutritionTestData =
                new ChildNutritionTestData(totalCount, validCount, startDateTime, endDateTime);
    }

    @Test
    public void testCountOfBreastFeedingWithInOneHourOfBirthForChildAgeZeroToSixMonth() {
        List<Members> members =
                childNutritionTestData
                        .createDataForCountOfBreastFeedingWithInOneHourOfBirthForChildAgeZeroToSixMonth();
        MIS1Report mis1Report = new MIS1Report(unionName, members, startDateTime, endDateTime);
        long count = mis1Report.getNutritionReport().getChildNutritionCalculator().forChildAgeZeroToSix().countOfBreastFeedingWithInOneHour();
        assertEquals(childNutritionTestData.validCount, count);

    }
}
