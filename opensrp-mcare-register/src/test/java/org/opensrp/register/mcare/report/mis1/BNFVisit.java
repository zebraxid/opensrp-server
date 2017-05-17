package org.opensrp.register.mcare.report.mis1;


import org.opensrp.register.mcare.domain.Members;

import java.util.HashMap;
import java.util.Map;

import static org.opensrp.register.mcare.domain.Members.BirthNotificationVisitKeyValue.*;

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

        public BNFVisit.BNFVisitBuilder deliveryAt(DeliveryPlace deliveryPlace) {
            visitData.put(Key.WHERE_DELIVERED, deliveryPlace.getValueInString());
            return this;
        }

        public BNFVisit.BNFVisitBuilder deliveryBy(DeliveryBy deliveryPerson) {
            visitData.put(Key.WHO_DELIVERED, deliveryPerson.getValueInString());
            return this;
        }

        public BNFVisit.BNFVisitBuilder deliveryType(DeliveryType deliveryType) {
            visitData.put(Key.DELIVERY_TYPE, deliveryType.getValueInString());
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
