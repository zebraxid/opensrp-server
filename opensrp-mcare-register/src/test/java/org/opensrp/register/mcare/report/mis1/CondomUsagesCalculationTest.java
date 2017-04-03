package org.opensrp.register.mcare.report.mis1;


import org.junit.Test;
import org.opensrp.register.mcare.report.mis1.data.CondomUsagesCalculatorTestData;
import org.opensrp.register.mcare.report.mis1.data.FamilyPlanningTestData;
import org.opensrp.register.mcare.report.mis1.familyPlanning.FamilyPlanningReportCalculator;

import static org.junit.Assert.assertEquals;

public class CondomUsagesCalculationTest {

    public FamilyPlanningTestData familyPlanningTestData;
    public MIS1Report mis1Report;


    @Test
    public void testTotalCountOfCondomUsagesOfCurrentMonth(){
        familyPlanningTestData = CondomUsagesCalculatorTestData.currentMonthTotalCondomUsages();
        mis1Report = new MIS1Report(familyPlanningTestData.members);
        int totalCondomUsagesOfCurrentMonth =
                mis1Report.getFamilyPlanningReport().getCondomUsagesCalculator().totalUsages();
        System.out.println(totalCondomUsagesOfCurrentMonth);
        System.out.println(familyPlanningTestData.resultCount);
        assertEquals(totalCondomUsagesOfCurrentMonth, familyPlanningTestData.resultCount);
    }

    @Test
    public void testNewCountOfCondomUsagesOfCurrentMonth(){
        this.familyPlanningTestData = CondomUsagesCalculatorTestData.currentMonthNewCondomUsages();
        this.mis1Report = new MIS1Report(this.familyPlanningTestData.members);
        int totalNewBirthControlPillUsagesOfCurrentMonth =
                mis1Report.getFamilyPlanningReport().getCondomUsagesCalculator().newUsages();
        assertEquals(totalNewBirthControlPillUsagesOfCurrentMonth, this.familyPlanningTestData.resultCount);
    }

    @Test
    public void testCountOfLeftCondomUsagesButTakenNoneInCurrentMonth(){
        this.familyPlanningTestData = CondomUsagesCalculatorTestData.currentMonthLeftCondomUsages();
        this.mis1Report = new MIS1Report(this.familyPlanningTestData.members);
        int totalCountOfMembersWhoLeftUsagesOfBirthControlPillOfCurrentMonth =
                mis1Report.getFamilyPlanningReport().getCondomUsagesCalculator().leftUsagesButTakenNone();
        assertEquals(totalCountOfMembersWhoLeftUsagesOfBirthControlPillOfCurrentMonth, this.familyPlanningTestData.resultCount);
    }

    @Test
    public void testCountOfLeftCondomUsagesButTakenOtherInCurrentMonth(){
        this.familyPlanningTestData = CondomUsagesCalculatorTestData.currentMonthLeftCondomUsages();
        this.mis1Report = new MIS1Report(this.familyPlanningTestData.members);
        int totalCountOfMembersWhoLeftUsagesOfBirthControlPillOfCurrentMonth =
                mis1Report.getFamilyPlanningReport().getCondomUsagesCalculator().leftUsagesButTakenOther();
        assertEquals(totalCountOfMembersWhoLeftUsagesOfBirthControlPillOfCurrentMonth, this.familyPlanningTestData.resultCount);
    }
}
