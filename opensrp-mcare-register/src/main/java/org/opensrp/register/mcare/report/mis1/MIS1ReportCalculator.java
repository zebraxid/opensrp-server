package org.opensrp.register.mcare.report.mis1;


import org.opensrp.register.mcare.domain.Members;

import java.util.List;

public class MIS1ReportCalculator {
    public List<Members> allUpdatedMemberOfCurrentMember;
    public static String BIRTH_CONTROL_KEY = "Birth_Control";
    public static String BIRTH_CONTROL_USING_PILL_VALUE = "1";
    public static String BIRTH_CONTROL_NULL_VALUE = "-1";

    public MIS1ReportCalculator(List<Members> allUpdatedMemberOfCurrentMember) {
        this.allUpdatedMemberOfCurrentMember = allUpdatedMemberOfCurrentMember;
    }

    public int getTotalBirthControlPillOfCurrentMonth() {
        int countBirthPill = 0;

        for (Members member : this.allUpdatedMemberOfCurrentMember) {
            String birthCotrolValue = this.getBirthControlValue(member);
            boolean usingPillBirthCotrol = birthCotrolValue.equalsIgnoreCase(BIRTH_CONTROL_USING_PILL_VALUE);
            if (usingPillBirthCotrol) {
                countBirthPill++;
            }
        }
        return countBirthPill;
    }

    private String getBirthControlValue(Members member) {
        if (member.details().containsKey(BIRTH_CONTROL_KEY)) {
            return member.details().get(BIRTH_CONTROL_KEY);
        } else {
            return BIRTH_CONTROL_NULL_VALUE;
        }
    }
}


