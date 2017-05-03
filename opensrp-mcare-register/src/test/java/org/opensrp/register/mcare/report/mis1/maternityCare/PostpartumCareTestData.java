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
        allMembers.add(createValidMemberWithStartDateTime());
        allMembers.add(createValidMemberWithEndDateTime());
        for(int i=0; i<totalCount;i++) {
            List<Map<String , String>> bnfVisits = new ArrayList<>();
            bnfVisits.addAll(createRandomNumberOfBNFVisitsWithExceedInvalidClientVersion());
            if(i<validCount-2) {
                String  deliveredBy = String.valueOf((i%6)+1);
                bnfVisits.add(
                        createBnfVisitWithDeliveredAtAndBy(
                                Members.BirthNotificationVisitKeyValue.DELIVERED_AT_HOME_VALUE, deliveredBy));
            }
            bnfVisits.addAll(createRandomNumberOfBNFVisitsWithExceedInvalidClientVersion());
            allMembers.add(createMemberWithBNFVisits(bnfVisits));
        }
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

    private Members createValidMemberWithEndDateTime() {
        List<Map<String, String>> bnfVisits = new ArrayList<>();
        bnfVisitBuilder.clientVersion(endDateTime);
        bnfVisitBuilder.deliveryAt(Members.BirthNotificationVisitKeyValue.DELIVERED_AT_HOME_VALUE);
        bnfVisitBuilder.deliveryBy(Members.BirthNotificationVisitKeyValue.DELIVERED_BY_FWV_VALUE);
        bnfVisits.add(bnfVisitBuilder.build().getVisitData());
        return createMemberWithBNFVisits(bnfVisits);
    }

    private Map<String , String> createBnfVisitWithDeliveredAtAndBy(String deliveredAt, String deliveredBy) {
        bnfVisitBuilder.clientVersion(endDateTime);
        bnfVisitBuilder.deliveryAt(deliveredAt);
        bnfVisitBuilder.deliveryBy(deliveredBy);
        return bnfVisitBuilder.build().getVisitData();
    }

    private List<Map<String, String>> createRandomNumberOfBNFVisitsWithPreviousInvalidClientVersion() {
        int randomNum = getRandomNumberBetween(0,100);
        List<Map<String, String>> invalidBnfVisits = new ArrayList<>();
        for (int i = 0; i < randomNum; i++) {
            Long randomDateTimeBeforeStartDateTime = getRandomNumberBetween(0, startDateTime - 1);
            bnfVisitBuilder.clientVersion(randomDateTimeBeforeStartDateTime);
            bnfVisitBuilder.deliveryBy(Members.BirthNotificationVisitKeyValue.DELIVERED_BY_FWV_VALUE);
            bnfVisitBuilder.deliveryAt(Members.BirthNotificationVisitKeyValue.DELIVERED_AT_HOME_VALUE);
            invalidBnfVisits.add(bnfVisitBuilder.build().getVisitData());

        }
        return invalidBnfVisits;
    }

    private List<Map<String, String>> createRandomNumberOfBNFVisitsWithExceedInvalidClientVersion() {
        int randomNum = getRandomNumberBetween(0, 100);
        List<Map<String, String>> invalidBnfVisits = new ArrayList<>();
        for (int i = 0; i < randomNum; i++) {
            Long randomDateTimeBeforeStartDateTime = getRandomNumberBetween(endDateTime + 1, endDateTime * 7);
            bnfVisitBuilder.clientVersion(randomDateTimeBeforeStartDateTime);
            bnfVisitBuilder.deliveryAt(Members.BirthNotificationVisitKeyValue.DELIVERED_AT_DISTRICT_OR_OTHER_GOVT_HOSPITAL_VALUE);
            bnfVisitBuilder.deliveryBy(Members.BirthNotificationVisitKeyValue.DELIVERED_BY_DOCTOR_VALUE);
            invalidBnfVisits.add(bnfVisitBuilder.build().getVisitData());

        }
        return invalidBnfVisits;
    }
}
