package org.opensrp.register.mcare.report.mis1.maternityCare;


import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.EligibleCoupleFollowUP;
import org.opensrp.register.mcare.report.mis1.MIS1TestData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class PregnantWomenCountTestData extends MIS1TestData {

     EligibleCoupleFollowUP.EligibleCoupleFollowUpBuilder builder;

    public PregnantWomenCountTestData(int totalCount, int validCount, long startDateTime, long endDateTime) {
        super(totalCount, validCount, startDateTime, endDateTime);
        builder = new EligibleCoupleFollowUP.EligibleCoupleFollowUpBuilder();
    }

    public List<Members> getDataForNewPregnantWomenCount() {
        List<Members> allMembers = new ArrayList<>();

        allMembers.add(createValidMemberWithStartDateTime());
        allMembers.add(createValidMemberWithEndDateTime());

        for (int i = 0; i < totalCount; i++) {
            if (i < validCount - 2) {
                allMembers.add(createValidMemberWithRandomDateTime());
            } else {
                allMembers.add(createInValidMemberWithRandomDateTime());
            }

        }
        return allMembers;
    }


    private Members createValidMemberWithStartDateTime() {
        List<Map<String, String>> eligibleCoupleFollowUpList = new ArrayList<>();
        eligibleCoupleFollowUpList.add(createEligibleCoupleFollowUpUsingClientVersionDateTimeWithPregStatus(startDateTime, Members.IS_PREGNANT));
        return createMemberWithEligibleCoupleFollowUpList(eligibleCoupleFollowUpList);
    }

    private Members createValidMemberWithEndDateTime() {
        List<Map<String, String>> eligibleCoupleFollowUpList = new ArrayList<>();
        eligibleCoupleFollowUpList.add(createEligibleCoupleFollowUpUsingClientVersionDateTimeWithPregStatus(endDateTime, Members.IS_PREGNANT));
        return createMemberWithEligibleCoupleFollowUpList(eligibleCoupleFollowUpList);
    }

    private Members createValidMemberWithRandomDateTime() {
        List<Map<String, String>> eligibleCoupleFollowUpList = new ArrayList<>();
        eligibleCoupleFollowUpList.addAll(createRandomNumberOfEligibleCoupleFollowUpWithPreviousInvalidClientVersionAndRandomPregStatus());
        Long randomDateTimeBetweenStartAndEndDateTime = getRandomNumberBetween(startDateTime, endDateTime);
        eligibleCoupleFollowUpList.add(
                createEligibleCoupleFollowUpUsingClientVersionDateTimeWithPregStatus(randomDateTimeBetweenStartAndEndDateTime, Members.IS_PREGNANT));
        eligibleCoupleFollowUpList.addAll(createRandomNumberOfEligibleCoupleFollowUpWithExceedInvalidClientVersionAndRandomPregStatus());
        return createMemberWithEligibleCoupleFollowUpList(eligibleCoupleFollowUpList);
    }

    private Members createInValidMemberWithRandomDateTime() {
        List<Map<String, String>> eligibleCoupleFollowUpList = new ArrayList<>();
        eligibleCoupleFollowUpList.addAll(createRandomNumberOfEligibleCoupleFollowUpWithPreviousInvalidClientVersionAndRandomPregStatus());
        Long randomDateTimeBetweenStartAndEndDateTime = getRandomNumberBetween(startDateTime, endDateTime);
        eligibleCoupleFollowUpList.add(
                createEligibleCoupleFollowUpUsingClientVersionDateTimeWithPregStatus(randomDateTimeBetweenStartAndEndDateTime, Members.NOT_PREGNANT));
        eligibleCoupleFollowUpList.addAll(createRandomNumberOfEligibleCoupleFollowUpWithExceedInvalidClientVersionAndRandomPregStatus());
        return createMemberWithEligibleCoupleFollowUpList(eligibleCoupleFollowUpList);
    }

    private List<Map<String, String>> createRandomNumberOfEligibleCoupleFollowUpWithPreviousInvalidClientVersionAndRandomPregStatus() {
        Random rand = new Random();
        int randomNum = rand.nextInt((100 - 0) + 1) + 0;
        EligibleCoupleFollowUP eligibleCoupleFollowUP;
        List<Map<String, String>> eligibleCoupleFollowUpList = new ArrayList<>();
        for (int i = 0; i < randomNum; i++) {
            Long randomDateTimeBeforeStartDateTime = getRandomNumberBetween(0, startDateTime - 1);
            builder.clientVersion(randomDateTimeBeforeStartDateTime);
            if (i % 2 == 0) {
                builder.pregnant(Members.IS_PREGNANT);
                eligibleCoupleFollowUP = builder.build();
                eligibleCoupleFollowUpList.add(eligibleCoupleFollowUP.getFollowUp());
            } else {
                builder.pregnant(Members.NOT_PREGNANT);
                eligibleCoupleFollowUP = builder.build();
                eligibleCoupleFollowUpList.add(eligibleCoupleFollowUP.getFollowUp());
            }
        }
        return eligibleCoupleFollowUpList;
    }

    private Map<String, String> createEligibleCoupleFollowUpUsingClientVersionDateTimeWithPregStatus(long clientVersion, String pregnantStatus) {
        builder.pregnant(pregnantStatus).clientVersion(clientVersion);
        return builder.build().getFollowUp();
    }

    private List<Map<String, String>> createRandomNumberOfEligibleCoupleFollowUpWithExceedInvalidClientVersionAndRandomPregStatus() {
        Random rand = new Random();
        int randomNum = rand.nextInt((100 - 0) + 1) + 0;
        EligibleCoupleFollowUP eligibleCoupleFollowUP;
        List<Map<String, String>> eligibleCoupleFollowUpList = new ArrayList<>();
        for (int i = 0; i < randomNum; i++) {
            Long randomDateTimeBeforeStartDateTime = getRandomNumberBetween(endDateTime + 1, endDateTime * 3);
            builder.clientVersion(randomDateTimeBeforeStartDateTime);
            if (i % 2 == 0) {
                builder.pregnant(Members.IS_PREGNANT);
                eligibleCoupleFollowUP = builder.build();
                eligibleCoupleFollowUpList.add(eligibleCoupleFollowUP.getFollowUp());
            } else {
                builder.pregnant(Members.NOT_PREGNANT);
                eligibleCoupleFollowUP = builder.build();
                eligibleCoupleFollowUpList.add(eligibleCoupleFollowUP.getFollowUp());
            }
        }
        return eligibleCoupleFollowUpList;
    }


}
