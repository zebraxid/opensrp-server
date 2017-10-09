package org.opensrp.register.mcare.report.mis1.nutrition;

import org.joda.time.DateTime;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.domain.Members.NutritionVisitKeyValue.WomanNutritionService;
import org.opensrp.register.mcare.report.mis1.ReportCalculator;

import java.util.List;
import java.util.Map;

import static org.opensrp.register.mcare.domain.Members.DetailKeyValue.Key.LAST_CHILD_BIRTH_DAY;
import static org.opensrp.register.mcare.domain.Members.EligibleCoupleVisitKeyValue.IS_PREGNANT;
import static org.opensrp.register.mcare.domain.Members.EligibleCoupleVisitKeyValue.Key.PREGNANT_STATUS;
import static org.opensrp.register.mcare.domain.Members.NutritionVisitKeyValue.Key.WOMAN_NUTRITION;

public class WomanNutritionCalculator extends ReportCalculator {
    private static long UPPER_RANGE_OF_MOTHERHOOD_IN_MILLIS = 23 * 30 * 24 * 60 * 60 * 1000l;

    private long countOfCounsellingOnFolicAcidAndIronForPregWoman = 0;
    private long countOfCounsellingOnFolicAcidAndIronForMother = 0;

    public WomanNutritionCalculator(long startDateTime, long endDateTime) {
        super(startDateTime, endDateTime);
    }

    public long getCountOfCounsellingOnFolicAcidAndIronForPregWoman() {
        return countOfCounsellingOnFolicAcidAndIronForPregWoman;
    }

    public long getCountOfCounsellingOnFolicAcidAndIronForMother() {
        return countOfCounsellingOnFolicAcidAndIronForMother;
    }

    @Override
    public void calculate(Members member) {
        List<Map<String, String>> nutritionVisits = member.nutrition();
        for (Map<String, String> nutritionVisit : nutritionVisits) {
            if (withInStartAndEndTime(nutritionVisit)) {
                String womanNutritionString = getWomanNutritionString(nutritionVisit);
                List<WomanNutritionService> nutritionServices = WomanNutritionService.extractDiseaseListFrom(womanNutritionString);
                if (validDiseaseString(womanNutritionString)) {
                    addToPregnantWomanCounts(member, nutritionServices);
                    addToMotherCounts(member, nutritionServices);
                } ;
            }
        }
    }

    private void addToMotherCounts(Members member, List<WomanNutritionService> nutritionServices) {
        if (lastChildWithInRange(member.details())) {
            addToCountForMother(nutritionServices);

        }
    }

    private void addToPregnantWomanCounts(Members member, List<WomanNutritionService> nutritionServices) {
        if (isPregnant(member.details())) {
            addToCountForPregWoman(nutritionServices);

        }
    }

    private boolean lastChildWithInRange(Map<String, String> details) {

        if (details.containsKey(LAST_CHILD_BIRTH_DAY)) {
            try {
                long lastChildBirthDay = Long.parseLong(details.get(LAST_CHILD_BIRTH_DAY));
                long age = new DateTime().getMillis() - lastChildBirthDay;
                if (age >= 0 && age <= UPPER_RANGE_OF_MOTHERHOOD_IN_MILLIS) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    private String getWomanNutritionString(Map<String, String> visitData) {
        if (visitData.containsKey(WOMAN_NUTRITION)) {
            return visitData.get(WOMAN_NUTRITION);
        }
        return "";
    }

    private boolean validDiseaseString(String ttDoses) {
        return ttDoses != null && !ttDoses.isEmpty();
    }


    private void addToCountForPregWoman(List<WomanNutritionService> womanNutritionServices) {
        for (WomanNutritionService womanNutritionService : womanNutritionServices) {
            switch (womanNutritionService) {
                case IRON_AND_FOLIC_ACID_COUNSELLING_OR_DISTRIBUTION:
                    countOfCounsellingOnFolicAcidAndIronForPregWoman++;
                    break;
            }
        }
    }

    private void addToCountForMother(List<WomanNutritionService> womanNutritionServices) {
        for (WomanNutritionService womanNutritionService : womanNutritionServices) {
            switch (womanNutritionService) {
                case IRON_AND_FOLIC_ACID_COUNSELLING_OR_DISTRIBUTION:
                    countOfCounsellingOnFolicAcidAndIronForMother++;
                    break;
            }
        }
    }

    private boolean isPregnant(Map<String, String> details) {
        if (details.containsKey(PREGNANT_STATUS)) {
            return IS_PREGNANT.equalsIgnoreCase(details.get(PREGNANT_STATUS));
        }
        return false;
    }

}
