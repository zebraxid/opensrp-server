package org.opensrp.register.mcare.report.mis1;


import org.opensrp.register.mcare.domain.Members;

import java.util.*;

public class FamilyPlanningTestData {
    public List<Members> members;
    public int resultCount;


    public FamilyPlanningTestData(List<org.opensrp.register.mcare.domain.Members> members, int resultCount) {
        this.members = members;
        this.resultCount = resultCount;
    }

    public static FamilyPlanningTestData currentMonthTotalBirthControlPill() {
        List<Members> members = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (i < 70) {
                members.add(createMemberUsingBirthControlValue(Members.BIRTH_CONTROL_PILL));
            } else if (i < 75) {
                members.add(createMemberUsingBirthControlValue(Members.BIRTH_CONTROL_CONDOM));
            }else {
                members.add(new Members());
            }
        }

        return new FamilyPlanningTestData(members, 70);
    }

    public static FamilyPlanningTestData currentMonthNewBirthControlPill() {
        List<Members> members = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (i < 25) {
                Members member = createMemberUsingBirthControlValue(Members.BIRTH_CONTROL_PILL);
                addRandomNumberOfElcoFollowUp(member);
                addElcoFollowUpUsingBirthControlValue(member, Members.BIRTH_CONTROL_CONDOM);
                addElcoFollowUpUsingBirthControlValue(member, Members.BIRTH_CONTROL_PILL);
                members.add(member);
            } else if(i<60) {
                Members member = createMemberUsingBirthControlValue(Members.BIRTH_CONTROL_PILL);
                addElcoFollowUpUsingBirthControlValue(member, Members.BIRTH_CONTROL_PILL);
                members.add(member);
            } else if (i < 75) {
                Members member = createMemberUsingBirthControlValue(Members.BIRTH_CONTROL_CONDOM);
                addRandomNumberOfElcoFollowUp(member);
                addElcoFollowUpUsingBirthControlValue(member, Members.BIRTH_CONTROL_PILL);
                addElcoFollowUpUsingBirthControlValue(member, Members.BIRTH_CONTROL_CONDOM);
                members.add(member);
            }else {
                members.add(new Members());
            }
        }

        return new FamilyPlanningTestData(members, 60);
    }

    public static FamilyPlanningTestData currentMonthLeftBirthControlPill() {
        List<Members> members = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (i < 25) {
                Members member = createMemberWithOutAnyFamilyPlanning();
                addRandomNumberOfElcoFollowUp(member);
                addElcoFollowUpUsingBirthControlValue(member, Members.BIRTH_CONTROL_PILL);
                addElcoFollowUpUsingBirthControlValue(member, Members.BIRTH_CONTROL_NULL_VALUE);
                members.add(member);
            } else if(i<50) {
                Members member = createMemberUsingBirthControlValue(Members.BIRTH_CONTROL_NOT_USING_ANY_METHOD);
                members.add(member);
            } else if (i < 75) {
                Members member = createMemberUsingBirthControlValue(Members.BIRTH_CONTROL_CONDOM);
                addRandomNumberOfElcoFollowUp(member);
                addElcoFollowUpUsingBirthControlValue(member, Members.BIRTH_CONTROL_PILL);
                addElcoFollowUpUsingBirthControlValue(member, Members.BIRTH_CONTROL_CONDOM);
                members.add(member);
            }else {
                Members member = createMemberUsingBirthControlValue(Members.BIRTH_CONTROL_INJECTABLE);
                addRandomNumberOfElcoFollowUp(member);
                addElcoFollowUpUsingBirthControlValue(member, Members.BIRTH_CONTROL_PILL);
                addElcoFollowUpUsingBirthControlValue(member, Members.BIRTH_CONTROL_INJECTABLE);
                members.add(member);
            }
        }

        return new FamilyPlanningTestData(members, 50);
    }

    private static Members createMemberUsingBirthControlValue(String birthControlValue) {
        Members member = new Members();
        Map<String, String> detail = createHashMap(Members.BIRTH_CONTROL_KEY, birthControlValue);
        member.setDetails(detail);
        return member;
    }

    private static Members createMemberWithOutAnyFamilyPlanning() {
        Members member = new Members();
        Map<String, String> detail = createHashMap(Members.USING_FAMILY_PLANNING_KEY, Members.NOT_USING_FAMILY_PLANNING_VALUE);
        member.setDetails(detail);
        return member;
    }

    private static Members addRandomNumberOfElcoFollowUp(Members member){
        Random rand = new Random();
        int randomNum = rand.nextInt((100 - 0) + 1) + 0;
        for(int i=0; i<randomNum; i++) {
            if(i%2 == 0){
                addElcoFollowUpUsingBirthControlValue(member, Members.BIRTH_CONTROL_PILL);
            }else {
                addElcoFollowUpUsingBirthControlValue(member, Members.BIRTH_CONTROL_CONDOM);
            }
        }
        return member;
    }


    private static Members addElcoFollowUpUsingBirthControlValue(Members member, String value){
        Map<String, String> birthControlUsages = createHashMap(Members.BIRTH_CONTROL_KEY, value);
        return addElcoFollowUp(member, birthControlUsages);
    }
    
    private static Members addElcoFollowUp(Members member, Map<String, String> detail) {
        List<Map<String, String>> elcoFollowUp = member.elco_Followup();
        elcoFollowUp.add(detail);
        member.setelco_Followup(elcoFollowUp);
        return member;
    }

    private static Map<String, String > createHashMap(String key, String value) {
        HashMap detail = new HashMap();
        detail.put(key, value);
        return detail;
    }

}
