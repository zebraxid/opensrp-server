package org.opensrp.register.mcare.report.mis1;


import org.opensrp.register.mcare.domain.Members;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;

public abstract class Report {

    public Report(long startDateTime, long endDateTime) {
        this.initCalculators(startDateTime, endDateTime);
    }

     protected abstract void calculate(Members member);

    protected abstract void initCalculators(long startDateTime, long endDateTime);


    protected void useReflectionToDynamicallyInitAllMemberOf(Class reportClass, long startDateTime, long endDateTime) {
        Field[] fields = reportClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Constructor constructor = reportClass.getConstructor(long.class, long.class);
                field.set(this, constructor.newInstance(startDateTime, endDateTime));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    protected void useReflectionToDynamicallyCallCalculateMethodOnAllMemberOf(Class reportClass, Members member) {
        Field[] fields = reportClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object fieldValue = field.get(this);
                Method calculate = fieldValue.getClass().getMethod("calculate", Members.class);
                calculate.invoke(fieldValue, member);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
