package org.opensrp.register.mcare.report.mis1;


import org.opensrp.register.mcare.domain.Members;

import java.util.List;

public class MIS1ReportCalculator {
    public List<Members> allUpdatedMemberOfCurrentMember;

    public MIS1ReportCalculator(List<Members> allUpdatedMemberOfCurrentMember) {
        this.allUpdatedMemberOfCurrentMember = allUpdatedMemberOfCurrentMember;
    }

    public int getTotalBirthControlPillOfCurrentMonth() {
        int countBirthPill = 0;

        for (Members member : this.allUpdatedMemberOfCurrentMember) {
            String birthControlValue = this.getBirthControlValue(member);
            boolean usingPillBirthControl = birthControlValue.equalsIgnoreCase(Members.BIRTH_CONTROL_USING_PILL_VALUE);
            if (usingPillBirthControl) {
                countBirthPill++;
            }
        }
        return countBirthPill;
    }

    private String getBirthControlValue(Members member) {
        if (member.details().containsKey(Members.BIRTH_CONTROL_KEY)) {
            return member.details().get(Members.BIRTH_CONTROL_KEY);
        } else {
            return Members.BIRTH_CONTROL_NULL_VALUE;
        }
    }
}


