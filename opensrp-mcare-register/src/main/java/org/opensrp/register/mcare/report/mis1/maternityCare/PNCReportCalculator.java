package org.opensrp.register.mcare.report.mis1.maternityCare;


import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.ReportCalculator;

import java.util.Map;

import static org.opensrp.register.mcare.domain.Members.PNCVisitKeyValue.*;

public class PNCReportCalculator extends ReportCalculator{

    private PNCOneVisitCalculator pncOneVisitCalculator = new PNCOneVisitCalculator();

    public PNCReportCalculator(long startDateTime, long endDateTime) {
        super(startDateTime, endDateTime);
    }

    public PNCOneVisitCalculator getPncOneVisitCalculator() {
        return pncOneVisitCalculator;
    }

    @Override
    public void calculate(Members member) {
        this.pncOneVisitCalculator.calculate(member);
    }

    private class Type {
        long informationCount = 0 ;
        long serviceCount = 0;

        public long getInformationCount() {
            return informationCount;
        }

        public long getServiceCount() {
            return serviceCount;
        }
    }

    protected class PNCOneVisitCalculator extends Type {

        public void calculate(Members member) {
            this.informationCount += addToInformationCount(member.PNCVisit1());
            this.serviceCount += addToServiceCount(member.PNCVisit1());
        }


    }

    private int addToInformationCount(Map<String, String> visitData) {
        if(withInStartAndEndTime(visitData)) {
            if(metWithWomenAndFoundLiveOrStillBirth(visitData)) {
                return 1;
            }
        }
        return 0;
    }

    private int addToServiceCount(Map<String, String> visitData) {
        if(withInStartAndEndTime(visitData)) {
            if(pncGivenOnTime(visitData)) {
                return 1;
            }
        }
        return 0;
    }

    protected boolean metWithWomenAndFoundLiveOrStillBirth(Map<String, String> visitData) {
        VisitStatus visitStatus = VisitStatus.fromStr(visitData.get(Key.VISIT_STATUS));
        if(visitStatus == VisitStatus.MET_AND_BABY_ALIVE || visitStatus == VisitStatus.MET_AND_WOMEN_HAD_STILLBIRTH) {
            return true;
        }
        return false;
    }

    protected boolean pncGivenOnTime(Map<String, String> visitData) {
        PNCGivenOnTime pncGivenOnTime = PNCGivenOnTime.fromStr(visitData.get(Key.HAS_PNC_GIVEN_ON_TIME));
        if(pncGivenOnTime == PNCGivenOnTime.YES) {
            return true;
        }
        return false;
    }
}
