package org.opensrp.register.mcare.report.mis1;


import org.opensrp.register.mcare.domain.Members;

import java.util.List;
import java.util.Map;

public class MIS1ReportCalculator {
    public List<Members> allUpdatedMemberOfCurrentMember;

    public MIS1ReportCalculator(List<Members> allUpdatedMemberOfCurrentMember) {
        this.allUpdatedMemberOfCurrentMember = allUpdatedMemberOfCurrentMember;
    }

    public int getCountOfTotalBirthControlPillUsagesOfCurrentMonth() {
        int countBirthPill = 0;
        for (Members member : this.allUpdatedMemberOfCurrentMember) {
            boolean usingBirthControlPillInMemberDetail =
                    checkBirthControlMethod(member.details(), Members.BIRTH_CONTROL_KEY, Members.BIRTH_CONTROL_USING_PILL_VALUE);
            if(usingBirthControlPillInMemberDetail) {
                countBirthPill++;
            }
        }
        return countBirthPill;
    }

    public int getCountOfNewBirthControlPillUsagesOfCurrentMonth() {
        int countBirthPill = 0;

        for (Members member : this.allUpdatedMemberOfCurrentMember) {
            boolean usingBirthControlPillInMemberDetail = checkBirthControlMethod(member.details(), Members.BIRTH_CONTROL_KEY, Members.BIRTH_CONTROL_USING_PILL_VALUE);
            if(usingBirthControlPillInMemberDetail) {
                List<Map<String, String>> totalElcoFollowUp = member.elco_Followup();
                if (totalElcoFollowUp.size()>=2){
                    Map<String, String> previousMonthElcoFollowUpData = totalElcoFollowUp.get(totalElcoFollowUp.size()-2);
                    boolean usingBirthControlPillInPreviousElcoFollowUp =
                            checkBirthControlMethod(previousMonthElcoFollowUpData, Members.BIRTH_CONTROL_KEY,
                                    Members.BIRTH_CONTROL_USING_PILL_VALUE);
                    if(!usingBirthControlPillInPreviousElcoFollowUp) {
                        countBirthPill++;
                    }
                }else {
                    countBirthPill++;
                }

            }
        }
        return countBirthPill;
    }

    private boolean checkBirthControlMethod(Map<String, String> memberData, String birthControlMethod, String expectedBirthControlValue){
        String birthControlValue = this.getBirthControlValueFor(memberData, birthControlMethod);
        return birthControlValue.equalsIgnoreCase(expectedBirthControlValue);
    }

    private String getBirthControlValueFor(Map<String, String> memberData, String birthControlMethod) {
        if (memberData.containsKey(birthControlMethod)) {
            return memberData.get(birthControlMethod);
        } else {
            return Members.BIRTH_CONTROL_NULL_VALUE;
        }
    }
}


