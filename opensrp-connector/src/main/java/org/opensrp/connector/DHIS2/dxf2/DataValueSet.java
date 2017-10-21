/**
 * src <https://github.com/openmrs/openmrs-module-dhisreport/tree/9ae042c2402fb819ea3ec5cab8d45cf1b7b596c7/api/src/main/java/org/openmrs/module/dhisreport/api/dxf2></https://github.com/openmrs/openmrs-module-dhisreport/tree/9ae042c2402fb819ea3ec5cab8d45cf1b7b596c7/api/src/main/java/org/openmrs/module/dhisreport/api/dxf2>
 */
package org.opensrp.connector.DHIS2.dxf2;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.common.util.HttpResponse;
import org.opensrp.connector.DHIS2.DHIS2Service;
import org.opensrp.connector.DHIS2.Dhis2HttpUtils;

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DataValueSet {


    protected List<DataValue> dataValue = new LinkedList<DataValue>();
    protected XMLGregorianCalendar completeDate;
    protected String dataSet;


    public DataValueSet(String dataSet) {
        this.dataSet = dataSet;
    }

    public DataValueSet(String dataSet, List<DataValue> dataValue) {
        this.dataValue = dataValue;
        this.dataSet = dataSet;
    }

    /**
     * Gets the value of the dataValue property.
     * <p>
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present. This is why there is not a
     * <CODE>set</CODE> method for the dataValue property.
     * <p>
     * <p>
     * For example, to add a new item, do as follows:
     * <p>
     * <pre>
     * getDataValue().add( newItem );
     * </pre>
     * <p>
     * <p>
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DataValue }
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
     */
    public void setCompleteDate(XMLGregorianCalendar value) {
        this.completeDate = value;
    }

    /**
     * Gets the value of the dataSet property.
     *
     * @return possible object is {@link String }
     */
    public String getDataSet() {
        return dataSet;
    }


    public JSONObject send(DHIS2Service service) throws JSONException, IOException {
        String baseUrl = service.getDHIS2_BASE_URL()+"/api/";
        String userName = service.getDHIS2_USER();
        String password = service.getDHIS2_PWD();
        HttpResponse response = Dhis2HttpUtils.post(baseUrl.replaceAll("\\s+", "") + "dataValueSets", "", new ObjectMapper().writeValueAsString(this),userName.replaceAll("\\s+", ""), password.replaceAll("\\s+", ""));
        return new JSONObject(response.body());
    }
}
