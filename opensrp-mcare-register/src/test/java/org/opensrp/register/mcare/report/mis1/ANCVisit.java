package org.opensrp.register.mcare.report.mis1;


import org.opensrp.register.mcare.domain.Members;

import java.util.HashMap;
import java.util.Map;

import static org.opensrp.register.mcare.domain.Members.ANCVisitKeyValue.*;

public class ANCVisit {
    private Map<String, String> visitData;

    public Map<String, String> getVisitData() {
        return visitData;
    }

    public static class ANCVisitBuilder {
        private Map<String, String> visitData;

        public ANCVisitBuilder() {
            visitData = new HashMap<>();
        }

        public ANCVisit.ANCVisitBuilder clientVersion(long clientVersion) {
            visitData.put(Members.CLIENT_VERSION_KEY, String.valueOf(clientVersion));
            return this;
        }

        public ANCVisit.ANCVisitBuilder pregnant(String pregnantStatus) {
            visitData.put(Members.EligibleCoupleVisitKeyValue.Key.PREGNANT_STATUS, pregnantStatus);
            return this;
        }

        public ANCVisit.ANCVisitBuilder isReferred(String isReferred) {
            visitData.put(Key.IS_REFERRED, isReferred);
            return this;
        }

        public ANCVisit build() {
            ANCVisit ancVisit = new ANCVisit(this);
            this.visitData.clear();
            return ancVisit;
        }
    }

    private ANCVisit(ANCVisit.ANCVisitBuilder ancVisitBuilder) {
        this.visitData = new HashMap<>(ancVisitBuilder.visitData);
    }

}
