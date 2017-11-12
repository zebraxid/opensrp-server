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
public class DeathCountofLessThanTwnEightDaysCalculator extends ReportCalculator {

    @DHIS2(dataElementId ="nnbO37HLsTj",categoryOptionId="tvXyBlG3shM",dataSetId="VvCMvqQWHa0")
    private long totalCountOfDeathofLessThanTwnEightDays ;

    public DeathCountofLessThanTwnEightDaysCalculator(long startDateTime, long endDateTime) {
        super(startDateTime, endDateTime);
        this.initCountVariables();
    }

    @Override
    public void calculate(Members member) {
        this.totalCountOfDeathofLessThanTwnEightDays += addTotalCountOfDeathofLessThanTwnEightDay(member);
    }

    public long getTotalCountofLessThanTwnEightDays() {

        return totalCountOfDeathofLessThanTwnEightDays;
    }

    public void initCountVariables() {
        this.totalCountOfDeathofLessThanTwnEightDays = 0;
    }

   private long addTotalCountOfDeathofLessThanTwnEightDay(Members member){
        long value=0;

       Map<String , String> deathReg = member.getDeathReg();
       if( deathReg.containsKey("death_today")){
           System.out.println("in1");
           String deathDateStr = member.getDeathReg().get("death_today");

           SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dooDate = null;
            Date startDate = null;
            Date endDate = null;

            try {
                dooDate = simpleDateFormat.parse(deathDateStr);
                startDate = new Date( startDateTime);
                endDate = new Date( endDateTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if(dooDate.after(startDate) && dooDate.before(endDate) || dooDate.equals(startDate)){
                System.out.println("in2");
                if (member.getDeathReg().get("Deceased_Age_Group").equals("2")) {
                    value = 1;
                }else {
                    value = 0;
                }

            }
        }

        return value;
   }

}
