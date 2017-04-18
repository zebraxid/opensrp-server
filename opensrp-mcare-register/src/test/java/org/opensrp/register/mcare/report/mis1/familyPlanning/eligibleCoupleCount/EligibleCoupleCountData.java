package org.opensrp.register.mcare.report.mis1.familyPlanning.eligibleCoupleCount;


import org.opensrp.register.mcare.domain.Members;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class EligibleCoupleCountData {
    public int totalCount;
    public int validCount;
    public long startDateTime;
    public long endDateTime;

    public EligibleCoupleCountData(int totalCount, int validCount, long startDateTime, long endDateTime) {
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
                Long randomDateTimeBetweenStartAndEndDateTime = ThreadLocalRandom.current().nextLong(startDateTime, endDateTime + 2);
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

    private Members addRandomNumberOfElcoFollowUpWithPreviousInvalidClientVersion(Members member) {
        Random rand = new Random();
        int randomNum = rand.nextInt((100 - 0) + 1) + 0;
        for (int i = 0; i < randomNum; i++) {
            Long randomDateTimeBeforeStartDateTime = ThreadLocalRandom.current().nextLong(0, startDateTime);
            member = addElcoFollowUpUsingClientVersionDateTime(member, randomDateTimeBeforeStartDateTime);

        }
        return member;
    }

    private Members addRandomNumberOfElcoFollowUpWithExceedInvalidClientVersion(Members member) {
        Random rand = new Random();
        int randomNum = rand.nextInt((100 - 0) + 1) + 0;
        for (int i = 0; i < randomNum; i++) {
            Long randomDateTimeAfterEndDateTime = ThreadLocalRandom.current().nextLong(endDateTime, endDateTime*6);
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
