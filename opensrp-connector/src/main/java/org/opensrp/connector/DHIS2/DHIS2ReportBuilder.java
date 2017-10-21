package org.opensrp.connector.DHIS2;

import org.joda.time.DateTime;
import org.opensrp.connector.DHIS2.dxf2.DHIS2;
import org.opensrp.connector.DHIS2.dxf2.DataValue;
import org.opensrp.connector.DHIS2.dxf2.DataValueSet;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class DHIS2ReportBuilder {
    private String orgUnitId;
    private DateTime completionDate;
    private DateTime period;
    private String dhis2FormattedPeriod;
    private Map<String, List<DataValue>> dataValueMap = new HashMap<>();
    private List<DataValueSet> dataValueSets;

    public DHIS2ReportBuilder(String orgUnitId, DateTime completionDate, DateTime period) {
        this.orgUnitId = orgUnitId;
        this.completionDate = completionDate;
        this.period = period;
        this.dhis2FormattedPeriod = createDhis2FormattedPeriod();
    }

    private String createDhis2FormattedPeriod() {
        int year = period.getYear();
        int month = period.getMonthOfYear() + 1;
        return String.valueOf(year) + String.valueOf(month);
    }

    public <T> List<DataValueSet> build(T reportingObj) {
        Class reportingClass = reportingObj.getClass();

        for (Field m : reportingClass.getDeclaredFields()) {
            if (m.isAnnotationPresent(DHIS2.class)) {
                try {
                    String dataSetId =
                            m.getAnnotation(DHIS2.class).dataSetId();
                    DataValue dataValue = createDataValue(reportingObj, m);
                    if (dataValueMap.containsKey(dataSetId)) {
                        List<DataValue> temp = dataValueMap.get(dataSetId);
                        temp.add(dataValue);
                        dataValueMap.put(dataSetId, temp);
                    } else {
                        dataValueMap.put(dataSetId, asList(dataValue));
                    }
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            } else {
                m.setAccessible(true);
                try {
                    if (!m.getType().isPrimitive() && !m.isSynthetic() && !m.getType().isArray()) {
                        Object memberObject = m.get(reportingObj);
                        build(memberObject);
                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return buildDataValueSets();
    }

    private <T> DataValue createDataValue(T reportingObj, Field m) throws IllegalAccessException {

        String orgUnit = orgUnitId;
        String period = dhis2FormattedPeriod;
        String dataElementId = m.getAnnotation(DHIS2.class).dateElementId();
        String categoryOptionId = m.getAnnotation(DHIS2.class).categoryOptionId();
        m.setAccessible(true);
        String value = m.get(reportingObj).toString();
        return new DataValue(dataElementId, categoryOptionId, orgUnit, period, value);
    }

    private List<DataValueSet> buildDataValueSets() {
        dataValueSets = new ArrayList<>(dataValueMap.entrySet().size());
        for (Map.Entry<String, List<DataValue>> dataValueSetEntry : dataValueMap.entrySet()) {
            DataValueSet dataValueSet = new DataValueSet(dataValueSetEntry.getKey(), dataValueSetEntry.getValue());
            dataValueSets.add(dataValueSet);
        }
        return dataValueSets;
    }

}
