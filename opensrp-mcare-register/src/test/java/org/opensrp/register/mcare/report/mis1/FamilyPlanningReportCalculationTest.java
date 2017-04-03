package org.opensrp.register.mcare.report.mis1;


import org.junit.Test;
import org.opensrp.register.mcare.report.mis1.familyPlanning.FamilyPlanningReportCalculator;

import static org.junit.Assert.assertEquals;

public class FamilyPlanningReportCalculationTest {

    public FamilyPlanningTestData familyPlanningTestData;
    public FamilyPlanningReportCalculator familyPlanningReportCalculator;



    @Test
    public void testTotalCountOfBirthControlPillOfCurrentMonth(){
        this.familyPlanningTestData = FamilyPlanningTestData.currentMonthTotalBirthControlPill();
        this.familyPlanningReportCalculator = new FamilyPlanningReportCalculator(this.familyPlanningTestData.members);
        int totalBirthControlPillUsagesOfCurrentMonth =
                familyPlanningReportCalculator.getPillCalculator().totalUsages();
        assertEquals(totalBirthControlPillUsagesOfCurrentMonth, this.familyPlanningTestData.resultCount);
    }

    @Test
    public void testNewCountOfBirthControlPillOfCurrentMonth(){
        this.familyPlanningTestData = FamilyPlanningTestData.currentMonthNewBirthControlPill();
        this.familyPlanningReportCalculator = new FamilyPlanningReportCalculator(this.familyPlanningTestData.members);
        int totalNewBirthControlPillUsagesOfCurrentMonth =
                familyPlanningReportCalculator.getPillCalculator().newUsages();
        assertEquals(totalNewBirthControlPillUsagesOfCurrentMonth, this.familyPlanningTestData.resultCount);
    }

    @Test
    public void testCountOfLeftBirthControlPillUsagesButTakenNoneInCurrentMonth(){
        this.familyPlanningTestData = FamilyPlanningTestData.currentMonthLeftBirthControlPill();
        this.familyPlanningReportCalculator = new FamilyPlanningReportCalculator(this.familyPlanningTestData.members);
        int totalCountOfMembersWhoLeftUsagesOfBirthControlPillOfCurrentMonth =
                familyPlanningReportCalculator.getPillCalculator().leftUsagesButTakenNone();
        assertEquals(totalCountOfMembersWhoLeftUsagesOfBirthControlPillOfCurrentMonth, this.familyPlanningTestData.resultCount);
    }

    @Test
    public void testCountOfLeftBirthControlPillUsagesButTakenOtherInCurrentMonth(){
        this.familyPlanningTestData = FamilyPlanningTestData.currentMonthLeftBirthControlPill();
        this.familyPlanningReportCalculator = new FamilyPlanningReportCalculator(this.familyPlanningTestData.members);
        int totalCountOfMembersWhoLeftUsagesOfBirthControlPillOfCurrentMonth =
                familyPlanningReportCalculator.getPillCalculator().leftUsagesButTakenOther();
        assertEquals(totalCountOfMembersWhoLeftUsagesOfBirthControlPillOfCurrentMonth, this.familyPlanningTestData.resultCount);
    }
}
