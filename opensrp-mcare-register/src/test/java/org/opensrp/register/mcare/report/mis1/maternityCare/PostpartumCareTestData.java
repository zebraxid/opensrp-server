package org.opensrp.register.mcare.report.mis1.maternityCare;


import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.BNFVisit;
import org.opensrp.register.mcare.report.mis1.MIS1TestData;
import java.util.Map;

import java.util.ArrayList;
import java.util.List;

public class PostpartumCareTestData extends MIS1TestData{

    BNFVisit.BNFVisitBuilder bnfVisitBuilder;

    public PostpartumCareTestData(int totalCount, int validCount, long startDateTime, long endDateTime) {
        super(totalCount, validCount, startDateTime, endDateTime);
        this.bnfVisitBuilder = new BNFVisit.BNFVisitBuilder();
    }

    public List<Members> createBirthAtHomeWithTrainedPersonTestData() {
        List<Members> allMembers = new ArrayList<>();
        allMembers.add(createValidMemberWithStartDateTimeForBirthAtHomeWithTrainedPerson());
        allMembers.add(createValidMemberWithEndDateTimeForBirthAtHomeWithTrainedPerson());
        for(int i=0; i<totalCount;i++) {
            List<Map<String , String>> bnfVisits = new ArrayList<>();
            bnfVisits.addAll(createRandomNumberOfBNFVisitsWithPreviousInvalidClientVersion());
            if(i<validCount-2) {
                String  deliveredBy = String.valueOf((i%6)+1);
                bnfVisits.add(
                        createBnfVisitWithDeliveredAtAndBy(
                                Members.BirthNotificationVisitKeyValue.DeliveryPlace.HOME.getValue().toString(), deliveredBy));
            }else {
                String  deliveredBy = String.valueOf((i%6)+1);
                bnfVisits.add(
                        createBnfVisitWithDeliveredAtAndBy(
                                Members.BirthNotificationVisitKeyValue.DeliveryPlace.DISTRICT_OR_OTHER_GOVT_HOSPITAL.getValue().toString(), deliveredBy));
            }
            bnfVisits.addAll(createRandomNumberOfBNFVisitsWithExceedInvalidClientVersion());
            allMembers.add(createMemberWithBNFVisits(bnfVisits));
        }
        return allMembers;
    }

    public List<Members> createNormalBirthAtHospitalOrClinicTestData() {
        String normalDeliveryType = String.valueOf(Members.BirthNotificationVisitKeyValue.DeliveryType.NORMAL.getValue());
        List<Members> allMembers = new ArrayList<>();
        allMembers.add(createValidMemberWithStartDateTimeForBirthAtHomeOrClinicDeliveryType(normalDeliveryType));
        allMembers.add(createValidMemberWithEndDateTimeForBirthAtHomeOrClinicDeliveryType(normalDeliveryType));

        for(int i=0; i<totalCount; i++) {
            List<Map<String , String>> bnfVisits = new ArrayList<>();
            bnfVisits.addAll(createRandomNumberOfBNFVisitsWithPreviousInvalidClientVersion());
            if(i<validCount-2) {
                String  deliveredAt = String.valueOf((i%6)+2);
                bnfVisits.add(
                        createBnfVisitWithDeliveredAtAndType(
                                deliveredAt, normalDeliveryType));
            }else {
                bnfVisits.add(
                        createBnfVisitWithDeliveredAtAndType(
                                Members.BirthNotificationVisitKeyValue.DeliveryPlace.HOME.getValue().toString(), normalDeliveryType));
            }
            bnfVisits.addAll(createRandomNumberOfBNFVisitsWithExceedInvalidClientVersion());
            allMembers.add(createMemberWithBNFVisits(bnfVisits));
        }
        return allMembers;
    }

    private Members createValidMemberWithStartDateTimeForBirthAtHomeWithTrainedPerson() {
        List<Map<String, String>> bnfVisits = new ArrayList<>();
        bnfVisitBuilder.clientVersion(startDateTime);
        bnfVisitBuilder.deliveryAt(Members.BirthNotificationVisitKeyValue.DeliveryPlace.HOME.getValue().toString());
        bnfVisitBuilder.deliveryBy(String.valueOf(Members.BirthNotificationVisitKeyValue.DeliveryBy.DOCTOR.getValue()));
        bnfVisits.add(bnfVisitBuilder.build().getVisitData());
        return createMemberWithBNFVisits(bnfVisits);
    }

