package org.opensrp.register.mcare.report.mis1.familyPlanning;


import org.opensrp.register.mcare.domain.Members;

import java.util.Map;

public class PillUsagesCalculator extends FamilyPlanningReportCalculator {


    public PillUsagesCalculator(){
        super();
    }

    public void calculate(Members member){
        this.countOfTotalUsages += addToTheCountOfTotalPillUsages(member);
        this.countOfNewUsages += addToTheCountOfNewPillUsages(member);
        this.countOfLeftUsagesButNoneTaken += addToTheCountOfLeftPillUsagesButNoneTaken(member);
        this.countOfLeftUsagesButOtherTaken += addToTheCountOfLeftPillUsagesButOtherTaken(member);
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
            boolean firstElcoFollowUP = previousMonthElcoFollowUpData.isEmpty();
            if (!firstElcoFollowUP) {
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
        boolean notUsingFamilyPlanningInMemberDetail =
                checkMemberFieldValue(member.details(), Members.USING_FAMILY_PLANNING_KEY, Members.NOT_USING_FAMILY_PLANNING_VALUE);
        if (notUsingBirthControlMethodInMemberDetail || notUsingFamilyPlanningInMemberDetail) {
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

}
