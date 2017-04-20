package org.opensrp.register.mcare.report.mis1.familyPlanning.eligibleCoupleCount;


import org.opensrp.register.mcare.TestData;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.MIS1TestData;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class EligibleCoupleCountTestData extends MIS1TestData{


    public EligibleCoupleCountTestData(int totalCount, int validCount, long startDateTime, long endDateTime) {
        super();
        this.totalCount = totalCount;
        this.validCount = validCount;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public List<Members> getNewEligibleCoupleVisitData() {
        List<Members> allMembers = new ArrayList<>();
        for (int i = 0; i < totalCount; i++) {
            Members member = new Members();
            member = addRandomNumberOfElcoFollowUpWithPreviousInvalidClientVersion(member);
            if (i < validCount - 2) {
                Long randomDateTimeBetweenStartAndEndDateTime = getRandomNumberBetween(startDateTime, endDateTime);
                member = addElcoFollowUpUsingClientVersionDateTime(member, randomDateTimeBetweenStartAndEndDateTime);
            }
            member = addRandomNumberOfElcoFollowUpWithExceedInvalidClientVersion(member);
            allMembers.add(member);
        }
        Members member = new Members();
        member = addElcoFollowUpUsingClientVersionDateTime(member, startDateTime);
        allMembers.add(member);
        member = new Members();
        member = addElcoFollowUpUsingClientVersionDateTime(member, endDateTime);
        allMembers.add(member);
        return allMembers;
    }

    public List<Members> getTotalEligibleCoupleData() {
        List<Members> allMembers = new ArrayList<>();
        for (int i = 0; i < totalCount; i++) {
            Members member = new Members();
            if(i < (validCount - 2)) {
                long randomDateTimeBetweenStartAndEndDateTime = getRandomNumberBetween(startDateTime, endDateTime);
                member.setClientVersion(randomDateTimeBetweenStartAndEndDateTime);
            }else {
                if(i % 2 == 0) {
                    long randomDateTimeBeforeStartDateTime = getRandomNumberBetween(0, startDateTime-1);
                    member.setClientVersion(randomDateTimeBeforeStartDateTime);
                }else {
                    long randomDateTimeAfterEndDateTime = getRandomNumberBetween(endDateTime+1, endDateTime*6);
                    member.setClientVersion(randomDateTimeAfterEndDateTime);
                }
            }
            allMembers.add(member);
        }

        Members member = new Members();
        member.setClientVersion(startDateTime);
        allMembers.add(member);

        member = new Members();
        member.setClientVersion(endDateTime);
        allMembers.add(member);

        member = new Members();
        allMembers.add(member);

        return allMembers;
    }

    public List<Members> getUnitTotalTestData(List<String> unitNames) {
        int validDataPerUnit = validCount / unitNames.size();
        List<Members> allMembers = new ArrayList<>();
        for (int i = 0; i < totalCount; i++) {
            Members member = new Members();
            if(i < validDataPerUnit) {
                long randomDateTimeBetweenStartAndEndDateTime = getRandomNumberBetween(startDateTime, endDateTime);
                member.setClientVersion(randomDateTimeBetweenStartAndEndDateTime);
            }
        }
        return allMembers;
    }

    private Members addRandomNumberOfElcoFollowUpWithPreviousInvalidClientVersion(Members member) {
        Random rand = new Random();
        int randomNum = rand.nextInt((100 - 0) + 1) + 0;
        for (int i = 0; i < randomNum; i++) {
            Long randomDateTimeBeforeStartDateTime = getRandomNumberBetween(0, startDateTime-1);
            member = addElcoFollowUpUsingClientVersionDateTime(member, randomDateTimeBeforeStartDateTime);

        }
        return member;
    }

    private Members addRandomNumberOfElcoFollowUpWithExceedInvalidClientVersion(Members member) {
        Random rand = new Random();
        int randomNum = rand.nextInt((100 - 0) + 1) + 0;
        for (int i = 0; i < randomNum; i++) {
            Long randomDateTimeAfterEndDateTime = getRandomNumberBetween(endDateTime+1, endDateTime*6);
            member = addElcoFollowUpUsingClientVersionDateTime(member, randomDateTimeAfterEndDateTime);

        }
        return member;
    }


    private Members addElcoFollowUpUsingClientVersionDateTime(Members member, long clientVersion) {
        Map<String, String> clientVersionKeyValuePair = createHashMap(Members.CLIENT_VERSION_KEY, String.valueOf(clientVersion));
        return addElcoFollowUp(member, clientVersionKeyValuePair);
    }

    private Map<String, String> createHashMap(String key, String value) {
        HashMap detail = new HashMap();
        detail.put(key, value);
        return detail;
    }

    private Members addElcoFollowUp(Members member, Map<String, String> detail) {
        List<Map<String, String>> elcoFollowUp = member.elco_Followup();
        elcoFollowUp.add(detail);
        member.setelco_Followup(elcoFollowUp);
        return member;
    }


}
