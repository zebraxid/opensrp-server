package org.opensrp.register.mcare.report.mis1.data;


import org.opensrp.register.mcare.domain.Members;

import java.util.List;

public class PillUsagesTestData extends FamilyPlanningTestData{

    public PillUsagesTestData(List<Members> members, int resultCount) {
        super(members, resultCount);
    }

    public static FamilyPlanningTestData currentMonthTotalBirthControlPill() {
        int totalCount = 100;
        int validCount = 70;
        List<Members> members =
                createTotalUsagesData(Members.BIRTH_CONTROL_PILL, totalCount, validCount);
        return new PillUsagesTestData(members, validCount);
    }

    public static FamilyPlanningTestData currentMonthNewBirthControlPill() {
        int totalCount = 100;
        int validCount = 60;
        List<Members> members = createNewTestData(Members.BIRTH_CONTROL_PILL, totalCount, validCount);
        return new PillUsagesTestData(members, validCount);
    }

    public static FamilyPlanningTestData currentMonthLeftBirthControlPill() {
        int totalData = 100;
        int validCount = 50;
        List<Members> members =
                createLeftBirthControlMethodTestData(Members.BIRTH_CONTROL_PILL, Members.BIRTH_CONTROL_CONDOM,
                        Members.BIRTH_CONTROL_INJECTABLE, totalData, validCount);
        return new PillUsagesTestData(members, validCount);
    }
}
