package org.opensrp.register.mcare.report.mis1.maternityCare;


import org.junit.Before;
import org.junit.Test;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.MIS1Report;
import org.opensrp.register.mcare.report.mis1.MIS1TestData;
import org.opensrp.register.mcare.report.mis1.maternityCare.data.PostpartumCareTestData;

import java.util.List;

import static junit.framework.Assert.assertEquals;

public class PostpartumCareTest {
    public String unionName = "union";
    private PostpartumCareTestData postpartumCareTestData;
    private long startDateTime;
    private long endDateTime;

    @Before
    public void setUp() throws Exception {
        int totalCount = 1000;
        int validCount = MIS1TestData.getRandomNumberBetween(0, totalCount);
        startDateTime = MIS1TestData.getRandomNumberBetween(120000, 140000);
        endDateTime = MIS1TestData.getRandomNumberBetween(170000, 200000);
        postpartumCareTestData =
                new PostpartumCareTestData(totalCount, validCount, startDateTime, endDateTime);
    }

    @Test
    public void testBirthAtHomeWithTrainedPerson() {
        List<Members> allMembers = postpartumCareTestData.createBirthAtHomeWithTrainedPersonTestData();
        MIS1Report mis1Report = new MIS1Report(unionName, allMembers, startDateTime, endDateTime);
        long birthAtHomeWithTrainedPerson = mis1Report.getMaternityCareReport().getPostpartumCareCalculator().getCountOfBirthAtHomeWithTrainedPerson();
        assertEquals(postpartumCareTestData.validCount, birthAtHomeWithTrainedPerson);
    }

    @Test
    public void testNormalBirthAtHospitalOrClinic() {
        List<Members> allMembers = postpartumCareTestData.createNormalBirthAtHospitalOrClinicTestData();
        MIS1Report mis1Report = new MIS1Report(unionName, allMembers, startDateTime, endDateTime);
        long normalBirthAtHospitalOrClinic = mis1Report.getMaternityCareReport().getPostpartumCareCalculator().getCountOfNormalBirthAtHospitalOrClinic();
        assertEquals(postpartumCareTestData.validCount, normalBirthAtHospitalOrClinic);
    }

}
