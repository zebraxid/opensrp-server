package org.opensrp.register.mcare.report.mis1;

import org.opensrp.register.mcare.domain.Members;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EligibleCoupleFollowUP {
    private List<Map<String, String>> followUp;

    public List<Map<String, String>> getFollowUp() {
        return followUp;
    }

    public static class EligibleCoupleFollowUpBuilder {
        private List<Map<String, String>> followUp;
        public EligibleCoupleFollowUpBuilder(long clientVersion) {
            followUp.add(createMapWith(Members.CLIENT_VERSION_KEY, String.valueOf(clientVersion)));
        }

        public EligibleCoupleFollowUpBuilder pregnant(String pregnantStatus) {
            followUp.add(createMapWith(Members.PREGNANT_STATUS_KEY, pregnantStatus));
            return this;
        }

        public EligibleCoupleFollowUP build() {
            return new EligibleCoupleFollowUP(this);
        }
        private Map<String, String> createMapWith(String key, String value) {
            Map<String, String> map = new HashMap<>();
            map.put(key, value);
            return map;
        }
    }

    private EligibleCoupleFollowUP(EligibleCoupleFollowUpBuilder eligibleCoupleFollowUpBuilder) {
        this.followUp = eligibleCoupleFollowUpBuilder.followUp;
    }
}
