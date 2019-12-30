package org.opensrp.domain;

public class MHVInfo {

    public Integer id;
    public String name;
    public Integer ccId;
    public Integer facilityWorkerTypeId;
    public String username;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCcId() {
        return ccId;
    }

    public void setCcId(Integer ccId) {
        this.ccId = ccId;
    }

    public Integer getFacilityWorkerTypeId() {
        return facilityWorkerTypeId;
    }

    public void setFacilityWorkerTypeId(Integer facilityWorkerTypeId) {
        this.facilityWorkerTypeId = facilityWorkerTypeId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
