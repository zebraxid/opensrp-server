package org.opensrp.register.mcare.report.mis1.childCare;

import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.Report;

public class ChildCareReport extends Report {
    private NewBornCareReportCalculator newBornCareReportCalculator;

    public ChildCareReport(long startDateTime, long endDateTime) {
        super(startDateTime, endDateTime);
    }


    public NewBornCareReportCalculator getNewBornCareReportCalculator() {
        return newBornCareReportCalculator;
    }

    @Override
    public void calculate(Members member) {
        useReflectionToDynamicallyCallCalculateMethodOnAllMemberOf(this.getClass(),member);
    }

    @Override
    public void initCalculators(long startDateTime, long endDateTime) {
        useReflectionToDynamicallyInitAllMemberOf(this.getClass(), startDateTime, endDateTime);
    }

}
