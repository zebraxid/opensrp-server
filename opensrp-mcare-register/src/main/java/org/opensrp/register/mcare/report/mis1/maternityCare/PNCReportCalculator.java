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
            this.informationCount += addToInformationCount(member);
        }

        private int addToInformationCount(Members member) {
            Map<String, String > pncVisitOneData = member.PNCVisit1();
            if(withInStartAndEndTime(pncVisitOneData)) {
                if(metWithWomenAndFoundLiveOrStillBirth(pncVisitOneData)) {
                    return 1;
                }
            }
            return 0;
        }
    }

    protected boolean metWithWomenAndFoundLiveOrStillBirth(Map<String, String> visitData) {
        VisitStatus visitStatus = VisitStatus.fromStr(visitData.get(Key.VISIT_STATUS));
        if(visitStatus == VisitStatus.MET_AND_BABY_ALIVE || visitStatus == VisitStatus.MET_AND_WOMEN_HAD_STILLBIRTH) {
            return true;
        }
        return false;
    }
}
