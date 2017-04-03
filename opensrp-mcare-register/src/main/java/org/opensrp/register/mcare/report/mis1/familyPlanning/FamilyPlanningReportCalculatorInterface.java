package org.opensrp.register.mcare.report.mis1.familyPlanning;


public interface FamilyPlanningReportCalculatorInterface {
    public int totalUsages();
    public int newUsages();
    public int unitTotal();
    public int leftUsagesButTakenNone();
    public int leftUsagesButTakenOther();
}
