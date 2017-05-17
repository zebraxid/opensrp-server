package org.opensrp.register.mcare.report.mis1;

import org.opensrp.register.mcare.domain.Members;

import java.util.HashMap;
import java.util.Map;

import static org.opensrp.register.mcare.domain.Members.EligibleCoupleVisitKeyValue.*;

public class EligibleCoupleFollowUP {
    private Map<String, String> followUp;

    public Map<String, String> getFollowUp() {
        return followUp;
    }

    public static class EligibleCoupleFollowUpBuilder {
        private Map<String, String> followUp;

        public EligibleCoupleFollowUpBuilder() {
            followUp = new HashMap<>();
        }

        public EligibleCoupleFollowUpBuilder clientVersion(long clientVersion) {
            followUp.put(Members.CLIENT_VERSION_KEY, String.valueOf(clientVersion));
            return this;
        }
        public EligibleCoupleFollowUpBuilder pregnant(String pregnantStatus) {
            followUp.put(Key.PREGNANT_STATUS, pregnantStatus);
            return this;
        }

        public EligibleCoupleFollowUpBuilder tt_dose(TTDoseBuilder ttDoseBuilder) {
            followUp.put(Key.TT_DOSE, ttDoseBuilder.build());
            return this;
        }

        public EligibleCoupleFollowUP build() {
            EligibleCoupleFollowUP eligibleCoupleFollowUP = new EligibleCoupleFollowUP(this);
            this.followUp.clear();
            return eligibleCoupleFollowUP;
        }
    }

    private EligibleCoupleFollowUP(EligibleCoupleFollowUpBuilder eligibleCoupleFollowUpBuilder) {
        this.followUp = new HashMap<>();
        this.followUp.putAll(eligibleCoupleFollowUpBuilder.followUp);
    }


    public static class TTDoseBuilder {
        private String ttDoses = "";

        public TTDoseBuilder addDoseOne() {
            ttDoses += Members.VaccineDose.ONE.getValueInString();
            return this;
        }

        public TTDoseBuilder addDoseTwo() {
            addSpaceIfNotEmpty();
            ttDoses += Members.VaccineDose.TWO.getValueInString();
            return this;
        }

        public TTDoseBuilder addDoseThree() {
            addSpaceIfNotEmpty();
            ttDoses += Members.VaccineDose.THREE.getValueInString();
            return this;
        }

        public TTDoseBuilder addDoseFour() {
            addSpaceIfNotEmpty();
            ttDoses += Members.VaccineDose.FOUR.getValueInString();
            return this;
        }

        public TTDoseBuilder addDoseFive() {
            addSpaceIfNotEmpty();
            ttDoses += Members.VaccineDose.FIVE.getValueInString();
            return this;
        }

        public String build() {
            String temp = ttDoses;
            ttDoses = "";
            return temp;
        }

        private void addSpaceIfNotEmpty() {
            if (!ttDoses.isEmpty()) {
                ttDoses += " ";
            }
        }

    }
}
