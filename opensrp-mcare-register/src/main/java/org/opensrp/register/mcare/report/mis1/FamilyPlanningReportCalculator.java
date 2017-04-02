package org.opensrp.register.mcare.report.mis1;


import org.opensrp.register.mcare.domain.Members;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class FamilyPlanningReportCalculator {
    private List<Members> allUpdatedMemberOfCurrentMember;

    private int countOfTotalPillUsages;
    private int countOfNewPillUsages;
    private int countOfLeftPillUsagesButNoneTaken;
    private int countOfLeftPillUsagesButOtherTaken;

    public FamilyPlanningReportCalculator(List<Members> allUpdatedMemberOfCurrentMember) {
        this.allUpdatedMemberOfCurrentMember = allUpdatedMemberOfCurrentMember;
        this.countOfTotalPillUsages = 0;
        this.countOfNewPillUsages = 0;
        for (Members member : this.allUpdatedMemberOfCurrentMember) {
            this.countOfTotalPillUsages += addToTheCountOfTotalPillUsages(member);
            this.countOfNewPillUsages += addToTheCountOfNewPillUsages(member);
            this.countOfLeftPillUsagesButNoneTaken += addToTheCountOfLeftPillUsagesButNoneTaken(member);
            this.countOfLeftPillUsagesButOtherTaken += addToTheCountOfLeftPillUsagesButOtherTaken(member);
        }
    }

    private int addToTheCountOfTotalPillUsages(Members member){
        boolean usingBirthControlPillInMemberDetail =
                checkMemberFieldValue(member.details(), Members.BIRTH_CONTROL_KEY, Members.BIRTH_CONTROL_PILL);
        if (usingBirthControlPillInMemberDetail) {
            return 1;
        }
        return 0;
    }

    private int addToTheCountOfNewPillUsages(Members member){
        boolean usingBirthControlPillInMemberDetail =
            checkMemberFieldValue(member.details(), Members.BIRTH_CONTROL_KEY, Members.BIRTH_CONTROL_PILL);
        if (usingBirthControlPillInMemberDetail) {
            Map<String, String> previousMonthElcoFollowUpData = getPreviousMonthElcoFollowUp(member.elco_Followup());
            if (previousMonthElcoFollowUpData.isEmpty()) {
                boolean usingBirthControlPillInPreviousElcoFollowUp =
                        checkMemberFieldValue(previousMonthElcoFollowUpData, Members.BIRTH_CONTROL_KEY,
                                Members.BIRTH_CONTROL_PILL);
                if (!usingBirthControlPillInPreviousElcoFollowUp) {
                    return 1;
                }
            } else {
                return 1;
            }
        }
        return 0;
    }

    private int addToTheCountOfLeftPillUsagesButNoneTaken(Members member){
        boolean notUsingBirthControlMethodInMemberDetail =
                checkMemberFieldValue(member.details(), Members.BIRTH_CONTROL_KEY, Members.BIRTH_CONTROL_NOT_USING_ANY_METHOD);
        if (notUsingBirthControlMethodInMemberDetail) {
            return 1;
        }

        boolean notUsingFamilyPlanning =
                checkMemberFieldValue(member.details(), Members.USING_FAMILY_PLANNING_KEY, Members.NOT_USING_FAMILY_PLANNING_VALUE);
        if (notUsingFamilyPlanning) {
            Map<String, String> previousMonthElcoFollowUpData = getPreviousMonthElcoFollowUp(member.elco_Followup());
            boolean usingBirthControlPillInPreviousElcoFollowUp =
                    checkMemberFieldValue(previousMonthElcoFollowUpData, Members.BIRTH_CONTROL_KEY,
                            Members.BIRTH_CONTROL_PILL);
            if (usingBirthControlPillInPreviousElcoFollowUp) {
                return 1;
            }
        }

        return 0;
    }

    private int addToTheCountOfLeftPillUsagesButOtherTaken(Members member){
        boolean usingBirthControlPillInMemberDetail =
                checkMemberFieldValue(member.details(), Members.BIRTH_CONTROL_KEY, Members.BIRTH_CONTROL_PILL);
        if(!usingBirthControlPillInMemberDetail) {
            String otherBirthControlValueThanPill = getMemberFieldValueFor(member.details(), Members.BIRTH_CONTROL_KEY);
            if(checkIfValidBirthControlMethod(otherBirthControlValueThanPill)) {
                return 1;
            }
        }
        return 0;
    }

    public int getCountOfTotalPillUsages() {
        return this.countOfTotalPillUsages;
    }

    public int getCountOfNewPillUsages() {
       return this.countOfNewPillUsages;
    }

    public int getCountOfLeftPillUsagesButNoneTaken() {
      return this.countOfLeftPillUsagesButNoneTaken;
    }

    public int getCountOfLeftPillUsagesButOtherTaken() {
        return this.countOfLeftPillUsagesButOtherTaken;
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

    private boolean checkIfValidBirthControlMethod(String birthControlValue) {
        return birthControlValue != Members.BIRTH_CONTROL_NULL_VALUE && birthControlValue != Members.BIRTH_CONTROL_NOT_USING_ANY_METHOD;
    }
}


