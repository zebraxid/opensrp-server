package org.opensrp.register.mcare.report.mis1;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MIS1ReportCalculationTest {

    public MIS1TestData mis1TestData;
    public MIS1ReportCalculator mis1ReportCalculator;



    @Test
    public void testTotalCountOfBirthControlPillOfCurrentMonth(){
        this.mis1TestData = MIS1TestData.currentMonthTotalBirthControlPill();
        this.mis1ReportCalculator = new MIS1ReportCalculator(this.mis1TestData.members);
        int totalBirthControlPillUsagesOfCurrentMonth = mis1ReportCalculator.getCountOfTotalBirthControlPillUsagesOfCurrentMonth();
        assertEquals(totalBirthControlPillUsagesOfCurrentMonth, this.mis1TestData.resultCount);
    }

    @Test
    public void testNewCountOfBirthControlPillOfCurrentMonth(){
        this.mis1TestData = MIS1TestData.currentMonthNewBirthControlPill();
        this.mis1ReportCalculator = new MIS1ReportCalculator(this.mis1TestData.members);
        int totalNewBirthControlPillUsagesOfCurrentMonth = mis1ReportCalculator.getCountOfNewBirthControlPillUsagesOfCurrentMonth();
        assertEquals(totalNewBirthControlPillUsagesOfCurrentMonth, this.mis1TestData.resultCount);
    }

    @Test
    public void testCountOfLeftBirthControlPillUsagesOfCurrentMonth(){
        this.mis1TestData = MIS1TestData.currentMonthLeftBirthControlPill();
        this.mis1ReportCalculator = new MIS1ReportCalculator(this.mis1TestData.members);
        int totalCountOfMembersWhoLeftUsagesOfBirthControlPillOfCurrentMonth =
                mis1ReportCalculator.getCountOfMembersWhoLeftUsagesOfBirthControlPillInCurrentMonth();
        assertEquals(totalCountOfMembersWhoLeftUsagesOfBirthControlPillOfCurrentMonth, this.mis1TestData.resultCount);
    }
}
