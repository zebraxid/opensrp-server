package org.opensrp.register.mcare.report.mis1.familyPlanning;


import org.opensrp.register.mcare.domain.Members;

public class CondomUsagesCalculator extends FamilyPlanningReportCalculator {


    public CondomUsagesCalculator(){
        super();
    }

    public void calculate(Members member){
        this.countOfTotalUsages += addToTheCountOfTotalCondomUsages(member);
       /* this.countOfNewUsages += addToTheCountOfNewPillUsages(member);
        this.countOfLeftUsagesButNoneTaken += addToTheCountOfLeftPillUsagesButNoneTaken(member);
        this.countOfLeftUsagesButOtherTaken += addToTheCountOfLeftPillUsagesButOtherTaken(member);*/
    }


    private int addToTheCountOfTotalCondomUsages(Members member){
        boolean usingCondomInMemberDetail =
                checkMemberFieldValue(member.details(), Members.BIRTH_CONTROL_KEY, Members.BIRTH_CONTROL_CONDOM);
        if (usingCondomInMemberDetail) {
            return 1;
        }
        return 0;
    }
}
