package org.opensrp.register.mcare.report.mis1.maternityCare;


import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.ANCVisit;
import org.opensrp.register.mcare.report.mis1.MIS1TestData;

import java.util.ArrayList;
import java.util.List;

import static org.opensrp.register.mcare.domain.Members.BooleanAnswer.*;

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

    public List<Members> createANCVisit2TestData() {
        List<Members> allMembers = new ArrayList<>();

        allMembers.add(createValidMemberWithStartDateTime(VisitNumber.two));
        allMembers.add(createValidMemberWithEndDateTime(VisitNumber.two));

        for (int i = 0; i < totalCount; i++) {
            if (i < validCount - 2) {
                allMembers.add(createValidMemberWithRandomDateTime(VisitNumber.two));
            } else {
                allMembers.add(createInValidMemberWithRandomDateTime(VisitNumber.two));
            }

        }
        return allMembers;
    }

    public List<Members> createANCVisit3TestData() {
        List<Members> allMembers = new ArrayList<>();

        allMembers.add(createValidMemberWithStartDateTime(VisitNumber.three));
        allMembers.add(createValidMemberWithEndDateTime(VisitNumber.three));

        for (int i = 0; i < totalCount; i++) {
            if (i < validCount - 2) {
                allMembers.add(createValidMemberWithRandomDateTime(VisitNumber.three));
            } else {
                allMembers.add(createInValidMemberWithRandomDateTime(VisitNumber.three));
            }

        }
        return allMembers;
    }

    public List<Members> createANCVisit4TestData() {
        List<Members> allMembers = new ArrayList<>();

        allMembers.add(createValidMemberWithStartDateTime(VisitNumber.four));
        allMembers.add(createValidMemberWithEndDateTime(VisitNumber.four));

        for (int i = 0; i < totalCount; i++) {
            if (i < validCount - 2) {
                allMembers.add(createValidMemberWithRandomDateTime(VisitNumber.four));
            } else {
                allMembers.add(createInValidMemberWithRandomDateTime(VisitNumber.four));
            }

        }
        return allMembers;
    }

    public List<Members> createAFCVisitIsReferredData() {
        List<Members> allMembers = new ArrayList<>();



        for (int i = 0; i < totalCount; i++) {
            if (i < validCount - 2) {
                allMembers.add(createValidMemberWithRandomDateTime(VisitNumber.four));
            } else {
                allMembers.add(createInValidMemberWithRandomDateTime(VisitNumber.four));
            }

        }
        return allMembers;
    }

    private Members createValidMemberWithStartDateTime(VisitNumber visitNumber) {
        builder.clientVersion(startDateTime).isReferred(YES.getValueInString());
        return createMemberWithANCVisit(visitNumber, builder.build().getVisitData());
    }

    private Members createValidMemberWithEndDateTime(VisitNumber visitNumber) {
        builder.clientVersion(endDateTime).isReferred(YES.getValueInString());
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
