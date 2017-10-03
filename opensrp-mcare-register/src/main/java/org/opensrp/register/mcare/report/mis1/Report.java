package org.opensrp.register.mcare.report.mis1;

import org.opensrp.register.mcare.domain.Members;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;

/**
 * Base class for Report. Every report should extends this class.
 */
public abstract class Report {

	public Report(long startDateTime, long endDateTime) {
		this.initCalculators(startDateTime, endDateTime);
	}

	/**
	 * Concrete class should implement this method to calculate their desired method to based on current
	 * Member.
	 * e.g: whether current member is pregnant or not.
	 *
	 * @param member
	 */
	protected abstract void calculate(Members member);

	/**
	 * Concrete class should implement this method to init their internal report calculator objects.
	 *
	 * @param startDateTime
	 * @param endDateTime
	 */
	protected abstract void initCalculators(long startDateTime, long endDateTime);

	/**
	 * This function uses reflection to init all the internal report calculator objects of a report.
	 * It assumes that an `Report` object only has `ReportCalculator` objects.
	 * TODO: Remove dependency (It assumes that an `Report` object only has `ReportCalculator` objects.)
	 *
	 * @param reportClass
	 * @param startDateTime
	 * @param endDateTime
	 */
	protected void useReflectionToDynamicallyInitAllMemberOf(Class reportClass, long startDateTime, long endDateTime) {
		Field[] fields = reportClass.getDeclaredFields();
		for (Field field : fields) {
			if (!field.isSynthetic()) {
				field.setAccessible(true);
				try {
					Class classOfFiled = Class.forName(field.getType().getName());
					Constructor constructor = classOfFiled.getConstructor(long.class, long.class);
					field.set(this, constructor.newInstance(startDateTime, endDateTime));
				}
				catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
				catch (InstantiationException e) {
					e.printStackTrace();
				}
				catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				catch (InvocationTargetException e) {
					System.out.println("all data:" );
					e.printStackTrace();
				}
				catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * This function uses reflection to call `calculate` method of all the internal report calculator objects of a report.
	 * It assumes that an `Report` object only has `ReportCalculator` objects.
	 * TODO: Remove dependency (It assumes that an `Report` object only has `ReportCalculator` objects.)
	 *
	 * @param reportClass
	 * @param member
	 */
		protected void useReflectionToDynamicallyCallCalculateMethodOnAllMemberOf(Class reportClass, Members member) {
		Field[] fields = reportClass.getDeclaredFields();
		for (Field field : fields) {
			if (!field.isSynthetic()) {
				field.setAccessible(true);
				try {
					Object fieldValue = field.get(this);
					Method calculate = fieldValue.getClass().getMethod("calculate", Members.class);
					//System.out.println( " calculate::" + calculate);
					calculate.invoke(fieldValue, member);
				}
				catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
				catch (InvocationTargetException e) {
					//System.out.println( " field::" + field);
					e.printStackTrace();
				}
			}
		}
	}
}
