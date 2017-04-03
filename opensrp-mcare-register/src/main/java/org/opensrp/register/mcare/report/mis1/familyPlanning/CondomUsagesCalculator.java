package org.opensrp.register.mcare.report.mis1.familyPlanning;


import org.opensrp.register.mcare.domain.Members;

import java.util.Map;

public class CondomUsagesCalculator extends FamilyPlanningReportCalculator {


    public CondomUsagesCalculator(){
        super();
    }

    public void calculate(Members member){
        this.countOfTotalUsages += addToTheCountOfTotalUsages(member);
        this.countOfNewUsages += addToTheCountOfNewUsages(member);
        this.countOfLeftUsagesButNoneTaken += addToTheCountOfLeftUsagesButNoneTaken(member);
        this.countOfLeftUsagesButOtherTaken += addToTheCountOfLeftUsagesButOtherTaken(member);
    }


    private int addToTheCountOfTotalUsages(Members member){
        boolean usingCondomInMemberDetail =
                checkMemberFieldValue(member.details(), Members.BIRTH_CONTROL_KEY, Members.BIRTH_CONTROL_CONDOM);
        if (usingCondomInMemberDetail) {
            return 1;
        }
        return 0;
    }

    private int addToTheCountOfNewUsages(Members member){
        boolean usingCondomInMemberDetail =
                checkMemberFieldValue(member.details(), Members.BIRTH_CONTROL_KEY, Members.BIRTH_CONTROL_CONDOM);
        if (usingCondomInMemberDetail) {
            Map<String, String> previousMonthElcoFollowUpData = getPreviousMonthElcoFollowUp(member.elco_Followup());
            if (previousMonthElcoFollowUpData.isEmpty()) {
                boolean usingCondomInPreviousElcoFollowUp =
                        checkMemberFieldValue(previousMonthElcoFollowUpData, Members.BIRTH_CONTROL_KEY,
                                Members.BIRTH_CONTROL_CONDOM);
                if (!usingCondomInPreviousElcoFollowUp) {
                    return 1;
                }
            } else {
                return 1;
            }
        }
        return 0;
    }

    private int addToTheCountOfLeftUsagesButNoneTaken(Members member){
        boolean notUsingBirthControlMethodInMemberDetail =
                checkMemberFieldValue(member.details(), Members.BIRTH_CONTROL_KEY, Members.BIRTH_CONTROL_NOT_USING_ANY_METHOD);
        boolean notUsingFamilyPlanningInMemberDetail =
                checkMemberFieldValue(member.details(), Members.USING_FAMILY_PLANNING_KEY, Members.NOT_USING_FAMILY_PLANNING_VALUE);
        if (notUsingBirthControlMethodInMemberDetail || notUsingFamilyPlanningInMemberDetail) {
            Map<String, String> previousMonthElcoFollowUpData = getPreviousMonthElcoFollowUp(member.elco_Followup());
            boolean usingCondomInPreviousElcoFollowUp =
                    checkMemberFieldValue(previousMonthElcoFollowUpData, Members.BIRTH_CONTROL_KEY,
                            Members.BIRTH_CONTROL_CONDOM);
            if (usingCondomInPreviousElcoFollowUp) {
                return 1;
            }
        }

        return 0;
    }

    private int addToTheCountOfLeftUsagesButOtherTaken(Members member){
        boolean usingCondomInMemberDetail =
                checkMemberFieldValue(member.details(), Members.BIRTH_CONTROL_KEY, Members.BIRTH_CONTROL_CONDOM);
        if(!usingCondomInMemberDetail) {
            String otherBirthControlValueThanCondom = getMemberFieldValueFor(member.details(), Members.BIRTH_CONTROL_KEY);
            if(checkIfValidBirthControlMethod(otherBirthControlValueThanCondom)) {
                return 1;
            }
        }
        return 0;
    }
}
