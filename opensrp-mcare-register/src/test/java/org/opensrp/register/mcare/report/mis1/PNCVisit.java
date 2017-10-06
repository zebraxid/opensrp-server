package org.opensrp.register.mcare.report.mis1;


import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.domain.Members.BooleanAnswer;

import java.util.HashMap;
import java.util.Map;

import static org.opensrp.register.mcare.domain.Members.PNCVisitKeyValue.*;
import static org.opensrp.register.mcare.domain.Members.PNCVisitKeyValue.Key;

public class PNCVisit {
    private Map<String, String> visitData;

    public Map<String, String> getVisitData() {
        return visitData;
    }

    public static class PNCVisitBuilder {
        private Map<String, String> visitData;

        public PNCVisitBuilder() {
            visitData = new HashMap<>();
        }

        public PNCVisitBuilder clientVersion(long clientVersion) {
            visitData.put(Members.CLIENT_VERSION_KEY, String.valueOf(clientVersion));
            return this;
        }

        public PNCVisitBuilder visitStatus(VisitStatus visitStatus) {
            visitData.put(Key.VISIT_STATUS, visitStatus.getValueInString());
            return this;
        }

        public PNCVisitBuilder hasPncGivenOnTime(BooleanAnswer pncGivenOnTime) {
            visitData.put(Key.HAS_PNC_GIVEN_ON_TIME, pncGivenOnTime.getValueInString());
            return this;
        }

        public PNCVisitBuilder isCleaned(BooleanAnswer isCleaned) {
            visitData.put(Key.IS_CLEANED, isCleaned.getValueInString());
            return this;
        }

        public PNCVisitBuilder usedChlorhexidin(BooleanAnswer usedChlorhexidin) {
            visitData.put(Key.USED_CHLORHEXIDIN, usedChlorhexidin.getValueInString());
            return this;
        }

        public PNCVisitBuilder fedBreastMilk(BooleanAnswer fedBreastMilk) {
            visitData.put(Key.BREASMILK_FED, fedBreastMilk.getValueInString());
            return this;
        }

        public PNCVisit build() {
            PNCVisit ancVisit = new PNCVisit(this);
            this.visitData.clear();
            return ancVisit;
        }
    }

    private PNCVisit(PNCVisitBuilder pncVisitBuilder) {
        this.visitData = new HashMap<>(pncVisitBuilder.visitData);
    }
}
