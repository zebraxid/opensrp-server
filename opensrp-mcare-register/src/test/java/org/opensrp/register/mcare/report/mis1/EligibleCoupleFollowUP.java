package org.opensrp.register.mcare.report.mis1;

import org.opensrp.register.mcare.domain.Members;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EligibleCoupleFollowUP {
    private List<Map<String, String>> followUp = new ArrayList<>();

    public List<Map<String, String>> getFollowUp() {
        return followUp;
    }

    public static class EligibleCoupleFollowUpBuilder {
        private Map<String, String> followUp = new HashMap<>();

        public EligibleCoupleFollowUpBuilder(long clientVersion) {
            followUp.put(Members.CLIENT_VERSION_KEY, String.valueOf(clientVersion));
        }

        public EligibleCoupleFollowUpBuilder pregnant(String pregnantStatus) {
            followUp.put(Members.PREGNANT_STATUS_KEY, pregnantStatus);
            return this;
        }

        public EligibleCoupleFollowUP add(EligibleCoupleFollowUP eligibleCoupleFollowUP) {
            eligibleCoupleFollowUP.addEligibleCoupleFollowUP(this.followUp);
            return eligibleCoupleFollowUP;
        }

        private Map<String, String> createMapWith(String key, String value) {
            Map<String, String> map = new HashMap<>();
            map.put(key, value);
            return map;
        }
    }

    private void addEligibleCoupleFollowUP(Map<String, String> eligibleCoupleFollowUp) {
        this.followUp.add(eligibleCoupleFollowUp);
    }

}
