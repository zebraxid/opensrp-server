package org.opensrp.register.mcare.report.mis1.maternityCare;


import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.domain.Members.BooleanAnswer;
import org.opensrp.register.mcare.report.mis1.ReportCalculator;

import java.util.Map;

import static org.opensrp.register.mcare.domain.Members.ANCVisitKeyValue.Key;

public class ANCReportCalculator extends ReportCalculator {
    long visitOneCount = 0;
    long visitTwoCount = 0;
    long isReferredCount = 0;


    public ANCReportCalculator(long startDateTime, long endDateTime) {
        super(startDateTime, endDateTime);
    }

    @Override
    public void calculate(Members member) {
        visitOneCount += addToVisitOneCount(member);
        visitTwoCount += addToVisitTwoCount(member);
        isReferredCount += addToIsReferredCount(member);
    }

    public long getVisitOneCount() {
        return visitOneCount;
    }

    public long getVisitTwoCount() {
        return visitTwoCount;
    }

    public long getIsReferredCount() {
        return isReferredCount;
    }

    private int addToVisitOneCount(Members member) {
        Map<String, String> anc1Visit = member.ANCVisit1();
        if (withInStartAndEndTime(anc1Visit)) {
            return 1;
        }
        return 0;
    }

    private int addToVisitTwoCount(Members member) {
        Map<String, String> anc2Visit = member.ANCVisit2();
        if (withInStartAndEndTime(anc2Visit)) {
            return 1;
        }
        return 0;
    }

    private int addToIsReferredCount(Members member) {
        int count = 0;

        Map<String, String> anc1Visit = member.ANCVisit1();
        Map<String, String> anc2Visit = member.ANCVisit2();
        Map<String, String> anc3Visit = member.ANCVisit3();
        Map<String, String> anc4Visit = member.ANCVisit4();

        if (isReferredWithInStartAndEndTime(anc1Visit)) {
            count++;
        }
        if (isReferredWithInStartAndEndTime(anc2Visit)) {
            count++;
        }
        if (isReferredWithInStartAndEndTime(anc3Visit)) {
            count++;
        }
        if (isReferredWithInStartAndEndTime(anc4Visit)) {
            count++;
        }

        return count;
    }

    private boolean isReferredWithInStartAndEndTime(Map<String, String> visitData) {
        if (withInStartAndEndTime(visitData)) {
            if (isReferred(visitData)) {
                return true;
            }
        }
        return false;
    }


    private boolean isReferred(Map<String, String> visitData) {
        String isReferred = visitData.get(Key.IS_REFERRED);
        if (BooleanAnswer.fromStr(isReferred) == BooleanAnswer.YES) {
            return true;
        }
        return false;
    }

}
