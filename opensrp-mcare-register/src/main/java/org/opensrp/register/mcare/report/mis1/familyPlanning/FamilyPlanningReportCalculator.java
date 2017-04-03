package org.opensrp.register.mcare.report.mis1.familyPlanning;


import org.opensrp.register.mcare.domain.Members;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class FamilyPlanningReportCalculator {

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


