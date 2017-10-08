package org.opensrp.register.mcare.report.mis1.nutrition;

import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.domain.Members.NutritionVisitKeyValue.WomanNutritionService;
import org.opensrp.register.mcare.report.mis1.Nutrition;
import org.opensrp.register.mcare.report.mis1.Nutrition.NutritionVisitBuilder;
import org.opensrp.register.mcare.report.mis1.WomanNutrition;
import org.opensrp.register.mcare.report.mis1.MIS1TestData;
import org.opensrp.register.mcare.report.mis1.WomanNutrition.WomanNutritionBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WomanNutritionReportTestData extends MIS1TestData {
    private WomanNutritionBuilder womanNutritionBuilder;
    private NutritionVisitBuilder nutritionVisitBuilder;

    public WomanNutritionReportTestData(int totalCount, int validCount, long startDateTime, long endDateTime) {
        super(totalCount, validCount, startDateTime, endDateTime);
        this.womanNutritionBuilder = new WomanNutritionBuilder();
        this.nutritionVisitBuilder = new NutritionVisitBuilder();
    }

    public List<Members> createDataForCountOfCounsellingOnFolicAcidAndIron() {
        List<Members> members = new ArrayList<>();
        members.add(creteValidMemberWithStartDateTime(WomanNutritionService.IRON_AND_FOLIC_ACID_COUNSELLING_OR_DISTRIBUTION, WomanNutritionService.COUNSELLING_ON_FEEDING_MM));
        members.add(creteValidMemberWithEndDateTime(WomanNutritionService.IRON_AND_FOLIC_ACID_COUNSELLING_OR_DISTRIBUTION, WomanNutritionService.COUNSELLING_ON_FEEDING_MM));
        for (int i=0; i<totalCount; i++) {
            if(i<validCount-2) {
                members.add(createValidMemberFor(WomanNutritionService.IRON_AND_FOLIC_ACID_COUNSELLING_OR_DISTRIBUTION, WomanNutritionService.COUNSELLING_ON_FEEDING_MM));
            }else {
                members.add(createInvalidMemberFor(i, WomanNutritionService.IRON_AND_FOLIC_ACID_COUNSELLING_OR_DISTRIBUTION, WomanNutritionService.COUNSELLING_ON_FEEDING_MM));
            }
        }
        return members;
    }

    private Members createInvalidMemberFor(int index, WomanNutritionService inTestService, WomanNutritionService otherService) {
        int i = index % 3;
        if(i==0) {
            Long randomDateTimeBetweenStartAndEndDateTime = getRandomNumberBetween(0, startDateTime-1);
            nutritionVisitBuilder.clientVersion(randomDateTimeBetweenStartAndEndDateTime);
            nutritionVisitBuilder.womanNutrition(womanNutritionBuilder.service(inTestService).service(otherService).build());
            return createMemberWithNutrition(Collections.singletonList(nutritionVisitBuilder.build().getVisitData()));
        }else if(i==1) {
            Long randomDateTimeBetweenStartAndEndDateTime = getRandomNumberBetween(endDateTime+1, endDateTime*2);
            nutritionVisitBuilder.clientVersion(randomDateTimeBetweenStartAndEndDateTime);
            nutritionVisitBuilder.womanNutrition(womanNutritionBuilder.service(inTestService).service(otherService).build());
            return createMemberWithNutrition(Collections.singletonList(nutritionVisitBuilder.build().getVisitData()));

        }else {
            Long randomDateTimeBetweenStartAndEndDateTime = getRandomNumberBetween(startDateTime, endDateTime);
            nutritionVisitBuilder.clientVersion(randomDateTimeBetweenStartAndEndDateTime);
            nutritionVisitBuilder.womanNutrition(womanNutritionBuilder.service(otherService).build());
            return createMemberWithNutrition(Collections.singletonList(nutritionVisitBuilder.build().getVisitData()));
        }
    }

    private Members createValidMemberFor(WomanNutritionService inTestService, WomanNutritionService otherService) {
        Long randomDateTimeBetweenStartAndEndDateTime = getRandomNumberBetween(startDateTime, endDateTime);
        nutritionVisitBuilder.clientVersion(randomDateTimeBetweenStartAndEndDateTime);
        nutritionVisitBuilder.womanNutrition(womanNutritionBuilder.service(inTestService).service(otherService).build());
        return createMemberWithNutrition(Collections.singletonList(nutritionVisitBuilder.build().getVisitData()));
    }

    private Members creteValidMemberWithEndDateTime(WomanNutritionService inTestService, WomanNutritionService otherService) {
        nutritionVisitBuilder.clientVersion(endDateTime);
        nutritionVisitBuilder.womanNutrition(womanNutritionBuilder.service(inTestService).service(otherService).build());
        return createMemberWithNutrition(Collections.singletonList(nutritionVisitBuilder.build().getVisitData()));
    }

    private Members creteValidMemberWithStartDateTime(WomanNutritionService inTestService, WomanNutritionService otherService) {
        nutritionVisitBuilder.clientVersion(startDateTime);
        nutritionVisitBuilder.womanNutrition(womanNutritionBuilder.service(otherService).service(inTestService).build());
        return createMemberWithNutrition(Collections.singletonList(nutritionVisitBuilder.build().getVisitData()));
    }
}
