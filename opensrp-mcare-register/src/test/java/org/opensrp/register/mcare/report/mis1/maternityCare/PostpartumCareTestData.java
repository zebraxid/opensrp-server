package org.opensrp.register.mcare.report.mis1.maternityCare;


import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.BNFVisit;
import org.opensrp.register.mcare.report.mis1.MIS1TestData;
import java.util.Map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PostpartumCareTestData extends MIS1TestData{

    BNFVisit.BNFVisitBuilder bnfVisitBuilder;

    public PostpartumCareTestData(int totalCount, int validCount, long startDateTime, long endDateTime) {
        super(totalCount, validCount, startDateTime, endDateTime);
        this.bnfVisitBuilder = new BNFVisit.BNFVisitBuilder();
    }

    public List<Members> createBirthAtHomeWithTrainedPersonTestData() {
        List<Members> allMembers = new ArrayList<>();

        return allMembers;
    }

    private Members createValidMemberWithStartDateTime() {
        List<Map<String, String>> bnfVisits = new ArrayList<>();
        bnfVisitBuilder.clientVersion(startDateTime);
        bnfVisitBuilder.deliveryAt(Members.BirthNotificationVisitKeyValue.DELIVERED_AT_HOME_VALUE);
        bnfVisitBuilder.deliveryBy(Members.BirthNotificationVisitKeyValue.DELIVERED_BY_DOCTOR_VALUE);
        bnfVisits.add(bnfVisitBuilder.build().getVisitData());
        return createMemberWithBNFVisits(bnfVisits);
    }

    private Members createValidMemberWithEndDateTime(VisitNumber visitNumber) {
        List<Map<String, String>> bnfVisits = new ArrayList<>();
        bnfVisitBuilder.clientVersion(endDateTime);
        bnfVisitBuilder.deliveryAt(Members.BirthNotificationVisitKeyValue.DELIVERED_AT_HOME_VALUE);
        bnfVisitBuilder.deliveryBy(Members.BirthNotificationVisitKeyValue.DELIVERED_BY_FWV_VALUE);
        bnfVisits.add(bnfVisitBuilder.build().getVisitData());
        return createMemberWithBNFVisits(bnfVisits);
    }

    private List<Map<String, String>> createRandomNumberOfBNFVisitsWithPreviousInvalidClientVersion() {
        Random rand = new Random();
        int randomNum = getRandomNumberBetween(0,100);
        List<Map<String, String>> invalidBnfVisits = new ArrayList<>();
        for (int i = 0; i < randomNum; i++) {
            Long randomDateTimeBeforeStartDateTime = getRandomNumberBetween(0, startDateTime - 1);
            bnfVisitBuilder.clientVersion(randomDateTimeBeforeStartDateTime);
            invalidBnfVisits.add(bnfVisitBuilder.build().getVisitData());

        }
        return invalidBnfVisits;
    }

    private List<Map<String, String>> createRandomNumberOfBNFVisitsWithExceedInvalidClientVersion() {
        Random rand = new Random();
        int randomNum = getRandomNumberBetween(0, 100);
        List<Map<String, String>> invalidBnfVisits = new ArrayList<>();
        for (int i = 0; i < randomNum; i++) {
            Long randomDateTimeBeforeStartDateTime = getRandomNumberBetween(endDateTime + 1, endDateTime * 7);
            bnfVisitBuilder.clientVersion(randomDateTimeBeforeStartDateTime);
            invalidBnfVisits.add(bnfVisitBuilder.build().getVisitData());

        }
        return invalidBnfVisits;
    }
}
