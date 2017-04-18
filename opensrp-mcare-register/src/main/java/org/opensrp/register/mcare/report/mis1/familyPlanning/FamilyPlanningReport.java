package org.opensrp.register.mcare.report.mis1.familyPlanning;


import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.familyPlanning.birthControlMethdos.BirthControlMethodUsagesCalculator;
import org.opensrp.register.mcare.report.mis1.familyPlanning.birthControlMethdos.CondomMethodUsagesCalculator;
import org.opensrp.register.mcare.report.mis1.familyPlanning.birthControlMethdos.PillMethodUsagesCalculator;

public class FamilyPlanningReport {

    private BirthControlMethodUsagesCalculator pillUsagesCalculator;
    private BirthControlMethodUsagesCalculator condomUsagesCalculator;

    public FamilyPlanningReport() {
        this.initCalculators();
    }

    private void initCalculators() {
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
