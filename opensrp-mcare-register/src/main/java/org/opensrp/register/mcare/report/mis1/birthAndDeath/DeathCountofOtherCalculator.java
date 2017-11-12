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
public class DeathCountofOtherCalculator extends ReportCalculator {

    @DHIS2(dataElementId ="nnbO37HLsTj",categoryOptionId="PMxt6isoiYP",dataSetId="VvCMvqQWHa0")
    private long totalCountOfDeathofOther;
    public DeathCountofOtherCalculator(long startDateTime, long endDateTime) {
        super(startDateTime, endDateTime);
        this.initCountVariables();
    }

    @Override
    public void calculate(Members member) {

        this.totalCountOfDeathofOther += addTotalCountOfDeathofOther(member);
    }

    public long getTotalCountOfDeathofOther() {

        return totalCountOfDeathofOther;
    }

    public void initCountVariables() {

        this.totalCountOfDeathofOther = 0;
    }

    private long addTotalCountOfDeathofOther(Members member){
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
                if (member.getDeathReg().get("Deceased_Age_Group").equals("6")) {
                    value = 1;
                }else{
                    value=0;
                }
            }
        }


        return value;
    }
}
