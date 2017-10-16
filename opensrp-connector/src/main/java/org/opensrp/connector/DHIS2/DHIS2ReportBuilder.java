package org.opensrp.connector.DHIS2;

import org.opensrp.connector.DHIS2.dxf2.DHIS2;
import org.opensrp.connector.DHIS2.dxf2.DataValue;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public  class DHIS2ReportBuilder {

    private Map<String ,List<DataValue>> dataValueMap = new HashMap<>();

    public DHIS2ReportBuilder() {
    }

    public <T> void build(T reportingObj) {
        Class reportingClass = reportingObj.getClass();

        for (Field m : reportingClass.getDeclaredFields()) {
            if (m.isAnnotationPresent(DHIS2.class)) {
                try {
                    String dataSetId =
                            m.getAnnotation(DHIS2.class).dataSetId();
                    DataValue dataValue = createDataValue(reportingObj, m);
                    if(dataValueMap.containsKey(dataSetId)) {
                        List<DataValue> temp = dataValueMap.get(dataSetId);
                        temp.add(dataValue);
                        dataValueMap.put(dataSetId, temp);
                    }else {
                        dataValueMap.put(dataSetId, asList(dataValue));
                    }
                    return ;
                }catch (Exception exc) {
                    exc.printStackTrace();
                }
            } else {
                m.setAccessible(true);
                try {
                    if(!m.getType().isPrimitive() && !m.isSynthetic() && !m.getType().isArray()) {
                        Object memberObject = m.get(reportingObj);
                        build(memberObject);
                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return ;
    }

    private <T> DataValue createDataValue(T reportingObj, Field m) throws IllegalAccessException {

        String orgUnit = m.getAnnotation(DHIS2.class).orgUnit();
        String period = m.getAnnotation(DHIS2.class).period();
        String dataElementId = m.getAnnotation(DHIS2.class).dateElementId();
        String categoryOptionId = m.getAnnotation(DHIS2.class).categoryOptionId();
        m.setAccessible(true);
        String value =  m.get(reportingObj).toString();
        return new DataValue(dataElementId, categoryOptionId, orgUnit, period, value);
    }
}
