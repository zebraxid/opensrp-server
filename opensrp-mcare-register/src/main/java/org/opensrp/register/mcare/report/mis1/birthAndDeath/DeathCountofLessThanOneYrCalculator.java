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
public class DeathCountofLessThanOneYrCalculator extends ReportCalculator {

    private long totalCountOfDeathofLessThanOneYr;
    public DeathCountofLessThanOneYrCalculator(long startDateTime, long endDateTime) {
        super(startDateTime, endDateTime);
        this.initCountVariables();
    }

    @Override
    public void calculate(Members member) {
        this.totalCountOfDeathofLessThanOneYr += addTotalCountOfDeathofLessThanOneYr(member);
    }

    public long getTotalCountofLessThanOneYr() {

        return totalCountOfDeathofLessThanOneYr;
    }

    public void initCountVariables() {

        this.totalCountOfDeathofLessThanOneYr = 0;
    }

    private long addTotalCountOfDeathofLessThanOneYr(Members member){
        long value=0;

        if( member.getDeathReg().get("DOO") != null ){
            String deliveryDateStr = member.getDeathReg().get("DOO");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date dooDate = null;
            Date startDate = null;
            Date endDate = null;

            try {
                dooDate = simpleDateFormat.parse(deliveryDateStr);
                startDate = new Date( startDateTime * 1000);
                endDate = new Date( endDateTime * 1000);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if(dooDate.after(startDate) && dooDate.before(endDate) || dooDate.equals(startDate)){
                //System.out.println("check Deceased_Age_Group ::: " + member.getDeathReg().get("Deceased_Age_Group"));
                if (member.getDeathReg().get("Deceased_Age_Group") != null && member.getDeathReg().get("Deceased_Age_Group") == "3") {
                    value = 1;
                }else {
                    value = 0;
                }

            }
        }

        return value;
    }
}
