package org.opensrp.register.mcare.report.mis1.familyPlanning;

import org.opensrp.connector.DHIS2.dxf2.DHIS2;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.Report;
import org.opensrp.register.mcare.report.mis1.familyPlanning.birthControlMethdoUsagesCalculation.*;
import org.opensrp.register.mcare.report.mis1.familyPlanning.eligibleCoupleCount.EligibleCoupleCountCalculator;


public class FamilyPlanningReport extends Report {
    private EligibleCoupleCountCalculator eligibleCoupleCountCalculator;
    @DHIS2(dateElementId = "d1")
    private PillMethodUsagesCalculator pillUsagesCalculator;
    @DHIS2(dateElementId = "d2")
    private CondomMethodUsagesCalculator condomUsagesCalculator;
    private IUDUsagesCalculator iudUsagesCalculator;
    //private InjectableUsagesCalculator injectableUsagesCalculator;
    private ImplantUsagesCalculator implantUsagesCalculator;
    private MalePermanentMethodUsagesCalculator malePermanentMethodUsagesCalculator;
    private FemalePermanentMethodUsagesCalculator femalePermanentMethodUsagesCalculator;

    public FamilyPlanningReport(long startDateTime, long endDateTime) {
        super(startDateTime, endDateTime);
    }

    /**
     * Use base classes reflection method to init all internal calculators.
     *
     * @param startDateTime
     * @param endDateTime
     */
    @Override
    protected void initCalculators(long startDateTime, long endDateTime) {
      //  System.err.println("WWWWWWWWWWWWWWWWWWW" + this.getClass());
        this.useReflectionToDynamicallyInitAllMemberOf(this.getClass(), startDateTime, endDateTime);
    }

    /**
     * Use base classes reflection method to call `calculate` method on all calculator.
     *
     * @param member
     */
    @Override
    public void calculate(Members member) {
      //  System.err.println("WWWWWWWWWWWWWWWWWWW" + this.getClass());
        this.useReflectionToDynamicallyCallCalculateMethodOnAllMemberOf(this.getClass(), member);
    }

    public PillMethodUsagesCalculator getPillUsagesCalculator() {

        return pillUsagesCalculator;
    }

    public CondomMethodUsagesCalculator getCondomUsagesCalculator() {
        return condomUsagesCalculator;
    }

   /* public InjectableUsagesCalculator getInjectableUsagesCalculator() {

        return injectableUsagesCalculator;
    }*/
    public IUDUsagesCalculator getIudUsagesCalculator (){

        return  iudUsagesCalculator;
    }
    public ImplantUsagesCalculator getImplantUsagesCalculator(){

        return implantUsagesCalculator;
    }

    public EligibleCoupleCountCalculator getEligibleCoupleCountCalculator()
    {
        return eligibleCoupleCountCalculator;
    }

}
