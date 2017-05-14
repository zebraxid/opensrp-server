package org.opensrp.register.mcare.report.mis1.familyPlanning.birthControlMethdoUsagesCalculation;


import org.opensrp.register.mcare.domain.Members;

public class CondomMethodUsagesCalculator extends BirthControlMethodUsagesCalculator {


    public CondomMethodUsagesCalculator(long startDateTime, long endDateTime) {
        super(Members.EligibleCoupleVisitKeyValue.BIRTH_CONTROL_CONDOM, startDateTime, endDateTime);
    }
}
