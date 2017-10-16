/**
 * src <https://github.com/openmrs/openmrs-module-dhisreport/tree/9ae042c2402fb819ea3ec5cab8d45cf1b7b596c7/api/src/main/java/org/openmrs/module/dhisreport/api/dxf2></https://github.com/openmrs/openmrs-module-dhisreport/tree/9ae042c2402fb819ea3ec5cab8d45cf1b7b596c7/api/src/main/java/org/openmrs/module/dhisreport/api/dxf2>
 */
package org.opensrp.connector.DHIS2.dxf2;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DataValueSet {


    public static enum Month {

        JAN, FEB, MAR, APR, MAY, JUN, JUL, AUG, SEP, NOV, DEC
    }

    protected List<DataValue> dataValue = new LinkedList<DataValue>();
    protected XMLGregorianCalendar completeDate;
    protected String dataSet;


    /**
     * Gets the value of the dataValue property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the dataValue property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     *
     * <pre>
     * getDataValue().add( newItem );
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DataValue }
     *
     *
     */
    public List<DataValue> getDataValues() {
        if (dataValue == null) {
            dataValue = new ArrayList<DataValue>();
        }
        return this.dataValue;
    }

    public XMLGregorianCalendar getCompleteDate() {
        return completeDate;
    }

    /**
     * Sets the value of the completeDate property.
     *
     * @param value allowed object is {@link XMLGregorianCalendar }
     *
     */
    public void setCompleteDate(XMLGregorianCalendar value) {
        this.completeDate = value;
    }

    /**
     * Gets the value of the dataSet property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getDataSet() {
        return dataSet;
    }

    /**
     * Sets the value of the dataSet property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setDataSet(String value) {
        this.dataSet = value;
    }

}
