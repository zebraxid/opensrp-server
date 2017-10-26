package org.opensrp.register.mcare.report.mis1.childCare;

import org.opensrp.connector.DHIS2.dxf2.DHIS2;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.ReportCalculator;

import java.util.Map;

public class NewBornCareReportCalculator extends ReportCalculator {

    @DHIS2(dataElementId ="biRKRKOV0Ul",categoryOptionId="D6XGHUZbN8B",dataSetId="Vc8Ps0P84hZ")
    private long isCleanedCount = 0;

    @DHIS2(dataElementId ="biRKRKOV0Ul",categoryOptionId="pdIMQ6uChwY",dataSetId="Vc8Ps0P84hZ")
    private long usedChlorhexidinCount = 0;

    @DHIS2(dataElementId ="biRKRKOV0Ul",categoryOptionId="KHDexzpGhKx",dataSetId="Vc8Ps0P84hZ")
    private long fedBreastMilkCount = 0;

    public long getIsCleanedCount() {
        return isCleanedCount;
    }

    public long getUsedChlorhexidinCount() {
        return usedChlorhexidinCount;
    }


    public long getFedBreastMilkCount() {
        return fedBreastMilkCount;
    }

    public NewBornCareReportCalculator(long startDateTime, long endDateTime) {
        super(startDateTime, endDateTime);
    }

    @Override
    public void calculate(Members member) {
        this.isCleanedCount += addToIsCleanedCount(member);
        this.usedChlorhexidinCount += addToUsedChlorhexidinCount(member);
        this.fedBreastMilkCount += addToFedBreastMilkCount(member);
    }

    private long addToIsCleanedCount(Members member) {
        return getCountBasedOnKey(member, Members.PNCVisitKeyValue.Key.IS_CLEANED);
    }


    private long addToUsedChlorhexidinCount(Members member) {
        return getCountBasedOnKey(member, Members.PNCVisitKeyValue.Key.USED_CHLORHEXIDIN);
    }

    private long addToFedBreastMilkCount(Members member) {
        return getCountBasedOnKey(member, Members.PNCVisitKeyValue.Key.BREASMILK_FED);
    }

    private long getCountBasedOnKey(Members member, String key) {
        Map<String, String> pnc1Visit = member.PNCVisit1();
        Map<String, String> pnc2Visit = member.PNCVisit2();
        Map<String, String> pnc3Visit = member.PNCVisit3();
        Map<String, String> pnc4Visit = member.PNCVisit4();

        if (positiveValueWithInStartAndEndTime(pnc1Visit, key)) {
            return 1;
        }
        if (positiveValueWithInStartAndEndTime(pnc2Visit,key)) {
            return 1;
        }
        if (positiveValueWithInStartAndEndTime(pnc3Visit, key)) {
            return 1;
        }
        if (positiveValueWithInStartAndEndTime(pnc4Visit, key)) {
            return 1;
        }

        return 0;
    }

    private boolean positiveValueWithInStartAndEndTime(Map<String, String> visitData, String key) {
        if (withInStartAndEndTime(visitData)) {
            if (isPositive(visitData, key)) {
                return true;
            }
        }
        return false;
    }

    private boolean isPositive(Map<String, String> visitData, String key) {
        return havePositiveValueWithKey(visitData, key);
    }

}
