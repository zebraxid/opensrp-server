package org.opensrp.register.mcare.report.mis1;


import org.opensrp.register.mcare.domain.Members;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
            case three:
                return member.setANCVisit3(visitData);
            case four:
                return member.setANCVisit4(visitData);
        }

        throw new IllegalArgumentException("Invalid visit number: " + visitNumber.toString());
    }

    protected Members createMemberWithBNFVisits(List<Map<String, String >> bnfVisits) {
        Members member = new Members();
        return member.setbnfVisit(bnfVisits);
    }

    protected Members createMemberWithPNCVisit(VisitNumber visitNumber, Map<String , String> pncVisit) {
        Members members = new Members();
        switch (visitNumber) {
            case one:
              return members.setPNCVisit1(pncVisit);
            case two:
                return members.setPNCVisit2(pncVisit);
            case three:
                return members.setPNCVisit3(pncVisit);
            case four:
                return members.setPNCVisit4(pncVisit);

        }
        throw new IllegalArgumentException();
    }

    protected Members createMemberWithAdolescentHealthVisits(List<Map<String, String>> adolescentHealthVisits) {
        Members member = new Members();
        member.setadolescent(adolescentHealthVisits);
        return member;
    }

    public enum VisitNumber {
        INVALID(-1),
        one(1),
        two(2),
        three(3),
        four(4);

        public int value;

        VisitNumber(int value) {
            this.value = value;
        }

        private static Map<Integer, VisitNumber> map = new HashMap<Integer, VisitNumber>();

        static {
            for (VisitNumber visitNumber : VisitNumber.values()) {
                map.put(visitNumber.value, visitNumber);
            }
        }

        public static VisitNumber fromStr(String value) {
            if(value == null || value.isEmpty()) {
                return INVALID;
            }else {
                return fromInt(Integer.parseInt(value));
            }
        }

        public static VisitNumber fromInt(int value) {
            if(map.containsKey(value)) {
                return map.get(value);
            }

            return INVALID;
        }

        public Integer getValue() {
            return this.value;
        }

        public String getValueInString() {
            return this.getValue().toString();
        }
    }
}
