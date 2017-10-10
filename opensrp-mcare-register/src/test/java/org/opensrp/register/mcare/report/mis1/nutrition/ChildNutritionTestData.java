package org.opensrp.register.mcare.report.mis1.nutrition;

import org.joda.time.DateTime;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.domain.Members.NutritionVisitKeyValue.ChildNutritionService;
import org.opensrp.register.mcare.report.mis1.ChildNutrition.ChildNutritionBuilder;
import org.opensrp.register.mcare.report.mis1.MIS1TestData;
import org.opensrp.register.mcare.report.mis1.Nutrition.NutritionVisitBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChildNutritionTestData extends MIS1TestData {
    ChildNutritionBuilder childNutritionBuilder;
    NutritionVisitBuilder nutritionVisitBuilder;

    public ChildNutritionTestData(int totalCount, int validCount, long startDateTime, long endDateTime) {
        super(totalCount, validCount, startDateTime, endDateTime);
        this.childNutritionBuilder = new ChildNutritionBuilder();
        this.nutritionVisitBuilder = new NutritionVisitBuilder();
    }

    public List<Members> createDataForCountOfBreastFeedingWithInOneHourOfBirthForChildAgeZeroToSixMonth() {
        ChildNutritionService inTest = ChildNutritionService.FEEDING_BREAST_MILK_WITHIN_ONE_HOUR_OF_BIRTH;
        ChildNutritionService other = ChildNutritionService.FEEDING_BREAST_MILK_AFTER_SIX_MONTH;
        List<Members> members = new ArrayList<>();
        members.add(creteValidMemberWithStartDateTime(inTest, other));
        members.add(creteValidMemberWithEndDateTime(inTest, other));
        for (int i=0; i<totalCount; i++) {
            if(i<validCount-2) {
                members.add(createValidMemberFor(inTest, other));
            }else {
                members.add(createInvalidMemberFor(i, inTest, other));
            }
        }
        return members;
    }

    private Members createInvalidMemberFor(int index, ChildNutritionService inTest, ChildNutritionService other) {
        int i = index % 4;
        if(i==0) {
            Long randomDateTimeBetweenStartAndEndDateTime = getRandomNumberBetween(0, startDateTime-1);
            nutritionVisitBuilder.clientVersion(randomDateTimeBetweenStartAndEndDateTime);
            nutritionVisitBuilder.childNutrition(childNutritionBuilder.service(other).service(inTest).build());
            Members member = createMemberWithNutrition(Collections.singletonList(nutritionVisitBuilder.build().getVisitData()));
            member.setMember_Birth_Date(createBirthDateStringOf(new DateTime().minusDays(2)));
            return member;
        }else if(i==1) {
            Long randomDateTimeBetweenStartAndEndDateTime = getRandomNumberBetween(endDateTime+1, endDateTime*2);
            nutritionVisitBuilder.clientVersion(randomDateTimeBetweenStartAndEndDateTime);
            nutritionVisitBuilder.childNutrition(childNutritionBuilder.service(other).service(inTest).build());
            Members member = createMemberWithNutrition(Collections.singletonList(nutritionVisitBuilder.build().getVisitData()));
            return member;

        }else if(i==2) {
            Long randomDateTimeBetweenStartAndEndDateTime = getRandomNumberBetween(startDateTime, endDateTime);
            nutritionVisitBuilder.clientVersion(randomDateTimeBetweenStartAndEndDateTime);
            nutritionVisitBuilder.childNutrition(childNutritionBuilder.service(other).build());
            Members member = createMemberWithNutrition(Collections.singletonList(nutritionVisitBuilder.build().getVisitData()));
            member.setMember_Birth_Date(createBirthDateStringOf(new DateTime().minusHours(3)));
            return member;
        } else {
            Long randomDateTimeBetweenStartAndEndDateTime = getRandomNumberBetween(startDateTime, endDateTime);
            nutritionVisitBuilder.clientVersion(randomDateTimeBetweenStartAndEndDateTime);
            nutritionVisitBuilder.clientVersion(randomDateTimeBetweenStartAndEndDateTime);
            nutritionVisitBuilder.childNutrition(childNutritionBuilder.service(other).service(inTest).build());
            Members member = createMemberWithNutrition(Collections.singletonList(nutritionVisitBuilder.build().getVisitData()));
            member.setMember_Birth_Date(createBirthDateStringOf(new DateTime().minusDays(7*30+4)));
            return member;
        }
    }

    private Members createValidMemberFor(ChildNutritionService inTest, ChildNutritionService other) {
        Long randomDateTimeBetweenStartAndEndDateTime = getRandomNumberBetween(startDateTime, endDateTime);
        int days = getRandomNumberBetween(0, (6*30-1));
        nutritionVisitBuilder.clientVersion(randomDateTimeBetweenStartAndEndDateTime);
        nutritionVisitBuilder.childNutrition(childNutritionBuilder.service(other).service(inTest).build());
        Members member = createMemberWithNutrition(Collections.singletonList(nutritionVisitBuilder.build().getVisitData()));
        member.setMember_Birth_Date(createBirthDateStringOf(new DateTime().minusDays(days)));
        return member;
    }

    private Members creteValidMemberWithEndDateTime(ChildNutritionService inTest, ChildNutritionService other) {
        nutritionVisitBuilder.clientVersion(endDateTime);
        nutritionVisitBuilder.childNutrition(childNutritionBuilder.service(other).service(inTest).build());
        Members member = createMemberWithNutrition(Collections.singletonList(nutritionVisitBuilder.build().getVisitData()));
        member.setMember_Birth_Date(createBirthDateStringOf(new DateTime().minusDays(5*30+29)));
        return member;
    }

    private Members creteValidMemberWithStartDateTime(ChildNutritionService intTestService, ChildNutritionService otherNutritionService) {
        nutritionVisitBuilder.clientVersion(startDateTime);
        nutritionVisitBuilder.childNutrition(childNutritionBuilder.service(otherNutritionService).service(intTestService).build());
        Members member = createMemberWithNutrition(Collections.singletonList(nutritionVisitBuilder.build().getVisitData()));
        member.setMember_Birth_Date(createBirthDateStringOf(new DateTime().minusHours(3)));
        return member;
    }

    public String createBirthDateStringOf(DateTime dateTime) {
       return dateTime.toString(Members.Member_Date_Format);
    }

}
