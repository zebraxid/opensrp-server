package org.opensrp.register.mcare.report.mis1.nutrition;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.domain.Members.NutritionVisitKeyValue.ChildNutritionService;
import org.opensrp.register.mcare.report.mis1.ReportCalculator;

import java.util.List;
import java.util.Map;

import static org.opensrp.register.mcare.domain.Members.NutritionVisitKeyValue.Key.CHILD_NUTRITION;

public class ChildNutritionCalculator extends ReportCalculator {
    private static int MAXIMUM_AGE_IN_MONTH = 60;
    private ChildAgeLimit childZeroToSix;
    private ChildAgeLimit child6TO23;
    private ChildAgeLimit child24to59;

    public ChildNutritionCalculator(long startDateTime, long endDateTime) {
        super(startDateTime, endDateTime);
        childZeroToSix = new ChildAgeLimit(0, 6);
        child6TO23 = new ChildAgeLimit(6, 23);
        child24to59 = new ChildAgeLimit(24, 59);
    }

    public ChildAgeLimit getChildZeroToSix() {
        return childZeroToSix;
    }

    public ChildAgeLimit getChild6TO23() {
        return child6TO23;
    }

    public ChildAgeLimit getChild24to59() {
        return child24to59;
    }

    @Override
    public void calculate(Members member) {
        int ageInMonth = getAgeInMonthOf(member);
        if (ageInMonth >= 0 && ageInMonth <= MAXIMUM_AGE_IN_MONTH) {
            List<Map<String, String>> nutritionVisits = member.nutrition();
            for (Map<String, String> nutritionVisit : nutritionVisits) {
                if (withInStartAndEndTime(nutritionVisit)) {
                    String childNutritionString = getChildNutritionString(nutritionVisit);
                    if (validString(childNutritionString)) {
                        List<ChildNutritionService> nutritionServices = ChildNutritionService.extractDiseaseListFrom(childNutritionString);
                        childZeroToSix.calculate(ageInMonth, nutritionServices);
                        child6TO23.calculate(ageInMonth, nutritionServices);
                        child24to59.calculate(ageInMonth, nutritionServices);
                    }
                }
            }
        }
    }

    private String getChildNutritionString(Map<String, String> visitData) {
        if (visitData.containsKey(CHILD_NUTRITION)) {
            return visitData.get(CHILD_NUTRITION);
        }
        return "";
    }

    private int getAgeInMonthOf(Members member) {
        if (member.getMember_Birth_Date() != null) {
            DateTimeFormatter formatter = DateTimeFormat.forPattern(Members.Member_Date_Format);
            DateTime birthDate = formatter.parseDateTime(member.getMember_Birth_Date());
            Period diff = new Period(birthDate, new DateTime());
            return diff.getMonths();
        }

        return -1;
    }

    public ChildAgeLimit forChildAgeZeroToSix() {
        return childZeroToSix;
    }

    public class ChildAgeLimit {
        private int startMonth;
        private int endMonth;
        private long countOfBreastFeedingWithInOneHour = 0;
        private long countOfBreastFeedingUntillSixMonth = 0;
        private long countOfBreastFeedingAfterSixMonth = 0;
        private long countOfContractedMAM = 0;

        public long getCountOfBreastFeedingWithInOneHour() {

            return countOfBreastFeedingWithInOneHour;
        }

        public long getCountOfBreastFeedingUntillSixMonth() {

            return countOfBreastFeedingUntillSixMonth;
        }

        public long getCountOfBreastFeedingAfterSixMonth() {
            return countOfBreastFeedingAfterSixMonth;
        }

        public long getCountOfContractedMAM() {

            return countOfContractedMAM;
        }

        public long getCountOfContractedSAM() {

            return countOfContractedSAM;
        }

        private long countOfContractedSAM = 0;

        public ChildAgeLimit(int startMonth, int endMonth) {
            this.startMonth = startMonth;
            this.endMonth = endMonth;
        }

        public long countOfBreastFeedingWithInOneHour() {
            return countOfBreastFeedingWithInOneHour;
        }

        private void calculate(int ageInMonth, List<ChildNutritionService> childNutritionServices) {
            boolean withStartAndEnd = ageInMonth >= startMonth && ageInMonth <= endMonth;
            if (withStartAndEnd) {
                addToCount(childNutritionServices);
            }
        }

        private void addToCount(List<ChildNutritionService> childNutritionServices) {
            for (ChildNutritionService childNutritionService : childNutritionServices) {
                switch (childNutritionService) {
                    case FEEDING_BREAST_MILK_WITHIN_ONE_HOUR_OF_BIRTH:
                        this.countOfBreastFeedingWithInOneHour++;
                    case FEEDING_BREAST_MILK_UNTILL_SIX_MONTH:
                        this.countOfBreastFeedingUntillSixMonth++;
                    case FEEDING_BREAST_MILK_AFTER_SIX_MONTH:
                        this.countOfBreastFeedingAfterSixMonth++;
                    case CONTRACTED_MAM:
                        this.countOfContractedMAM++;
                        break;
                    case CONTRACTED_SAM:
                        this.countOfContractedSAM++;
                        break;
                }
            }
        }
    }
}
