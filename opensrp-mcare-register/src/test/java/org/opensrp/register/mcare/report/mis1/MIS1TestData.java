package org.opensrp.register.mcare.report.mis1;


import org.opensrp.register.mcare.domain.Members;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MIS1TestData {
    public List<Members> Members;
    public int resultCount;

    public MIS1TestData(List<org.opensrp.register.mcare.domain.Members> members, int resultCount) {
        Members = members;
        this.resultCount = resultCount;
    }

    public static MIS1TestData currentMonthTotalBirthControlPill(){
        List<Members> members = new ArrayList<>();
        for(int i=0; i<100; i++) {
            if(i<50){
                Members member = new Members();
                HashMap birthControlPillMap = new HashMap();
                birthControlPillMap.put("Birth_Control", "1");
                member.setDetails(birthControlPillMap);
                members.add(member);
            }else if(i<75){
                Members member = new Members();
                HashMap birthControlPillMap = new HashMap();
                birthControlPillMap.put("Birth_Control", "0");
                member.setDetails(birthControlPillMap);
                members.add(member);
            }
        }

        return new MIS1TestData(members, 50);
    }
}
