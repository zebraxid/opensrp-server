package org.opensrp.register.mcare.report.mis1.maternityCare;


import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.EligibleCoupleFollowUP;
import org.opensrp.register.mcare.report.mis1.MIS1TestData;

import java.util.*;

public class PregnantWomenCountTestData extends MIS1TestData {

    public PregnantWomenCountTestData(int totalCount, int validCount, long startDateTime, long endDateTime) {
        super(totalCount, validCount, startDateTime, endDateTime);
    }

    public List<Members> getDataForNewPregnantWomenCount() {
        List<Members> allMembers = new ArrayList<>();

        Members member = new Members();
        EligibleCoupleFollowUP eligibleCoupleFollowUP = new EligibleCoupleFollowUP();
        eligibleCoupleFollowUP = addEligibleCoupleFollowUpUsingClientVersionDateTimeWithPregStatus(eligibleCoupleFollowUP, startDateTime, Members.IS_PREGNANT);
        member.setelco_Followup(eligibleCoupleFollowUP.getFollowUp());
        allMembers.add(member);

        member = new Members();
        eligibleCoupleFollowUP = new EligibleCoupleFollowUP();
        eligibleCoupleFollowUP = addEligibleCoupleFollowUpUsingClientVersionDateTimeWithPregStatus(eligibleCoupleFollowUP, endDateTime, Members.IS_PREGNANT);
        member.setelco_Followup(eligibleCoupleFollowUP.getFollowUp());
        allMembers.add(member);

        for (int i = 0; i < totalCount; i++) {
            member = new Members();
            eligibleCoupleFollowUP = new EligibleCoupleFollowUP();
            eligibleCoupleFollowUP = createRandomNumberOfEligibleCoupleFollowUpWithPreviousInvalidClientVersionAndRandomPregStatus(eligibleCoupleFollowUP);
            if (i < validCount - 2) {
                Long randomDateTimeBetweenStartAndEndDateTime = getRandomNumberBetween(startDateTime, endDateTime);
                eligibleCoupleFollowUP = addEligibleCoupleFollowUpUsingClientVersionDateTimeWithPregStatus(eligibleCoupleFollowUP, randomDateTimeBetweenStartAndEndDateTime, Members.IS_PREGNANT);
            } else {
                Long randomDateTimeBetweenStartAndEndDateTime = getRandomNumberBetween(startDateTime, endDateTime);
                eligibleCoupleFollowUP = addEligibleCoupleFollowUpUsingClientVersionDateTimeWithPregStatus(eligibleCoupleFollowUP, randomDateTimeBetweenStartAndEndDateTime, Members.NOT_PREGNANT);
            }

            eligibleCoupleFollowUP = addRandomNumberOfEligibleCoupleFollowUpWithExceedInvalidClientVersionAndRandomPregStatus(eligibleCoupleFollowUP);
            member.setelco_Followup(eligibleCoupleFollowUP.getFollowUp());
            allMembers.add(member);
        }

        return allMembers;
    }

    private EligibleCoupleFollowUP createRandomNumberOfEligibleCoupleFollowUpWithPreviousInvalidClientVersionAndRandomPregStatus(EligibleCoupleFollowUP eligibleCoupleFollowUP) {
        Random rand = new Random();
        int randomNum = rand.nextInt((100 - 0) + 1) + 0;
        for (int i = 0; i < randomNum; i++) {
            Long randomDateTimeBeforeStartDateTime = getRandomNumberBetween(0, startDateTime - 1);
            EligibleCoupleFollowUP.EligibleCoupleFollowUpBuilder builder =
                    new EligibleCoupleFollowUP.EligibleCoupleFollowUpBuilder(randomDateTimeBeforeStartDateTime);
            if (i % 2 == 0) {
                builder.pregnant(Members.IS_PREGNANT);
                eligibleCoupleFollowUP = builder.add(eligibleCoupleFollowUP);
            } else {
                builder.pregnant(Members.NOT_PREGNANT);
                eligibleCoupleFollowUP = builder.add(eligibleCoupleFollowUP);
            }
        }
    }

    private EligibleCoupleFollowUP addEligibleCoupleFollowUpUsingClientVersionDateTimeWithPregStatus
            (EligibleCoupleFollowUP eligibleCoupleFollowUP, long clientVersion, String pregnantStatus) {
        EligibleCoupleFollowUP.EligibleCoupleFollowUpBuilder builder =
                new EligibleCoupleFollowUP.EligibleCoupleFollowUpBuilder(clientVersion);
        builder.pregnant(pregnantStatus);
        builder.add(eligibleCoupleFollowUP);

    }

    private EligibleCoupleFollowUP addRandomNumberOfEligibleCoupleFollowUpWithExceedInvalidClientVersionAndRandomPregStatus(EligibleCoupleFollowUP eligibleCoupleFollowUP) {
        Random rand = new Random();
        int randomNum = rand.nextInt((100 - 0) + 1) + 0;
        for (int i = 0; i < randomNum; i++) {
            Long randomDateTimeBeforeStartDateTime = getRandomNumberBetween(endDateTime + 1, endDateTime * 3);
            EligibleCoupleFollowUP.EligibleCoupleFollowUpBuilder builder =
                    new EligibleCoupleFollowUP.EligibleCoupleFollowUpBuilder(randomDateTimeBeforeStartDateTime);
            if (i % 2 == 0) {
                builder.pregnant(Members.IS_PREGNANT);
                eligibleCoupleFollowUP = builder.add(eligibleCoupleFollowUP);
            } else {
                builder.pregnant(Members.NOT_PREGNANT);
                eligibleCoupleFollowUP = builder.add(eligibleCoupleFollowUP);
            }
        }
    }

}
