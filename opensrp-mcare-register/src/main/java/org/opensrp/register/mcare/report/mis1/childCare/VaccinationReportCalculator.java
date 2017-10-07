package org.opensrp.register.mcare.report.mis1.childCare;

import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.domain.Vaccine;
import org.opensrp.register.mcare.report.mis1.ReportCalculator;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class VaccinationReportCalculator extends ReportCalculator{
    private long bcgCount = 0;

    public VaccinationReportCalculator(long startDateTime, long endDateTime) {
        super(startDateTime, endDateTime);
    }

    public long getBCGCount() {
        return bcgCount;
    }


    @Override
    public void calculate(Members member) {
        List<Vaccine> givenVaccines = getGivenVaccines(member);
        Vaccine bcg = new Vaccine(Vaccine.VaccineName.BCG, null);
        this.bcgCount += vaccineGiven(givenVaccines, bcg);
    }


    private List<Vaccine> getGivenVaccines(Members member) {
        List<Map<String, String>> childVaccineMap = member.child_vaccine();
        if(childVaccineMap != null) {
            for(Map<String, String> visit : childVaccineMap) {
                if(withInStartAndEndTime(visit)) {
                   return getVaccines(visit);
                }
            }
        }
        return Collections.EMPTY_LIST;
    }

    private long vaccineGiven(List<Vaccine> givenVaccines, Vaccine vaccine) {
        if(givenVaccines.contains(vaccine)) {
            return 1;
        }
        return 0;
    }

    private List<Vaccine> getVaccines(Map<String, String> visit) {
        if(visit.containsKey(Members.ChildVisitKeyValue.Key.VACCINE)) {
            String vaccinesStr = visit.get(Members.ChildVisitKeyValue.Key.VACCINE);
            return Vaccine.extractVaccinesFromStr(vaccinesStr);
        }else {
            return Collections.EMPTY_LIST;
        }
    }


}
