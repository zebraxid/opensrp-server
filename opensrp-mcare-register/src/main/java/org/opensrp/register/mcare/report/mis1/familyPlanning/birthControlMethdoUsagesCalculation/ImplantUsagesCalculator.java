package org.opensrp.register.mcare.report.mis1.familyPlanning.birthControlMethdoUsagesCalculation;

import org.opensrp.register.mcare.domain.Members;

public class ImplantUsagesCalculator extends BirthControlMethodUsagesCalculator {
    public ImplantUsagesCalculator(long startDateTime, long endDateTime) {
        super(Members.EligibleCoupleVisitKeyValue.BIRTH_CONTROL_IMPLANT, startDateTime, endDateTime);
    }
}