    private Members createValidMemberWithStartDateTimeForBirthAtHomeOrClinicDeliveryType(String deliveryType) {
        List<Map<String, String>> bnfVisits = new ArrayList<>();
        bnfVisitBuilder.clientVersion(startDateTime);
        bnfVisitBuilder.deliveryAt(Members.BirthNotificationVisitKeyValue.DeliveryPlace.MOTHER_AND_CHILD_WELFARE_CENTER.getValue().toString());
        bnfVisitBuilder.deliveryType(deliveryType);
        bnfVisits.add(bnfVisitBuilder.build().getVisitData());
        return createMemberWithBNFVisits(bnfVisits);
    }

    private Members createValidMemberWithEndDateTimeForBirthAtHomeWithTrainedPerson() {
        List<Map<String, String>> bnfVisits = new ArrayList<>();
        bnfVisitBuilder.clientVersion(endDateTime);
        bnfVisitBuilder.deliveryAt(Members.BirthNotificationVisitKeyValue.DeliveryPlace.HOME.getValue().toString());
        bnfVisitBuilder.deliveryBy(String.valueOf(Members.BirthNotificationVisitKeyValue.DeliveryBy.FWV.getValue()));
        bnfVisits.add(bnfVisitBuilder.build().getVisitData());
        return createMemberWithBNFVisits(bnfVisits);
    }

    private Members createValidMemberWithEndDateTimeForBirthAtHomeOrClinicDeliveryType(String deliveryType) {
        List<Map<String, String>> bnfVisits = new ArrayList<>();
        bnfVisitBuilder.clientVersion(endDateTime);
        bnfVisitBuilder.deliveryAt(Members.BirthNotificationVisitKeyValue.DeliveryPlace.NGO_CLINIC_OR_HOSPITAL.getValue().toString());
        bnfVisitBuilder.deliveryType(deliveryType);
        bnfVisits.add(bnfVisitBuilder.build().getVisitData());
        return createMemberWithBNFVisits(bnfVisits);
    }


    private Map<String , String> createBnfVisitWithDeliveredAtAndBy(String deliveredAt, String deliveredBy) {
        bnfVisitBuilder.clientVersion(endDateTime);
        bnfVisitBuilder.deliveryAt(deliveredAt);
        bnfVisitBuilder.deliveryBy(deliveredBy);
        return bnfVisitBuilder.build().getVisitData();
    }

    private Map<String , String> createBnfVisitWithDeliveredAtAndType(String deliveredAt, String deliveryType) {
        bnfVisitBuilder.clientVersion(endDateTime);
        bnfVisitBuilder.deliveryAt(deliveredAt);
        bnfVisitBuilder.deliveryType(deliveryType);
        return bnfVisitBuilder.build().getVisitData();
    }

    private List<Map<String, String>> createRandomNumberOfBNFVisitsWithPreviousInvalidClientVersion() {
        int randomNum = getRandomNumberBetween(0,100);
        List<Map<String, String>> invalidBnfVisits = new ArrayList<>();
        for (int i = 0; i < randomNum; i++) {
            Long randomDateTimeBeforeStartDateTime = getRandomNumberBetween(0, startDateTime - 1);
            bnfVisitBuilder.clientVersion(randomDateTimeBeforeStartDateTime);
            bnfVisitBuilder.deliveryBy(String.valueOf(Members.BirthNotificationVisitKeyValue.DeliveryBy.FWV.getValue()));
            bnfVisitBuilder.deliveryAt(Members.BirthNotificationVisitKeyValue.DeliveryPlace.HOME.getValue().toString());
            bnfVisitBuilder.deliveryType(Members.BirthNotificationVisitKeyValue.DeliveryType.CESAREAN.toString());
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
            bnfVisitBuilder.deliveryAt(Members.BirthNotificationVisitKeyValue.DeliveryPlace.DISTRICT_OR_OTHER_GOVT_HOSPITAL.getValue().toString());
            bnfVisitBuilder.deliveryBy(Members.BirthNotificationVisitKeyValue.DeliveryBy.DOCTOR.getValue().toString());
            invalidBnfVisits.add(bnfVisitBuilder.build().getVisitData());

        }
        return invalidBnfVisits;
    }
}
