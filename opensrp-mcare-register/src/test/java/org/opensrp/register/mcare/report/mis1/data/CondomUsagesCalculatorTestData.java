package org.opensrp.register.mcare.report.mis1.data;

import org.opensrp.register.mcare.domain.Members;

import java.util.ArrayList;
import java.util.List;


public class CondomUsagesCalculatorTestData extends FamilyPlanningTestData {

    public CondomUsagesCalculatorTestData(List<Members> members, int resultCount) {
        super(members, resultCount);
    }

    public static FamilyPlanningTestData currentMonthTotalCondomUsages() {
        int totalData = 1000;
        int validDataIndex = 500;
        List<Members> members = createTotalUsagesData(Members.BIRTH_CONTROL_CONDOM, Members.BIRTH_CONTROL_INJECTABLE, totalData, validDataIndex);
        return new CondomUsagesCalculatorTestData(members, validDataIndex);
    }
}


