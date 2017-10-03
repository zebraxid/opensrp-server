package org.opensrp.register.mcare.report.mis1.birthAndDeath;


import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.Report;
import org.springframework.beans.factory.annotation.Autowired;

public class BirthAndDeathReport extends Report {


    private BirthCountCalculator birthCountCalculator;
    private ChildWithUnderWeightCalculator childWithUnderWeightCalculator;
    private PrematureChildCountCalculator prematureChildCountCalculator;
    private DeathCountofLessThanFiveYrCalculator deathCountofLessThanFiveYrCalculator;
    private DeathCountofLessThanOneYrCalculator deathCountofLessThanOneYrCalculator;
    private DeathCountofLessThanSevenDaysCalculator deathCountofLessThanSevenDaysCalculator;
    private DeathCountofLessThanTwnEightDaysCalculator deathCountofLessThanTwnEightDaysCalculator;
    private DeathCountofMotherCalculator deathCountofMotherCalculator;
    private DeathCountofOtherCalculator deathCountofOtherCalculator;

    public BirthAndDeathReport(long startDateTime, long endDateTime) {
        super(startDateTime, endDateTime);

    }

    @Override
    public void calculate(Members member) {
      useReflectionToDynamicallyCallCalculateMethodOnAllMemberOf(this.getClass(), member);
    }

    @Override
    protected void initCalculators(long startDateTime, long endDateTime) {
        useReflectionToDynamicallyInitAllMemberOf(this.getClass(), startDateTime, endDateTime);
    }

    public BirthCountCalculator getBirthCountCalculator() {

        return birthCountCalculator;
    }

    public ChildWithUnderWeightCalculator getTotalChildWithUnderWeight() {

        return childWithUnderWeightCalculator;
    }

    public PrematureChildCountCalculator getTotalPrematureChild(){
        return  prematureChildCountCalculator;
    }

    public DeathCountofLessThanSevenDaysCalculator getTotalDeathCountofLessThanSevenDays(){
        return deathCountofLessThanSevenDaysCalculator;
    }

    public DeathCountofLessThanTwnEightDaysCalculator getTotalDeathCountofLessThanTwnEightDays(){
        return deathCountofLessThanTwnEightDaysCalculator;
    }

    public DeathCountofLessThanOneYrCalculator getDeathCountofLessThanOneYr(){

        return deathCountofLessThanOneYrCalculator;
    }

    public DeathCountofLessThanFiveYrCalculator getDeathCountofLessThanFiveYr(){
        return deathCountofLessThanFiveYrCalculator;
    }

    public DeathCountofMotherCalculator getDeathCountofMother(){
        return deathCountofMotherCalculator;
    }

    public DeathCountofOtherCalculator getDeathCountofOther(){
        return deathCountofOtherCalculator;
    }
}
