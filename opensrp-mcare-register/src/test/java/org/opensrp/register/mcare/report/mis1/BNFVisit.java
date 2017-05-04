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

        public BNFVisit.BNFVisitBuilder deliveryAt(String deliveryPlace) {
            visitData.put(Members.BirthNotificationVisitKeyValue.Key.WHERE_DELIVERED, deliveryPlace);
            return this;
        }

        public BNFVisit.BNFVisitBuilder deliveryBy(String deliveryPerson) {
            visitData.put(Members.BirthNotificationVisitKeyValue.Key.WHO_DELIVERED, deliveryPerson);
            return this;
        }

        public BNFVisit.BNFVisitBuilder normalDelivery(String deliveryType) {
            visitData.put(Members.BirthNotificationVisitKeyValue.Key.DELIVERY_TYPE, deliveryType);
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
