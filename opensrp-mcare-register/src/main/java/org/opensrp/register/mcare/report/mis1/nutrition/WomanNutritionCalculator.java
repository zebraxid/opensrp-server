package org.opensrp.register.mcare.report.mis1.nutrition;

import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.domain.Members.NutritionVisitKeyValue.WomanNutritionService;
import org.opensrp.register.mcare.report.mis1.ReportCalculator;

import java.util.List;
import java.util.Map;

public class WomanNutritionCalculator extends ReportCalculator{

    private long countOfCounsellingOnFolicAcidAndIron = 0;

    public WomanNutritionCalculator(long startDateTime, long endDateTime) {
        super(startDateTime, endDateTime);
    }

    public long getCountOfCounsellingOnFolicAcidAndIron() {
        return countOfCounsellingOnFolicAcidAndIron;
    }

    @Override
    public void calculate(Members member) {
        List<Map<String, String>> nutritionVisits = member.nutrition();
        for (Map<String, String> nutritionVisit : nutritionVisits) {
            if (withInStartAndEndTime(nutritionVisit)) {
                String womanNutritionString = getWomanNutritionString(nutritionVisit);
                if (validDiseaseString(womanNutritionString)) {
                    List<WomanNutritionService> nutritionServices = WomanNutritionService.extractDiseaseListFrom(womanNutritionString);
                    addToCount(nutritionServices);
                }
            }
        }
    }

    private String getWomanNutritionString(Map<String, String> visitData) {
        if (visitData.containsKey(Members.NutritionVisitKeyValue.Key.WOMAN_NUTRITION)) {
            return visitData.get(Members.NutritionVisitKeyValue.Key.WOMAN_NUTRITION);
        }
        return "";
    }

    private boolean validDiseaseString(String ttDoses) {
        return ttDoses != null && !ttDoses.isEmpty();
    }



    private void addToCount(List<WomanNutritionService> womanNutritionServices) {
        for(WomanNutritionService womanNutritionService: womanNutritionServices) {
            switch (womanNutritionService) {
                case IRON_AND_FOLIC_ACID_COUNSELLING_OR_DISTRIBUTION:
                    countOfCounsellingOnFolicAcidAndIron ++;
                    break;
            }
        }
    }
}
