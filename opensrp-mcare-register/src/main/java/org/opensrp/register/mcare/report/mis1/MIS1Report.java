package org.opensrp.register.mcare.report.mis1;


import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.familyPlanning.FamilyPlanningReport;

import java.util.List;

public class MIS1Report {
    private List<Members> membersList;
    private FamilyPlanningReport familyPlanningReport;
    private long startDateTime;
    private long endDateTime;

    public MIS1Report(List<Members> membersList, long startDateTime, long endDateTime) {
        this.membersList = membersList;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        familyPlanningReport = new FamilyPlanningReport();
        this.calculateReport();
    }

    public FamilyPlanningReport getFamilyPlanningReport() {
        return familyPlanningReport;
    }

    private void calculateReport() {
        for (Members member : membersList) {
            familyPlanningReport.calculate(member);
        }
    }
}
