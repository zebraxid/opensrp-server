package org.opensrp.register.mcare.report.mis1.childCare;

import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.domain.Members.DiseaseName;
import org.opensrp.register.mcare.report.mis1.ChildDiseaseProblem;
import org.opensrp.register.mcare.report.mis1.ChildVisit;
import org.opensrp.register.mcare.report.mis1.MIS1TestData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DiseaseProblemReportTestData extends MIS1TestData {
    private ChildDiseaseProblem.ChildDiseaseProblemVisitBuilder childDiseaseProblemVisitBuilder;
    private ChildVisit.ChildVisitBuilder childVisitBuilder;

    public DiseaseProblemReportTestData(int totalCount, int validCount, long startDateTime, long endDateTime) {
        super(totalCount, validCount, startDateTime, endDateTime);
        this.childDiseaseProblemVisitBuilder = new ChildDiseaseProblem.ChildDiseaseProblemVisitBuilder();
        this.childVisitBuilder = new ChildVisit.ChildVisitBuilder();
    }

    public List<Members> createVeryDangerousDiseasesTestData() {
        List<Members> members = new ArrayList<>();
        members.add(creteValidMemberWithStartDateTime(DiseaseName.OTHERS, DiseaseName.DIARRHEA));
        members.add(creteValidMemberWithEndDateTime(DiseaseName.OTHERS, DiseaseName.DIARRHEA));
        for (int i=0; i<totalCount; i++) {
            if(i<validCount-2) {
                members.add(createValidMemberFor(DiseaseName.OTHERS, DiseaseName.DIARRHEA));
            }else {
                members.add(createInvalidMemberFor(i,DiseaseName.OTHERS, DiseaseName.DIARRHEA));
            }
        }
        return members;
    }

    private Members createInvalidMemberFor(int index, DiseaseName inTestDiseases, DiseaseName otherDisease) {
        int i = index % 3;
        if(i == 0) {
            Long randomDateTimeBetweenStartAndEndDateTime = getRandomNumberBetween(endDateTime+1, endDateTime*3);
            childVisitBuilder.clientVersion(randomDateTimeBetweenStartAndEndDateTime);
            childVisitBuilder.disease(childDiseaseProblemVisitBuilder.disease(inTestDiseases).disease(otherDisease).build());
            return createMemberWithChildVaccine(Collections.singletonList(childVisitBuilder.build().getVisitData()));
        }else if(i == 1){
            Long randomDateTimeBetweenStartAndEndDateTime = getRandomNumberBetween(0, startDateTime-1);
            childVisitBuilder.clientVersion(randomDateTimeBetweenStartAndEndDateTime);
            childVisitBuilder.disease(childDiseaseProblemVisitBuilder.disease(inTestDiseases).disease(otherDisease).build());
            return createMemberWithChildVaccine(Collections.singletonList(childVisitBuilder.build().getVisitData()));
        }else {
            Long randomDateTimeBetweenStartAndEndDateTime = getRandomNumberBetween(startDateTime, endDateTime);
            childVisitBuilder.clientVersion(randomDateTimeBetweenStartAndEndDateTime);
            childVisitBuilder.disease(childDiseaseProblemVisitBuilder.disease(otherDisease).build());
            return createMemberWithChildVaccine(Collections.singletonList(childVisitBuilder.build().getVisitData()));
        }
    }

    private Members createValidMemberFor(DiseaseName inTestDiseases, DiseaseName otherDisease) {
        Long randomDateTimeBetweenStartAndEndDateTime = getRandomNumberBetween(startDateTime, endDateTime);
        childVisitBuilder.clientVersion(randomDateTimeBetweenStartAndEndDateTime);
        childVisitBuilder.disease(childDiseaseProblemVisitBuilder.disease(inTestDiseases).disease(otherDisease).build());
        return createMemberWithChildVaccine(Collections.singletonList(childVisitBuilder.build().getVisitData()));
    }

    private Members creteValidMemberWithEndDateTime(DiseaseName inTestDiseases, DiseaseName otherDisease) {
        childVisitBuilder.clientVersion(endDateTime);
        childVisitBuilder.disease(childDiseaseProblemVisitBuilder.disease(inTestDiseases).disease(otherDisease).build());
        return createMemberWithChildVaccine(Collections.singletonList(childVisitBuilder.build().getVisitData()));

    }

    private Members creteValidMemberWithStartDateTime(DiseaseName inTestDiseases, DiseaseName otherDisease) {
        childVisitBuilder.clientVersion(startDateTime);
        childVisitBuilder.disease(childDiseaseProblemVisitBuilder.disease(inTestDiseases).disease(otherDisease).build());
        return createMemberWithChildVaccine(Collections.singletonList(childVisitBuilder.build().getVisitData()));
    }
}
