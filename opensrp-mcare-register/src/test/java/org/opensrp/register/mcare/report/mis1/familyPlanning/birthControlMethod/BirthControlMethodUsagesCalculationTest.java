package org.opensrp.register.mcare.report.mis1.familyPlanning.birthControlMethod;


import org.junit.Test;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.MIS1Report;

import static org.junit.Assert.assertEquals;

public class BirthControlMethodUsagesCalculationTest {

    public BirthControlMethodTestData birthControlMethodTestData;
    public MIS1Report mis1Report;
    public static String ON_TEST_BIRTH_CONTROL_METHOD = Members.BIRTH_CONTROL_CONDOM;
    public static String SECOND_BIRTH_CONTROL_METHOD = Members.BIRTH_CONTROL_INJECTABLE;
    public static String THIRD_BIRTH_CONTROL_METHOD = Members.BIRTH_CONTROL_PILL;


    @Test
    public void testTotalCountOfCondomUsagesOfCurrentMonth(){
        int totalCount = 10000;
        int validCount = 200;
        birthControlMethodTestData = new BirthControlMethodTestData(ON_TEST_BIRTH_CONTROL_METHOD, totalCount, validCount);
        mis1Report = new MIS1Report(birthControlMethodTestData.createTotalUsagesData());
        int totalCondomUsagesOfCurrentMonth =
                mis1Report.getFamilyPlanningReport().getCondomUsagesCalculator().totalUsages();
        assertEquals(totalCondomUsagesOfCurrentMonth, birthControlMethodTestData.validCount);
    }

    @Test
    public void testNewCountOfCondomUsagesOfCurrentMonth(){
        int totalCount = 10000;
        int validCount = 500;
        birthControlMethodTestData = new BirthControlMethodTestData(ON_TEST_BIRTH_CONTROL_METHOD, totalCount, validCount);
        this.mis1Report = new MIS1Report(this.birthControlMethodTestData.createNewTestData());
        int totalNewBirthControlPillUsagesOfCurrentMonth =
                mis1Report.getFamilyPlanningReport().getCondomUsagesCalculator().newUsages();
        assertEquals(totalNewBirthControlPillUsagesOfCurrentMonth, this.birthControlMethodTestData.validCount);
    }

    @Test
    public void testCountOfLeftCondomUsagesButTakenNoneInCurrentMonth(){
        int totalCount = 10000;
        int validCount = 400;
        birthControlMethodTestData = new BirthControlMethodTestData(ON_TEST_BIRTH_CONTROL_METHOD, totalCount, validCount);
        this.mis1Report = new MIS1Report(this.birthControlMethodTestData.createLeftBirthControlMethodTestData(SECOND_BIRTH_CONTROL_METHOD, THIRD_BIRTH_CONTROL_METHOD));
        int totalCountOfMembersWhoLeftUsagesOfBirthControlPillOfCurrentMonth =
                mis1Report.getFamilyPlanningReport().getCondomUsagesCalculator().leftUsagesButTakenNone();
        assertEquals(totalCountOfMembersWhoLeftUsagesOfBirthControlPillOfCurrentMonth, this.birthControlMethodTestData.validCount);
    }

    @Test
    public void testCountOfLeftCondomUsagesButTakenOtherInCurrentMonth(){
        int totalCount = 10000;
        int validCount = 600;
        birthControlMethodTestData = new BirthControlMethodTestData(ON_TEST_BIRTH_CONTROL_METHOD, totalCount, validCount);
        this.mis1Report = new MIS1Report(this.birthControlMethodTestData.createLeftBirthControlMethodTestData(SECOND_BIRTH_CONTROL_METHOD, THIRD_BIRTH_CONTROL_METHOD));
        int totalCountOfMembersWhoLeftUsagesOfBirthControlPillOfCurrentMonth =
                mis1Report.getFamilyPlanningReport().getCondomUsagesCalculator().leftUsagesButTakenOther();
        assertEquals(totalCountOfMembersWhoLeftUsagesOfBirthControlPillOfCurrentMonth, this.birthControlMethodTestData.validCount);
    }
}
