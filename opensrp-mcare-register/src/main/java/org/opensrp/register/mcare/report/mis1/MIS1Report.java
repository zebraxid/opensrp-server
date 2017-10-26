package org.opensrp.register.mcare.report.mis1;

import org.opensrp.connector.DHIS2.dxf2.DHIS2;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.domain.Vaccine;
import org.opensrp.register.mcare.report.mis1.birthAndDeath.BirthAndDeathReport;
import org.opensrp.register.mcare.report.mis1.childCare.ChildCareReport;
import org.opensrp.register.mcare.report.mis1.familyPlanning.FamilyPlanningReport;
import org.opensrp.register.mcare.report.mis1.maternityCare.MaternityCareReport;
import org.opensrp.register.mcare.report.mis1.nutrition.NutritionReport;

import java.util.List;

/**
 * Top level class for calculating MIS1Report.
 * Client init an object of this class using the targeted member list.
 * <br>
 * <b>For developers</b>
 * <br>
 * Targeted member list is looped through once in this top level class. All the internal.
 * <i>{@link org.opensrp.register.mcare.report.mis1.Report}</i> object operate on single <i>{@link org.opensrp.register.mcare.domain.Members}</i> object.
 * <br>
 * TODO: <br>
 * 1. Go through the code to understand underlying desing patterns. <br>
 * 2. Don't use raw strings. Update <i>{@link org.opensrp.register.mcare.domain.Members}</i> class with key value. e.g: {@link Vaccine.VaccineDose}. <br>
 * 3. Some report calculators are done partially e.g:{@link org.opensrp.register.mcare.report.mis1.maternityCare.ANCReportCalculator}. Others calculation can be done in same way. <br>
 * 4. Read description of SRS in alfresco about color code. Color code is not implemented fully.<br>
 * 5. Class diagram is not implemented fully. Members and Methods are not listed.
 * <br>
 * <b>For a general understanding, this class represents the complete page of SRS with all the table. Every Report object {@link FamilyPlanningReport} is an table of SRS.
 * Every Report calculator object can be thought of an row of the table {@link org.opensrp.register.mcare.report.mis1.familyPlanning.birthControlMethdoUsagesCalculation.CondomMethodUsagesCalculator}, {@link org.opensrp.register.mcare.report.mis1.maternityCare.TTDoseReportCalculator}.</b>
 * <br>
 *
 * @see <a href="http://doc.mpower-social.com:8080/share/page/document-details?nodeRef=workspace://SpacesStore/bb7b37bd-0d5a-4813-9903-33dce944b165">MIS1 Report SRS.</a>
 * <br>
 * @see <a href="https://go.gliffy.com/go/publish/12216901">Class diagram</a>
 */
public class MIS1Report {

    private String unionName;

    private List<Members> membersList;

    @DHIS2(dataSetId="Z5WPr2zconV")
    private FamilyPlanningReport familyPlanningReport;

    private MaternityCareReport maternityCareReport;

    private BirthAndDeathReport birthAndDeathReport;
    private ChildCareReport childCareReport;
    private NutritionReport nutritionReport;


    public MIS1Report(String unionName, List<Members> membersList, long startDateTime, long endDateTime) {
        this.unionName = unionName;
        this.membersList = membersList;
        this.familyPlanningReport = new FamilyPlanningReport(startDateTime, endDateTime);
        this.maternityCareReport = new MaternityCareReport(startDateTime, endDateTime);
        this.birthAndDeathReport = new BirthAndDeathReport(startDateTime, endDateTime);
        this.childCareReport = new ChildCareReport(startDateTime, endDateTime);
        this.nutritionReport = new NutritionReport(startDateTime, endDateTime);
        this.calculateReport();
    }

    public List<Members> getMembersList() {
        return membersList;
    }

    public void setMembersList(List<Members> membersList) {

        this.membersList = membersList;
    }

    public FamilyPlanningReport getFamilyPlanningReport() {

        return familyPlanningReport;
    }

    public MaternityCareReport getMaternityCareReport() {

        return maternityCareReport;
    }

    public BirthAndDeathReport getBirthAndDeathReport() {

        return birthAndDeathReport;
    }

    public ChildCareReport getChildCareReport() {
        return childCareReport;
    }


    /**
     * This function calls all member reports <i>calculate</i> method using single member.
     * <b>Should be called inside constructor.</b>
     */
    private void calculateReport() {
        for (Members member : membersList) {
            familyPlanningReport.calculate(member);
            maternityCareReport.calculate(member);
            birthAndDeathReport.calculate(member);
            childCareReport.calculate(member);
            nutritionReport.calculate(member);
        }
    }


    public NutritionReport getNutritionReport() {
        return nutritionReport;
    }
}
