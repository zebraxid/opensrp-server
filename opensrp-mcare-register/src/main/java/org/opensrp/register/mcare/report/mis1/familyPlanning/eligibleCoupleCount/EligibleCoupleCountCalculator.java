package org.opensrp.register.mcare.report.mis1.familyPlanning.eligibleCoupleCount;

import org.opensrp.connector.DHIS2.dxf2.DHIS2;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.ReportCalculator;

import java.util.List;
import java.util.Map;

/**
 * Calculate report related to Eligible couple count.
 * TODO : Implement rest of the calculation based on @see <a href="http://doc.mpower-social.com:8080/share/page/document-details?nodeRef=workspace://SpacesStore/bb7b37bd-0d5a-4813-9903-33dce944b165">MIS1 Report SRS</a>
 */
public class EligibleCoupleCountCalculator extends ReportCalculator {

	private int newEligibleCoupleVisitCount = 0;

	private int unitTotalEligibleCoupleVisitCount = 0;

	@DHIS2(dateElementId="qQRGs57YZ2z",categoryOptionId="DHJ5tZVSSsl", dataSetId = "Z5WPr2zconV")
	private int totalEligibleCouple = 0;

	public EligibleCoupleCountCalculator(long startDateTime, long endDateTime) {
		super(startDateTime, endDateTime);

	}

	@Override
	public void calculate(Members member) {
		this.newEligibleCoupleVisitCount += addToNewEligibleCoupleVisitCount(member);
		this.totalEligibleCouple += addToTotalEligibleCoupleCount(member);
	}

	public int getNewEligibleCoupleVisitCount() {

		return newEligibleCoupleVisitCount;
	}

	public int getUnitTotalEligibleCoupleVisitCount()
	{
		return unitTotalEligibleCoupleVisitCount;
	}

	public int getTotalEligibleCouple() {
		return totalEligibleCouple;
	}

	/**
	 * Add to new count if current member is newly eligible.
	 *
	 * @param member
	 * @return
	 */
	private int addToNewEligibleCoupleVisitCount(Members member) {
		int countOfVisitForAMember = 0;
		List<Map<String, String>> eligibleCoupleVisits = member.elco_Followup();
		for (Map<String, String> eligibleCoupleVisit : eligibleCoupleVisits) {
			countOfVisitForAMember += checkIfVisitedBetweenStartAndEndDateTime(eligibleCoupleVisit);
		}
		return countOfVisitForAMember;
	}

	/**
	 * Add to total count if current member is eligible.
	 *
	 * @param member
	 * @return
	 */
	private int addToTotalEligibleCoupleCount(Members member) {
		Long clientVersion = member.getClientVersion();
		if (clientVersion != null) {
			if (clientVersion >= startDateTime && clientVersion <= endDateTime) {
				return 1;
			}
		}
		return 0;
	}

	/**
	 * TODO: Use {@link org.opensrp.register.mcare.report.mis1.ReportCalculator#withInStartAndEndTime existing Funciton}
	 *
	 * @param eligibleCoupleVisit
	 * @return
	 */
	private int checkIfVisitedBetweenStartAndEndDateTime(Map<String, String> eligibleCoupleVisit) {
		if (eligibleCoupleVisit.containsKey(Members.CLIENT_VERSION_KEY)) {
			long clientVersion = Long.parseLong(eligibleCoupleVisit.get(Members.CLIENT_VERSION_KEY));
			if (clientVersion >= startDateTime && clientVersion <= endDateTime) {
				return 1;
			}
		}
		return 0;
	}
}
