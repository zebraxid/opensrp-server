package org.opensrp.register.mcare.report.mis1.maternityCare.data;


import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.BNFVisit;
import org.opensrp.register.mcare.report.mis1.MIS1TestData;
import java.util.Map;

import java.util.ArrayList;
import java.util.List;

import static org.opensrp.register.mcare.domain.Members.BirthNotificationVisitKeyValue.*;

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
            int  deliveredBy = (i%6)+1;
            if(i<validCount-2) {
                bnfVisits.add(
                        createBnfVisitWithDeliveredAtAndBy(
                                DeliveryPlace.HOME, DeliveryBy.fromInt(deliveredBy)));
            }else {
                bnfVisits.add(
                        createBnfVisitWithDeliveredAtAndBy(
                                DeliveryPlace.DISTRICT_OR_OTHER_GOVT_HOSPITAL, DeliveryBy.fromInt(deliveredBy)));
            }
            bnfVisits.addAll(createRandomNumberOfBNFVisitsWithExceedInvalidClientVersion());
            allMembers.add(createMemberWithBNFVisits(bnfVisits));
        }
        return allMembers;
    }

    public List<Members> createNormalBirthAtHospitalOrClinicTestData() {
        List<Members> allMembers = new ArrayList<>();
        allMembers.add(createValidMemberWithStartDateTimeForBirthAtHomeOrClinicDeliveryType(DeliveryType.NORMAL));
        allMembers.add(createValidMemberWithEndDateTimeForBirthAtHomeOrClinicDeliveryType(DeliveryType.NORMAL));

        for(int i=0; i<totalCount; i++) {
            List<Map<String , String>> bnfVisits = new ArrayList<>();
            bnfVisits.addAll(createRandomNumberOfBNFVisitsWithPreviousInvalidClientVersion());
            if(i<validCount-2) {
                int  deliveredAt = (i%6)+2;
                bnfVisits.add(
                        createBnfVisitWithDeliveredAtAndType(
                                DeliveryPlace.fromInt(deliveredAt), DeliveryType.NORMAL));
            }else {
                bnfVisits.add(
                        createBnfVisitWithDeliveredAtAndType(
                                DeliveryPlace.HOME, DeliveryType.NORMAL));
            }
            bnfVisits.addAll(createRandomNumberOfBNFVisitsWithExceedInvalidClientVersion());
            allMembers.add(createMemberWithBNFVisits(bnfVisits));
        }
        return allMembers;
    }

    public List<Members> createCesareanBirthAtHospitalOrClinicTestData() {
        List<Members> allMembers = new ArrayList<>();
        DeliveryType cesarean = DeliveryType.CESAREAN;
        allMembers.add(createValidMemberWithStartDateTimeForBirthAtHomeOrClinicDeliveryType(cesarean));
        allMembers.add(createValidMemberWithEndDateTimeForBirthAtHomeOrClinicDeliveryType(cesarean));

        for(int i=0; i<totalCount; i++) {
            List<Map<String , String>> bnfVisits = new ArrayList<>();
            bnfVisits.addAll(createRandomNumberOfBNFVisitsWithPreviousInvalidClientVersion());
            if(i<validCount-2) {
                int  deliveredAt = (i%6)+2;
                bnfVisits.add(
                        createBnfVisitWithDeliveredAtAndType(
                                DeliveryPlace.fromInt(deliveredAt), cesarean));
            }else {
                bnfVisits.add(
                        createBnfVisitWithDeliveredAtAndType(
                                DeliveryPlace.HOME, cesarean));
            }
            bnfVisits.addAll(createRandomNumberOfBNFVisitsWithExceedInvalidClientVersion());
            allMembers.add(createMemberWithBNFVisits(bnfVisits));
        }
        return allMembers;
    }

    private Members createValidMemberWithStartDateTimeForBirthAtHomeWithTrainedPerson() {
        List<Map<String, String>> bnfVisits = new ArrayList<>();
        bnfVisitBuilder.clientVersion(startDateTime);
        bnfVisitBuilder.deliveryAt(DeliveryPlace.HOME);
        bnfVisitBuilder.deliveryBy(DeliveryBy.DOCTOR);
        bnfVisits.add(bnfVisitBuilder.build().getVisitData());
        return createMemberWithBNFVisits(bnfVisits);
    }

    private Members createValidMemberWithStartDateTimeForBirthAtHomeOrClinicDeliveryType(DeliveryType deliveryType) {
        List<Map<String, String>> bnfVisits = new ArrayList<>();
        bnfVisitBuilder.clientVersion(startDateTime);
        bnfVisitBuilder.deliveryAt(DeliveryPlace.MOTHER_AND_CHILD_WELFARE_CENTER);
        bnfVisitBuilder.deliveryType(deliveryType);
        bnfVisits.add(bnfVisitBuilder.build().getVisitData());
        return createMemberWithBNFVisits(bnfVisits);
    }

    private Members createValidMemberWithEndDateTimeForBirthAtHomeWithTrainedPerson() {
        List<Map<String, String>> bnfVisits = new ArrayList<>();
        bnfVisitBuilder.clientVersion(endDateTime);
        bnfVisitBuilder.deliveryAt(DeliveryPlace.HOME);
        bnfVisitBuilder.deliveryBy(DeliveryBy.FWV);
        bnfVisits.add(bnfVisitBuilder.build().getVisitData());
        return createMemberWithBNFVisits(bnfVisits);
    }

    private Members createValidMemberWithEndDateTimeForBirthAtHomeOrClinicDeliveryType(DeliveryType deliveryType) {
        List<Map<String, String>> bnfVisits = new ArrayList<>();
        bnfVisitBuilder.clientVersion(endDateTime);
        bnfVisitBuilder.deliveryAt(DeliveryPlace.NGO_CLINIC_OR_HOSPITAL);
        bnfVisitBuilder.deliveryType(deliveryType);
        bnfVisits.add(bnfVisitBuilder.build().getVisitData());
        return createMemberWithBNFVisits(bnfVisits);
    }


    private Map<String , String> createBnfVisitWithDeliveredAtAndBy(DeliveryPlace deliveredAt, DeliveryBy deliveredBy) {
        bnfVisitBuilder.clientVersion(endDateTime);
        bnfVisitBuilder.deliveryAt(deliveredAt);
        bnfVisitBuilder.deliveryBy(deliveredBy);
        return bnfVisitBuilder.build().getVisitData();
    }

    private Map<String , String> createBnfVisitWithDeliveredAtAndType(DeliveryPlace deliveredAt, DeliveryType deliveryType) {
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
            bnfVisitBuilder.deliveryBy(DeliveryBy.FWV);
            bnfVisitBuilder.deliveryAt(DeliveryPlace.HOME);
            bnfVisitBuilder.deliveryType(DeliveryType.CESAREAN);
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
            bnfVisitBuilder.deliveryAt(DeliveryPlace.DISTRICT_OR_OTHER_GOVT_HOSPITAL);
            bnfVisitBuilder.deliveryBy(DeliveryBy.DOCTOR);
            invalidBnfVisits.add(bnfVisitBuilder.build().getVisitData());

        }
        return invalidBnfVisits;
    }
}
