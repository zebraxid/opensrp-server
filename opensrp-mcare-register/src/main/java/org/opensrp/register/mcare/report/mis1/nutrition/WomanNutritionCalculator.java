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
import static org.opensrp.register.mcare.domain.Members.NutritionVisitKeyValue.Key.DISTRINUTED_NUTRITION;
import static org.opensrp.register.mcare.domain.Members.NutritionVisitKeyValue.Key.WOMAN_NUTRITION;

public class WomanNutritionCalculator extends ReportCalculator {
    private static long UPPER_RANGE_OF_MOTHERHOOD_IN_MILLIS = 23 * 30 * 24 * 60 * 60 * 1000l;

    private long countOfCounsellingOnFolicAcidAndIronForPregWoman = 0;
    private long countOfCounsellingOnFolicAcidAndIronForMother = 0;
    private long countOfCounsellingOnBreastMilkAndComplementaryFoodForPregWoman = 0;
    private long countOfCounsellingOnFeedingMMPregWoman = 0;
    private long countOfCounsellingOnBreastMilkAndComplementaryFoodFoMother = 0;
    private long countOfCounsellingOnFeedingMMMother = 0;
    private long countOfDistributionOfFolicAcidAndIronForMother = 0;
    private long countOfDistributionOfFolicAcidAndIronForPregWoman = 0;

    public WomanNutritionCalculator(long startDateTime, long endDateTime) {
        super(startDateTime, endDateTime);
    }

    public long getCountOfCounsellingOnFolicAcidAndIronForPregWoman() {
        return countOfCounsellingOnFolicAcidAndIronForPregWoman;
    }

    public long getCountOfCounsellingOnFolicAcidAndIronForMother() {
        return countOfCounsellingOnFolicAcidAndIronForMother;
    }

    public long getCountOfCounsellingOnBreastMilkAndComplementaryFoodForPregWoman() {
        return countOfCounsellingOnBreastMilkAndComplementaryFoodForPregWoman;
    }

    public long getCountOfCounsellingOnFeedingMMPregWoman() {
        return countOfCounsellingOnFeedingMMPregWoman;
    }

    public long getCountOfCounsellingOnBreastMilkAndComplementaryFoodFoMother() {
        return countOfCounsellingOnBreastMilkAndComplementaryFoodFoMother;
    }

    public long getCountOfCounsellingOnFeedingMMMother() {
        return countOfCounsellingOnFeedingMMMother;
    }

    public long getCountOfDistributionOfFolicAcidAndIronForMother() {
        return countOfDistributionOfFolicAcidAndIronForMother;
    }

    public long getCountOfDistributionOfFolicAcidAndIronForPregWoman() {
        return countOfDistributionOfFolicAcidAndIronForPregWoman;
    }

    @Override
    public void calculate(Members member) {
        List<Map<String, String>> nutritionVisits = member.nutrition();
        for (Map<String, String> nutritionVisit : nutritionVisits) {
            if (withInStartAndEndTime(nutritionVisit)) {
                String womanNutritionString = getWomanNutritionString(nutritionVisit);
                String womanDistrinutedString = getWomanDistrinutedNutritionString(nutritionVisit);
                List<WomanNutritionService> nutritionServices = WomanNutritionService.extractNutritionListFrom(womanNutritionString);
                List<WomanNutritionService> distrinutedNutritionServices = WomanNutritionService.extractNutritionListFrom(womanDistrinutedString);
                addToPregnantWomanCounts(member, nutritionServices, distrinutedNutritionServices);
                addToMotherCounts(member, nutritionServices, distrinutedNutritionServices);
            }
        }
    }

    private String getWomanDistrinutedNutritionString(Map<String, String> visitData) {
        if (visitData.containsKey(DISTRINUTED_NUTRITION)) {
            return validString(visitData.get(DISTRINUTED_NUTRITION)) ? visitData.get(DISTRINUTED_NUTRITION) : "";
        }
        return "";
    }

    private void addToMotherCounts(Members member, List<WomanNutritionService> nutritionServices, List<WomanNutritionService> distrinutedNutritionServices) {
        if (lastChildWithInRange(member.details())) {
            addToCountForMother(nutritionServices,distrinutedNutritionServices);

        }
    }

    private void addToPregnantWomanCounts(Members member, List<WomanNutritionService> nutritionServices, List<WomanNutritionService> distrinutedNutritionServices) {
        if (isPregnant(member.details())) {
            addToCountForPregWoman(nutritionServices, distrinutedNutritionServices);

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
            return validString(visitData.get(WOMAN_NUTRITION)) ? visitData.get(WOMAN_NUTRITION) : "";
        }
        return "";
    }

    private void addToCountForPregWoman(List<WomanNutritionService> womanNutritionServices, List<WomanNutritionService> distrinutedNutritionServices) {
        for (WomanNutritionService womanNutritionService : womanNutritionServices) {
            switch (womanNutritionService) {
                case IRON_AND_FOLIC_ACID_COUNSELLING_OR_DISTRIBUTION:
                    countOfCounsellingOnFolicAcidAndIronForPregWoman++;
                    break;
                case COUNSELLING_ON_BREAST_MILK_AND_COMPLEMENTARY_FOOD:
                    countOfCounsellingOnBreastMilkAndComplementaryFoodForPregWoman++;
                    break;
                case COUNSELLING_ON_FEEDING_MM:
                    countOfCounsellingOnFeedingMMPregWoman++;
                    break;
            }
        }

        for(WomanNutritionService distrinutedNutritionService : distrinutedNutritionServices) {
            switch (distrinutedNutritionService) {
                case IRON_AND_FOLIC_ACID_COUNSELLING_OR_DISTRIBUTION:
                    countOfDistributionOfFolicAcidAndIronForPregWoman++;
            }
        }
    }

    private void addToCountForMother(List<WomanNutritionService> womanNutritionServices, List<WomanNutritionService> distrinutedNutritionServices) {
        for (WomanNutritionService womanNutritionService : womanNutritionServices) {
            switch (womanNutritionService) {
                case IRON_AND_FOLIC_ACID_COUNSELLING_OR_DISTRIBUTION:
                    countOfCounsellingOnFolicAcidAndIronForMother++;
                    break;
                case COUNSELLING_ON_BREAST_MILK_AND_COMPLEMENTARY_FOOD:
                    countOfCounsellingOnBreastMilkAndComplementaryFoodFoMother++;

                    break;
                case COUNSELLING_ON_FEEDING_MM:
                    countOfCounsellingOnFeedingMMMother++;
                    break;
            }
        }

        for(WomanNutritionService distrinutedNutritionService : distrinutedNutritionServices) {
            switch (distrinutedNutritionService) {
                case IRON_AND_FOLIC_ACID_COUNSELLING_OR_DISTRIBUTION:
                    countOfDistributionOfFolicAcidAndIronForMother++;
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
