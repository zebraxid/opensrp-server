package org.opensrp.register.mcare.report.mis1;

import org.opensrp.register.mcare.domain.Members;

import java.util.HashMap;
import java.util.Map;

public class ChildVisit {
    private Map<String, String> visitData;

    public Map<String, String> getVisitData() {

        return visitData;
    }

    public static class ChildVisitBuilder {
        private Map<String, String> visitData;

        public ChildVisitBuilder() {

            visitData = new HashMap<>();
        }

        public ChildVisitBuilder clientVersion(long clientVersion) {
            visitData.put(Members.CLIENT_VERSION_KEY, String.valueOf(clientVersion));
            return this;
        }

        public ChildVisitBuilder vaccine(ChildVaccine vaccine) {
            visitData.put(Members.ChildVisitKeyValue.Key.VACCINE, vaccine.getVisitData());
            return this;
        }

        public ChildVisitBuilder disease(ChildDiseaseProblem childDiseaseProblem) {
            visitData.put(Members.ChildVisitKeyValue.Key.DISEASE_PROBLEM, childDiseaseProblem.getVisitData());
            return this;
        }

        public ChildVisit build() {
            ChildVisit ChildVisit = new ChildVisit(this);
            this.visitData.clear();
            return ChildVisit;
        }
    }

    private ChildVisit(ChildVisitBuilder ChildVaccineBuilder) {
        this.visitData = new HashMap<>(ChildVaccineBuilder.visitData);
    }
}
