package org.opensrp.register.mcare.report.mis1.familyPlanning.birthControlMethdoUsagesCalculation;

import org.opensrp.register.mcare.domain.Members;

public class IUDUsagesCalculator extends BirthControlMethodUsagesCalculator {
    public IUDUsagesCalculator(long startDateTime, long endDateTime) {
        super(Members.EligibleCoupleVisitKeyValue.BIRTH_CONTROL_MALE_PERMANENT, startDateTime, endDateTime);
    }
}
