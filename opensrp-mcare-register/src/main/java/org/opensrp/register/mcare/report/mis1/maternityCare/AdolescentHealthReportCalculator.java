package org.opensrp.register.mcare.report.mis1.maternityCare;


import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.ReportCalculator;

import java.util.List;
import java.util.Map;

import static org.opensrp.register.mcare.domain.Members.AdolescentHealthVisitKeyValue.*;

public class AdolescentHealthReportCalculator extends ReportCalculator {

    long countOfCounsellingOnChangesOfAdolescent = 0;

    public AdolescentHealthReportCalculator(long startDateTime, long endDateTime) {
        super(startDateTime, endDateTime);
    }

    public long getCountOfCounsellingOnChangesOfAdolescent() {
        return countOfCounsellingOnChangesOfAdolescent;
    }

    @Override
    public void calculate(Members member) {
        List<Map<String, String>> visits = member.adolescent();
        for(Map<String , String> visit: visits) {
            if(withInStartAndEndTime(visit)) {
                countOfCounsellingOnChangesOfAdolescent += addToCountFor(visit, CounsellingType.ON_ADOLESCENT);
            }
        }
    }

    public int addToCountFor(Map<String, String> visitData, CounsellingType counsellingType) {
        String counsellingTypeStr = visitData.get(Key.COUNSELLING);
        List<CounsellingType> counsellingTypeList = CounsellingType.extractCounsellingTypeListFrom(counsellingTypeStr);
        for (CounsellingType type: counsellingTypeList) {
            if (type == counsellingType) {
                return 1;
            }
        }

        return 0;
    }
}
