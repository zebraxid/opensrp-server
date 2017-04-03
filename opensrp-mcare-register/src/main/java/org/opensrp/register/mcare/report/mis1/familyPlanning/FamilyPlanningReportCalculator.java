package org.opensrp.register.mcare.report.mis1.familyPlanning;


import org.opensrp.register.mcare.domain.Members;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class FamilyPlanningReportCalculator {
    private List<Members> allUpdatedMemberOfCurrentMember;
    private PillUsagesCalculator pillUsagesCalculator;
    private CondomUsagesCalculator condomUsagesCalculator;


    protected int countOfTotalUsages;
    protected int countOfNewUsages;
    protected int countOfLeftUsagesButNoneTaken;
    protected int countOfLeftUsagesButOtherTaken;


    protected FamilyPlanningReportCalculator(){
        this.countOfTotalUsages = 0;
        this.countOfNewUsages = 0;
        this.countOfLeftUsagesButOtherTaken = 0;
        this.countOfLeftUsagesButNoneTaken =0;
    }

    public FamilyPlanningReportCalculator(List<Members> allUpdatedMemberOfCurrentMember) {
        this.allUpdatedMemberOfCurrentMember = allUpdatedMemberOfCurrentMember;
        this.initCalculators();
        this.calculate();

    }

    public void initCalculators(){
        this.pillUsagesCalculator = new PillUsagesCalculator();
        this.condomUsagesCalculator = new CondomUsagesCalculator();
    }

    public PillUsagesCalculator getPillUsagesCalculator() {
        return pillUsagesCalculator;
    }

    public CondomUsagesCalculator getCondomUsagesCalculator() {
        return condomUsagesCalculator;
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


    public void calculate(){
        for (Members member : this.allUpdatedMemberOfCurrentMember) {
            pillUsagesCalculator.calculate(member);
            condomUsagesCalculator.calculate(member);
        }
    }


    protected Map<String, String> getPreviousMonthElcoFollowUp(List<Map<String, String>> allElcoFollowUp) {
        if (allElcoFollowUp.size() >= 2) {
            return allElcoFollowUp.get(allElcoFollowUp.size() - 2);
        } else {
            return Collections.<String, String>emptyMap();
        }
    }

    protected boolean checkMemberFieldValue(Map<String, String> memberData, String birthControlMethod,
                                          String expectedBirthControlValue) {
        String birthControlValue = this.getMemberFieldValueFor(memberData, birthControlMethod);
        return birthControlValue.equalsIgnoreCase(expectedBirthControlValue);
    }

    protected String getMemberFieldValueFor(Map<String, String> memberData, String birthControlMethod) {
        if (memberData.containsKey(birthControlMethod)) {
            return memberData.get(birthControlMethod);
        } else {
            return Members.BIRTH_CONTROL_NULL_VALUE;
        }
    }

    protected boolean checkIfValidBirthControlMethod(String birthControlValue) {
        return birthControlValue != Members.BIRTH_CONTROL_NULL_VALUE && birthControlValue != Members.BIRTH_CONTROL_NOT_USING_ANY_METHOD;
    }
}


