package org.opensrp.register.mcare.report.mis1.familyPlanning.eligibleCoupleCount;


import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.ReportCalculator;

import java.util.List;
import java.util.Map;

public class EligibleCoupleCountCalculator extends ReportCalculator{
    private int newEligibleCoupleVisitCount = 0;
    private int unitTotalEligibleCoupleVisitCount = 0;
    private int totalEligibleCouple = 0;


    public EligibleCoupleCountCalculator(long startDateTime, long endDateTime) {
        super(startDateTime, endDateTime);

    }

    @Override
    public void calculate(Members member) {
        this.newEligibleCoupleVisitCount += addToNewEligibleCoupleVisitCount(member);
        this.totalEligibleCouple += addToTotalEligibleCoupleCount(member);
    }

    public int getNewEligibleCoupleVisitCount() {
        return newEligibleCoupleVisitCount;
    }

    public int getUnitTotalEligibleCoupleVisitCount() {
        return unitTotalEligibleCoupleVisitCount;
    }

    public int getTotalEligibleCouple() {
        return totalEligibleCouple;
    }

    private int addToNewEligibleCoupleVisitCount(Members member) {
        int countOfVisitForAMember = 0;
        List<Map<String, String>> eligibleCoupleVisits = member.elco_Followup();
        for (Map<String, String> eligibleCoupleVisit : eligibleCoupleVisits) {
            countOfVisitForAMember += checkIfVisitedBetweenStartAndEndDateTime(eligibleCoupleVisit);
        }
        return countOfVisitForAMember;
    }

    private int addToTotalEligibleCoupleCount(Members member) {
        Long clientVersion = member.getClientVersion();
        if (clientVersion != null) {
            if (clientVersion >= startDateTime && clientVersion <= endDateTime) {
                return 1;
            }
        }
        return 0;
    }

    private int checkIfVisitedBetweenStartAndEndDateTime(Map<String, String> eligibleCoupleVisit) {
        if (eligibleCoupleVisit.containsKey(Members.CLIENT_VERSION_KEY)) {
            long clientVersion = Long.parseLong(eligibleCoupleVisit.get(Members.CLIENT_VERSION_KEY));
            if (clientVersion >= startDateTime && clientVersion <= endDateTime) {
                return 1;
            }
        }
        return 0;
    }
}
