package org.opensrp.register.mcare.report.mis1.eligibleCoupleCount;


import org.opensrp.register.mcare.domain.Members;

public class EligibleCoupleCountCalculator {
    long startDateTime;
    long endDateTime;
    private int newEligibleCoupleVisitCount;
    private int unitTotalEligibleCoupleVisitCount;
    private int totalEligibleCouple;


    public EligibleCoupleCountCalculator(long startDateTime, long endDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public void calculate(Members member) {

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
}
