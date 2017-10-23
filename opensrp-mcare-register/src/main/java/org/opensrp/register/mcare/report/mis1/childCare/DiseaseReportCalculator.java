package org.opensrp.register.mcare.report.mis1.childCare;

import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.domain.Members.DiseaseName;
import org.opensrp.register.mcare.report.mis1.ReportCalculator;

import java.util.List;
import java.util.Map;

public class DiseaseReportCalculator extends ReportCalculator{

    private long veryDangerousDiseasesCount = 0;
    private long pneumoniaCount = 0;
    private long diarrheaCount = 0;

    public DiseaseReportCalculator(long startDateTime, long endDateTime) {
        super(startDateTime, endDateTime);
    }

    public long getVeryDangerousDiseasesCount() {
        return veryDangerousDiseasesCount;
    }

    public long getPneumoniaCount() {
        return pneumoniaCount;
    }

    public long getDiarrheaCount() {
        return diarrheaCount;
    }

    @Override
    public void calculate(Members member) {
        List<Map<String, String>> childVisits = member.child_vaccine();
        for (Map<String, String> childVisit : childVisits) {
            if (withInStartAndEndTime(childVisit)) {
                String diseaseStr = getDiseaseString(childVisit);
                if (validDiseaseString(diseaseStr)) {
                    List<DiseaseName> vaccineDoses = DiseaseName.extractDiseaseListFrom(diseaseStr);
                    addToCount(vaccineDoses);
                }
            }
        }
    }

    private String getDiseaseString(Map<String, String> visitData) {
        if (visitData.containsKey(Members.ChildVisitKeyValue.Key.DISEASE_PROBLEM)) {
            return visitData.get(Members.ChildVisitKeyValue.Key.DISEASE_PROBLEM);
        }
        return "";
    }

    private boolean validDiseaseString(String ttDoses) {
        return ttDoses != null && !ttDoses.isEmpty();
    }



    private void addToCount(List<DiseaseName> diseaseNames) {
        for(DiseaseName diseaseName: diseaseNames) {
            switch (diseaseName) {
                case OTHERS:
                    veryDangerousDiseasesCount ++;
                    break;
                case PNEUMONIA:
                    pneumoniaCount++;
                case DIARRHEA:
                    diarrheaCount++;
                    break;
            }
        }
    }


}
