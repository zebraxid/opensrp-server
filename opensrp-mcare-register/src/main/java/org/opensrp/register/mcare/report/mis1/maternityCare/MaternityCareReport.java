package org.opensrp.register.mcare.report.mis1.maternityCare;


import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.Report;

public class MaternityCareReport extends Report{
    private PregnantWomenCountCalculator pregnantWomenCountCalculator;
    private ANCReportCalculator ancReportCalculator;
    private PostpartumCareCalculator postpartumCareCalculator;
    private PNCReportCalculator pncReportCalculator;
    private TTDoseReportCalculator ttDoseReportCalculator;
    private AdolescentHealthReportCalculator adolescentHealthReportCalculator;

    public MaternityCareReport(long startDateTime, long endDateTime) {
       super(startDateTime, endDateTime);
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

    public TTDoseReportCalculator getTTDoseReportCalculator() {
        return ttDoseReportCalculator;
    }

    public AdolescentHealthReportCalculator getAdolescentHealthReportCalculator() {
        return adolescentHealthReportCalculator;
    }

    @Override
    public void calculate(Members member) {
        this.useReflectionToDynamicallyCallCalculateMethodOnAllMemberOf(this.getClass(), member);
    }

    @Override
    protected void initCalculators(long startDateTime, long endDateTime){
        this.useReflectionToDynamicallyInitAllMemberOf(this.getClass(), startDateTime, endDateTime);
    }


}
