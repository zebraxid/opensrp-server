package org.opensrp.register.mcare.report.mis1.birthAndDeath;


import org.opensrp.connector.DHIS2.dxf2.DHIS2;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.ReportCalculator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

        Map<String , String> detailsMap = member.details();
        if( detailsMap.containsKey("DOO")){
            if( member.details().get("DOO").length() != 0 ){
                String deliveryDateStr = member.details().get("DOO");

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

                try {
                    dooDate = simpleDateFormat.parse(deliveryDateStr);
                    startDate = new Date( startDateTime);
                    endDate = new Date( endDateTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(detailsMap.containsKey("Child_Weight")){
                    System.out.println("Details::: " + member.details().toString());

                    if(dooDate.after(startDate) && dooDate.before(endDate) || dooDate.equals(startDate)){
                            String childWeightStr = member.details().get("Child_Weight");
                            float childWeight = Float.parseFloat(childWeightStr);
                            System.out.println("float:" + childWeight + " string:" + childWeightStr);
                            if( childWeight < 2.5 )
                                totalunderweightcount = 1;

                    }
                    else{
                        totalunderweightcount =0 ;
                    }
                }
                }



        }

        return totalunderweightcount;
    }
}
