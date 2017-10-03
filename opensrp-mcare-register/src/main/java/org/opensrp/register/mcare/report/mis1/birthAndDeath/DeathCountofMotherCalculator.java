package org.opensrp.register.mcare.report.mis1.birthAndDeath;

import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.Report;
import org.opensrp.register.mcare.report.mis1.ReportCalculator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by asha on 9/28/17.
 */
public class DeathCountofMotherCalculator extends ReportCalculator {

    private long totalCountOfDeathofMother;
    public DeathCountofMotherCalculator(long startDateTime, long endDateTime) {
        super(startDateTime, endDateTime);
        this.initCountVariables();
    }

    @Override
    public void calculate(Members member) {

        this.totalCountOfDeathofMother += addTotalCountOfDeathofMother(member);
    }

    public long getTotalCountOfDeathofMother() {

        return totalCountOfDeathofMother;
    }

    public void initCountVariables() {

        this.totalCountOfDeathofMother = 0;
    }

    private long addTotalCountOfDeathofMother(Members member) {
        long value = 0;

        if( member.getDeathReg().get("DOO") != null ){
            String deliveryDateStr = member.getDeathReg().get("DOO");

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date dooDate = null;
            Date startDate = null;
            Date endDate = null;

            try {
                dooDate = simpleDateFormat.parse(deliveryDateStr);
                startDate = new Date(startDateTime * 1000);
                endDate = new Date(endDateTime * 1000);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (dooDate.after(startDate) && dooDate.before(endDate) || dooDate.equals(startDate)) {
                if (member.getDeathReg().get("Deceased_Age_Group") != null && member.getDeathReg().get("Deceased_Age_Group") == "5") {
                    value = 1;
                }else{
                    value = 0;
                }
            }
        }

        return value;
    }
}
