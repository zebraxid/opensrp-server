package org.opensrp.register.mcare.report.mis1.birthAndDeath;


import org.opensrp.connector.DHIS2.dxf2.DHIS2;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.ReportCalculator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChildWithUnderWeightCalculator extends ReportCalculator {

    @DHIS2(dataElementId ="m1KL4jyNQMt",categoryOptionId="URSu6Wxv8KV",dataSetId="VvCMvqQWHa0")
    private long totalChildWithUnderWeight ;

    public ChildWithUnderWeightCalculator(long startDateTime, long endDateTime) {
        super(startDateTime, endDateTime);
        this.initCountVariables();
    }

    public long getTotalChildWithUnderWeight(){
        return totalChildWithUnderWeight;
    }

    public void initCountVariables() {
        this.totalChildWithUnderWeight = 0;
    }

    @Override
    public void calculate(Members member) {

        this.totalChildWithUnderWeight += countTotalChildWithUnderWeight(member);
    }

    private long countTotalChildWithUnderWeight(Members member) {
        Date dooDate = null;
        Date startDate = null;
        Date endDate = null;
        long totalunderweightcount = 0;
        if( member.details().get("DOO") != null ){
            String deliveryDateStr = member.details().get("DOO");

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

            try {
                dooDate = simpleDateFormat.parse(deliveryDateStr);
                startDate = new Date( startDateTime * 1000);
                endDate = new Date( endDateTime * 1000);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if(dooDate.after(startDate) && dooDate.before(endDate) || dooDate.equals(startDate)){

                if( member.details().get("Child_Weight") != null ){
                    System.out.println("Child_Weight::: " + member.details().get("Child_Weight") + " dooDate::: " + dooDate);
                    String childWeightStr = member.details().get("Child_Weight");
                    float childWeight = Float.parseFloat(childWeightStr);
                    if( childWeight < 2.5 )
                        totalunderweightcount = 1;
                }
            }
            else{
                totalunderweightcount =0 ;
            }


        }

        return totalunderweightcount;
    }
}
