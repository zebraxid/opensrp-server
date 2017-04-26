package org.opensrp.register.mcare.report.mis1.maternityCare;


import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.ANCVisit;
import org.opensrp.register.mcare.report.mis1.MIS1TestData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ANCVisitTestData extends MIS1TestData {

    private ANCVisit.ANCVisitBuilder builder;

    public ANCVisitTestData(int totalCount, int validCount, long startDateTime, long endDateTime) {
        super(totalCount, validCount, startDateTime, endDateTime);
        this.builder = new ANCVisit.ANCVisitBuilder();
    }


    public List<Members> createANCVisit1TestData() {
        List<Members> allMembers = new ArrayList<>();

        allMembers.add(createValidMemberWithStartDateTime(VisitNumber.one));
        allMembers.add(createValidMemberWithEndDateTime(VisitNumber.one));

        for (int i = 0; i < totalCount; i++) {
            if (i < validCount - 2) {
                allMembers.add(createValidMemberWithRandomDateTime(VisitNumber.one));
            } else {
                allMembers.add(createInValidMemberWithRandomDateTime(VisitNumber.one));
            }

        }
        return allMembers;
    }

    private Members createValidMemberWithStartDateTime(VisitNumber visitNumber) {
        builder.clientVersion(startDateTime);
        return createMemberWithANCVisit(visitNumber, builder.build().getVisitData());
    }

    private Members createValidMemberWithEndDateTime(VisitNumber visitNumber) {
        builder.clientVersion(endDateTime);
        return createMemberWithANCVisit(visitNumber, builder.build().getVisitData());
    }

    private Members createValidMemberWithRandomDateTime(VisitNumber visitNumber) {
        Long randomDateTimeBetweenStartAndEndDateTime = getRandomNumberBetween(startDateTime, endDateTime);
        builder.clientVersion(randomDateTimeBetweenStartAndEndDateTime);
        return createMemberWithANCVisit(visitNumber, builder.build().getVisitData());
    }

    private Members createInValidMemberWithRandomDateTime(VisitNumber visitNumber) {
        int random = getRandomNumberBetween(1, 100);
        long invalidDataTime;
        if(random % 2 == 0) {
            invalidDataTime = getRandomNumberBetween(0, startDateTime-1);
        }else {
            invalidDataTime = getRandomNumberBetween(endDateTime+1, endDateTime*5);
        }
        builder.clientVersion(invalidDataTime);
        return createMemberWithANCVisit(visitNumber, builder.build().getVisitData());
    }


}
