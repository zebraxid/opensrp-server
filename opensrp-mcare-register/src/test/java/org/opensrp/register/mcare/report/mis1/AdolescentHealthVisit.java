package org.opensrp.register.mcare.report.mis1;

import org.opensrp.register.mcare.domain.Members;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.opensrp.register.mcare.domain.Members.AdolescentHealthVisitKeyValue.*;
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

        public AdolescentHealthVisitBuilder counselling(List<CounsellingType> counsellingTypes) {
            visitData.put(Key.COUNSELLING, CounsellingType.createValueStringFrom(counsellingTypes));
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
        private List<CounsellingType> counsellingTypes = new ArrayList<>();

        public CounsellingBuilder badEffectOfChildMarriageAndTeenageMother() {
            counsellingTypes.add(CounsellingType.BAD_EFFECT_OF_CHILD_MARRIAGE_AND_TEENAGE_MOTHER);
            return this;
        }

        public CounsellingBuilder takingIronAndFolicAcid() {
            counsellingTypes.add(CounsellingType.TAKING_IRON_AND_FOLIC_ACID);
            return this;
        }

        public CounsellingBuilder eatingIronAndBalancedDiet() {
            counsellingTypes.add(CounsellingType.EATING_NUTRITIOUS_AND_BALANCED_DIET);
            return this;
        }

        public CounsellingBuilder onAdolescent() {
            counsellingTypes.add(CounsellingType.ON_ADOLESCENT);
            return this;
        }

        public CounsellingBuilder cleannessAndComplexityOfMenstruation() {
            counsellingTypes.add(CounsellingType.CLEANNESS_AND_COMPLEXITY_OF_MENSTRUATION);
            return this;
        }

        public CounsellingBuilder sexOrganInfectionAndSexuallyTransmittedDiseases() {
            counsellingTypes.add(CounsellingType.SEX_ORGAN_INFECTION_AND_SEXUALLY_TRANSMITTED_DISEASES);
            return this;
        }

        public CounsellingBuilder add(CounsellingType counsellingType) {
            counsellingTypes.add(counsellingType);
            return this;
        }

        public List<CounsellingType> buildAndClean() {
            List<CounsellingType> temp = new ArrayList<>(counsellingTypes);
            counsellingTypes = new ArrayList<>();
            return temp;
        }

    }
}
