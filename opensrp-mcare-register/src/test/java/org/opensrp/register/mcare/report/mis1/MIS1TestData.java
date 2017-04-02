package org.opensrp.register.mcare.report.mis1;


import org.opensrp.register.mcare.domain.Members;

import java.util.*;

public class MIS1TestData {
    public List<Members> members;
    public int resultCount;


    public MIS1TestData(List<org.opensrp.register.mcare.domain.Members> members, int resultCount) {
        this.members = members;
        this.resultCount = resultCount;
    }

    public static MIS1TestData currentMonthTotalBirthControlPill() {
        List<Members> members = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (i < 50) {
                members.add(createMemberUsingBirthControlValue(Members.BIRTH_CONTROL_PILL));
            } else if (i < 75) {
                members.add(createMemberUsingBirthControlValue(Members.BIRTH_CONTROL_CONDOM));
            }else {
                members.add(new Members());
            }
        }

        return new MIS1TestData(members, 50);
    }

    public static MIS1TestData currentMonthNewBirthControlPill() {
        List<Members> members = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (i < 25) {
                Members member = createMemberUsingBirthControlValue(Members.BIRTH_CONTROL_PILL);
                addRandomNumberOfElcoFollowUp(member);
                addElcoFollowUpUsingCondom(member);
                addElcoFollowUpUsingBirthControlPill(member);
                members.add(member);
            } else if(i<50) {
                Members member = createMemberUsingBirthControlValue(Members.BIRTH_CONTROL_PILL);
                addElcoFollowUpUsingBirthControlPill(member);
                members.add(member);
            } else if (i < 75) {
                Members member = createMemberUsingBirthControlValue(Members.BIRTH_CONTROL_CONDOM);
                addRandomNumberOfElcoFollowUp(member);
                addElcoFollowUpUsingBirthControlPill(member);
                addElcoFollowUpUsingCondom(member);
                members.add(member);
            }else {
                members.add(new Members());
            }
        }

        return new MIS1TestData(members, 50);
    }

    public static MIS1TestData currentMonthLeftBirthControlPill() {
        List<Members> members = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (i < 25) {
                Members member = createMemberWithOutAnyFamilyPlanning();
                addRandomNumberOfElcoFollowUp(member);
                addElcoFollowUpUsingBirthControlPill(member);
                addElcoFollowUpUsingCondom(member);
                members.add(member);
            } else if(i<50) {
                Members member = createMemberUsingBirthControlValue(Members.BIRTH_CONTROL_NOT_USING_ANY_METHOD);
                members.add(member);
            } else if (i < 75) {
                Members member = createMemberUsingBirthControlValue(Members.BIRTH_CONTROL_CONDOM);
                addRandomNumberOfElcoFollowUp(member);
                addElcoFollowUpUsingBirthControlPill(member);
                addElcoFollowUpUsingCondom(member);
                members.add(member);
            }else {
                Members member = createMemberUsingBirthControlValue(Members.BIRTH_CONTROL_CONDOM);
                addRandomNumberOfElcoFollowUp(member);
                addElcoFollowUpUsingBirthControlPill(member);
                addElcoFollowUpUsingCondom(member);
                members.add(member);
            }
        }

        return new MIS1TestData(members, 50);
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
                addElcoFollowUpUsingBirthControlPill(member);
            }else {
                addElcoFollowUpUsingCondom(member);
            }
        }
        return member;
    }


    private static Members addElcoFollowUpUsingBirthControlPill(Members member){
        Map<String, String> birthControlPillUsages = createHashMap(Members.BIRTH_CONTROL_KEY, Members.BIRTH_CONTROL_PILL);
        return addElcoFollowUp(member, birthControlPillUsages);
    }

    private static Members addElcoFollowUpUsingCondom(Members member){
        Map<String, String> condomUsages = createHashMap(Members.BIRTH_CONTROL_KEY, Members.BIRTH_CONTROL_CONDOM);
        return addElcoFollowUp(member, condomUsages);
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
