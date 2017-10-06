package org.opensrp.register.mcare.report.mis1.childCare;

import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.MIS1TestData;
import org.opensrp.register.mcare.report.mis1.PNCVisit;

import java.util.ArrayList;
import java.util.List;

public class NewBornCareTestData extends MIS1TestData {
    private PNCVisit.PNCVisitBuilder pncVisitBuilder;

    public NewBornCareTestData(int totalCount, int validCount, long startDateTime, long endDateTime) {
        super(totalCount, validCount, startDateTime, endDateTime);
        pncVisitBuilder = new PNCVisit.PNCVisitBuilder();
    }

    public List<Members> createIsCleanOnPNCVisitTestData() {
        List<Members> allMembers = new ArrayList<>();
        for( VisitNumber visitNumber : VisitNumber.values()) {
            if(visitNumber != VisitNumber.INVALID) {
                allMembers.add(createValidMemberWithStartDateTimeFor(visitNumber));
                allMembers.add(createValidMemberWithEndDateTimeFor(visitNumber));
            }
        }

        for(int i=0; i<totalCount; i++) {
            if(i<validCount-8) {
                allMembers.add(createValidMemberForIsCleanedWithInStartAndEndDateTimeWith(VisitNumber.fromInt(i%4+1)));
            }else {
                if(i%3 == 0) {
                    allMembers.add(createInvalidMemberWithPreviousClientVersion(VisitNumber.fromInt(i%4+1)));
                }else if(i%3 == 1) {
                    allMembers.add(createInvalidMemberWithExceedClientVersion(VisitNumber.fromInt(i%4+1)));
                }
            }
        }
        return allMembers;
    }

    private Members createValidMemberWithStartDateTimeFor(VisitNumber visitNumber) {
        pncVisitBuilder.clientVersion(startDateTime).visitStatus(Members.PNCVisitKeyValue.VisitStatus.MET_AND_BABY_ALIVE);
        pncVisitBuilder.hasPncGivenOnTime(Members.BooleanAnswer.YES).isCleaned(Members.BooleanAnswer.YES);
        return createMemberWithPNCVisit(visitNumber, pncVisitBuilder.build().getVisitData());
    }

    private Members createValidMemberWithEndDateTimeFor(VisitNumber visitNumber) {
        pncVisitBuilder.clientVersion(endDateTime).visitStatus(Members.PNCVisitKeyValue.VisitStatus.MET_AND_WOMEN_HAD_STILLBIRTH);
        pncVisitBuilder.hasPncGivenOnTime(Members.BooleanAnswer.YES).isCleaned(Members.BooleanAnswer.YES);
        return createMemberWithPNCVisit(visitNumber, pncVisitBuilder.build().getVisitData());
    }

    private Members createValidMemberForIsCleanedWithInStartAndEndDateTimeWith(VisitNumber visitNumber) {
        long validClientVersion = getRandomNumberBetween(startDateTime, endDateTime);
        pncVisitBuilder.clientVersion(validClientVersion);
        pncVisitBuilder.isCleaned(Members.BooleanAnswer.YES);
        return createMemberWithPNCVisit(visitNumber, pncVisitBuilder.build().getVisitData());

    }

    private Members createInvalidMemberWithPreviousClientVersion(VisitNumber visitNumber) {
        long previousClientVersion = getRandomNumberBetween(0, startDateTime - 1);
        pncVisitBuilder.clientVersion(previousClientVersion);
        pncVisitBuilder.visitStatus(Members.PNCVisitKeyValue.VisitStatus.MET_AND_BABY_ALIVE);
        pncVisitBuilder.hasPncGivenOnTime(Members.BooleanAnswer.YES).isCleaned(Members.BooleanAnswer.YES);
        return createMemberWithPNCVisit(visitNumber, pncVisitBuilder.build().getVisitData());
    }

    private Members createInvalidMemberWithExceedClientVersion(VisitNumber visitNumber) {
        long previousClientVersion = getRandomNumberBetween(endDateTime + 1, endDateTime * 6);
        pncVisitBuilder.clientVersion(previousClientVersion);
        pncVisitBuilder.visitStatus(Members.PNCVisitKeyValue.VisitStatus.MET_AND_BABY_ALIVE);
        pncVisitBuilder.hasPncGivenOnTime(Members.BooleanAnswer.YES).isCleaned(Members.BooleanAnswer.YES);
        return createMemberWithPNCVisit(visitNumber, pncVisitBuilder.build().getVisitData());
    }
}
