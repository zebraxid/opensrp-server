package org.opensrp.register.mcare.report.mis1.maternityCare;


import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.Report;

public class MaternityCareReport extends Report{
    private PregnantWomenCountCalculator pregnantWomenCountCalculator;
    private ANCReportCalculator ANCReportCalculator;


    public MaternityCareReport(long startDateTime, long endDateTime) {
        this.initCalculators(startDateTime, endDateTime);
    }

    public PregnantWomenCountCalculator getPregnantWomenCountCalculator() {
        return pregnantWomenCountCalculator;
    }

    public ANCReportCalculator getANCReportCalculator() {
        return ANCReportCalculator;
    }

    @Override
    public void calculate(Members member) {
        this.pregnantWomenCountCalculator.calculate(member);
        this.ANCReportCalculator.calculate(member);
    }

    @Override
    protected void initCalculators(long startDateTime, long endDateTime){
        this.pregnantWomenCountCalculator = new PregnantWomenCountCalculator(startDateTime, endDateTime);
        this.ANCReportCalculator = new ANCReportCalculator(startDateTime, endDateTime);
    }


}
