package org.opensrp.register.mcare.report.mis1.familyPlanning.birthControlMethodUsagesCalculation;


import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.MIS1Report;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertEquals;


public class BirthControlMethodUsagesCalculationTest {

    public String unionName = "union";
    public BirthControlMethodTestData birthControlMethodTestData;
    public MIS1Report mis1Report;
    public static String ON_TEST_BIRTH_CONTROL_METHOD = Members.EligibleCoupleVisitKeyValue.BIRTH_CONTROL_CONDOM;
    public static String SECOND_BIRTH_CONTROL_METHOD = Members.EligibleCoupleVisitKeyValue.BIRTH_CONTROL_INJECTABLE;
    public static String THIRD_BIRTH_CONTROL_METHOD = Members.EligibleCoupleVisitKeyValue.BIRTH_CONTROL_PILL;

    @Before
    public void setUp() throws Exception {
        int totalCount = 10000;
        int validCount = ThreadLocalRandom.current().nextInt(200, totalCount+1);
       
        birthControlMethodTestData = new BirthControlMethodTestData(ON_TEST_BIRTH_CONTROL_METHOD, totalCount, validCount);
    }

    @Test
    public void testTotalCountOfCondomUsagesOfCurrentMonth() {
        mis1Report = new MIS1Report(unionName, birthControlMethodTestData.createTotalUsagesData(), 0, 0 );
        System.err.println(""+mis1Report.getMembersList().toString());
        int totalCondomUsagesOfCurrentMonth =
                mis1Report.getFamilyPlanningReport().getCondomUsagesCalculator().totalUsages();
        assertEquals(birthControlMethodTestData.validCount, totalCondomUsagesOfCurrentMonth);
    }

    @Test
    public void testNewCountOfCondomUsagesOfCurrentMonth() {
        this.mis1Report = new MIS1Report(unionName, this.birthControlMethodTestData.createNewTestData(), 0, 0);
        int totalNewBirthControlPillUsagesOfCurrentMonth =
                mis1Report.getFamilyPlanningReport().getCondomUsagesCalculator().newUsages();
        assertEquals(this.birthControlMethodTestData.validCount, totalNewBirthControlPillUsagesOfCurrentMonth);
    }

    @Test
    public void testCountOfLeftCondomUsagesButTakenNoneInCurrentMonth() {
        this.mis1Report = new MIS1Report(
               unionName, this.birthControlMethodTestData.createLeftBirthControlMethodButNoneTakenTestData(
                        SECOND_BIRTH_CONTROL_METHOD, THIRD_BIRTH_CONTROL_METHOD), 0, 0);
        int totalCountOfMembersWhoLeftUsagesOfBirthControlCondomOfCurrentMonth =
                mis1Report.getFamilyPlanningReport().getCondomUsagesCalculator().leftUsagesButTakenNone();
        assertEquals(this.birthControlMethodTestData.validCount, totalCountOfMembersWhoLeftUsagesOfBirthControlCondomOfCurrentMonth);
    }

    @Test
    public void testCountOfLeftCondomUsagesButTakenOtherInCurrentMonth() {
        this.mis1Report = new MIS1Report(
                unionName, this.birthControlMethodTestData.createLeftBirthControlMethodButOtherTakenTestData(
                        SECOND_BIRTH_CONTROL_METHOD, THIRD_BIRTH_CONTROL_METHOD), 0, 0);
        int totalCountOfMembersWhoLeftUsagesOfBirthControlPillOfCurrentMonth =
                mis1Report.getFamilyPlanningReport().getCondomUsagesCalculator().leftUsagesButTakenOther();
        assertEquals(this.birthControlMethodTestData.validCount, totalCountOfMembersWhoLeftUsagesOfBirthControlPillOfCurrentMonth);
    }
}
