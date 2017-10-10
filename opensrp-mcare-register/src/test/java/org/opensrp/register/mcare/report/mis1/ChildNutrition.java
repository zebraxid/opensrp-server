package org.opensrp.register.mcare.report.mis1;

import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.domain.Members.NutritionVisitKeyValue.ChildNutritionService;

public class ChildNutrition {
    private String visitData;

    private ChildNutrition(ChildNutritionBuilder builder) {
        this.visitData = builder.visitData;
    }

    public String getVisitData() {
        return visitData;
    }

    public static class ChildNutritionBuilder {

        private String visitData;

        public ChildNutritionBuilder() {
            visitData = "";
        }

        public ChildNutritionBuilder service(ChildNutritionService nutritionService) {
            if (nutritionService != null) {
                addNutritionToExistingList(nutritionService.getValueAsStr());
            }
            return this;
        }

        private void addNutritionToExistingList(String nutrition) {
            if(visitData.isEmpty()) {
                visitData = nutrition;
            }else {
                visitData += Members.NutritionVisitKeyValue.LIST_SEPERATOR + nutrition;
            }
        }

        public ChildNutrition build() {
            ChildNutrition ChildNutrition = new ChildNutrition(this);
            this.visitData = "";
            return ChildNutrition;
        }
    }
}

