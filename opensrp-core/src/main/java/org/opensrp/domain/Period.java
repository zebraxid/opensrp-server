package org.opensrp.domain;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;

public class Period {
    @JsonProperty
    private String name;

    @JsonProperty
    private Date startDate;

    @JsonProperty
    private Date endDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
