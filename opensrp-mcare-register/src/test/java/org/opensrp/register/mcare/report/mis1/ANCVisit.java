package org.opensrp.register.mcare.report.mis1;


import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.domain.Members.BooleanAnswer;

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


        public ANCVisit.ANCVisitBuilder isReferred(BooleanAnswer isReferred) {
            visitData.put(Key.IS_REFERRED, isReferred.getValueInString());
            return this;
        }

        public ANCVisitBuilder misoprostolReceived(BooleanAnswer booleanAnswer) {
            visitData.put(Key.MISOPROSTOL_RECEIVED, booleanAnswer.getValueInString());
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
