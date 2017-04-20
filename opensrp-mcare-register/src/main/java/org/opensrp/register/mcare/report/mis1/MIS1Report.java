package org.opensrp.register.mcare.report.mis1;


import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.familyPlanning.FamilyPlanningReport;
import org.opensrp.register.mcare.report.mis1.maternityCare.MaternityCareReport;

import java.util.List;

public class MIS1Report {
    private String unionName;
    private List<Members> membersList;
    private FamilyPlanningReport familyPlanningReport;
    private MaternityCareReport maternityCareReport;
    private long startDateTime;
    private long endDateTime;

    public MIS1Report(String unionName, List<Members> membersList, long startDateTime, long endDateTime) {
        this.unionName = unionName;
        this.membersList = membersList;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.familyPlanningReport = new FamilyPlanningReport(startDateTime, endDateTime);
        this.maternityCareReport = new MaternityCareReport(startDateTime, endDateTime);
        this.calculateReport();
    }

    public FamilyPlanningReport getFamilyPlanningReport() {
        return familyPlanningReport;
    }

    public MaternityCareReport getMaternityCareReport() {
        return maternityCareReport;
    }

    private void calculateReport() {
        for (Members member : membersList) {
            familyPlanningReport.calculate(member);
            maternityCareReport.calculate(member);
        }
    }
}
