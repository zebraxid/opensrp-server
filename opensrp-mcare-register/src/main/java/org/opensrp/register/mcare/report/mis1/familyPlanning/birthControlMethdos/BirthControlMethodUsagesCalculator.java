package org.opensrp.register.mcare.report.mis1.familyPlanning.birthControlMethdos;


import org.opensrp.register.mcare.domain.Members;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class BirthControlMethodUsagesCalculator {

    private String birthControlMethodToCalculate;
    private int countOfTotalUsages;
    private int countOfNewUsages;
    private int countOfLeftUsagesButNoneTaken;
    private int countOfLeftUsagesButOtherTaken;


    private BirthControlMethodUsagesCalculator() {

    }

    protected BirthControlMethodUsagesCalculator(String birthControlMethodToCalculate) {
        this.birthControlMethodToCalculate = birthControlMethodToCalculate;
        this.initCountVariables();
    }

    public void initCountVariables() {
        this.countOfTotalUsages = 0;
        this.countOfNewUsages = 0;
        this.countOfLeftUsagesButOtherTaken = 0;
        this.countOfLeftUsagesButNoneTaken = 0;
    }

    public void calculate(Members member) {
        this.countOfTotalUsages += addToTheCountOfTotalUsages(member);
        this.countOfNewUsages += addToTheCountOfNewUsages(member);
        this.countOfLeftUsagesButNoneTaken += addToTheCountOfLeftUsagesButNoneTaken(member);
        this.countOfLeftUsagesButOtherTaken += addToTheCountOfLeftUsagesButOtherTaken(member);
    }


    public int totalUsages() {
        return this.countOfTotalUsages;
    }


    public int newUsages() {
        return countOfNewUsages;
    }


    public int unitTotal() {
        return 0;
    }


    public int leftUsagesButTakenNone() {
        return countOfLeftUsagesButNoneTaken;
    }


    public int leftUsagesButTakenOther() {
        return countOfLeftUsagesButOtherTaken;
    }


    private int addToTheCountOfTotalUsages(Members member) {
        boolean usingBirthControlPillInMemberDetail =
                checkMemberFieldValue(member.details(), Members.BIRTH_CONTROL_KEY, birthControlMethodToCalculate);
        if (usingBirthControlPillInMemberDetail) {
            return 1;
        }
        return 0;
    }

    private int addToTheCountOfNewUsages(Members member) {
        boolean usingBirthControlPillInMemberDetail =
                checkMemberFieldValue(member.details(), Members.BIRTH_CONTROL_KEY, birthControlMethodToCalculate);
        if (usingBirthControlPillInMemberDetail) {
            Map<String, String> previousMonthElcoFollowUpData = getPreviousMonthElcoFollowUp(member.elco_Followup());
            boolean firstElcoFollowUP = previousMonthElcoFollowUpData.isEmpty();
            if (!firstElcoFollowUP) {
                boolean usingBirthControlPillInPreviousElcoFollowUp =
                        checkMemberFieldValue(previousMonthElcoFollowUpData, Members.BIRTH_CONTROL_KEY,
                                birthControlMethodToCalculate);
                if (!usingBirthControlPillInPreviousElcoFollowUp) {
                    return 1;
                }
            } else {
                return 1;
            }
        }
        return 0;
    }

    private int addToTheCountOfLeftUsagesButNoneTaken(Members member) {
        boolean notUsingBirthControlMethodInMemberDetail =
                checkMemberFieldValue(member.details(), Members.BIRTH_CONTROL_KEY, Members.BIRTH_CONTROL_NOT_USING_ANY_METHOD);
        boolean notUsingFamilyPlanningInMemberDetail =
                checkMemberFieldValue(member.details(), Members.USING_FAMILY_PLANNING_KEY, Members.NOT_USING_FAMILY_PLANNING_VALUE);
        if (notUsingBirthControlMethodInMemberDetail || notUsingFamilyPlanningInMemberDetail) {
            Map<String, String> previousMonthElcoFollowUpData = getPreviousMonthElcoFollowUp(member.elco_Followup());
            boolean usingBirthControlPillInPreviousElcoFollowUp =
                    checkMemberFieldValue(previousMonthElcoFollowUpData, Members.BIRTH_CONTROL_KEY,
                            birthControlMethodToCalculate);
            if (usingBirthControlPillInPreviousElcoFollowUp) {
                return 1;
            }
        }

        return 0;
    }

    private int addToTheCountOfLeftUsagesButOtherTaken(Members member) {
        boolean usingBirthControlPillInMemberDetail =
                checkMemberFieldValue(member.details(), Members.BIRTH_CONTROL_KEY, birthControlMethodToCalculate);
        if (!usingBirthControlPillInMemberDetail) {
            String otherBirthControlValueThanPill = getMemberFieldValueFor(member.details(), Members.BIRTH_CONTROL_KEY);
            if (checkIfValidBirthControlMethod(otherBirthControlValueThanPill)) {
                return 1;
            }
        }
        return 0;
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


