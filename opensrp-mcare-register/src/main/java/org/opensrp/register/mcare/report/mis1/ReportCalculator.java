package org.opensrp.register.mcare.report.mis1;


import org.opensrp.register.mcare.domain.Members;

import java.util.Map;

public abstract class ReportCalculator {
    protected long startDateTime;
    protected long endDateTime;

    public ReportCalculator(long startDateTime, long endDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public abstract void calculate(Members member);

    protected boolean withInStartAndEndTime(Map<String, String> visitData) {
        if(visitData.containsKey(Members.CLIENT_VERSION_KEY)) {
            long clientVersion = Long.parseLong(visitData.get(Members.CLIENT_VERSION_KEY));
            if(clientVersion >= startDateTime && clientVersion <= endDateTime) {
                return true;
            }
        }
        return false;
    }
}
