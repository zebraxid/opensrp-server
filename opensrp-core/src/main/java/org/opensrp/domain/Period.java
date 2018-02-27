package org.opensrp.domain;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;

public class Period {
    @JsonProperty
    private String name;

    @JsonProperty
    private String startDate;

    @JsonProperty
    private String endDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
