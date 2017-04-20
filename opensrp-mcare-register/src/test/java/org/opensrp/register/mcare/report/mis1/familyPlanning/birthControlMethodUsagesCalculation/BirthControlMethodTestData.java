package org.opensrp.register.mcare.report.mis1.familyPlanning.birthControlMethodUsagesCalculation;


import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.MIS1TestData;

import java.util.*;

public class BirthControlMethodTestData extends MIS1TestData {
    public String onTestBirthControlMethod;

    public BirthControlMethodTestData(String onTestBirthControlMethod, int totalCount, int validCount) {
        super();
        this.onTestBirthControlMethod = onTestBirthControlMethod;
        this.totalCount = totalCount;
        this.validCount = validCount;
    }

    public List<Members> createNewTestData() {
        List<Members> members = new ArrayList<>();
        for (int i = 0; i < totalCount; i++) {
            if (i < validCount / 2) {
                Members member = createMemberUsingBirthControlValue(onTestBirthControlMethod);
                addRandomNumberOfElcoFollowUp(member);
                addElcoFollowUpUsingBirthControlValue(member, Members.BIRTH_CONTROL_NULL_VALUE);
                addElcoFollowUpUsingBirthControlValue(member, onTestBirthControlMethod);
                members.add(member);
            } else if (i < validCount) {
                Members member = createMemberUsingBirthControlValue(onTestBirthControlMethod);
                addElcoFollowUpUsingBirthControlValue(member, onTestBirthControlMethod);
                members.add(member);
            } else if (i < validCount + validCount / 2) {
                Members member = createMemberUsingBirthControlValue(Members.BIRTH_CONTROL_NULL_VALUE);
                addRandomNumberOfElcoFollowUp(member);
                addElcoFollowUpUsingBirthControlValue(member, onTestBirthControlMethod);
                addElcoFollowUpUsingBirthControlValue(member, Members.BIRTH_CONTROL_NULL_VALUE);
                members.add(member);
            } else {
                members.add(new Members());
            }
        }
        return members;
    }


    public List<Members> createLeftBirthControlMethodButNoneTakenTestData(String secondBirthControlMethod, String thirdBirthControlMethod) {
        List<Members> members = new ArrayList<>();
        for (int i = 0; i < totalCount; i++) {
            if (i < validCount) {
                if (i % 2 == 0) {
                    Members member = createMemberWithOutAnyFamilyPlanning();
                    addRandomNumberOfElcoFollowUp(member);
                    addElcoFollowUpUsingBirthControlValue(member, onTestBirthControlMethod);
                    addElcoFollowUpUsingBirthControlValue(member, Members.BIRTH_CONTROL_NULL_VALUE);
                    members.add(member);
                } else {
                    Members member = createMemberUsingBirthControlValue(Members.BIRTH_CONTROL_NOT_USING_ANY_METHOD);
                    addRandomNumberOfElcoFollowUp(member);
                    addElcoFollowUpUsingBirthControlValue(member, onTestBirthControlMethod);
                    addElcoFollowUpUsingBirthControlValue(member, Members.BIRTH_CONTROL_NOT_USING_ANY_METHOD);
                    members.add(member);
                }
            } else {
                if (i % 2 == 0) {
                    Members member = createMemberWithOutAnyFamilyPlanning();
                    addRandomNumberOfElcoFollowUp(member);
                    addElcoFollowUpUsingBirthControlValue(member, thirdBirthControlMethod);
                    addElcoFollowUpUsingBirthControlValue(member, Members.BIRTH_CONTROL_NULL_VALUE);
                    members.add(member);
                } else {
                    Members member = createMemberUsingBirthControlValue(Members.BIRTH_CONTROL_NOT_USING_ANY_METHOD);
                    addRandomNumberOfElcoFollowUp(member);
                    addElcoFollowUpUsingBirthControlValue(member, secondBirthControlMethod);
                    addElcoFollowUpUsingBirthControlValue(member, Members.BIRTH_CONTROL_NOT_USING_ANY_METHOD);
                    members.add(member);
                }

            }
        }
        Members member = createMemberUsingBirthControlValue(thirdBirthControlMethod);
        addElcoFollowUpUsingBirthControlValue(member, thirdBirthControlMethod);
        members.add(member);
        member = new Members();
        members.add(member);
        return members;
    }

