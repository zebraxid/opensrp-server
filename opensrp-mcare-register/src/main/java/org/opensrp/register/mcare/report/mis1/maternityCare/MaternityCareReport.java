package org.opensrp.register.mcare.report.mis1.maternityCare;


import org.opensrp.register.mcare.domain.Members;

public class MaternityCareReport {
    private PregnantWomenCountCalculator pregnantWomenCountCalculator;


    public MaternityCareReport(long startDateTime, long endDateTime) {
        this.initCalculators(startDateTime, endDateTime);
    }

    public PregnantWomenCountCalculator getPregnantWomenCountCalculator() {
        return pregnantWomenCountCalculator;
    }

    public void calculate(Members member) {
        this.pregnantWomenCountCalculator.calculate(member);
    }

    private void initCalculators(long startDateTime, long endDateTime){
        this.pregnantWomenCountCalculator = new PregnantWomenCountCalculator(startDateTime, endDateTime);
    }


}
