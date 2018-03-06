package org.opensrp.domain;

import org.codehaus.jackson.annotate.JsonProperty;

public class ShipmentLineItem {
    @JsonProperty
    private String antigenType;

    @JsonProperty
    private int orderedQuantity;

    @JsonProperty
    private int shippedQuantity;

    @JsonProperty
    private int numDoses;


    public String getAntigenType() {
        return antigenType;
    }

    public void setAntigenType(String antigenType) {
        this.antigenType = antigenType;
    }

    public int getOrderedQuantity() {
        return orderedQuantity;
    }

    public void setOrderedQuantity(int orderedQuantity) {
        this.orderedQuantity = orderedQuantity;
    }

    public int getShippedQuantity() {
        return shippedQuantity;
    }

    public void setShippedQuantity(int shippedQuantity) {
        this.shippedQuantity = shippedQuantity;
    }

    public int getNumDoses() {
        return numDoses;
    }

    public void setNumDoses(int numDoses) {
        this.numDoses = numDoses;
    }
}
