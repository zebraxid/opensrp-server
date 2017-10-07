package org.opensrp.register.mcare.report.mis1.childCare;

import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.domain.Vaccine;
import org.opensrp.register.mcare.report.mis1.ChildVaccine;
import org.opensrp.register.mcare.report.mis1.MIS1TestData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VaccineCountTestData extends MIS1TestData {
    private ChildVaccine.ChildVaccineVisitBuilder childVaccineVisitBuilder;

    public VaccineCountTestData(int totalCount, int validCount, long startDateTime, long endDateTime) {
        super(totalCount, validCount, startDateTime, endDateTime);
        this.childVaccineVisitBuilder = new ChildVaccine.ChildVaccineVisitBuilder();
    }

    public List<Members> createBCGVaccineTestDate() {
        Vaccine BCG = new Vaccine(Vaccine.VaccineName.BCG, null);
        Vaccine penta1= new Vaccine( Vaccine.VaccineName.PENTA, Vaccine.VaccineDose.ONE);
        List<Members> members = new ArrayList<>();
        members.add(creteValidMemberWithStartDateTime(BCG, penta1));
        members.add(creteValidMemberWithEndDateTime(BCG, penta1));
        for (int i=0; i<totalCount; i++) {
            if(i<validCount-2) {
                members.add(createValidMemberFor(BCG,penta1));
            }else {
                members.add(createInvalidMemberFor(i,BCG, penta1));
            }
        }
        return members;
    }

    private Members createInvalidMemberFor(int index, Vaccine inTestVaccine, Vaccine otherVaccine) {
        int i = index % 3;
        if(i == 0) {
            Long randomDateTimeBetweenStartAndEndDateTime = getRandomNumberBetween(endDateTime+1, endDateTime*3);
            childVaccineVisitBuilder.clientVersion(randomDateTimeBetweenStartAndEndDateTime).vaccine(inTestVaccine).vaccine(otherVaccine);
            return createMemberWithChildVaccine(Collections.singletonList(childVaccineVisitBuilder.build().getVisitData()));
        }else if(i == 1){
            Long randomDateTimeBetweenStartAndEndDateTime = getRandomNumberBetween(0, startDateTime-1);
            childVaccineVisitBuilder.clientVersion(randomDateTimeBetweenStartAndEndDateTime).vaccine(inTestVaccine).vaccine(otherVaccine);
            return createMemberWithChildVaccine(Collections.singletonList(childVaccineVisitBuilder.build().getVisitData()));
        }else {
            Long randomDateTimeBetweenStartAndEndDateTime = getRandomNumberBetween(startDateTime, endDateTime);
            childVaccineVisitBuilder.clientVersion(randomDateTimeBetweenStartAndEndDateTime).vaccine(otherVaccine);
            return createMemberWithChildVaccine(Collections.singletonList(childVaccineVisitBuilder.build().getVisitData()));
        }
    }

    private Members createValidMemberFor(Vaccine inTestVaccine, Vaccine otherVaccine) {
        Long randomDateTimeBetweenStartAndEndDateTime = getRandomNumberBetween(startDateTime, endDateTime);
        childVaccineVisitBuilder.clientVersion(randomDateTimeBetweenStartAndEndDateTime).vaccine(inTestVaccine).vaccine(otherVaccine);
        return createMemberWithChildVaccine(Collections.singletonList(childVaccineVisitBuilder.build().getVisitData()));
    }

    private Members creteValidMemberWithEndDateTime(Vaccine inTestVaccine, Vaccine otherVaccine) {
        childVaccineVisitBuilder.clientVersion(endDateTime).vaccine(inTestVaccine).vaccine(otherVaccine);
        return createMemberWithChildVaccine(Collections.singletonList(childVaccineVisitBuilder.build().getVisitData()));

    }

    private Members creteValidMemberWithStartDateTime(Vaccine inTestVaccine, Vaccine otherVaccine) {
        childVaccineVisitBuilder.clientVersion(startDateTime).vaccine(inTestVaccine).vaccine(otherVaccine);
        return createMemberWithChildVaccine(Collections.singletonList(childVaccineVisitBuilder.build().getVisitData()));
    }
}
