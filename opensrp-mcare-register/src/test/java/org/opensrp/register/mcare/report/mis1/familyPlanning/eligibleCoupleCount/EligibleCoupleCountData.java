package org.opensrp.register.mcare.report.mis1.familyPlanning.eligibleCoupleCount;


import org.opensrp.register.mcare.domain.Members;

import java.util.ArrayList;
import java.util.List;

public class EligibleCoupleCountData {
    public int totalCount;
    public int validCount;
    public long startDateTime;
    public long endDateTime;

    public EligibleCoupleCountData(int totalCount, int validCount, long startDateTime, long endDateTime) {
        this.totalCount = totalCount;
        this.validCount = validCount;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public List<Members> getNewEligibleCoupleVistData(){
        List<Members> allMembers = new ArrayList<>();
       // for()
        return allMembers;
    }
}
