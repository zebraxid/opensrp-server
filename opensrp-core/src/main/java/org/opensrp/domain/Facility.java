package org.opensrp.domain;

import org.codehaus.jackson.annotate.JsonProperty;

public class Facility {
    @JsonProperty
    private String code;

    @JsonProperty
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
