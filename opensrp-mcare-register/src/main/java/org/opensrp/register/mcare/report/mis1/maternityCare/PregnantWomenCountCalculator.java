package org.opensrp.register.mcare.report.mis1.maternityCare;


import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.ReportCalculator;

import java.util.List;
import java.util.Map;

public class PregnantWomenCountCalculator extends ReportCalculator {

    long newPregnantCount = 0;

    public PregnantWomenCountCalculator(long statDateTime, long endDateTime) {
        super(statDateTime, endDateTime);
    }

    @Override
    public void calculate(Members member) {

        this.newPregnantCount += addToNewPregnantCount(member);
    }

    public long getNewPregnantCount() {
        return newPregnantCount;
    }

    private long addToNewPregnantCount(Members member) {
        List<Map<String, String>> eligibleCoupleVisits = member.elco_Followup();
        for (Map<String, String> eligibleCoupleVisit : eligibleCoupleVisits) {
            if (withInStartAndEndTime(eligibleCoupleVisit)) {
                if(checkPregnant(eligibleCoupleVisit)){
                    return 1;
                }
            }
        }
        return 0;
    }


    private boolean checkPregnant(Map<String, String> eligibleCoupleVisit) {
        if (eligibleCoupleVisit.containsKey(Members.EligibleCoupleVisitKeyValue.Key.PREGNANT_STATUS)) {
            return Members.EligibleCoupleVisitKeyValue.IS_PREGNANT.equalsIgnoreCase(eligibleCoupleVisit.get(Members.EligibleCoupleVisitKeyValue.Key.PREGNANT_STATUS));
        }
        return false;
    }
}
