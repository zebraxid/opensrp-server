package org.opensrp.register.mcare.report.mis1.maternityCare;


import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.ReportCalculator;

import java.util.List;
import java.util.Map;

import static org.opensrp.register.mcare.domain.Members.BirthNotificationVisitKeyValue.*;

public class PostpartumCareCalculator extends ReportCalculator {
    private int countOfBirthAtHomeWithTrainedPerson = 0;
    private int countOfNormalBirthAtHospitalOrClinic = 0;

    public PostpartumCareCalculator(long startDateTime, long endDateTime) {
        super(startDateTime, endDateTime);
    }

    public int getCountOfBirthAtHomeWithTrainedPerson() {
        return countOfBirthAtHomeWithTrainedPerson;
    }

    public int getCountOfNormalBirthAtHospitalOrClinic() {
        return countOfNormalBirthAtHospitalOrClinic;
    }

    @Override
    public void calculate(Members member) {
        this.countOfBirthAtHomeWithTrainedPerson += addToCountOfBirthAtHomeWithTrainedPerson(member);
        this.countOfNormalBirthAtHospitalOrClinic += addToCountOfNormalBirthAtHospitalOrClinic(member);
    }

    private int addToCountOfBirthAtHomeWithTrainedPerson(Members member) {
        List<Map<String, String>> bnfVisits = member.bnfVisit();
        for (Map<String, String> bnfVisit : bnfVisits) {
            if (withInStartAndEndTime(bnfVisit)) {
                if (deliveredAtHomeWithTrainedPerson(bnfVisit)) {
                    return 1;
                }
            }
        }
        return 0;
    }

    private int addToCountOfNormalBirthAtHospitalOrClinic(Members member) {
        List<Map<String, String>> bnfVisits = member.bnfVisit();
        for (Map<String, String> bnfVisit : bnfVisits) {
            if (withInStartAndEndTime(bnfVisit)) {
                if (deliveryAtHospitalOrClinicWithType(bnfVisit, DeliveryType.NORMAL)) {
                    return 1;
                }
            }
        }
        return 0;
    }

    private boolean withInStartAndEndTime(Map<String, String> visitData) {
        if (visitData.containsKey(Members.CLIENT_VERSION_KEY)) {
            long clientVersion = Long.parseLong(visitData.get(Members.CLIENT_VERSION_KEY));
            if (clientVersion >= startDateTime && clientVersion <= endDateTime) {
                return true;
            }
        }
        return false;
    }

    private boolean deliveredAtHomeWithTrainedPerson(Map<String, String> visitData) {
        if (visitData.containsKey(Key.WHERE_DELIVERED)) {
            DeliveryPlace deliveryPlace = DeliveryPlace.fromStr(visitData.get(Key.WHERE_DELIVERED));
            if (!assertDeliveredAtHospitalOrClinic(deliveryPlace)) {
                if (visitData.containsKey(Key.WHO_DELIVERED)) {
                    String deliveryPerson = visitData.get(Key.WHO_DELIVERED);
                    return deliveredByTrainedPerson(DeliveryBy.fromInt(Integer.parseInt(deliveryPerson)));
                }
            }

        }
        return false;
    }

    private boolean deliveryAtHospitalOrClinicWithType(Map<String, String> visitData, DeliveryType expectedDeliveryType) {
        if (visitData.containsKey(Key.WHERE_DELIVERED)) {
            DeliveryPlace deliveryPlace = DeliveryPlace.fromStr(visitData.get(Key.WHERE_DELIVERED));
            if (assertDeliveredAtHospitalOrClinic(deliveryPlace)) {
                if (visitData.containsKey(Key.DELIVERY_TYPE)) {
                    DeliveryType actualDeliveryType = DeliveryType.fromStr(visitData.get(Key.DELIVERY_TYPE));
                    return assertDeliveryType(expectedDeliveryType, actualDeliveryType);
                }
            }
        }
        return false;
    }

    private boolean assertDeliveredAtHospitalOrClinic(DeliveryPlace deliveryPlace) {
        switch (deliveryPlace) {
            case HOME:
                return false;
            default:
                return true;
        }
    }

    private boolean assertDeliveryType(DeliveryType expected, DeliveryType actual) {
        if (expected == actual) {
            return true;
        }
        return false;
    }

    private boolean deliveredByTrainedPerson(DeliveryBy deliveryPerson) {
        switch (deliveryPerson) {
            case DOCTOR:
                return true;
            case NURSE:
                return true;
            case SACMO:
                return true;
            case FWV:
                return true;
            case PARAMEDICS:
                return true;
            case CSBA:
                return true;
            default:
                return false;
        }
    }
}
