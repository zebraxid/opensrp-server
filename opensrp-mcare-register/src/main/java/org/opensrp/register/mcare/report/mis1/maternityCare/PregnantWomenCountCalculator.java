package org.opensrp.register.mcare.report.mis1.maternityCare;


import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.ReportCalculator;

public class PregnantWomenCountCalculator extends ReportCalculator{

    int newCount = 0;

    public PregnantWomenCountCalculator(long statDateTime, long endDateTime) {
        super(statDateTime, endDateTime);
    }

    @Override
    public void calculate(Members member) {

    }

    public int getNewCount() {
        return newCount;
    }
}
