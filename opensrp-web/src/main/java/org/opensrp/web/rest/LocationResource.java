package org.opensrp.web.rest;

import com.google.gson.Gson;
import org.json.JSONException;
import org.opensrp.connector.openmrs.service.OpenmrsLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/rest/locations")
public class LocationResource {
    @Autowired
    private OpenmrsLocationService locationService;
    public static final String LOCATION_ID = "id";

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    protected String getLocationHeirarchy(@PathVariable(value="id") String id) throws JSONException {
        return new Gson().toJson(locationService.getLocationTreeStartingFrom(id));
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    protected String getLocationHeirarchy() throws JSONException {
        return new Gson().toJson(locationService.getLocationTree());
    }
}