package org.opensrp.register.mcare.report.mis1.birthAndDeath;

import org.junit.Before;
import org.junit.Test;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.MIS1Report;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertEquals;


public class AliveDeathCountCalculatorTest {

    public String unionName = "union";
    private AliveDeathCountTestData aliveDeathCountTestData;
    private long startDateTime;
    private long endDateTime;

    @Before
    public void setUp() throws Exception {
        int totalCount = 10;
        int validCount = ThreadLocalRandom.current().nextInt(3, totalCount);

        startDateTime = 1501524000;
        endDateTime = 1504137600 ;

        aliveDeathCountTestData =
                new AliveDeathCountTestData(totalCount, validCount, startDateTime, endDateTime);
    }

    
  @Test
    public void totalAliveCountTest(){
        List<Members> members = aliveDeathCountTestData.getTotalAliveChild();
        MIS1Report mis1Report = new MIS1Report(unionName,members, startDateTime, endDateTime);
      long totalAliveChild = mis1Report.getBirthAndDeathReport().getBirthCountCalculator().getTotalCountOfLiveBirth();
      System.out.println("member size:" + members.size() + " validcount:" + aliveDeathCountTestData.validCount + " totalAliveChild:" + totalAliveChild);
      assertEquals(aliveDeathCountTestData.validCount, totalAliveChild);
    }

   @Test
    public void childWithUnderWeight(){
        List<Members> members = aliveDeathCountTestData.getChildUnderWeight();
        MIS1Report mis1Report = new MIS1Report(unionName,members, startDateTime, endDateTime);
        long totalChildWithUnderWeight = mis1Report.getBirthAndDeathReport().getTotalChildWithUnderWeight().getTotalChildWithUnderWeight();
        System.out.println("member size:" + members.size() + " validcount:" + aliveDeathCountTestData.validCount + " totalChildUnderWeight:" + totalChildWithUnderWeight);
        assertEquals(aliveDeathCountTestData.validCount, totalChildWithUnderWeight);
    }

    @Test
    public void prematureChild(){
        List<Members> members = aliveDeathCountTestData.getPrematureChild();
        MIS1Report mis1Report = new MIS1Report(unionName,members, startDateTime, endDateTime);
        long totalPrematureChild = mis1Report.getBirthAndDeathReport().getTotalPrematureChild().getTotalPrematureChild();
        System.out.println("member size:" + members.size() + " validcount:" + aliveDeathCountTestData.validCount + " totalPrematureChild:" + totalPrematureChild);
        assertEquals(aliveDeathCountTestData.validCount, totalPrematureChild);
    }

    @Test
    public void deathInLessThanSevenDays(){
        List<Members> members = aliveDeathCountTestData.getDeathInLessThanSevenDays();
        System.out.println("size of member:" + members.size());
        MIS1Report mis1Report = new MIS1Report( unionName , members , startDateTime , endDateTime);
        long deathInLessThanSevenDays = mis1Report.getBirthAndDeathReport().getTotalDeathCountofLessThanSevenDays().getTotalCountofLessThanSevenDays();
        System.out.println("member size:" + members.size() + " validcount:" + aliveDeathCountTestData.validCount + " deathInLessThanSevenDays:" + deathInLessThanSevenDays);
        assertEquals(aliveDeathCountTestData.validCount, deathInLessThanSevenDays);
    }

   @Test
    public void deathInLessThanTwnEightDays(){
        List<Members> members = aliveDeathCountTestData.getDeathInLessThanTwnEightDays();
        MIS1Report mis1Report = new MIS1Report( unionName , members , startDateTime , endDateTime);
        long deathInLessThanTwnEightDays = mis1Report.getBirthAndDeathReport().getTotalDeathCountofLessThanTwnEightDays().getTotalCountofLessThanTwnEightDays();
        System.out.println("member size:" + members.size() + " validcount:" + aliveDeathCountTestData.validCount + " deathInLessThanTwnEightDays:" + deathInLessThanTwnEightDays);
        assertEquals(aliveDeathCountTestData.validCount, deathInLessThanTwnEightDays);
    }

    @Test
    public void deathInLessThanOneyr(){
        List<Members> members = aliveDeathCountTestData.getDeathInLessThanOneYrDays();
        MIS1Report mis1Report = new MIS1Report( unionName , members , startDateTime , endDateTime);
        long deathInLessThanOneYr = mis1Report.getBirthAndDeathReport().getDeathCountofLessThanOneYr().getTotalCountofLessThanOneYr();
        System.out.println("member size:" + members.size() + " validcount:" + aliveDeathCountTestData.validCount + " deathInLessThanOneYr:" + deathInLessThanOneYr);
        assertEquals(aliveDeathCountTestData.validCount, deathInLessThanOneYr);
    }

    @Test
    public void deathInLessThanFiveyr(){
        List<Members> members = aliveDeathCountTestData.getDeathInLessThanFiveYrDays();
        MIS1Report mis1Report = new MIS1Report( unionName , members , startDateTime , endDateTime);
        long deathInLessThanFiveYr = mis1Report.getBirthAndDeathReport().getDeathCountofLessThanFiveYr().getTotalCountofLessThanFiveYr();
        System.out.println("member size:" + members.size() + " validcount:" + aliveDeathCountTestData.validCount + " deathInLessThanFiveYr:" + deathInLessThanFiveYr);
        assertEquals(aliveDeathCountTestData.validCount, deathInLessThanFiveYr);
    }

    @Test
    public void deathofMother(){
        List<Members> members = aliveDeathCountTestData.getDeathofMotherDays();
        MIS1Report mis1Report = new MIS1Report( unionName , members , startDateTime , endDateTime);
        long deathofMother = mis1Report.getBirthAndDeathReport().getDeathCountofMother().getTotalCountOfDeathofMother();
        System.out.println("member size:" + members.size() + " validcount:" + aliveDeathCountTestData.validCount + " deathofMother:" + deathofMother);
        assertEquals(aliveDeathCountTestData.validCount, deathofMother);
    }

    @Test
    public void deathofOther(){
        List<Members> members = aliveDeathCountTestData.getOtherDeathDays();
        MIS1Report mis1Report = new MIS1Report( unionName , members , startDateTime , endDateTime);
        long deathofOther = mis1Report.getBirthAndDeathReport().getDeathCountofOther().getTotalCountOfDeathofOther();
        System.out.println("member size:" + members.size() + " validcount:" + aliveDeathCountTestData.validCount + " deathofOther:" + deathofOther);
        assertEquals(aliveDeathCountTestData.validCount, deathofOther);
    }

}
