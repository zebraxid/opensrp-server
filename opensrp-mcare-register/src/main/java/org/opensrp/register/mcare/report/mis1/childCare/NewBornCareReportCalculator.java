package org.opensrp.register.mcare.report.mis1.childCare;

import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.ReportCalculator;

import java.util.Map;

public class NewBornCareReportCalculator extends ReportCalculator {
    private long isCleanedCount = 0;

    public long getIsCleanedCount() {
        return isCleanedCount;
    }

    public NewBornCareReportCalculator(long startDateTime, long endDateTime) {
        super(startDateTime, endDateTime);
    }

    @Override
    public void calculate(Members member) {
        this.isCleanedCount += addToIsCleanedCount(member);
    }

    private long addToIsCleanedCount(Members member) {
        Map<String, String> pnc1Visit = member.PNCVisit1();
        Map<String, String> pnc2Visit = member.PNCVisit2();
        Map<String, String> pnc3Visit = member.PNCVisit3();
        Map<String, String> pnc4Visit = member.PNCVisit4();

        if (isCleanedWithInStartAndEndTime(pnc1Visit)) {
           return 1;
        }
        if (isCleanedWithInStartAndEndTime(pnc2Visit)) {
            return 1;
        }
        if (isCleanedWithInStartAndEndTime(pnc3Visit)) {
            return 1;
        }
        if (isCleanedWithInStartAndEndTime(pnc4Visit)) {
            return 1;
        }

        return 0;
    }

    private boolean isCleanedWithInStartAndEndTime(Map<String, String> visitData) {
        if (withInStartAndEndTime(visitData)) {
            if (isCleaned(visitData)) {
                return true;
            }
        }
        return false;
    }

    private boolean isCleaned(Map<String, String> visitData) {
        return havePositiveValueWithKey(visitData, Members.PNCVisitKeyValue.Key.IS_CLEANED);
    }


}
