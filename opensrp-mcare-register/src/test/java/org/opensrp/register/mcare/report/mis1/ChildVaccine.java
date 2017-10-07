package org.opensrp.register.mcare.report.mis1;

import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.domain.Vaccine;

public class ChildVaccine {
    private String visitData;

    public String getVisitData() {

        return visitData;
    }

    public static class ChildVaccineVisitBuilder {
        private String visitData;

        public ChildVaccineVisitBuilder() {
            visitData = "";
        }

        public ChildVaccine.ChildVaccineVisitBuilder vaccine(Vaccine vaccine) {
            if (vaccine != null) {
                addVaccineToExistingList(vaccine.toString());
            }
            return this;
        }

        private void addVaccineToExistingList(String vaccine) {
            if(visitData.isEmpty()) {
                visitData = vaccine;
            }else {
                visitData += Members.ChildVisitKeyValue.LIST_SEPARATOR + vaccine;
            }
        }

        public ChildVaccine build() {
            ChildVaccine childVaccine = new ChildVaccine(this);
            this.visitData = "";
            return childVaccine;
        }
    }

    private ChildVaccine(ChildVaccine.ChildVaccineVisitBuilder ChildVaccineBuilder) {
        this.visitData = ChildVaccineBuilder.visitData;
    }
}
