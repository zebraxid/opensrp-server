package org.opensrp.register.mcare.report.mis1;


import org.junit.Test;
import org.opensrp.register.mcare.report.mis1.data.FamilyPlanningTestData;
import org.opensrp.register.mcare.report.mis1.data.PillUsagesTestData;
import org.opensrp.register.mcare.report.mis1.familyPlanning.FamilyPlanningReportCalculator;

import static org.junit.Assert.assertEquals;

public class PillUsagesCalculationTest {

    public FamilyPlanningTestData familyPlanningTestData;
    public MIS1Report mis1Report;



    @Test
    public void testTotalCountOfBirthControlPillOfCurrentMonth(){
        this.familyPlanningTestData = PillUsagesTestData.currentMonthTotalBirthControlPill();
        this.mis1Report = new MIS1Report(this.familyPlanningTestData.members);
        int totalBirthControlPillUsagesOfCurrentMonth =
                mis1Report.getFamilyPlanningReport().getPillUsagesCalculator().totalUsages();
        assertEquals(totalBirthControlPillUsagesOfCurrentMonth, this.familyPlanningTestData.resultCount);
    }

    @Test
    public void testNewCountOfBirthControlPillOfCurrentMonth(){
        this.familyPlanningTestData = PillUsagesTestData.currentMonthNewBirthControlPill();
        this.mis1Report = new MIS1Report(this.familyPlanningTestData.members);
        int totalNewBirthControlPillUsagesOfCurrentMonth =
                mis1Report.getFamilyPlanningReport().getPillUsagesCalculator().newUsages();
        assertEquals(totalNewBirthControlPillUsagesOfCurrentMonth, this.familyPlanningTestData.resultCount);
    }

    @Test
    public void testCountOfLeftBirthControlPillUsagesButTakenNoneInCurrentMonth(){
        this.familyPlanningTestData = PillUsagesTestData.currentMonthLeftBirthControlPill();
        this.mis1Report = new MIS1Report(this.familyPlanningTestData.members);
        int totalCountOfMembersWhoLeftUsagesOfBirthControlPillOfCurrentMonth =
                mis1Report.getFamilyPlanningReport().getPillUsagesCalculator().leftUsagesButTakenNone();
        assertEquals(totalCountOfMembersWhoLeftUsagesOfBirthControlPillOfCurrentMonth, this.familyPlanningTestData.resultCount);
    }

    @Test
    public void testCountOfLeftBirthControlPillUsagesButTakenOtherInCurrentMonth(){
        this.familyPlanningTestData = PillUsagesTestData.currentMonthLeftBirthControlPill();
        this.mis1Report = new MIS1Report(this.familyPlanningTestData.members);
        int totalCountOfMembersWhoLeftUsagesOfBirthControlPillOfCurrentMonth =
                mis1Report.getFamilyPlanningReport().getPillUsagesCalculator().leftUsagesButTakenOther();
        assertEquals(totalCountOfMembersWhoLeftUsagesOfBirthControlPillOfCurrentMonth, this.familyPlanningTestData.resultCount);
    }
}
