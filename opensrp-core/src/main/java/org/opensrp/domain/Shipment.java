package org.opensrp.domain;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@TypeDiscriminator("doc.type == 'Shipment'")
public class Shipment extends BaseDataObject {

    @JsonProperty
    private Date orderedDate;

    @JsonProperty
    private String orderCode;

    @JsonProperty
    private Facility receivingFacility;

    @JsonProperty
    private Facility supplyingFacility;

    @JsonProperty
    private ShipmentLineItem[] lineItems;

    @JsonProperty
    private Period processingPeriod;

    public Date getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(Date orderedDate) {
        this.orderedDate = orderedDate;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Facility getReceivingFacility() {
        return receivingFacility;
    }

    public void setReceivingFacility(Facility receivingFacility) {
        this.receivingFacility = receivingFacility;
    }

    public Facility getSupplyingFacility() {
        return supplyingFacility;
    }

    public void setSupplyingFacility(Facility supplyingFacility) {
        this.supplyingFacility = supplyingFacility;
    }

    public ShipmentLineItem[] getLineItems() {
        return lineItems;
    }

    public void setLineItems(ShipmentLineItem[] lineItems) {
        this.lineItems = lineItems;
    }

    public Period getProcessingPeriod() {
        return processingPeriod;
    }

    public void setProcessingPeriod(Period processingPeriod) {
        this.processingPeriod = processingPeriod;
    }
}
