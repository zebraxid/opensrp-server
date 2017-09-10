package org.opensrp.register.mcare.report.mis1;

import org.opensrp.register.mcare.domain.Members;

import java.util.Map;

/**
 * Base abstract class for report calculation. Every report calculator should extends this class.
 */
public abstract class ReportCalculator {

	protected long startDateTime;

	protected long endDateTime;

	public ReportCalculator(long startDateTime, long endDateTime) {
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
	}

	/**
	 * Concrete class should implement this method to calculate their desired method to based on current
	 * Member.
	 * e.g: whether current member is pregnant or not.
	 *
	 * @param member
	 */
	public abstract void calculate(Members member);

	/**
	 * Check if given map visit data(PSRF, ANC, PNC) within start and end time.
	 *
	 * @param visitData
	 * @return
	 */
	protected boolean withInStartAndEndTime(Map<String, String> visitData) {
		if (visitData.containsKey(Members.CLIENT_VERSION_KEY)) {
			long clientVersion = Long.parseLong(visitData.get(Members.CLIENT_VERSION_KEY));
			if (clientVersion >= startDateTime && clientVersion <= endDateTime) {
				return true;
			}
		}
		return false;
	}
}