    public List<Members> createLeftBirthControlMethodButOtherTakenTestData(String secondBirthControlMethod, String thirdBirthControlMethod) {
        List<Members> members = new ArrayList<>();
        for (int i = 0; i < totalCount; i++) {
            if (i < validCount) {
                if (i % 2 == 0) {
                    Members member = createMemberUsingBirthControlValue(secondBirthControlMethod);
                    addRandomNumberOfElcoFollowUp(member);
                    addElcoFollowUpUsingBirthControlValue(member, onTestBirthControlMethod);
                    addElcoFollowUpUsingBirthControlValue(member, secondBirthControlMethod);
                    members.add(member);
                } else {
                    Members member = createMemberUsingBirthControlValue(thirdBirthControlMethod);
                    addRandomNumberOfElcoFollowUp(member);
                    addElcoFollowUpUsingBirthControlValue(member, onTestBirthControlMethod);
                    addElcoFollowUpUsingBirthControlValue(member, thirdBirthControlMethod);
                    members.add(member);
                }
            } else {
                if (i % 2 == 0) {
                    Members member = createMemberUsingBirthControlValue(thirdBirthControlMethod);
                    addRandomNumberOfElcoFollowUp(member);
                    addElcoFollowUpUsingBirthControlValue(member, secondBirthControlMethod);
                    addElcoFollowUpUsingBirthControlValue(member, thirdBirthControlMethod);
                    members.add(member);
                } else {
                    Members member = createMemberUsingBirthControlValue(Members.BIRTH_CONTROL_NOT_USING_ANY_METHOD);
                    addRandomNumberOfElcoFollowUp(member);
                    addElcoFollowUpUsingBirthControlValue(member, onTestBirthControlMethod);
                    addElcoFollowUpUsingBirthControlValue(member, Members.BIRTH_CONTROL_NOT_USING_ANY_METHOD);
                    members.add(member);
                }
            }
        }

        Members member = createMemberUsingBirthControlValue(thirdBirthControlMethod);
        addElcoFollowUpUsingBirthControlValue(member, thirdBirthControlMethod);
        members.add(member);
        member = new Members();
        members.add(member);
        return members;
    }

    public List<Members> createTotalUsagesData() {
        List<Members> members = new ArrayList<>();
        for (int i = 0; i < totalCount; i++) {
            if (i < validCount) {
                members.add(createMemberUsingBirthControlValue(onTestBirthControlMethod));
            } else if (i < (validCount + (totalCount - validCount) / 2)) {
                members.add(createMemberUsingBirthControlValue(Members.BIRTH_CONTROL_NULL_VALUE));
            } else {
                members.add(new Members());
            }
        }

        return members;
    }

    protected Members createMemberUsingBirthControlValue(String birthControlValue) {
        Members member = new Members();
        Map<String, String> detail = createHashMap(Members.BIRTH_CONTROL_KEY, birthControlValue);
        member.setDetails(detail);
        return member;
    }

    protected Members createMemberWithOutAnyFamilyPlanning() {
        Members member = new Members();
        Map<String, String> detail = createHashMap(Members.USING_FAMILY_PLANNING_KEY, Members.NOT_USING_FAMILY_PLANNING_VALUE);
        member.setDetails(detail);
        return member;
    }

    protected Members addRandomNumberOfElcoFollowUp(Members member) {
        Random rand = new Random();
        int randomNum = rand.nextInt((100 - 0) + 1) + 0;
        for (int i = 0; i < randomNum; i++) {
            if (i % 2 == 0) {
                addElcoFollowUpUsingBirthControlValue(member, Members.BIRTH_CONTROL_PILL);
            } else {
                addElcoFollowUpUsingBirthControlValue(member, Members.BIRTH_CONTROL_CONDOM);
            }
        }
        return member;
    }


    protected Members addElcoFollowUpUsingBirthControlValue(Members member, String value) {
        Map<String, String> birthControlUsages = createHashMap(Members.BIRTH_CONTROL_KEY, value);
        return addElcoFollowUp(member, birthControlUsages);
    }

    protected Members addElcoFollowUp(Members member, Map<String, String> detail) {
        List<Map<String, String>> elcoFollowUp = member.elco_Followup();
        elcoFollowUp.add(detail);
        member.setelco_Followup(elcoFollowUp);
        return member;
    }

    protected Map<String, String> createHashMap(String key, String value) {
        HashMap detail = new HashMap();
        detail.put(key, value);
        return detail;
    }

}
