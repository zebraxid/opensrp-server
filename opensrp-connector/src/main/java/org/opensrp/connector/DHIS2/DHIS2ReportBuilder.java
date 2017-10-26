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
        this.dhis2FormattedPeriod = "201410";
    }

    private String createDhis2FormattedPeriod() {
        int year = period.getYear();
        int month = period.getMonthOfYear();
        return String.valueOf(year) + String.valueOf(month);
    }

    public <T> List<DataValueSet> build(T reportingObj) throws IllegalAccessException {
        Class reportingClass = reportingObj.getClass();

        for (Field m : reportingClass.getDeclaredFields()) {
            if (m.isAnnotationPresent(DHIS2.class)) {
                String dataSetId;
                dataSetId = m.getAnnotation(DHIS2.class).dataSetId();
                String dataElementId;
                String categoryOptionId;
                dataElementId = m.getAnnotation(DHIS2.class).dataElementId();
                categoryOptionId = m.getAnnotation(DHIS2.class).categoryOptionId();
                if(dataElementId.isEmpty() && categoryOptionId.isEmpty()){
                    for(Field dataElementField : m.getType().getDeclaredFields()) {
                        if(dataElementField.isAnnotationPresent(DHIS2.class)){
                            dataElementId = dataElementField.getAnnotation(DHIS2.class).dataElementId();
                            categoryOptionId = dataElementField.getAnnotation(DHIS2.class).categoryOptionId();
                            if(!dataElementId.isEmpty() && categoryOptionId.isEmpty()) {
                                for (Field categoryOptionField : dataElementField.getType().getSuperclass().getDeclaredFields()) {
                                    if(categoryOptionField.isAnnotationPresent(DHIS2.class)) {
                                        categoryOptionId = categoryOptionField.getAnnotation(DHIS2.class).categoryOptionId();
                                        String orgUnit = orgUnitId;
                                        String period = dhis2FormattedPeriod;
                                        categoryOptionField.setAccessible(true);
                                        dataElementField.setAccessible(true);
                                        m.setAccessible(true);
                                        categoryOptionField.setAccessible(true);
                                        String value = categoryOptionField.get(dataElementField.get(m.get(reportingObj))).toString();
                                        DataValue dataValue = new DataValue(dataElementId, categoryOptionId, orgUnit, period, value);
                                        addToTemplate(dataSetId, dataValue);
                                    }
                                }
                            }
                        }

                    }
                }else{
                    try {
                        DataValue dataValue = createDataValue(reportingObj, m);
                        addToTemplate(dataSetId, dataValue);
                    } catch (Exception exc) {
                        exc.printStackTrace();
                    }

                }
                /*
                    check contain data set only
                    if true
                        list all the member of that field
                        for m : allFields
                            if field contain data elementId
                                list all the member of that field
                                    for m : allFields
                                        if fileld contain category option id
                                            create ad datavalue object
                                            add to template list
                    else
                    below block
                     */

            } else {
                m.setAccessible(true);
                try {
                    if (isUserDefinedNonPrimitiveField(m)) {
                        recurse(reportingObj, m);
                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return buildDataValueSets();
    }

    private void addToTemplate(String dataSetId, DataValue dataValue) {
        if (dataValueMap.containsKey(dataSetId)) {
            List<DataValue> temp = dataValueMap.get(dataSetId);
            temp.add(dataValue);
            dataValueMap.put(dataSetId, temp);
        } else {
            List<DataValue> temp = new ArrayList<>(1);
            temp.add(dataValue);
            dataValueMap.put(dataSetId, temp);
        }
    }

    private <T> void recurse(T reportingObj, Field m) throws IllegalAccessException {
        Object memberObject = m.get(reportingObj);
        build(memberObject);
    }

    private boolean isUserDefinedNonPrimitiveField(Field m) {
        return !m.getType().isPrimitive() && !m.isSynthetic() && !m.getType().isArray();
    }

    private <T> DataValue createDataValue(T reportingObj, Field m) throws IllegalAccessException {

        String orgUnit = orgUnitId;
        String period = dhis2FormattedPeriod;
        String dataElementId = m.getAnnotation(DHIS2.class).dataElementId();
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
