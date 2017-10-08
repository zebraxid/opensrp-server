package org.opensrp.register.mcare.report.mis1;

import org.opensrp.register.mcare.domain.Members;

import java.util.HashMap;
import java.util.Map;

public class Nutrition {
    private Map<String, String> visitData;

    public Map<String, String> getVisitData() {

        return visitData;
    }

    public static class NutritionVisitBuilder {
        private Map<String, String> visitData;

        public NutritionVisitBuilder() {

            visitData = new HashMap<>();
        }

        public NutritionVisitBuilder clientVersion(long clientVersion) {
            visitData.put(Members.CLIENT_VERSION_KEY, String.valueOf(clientVersion));
            return this;
        }

        public NutritionVisitBuilder womanNutrition(WomanNutrition womanNutrition) {
            visitData.put(Members.NutritionVisitKeyValue.Key.WOMAN_NUTRITION, womanNutrition.getVisitData());
            return this;
        }

        public NutritionVisitBuilder distrinutedNutrition(WomanNutrition womanNutrition) {
            visitData.put(Members.NutritionVisitKeyValue.Key.DISTRINUTED_NUTRITION, womanNutrition.getVisitData());
            return this;
        }

        public NutritionVisitBuilder childNutrition(ChildNutrition womanNutrition) {
            visitData.put(Members.NutritionVisitKeyValue.Key.CHILD_NUTRITION, womanNutrition.getVisitData());
            return this;
        }

        public Nutrition build() {
            Nutrition Nutrition = new Nutrition(this);
            this.visitData.clear();
            return Nutrition;
        }
    }

    private Nutrition(NutritionVisitBuilder builder) {
        this.visitData = new HashMap<>(builder.visitData);
    }
}
