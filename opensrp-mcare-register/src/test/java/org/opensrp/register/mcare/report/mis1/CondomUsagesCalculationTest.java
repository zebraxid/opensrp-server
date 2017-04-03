package org.opensrp.register.mcare.report.mis1;


import org.junit.Test;
import org.opensrp.register.mcare.report.mis1.data.CondomUsagesCalculatorTestData;
import org.opensrp.register.mcare.report.mis1.data.FamilyPlanningTestData;
import org.opensrp.register.mcare.report.mis1.familyPlanning.FamilyPlanningReportCalculator;

import static org.junit.Assert.assertEquals;

public class CondomUsagesCalculationTest {

    public FamilyPlanningTestData familyPlanningTestData;
    public FamilyPlanningReportCalculator familyPlanningReportCalculator;


    @Test
    public void testTotalCountOfCondomUsagesOfCurrentMonth(){
        familyPlanningTestData = CondomUsagesCalculatorTestData.currentMonthTotalCondomUsages();
        familyPlanningReportCalculator = new FamilyPlanningReportCalculator(familyPlanningTestData.members);
        int totalCondomUsagesOfCurrentMonth =
                familyPlanningReportCalculator.getCondomUsagesCalculator().totalUsages();
        System.out.println(totalCondomUsagesOfCurrentMonth);
        System.out.println(familyPlanningTestData.resultCount);
        assertEquals(totalCondomUsagesOfCurrentMonth, familyPlanningTestData.resultCount);
    }
}
