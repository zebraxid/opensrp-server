package org.opensrp.register.mcare.report.mis1;


import org.opensrp.register.mcare.domain.Members;

public abstract class Report {

     public abstract void calculate(Members member);

    protected abstract void initCalculators(long startDateTime, long endDateTime);
}
