package org.opensrp.register.mcare.report.mis1.maternityCare;


import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.EligibleCoupleFollowUP;
import org.opensrp.register.mcare.report.mis1.MIS1TestData;

import java.util.*;

public class CountOfPregnantWomenTestData extends MIS1TestData {

    public List<Members> getDataForNewPregnantWomenCount() {
        List<Members> allMembers = new ArrayList<>();

        Members member = new Members();
        member = addEligibleCoupleFollowUpUsingClientVersionDateTimeWithPregStatus(member, startDateTime, Members.IS_PREGNANT);
        allMembers.add(member);
        member = new Members();
        member = addEligibleCoupleFollowUpUsingClientVersionDateTimeWithPregStatus(member, endDateTime, Members.IS_PREGNANT);
        allMembers.add(member);

        for (int i = 0; i < totalCount; i++) {
            member = new Members();
            member = addRandomNumberOfEligibleCoupleFollowUpWithPreviousInvalidClientVersionAndRandomPregStatus(member);
            if (i < validCount - 2) {
                Long randomDateTimeBetweenStartAndEndDateTime = getRandomNumberBetween(startDateTime, endDateTime);
                member = addEligibleCoupleFollowUpUsingClientVersionDateTimeWithPregStatus(
                        member, randomDateTimeBetweenStartAndEndDateTime, Members.IS_PREGNANT);
            } else {
                Long randomDateTimeBetweenStartAndEndDateTime = getRandomNumberBetween(startDateTime, endDateTime);
                member = addEligibleCoupleFollowUpUsingClientVersionDateTimeWithPregStatus(
                        member, randomDateTimeBetweenStartAndEndDateTime, Members.NOT_PREGNANT);
            }
            member = addRandomNumberOfEligibleCoupleFollowUpWithExceedInvalidClientVersionAndRandomPregStatus(member);
            allMembers.add(member);
        }

        return allMembers;
    }

    private Members addRandomNumberOfEligibleCoupleFollowUpWithPreviousInvalidClientVersionAndRandomPregStatus(Members member) {
        Random rand = new Random();
        int randomNum = rand.nextInt((100 - 0) + 1) + 0;
        EligibleCoupleFollowUP eligibleCoupleFollowUP;
        for (int i = 0; i < randomNum; i++) {
            Long randomDateTimeBeforeStartDateTime = getRandomNumberBetween(0, startDateTime - 1);
            EligibleCoupleFollowUP.EligibleCoupleFollowUpBuilder builder =
                    new EligibleCoupleFollowUP.EligibleCoupleFollowUpBuilder(randomDateTimeBeforeStartDateTime);
            if (i % 2 == 0) {
                builder.pregnant(Members.IS_PREGNANT);
                eligibleCoupleFollowUP = builder.build();
                member.setelco_Followup(eligibleCoupleFollowUP.getFollowUp());
            } else {
                builder.pregnant(Members.NOT_PREGNANT);
                eligibleCoupleFollowUP = builder.build();
                member.setelco_Followup(eligibleCoupleFollowUP.getFollowUp());
            }
        }
        return member;
    }

    private Members addEligibleCoupleFollowUpUsingClientVersionDateTimeWithPregStatus
            (Members member, long clientVersion, String pregnantStatus) {
        EligibleCoupleFollowUP.EligibleCoupleFollowUpBuilder builder =
                new EligibleCoupleFollowUP.EligibleCoupleFollowUpBuilder(clientVersion);
        builder.pregnant(pregnantStatus);
        EligibleCoupleFollowUP eligibleCoupleFollowUP = builder.build();
        member.setelco_Followup(eligibleCoupleFollowUP.getFollowUp());
        return member;
    }

    private Members addRandomNumberOfEligibleCoupleFollowUpWithExceedInvalidClientVersionAndRandomPregStatus(Members member) {
        Random rand = new Random();
        int randomNum = rand.nextInt((100 - 0) + 1) + 0;
        EligibleCoupleFollowUP eligibleCoupleFollowUP;
        for (int i = 0; i < randomNum; i++) {
            Long randomDateTimeBeforeStartDateTime = getRandomNumberBetween(endDateTime + 1, endDateTime * 3);
            EligibleCoupleFollowUP.EligibleCoupleFollowUpBuilder builder =
                    new EligibleCoupleFollowUP.EligibleCoupleFollowUpBuilder(randomDateTimeBeforeStartDateTime);
            if (i % 2 == 0) {
                builder.pregnant(Members.IS_PREGNANT);
                eligibleCoupleFollowUP = builder.build();
                member.setelco_Followup(eligibleCoupleFollowUP.getFollowUp());
            } else {
                builder.pregnant(Members.NOT_PREGNANT);
                eligibleCoupleFollowUP = builder.build();
                member.setelco_Followup(eligibleCoupleFollowUP.getFollowUp());
            }
        }
        return member;
    }

}
