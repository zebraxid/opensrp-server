package org.opensrp.register.mcare.report.mis1.maternityCare;


import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.EligibleCoupleFollowUP;
import org.opensrp.register.mcare.report.mis1.MIS1TestData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TTDoseCalculationTestData extends MIS1TestData {

    EligibleCoupleFollowUP.EligibleCoupleFollowUpBuilder eligibleCoupleFollowUpBuilder;
    EligibleCoupleFollowUP.TTDoseBuilder ttDoseBuilder;

    public TTDoseCalculationTestData(int totalCount, int validCount, long startDateTime, long endDateTime) {
        super(totalCount, validCount, startDateTime, endDateTime);
        eligibleCoupleFollowUpBuilder = new EligibleCoupleFollowUP.EligibleCoupleFollowUpBuilder();
        ttDoseBuilder = new EligibleCoupleFollowUP.TTDoseBuilder();
    }

    public List<Members> createTTOneDoseCountTestData() {
        List<Members> allMembers = new ArrayList<>();

        allMembers.add(createValidMemberWithStartDateTime());
        allMembers.add(createValidMemberWithEndDateTime());

        for (int i=0; i< totalCount; i++) {
            List<Map<String, String>> allEligibleCoupleFollowUp = new ArrayList<>();
            allEligibleCoupleFollowUp.addAll(createRandomNumberOfInvalidElcoFollowUP());
            if(i < validCount-2) {
                long randomDateTime = getRandomNumberBetween(startDateTime, endDateTime);
                ttDoseBuilder.addDoseOne();
                ttDoseBuilder.addDoseTwo();
                eligibleCoupleFollowUpBuilder.clientVersion(randomDateTime);
                eligibleCoupleFollowUpBuilder.tt_dose(ttDoseBuilder);
                allEligibleCoupleFollowUp.add(eligibleCoupleFollowUpBuilder.build().getFollowUp());
                allMembers.add(createMemberWithEligibleCoupleFollowUpList(allEligibleCoupleFollowUp));
            }
        }

        return allMembers;
    }

    public Members createValidMemberWithStartDateTime() {
        eligibleCoupleFollowUpBuilder.clientVersion(startDateTime);
        ttDoseBuilder.addDoseOne().addDoseTwo().addDoseThree().addDoseFour().addDoseFive();
        eligibleCoupleFollowUpBuilder.tt_dose(ttDoseBuilder);
        List<Map<String, String>> elcofollowUps = new ArrayList<>();
        elcofollowUps.add(eligibleCoupleFollowUpBuilder.build().getFollowUp());
        return createMemberWithEligibleCoupleFollowUpList(elcofollowUps);
    }

    public Members createValidMemberWithEndDateTime() {
        eligibleCoupleFollowUpBuilder.clientVersion(startDateTime);
        ttDoseBuilder.addDoseOne().addDoseTwo().addDoseThree().addDoseFour().addDoseFive();
        eligibleCoupleFollowUpBuilder.tt_dose(ttDoseBuilder);
        List<Map<String, String>> elcofollowUps = new ArrayList<>();
        elcofollowUps.add(eligibleCoupleFollowUpBuilder.build().getFollowUp());
        return createMemberWithEligibleCoupleFollowUpList(elcofollowUps);
    }


    public List<Map<String , String>> createRandomNumberOfInvalidElcoFollowUP() {
        int rand = getRandomNumberBetween(0, 100);
        List<Map<String, String>> elcofollowUps = new ArrayList<>();
        long invalidDateTime;
        for(int i=0; i<rand; i++) {
            if(i%2 == 0) {
                 invalidDateTime = getRandomNumberBetween(0, startDateTime-1);
            }else {
                invalidDateTime = getRandomNumberBetween(endDateTime+1, endDateTime*3);
            }
            eligibleCoupleFollowUpBuilder.clientVersion(invalidDateTime);
            ttDoseBuilder.addDoseOne().addDoseTwo().addDoseThree().addDoseFour().addDoseFive();
            eligibleCoupleFollowUpBuilder.tt_dose(ttDoseBuilder);
            elcofollowUps.add(eligibleCoupleFollowUpBuilder.build().getFollowUp());
        }

        return elcofollowUps;
    }

}
