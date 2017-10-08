package org.opensrp.register.mcare.report.mis1;

import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.domain.Members.NutritionVisitKeyValue.WomanNutritionService;

public class WomanNutrition {
    private String visitData;

    private WomanNutrition(WomanNutritionBuilder builder) {
        this.visitData = builder.visitData;
    }

    public String getVisitData() {
        return visitData;
    }

    public static class WomanNutritionBuilder {

        private String visitData;

        public WomanNutritionBuilder() {
            visitData = "";
        }

        public WomanNutritionBuilder service(WomanNutritionService nutritionService) {
            if (nutritionService != null) {
                addServiceToExistingList(nutritionService.getValueAsStr());
            }
            return this;
        }

        private void addServiceToExistingList(String service) {
            if(visitData.isEmpty()) {
                visitData = service;
            }else {
                visitData += Members.NutritionVisitKeyValue.LIST_SEPERATOR + service;
            }
        }

        public WomanNutrition build() {
            WomanNutrition womanNutrition = new WomanNutrition(this);
            this.visitData = "";
            return womanNutrition;
        }
    }
}
