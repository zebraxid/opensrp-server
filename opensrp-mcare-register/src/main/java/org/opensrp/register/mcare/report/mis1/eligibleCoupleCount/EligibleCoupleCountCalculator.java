package org.opensrp.register.mcare.report.mis1.eligibleCoupleCount;


import org.opensrp.register.mcare.domain.Members;

import java.util.List;
import java.util.Map;

public class EligibleCoupleCountCalculator {
    long startDateTime;
    long endDateTime;
    private int newEligibleCoupleVisitCount;
    private int unitTotalEligibleCoupleVisitCount;
    private int totalEligibleCouple;


    public EligibleCoupleCountCalculator(long startDateTime, long endDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.initCountVariable();
    }

    public void initCountVariable(){
        this.newEligibleCoupleVisitCount = 0;
        this.unitTotalEligibleCoupleVisitCount = 0;
        this.totalEligibleCouple = 0;
    }

    public void calculate(Members member) {
        this.newEligibleCoupleVisitCount += addToNewEligibleCoupleVisitCount(member);
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

    private int addToNewEligibleCoupleVisitCount(Members member){
        int countOfVisitForAMember = 0;
        List<Map<String, String>> eligibleCoupleVisits = member.elco_Followup();
        for(Map<String, String> eligibleCoupleVisit : eligibleCoupleVisits) {
            if(eligibleCoupleVisit.containsKey(Members.CLIENT_VERSION_KEY)) {
                long clientVersion = Long.parseLong(eligibleCoupleVisit.get(Members.CLIENT_VERSION_KEY));
                if(clientVersion >= startDateTime && clientVersion <= endDateTime) {
                    countOfVisitForAMember++;
                }
            }
        }
        return countOfVisitForAMember;
    }
}
