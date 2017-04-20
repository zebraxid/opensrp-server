package org.opensrp.register.mcare.report.mis1;

import org.opensrp.register.mcare.domain.Members;

import java.util.HashMap;
import java.util.Map;

public class EligibleCoupleFollowUP {
    private Map<String, String> followUp;

    public Map<String, String> getFollowUp() {
        return followUp;
    }

    public static class EligibleCoupleFollowUpBuilder {
        private Map<String, String> followUp;

        public EligibleCoupleFollowUpBuilder(long clientVersion) {
            followUp = new HashMap<>();
            followUp.put(Members.CLIENT_VERSION_KEY, String.valueOf(clientVersion));
        }

        public EligibleCoupleFollowUpBuilder pregnant(String pregnantStatus) {
            followUp.put(Members.PREGNANT_STATUS_KEY, pregnantStatus);
            return this;
        }

        public EligibleCoupleFollowUP build() {
            return new EligibleCoupleFollowUP(this);
        }
    }

    private EligibleCoupleFollowUP(EligibleCoupleFollowUpBuilder eligibleCoupleFollowUpBuilder) {
        this.followUp = eligibleCoupleFollowUpBuilder.followUp;
    }

}
