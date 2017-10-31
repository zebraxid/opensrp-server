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

/**
 * SUPPORT MULTIPLE COI
 */
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
        this.dhis2FormattedPeriod = "201709";
    }

    private String createDhis2FormattedPeriod() {
        int year = period.getYear();
        int month = period.getMonthOfYear();
        return String.valueOf(year) + String.valueOf(month);
    }

    public <T> List<DataValueSet> build(T reportingObj) throws IllegalAccessException {
        String dataSetId;
        String dataElementId;
        String categoryOptionId;
        Class reportingClass = reportingObj.getClass();

        for (Field m : reportingClass.getDeclaredFields()) {
            if (m.isAnnotationPresent(DHIS2.class)) {
                dataSetId = m.getAnnotation(DHIS2.class).dataSetId();
                dataElementId = m.getAnnotation(DHIS2.class).dataElementId();
                categoryOptionId = m.getAnnotation(DHIS2.class).categoryOptionId();
                boolean onlyDataSetAnnotationPresent = !dataSetId.isEmpty() && dataElementId.isEmpty() && categoryOptionId.isEmpty();
                boolean dataSetAndDataElementAnnotationPresent = !dataSetId.isEmpty() && !dataElementId.isEmpty() && categoryOptionId.isEmpty();
                boolean completeAnnotationPresent = !dataSetId.isEmpty() && dataElementId.isEmpty() && !categoryOptionId.isEmpty();
                if(onlyDataSetAnnotationPresent){
                    for(Field dataElementField : m.getType().getDeclaredFields()) {
                        createDataValueSetForChainAnnotation(reportingObj, m, dataSetId, dataElementField);

                    }
                } else if(dataSetAndDataElementAnnotationPresent) {

                } else if(completeAnnotationPresent){
                    createDataValueFromCompleteAnnotatedMember(reportingObj, m, dataSetId);

                }
                /*
                assumption: For single variable annotation, one annotation at a time, will be in consecutive class hierarchy and in order dataSet-dataElementId-categoryOptionId
                            No double variable annotation supported
                            complete annotation supported at any level of class/object hierarchy.
                    check contain data set only
                    if true
                        //list all the member of that field
                        for m : allFields
                            if field contain only data elementId
                               / list all the member of that field
                                    for m : allFields in super class
                                        if field contain one or multiple category option id in super class
                                            create ad datavalue object
                                            add to template list
                                        else
                                            recurse

                                    for m : all declared fields
                                         if field contain one or multiple category option id in super class
                                            create ad datavalue object
                                            add to template list
                                        else
                                           recurse

                            else
                                recurese

                    else
                        recurse


                    label : recurese
                     */

            } else {
                recurse(reportingObj, m);
            }
        }

        return buildDataValueSets();
    }

    private <T> void createDataValueSetForChainAnnotation(T reportingObj, Field dataSetIdAnnotedField, String dataSetId, Field dataElementAnnotedField) throws IllegalAccessException {
        String dataElementId;
        String categoryOptionId;
        if(dataElementAnnotedField.isAnnotationPresent(DHIS2.class)){
            dataElementId = dataElementAnnotedField.getAnnotation(DHIS2.class).dataElementId();
            categoryOptionId = dataElementAnnotedField.getAnnotation(DHIS2.class).categoryOptionId();
            boolean onlyDataElementAnnotationPresent = !dataElementId.isEmpty() && categoryOptionId.isEmpty();
            if(onlyDataElementAnnotationPresent) {
                createDataValueFromSuperClassCategoryOptionId(reportingObj, dataSetIdAnnotedField, dataSetId, dataElementId, dataElementAnnotedField);
                createDataValueFromDeclaredFieldsCategoryOptionId(reportingObj, dataSetIdAnnotedField, dataSetId, dataElementId, dataElementAnnotedField);

            }
        }else {
            recurse(reportingObj, dataElementAnnotedField);
        }
    }

    private <T> void createDataValueFromDeclaredFieldsCategoryOptionId(T reportingObj, Field dataSetIdAnnotedField, String dataSetId, String dataElementId, Field dataElementAnnotedField) throws IllegalAccessException {
        String categoryOptionId;
        for (Field categoryOptionField : dataElementAnnotedField.getType().getDeclaredFields()) {
            if(categoryOptionField.isAnnotationPresent(DHIS2.class)) {
                categoryOptionId = categoryOptionField.getAnnotation(DHIS2.class).categoryOptionId();
                createDataValueSetFromChain(reportingObj, dataSetIdAnnotedField, dataSetId, dataElementId, categoryOptionId, dataElementAnnotedField, categoryOptionField);
            }else {
                recurse(reportingObj, categoryOptionField);
            }
        }
    }

    private <T> void createDataValueFromSuperClassCategoryOptionId(T reportingObj, Field dataSetAnnotatedField, String dataSetId, String dataElementId, Field dataElementField) throws IllegalAccessException {
        String categoryOptionId;
        for (Field categoryOptionField : dataElementField.getType().getSuperclass().getDeclaredFields()) {
            if(categoryOptionField.isAnnotationPresent(DHIS2.class)) {
                categoryOptionId = categoryOptionField.getAnnotation(DHIS2.class).categoryOptionId();
                createDataValueSetFromChain(reportingObj, dataSetAnnotatedField, dataSetId, dataElementId, categoryOptionId, dataElementField, categoryOptionField);
            }
        }
    }

    private <T> void createDataValueSetFromChain(T reportingObj, Field m, String dataSetId, String dataElementId, String categoryOptionId, Field dataElementField, Field categoryOptionField) throws IllegalAccessException {
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

    private <T> void createDataValueFromCompleteAnnotatedMember(T reportingObj, Field m, String dataSetId) {
        try {
            DataValue dataValue = createDataValue(reportingObj, m);
            addToTemplate(dataSetId, dataValue);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    private <T> void recurse(T reportingObj, Field m) {
        m.setAccessible(true);
        try {
            if (isUserDefinedNonPrimitiveField(m)) {
                Object memberObject = m.get(reportingObj);
                build(memberObject);
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
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
