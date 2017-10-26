    package org.opensrp.register.mcare.report.mis1.maternityCare;


import org.opensrp.connector.DHIS2.dxf2.DHIS2;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.ReportCalculator;

import java.util.List;
import java.util.Map;

import static org.opensrp.register.mcare.domain.Members.AdolescentHealthVisitKeyValue.*;

public class AdolescentHealthReportCalculator extends ReportCalculator {

    @DHIS2(dateElementId="owIgYD4tVO4",categoryOptionId="CzqFFc5xsJT",dataSetId="iVd5aTE9P1B")
    private long countOfCounsellingOnChangesOfAdolescent = 0;

    @DHIS2(dateElementId="owIgYD4tVO4",categoryOptionId="Z8LnHwmNSHQ",dataSetId="iVd5aTE9P1B")
    private long countOfCounsellingBadEffectOnChildMarriageAndTeenPregnancy = 0;

    @DHIS2(dateElementId="owIgYD4tVO4",categoryOptionId="KSBm7EWOgDY",dataSetId="iVd5aTE9P1B")
    private long countOfCounsellingTeenageGirlsOnTakingIronAndFolicAcid = 0;

    @DHIS2(dateElementId="owIgYD4tVO4",categoryOptionId="BfyPoGtghkP",dataSetId="iVd5aTE9P1B")
    private long countOfCounsellingOnInfectionOfGenitialsAndSexuallyTransmittedDiseases = 0;

    public AdolescentHealthReportCalculator(long startDateTime, long endDateTime) {
        super(startDateTime, endDateTime);
    }

    public long getCountOfCounsellingOnChangesOfAdolescent() {
        return countOfCounsellingOnChangesOfAdolescent;
    }

    public long getCountOfCounsellingBadEffectOnChildMarriageAndTeenPregnancy() {
        return countOfCounsellingBadEffectOnChildMarriageAndTeenPregnancy;
    }

    public long getCountOfCounsellingTeenageGirlsOnTakingIronAndFolicAcid() {
        return countOfCounsellingTeenageGirlsOnTakingIronAndFolicAcid;
    }

    public long getCountOfCounsellingOnInfectionOfGenitialsAndSexuallyTransmittedDiseases() {
        return countOfCounsellingOnInfectionOfGenitialsAndSexuallyTransmittedDiseases;
    }

    @Override
    public void calculate(Members member) {
        List<Map<String, String>> visits = member.adolescent();
        for(Map<String , String> visit: visits) {
            if(withInStartAndEndTime(visit)) {
                countOfCounsellingOnChangesOfAdolescent += addToCountFor(visit, CounsellingType.ON_ADOLESCENT);
                countOfCounsellingBadEffectOnChildMarriageAndTeenPregnancy += addToCountFor(visit, CounsellingType.BAD_EFFECT_OF_CHILD_MARRIAGE_AND_TEENAGE_MOTHER);
                countOfCounsellingTeenageGirlsOnTakingIronAndFolicAcid += addToCountFor(visit, CounsellingType.TAKING_IRON_AND_FOLIC_ACID);
                countOfCounsellingOnInfectionOfGenitialsAndSexuallyTransmittedDiseases += addToCountFor(visit, CounsellingType.SEX_ORGAN_INFECTION_AND_SEXUALLY_TRANSMITTED_DISEASES);
            }
        }
    }

    public int addToCountFor(Map<String, String> visitData, CounsellingType counsellingType) {
        String counsellingTypeStr = visitData.get(Key.COUNSELLING);
        List<CounsellingType> counsellingTypeList = CounsellingType.extractCounsellingTypeListFrom(counsellingTypeStr);
        for (CounsellingType type: counsellingTypeList) {
            if (type == counsellingType) {
                return 1;
            }
        }

        return 0;
    }

}
