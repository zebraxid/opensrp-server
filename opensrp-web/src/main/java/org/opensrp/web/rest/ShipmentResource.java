package org.opensrp.web.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.util.TextUtils;
import org.joda.time.DateTime;
import org.opensrp.domain.Shipment;
import org.opensrp.service.ShipmentService;
import org.opensrp.util.DateTimeTypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping(value = "/rest/stockresource/shipment")
public class ShipmentResource extends RestResource<Shipment> {

    private ShipmentService shipmentService;
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .registerTypeAdapter(DateTime.class, new DateTimeTypeConverter()).create();

    @Autowired
    public ShipmentResource(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<HttpStatus> add(@RequestBody String jsonData) {
        if (TextUtils.isEmpty(jsonData)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Shipment newShipment = gson.fromJson(jsonData, Shipment.class);
        shipmentService.add(newShipment);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public List<Shipment> filter(String query) {
        return null;
    }

    @Override
    public List<Shipment> search(HttpServletRequest request) throws ParseException {
        return null;
    }

    @Override
    public Shipment getByUniqueId(String uniqueId) {
        return null;
    }

    @Override
    public List<String> requiredProperties() {
        return null;
    }

    @Override
    public Shipment create(Shipment entity) {
        return null;
    }

    @Override
    public Shipment update(Shipment entity) {
        return null;
    }
}
