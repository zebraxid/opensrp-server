package org.opensrp.register.mcare.report.mis1.familyPlanning.eligibleCoupleCount;


import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.EligibleCoupleFollowUP;
import org.opensrp.register.mcare.report.mis1.MIS1TestData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class EligibleCoupleCountTestData extends MIS1TestData {
    static EligibleCoupleFollowUP.EligibleCoupleFollowUpBuilder builder =
            new EligibleCoupleFollowUP.EligibleCoupleFollowUpBuilder();

    public EligibleCoupleCountTestData(int totalCount, int validCount, long startDateTime, long endDateTime) {
        super(totalCount, validCount, startDateTime, endDateTime);
    }

    public List<Members> getNewEligibleCoupleVisitData() {
        List<Members> allMembers = new ArrayList<>();
        for (int i = 0; i < totalCount; i++) {
            if (i < validCount - 2) {
                allMembers.add(createValidDataWithRandomDateTime());
            } else {
                allMembers.add(createInvalidValidDataWithRandomDateTime());
            }
        }

        allMembers.add(createValidDataWithStartDateTime());
        allMembers.add(createValidDataWithEndDateTime());

        return allMembers;
    }

    public List<Members> getTotalEligibleCoupleData() {
        List<Members> allMembers = new ArrayList<>();
        for (int i = 0; i < totalCount; i++) {
            if (i < (validCount - 2)) {
                long randomDateTimeBetweenStartAndEndDateTime = getRandomNumberBetween(startDateTime, endDateTime);
                allMembers.add(createMemberWithClientVersion(randomDateTimeBetweenStartAndEndDateTime));
            } else {
                if (i % 2 == 0) {
                    long randomDateTimeBeforeStartDateTime = getRandomNumberBetween(0, startDateTime - 1);
                    allMembers.add(createMemberWithClientVersion(randomDateTimeBeforeStartDateTime));
                } else {
                    long randomDateTimeAfterEndDateTime = getRandomNumberBetween(endDateTime + 1, endDateTime * 6);
                    allMembers.add(createMemberWithClientVersion(randomDateTimeAfterEndDateTime));
                }
            }
        }

        allMembers.add(createMemberWithClientVersion(startDateTime));
        allMembers.add(createMemberWithClientVersion(endDateTime));
        allMembers.add(new Members());
        return allMembers;
    }


    private Members createValidDataWithStartDateTime() {
        List<Map<String, String>> eligibleCoupleFollowUpList = new ArrayList<>();
        eligibleCoupleFollowUpList.add(createEligibleCoupleFollowUpUsingClientVersionDateTime(startDateTime));
        return createMemberWithEligibleCoupleFollowUpList(eligibleCoupleFollowUpList);
    }

    private Members createValidDataWithEndDateTime() {
        List<Map<String, String>> eligibleCoupleFollowUpList = new ArrayList<>();
        eligibleCoupleFollowUpList.add(createEligibleCoupleFollowUpUsingClientVersionDateTime(endDateTime));
        return createMemberWithEligibleCoupleFollowUpList(eligibleCoupleFollowUpList);
    }

    private Members createValidDataWithRandomDateTime() {
        List<Map<String, String>> eligibleCoupleFollowUpList = new ArrayList<>();
        eligibleCoupleFollowUpList.addAll(createRandomNumberOfElcoFollowUpWithPreviousInvalidClientVersion());
        eligibleCoupleFollowUpList.add(createEligibleCoupleFollowUpUsingClientVersionDateTime(endDateTime));
        eligibleCoupleFollowUpList.addAll(createRandomNumberOfElcoFollowUpWithExceedInvalidClientVersion());
        return createMemberWithEligibleCoupleFollowUpList(eligibleCoupleFollowUpList);
    }


    private Members createInvalidValidDataWithRandomDateTime() {
        List<Map<String, String>> eligibleCoupleFollowUpList = new ArrayList<>();
        eligibleCoupleFollowUpList.addAll(createRandomNumberOfElcoFollowUpWithPreviousInvalidClientVersion());
        eligibleCoupleFollowUpList.addAll(createRandomNumberOfElcoFollowUpWithExceedInvalidClientVersion());
        return createMemberWithEligibleCoupleFollowUpList(eligibleCoupleFollowUpList);
    }

    private List<Map<String, String>> createRandomNumberOfElcoFollowUpWithPreviousInvalidClientVersion() {
        Random rand = new Random();
        int randomNum = rand.nextInt((100 - 0) + 1) + 0;
        List<Map<String, String>> eligibleCoupleFollowUpList = new ArrayList<>();
        for (int i = 0; i < randomNum; i++) {
            Long randomDateTimeBeforeStartDateTime = getRandomNumberBetween(0, startDateTime - 1);
            builder.clientVersion(randomDateTimeBeforeStartDateTime);
            eligibleCoupleFollowUpList.add(builder.build().getFollowUp());

        }
        return eligibleCoupleFollowUpList;
    }

    private List<Map<String, String>> createRandomNumberOfElcoFollowUpWithExceedInvalidClientVersion() {
        Random rand = new Random();
        int randomNum = rand.nextInt((100 - 0) + 1) + 0;
        List<Map<String, String>> eligibleCoupleFollowUpList = new ArrayList<>();
        for (int i = 0; i < randomNum; i++) {
            Long randomDateTimeBeforeStartDateTime = getRandomNumberBetween(endDateTime + 1, endDateTime * 7);
            builder.clientVersion(randomDateTimeBeforeStartDateTime);
            eligibleCoupleFollowUpList.add(builder.build().getFollowUp());

        }
        return eligibleCoupleFollowUpList;
    }

    private Map<String, String> createEligibleCoupleFollowUpUsingClientVersionDateTime(long clientVersion) {
        builder.clientVersion(clientVersion);
        return builder.build().getFollowUp();
    }
}
