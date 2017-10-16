/**
 *src <https://github.com/openmrs/openmrs-module-dhisreport/tree/9ae042c2402fb819ea3ec5cab8d45cf1b7b596c7/api/src/main/java/org/openmrs/module/dhisreport/api/dxf2></https://github.com/openmrs/openmrs-module-dhisreport/tree/9ae042c2402fb819ea3ec5cab8d45cf1b7b596c7/api/src/main/java/org/openmrs/module/dhisreport/api/dxf2>
 *
 */
package org.opensrp.connector.DHIS2.dxf2;

public class DataValue
{
    protected String dataElement;
    protected String categoryOptionCombo;
    protected String orgUnit;
    protected String period;
    protected String value;


    public DataValue(String dataElement, String categoryOptionCombo, String orgUnit, String period, String value) {
        this.dataElement = dataElement;
        this.categoryOptionCombo = categoryOptionCombo;
        this.orgUnit = orgUnit;
        this.period = period;
        this.value = value;
    }

    public DataValue(String dataElement, String orgUnit, String period, String value) {
        this.dataElement = dataElement;
        this.orgUnit = orgUnit;
        this.period = period;
        this.value = value;
    }

    /**
     * Gets the value of the dataElement property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDataElement()
    {
        return dataElement;
    }

    /**
     * Sets the value of the dataElement property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDataElement( String value )
    {
        this.dataElement = value;
    }

    /**
     * Gets the value of the categoryOptionCombo property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getCategoryOptionCombo()
    {
        return categoryOptionCombo;
    }

    /**
     * Sets the value of the categoryOptionCombo property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setCategoryOptionCombo( String value )
    {
        this.categoryOptionCombo = value;
    }

    /**
     * Gets the value of the value property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getValue()
    {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setValue( String value )
    {
        this.value = value;
    }

    public String getOrgUnit() {
        return orgUnit;
    }

    public String getPeriod() {
        return period;
    }
}
