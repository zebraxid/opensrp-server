package org.opensrp.register.mcare.report.mis1.maternityCare;


import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.BNFVisit;
import org.opensrp.register.mcare.report.mis1.MIS1TestData;

public class PostpartumCareTestData extends MIS1TestData{

    BNFVisit.BNFVisitBuilder bnfVisitBuilder;

    public PostpartumCareTestData(int totalCount, int validCount, long startDateTime, long endDateTime) {
        super(totalCount, validCount, startDateTime, endDateTime);
        this.bnfVisitBuilder = new BNFVisit.BNFVisitBuilder();
    }

  /*  public Members createBirthAtHomeWithTrainedPersonTestData() {

    }*/
}
