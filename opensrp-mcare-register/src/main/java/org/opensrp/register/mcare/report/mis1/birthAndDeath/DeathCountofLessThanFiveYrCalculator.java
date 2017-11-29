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
public class DeathCountofLessThanFiveYrCalculator extends ReportCalculator {

    @DHIS2(dataElementId ="nnbO37HLsTj",categoryOptionId="zlga59Nvu4T",dataSetId="VvCMvqQWHa0")
    private long totalCountOfDeathofLessThanFiveYr;

    public DeathCountofLessThanFiveYrCalculator(long startDateTime, long endDateTime) {
        super(startDateTime, endDateTime);
        this.initCountVariables();
    }

    @Override
    public void calculate(Members member) {
        this.totalCountOfDeathofLessThanFiveYr += addTotalCountOfDeathofLessThanFiveYr(member);
    }

    public long getTotalCountofLessThanFiveYr() {

        return totalCountOfDeathofLessThanFiveYr;
    }

    public void initCountVariables() {

        this.totalCountOfDeathofLessThanFiveYr = 0;
    }

    private long addTotalCountOfDeathofLessThanFiveYr(Members member){
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
            if(dooDate.after(startDate) && dooDate.before(endDate) || dooDate.equals(startDate)) {

                if (member.getDeathReg().get("Deceased_Age_Group").equals("4")) {
                    //  long Deceased_Age_Group = Long.parseLong(member.getDeathReg().get("Deceased_Age_Group"));
                    // if (Deceased_Age_Group == 4) {
                    value = 1;
                    //}
                }
                else{
                    value =0;
                }
            }
        }


        return value;
    }
}
