package org.opensrp.register.mcare.report.mis1.childCare;

import org.opensrp.connector.DHIS2.dxf2.DHIS2;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.domain.Vaccine;
import org.opensrp.register.mcare.report.mis1.ReportCalculator;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class VaccinationReportCalculator extends ReportCalculator{

    @DHIS2(dataElementId ="C7dBDNkUwIF",categoryOptionId="IMxc771Kd54",dataSetId="Vc8Ps0P84hZ")
    private long bcgCount = 0;

    @DHIS2(dataElementId ="C7dBDNkUwIF",categoryOptionId="qLFls93A9pf",dataSetId="Vc8Ps0P84hZ")
    private long opv1Andpcv1Andpenta1Count = 0;

    @DHIS2(dataElementId ="C7dBDNkUwIF",categoryOptionId="rOHlNDOjCCd",dataSetId="Vc8Ps0P84hZ")
    private long opv2Andpcv2Andpenta2Count = 0;

    @DHIS2(dataElementId ="C7dBDNkUwIF",categoryOptionId="kLTdVFqLpBq",dataSetId="Vc8Ps0P84hZ")
    private long opv3Andpenta3Count = 0;

    @DHIS2(dataElementId ="C7dBDNkUwIF",categoryOptionId="DuKOJDReMQm",dataSetId="Vc8Ps0P84hZ")
    private long pcv3Count = 0;

    public VaccinationReportCalculator(long startDateTime, long endDateTime) {
        super(startDateTime, endDateTime);
    }

    public long getBcgCount() {
        return bcgCount;
    }

    public long getOpv1Andpcv1Andpenta1Count() {

        return opv1Andpcv1Andpenta1Count;
    }

    public long getOpv2Andpcv2Andpenta2Count() {
        return opv2Andpcv2Andpenta2Count;
    }

    public long getOpv3Andpenta3Count() {

        return opv3Andpenta3Count;
    }

    public long getPcv3Count() {
        return pcv3Count;
    }

    @Override
    public void calculate(Members member) {
        List<Vaccine> givenVaccines = getGivenVaccines(member);
        Vaccine bcg = new Vaccine(Vaccine.VaccineName.BCG, null);
        Vaccine pcv1 = new Vaccine(Vaccine.VaccineName.PCV, Vaccine.VaccineDose.ONE);
        Vaccine pcv2 = new Vaccine(Vaccine.VaccineName.PCV, Vaccine.VaccineDose.TWO);
        Vaccine pcv3 = new Vaccine(Vaccine.VaccineName.PCV, Vaccine.VaccineDose.THREE);
        Vaccine opv1 = new Vaccine(Vaccine.VaccineName.OPV, Vaccine.VaccineDose.ONE);
        Vaccine opv2 = new Vaccine(Vaccine.VaccineName.OPV, Vaccine.VaccineDose.TWO);
        Vaccine opv3 = new Vaccine(Vaccine.VaccineName.OPV, Vaccine.VaccineDose.THREE);
        Vaccine penta1 = new Vaccine(Vaccine.VaccineName.PENTA, Vaccine.VaccineDose.ONE);
        Vaccine penta2 = new Vaccine(Vaccine.VaccineName.PENTA, Vaccine.VaccineDose.TWO);
        Vaccine penta3 = new Vaccine(Vaccine.VaccineName.PENTA, Vaccine.VaccineDose.THREE);


        this.bcgCount += vaccineGiven(givenVaccines, bcg);
        this.opv1Andpcv1Andpenta1Count += vaccineGiven(givenVaccines, asList(opv1, pcv1, penta1));
        this.opv2Andpcv2Andpenta2Count += vaccineGiven(givenVaccines, asList(opv2, pcv2, penta2));
        this.opv3Andpenta3Count += vaccineGiven(givenVaccines, asList(opv3, penta3));
        this.pcv3Count += vaccineGiven(givenVaccines, pcv3);
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

    private long vaccineGiven(List<Vaccine> givenVaccines, List<Vaccine> vaccine) {
        if(givenVaccines.containsAll(vaccine)) {
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
