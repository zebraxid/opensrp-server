package org.opensrp.web.controller;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;
import org.opensrp.connector.DHIS2.DHIS2ReportBuilder;
import org.opensrp.connector.DHIS2.DHIS2Service;
import org.opensrp.connector.DHIS2.dxf2.DataValueSet;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.MIS1Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportControllerTest {

    @Test
    @Ignore
    public void mis1Dhis2ControllerTest(){
        String data = "{" +
                "\"startDate\":\"2017-10-1\"," +
                "\"endDate\":\"2017-10-30\"" +
                "}";
        try {
            ResponseEntity<Map<String, JSONObject>> responseEntity =
                    this.createAndSendMis1ReportToDhis2(new JSONObject(data));
            System.out.println(new ObjectMapper().writeValueAsString(responseEntity.getBody()));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ;
    }

    public ResponseEntity<Map<String, JSONObject>> createAndSendMis1ReportToDhis2(@RequestParam("startDateTime")JSONObject data) {
        try {

            String startDate = data.getString("startDate");
            String endDate = data.getString("endDate");
            List<Members> members = Collections.EMPTY_LIST;
            DateTime startDateTime = new DateTime(startDate);
            DateTime endDateTime = new DateTime(endDate);
            MIS1Report mis1Report = new MIS1Report("union", members, startDateTime.getMillis(), endDateTime.getMillis());
            List<DataValueSet> dataSets = new DHIS2ReportBuilder("orgUnit", null, startDateTime).build(mis1Report);
            Map<String, JSONObject> dhis2Responses = new HashMap<>(data.length());
            DHIS2Service service = new DHIS2Service("http://123.200.18.20:8080", "dgfp", "Dgfp@123");
            for(DataValueSet dataValueSet: dataSets) {
                JSONObject response = dataValueSet.send(service);
                dhis2Responses.put(dataValueSet.getDataSet(), response);
            }
            return new ResponseEntity<Map<String, JSONObject>>(dhis2Responses, HttpStatus.OK);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
