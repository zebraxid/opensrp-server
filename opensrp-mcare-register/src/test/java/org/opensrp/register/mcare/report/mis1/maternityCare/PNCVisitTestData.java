package org.opensrp.register.mcare.report.mis1.maternityCare;


import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.MIS1TestData;
import org.opensrp.register.mcare.report.mis1.PNCVisit;

import java.util.ArrayList;
import java.util.List;

import static org.opensrp.register.mcare.domain.Members.PNCVisitKeyValue.*;
import static org.opensrp.register.mcare.domain.Members.PNCVisitKeyValue.VisitStatus;

public class PNCVisitTestData extends MIS1TestData{
    private PNCVisit.PNCVisitBuilder pncVisitBuilder;

    public PNCVisitTestData(int totalCount, int validCount, long startDateTime, long endDateTime) {
        super(totalCount, validCount, startDateTime, endDateTime);
        pncVisitBuilder = new PNCVisit.PNCVisitBuilder();
    }

   /* public List<Members> createPncVisit1InformationTestData() {
        List<Members> allMembers = new ArrayList<>();
        allMembers.add(createValidMemberWithStartDateTimeFor(VisitNumber.one));
        allMembers.add(createValidMemberWithEndDateTimeFor(VisitNumber.two));
        return allMembers
    }

    private Members createValidMemberWithStartDateTimeFor(VisitNumber visitNumber) {
        pncVisitBuilder.clientVersion(startDateTime).visitStatus(VisitStatus.MET_AND_BABY_ALIVE.toString());
        pncVisitBuilder.hasPncGivenOnTime(PNCGivenOnTime.YES.toString());
        return createMemberWithPNCVisit(visitNumber, pncVisitBuilder.build().getVisitData());
    }

    private Members createValidMemberWithEndDateTimeFor(VisitNumber visitNumber) {
        pncVisitBuilder.clientVersion(endDateTime).visitStatus(VisitStatus.MET_AND_WOMEN_HAD_STILLBIRTH.toString());
        pncVisitBuilder.hasPncGivenOnTime(PNCGivenOnTime.YES.toString());
        return createMemberWithPNCVisit(visitNumber, pncVisitBuilder.build().getVisitData());
    }*/
}
