package org.opensrp.register.mcare.report.mis1.maternityCare;


import org.junit.Before;
import org.junit.Test;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.MIS1Report;
import org.opensrp.register.mcare.report.mis1.maternityCare.data.PregnantWomenCountTestData;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertEquals;

public class PregnantWomenCountTest {
    public String unionName = "union";
    private PregnantWomenCountTestData pregnantWomenCountTestData;
    private long startDateTime;
    private long endDateTime;

    @Before
    public void setUp() throws Exception {
        int totalCount = 1000;
        int validCount = ThreadLocalRandom.current().nextInt(0, totalCount);
        startDateTime = ThreadLocalRandom.current().nextLong(120000, 140000);
        endDateTime = ThreadLocalRandom.current().nextLong(170000, 200000);
        pregnantWomenCountTestData =
                new PregnantWomenCountTestData(totalCount, validCount, startDateTime, endDateTime);
    }

    @Test
    public void testNewPregnantWomenCount() {
        List<Members> allMembers = pregnantWomenCountTestData.getDataForNewPregnantWomenCount();
        MIS1Report mis1Report = new MIS1Report(unionName, allMembers, startDateTime, endDateTime);

        long newPregnantWomenCount = mis1Report.getMaternityCareReport().getPregnantWomenCountCalculator().getNewPregnantCount();

        assertEquals(pregnantWomenCountTestData.validCount, newPregnantWomenCount);
    }
}

