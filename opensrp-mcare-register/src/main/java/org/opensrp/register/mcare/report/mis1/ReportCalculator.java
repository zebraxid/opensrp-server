package org.opensrp.register.mcare.report.mis1;


import org.opensrp.register.mcare.domain.Members;

public abstract class ReportCalculator {
    protected long startDateTime;
    protected long endDateTime;

    public ReportCalculator(long startDateTime, long endDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public abstract void calculate(Members member);
}
