package org.opensrp.register.mcare.domain;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.motechproject.model.MotechBaseDataObject;


@JsonIgnoreProperties(ignoreUnknown = true)
@TypeDiscriminator("doc.type === 'Stock'")
public class Stock extends MotechBaseDataObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3557930891320863509L;
	
	@JsonProperty
	private String caseId;
	@JsonProperty
	private String instanceId;
	@JsonProperty
	private String provider;
	@JsonProperty
	private String locationId;
	@JsonProperty
	private String today;
	@JsonProperty
	private String start;
	@JsonProperty
	private String end;
	@JsonProperty	
	private String country;
	@JsonProperty
	private String division;
	@JsonProperty
	private String district;
	@JsonProperty
	private String upazila;
	@JsonProperty
	private String union;
	@JsonProperty
	private String ward;
	@JsonProperty
	private String block;
	@JsonProperty
	private String unit;
	@JsonProperty
	private Map<String, String> details;
	@JsonProperty
	private String vaccinator_name;
	@JsonProperty
	private String report;
	@JsonProperty
	private String existing_provider_facility;
	@JsonProperty
	private String center_name;
	@JsonProperty
	private String date;
	@JsonProperty
	private long clientVersion;
	@JsonProperty("updateVersion")
	private long updateVersion;
	@JsonProperty("serverVersion")
	private long serverVersion;
	
	
	@JsonProperty
	private int Target_assigned_for_vaccination_at_each_month;
	@JsonProperty
	private int Target_assigned_for_vaccination_for_the_year;
	@JsonProperty
	private int bcg_balance_in_hand;
	@JsonProperty
	private int bcg_received;
	@JsonProperty
	private int bcg_wasted;
	@JsonProperty
	private int opv_balance_in_hand;
	@JsonProperty
	private int opv_received;
	@JsonProperty
	private int opv_wasted;
	@JsonProperty
	private int ipv_balance_in_hand;
	@JsonProperty
	private int ipv_received;
	@JsonProperty
	private int ipv_wasted;
	@JsonProperty
	private int pcv_balance_in_hand;
	@JsonProperty
	private int pcv_received;
	@JsonProperty
	private int pcv_wasted;
	@JsonProperty
	private int penta_balance_in_hand;
	@JsonProperty
	private int penta_received;
	@JsonProperty
	private int penta_wasted;
	@JsonProperty
	private int measles_balance_in_hand;
	@JsonProperty
	private int measles_received;
	@JsonProperty
	private int measles_wasted;
	@JsonProperty
	private int tt_balance_in_hand;
	@JsonProperty
	private int tt_received;
	@JsonProperty
	private int tt_wasted;
	@JsonProperty
	private int dilutants_balance_in_hand;
	@JsonProperty
	private int dilutants_received;
	@JsonProperty
	private int dilutants_wasted;
	@JsonProperty
	private int syringes_balance_in_hand;
	@JsonProperty
	private int syringes_received;
	@JsonProperty
	private int syringes_wasted;
	@JsonProperty
	private int safety_boxes_balance_in_hand;
	@JsonProperty
	private int safety_boxes_received;
	@JsonProperty
	private int safety_boxes_wasted;
	@JsonProperty
	private String total_wasted;
	@JsonProperty
	private String total_received;
	@JsonProperty
	private String total_balanceInHand;
	public Stock(){
		
	}

	public Stock setCaseId(String caseId) {
		this.caseId = caseId;
		return this;
	}
	public String getCaseId() {
		return caseId;
	}
	
	public Stock setInstanceId(String instanceId) {
		this.instanceId = instanceId;
		return this;
	}
	public String getInstanceId() {
		return caseId;
	}
	
	public Stock setProvider(String provider) {
		this.provider = provider;
		return this;
	}
	public String getProvider() {
		return provider;
	}
	
	public Stock setLocationId(String locationId) {
		this.locationId = locationId;
		return this;
	}
	public String getLocationId() {
		return locationId;
	}
	
	public Stock setToday(String today) {
		this.today = today;
		return this;
	}
	public String getToday() {
		return today;
	}
	
	public Stock setStart(String start) {
		this.start = start;
		return this;
	}
	public String geStart() {
		return start;
	}
	
	public Stock setEnd(String end) {
		this.end = end;
		return this;
	}
	public String getEnd() {
		return end;
	}
	
	public Stock setCountry(String country) {
		this.country = country;
		return this;
	}
	public String getCountry() {
		return country;
	}
	
	public Stock setDivision(String division) {
		this.division = division;
		return this;
	}
	public String getDivision() {
		return division;
	}
	
	public Stock setUpazila(String upazila) {
		this.upazila = upazila;
		return this;
	}
	public String getUpazila() {
		return upazila;
	}
	
	public Stock setUnion(String union) {
		this.union = union;
		return this;
	}
	public String getUnion() {
		return union;
	}
	
	public Stock setWard(String ward) {
		this.ward = ward;
		return this;
	}
	public String getWard() {
		return ward;
	}
	public Stock setBlock(String block) {
		this.block = block;
		return this;
	}
	public String getBlock() {
		return block;
	}
	
	public Stock setUnit(String unit) {
		this.unit = unit;
		return this;
	}
	public String getUnit() {
		return unit;
	}
	
	public Stock setDetails(Map<String, String> details) {
        this.details = new HashMap<>(details);
        return this;
    }
	public Map<String, String> details() {
		if (details == null)
			this.details = new HashMap<>();
		return details;
	}
	
	
	public Stock setVaccinatorName(String vaccinator_name) {
		this.vaccinator_name = vaccinator_name;
		return this;
	}
	public String getVaccinatorName() {
		return vaccinator_name;
	}
	
	public Stock setExistingProviderFacility( String existing_provider_facility) {
		this.existing_provider_facility = existing_provider_facility;
		return this;
	}
	public String getExistingProviderFacility() {
		return existing_provider_facility;
	}
	
	public Stock setReporte(String report) {
		this.report = report;
		return this;
	}
	public String getReport() {
		return report;
	}
	
	public Stock setCenterName(String center_name) {
		this.center_name = center_name;
		return this;
	}
	public String getCenterName() {
		return center_name;
	}
	public Stock setDate(String date) {
		this.date = date;
		return this;
	}
	public String getDate() {
		return date;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getVaccinator_name() {
		return vaccinator_name;
	}

	public void setVaccinator_name(String vaccinator_name) {
		this.vaccinator_name = vaccinator_name;
	}

	public String getExisting_provider_facility() {
		return existing_provider_facility;
	}

	public void setExisting_provider_facility(String existing_provider_facility) {
		this.existing_provider_facility = existing_provider_facility;
	}

	public String getCenter_name() {
		return center_name;
	}

	public void setCenter_name(String center_name) {
		this.center_name = center_name;
	}

	public int getTarget_assigned_for_vaccination_at_each_month() {
		return Target_assigned_for_vaccination_at_each_month;
	}

	public Stock setTarget_assigned_for_vaccination_at_each_month(
			int target_assigned_for_vaccination_at_each_month) {
		this.Target_assigned_for_vaccination_at_each_month = target_assigned_for_vaccination_at_each_month;
	return this;
	}

	public int getTarget_assigned_for_vaccination_for_the_year() {
		return Target_assigned_for_vaccination_for_the_year;
	}

	public Stock setTarget_assigned_for_vaccination_for_the_year(
			int target_assigned_for_vaccination_for_the_year) {
		this.Target_assigned_for_vaccination_for_the_year = target_assigned_for_vaccination_for_the_year;
		return this;
	}

	public int getBcg_balance_in_hand() {
		return bcg_balance_in_hand;
	}

	public Stock setBcg_balance_in_hand(int bcg_balance_in_hand) {
		this.bcg_balance_in_hand = bcg_balance_in_hand;
		return this;
	}

	public int getBcg_received() {
		return bcg_received;
	}

	public Stock setBcg_received(int bcg_received) {
		this.bcg_received = bcg_received;
		return this;
	}

	public int getBcg_wasted() {
		return bcg_wasted;
	}

	public Stock setBcg_wasted(int bcg_wasted) {
		this.bcg_wasted = bcg_wasted;
		return this;
	}

	public int getOpv_balance_in_hand() {
		return opv_balance_in_hand;
	}

	public Stock setOpv_balance_in_hand(int opv_balance_in_hand) {
		this.opv_balance_in_hand = opv_balance_in_hand;
		return this;
	}

	public int getOpv_received() {
		return opv_received;
	}

	public Stock setOpv_received(int opv_received) {
		this.opv_received = opv_received;
		return this;
	}

	public int getOpv_wasted() {
		return opv_wasted;
	}

	public Stock setOpv_wasted(int opv_wasted) {
		this.opv_wasted = opv_wasted;
		return this;
	}

	public int getIpv_balance_in_hand() {
		return ipv_balance_in_hand;
	}

	public Stock setIpv_balance_in_hand(int ipv_balance_in_hand) {
		this.ipv_balance_in_hand = ipv_balance_in_hand;
		return this;
	}

	public int getIpv_received() {
		return ipv_received;
	}

	public Stock setIpv_received(int ipv_received) {
		this.ipv_received = ipv_received;
		return this;
	}

	public int getIpv_wasted() {
		return ipv_wasted;
	}

	public Stock setIpv_wasted(int ipv_wasted) {
		this.ipv_wasted = ipv_wasted;
		return this;
	}

	public int getPcv_balance_in_hand() {
		return pcv_balance_in_hand;
	}

	public Stock setPcv_balance_in_hand(int pcv_balance_in_hand) {
		this.pcv_balance_in_hand = pcv_balance_in_hand;
		return this;
	}

	public int getPcv_received() {
		return pcv_received;
	}

	public Stock setPcv_received(int pcv_received) {
		this.pcv_received = pcv_received;
		return this;
	}

	public int getPcv_wasted() {
		return pcv_wasted;
	}

	public Stock setPcv_wasted(int pcv_wasted) {
		this.pcv_wasted = pcv_wasted;
		return this;
	}

	public int getPenta_balance_in_hand() {
		return penta_balance_in_hand;
	}

	public Stock setPenta_balance_in_hand(int penta_balance_in_hand) {
		this.penta_balance_in_hand = penta_balance_in_hand;
		return this;
	}

	public int getPenta_received() {
		return penta_received;
	}

	public Stock setPenta_received(int penta_received) {
		this.penta_received = penta_received;
		return this;
	}

	public int getPenta_wasted() {
		return penta_wasted;
	}

	public Stock setPenta_wasted(int penta_wasted) {
		this.penta_wasted = penta_wasted;
		return this;
	}

	public int getMeasles_balance_in_hand() {
		return measles_balance_in_hand;
	}

	public Stock setMeasles_balance_in_hand(int measles_balance_in_hand) {
		this.measles_balance_in_hand = measles_balance_in_hand;
		return this;
	}

	public int getMeasles_received() {
		return measles_received;
	}

	public Stock setMeasles_received(int measles_received) {
		this.measles_received = measles_received;
		return this;
	}

	public int getMeasles_wasted() {
		return measles_wasted;
	}

	public Stock setMeasles_wasted(int measles_wasted) {
		this.measles_wasted = measles_wasted;
		return this;
	}

	public int getTt_balance_in_hand() {
		return tt_balance_in_hand;
	}

	public Stock setTt_balance_in_hand(int tt_balance_in_hand) {
		this.tt_balance_in_hand = tt_balance_in_hand;
		return this;
	}

	public int getTt_received() {
		return tt_received;
	}

	public Stock setTt_received(int tt_received) {
		this.tt_received = tt_received;
		return this;
	}

	public int getTt_wasted() {
		return tt_wasted;
	}

	public Stock setTt_wasted(int tt_wasted) {
		this.tt_wasted = tt_wasted;
		return this;
	}

	public int getDilutants_balance_in_hand() {
		return dilutants_balance_in_hand;
	}

	public Stock setDilutants_balance_in_hand(int dilutants_balance_in_hand) {
		this.dilutants_balance_in_hand = dilutants_balance_in_hand;
		return this;
	}

	public int getDilutants_received() {
		return dilutants_received;
	}

	public Stock setDilutants_received(int dilutants_received) {
		this.dilutants_received = dilutants_received;
		return this;
	}

	public int getDilutants_wasted() {
		return dilutants_wasted;
	}

	public Stock setDilutants_wasted(int dilutants_wasted) {
		this.dilutants_wasted = dilutants_wasted;
		return this;
	}

	public int getSyringes_balance_in_hand() {
		return syringes_balance_in_hand;
	}

	public Stock setSyringes_balance_in_hand(int syringes_balance_in_hand) {
		this.syringes_balance_in_hand = syringes_balance_in_hand;
		return this;
	}

	public int getSyringes_received() {
		return syringes_received;
	}

	public Stock setSyringes_received(int syringes_received) {
		this.syringes_received = syringes_received;
		return this;
	}

	public int getSyringes_wasted() {
		return syringes_wasted;
	}

	public Stock setSyringes_wasted(int syringes_wasted) {
		this.syringes_wasted = syringes_wasted;
		return this;
	}

	public int getSafety_boxes_balance_in_hand() {
		return safety_boxes_balance_in_hand;
	}

	public Stock setSafety_boxes_balance_in_hand(int safety_boxes_balance_in_hand) {
		this.safety_boxes_balance_in_hand = safety_boxes_balance_in_hand;
		return this;
	}

	public int getSafety_boxes_received() {
		return safety_boxes_received;
	}

	public Stock setSafety_boxes_received(int safety_boxes_received) {
		this.safety_boxes_received = safety_boxes_received;
		return this;
	}

	public int getSafety_boxes_wasted() {
		return safety_boxes_wasted;
	}

	public Stock setSafety_boxes_wasted(int safety_boxes_wasted) {
		this.safety_boxes_wasted = safety_boxes_wasted;
		return this;
	}

	public String getTotal_wasted() {
		return total_wasted;
	}

	public Stock setTotal_wasted(String total_wasted) {
		this.total_wasted = total_wasted;
		return this;
	}

	public String getTotal_received() {
		return total_received;
	}

	public Stock setTotal_received(String total_received) {
		this.total_received = total_received;
		return this;
	}

	public String getTotal_balanceInHand() {
		return total_balanceInHand;
	}

	public Stock setTotal_balanceInHand(String total_balanceInHand) {
		this.total_balanceInHand = total_balanceInHand;
		return this;
	}

	public String getStart() {
		return start;
	}

	public Map<String, String> getDetails() {
		return details;
	}

	public Stock setReport(String report) {
		this.report = report;
		return this;
	}
	
	
	public long getServerVersion() {
		return serverVersion;
	}

	public Stock setServerVersion(long serverVersion) {
		this.serverVersion = serverVersion;
		return this;
	}
	
	public long getClientVersion() {
		return clientVersion;
	}

	public Stock setClientVersion(long clientVersion) {
		this.clientVersion = clientVersion;
		return this;
	}
	
	public long getUpdateVersion() {
		return updateVersion;
	}

	public Stock setUpdateVersion(long updateVersion) {
		this.updateVersion = updateVersion;
		return this;
	}
	
}
