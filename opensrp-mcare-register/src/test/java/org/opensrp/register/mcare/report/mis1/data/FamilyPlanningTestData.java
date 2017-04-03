package org.opensrp.register.mcare.report.mis1.data;


import org.opensrp.register.mcare.domain.Members;

import java.util.*;

public abstract class FamilyPlanningTestData {
    public List<Members> members;
    public int resultCount;

    public FamilyPlanningTestData(List<Members> members, int resultCount) {
        this.members = members;
        this.resultCount = resultCount;
    }

    protected static List<Members> createNewTestData(String validBirthControlMethod, int totalData, int validData){
        List<Members> members = new ArrayList<>();
        for (int i = 0; i < totalData; i++) {
            if (i < validData/2) {
                Members member = createMemberUsingBirthControlValue(validBirthControlMethod);
                addRandomNumberOfElcoFollowUp(member);
                addElcoFollowUpUsingBirthControlValue(member, Members.BIRTH_CONTROL_NULL_VALUE);
                addElcoFollowUpUsingBirthControlValue(member, validBirthControlMethod);
                members.add(member);
            } else if(i<validData) {
                Members member = createMemberUsingBirthControlValue(validBirthControlMethod);
                addElcoFollowUpUsingBirthControlValue(member, validBirthControlMethod);
                members.add(member);
            } else if (i < validData+validData/2) {
                Members member = createMemberUsingBirthControlValue(Members.BIRTH_CONTROL_NULL_VALUE);
                addRandomNumberOfElcoFollowUp(member);
                addElcoFollowUpUsingBirthControlValue(member, validBirthControlMethod);
                addElcoFollowUpUsingBirthControlValue(member, Members.BIRTH_CONTROL_NULL_VALUE);
                members.add(member);
            }else {
                members.add(new Members());
            }
        }
        return members;
    }



    protected static List<Members> createLeftBirthControlMethodTestData(
            String validBirthControlMethod, String secondBirthControlMethod, String thirdBirthControlMethod,
            int totalCount, int validCount) {
        List<Members> members = new ArrayList<>();
        int halfValidCount = validCount/2;
        for (int i = 0; i < totalCount; i++) {
            if (i < halfValidCount) {
                Members member = createMemberWithOutAnyFamilyPlanning();
                addRandomNumberOfElcoFollowUp(member);
                addElcoFollowUpUsingBirthControlValue(member, validBirthControlMethod);
                addElcoFollowUpUsingBirthControlValue(member, Members.BIRTH_CONTROL_NULL_VALUE);
                members.add(member);
            } else if(i<halfValidCount*2) {
                Members member = createMemberUsingBirthControlValue(Members.BIRTH_CONTROL_NOT_USING_ANY_METHOD);
                addRandomNumberOfElcoFollowUp(member);
                addElcoFollowUpUsingBirthControlValue(member, validBirthControlMethod);
                addElcoFollowUpUsingBirthControlValue(member, Members.BIRTH_CONTROL_NOT_USING_ANY_METHOD);
                members.add(member);
            } else if (i < halfValidCount*3) {
                Members member = createMemberUsingBirthControlValue(secondBirthControlMethod);
                addRandomNumberOfElcoFollowUp(member);
                addElcoFollowUpUsingBirthControlValue(member, validBirthControlMethod);
                addElcoFollowUpUsingBirthControlValue(member, secondBirthControlMethod);
                members.add(member);
            }else if (i < halfValidCount*4){
                Members member = createMemberUsingBirthControlValue(thirdBirthControlMethod);
                addRandomNumberOfElcoFollowUp(member);
                addElcoFollowUpUsingBirthControlValue(member, validBirthControlMethod);
                addElcoFollowUpUsingBirthControlValue(member, thirdBirthControlMethod);
                members.add(member);
            }else {
                members.add(new Members());
            }
        }
        return members;
    }

    protected static List<Members> createTotalUsagesData(
            String validBirthControlMethod, int totalData, int validData) {
        List<Members> members = new ArrayList<>();
        for (int i = 0; i < totalData; i++) {
            if (i < validData) {
                members.add(createMemberUsingBirthControlValue(validBirthControlMethod));
            } else if (i < 700) {
                members.add(createMemberUsingBirthControlValue(Members.BIRTH_CONTROL_NULL_VALUE));
            }else {
                members.add(new Members());
            }
        }

        return members;
    }

    protected static Members createMemberUsingBirthControlValue(String birthControlValue) {
        Members member = new Members();
        Map<String, String> detail = createHashMap(Members.BIRTH_CONTROL_KEY, birthControlValue);
        member.setDetails(detail);
        return member;
    }

    protected static Members createMemberWithOutAnyFamilyPlanning() {
        Members member = new Members();
        Map<String, String> detail = createHashMap(Members.USING_FAMILY_PLANNING_KEY, Members.NOT_USING_FAMILY_PLANNING_VALUE);
        member.setDetails(detail);
        return member;
    }

    protected static Members addRandomNumberOfElcoFollowUp(Members member){
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


    protected static Members addElcoFollowUpUsingBirthControlValue(Members member, String value){
        Map<String, String> birthControlUsages = createHashMap(Members.BIRTH_CONTROL_KEY, value);
        return addElcoFollowUp(member, birthControlUsages);
    }

    protected static Members addElcoFollowUp(Members member, Map<String, String> detail) {
        List<Map<String, String>> elcoFollowUp = member.elco_Followup();
        elcoFollowUp.add(detail);
        member.setelco_Followup(elcoFollowUp);
        return member;
    }

    protected static Map<String, String > createHashMap(String key, String value) {
        HashMap detail = new HashMap();
        detail.put(key, value);
        return detail;
    }

}
