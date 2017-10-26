package org.opensrp.register.mcare.report.mis1.familyPlanning;

import org.opensrp.connector.DHIS2.dxf2.DHIS2;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.Report;
import org.opensrp.register.mcare.report.mis1.familyPlanning.birthControlMethdoUsagesCalculation.*;
import org.opensrp.register.mcare.report.mis1.familyPlanning.eligibleCoupleCount.EligibleCoupleCountCalculator;


public class FamilyPlanningReport extends Report {
    @DHIS2(dataElementId ="qQRGs57YZ2z")
    private EligibleCoupleCountCalculator eligibleCoupleCountCalculator;
    @DHIS2(dataElementId="Kt4PihqO6KL")
    private PillMethodUsagesCalculator pillUsagesCalculator;
    @DHIS2(dataElementId ="rQKuOKwecFt")
    private CondomMethodUsagesCalculator condomUsagesCalculator;
    @DHIS2(dataElementId="V6hsObJOf8O")
    private IUDUsagesCalculator iudUsagesCalculator;
    @DHIS2(dataElementId="WJQ7zP7o5HU")
    private InjectableUsagesCalculator injectableUsagesCalculator;
    @DHIS2(dataElementId="GTORqGy67Jm")
    private ImplantUsagesCalculator implantUsagesCalculator;
    @DHIS2(dataElementId="SFwYAnTq2cH")
    private MalePermanentMethodUsagesCalculator malePermanentMethodUsagesCalculator;
    @DHIS2(dataElementId="cZ4qzXVjQPL")
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

    public InjectableUsagesCalculator getInjectableUsagesCalculator() {

        return injectableUsagesCalculator;
    }
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
