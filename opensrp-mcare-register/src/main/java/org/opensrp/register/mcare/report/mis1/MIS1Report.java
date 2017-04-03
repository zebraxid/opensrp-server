package org.opensrp.register.mcare.report.mis1;


import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.familyPlanning.FamilyPlanningReport;

import java.util.List;

public class MIS1Report {
    private  List<Members> membersList;
    private FamilyPlanningReport familyPlanningReport;

    public MIS1Report(List<Members> membersList) {
        this.membersList = membersList;
        familyPlanningReport = new FamilyPlanningReport();
        this.calculateReport();
    }

    public FamilyPlanningReport getFamilyPlanningReport() {
        return familyPlanningReport;
    }

    private void calculateReport() {
        for(Members member : membersList) {
            familyPlanningReport.calculate(member);
        }
    }
}
