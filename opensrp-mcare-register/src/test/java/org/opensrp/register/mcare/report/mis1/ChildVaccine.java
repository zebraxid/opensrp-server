package org.opensrp.register.mcare.report.mis1;

import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.domain.Vaccine;

import java.util.HashMap;
import java.util.Map;

public class ChildVaccine {
    private Map<String, String> visitData;

    public Map<String, String> getVisitData() {

        return visitData;
    }

    public static class ChildVaccineVisitBuilder {
        private Map<String, String> visitData;

        public ChildVaccineVisitBuilder() {

            visitData = new HashMap<>();
        }

        public ChildVaccine.ChildVaccineVisitBuilder clientVersion(long clientVersion) {
            visitData.put(Members.CLIENT_VERSION_KEY, String.valueOf(clientVersion));
            return this;
        }

        public ChildVaccine.ChildVaccineVisitBuilder vaccine(Vaccine vaccine) {
            if (vaccine != null) {
                addVaccineToExistingList(vaccine.toString());
            }
            return this;
        }

        private void addVaccineToExistingList(String vaccine) {
            if(visitData.containsKey(Vaccine.Key)) {
                String vaccines = visitData.get(Vaccine.Key);
                vaccines += " " + vaccine;
                visitData.put(Vaccine.Key, vaccines);
            }else {
                visitData.put(Vaccine.Key, vaccine);
            }
        }

        public ChildVaccine build() {
            ChildVaccine childVaccine = new ChildVaccine(this);
            this.visitData.clear();
            return childVaccine;
        }
    }

    private ChildVaccine(ChildVaccine.ChildVaccineVisitBuilder ChildVaccineBuilder) {
        this.visitData = new HashMap<>(ChildVaccineBuilder.visitData);
    }
}
