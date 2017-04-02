package org.opensrp.register.mcare.report.mis1;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MIS1ReportCalculationTest {

    public MIS1TestData mis1TestData;
    public MIS1ReportCalculator mis1ReportCalculator;



    @Test
    public void testTotalCountOfBirthControlPillOfCurrentMonth(){
        this.mis1TestData = MIS1TestData.currentMonthTotalBirthControlPill();
        this.mis1ReportCalculator = new MIS1ReportCalculator(this.mis1TestData.Members);
        int totalBirthControlPillUsagesOfCurrentMonth = mis1ReportCalculator.getTotalBirthControlPillOfCurrentMonth();
        assertEquals(totalBirthControlPillUsagesOfCurrentMonth, this.mis1TestData.resultCount);
    }


}
