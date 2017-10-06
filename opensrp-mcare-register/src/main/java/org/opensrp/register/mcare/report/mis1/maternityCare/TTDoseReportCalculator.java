package org.opensrp.register.mcare.report.mis1.maternityCare;


import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.ReportCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.opensrp.register.mcare.domain.Members.EligibleCoupleVisitKeyValue.Key;
import static org.opensrp.register.mcare.domain.Members.VaccineDose;

public class TTDoseReportCalculator extends ReportCalculator {
    private long doseOneCount = 0;
    private long doseTwoCount = 0;
    private long doseThreeCount = 0;
    private long doseFourCount = 0;
    private long doseFiveCount = 0;


    public TTDoseReportCalculator(long startDateTime, long endDateTime) {
        super(startDateTime, endDateTime);
    }

    public long getDoseOneCount() {
        return doseOneCount;
    }


    public long getDoseTwoCount() {
        return doseTwoCount;
    }

    public long getDoseThreeCount() {
        return doseThreeCount;
    }

    public long getDoseFourCount() {
        return doseFourCount;
    }

    public long getDoseFiveCount() {
        return doseFiveCount;
    }


    @Override
    public void calculate(Members member) {
        List<Map<String, String>> elcoFollowUps = member.elco_Followup();
        for (Map<String, String> elcoFollowUp : elcoFollowUps) {
            if (withInStartAndEndTime(elcoFollowUp)) {
                String ttDoseStr = getTTDoseString(elcoFollowUp);
                if (validTTDoseString(ttDoseStr)) {
                    List<VaccineDose> vaccineDoses = VaccineDose.extractVaccineDoseListFrom(ttDoseStr);
                    addToCount(vaccineDoses);
                }
            }
        }
    }

    private String getTTDoseString(Map<String, String> followUp) {
        if (followUp.containsKey(Key.TT_DOSE)) {
            return followUp.get(Key.TT_DOSE);
        }
        return "";
    }

    private boolean validTTDoseString(String ttDoses) {
        return ttDoses != null && !ttDoses.isEmpty();
    }



    private void addToCount(List<VaccineDose> vaccineDoses) {
        for(VaccineDose vaccineDose: vaccineDoses) {
            switch (vaccineDose) {
                case ONE:
                    doseOneCount ++;
                    break;
                case TWO:
                    doseTwoCount ++;
                    break;
                case THREE:
                    doseThreeCount ++;
                    break;
                case FOUR:
                    doseFourCount ++;
                    break;
                case FIVE:
                    doseFiveCount ++;
                    break;
            }
        }
    }

}
