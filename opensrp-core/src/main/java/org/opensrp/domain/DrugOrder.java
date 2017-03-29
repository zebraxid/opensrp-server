package org.opensrp.domain;

import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.joda.time.DateTime;

@TypeDiscriminator("doc.type == 'DrugOrder'")
public class DrugOrder extends BaseDataObject {

	@JsonProperty
	private Map<String, String> codes;
	@JsonProperty
	private String orderType;
	@JsonProperty
	private String drug;
	@JsonProperty
	private String orderNumber;
	@JsonProperty
	private String action;
	@JsonProperty
	private String previousOrder;
	@JsonProperty
	private DateTime dateActivated;
	@JsonProperty
	private String discontinuedBy;
	@JsonProperty
	private DateTime discontinuedDate;
	@JsonProperty
	private String discontinuedReason;
	@JsonProperty
	private DateTime autoExpireDate;
	@JsonProperty
	private String urgency;
	@JsonProperty
	private String instructions;
	@JsonProperty
	private String orderReason;
	@JsonProperty
	private String dosingType;
	@JsonProperty
	private String dose;
	@JsonProperty
	private String frequency;
	@JsonProperty
	private String descriptions;
	@JsonProperty
	private String quantity;
	@JsonProperty
	private String baseEntityId;
	@JsonProperty
	private String orderer;
	@JsonProperty
	private String route;
	@JsonProperty
	private String quantityUnits;
	 
	public DrugOrder(String baseEntityId,String ordererName,String drug,String orderType, Map<String, String> codes,
			String orderNumber, String action, String previousOrder
			, DateTime dateActivated, DateTime autoExpireDate,String urgency,
			String instructions, String dosingType, String description,String quantity,String route, String quantityUnits)
	{
		this.baseEntityId=baseEntityId;
		this.orderer=ordererName;
		this.drug=drug;
		this.orderType=orderType;
		this.codes=codes;
		this.orderNumber=orderNumber;
		this.action=action;
		this.previousOrder=previousOrder;
		this.dateActivated=dateActivated;
		this.autoExpireDate=autoExpireDate;
		this.urgency=urgency;
		this.instructions=instructions;
		this.dosingType=dosingType;
		this.descriptions=description;
		this.quantity=quantity;
		this.route=route;
		this.quantityUnits=quantityUnits;
	}
	
	public DrugOrder(String baseEntityId, String orderType, String drug, Map<String, String> codes,
			DateTime dateActivated, DateTime autoExpireDate, String frequency, String quantity, String quantityUnits,
			String orderNumber, String action, String urgency, String instructions, String dose, String dosingType,
			String previousOrder, String discontinuedBy, DateTime discontinuedDate, String discontinuedReason,
			String orderReason, String orderer, String route, String descriptions) {
		super();
		this.baseEntityId = baseEntityId;
		this.orderType = orderType;
		this.drug = drug;
		this.codes = codes;
		this.dateActivated = dateActivated;
		this.autoExpireDate = autoExpireDate;
		this.frequency = frequency;
		this.quantity = quantity;
		this.quantityUnits = quantityUnits;
		this.orderNumber = orderNumber;
		this.action = action;
		this.urgency = urgency;
		this.instructions = instructions;
		this.dose = dose;
		this.dosingType = dosingType;
		this.previousOrder = previousOrder;
		this.discontinuedBy = discontinuedBy;
		this.discontinuedDate = discontinuedDate;
		this.discontinuedReason = discontinuedReason;
		this.orderReason = orderReason;
		this.orderer = orderer;
		this.route = route;
		this.descriptions = descriptions;
	}

	public DrugOrder()
	{
		
	}
	
	public DrugOrder(String drug)
	{
		this.drug=drug;
	}
	public Map<String, String> getCodes() {
		return codes;
	}

	public void setCodes(Map<String, String> codes) {
		this.codes = codes;
	}

	
	 
	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getDrug() {
		return drug;
	}

	public void setDrug(String drug) {
		this.drug = drug;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getPreviousOrder() {
		return previousOrder;
	}

	public void setPreviousOrder(String previousOrder) {
		this.previousOrder = previousOrder;
	}

	public DateTime getDateActivated() {
		return dateActivated;
	}

	public void setDateActivated(DateTime dateActivated) {
		this.dateActivated = dateActivated;
	}

	public DateTime getAutoExpireDate() {
		return autoExpireDate;
	}

	public void setAutoExpireDate(DateTime autoExpireDate) {
		this.autoExpireDate = autoExpireDate;
	}

	public String getUrgency() {
		return urgency;
	}

	public void setUrgency(String urgency) {
		this.urgency = urgency;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public String getOrderReason() {
		return orderReason;
	}

	public void setOrderReason(String orderReason) {
		this.orderReason = orderReason;
	}

	public String getDiscontinuedBy() {
		return discontinuedBy;
	}

	public void setDiscontinuedBy(String discontinuedBy) {
		this.discontinuedBy = discontinuedBy;
	}

	public DateTime getDiscontinuedDate() {
		return discontinuedDate;
	}

	public void setDiscontinuedDate(DateTime discontinuedDate) {
		this.discontinuedDate = discontinuedDate;
	}

	public String getDiscontinuedReason() {
		return discontinuedReason;
	}

	public void setDiscontinuedReason(String discontinuedReason) {
		this.discontinuedReason = discontinuedReason;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getBaseEntityId() {
		return baseEntityId;
	}

	public void setBaseEntityId(String baseEntityId) {
		this.baseEntityId = baseEntityId;
	}

	public String getOrderer() {
		return orderer;
	}

	public void setOrderer(String orderer) {
		this.orderer = orderer;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getQuantityUnits() {
		return quantityUnits;
	}

	public void setQuantityUnits(String quantityUnits) {
		this.quantityUnits = quantityUnits;
	}

	public String getDosingType() {
		return dosingType;
	}

	public void setDosingType(String dosingType) {
		this.dosingType = dosingType;
	}

	public String getDose() {
		return dose;
	}

	public void setDose(String dose) {
		this.dose = dose;
	}

	public String getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
}
