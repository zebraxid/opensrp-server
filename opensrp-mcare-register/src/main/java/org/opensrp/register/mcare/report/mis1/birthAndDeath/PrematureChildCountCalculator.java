package org.opensrp.register.mcare.report.mis1.birthAndDeath;

import org.opensrp.connector.DHIS2.dxf2.DHIS2;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.ReportCalculator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by asha on 9/26/17.
 */
public class PrematureChildCountCalculator extends ReportCalculator {

    @DHIS2(dataElementId ="m1KL4jyNQMt",categoryOptionId="GkwIERMWu28",dataSetId="VvCMvqQWHa0")
    private long totalPrematureChild;
    public PrematureChildCountCalculator(long startDateTime, long endDateTime) {
        super(startDateTime, endDateTime);
        this.initCountVariables();
    }

    public long getTotalPrematureChild() {

        return totalPrematureChild;
    }

    public void initCountVariables() {
        this.totalPrematureChild = 0;
    }


    @Override
    public void calculate(Members member) {

        this.totalPrematureChild += countTotalPrematureChild(member);
    }

    private long countTotalPrematureChild(Members member){

        long value = 0;
        Map<String , String> detailsMap = member.details();
        if( detailsMap.containsKey("DOO")){
            if( member.details().get("DOO").length() != 0 ){
                String deliveryDateStr = member.details().get("DOO");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
                Date dooDate = null;
                Date startDate = null;
                Date endDate = null;

                try {
                    dooDate = simpleDateFormat.parse(deliveryDateStr);
                    startDate = new Date( startDateTime);
                    endDate = new Date( endDateTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
          if(dooDate.after(startDate) && dooDate.before(endDate) || dooDate.equals(startDate)){

                    if( member.details().get("Premature_Birth") != null){
                       // System.out.println("Premature_Birth::: " + member.details().get("Premature_Birth") + " dooDate::: " + dooDate);
                        value =  1 ;
                    }
                    else{
                        value = 0;
                    }

                }
            }

        }
        return value;
    }
}
