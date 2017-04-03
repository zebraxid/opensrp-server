package org.opensrp.register.mcare.report.mis1.familyPlanning;


import org.opensrp.register.mcare.domain.Members;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PillCalculator extends FamilyPlanningReportCalculator implements FamilyPlanningReportCalculatorInterface{

    private int countOfTotalPillUsages;
    private int countOfNewPillUsages;
    private int countOfLeftPillUsagesButNoneTaken;
    private int countOfLeftPillUsagesButOtherTaken;

    public PillCalculator(){
        super();
        this.countOfTotalPillUsages = 0;
        this.countOfNewPillUsages = 0;
        this.countOfLeftPillUsagesButOtherTaken = 0;
        this.countOfLeftPillUsagesButNoneTaken =0;
    }

    public void calculate(Members member){
        this.countOfTotalPillUsages += addToTheCountOfTotalPillUsages(member);
        this.countOfNewPillUsages += addToTheCountOfNewPillUsages(member);
        this.countOfLeftPillUsagesButNoneTaken += addToTheCountOfLeftPillUsagesButNoneTaken(member);
        this.countOfLeftPillUsagesButOtherTaken += addToTheCountOfLeftPillUsagesButOtherTaken(member);
    }

    @Override
    public int totalUsages() {
        return this.countOfTotalPillUsages;
    }

    @Override
    public int newUsages() {
        return countOfNewPillUsages;
    }

    @Override
    public int unitTotal() {
        return 0;
    }

    @Override
    public int leftUsagesButTakenNone() {
        return countOfLeftPillUsagesButNoneTaken;
    }

    @Override
    public int leftUsagesButTakenOther() {
        return countOfLeftPillUsagesButOtherTaken;
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

}
