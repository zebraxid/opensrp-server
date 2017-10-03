package org.opensrp.register.mcare.report.mis1.familyPlanning.birthControlMethdoUsagesCalculation;

import org.opensrp.register.mcare.domain.Members;

public class FemalePermanentMethodUsagesCalculator extends BirthControlMethodUsagesCalculator {
    public FemalePermanentMethodUsagesCalculator(long startDateTime, long endDateTime) {
        super(Members.EligibleCoupleVisitKeyValue.BIRTH_CONTROL_FEMALE_PERMANENT, startDateTime, endDateTime);
    }
}
