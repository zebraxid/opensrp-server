package org.opensrp.register.mcare.report.mis1;


import org.opensrp.register.mcare.domain.Members;

import java.util.Collections;
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
                    checkMemberFieldValue(member.details(), Members.BIRTH_CONTROL_KEY, Members.BIRTH_CONTROL_PILL);
            if (usingBirthControlPillInMemberDetail) {
                countBirthPill++;
            }
        }
        return countBirthPill;
    }

    public int getCountOfNewBirthControlPillUsagesOfCurrentMonth() {
        int countBirthPill = 0;

        for (Members member : this.allUpdatedMemberOfCurrentMember) {
            boolean usingBirthControlPillInMemberDetail =
                    checkMemberFieldValue(member.details(), Members.BIRTH_CONTROL_KEY, Members.BIRTH_CONTROL_PILL);
            if (usingBirthControlPillInMemberDetail) {
                Map<String, String> previousMonthElcoFollowUpData = getPreviousMonthElcoFollowUp(member.elco_Followup());
                if (previousMonthElcoFollowUpData.isEmpty()) {
                    boolean usingBirthControlPillInPreviousElcoFollowUp =
                            checkMemberFieldValue(previousMonthElcoFollowUpData, Members.BIRTH_CONTROL_KEY,
                                    Members.BIRTH_CONTROL_PILL);
                    if (!usingBirthControlPillInPreviousElcoFollowUp) {
                        countBirthPill++;
                    }
                } else {
                    countBirthPill++;
                }
            }
        }
        return countBirthPill;
    }

    public int getCountOfMembersWhoLeftUsagesOfBirthControlPillInCurrentMonth() {
        int countBirthPill = 0;

        for (Members member : this.allUpdatedMemberOfCurrentMember) {
            boolean notUsingBirthControlMethodInMemberDetail =
                    checkMemberFieldValue(member.details(), Members.BIRTH_CONTROL_KEY, Members.BIRTH_CONTROL_NOT_USING_ANY_METHOD);
            if (notUsingBirthControlMethodInMemberDetail) {
                countBirthPill++;
            }

            boolean notUsingFamilyPlanning =
                    checkMemberFieldValue(member.details(), Members.USING_FAMILY_PLANNING_KEY, Members.NOT_USING_FAMILY_PLANNING_VALUE);
            if (notUsingFamilyPlanning) {
                Map<String, String> previousMonthElcoFollowUpData = getPreviousMonthElcoFollowUp(member.elco_Followup());
                boolean usingBirthControlPillInPreviousElcoFollowUp =
                        checkMemberFieldValue(previousMonthElcoFollowUpData, Members.BIRTH_CONTROL_KEY,
                                Members.BIRTH_CONTROL_PILL);
                if (usingBirthControlPillInPreviousElcoFollowUp) {
                    countBirthPill++;
                }
            }
        }

        return countBirthPill;
    }

    private Map<String, String> getPreviousMonthElcoFollowUp(List<Map<String, String>> allElcoFollowUp) {
        if (allElcoFollowUp.size() >= 2) {
            return allElcoFollowUp.get(allElcoFollowUp.size() - 2);
        } else {
            return Collections.<String, String>emptyMap();
        }
    }

    private boolean checkMemberFieldValue(Map<String, String> memberData, String birthControlMethod,
                                          String expectedBirthControlValue) {
        String birthControlValue = this.getMemberFieldValueFor(memberData, birthControlMethod);
        return birthControlValue.equalsIgnoreCase(expectedBirthControlValue);
    }

    private String getMemberFieldValueFor(Map<String, String> memberData, String birthControlMethod) {
        if (memberData.containsKey(birthControlMethod)) {
            return memberData.get(birthControlMethod);
        } else {
            return Members.BIRTH_CONTROL_NULL_VALUE;
        }
    }
}


