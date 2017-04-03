package org.opensrp.register.mcare.report.mis1.familyPlanning;


import org.opensrp.register.mcare.domain.Members;

public class FamilyPlanningReport {

    private PillUsagesCalculator pillUsagesCalculator;
    private CondomUsagesCalculator condomUsagesCalculator;

    public FamilyPlanningReport() {
        this.initCalculators();
    }

    private void initCalculators() {
        this.pillUsagesCalculator = new PillUsagesCalculator();
        this.condomUsagesCalculator = new CondomUsagesCalculator();
    }


    public void calculate(Members member) {
        pillUsagesCalculator.calculate(member);
        condomUsagesCalculator.calculate(member);
    }

    public PillUsagesCalculator getPillUsagesCalculator() {
        return pillUsagesCalculator;
    }

    public CondomUsagesCalculator getCondomUsagesCalculator() {
        return condomUsagesCalculator;
    }

}
