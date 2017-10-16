package org.opensrp.connector.DHIS2.dxf2;

import java.util.List;

public class DataValuSetList {
    List<DataValueSet> dataValueSets;

    public DataValuSetList(List<DataValueSet> dataValueSets) {
        this.dataValueSets = dataValueSets;
    }

    public List<DataValueSet> getDataValueSets() {
        return dataValueSets;
    }
}
