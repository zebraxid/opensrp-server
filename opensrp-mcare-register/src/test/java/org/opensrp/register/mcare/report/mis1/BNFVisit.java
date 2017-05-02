package org.opensrp.register.mcare.report.mis1;


import org.opensrp.register.mcare.domain.Members;

import java.util.HashMap;
import java.util.Map;

public class BNFVisit {
    private Map<String, String> visitData;

    public Map<String, String> getVisitData() {
        return visitData;
    }

    public static class BNFVisitBuilder {
        private Map<String, String> visitData;

        public BNFVisitBuilder() {
            visitData = new HashMap<>();
        }

        public BNFVisit.BNFVisitBuilder clientVersion(long clientVersion) {
            visitData.put(Members.CLIENT_VERSION_KEY, String.valueOf(clientVersion));
            return this;
        }
        public BNFVisit.BNFVisitBuilder pregnant(String pregnantStatus) {
            visitData.put(Members.PREGNANT_STATUS_KEY, pregnantStatus);
            return this;
        }

        public BNFVisit build() {
            BNFVisit bnfVisit = new BNFVisit(this);
            this.visitData.clear();
            return bnfVisit;
        }
    }

    private BNFVisit(BNFVisit.BNFVisitBuilder bnfVisitBuilder) {
        this.visitData = new HashMap<>(bnfVisitBuilder.visitData);
    }
}
