package org.opensrp.register.mcare.report.mis1.maternityCare;


import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.Report;

public class MaternityCareReport extends Report{
    private PregnantWomenCountCalculator pregnantWomenCountCalculator;
    private ANCReportCalculator ancReportCalculator;
    private PostpartumCareCalculator postpartumCareCalculator;
    private PNCReportCalculator pncReportCalculator;

    public MaternityCareReport(long startDateTime, long endDateTime) {
        this.initCalculators(startDateTime, endDateTime);
    }

    public PregnantWomenCountCalculator getPregnantWomenCountCalculator() {
        return pregnantWomenCountCalculator;
    }

    public ANCReportCalculator getAncReportCalculator() {
        return ancReportCalculator;
    }

    public PostpartumCareCalculator getPostpartumCareCalculator() {
        return postpartumCareCalculator;
    }

    public PNCReportCalculator getPncReportCalculator() {
        return pncReportCalculator;
    }

    @Override
    public void calculate(Members member) {
        this.pregnantWomenCountCalculator.calculate(member);
        this.ancReportCalculator.calculate(member);
        this.postpartumCareCalculator.calculate(member);
        this.pncReportCalculator.calculate(member);
    }

    @Override
    protected void initCalculators(long startDateTime, long endDateTime){
        this.pregnantWomenCountCalculator = new PregnantWomenCountCalculator(startDateTime, endDateTime);
        this.ancReportCalculator = new ANCReportCalculator(startDateTime, endDateTime);
        this.postpartumCareCalculator = new PostpartumCareCalculator(startDateTime, endDateTime);
        this.pncReportCalculator = new PNCReportCalculator(startDateTime, endDateTime);
    }


}
