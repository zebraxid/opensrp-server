package org.opensrp.register.mcare.report.mis1;

import org.opensrp.register.mcare.domain.Members;

import java.util.HashMap;
import java.util.Map;

import static org.opensrp.register.mcare.domain.Members.AdolescentHealthVisitKeyValue.CounsellingType;

public class AdolescentHealthVisit {

    private Map<String, String> visitData;

    public Map<String, String> getVisitData() {
        return visitData;
    }

    public static class AdolescentHealthVisitBuilder {
        private Map<String, String> visitData;

        public AdolescentHealthVisitBuilder() {
            visitData = new HashMap<>();
        }

        public AdolescentHealthVisitBuilder clientVersion(long clientVersion) {
            visitData.put(Members.CLIENT_VERSION_KEY, String.valueOf(clientVersion));
            return this;
        }

        public AdolescentHealthVisitBuilder counselling(CounsellingBuilder counsellingBuilder) {
            visitData.put(Members.EligibleCoupleVisitKeyValue.Key.TT_DOSE, counsellingBuilder.build());
            return this;
        }

        public AdolescentHealthVisit build() {
            AdolescentHealthVisit adolescentHealthVisit = new AdolescentHealthVisit(this);
            this.visitData.clear();
            return adolescentHealthVisit;
        }
    }

    private AdolescentHealthVisit(AdolescentHealthVisitBuilder adolescentHealthVisitBuilder) {
        this.visitData = new HashMap<>();
        this.visitData.putAll(adolescentHealthVisitBuilder.visitData);
    }


    public static class CounsellingBuilder {
        private String counselling = "";

        public CounsellingBuilder badEffectOfChildMarriageAndTeenageMother() {
            counselling += CounsellingType.BAD_EFFECT_OF_CHILD_MARRIAGE_AND_TEENAGE_MOTHER.getValueInString();
            return this;
        }

        public CounsellingBuilder takingIronAndFolicAcid() {
            addSpaceIfNotEmpty();
            counselling += CounsellingType.TAKING_IRON_AND_FOLIC_ACID.getValueInString();
            return this;
        }

        public CounsellingBuilder eatingIronAndBalancedDiet() {
            addSpaceIfNotEmpty();
            counselling += CounsellingType.EATING_NUTRITIOUS_AND_BALANCED_DIET.getValueInString();
            return this;
        }

        public CounsellingBuilder onAdolescent() {
            addSpaceIfNotEmpty();
            counselling += CounsellingType.ON_ADOLESCENT.getValueInString();
            return this;
        }

        public CounsellingBuilder cleannessAndComplexityOfMenstruation() {
            addSpaceIfNotEmpty();
            counselling += CounsellingType.CLEANNESS_AND_COMPLEXITY_OF_MENSTRUATION.getValueInString();
            return this;
        }

        public CounsellingBuilder sexOrganInfectionAndSexuallyTransmittedDiseases() {
            addSpaceIfNotEmpty();
            counselling += CounsellingType.SEX_ORGAN_INFECTION_AND_SEXUALLY_TRANSMITTED_DISEASES.getValueInString();
            return this;
        }


        public String build() {
            String temp = counselling;
            counselling = "";
            return temp;
        }

        private void addSpaceIfNotEmpty() {
            if (!counselling.isEmpty()) {
                counselling += " ";
            }
        }

    }
}
