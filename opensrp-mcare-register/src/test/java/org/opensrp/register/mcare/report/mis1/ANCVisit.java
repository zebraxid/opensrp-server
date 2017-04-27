package org.opensrp.register.mcare.report.mis1;


import org.opensrp.register.mcare.domain.Members;

import java.util.HashMap;
import java.util.Map;

public class ANCVisit {
    private Map<String, String> visitData;

    public Map<String, String> getVisitData() {
        return visitData;
    }

    public static class ANCVisitBuilder {
        private Map<String, String> followUp;

        public ANCVisitBuilder() {
            followUp = new HashMap<>();
        }

        public ANCVisit.ANCVisitBuilder clientVersion(long clientVersion) {
            followUp.put(Members.CLIENT_VERSION_KEY, String.valueOf(clientVersion));
            return this;
        }
        public ANCVisit.ANCVisitBuilder pregnant(String pregnantStatus) {
            followUp.put(Members.PREGNANT_STATUS_KEY, pregnantStatus);
            return this;
        }

        public ANCVisit build() {
            ANCVisit eligibleCoupleFollowUP = new ANCVisit(this);
            this.followUp.clear();
            return eligibleCoupleFollowUP;
        }
    }

    private ANCVisit(ANCVisit.ANCVisitBuilder ancVisitBuilder) {
        this.visitData = new HashMap<>(ancVisitBuilder.followUp);
    }

}
