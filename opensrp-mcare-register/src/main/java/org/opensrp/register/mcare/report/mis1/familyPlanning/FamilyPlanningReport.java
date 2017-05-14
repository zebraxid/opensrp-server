package org.opensrp.register.mcare.report.mis1.familyPlanning;


import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.Report;
import org.opensrp.register.mcare.report.mis1.familyPlanning.birthControlMethdoUsagesCalculation.BirthControlMethodUsagesCalculator;
import org.opensrp.register.mcare.report.mis1.familyPlanning.birthControlMethdoUsagesCalculation.CondomMethodUsagesCalculator;
import org.opensrp.register.mcare.report.mis1.familyPlanning.birthControlMethdoUsagesCalculation.PillMethodUsagesCalculator;
import org.opensrp.register.mcare.report.mis1.familyPlanning.eligibleCoupleCount.EligibleCoupleCountCalculator;

public class FamilyPlanningReport extends Report {
    private EligibleCoupleCountCalculator eligibleCoupleCountCalculator;
    private PillMethodUsagesCalculator pillUsagesCalculator;
    private CondomMethodUsagesCalculator condomUsagesCalculator;

    public FamilyPlanningReport(long startDateTime, long endDateTime) {
        this.initCalculators(startDateTime, endDateTime);
    }

    @Override
    protected void initCalculators(long startDateTime, long endDateTime) {
        this.useReflectionToDynamicallyInitAllMemberOf(this.getClass(), startDateTime, endDateTime);
    }

    @Override
    public void calculate(Members member) {
        this.useReflectionToDynamicallyCallCalculateMethodOnAllMemberOf(this.getClass(), member);
    }

    public PillMethodUsagesCalculator getPillUsagesCalculator() {
        return pillUsagesCalculator;
    }

    public CondomMethodUsagesCalculator getCondomUsagesCalculator() {
        return condomUsagesCalculator;
    }

    public EligibleCoupleCountCalculator getEligibleCoupleCountCalculator() {
        return eligibleCoupleCountCalculator;
    }
}
