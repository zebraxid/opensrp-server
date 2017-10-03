package org.opensrp.register.mcare.report.mis1.familyPlanning.birthControlMethdoUsagesCalculation;

import org.opensrp.register.mcare.domain.Members;

public class InjectableUsagesCalculator extends BirthControlMethodUsagesCalculator {

    protected InjectableUsagesCalculator(long startDateTime, long endDateTime) {
        super(Members.EligibleCoupleVisitKeyValue.BIRTH_CONTROL_INJECTABLE, startDateTime, endDateTime);
    }
}
