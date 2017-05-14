package org.opensrp.register.mcare.report.mis1.maternityCare;


import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.MIS1TestData;
import org.opensrp.register.mcare.report.mis1.PNCVisit;

import java.util.ArrayList;
import java.util.List;

import static org.opensrp.register.mcare.domain.Members.PNCVisitKeyValue.PNCGivenOnTime;
import static org.opensrp.register.mcare.domain.Members.PNCVisitKeyValue.VisitStatus;

public class PNCVisitTestData extends MIS1TestData {
    private PNCVisit.PNCVisitBuilder pncVisitBuilder;

    public PNCVisitTestData(int totalCount, int validCount, long startDateTime, long endDateTime) {
        super(totalCount, validCount, startDateTime, endDateTime);
        pncVisitBuilder = new PNCVisit.PNCVisitBuilder();
    }

    public List<Members> createPncVisit1TestData() {
        List<Members> allMembers = new ArrayList<>();
        allMembers.add(createValidMemberWithStartDateTimeFor(VisitNumber.one));
        allMembers.add(createValidMemberWithEndDateTimeFor(VisitNumber.one));

        for (int i = 0; i < totalCount; i++) {
            if (i < validCount - 2) {
                allMembers.add(createValidMemberWithInStartAndEndDateTimeWith(VisitNumber.one));
            } else {
                if (i % 3 == 0) {
                    allMembers.add(createInvalidMemberWithPreviousClientVersion(VisitNumber.one));
                } else if (i % 3 == 1) {
                    allMembers.add(createInvalidMemberWithExceedClientVersion(VisitNumber.one));
                } else {
                    allMembers.add(createInvalidMemberWithInStartAndEndDateTimeWith(VisitNumber.one));
                }
            }
        }
        return allMembers;
    }

    private Members createValidMemberWithStartDateTimeFor(VisitNumber visitNumber) {
        pncVisitBuilder.clientVersion(startDateTime).visitStatus(VisitStatus.MET_AND_BABY_ALIVE.getValue().toString());
        pncVisitBuilder.hasPncGivenOnTime(PNCGivenOnTime.YES.getValue().toString());
        return createMemberWithPNCVisit(visitNumber, pncVisitBuilder.build().getVisitData());
    }

    private Members createValidMemberWithEndDateTimeFor(VisitNumber visitNumber) {
        pncVisitBuilder.clientVersion(endDateTime).visitStatus(VisitStatus.MET_AND_WOMEN_HAD_STILLBIRTH.getValue().toString());
        pncVisitBuilder.hasPncGivenOnTime(PNCGivenOnTime.YES.getValue().toString());
        return createMemberWithPNCVisit(visitNumber, pncVisitBuilder.build().getVisitData());
    }

    private Members createInvalidMemberWithPreviousClientVersion(VisitNumber visitNumber) {
        long previousClientVersion = getRandomNumberBetween(0, startDateTime - 1);
        pncVisitBuilder.clientVersion(previousClientVersion);
        pncVisitBuilder.visitStatus(VisitStatus.MET_AND_BABY_ALIVE.getValue().toString());
        pncVisitBuilder.hasPncGivenOnTime(PNCGivenOnTime.YES.getValue().toString());
        return createMemberWithPNCVisit(visitNumber, pncVisitBuilder.build().getVisitData());
    }

    private Members createInvalidMemberWithExceedClientVersion(VisitNumber visitNumber) {
        long previousClientVersion = getRandomNumberBetween(endDateTime + 1, endDateTime * 6);
        pncVisitBuilder.clientVersion(previousClientVersion);
        pncVisitBuilder.visitStatus(VisitStatus.MET_AND_BABY_ALIVE.getValue().toString());
        pncVisitBuilder.hasPncGivenOnTime(PNCGivenOnTime.YES.getValue().toString());
        return createMemberWithPNCVisit(visitNumber, pncVisitBuilder.build().getVisitData());
    }

    private Members createValidMemberWithInStartAndEndDateTimeWith(VisitNumber visitNumber) {
        long validClientVersion = getRandomNumberBetween(startDateTime, endDateTime);
        long randNumber = getRandomNumberBetween(0, 10);
        if (randNumber % 2 == 0) {
            pncVisitBuilder.clientVersion(validClientVersion);
            pncVisitBuilder.visitStatus(VisitStatus.MET_AND_BABY_ALIVE.getValue().toString());
            pncVisitBuilder.hasPncGivenOnTime(PNCGivenOnTime.YES.getValue().toString());
            return createMemberWithPNCVisit(visitNumber, pncVisitBuilder.build().getVisitData());
        } else {
            pncVisitBuilder.clientVersion(validClientVersion);
            pncVisitBuilder.visitStatus(VisitStatus.MET_AND_WOMEN_HAD_STILLBIRTH.getValue().toString());
            pncVisitBuilder.hasPncGivenOnTime(PNCGivenOnTime.YES.getValue().toString());
            return createMemberWithPNCVisit(visitNumber, pncVisitBuilder.build().getVisitData());
        }
    }

    private Members createInvalidMemberWithInStartAndEndDateTimeWith(VisitNumber visitNumber) {
        long validClientVersion = getRandomNumberBetween(startDateTime, endDateTime);
        pncVisitBuilder.clientVersion(validClientVersion);
        pncVisitBuilder.visitStatus(VisitStatus.REFUSED.getValue().toString());
        pncVisitBuilder.hasPncGivenOnTime(PNCGivenOnTime.NO.getValue().toString());
        return createMemberWithPNCVisit(visitNumber, pncVisitBuilder.build().getVisitData());

    }
}
