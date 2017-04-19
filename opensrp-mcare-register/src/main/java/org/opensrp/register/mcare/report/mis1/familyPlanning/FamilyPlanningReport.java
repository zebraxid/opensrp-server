package org.opensrp.register.mcare.report.mis1.familyPlanning;


import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.eligibleCoupleCount.EligibleCoupleCountCalculator;
import org.opensrp.register.mcare.report.mis1.familyPlanning.birthControlMethdoUsagesCalculation.BirthControlMethodUsagesCalculator;
import org.opensrp.register.mcare.report.mis1.familyPlanning.birthControlMethdoUsagesCalculation.CondomMethodUsagesCalculator;
import org.opensrp.register.mcare.report.mis1.familyPlanning.birthControlMethdoUsagesCalculation.PillMethodUsagesCalculator;

public class FamilyPlanningReport {
    private long startDateTime;
    private long endDateTime;
    private EligibleCoupleCountCalculator eligibleCoupleCountCalculator;
    private BirthControlMethodUsagesCalculator pillUsagesCalculator;
    private BirthControlMethodUsagesCalculator condomUsagesCalculator;

    public FamilyPlanningReport(long startDateTime, long endDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.initCalculators();
    }

    private void initCalculators() {
        this.eligibleCoupleCountCalculator = new EligibleCoupleCountCalculator(startDateTime, endDateTime);
        this.pillUsagesCalculator = new PillMethodUsagesCalculator();
        this.condomUsagesCalculator = new CondomMethodUsagesCalculator();
    }


    public void calculate(Members member) {
        pillUsagesCalculator.calculate(member);
        condomUsagesCalculator.calculate(member);
    }

    public BirthControlMethodUsagesCalculator getPillUsagesCalculator() {
        return pillUsagesCalculator;
    }

    public BirthControlMethodUsagesCalculator getCondomUsagesCalculator() {
        return condomUsagesCalculator;
    }

}
