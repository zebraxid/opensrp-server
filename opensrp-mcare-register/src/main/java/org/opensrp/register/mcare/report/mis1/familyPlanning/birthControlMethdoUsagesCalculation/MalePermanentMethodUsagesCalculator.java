package org.opensrp.register.mcare.report.mis1.familyPlanning.birthControlMethdoUsagesCalculation;

import org.opensrp.register.mcare.domain.Members;

public class MalePermanentMethodUsagesCalculator extends BirthControlMethodUsagesCalculator {
    public MalePermanentMethodUsagesCalculator(long startDateTime, long endDateTime) {
        super(Members.EligibleCoupleVisitKeyValue.BIRTH_CONTROL_MALE_PERMANENT, startDateTime, endDateTime);
    }
}
