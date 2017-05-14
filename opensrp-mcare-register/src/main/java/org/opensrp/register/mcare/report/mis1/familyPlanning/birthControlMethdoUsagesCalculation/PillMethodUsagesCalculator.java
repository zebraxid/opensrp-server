package org.opensrp.register.mcare.report.mis1.familyPlanning.birthControlMethdoUsagesCalculation;


import org.opensrp.register.mcare.domain.Members;

public class PillMethodUsagesCalculator extends BirthControlMethodUsagesCalculator {


    public PillMethodUsagesCalculator(long startDateTime, long endDateTime) {
        super(Members.EligibleCoupleVisitKeyValue.BIRTH_CONTROL_PILL, startDateTime, endDateTime);
    }

}
