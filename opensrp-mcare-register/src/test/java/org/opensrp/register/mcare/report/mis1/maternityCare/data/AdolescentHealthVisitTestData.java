package org.opensrp.register.mcare.report.mis1.maternityCare.data;


import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.MIS1TestData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.opensrp.register.mcare.domain.Members.AdolescentHealthVisitKeyValue.CounsellingType;
import static org.opensrp.register.mcare.report.mis1.AdolescentHealthVisit.AdolescentHealthVisitBuilder;
import static org.opensrp.register.mcare.report.mis1.AdolescentHealthVisit.CounsellingBuilder;

public class AdolescentHealthVisitTestData extends MIS1TestData {

    private AdolescentHealthVisitBuilder adolescentHealthVisitBuilder;
    private CounsellingBuilder counsellingBuilder;

    public AdolescentHealthVisitTestData(int totalCount, int validCount, long startDateTime, long endDateTime) {
        super(totalCount, validCount, startDateTime, endDateTime);
        this.adolescentHealthVisitBuilder = new AdolescentHealthVisitBuilder();
        this.counsellingBuilder = new CounsellingBuilder();
    }


    public List<Members> createCountOfCounsellingOnChangesOfAdolescentTestData() {
        List<Members> allMembers = new ArrayList<>();

        allMembers.add(createValidMemberWithStartDateTime());
        allMembers.add(createValidMemberWithEndDateTime());
        allMembers.add(createValidMemberWithTripleAdolescentVisit());


        for (int i=0; i<totalCount; i++) {

            if(i<validCount-5) {
                allMembers.add(createValidMemberWith(CounsellingType.ON_ADOLESCENT));
            }else {
                allMembers.add(createInvalidMemberWithValidDateTimeExcept(CounsellingType.ON_ADOLESCENT));
            }

        }

        return allMembers;
    }

    public List<Members> createCountOfCounsellingOnBadEffectOnChildMarriageAnTeenPregnancy() {
        List<Members> allMembers = new ArrayList<>();

        allMembers.add(createValidMemberWithStartDateTime());
        allMembers.add(createValidMemberWithEndDateTime());
        allMembers.add(createValidMemberWithTripleAdolescentVisit());


        for (int i=0; i<totalCount; i++) {

            CounsellingType badEffectOfChildMarriageAndTeenageMother = CounsellingType.BAD_EFFECT_OF_CHILD_MARRIAGE_AND_TEENAGE_MOTHER;
            if(i<validCount-5) {
                allMembers.add(createValidMemberWith(badEffectOfChildMarriageAndTeenageMother));
            }else {
                allMembers.add(createInvalidMemberWithValidDateTimeExcept(badEffectOfChildMarriageAndTeenageMother));
            }

        }

        return allMembers;
    }

    private Members createValidMemberWith(CounsellingType counsellingType) {
        List<Map<String, String>> visits = new ArrayList<>();
        visits.addAll(crateRandomNumberOfInvalidAdolescentVisit());
        long clientVersion = getRandomNumberBetween(startDateTime, endDateTime);
        counsellingBuilder.add(counsellingType);
        adolescentHealthVisitBuilder.clientVersion(clientVersion).counselling(counsellingBuilder.buildAndClean());
        visits.add(adolescentHealthVisitBuilder.build().getVisitData());
        return createMemberWithAdolescentHealthVisits(visits);
    }
    private Members createInvalidMemberWithValidDateTimeExcept(CounsellingType counsellingType) {
        for(CounsellingType type : CounsellingType.values()) {
            if (counsellingType != type) {
                counsellingBuilder.add(type);
            }
        }
        adolescentHealthVisitBuilder.clientVersion(getRandomNumberBetween(startDateTime, endDateTime)).counselling(counsellingBuilder.buildAndClean());
        List<Map<String, String>> adolescentVisits = new ArrayList<>();
        adolescentVisits.addAll(crateRandomNumberOfInvalidAdolescentVisit());
        adolescentVisits.add(adolescentHealthVisitBuilder.build().getVisitData());
        return createMemberWithAdolescentHealthVisits(adolescentVisits);
    }

    private Members createValidMemberWithStartDateTime() {
        addAllTypeOfCounsellingToBuilder();
        adolescentHealthVisitBuilder.clientVersion(startDateTime).counselling(counsellingBuilder.buildAndClean());
        List<Map<String, String>> adolescentVisits = new ArrayList<>();
        adolescentVisits.add(adolescentHealthVisitBuilder.build().getVisitData());
        return createMemberWithAdolescentHealthVisits(adolescentVisits);
    }

    private Members createValidMemberWithEndDateTime() {
        addAllTypeOfCounsellingToBuilder();
        adolescentHealthVisitBuilder.clientVersion(endDateTime).counselling(counsellingBuilder.buildAndClean());
        List<Map<String, String>> adolescentVisits = new ArrayList<>();
        adolescentVisits.add(adolescentHealthVisitBuilder.build().getVisitData());
        return createMemberWithAdolescentHealthVisits(adolescentVisits);
    }

    private Members createValidMemberWithTripleAdolescentVisit() {
        List<Map<String, String>> adolescentVisits = new ArrayList<>();

        addAllTypeOfCounsellingToBuilder();
        adolescentHealthVisitBuilder.clientVersion(startDateTime).counselling(counsellingBuilder.buildAndClean());
        adolescentVisits.add(adolescentHealthVisitBuilder.build().getVisitData());

        addAllTypeOfCounsellingToBuilder();
        adolescentHealthVisitBuilder.clientVersion(startDateTime).counselling(counsellingBuilder.buildAndClean());
        adolescentVisits.add(adolescentHealthVisitBuilder.build().getVisitData());

        addAllTypeOfCounsellingToBuilder();
        adolescentHealthVisitBuilder.clientVersion(startDateTime).counselling(counsellingBuilder.buildAndClean());
        adolescentVisits.add(adolescentHealthVisitBuilder.build().getVisitData());

        return createMemberWithAdolescentHealthVisits(adolescentVisits);
    }

    private List<Map<String, String>> crateRandomNumberOfInvalidAdolescentVisit() {
        List<Map<String, String>> visits = new ArrayList<>();
        int total = getRandomNumberBetween(0, 1000);
        long clientVersion = 0;
        for (int i = 0; i < total; i++) {
            if (i % 3 == 0) {
                clientVersion = getRandomNumberBetween(startDateTime, endDateTime);
                adolescentHealthVisitBuilder.clientVersion(clientVersion);
                visits.add(adolescentHealthVisitBuilder.build().getVisitData());
            } else {
                if (i % 3 == 1) {
                    clientVersion = getRandomNumberBetween(0, startDateTime - 1);
                } else {
                    clientVersion = getRandomNumberBetween(endDateTime + 1, endDateTime * 4);
                }
                adolescentHealthVisitBuilder.clientVersion(clientVersion);
                addAllTypeOfCounsellingToBuilder();
                adolescentHealthVisitBuilder.counselling(counsellingBuilder.buildAndClean());
                visits.add(adolescentHealthVisitBuilder.build().getVisitData());
            }
        }

        return visits;
    }

    private void addAllTypeOfCounsellingToBuilder() {
        counsellingBuilder.badEffectOfChildMarriageAndTeenageMother().cleannessAndComplexityOfMenstruation().
                eatingIronAndBalancedDiet().eatingIronAndBalancedDiet().
                sexOrganInfectionAndSexuallyTransmittedDiseases().takingIronAndFolicAcid().onAdolescent();
    }
}
