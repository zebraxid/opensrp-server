package org.opensrp.register.mcare.report.mis1.familyPlanning.birthControlMethdoUsagesCalculation;

import org.opensrp.connector.DHIS2.dxf2.DHIS2;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.ReportCalculator;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Base class to calculate Birth control methods(e.g: condom, pill etc) related reports.
 * It contains all the calculation (e.g: new users, old users etc).
 * Concrete class should extends this class and pass their birth control method to its constructor.
 * e.g: {@link org.opensrp.register.mcare.report.mis1.familyPlanning.birthControlMethdoUsagesCalculation.CondomMethodUsagesCalculator}
 * TODO: To use more concise and clear naming of members and methods.
 */
public abstract class BirthControlMethodUsagesCalculator extends ReportCalculator {

    @DHIS2(categoryOptionId = "c1")
    private String birthControlMethodToCalculate;

    @DHIS2(categoryOptionId = "c3")
    private int countOfTotalUsages;

    private int countOfNewUsages;

    private int countOfLeftUsagesButNoneTaken;

    private int countOfLeftUsagesButOtherTaken;

    protected BirthControlMethodUsagesCalculator(String birthControlMethodToCalculate, long startDateTime,
                                                 long endDateTime) {
        super(startDateTime, endDateTime);
        this.birthControlMethodToCalculate = birthControlMethodToCalculate;
        this.initCountVariables();
    }

    public void initCountVariables() {
        this.countOfTotalUsages = 0;
        this.countOfNewUsages = 0;
        this.countOfLeftUsagesButOtherTaken = 0;
        this.countOfLeftUsagesButNoneTaken = 0;
    }

    @Override
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
        boolean usingThisBirthControlMethodInMemberDetail = checkValueOfKeyIn(member.details(),
                Members.EligibleCoupleVisitKeyValue.Key.BIRTH_CONTROL, birthControlMethodToCalculate);
        if (usingThisBirthControlMethodInMemberDetail) {
            return 1;
        }
        return 0;
    }

    private int addToTheCountOfNewUsages(Members member) {
        boolean usingThisBirthControlMethodInMemberDetail = checkValueOfKeyIn(member.details(),
                Members.EligibleCoupleVisitKeyValue.Key.BIRTH_CONTROL, birthControlMethodToCalculate);
        if (usingThisBirthControlMethodInMemberDetail) {
            Map<String, String> previousMonthElcoFollowUpData = getPreviousMonthElcoFollowUp(member.elco_Followup());
            boolean firstElcoFollowUP = previousMonthElcoFollowUpData.isEmpty();
            if (!firstElcoFollowUP) {
                boolean usedThisBirthControlMethodInPreviousElcoFollowUp = checkValueOfKeyIn(previousMonthElcoFollowUpData,
                        Members.EligibleCoupleVisitKeyValue.Key.BIRTH_CONTROL, birthControlMethodToCalculate);
                if (!usedThisBirthControlMethodInPreviousElcoFollowUp) {
                    return 1;
                }
            } else {
                return 1;
            }
        }
        return 0;
    }

    private int addToTheCountOfLeftUsagesButNoneTaken(Members member) {
        boolean notUsingAnyBirthControlMethodInMemberDetail = checkValueOfKeyIn(member.details(),
                Members.EligibleCoupleVisitKeyValue.Key.BIRTH_CONTROL,
                Members.EligibleCoupleVisitKeyValue.BIRTH_CONTROL_NOT_USING_ANY_METHOD);
        boolean notUsingFamilyPlanningInMemberDetail = checkValueOfKeyIn(member.details(),
                Members.EligibleCoupleVisitKeyValue.Key.USING_FAMILY_PLANNING,
                Members.EligibleCoupleVisitKeyValue.NOT_USING_FAMILY_PLANNING_VALUE);
        if (notUsingAnyBirthControlMethodInMemberDetail || notUsingFamilyPlanningInMemberDetail) {
            Map<String, String> previousMonthElcoFollowUpData = getPreviousMonthElcoFollowUp(member.elco_Followup());
            boolean usedThisBirthControlMethodInPreviousElcoFollowUp = checkValueOfKeyIn(previousMonthElcoFollowUpData,
                    Members.EligibleCoupleVisitKeyValue.Key.BIRTH_CONTROL, birthControlMethodToCalculate);
            if (usedThisBirthControlMethodInPreviousElcoFollowUp) {
                return 1;
            }
        }

        return 0;
    }

    private int addToTheCountOfLeftUsagesButOtherTaken(Members member) {
        boolean usingThisBirthControlMethodInMemberDetail = checkValueOfKeyIn(member.details(),
                Members.EligibleCoupleVisitKeyValue.Key.BIRTH_CONTROL, birthControlMethodToCalculate);
        if (!usingThisBirthControlMethodInMemberDetail) {
            Map<String, String> previousMonthElcoFollowUpData = getPreviousMonthElcoFollowUp(member.elco_Followup());
            boolean usedThisBirthControlMethodInPreviousElcoFollowUp = checkValueOfKeyIn(previousMonthElcoFollowUpData,
                    Members.EligibleCoupleVisitKeyValue.Key.BIRTH_CONTROL, birthControlMethodToCalculate);
            if (usedThisBirthControlMethodInPreviousElcoFollowUp) {
                String currentBirthControlMethod = getValueBasedOnKeyIn(member.details(),
                        Members.EligibleCoupleVisitKeyValue.Key.BIRTH_CONTROL);
                if (checkIfValidBirthControlMethod(currentBirthControlMethod)) {
                    return 1;
                }
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

    private boolean checkValueOfKeyIn(Map<String, String> memberData, String key, String expectedValue) {
        String birthControlValue = this.getValueBasedOnKeyIn(memberData, key);
        return birthControlValue.equalsIgnoreCase(expectedValue);
    }

    private String getValueBasedOnKeyIn(Map<String, String> memberData, String key) {
        if (memberData.containsKey(key)) {
            return memberData.get(key);
        } else {
            return Members.EligibleCoupleVisitKeyValue.BIRTH_CONTROL_NULL_VALUE;
        }
    }

    private boolean checkIfValidBirthControlMethod(String birthControlMethod) {
        return !birthControlMethod.equalsIgnoreCase(Members.EligibleCoupleVisitKeyValue.BIRTH_CONTROL_NULL_VALUE)
                && !birthControlMethod
                .equalsIgnoreCase(Members.EligibleCoupleVisitKeyValue.BIRTH_CONTROL_NOT_USING_ANY_METHOD);
    }
}


