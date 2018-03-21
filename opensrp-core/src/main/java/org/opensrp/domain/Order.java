package org.opensrp.domain;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@TypeDiscriminator("doc.type == 'Order'")
public class Order extends BaseDataObject {

    @JsonProperty
    private String formSubmissionId;

    @JsonProperty
    private String locationId;

    @JsonProperty
    private String providerId;

    @JsonProperty
    private long dateCreatedByClient;

    @JsonProperty
    private Map<String, Integer> stockOnHand;

    public String getFormSubmissionId() {
        return formSubmissionId;
    }

    public void setFormSubmissionId(String formSubmissionId) {
        this.formSubmissionId = formSubmissionId;
    }

    public Map<String, Integer> getStockOnHand() {
        return stockOnHand;
    }

    public void setStockOnHand(Map<String, Integer> stockOnHand) {
        this.stockOnHand = stockOnHand;
    }

    public long getDateCreatedByClient() {
        return dateCreatedByClient;
    }

    public void setDateCreatedByClient(long dateCreatedByClient) {
        this.dateCreatedByClient = dateCreatedByClient;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
