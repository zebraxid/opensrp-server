package org.opensrp.register.mcare.report.mis1;

import org.opensrp.register.mcare.domain.Members;

public class ChildDiseaseProblem {
    private String visitData;

    public String getVisitData() {
        return visitData;
    }
    public static class ChildDiseaseProblemVisitBuilder {

        private String visitData;

        public ChildDiseaseProblemVisitBuilder() {
            visitData = "";
        }


        public ChildDiseaseProblem.ChildDiseaseProblemVisitBuilder disease(Members.DiseaseName diseaseName) {
            if (diseaseName != null) {
                addVaccineToExistingList(diseaseName.getValueAsStr());
            }
            return this;
        }

        private void addVaccineToExistingList(String diseaseStr) {
            if(visitData.isEmpty()) {
                visitData += diseaseStr;
            }else {
                visitData += Members.ChildVisitKeyValue.LIST_SEPARATOR + diseaseStr;
            }
        }

        public ChildDiseaseProblem build() {
            ChildDiseaseProblem ChildDiseaseProblem = new ChildDiseaseProblem(this);
            this.visitData = "";
            return ChildDiseaseProblem;
        }
    }

    private ChildDiseaseProblem(ChildDiseaseProblem.ChildDiseaseProblemVisitBuilder childVaccineBuilder) {
        this.visitData = childVaccineBuilder.visitData;
    }
}
