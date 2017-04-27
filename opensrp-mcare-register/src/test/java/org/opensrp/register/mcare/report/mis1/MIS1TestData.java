package org.opensrp.register.mcare.report.mis1;


import com.sun.javaws.exceptions.InvalidArgumentException;
import org.opensrp.register.mcare.domain.Members;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public abstract class MIS1TestData {
    public int totalCount;
    public int validCount;
    public long startDateTime;
    public long endDateTime;

    public MIS1TestData(int totalCount, int validCount, long startDateTime, long endDateTime) {
        this.totalCount = totalCount;
        this.validCount = validCount;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public static long getRandomNumberBetween(long start, long end) {
        return ThreadLocalRandom.current().nextLong(start, end);
    }

    public static int getRandomNumberBetween(int start, int end) {
        return ThreadLocalRandom.current().nextInt(start, end);
    }

    protected Members createMemberWithEligibleCoupleFollowUpList(List<Map<String, String>> followUpList) {
        Members member = new Members();
        member.setelco_Followup(followUpList);
        return member;
    }

    protected Members createMemberWithClientVersion(long clientVersion) {
        Members member = new Members();
        member.setClientVersion(clientVersion);
        return member;
    }

    protected Members createMemberWithANCVisit(VisitNumber visitNumber, Map<String, String> visitData)  {
        Members member = new Members();
        switch (visitNumber){
            case one:
                return member.setANCVisit1(visitData);
            case two:
                return member.setANCVisit2(visitData);
        }

        throw new IllegalArgumentException("Invalid visit number: " + visitNumber.toString());
    }

    public enum VisitNumber {
        one,
        two,
        three,
        four
    }
}
