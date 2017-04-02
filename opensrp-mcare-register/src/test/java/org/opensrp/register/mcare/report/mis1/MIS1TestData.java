package org.opensrp.register.mcare.report.mis1;


import org.opensrp.register.mcare.domain.Members;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MIS1TestData {
    public List<Members> members;
    public int resultCount;


    public MIS1TestData(List<org.opensrp.register.mcare.domain.Members> members, int resultCount) {
        this.members = members;
        this.resultCount = resultCount;
    }

    public static MIS1TestData currentMonthTotalBirthControlPill() {
        List<Members> members = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (i < 50) {
                members.add(createMemberWhoUsesBirthControlPill());
            } else if (i < 75) {
                members.add(createMembersUsingCondom());
            }
        }

        return new MIS1TestData(members, 50);
    }

    public static MIS1TestData currentMonthTotalNewBirthControlPill() {
        List<Members> members = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (i < 50) {
                members.add(createMemberWhoUsesBirthControlPill());
            } else if (i < 75) {
                members.add(createMembersUsingCondom());
            }
        }

        return new MIS1TestData(members, 50);
    }

    private static Members createMemberWhoUsesBirthControlPill() {
        Members member = new Members();
        HashMap detail = new HashMap();
        detail.put(Members.BIRTH_CONTROL_KEY, Members.BIRTH_CONTROL_USING_PILL_VALUE);
        member.setDetails(detail);
        return member;
    }

    private static Members createMembersUsingCondom() {
        Members member = new Members();
        HashMap detail = new HashMap();
        detail.put(Members.BIRTH_CONTROL_KEY, Members.BIRTH_CONTROL_USING_CONDOM_VALUE);
        member.setDetails(detail);
        return member;
    }
}
