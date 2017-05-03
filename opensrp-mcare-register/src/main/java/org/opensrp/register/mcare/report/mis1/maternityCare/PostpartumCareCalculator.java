package org.opensrp.register.mcare.report.mis1.maternityCare;


import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.ReportCalculator;

import java.util.List;
import java.util.Map;

import static org.opensrp.register.mcare.domain.Members.BirthNotificationVisitKeyValue.*;

public class PostpartumCareCalculator extends ReportCalculator {
    private int countOfBirthAtHomeWithTrainedPerson = 0;

    public PostpartumCareCalculator(long startDateTime, long endDateTime) {
        super(startDateTime, endDateTime);
    }

    public int getCountOfBirthAtHomeWithTrainedPerson() {
        return countOfBirthAtHomeWithTrainedPerson;
    }

    @Override
    public void calculate(Members member) {
        this.countOfBirthAtHomeWithTrainedPerson += addToCountOfBirthAtHomeWithTrainedPerson(member);
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
        if (visitData.containsKey(WHERE_DELIVERED_KEY)) {
            String deliveryPlace = visitData.get(WHERE_DELIVERED_KEY);
            if(assertDeliveredAt(DELIVERED_AT_HOME_VALUE, deliveryPlace)) {
                if (visitData.containsKey(WHO_DELIVERED_KEY)) {
                    String deliveryPerson = visitData.get(WHO_DELIVERED_KEY);
                    return deliveredByTrainedPerson(deliveryPerson);
                }
            }
        }
        return false;
    }

    private boolean assertDeliveredAt(String expected, String actual) {
        if(expected.equalsIgnoreCase(actual)){
            return true;
        }
        return false;
    }

    private boolean deliveredByTrainedPerson(String deliveryPerson) {
        switch (deliveryPerson) {
            case DELIVERED_BY_DOCTOR_VALUE:
                return true;
            case DELIVERED_BY_NURSE_VALUE:
                return true;
            case DELIVERED_BY_SACMO_VALUE:
                return true;
            case DELIVERED_BY_FWV_VALUE:
                return true;
            case DELIVERED_BY_PARAMEDICS_VALUE:
                return true;
            case DELIVERED_BY_CSBA_VALUE:
                return true;
            default:
                return false;
        }
    }
}
