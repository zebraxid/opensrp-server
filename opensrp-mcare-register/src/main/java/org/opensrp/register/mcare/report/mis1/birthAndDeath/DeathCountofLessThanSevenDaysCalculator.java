package org.opensrp.register.mcare.report.mis1.birthAndDeath;

import org.opensrp.connector.DHIS2.dxf2.DHIS2;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.ReportCalculator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by asha on 9/28/17.
 */
public class DeathCountofLessThanSevenDaysCalculator extends ReportCalculator {

    @DHIS2(dataElementId ="nnbO37HLsTj",categoryOptionId="QypvSQnrOeY",dataSetId="VvCMvqQWHa0")
    private long totalCountOfDeathofLessThanSevenDays;

    public DeathCountofLessThanSevenDaysCalculator(long startDateTime, long endDateTime) {
        super(startDateTime, endDateTime);
        this.initCountVariables();
    }

    public long getTotalCountofLessThanSevenDays() {

        return totalCountOfDeathofLessThanSevenDays;
    }

    public void initCountVariables() {

        this.totalCountOfDeathofLessThanSevenDays = 0;
    }

    @Override
    public void calculate(Members member) {
        this.totalCountOfDeathofLessThanSevenDays += addTotalCountOfDeathofLessThanSevenDays(member);
    }

    private long addTotalCountOfDeathofLessThanSevenDays(Members member){

        long value=0;

        Map<String , String> deathReg = member.getDeathReg();
        if( deathReg.containsKey("death_today")){
            String deathDateStr = member.getDeathReg().get("death_today");

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dooDate = null;
            Date startDate = null;
            Date endDate = null;

            try {
                dooDate = simpleDateFormat.parse(deathDateStr);
                startDate = new Date( startDateTime * 1000);
                endDate = new Date( endDateTime * 1000);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if(dooDate.after(startDate) && dooDate.before(endDate) || dooDate.equals(startDate)){

                if (member.getDeathReg().get("Deceased_Age_Group").equals("1")) {
                    value = 1;
                }
                else {
                    value = 0;
                }

            }

        }

        return value;
    }
}
