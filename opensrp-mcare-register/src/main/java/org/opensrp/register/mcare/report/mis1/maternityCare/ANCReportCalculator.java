package org.opensrp.register.mcare.report.mis1.maternityCare;


import org.opensrp.connector.DHIS2.dxf2.DHIS2;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.domain.Members.BooleanAnswer;
import org.opensrp.register.mcare.report.mis1.ReportCalculator;

import java.util.Map;

import static org.opensrp.register.mcare.domain.Members.ANCVisitKeyValue.Key;

public class ANCReportCalculator extends ReportCalculator {
    @DHIS2(dataElementId="tpA10DvTrwU",categoryOptionId={"Gp7njQnYwG4", "rAjjuDsDx61"},dataSetId="iVd5aTE9P1B")
    long visitOneCount = 0;
    long visitTwoCount = 0;
    long isReferredCount = 0;
    long misoprostolRecivedCount = 0;
    private long visitThreeCount = 0;
    @DHIS2(dataElementId="R1nlR3GB5rB",categoryOptionId={"Gp7njQnYwG4", "rAjjuDsDx61"},dataSetId="iVd5aTE9P1B")
    private long visitFourCount = 0;


    public ANCReportCalculator(long startDateTime, long endDateTime) {
        super(startDateTime, endDateTime);
    }

    @Override
    public void calculate(Members member) {
        visitOneCount += addToVisitOneCount(member);
        visitTwoCount += addToVisitTwoCount(member);
        visitThreeCount += addToVisitThreeCount(member);
        visitFourCount += addToVisitFourCount(member);
        isReferredCount += addToIsReferredCount(member);
        misoprostolRecivedCount += addToMisoprostolRecivedCount(member);
    }


    public long getVisitOneCount() {
        return visitOneCount;
    }

    public long getVisitTwoCount() {
        return visitTwoCount;
    }

    public long getVisitThreeCount() {
        return visitThreeCount;
    }

    public long getVisitFourCount() {
        return visitFourCount;
    }

    public long getIsReferredCount() {
        return isReferredCount;
    }


    public long getMisoprostolRecivedCount() {
        return misoprostolRecivedCount;
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


    private long addToVisitThreeCount(Members member) {
        Map<String, String> anc3Visit = member.ANCVisit3();
        if (withInStartAndEndTime(anc3Visit)) {
            return 1;
        }
        return 0;
    }

    private long addToVisitFourCount(Members member) {
        Map<String, String> anc4Visit = member.ANCVisit4();
        if (withInStartAndEndTime(anc4Visit)) {
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

    private int addToMisoprostolRecivedCount(Members member) {
        int count = 0;
        Map<String, String> anc3Visit = member.ANCVisit3();
        Map<String, String> anc4Visit = member.ANCVisit4();
        if (misoprostolRecivedWithInStartAndEndTime(anc3Visit)) {
            count++;
        }
        if (misoprostolRecivedWithInStartAndEndTime(anc4Visit)) {
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

    private boolean misoprostolRecivedWithInStartAndEndTime(Map<String, String> visitData) {
        if (withInStartAndEndTime(visitData)) {
            if (misoprostolRecived(visitData)) {
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

    private boolean misoprostolRecived(Map<String, String> visitData) {
        String misoprostolRecived = visitData.get(Key.MISOPROSTOL_RECEIVED);
        if (BooleanAnswer.fromStr(misoprostolRecived) == BooleanAnswer.YES) {
            return true;
        }
        return false;
    }

}
