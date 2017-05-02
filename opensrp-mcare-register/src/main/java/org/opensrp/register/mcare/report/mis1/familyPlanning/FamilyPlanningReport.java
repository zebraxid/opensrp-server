package org.opensrp.register.mcare.report.mis1.familyPlanning;


import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.Report;
import org.opensrp.register.mcare.report.mis1.familyPlanning.birthControlMethdoUsagesCalculation.BirthControlMethodUsagesCalculator;
import org.opensrp.register.mcare.report.mis1.familyPlanning.birthControlMethdoUsagesCalculation.CondomMethodUsagesCalculator;
import org.opensrp.register.mcare.report.mis1.familyPlanning.birthControlMethdoUsagesCalculation.PillMethodUsagesCalculator;
import org.opensrp.register.mcare.report.mis1.familyPlanning.eligibleCoupleCount.EligibleCoupleCountCalculator;

public class FamilyPlanningReport extends Report {
    private EligibleCoupleCountCalculator eligibleCoupleCountCalculator;
    private BirthControlMethodUsagesCalculator pillUsagesCalculator;
    private BirthControlMethodUsagesCalculator condomUsagesCalculator;

    public FamilyPlanningReport(long startDateTime, long endDateTime) {
        this.initCalculators(startDateTime, endDateTime);
    }

    @Override
    protected void initCalculators(long startDateTime, long endDateTime) {
        this.eligibleCoupleCountCalculator = new EligibleCoupleCountCalculator(startDateTime, endDateTime);
        this.pillUsagesCalculator = new PillMethodUsagesCalculator();
        this.condomUsagesCalculator = new CondomMethodUsagesCalculator();
    }

    @Override
    public void calculate(Members member) {
        pillUsagesCalculator.calculate(member);
        condomUsagesCalculator.calculate(member);
        eligibleCoupleCountCalculator.calculate(member);
    }

    public BirthControlMethodUsagesCalculator getPillUsagesCalculator() {
        return pillUsagesCalculator;
    }

    public BirthControlMethodUsagesCalculator getCondomUsagesCalculator() {
        return condomUsagesCalculator;
    }

    public EligibleCoupleCountCalculator getEligibleCoupleCountCalculator() {
        return eligibleCoupleCountCalculator;
    }
}
