package org.opensrp.register.mcare.report.mis1.maternityCare;


import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.ReportCalculator;

public class AncReportCalculator extends ReportCalculator{
    long visitOneCount = 0;


    public AncReportCalculator(long startDateTime, long endDateTime) {
        super(startDateTime, endDateTime);
    }

    @Override
    public void calculate(Members member) {

    }

    public long getVisitOneCount() {
        return visitOneCount;
    }
}
