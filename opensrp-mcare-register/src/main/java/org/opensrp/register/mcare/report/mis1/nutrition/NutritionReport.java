package org.opensrp.register.mcare.report.mis1.nutrition;

import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.Report;

public class NutritionReport extends Report {

    private WomanNutritionCalculator womanNutritionCalculator;
    private ChildNutritionCalculator childNutritionCalculator;

    public NutritionReport(long startDateTime, long endDateTime) {
        super(startDateTime, endDateTime);
    }

    @Override
    public void calculate(Members member) {
        useReflectionToDynamicallyCallCalculateMethodOnAllMemberOf(this.getClass(), member);
     }

    @Override
    protected void initCalculators(long startDateTime, long endDateTime) {
        useReflectionToDynamicallyInitAllMemberOf(this.getClass(), startDateTime, endDateTime);
    }

    public WomanNutritionCalculator getWomanNutritionCalculator() {
        return womanNutritionCalculator;
    }

    public ChildNutritionCalculator getChildNutritionCalculator() {
        return childNutritionCalculator;
    }
}
