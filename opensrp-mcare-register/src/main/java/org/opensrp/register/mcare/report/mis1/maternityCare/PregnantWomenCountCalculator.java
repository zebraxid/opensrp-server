package org.opensrp.register.mcare.report.mis1.maternityCare;


import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.ReportCalculator;

import java.util.List;
import java.util.Map;

public class PregnantWomenCountCalculator extends ReportCalculator {

    long newCount = 0;

    public PregnantWomenCountCalculator(long statDateTime, long endDateTime) {
        super(statDateTime, endDateTime);
    }

    @Override
    public void calculate(Members member) {
        this.newCount += addToNewPregnantCount(member);
    }

    public long getNewCount() {
        return newCount;
    }

    private long addToNewPregnantCount(Members member) {
        List<Map<String, String>> eligibleCoupleVisits = member.elco_Followup();
        for (Map<String, String> eligibleCoupleVisit : eligibleCoupleVisits) {
            if (identifiedPregnantWithInStartAndEndDateTime(eligibleCoupleVisit)) {
                return 1;
            }
        }
        return 0;
    }

    private boolean identifiedPregnantWithInStartAndEndDateTime(Map<String, String> eligibleCoupleVisit) {
        if (eligibleCoupleVisit.containsKey(Members.CLIENT_VERSION_KEY)) {
            long clientVersion = Long.parseLong(eligibleCoupleVisit.get(Members.CLIENT_VERSION_KEY));
            if (clientVersion >= startDateTime && clientVersion <= endDateTime) {
                return checkPregnant(eligibleCoupleVisit);
            }
        }
        return false;
    }

    private boolean checkPregnant(Map<String, String> eligibleCoupleVisit) {
        if (eligibleCoupleVisit.containsKey(Members.EligibleCoupleVisitKeyValue.PREGNANT_STATUS_KEY)) {
            return Members.EligibleCoupleVisitKeyValue.IS_PREGNANT.equalsIgnoreCase(eligibleCoupleVisit.get(Members.EligibleCoupleVisitKeyValue.PREGNANT_STATUS_KEY));
        }
        return false;
    }
}
