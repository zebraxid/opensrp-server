package org.opensrp.register.mcare.report.mis1.maternityCare;


import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.EligibleCoupleFollowUP;
import org.opensrp.register.mcare.report.mis1.MIS1TestData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class PregnantWomenCountTestData extends MIS1TestData {

    public PregnantWomenCountTestData(int totalCount, int validCount, long startDateTime, long endDateTime) {
        super(totalCount, validCount, startDateTime, endDateTime);
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
        eligibleCoupleFollowUpList.add(addEligibleCoupleFollowUpUsingClientVersionDateTimeWithPregStatus(startDateTime, Members.IS_PREGNANT));
        return createMemberWithEligibleCoupleFollowUpList(eligibleCoupleFollowUpList);
    }

    private Members createValidMemberWithEndDateTime() {
        List<Map<String, String>> eligibleCoupleFollowUpList = new ArrayList<>();
        eligibleCoupleFollowUpList.add(addEligibleCoupleFollowUpUsingClientVersionDateTimeWithPregStatus(endDateTime, Members.IS_PREGNANT));
        return createMemberWithEligibleCoupleFollowUpList(eligibleCoupleFollowUpList);
    }

    private Members createValidMemberWithRandomDateTime() {
        List<Map<String, String>> eligibleCoupleFollowUpList = new ArrayList<>();
        eligibleCoupleFollowUpList.addAll(createRandomNumberOfEligibleCoupleFollowUpWithPreviousInvalidClientVersionAndRandomPregStatus());
        Long randomDateTimeBetweenStartAndEndDateTime = getRandomNumberBetween(startDateTime, endDateTime);
        eligibleCoupleFollowUpList.add(
                addEligibleCoupleFollowUpUsingClientVersionDateTimeWithPregStatus(randomDateTimeBetweenStartAndEndDateTime, Members.IS_PREGNANT));
        eligibleCoupleFollowUpList.addAll(addRandomNumberOfEligibleCoupleFollowUpWithExceedInvalidClientVersionAndRandomPregStatus());
        return createMemberWithEligibleCoupleFollowUpList(eligibleCoupleFollowUpList);
    }

    private Members createInValidMemberWithRandomDateTime() {
        List<Map<String, String>> eligibleCoupleFollowUpList = new ArrayList<>();
        eligibleCoupleFollowUpList.addAll(createRandomNumberOfEligibleCoupleFollowUpWithPreviousInvalidClientVersionAndRandomPregStatus());
        Long randomDateTimeBetweenStartAndEndDateTime = getRandomNumberBetween(startDateTime, endDateTime);
        eligibleCoupleFollowUpList.add(
                addEligibleCoupleFollowUpUsingClientVersionDateTimeWithPregStatus(randomDateTimeBetweenStartAndEndDateTime, Members.NOT_PREGNANT));
        eligibleCoupleFollowUpList.addAll(addRandomNumberOfEligibleCoupleFollowUpWithExceedInvalidClientVersionAndRandomPregStatus());
        return createMemberWithEligibleCoupleFollowUpList(eligibleCoupleFollowUpList);
    }

    private List<Map<String, String>> createRandomNumberOfEligibleCoupleFollowUpWithPreviousInvalidClientVersionAndRandomPregStatus() {
        Random rand = new Random();
        int randomNum = rand.nextInt((100 - 0) + 1) + 0;
        EligibleCoupleFollowUP eligibleCoupleFollowUP;
        List<Map<String, String>> eligibleCoupleFollowUpList = new ArrayList<>();
        for (int i = 0; i < randomNum; i++) {
            Long randomDateTimeBeforeStartDateTime = getRandomNumberBetween(0, startDateTime - 1);
            EligibleCoupleFollowUP.EligibleCoupleFollowUpBuilder builder =
                    new EligibleCoupleFollowUP.EligibleCoupleFollowUpBuilder(randomDateTimeBeforeStartDateTime);
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

    private Map<String, String> addEligibleCoupleFollowUpUsingClientVersionDateTimeWithPregStatus(long clientVersion, String pregnantStatus) {
        EligibleCoupleFollowUP.EligibleCoupleFollowUpBuilder builder =
                new EligibleCoupleFollowUP.EligibleCoupleFollowUpBuilder(clientVersion);
        builder.pregnant(pregnantStatus);
        return builder.build().getFollowUp();
    }

    private List<Map<String, String>> addRandomNumberOfEligibleCoupleFollowUpWithExceedInvalidClientVersionAndRandomPregStatus() {
        Random rand = new Random();
        int randomNum = rand.nextInt((100 - 0) + 1) + 0;
        EligibleCoupleFollowUP eligibleCoupleFollowUP;
        List<Map<String, String>> eligibleCoupleFollowUpList = new ArrayList<>();
        for (int i = 0; i < randomNum; i++) {
            Long randomDateTimeBeforeStartDateTime = getRandomNumberBetween(endDateTime + 1, endDateTime * 3);
            EligibleCoupleFollowUP.EligibleCoupleFollowUpBuilder builder =
                    new EligibleCoupleFollowUP.EligibleCoupleFollowUpBuilder(randomDateTimeBeforeStartDateTime);
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

    private Members createMemberWithEligibleCoupleFollowUpList(List<Map<String, String>> followUpList) {
        Members member = new Members();
        member.setelco_Followup(followUpList);
        return member;
    }

}
