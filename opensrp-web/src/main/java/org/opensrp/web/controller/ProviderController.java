package org.opensrp.web.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.api.domain.User;
import org.opensrp.common.util.HttpAgent;
import org.opensrp.common.util.HttpResponse;
import org.opensrp.domain.Client;
import org.opensrp.domain.Drug;
import org.opensrp.domain.DrugOrder;
import org.opensrp.domain.Event;
import org.opensrp.dto.VillagesDTO;
import org.opensrp.repository.AllDrugOrders;
import org.opensrp.repository.AllDrugs;
import org.opensrp.service.ClientService;
import org.opensrp.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class ProviderController {
    private static Logger logger = LoggerFactory.getLogger(ProviderController.class.toString());
    private final String opensrpANMVillagesURL;
    private UserController userController;
    private HttpAgent httpAgent;
    private ClientService clientService;
    private EventService eventService;
    private AllDrugs allDrugs;
    private AllDrugOrders allDrugOrders;

    @Autowired
    public ProviderController(@Value("#{opensrp['opensrp.anm.villages.url']}") String opensrpANMVillagesURL,
             UserController userController, HttpAgent httpAgent,
             ClientService clientService, EventService eventService, AllDrugs allDrugs, AllDrugOrders allDrugOrders) {
        this.opensrpANMVillagesURL = opensrpANMVillagesURL;
        this.userController = userController;
        this.httpAgent = httpAgent;
        
        this.clientService = clientService;
        this.eventService= eventService;
        this.allDrugs = allDrugs;
        this.allDrugOrders = allDrugOrders;
    }

    @RequestMapping(method = GET, value = "/anm-villages")
    @ResponseBody
    public ResponseEntity<VillagesDTO> villagesForANM() {
        HttpResponse response = new HttpResponse(false, null);
        try {
            String anmIdentifier = userController.currentUser().getUsername();
            response = httpAgent.get(opensrpANMVillagesURL + "?anm-id=" + anmIdentifier);
            VillagesDTO villagesDTOs = new Gson().fromJson(response.body(),
                    new TypeToken<VillagesDTO>() {
                    }.getType());
            logger.info("Fetched Villages for the ANM");
            return new ResponseEntity<>(villagesDTOs, HttpStatus.OK);
        } catch (Exception exception) {
            logger.error(MessageFormat.format("{0} occurred while fetching Village Details for anm. StackTrace: \n {1}", exception.getMessage(), ExceptionUtils.getFullStackTrace(exception)));
            logger.error(MessageFormat.format("Response with status {0} and body: {1} was obtained from {2}", response.isSuccess(), response.body(), opensrpANMVillagesURL));
        }
        return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
    }
    
    @RequestMapping("/assigned-clients-full")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> assignedClientDataForProvider(@RequestParam("providerId") String providerId) throws JSONException {
    	Map<String, Object> map = new HashMap<>();
    	JSONObject tm = userController.teamAssociation(providerId);
    	JSONArray patientList = tm.getJSONArray("patients");
    	
    	List<Client> clients = new ArrayList<>();
    	List<Event> events = new ArrayList<>();
    	List<Drug> drugs = new ArrayList<>();
    	List<DrugOrder> drugOrders = new ArrayList<>();
    	
    	for (int i = 0; i < patientList.length(); i++) {
			JSONObject patient = patientList.getJSONObject(i);
			List<Client> cList = clientService.findAllByIdentifier(patient.getString("uuid"));
			Client client = cList.isEmpty()?null:cList.get(0);
			
			if(client != null){
				clients.add(client);
				events.addAll(eventService.findByBaseEntityId(client.getBaseEntityId()));
				drugs.addAll(allDrugs.getAll());
				drugOrders.addAll(allDrugOrders.findByBaseEntityId(client.getBaseEntityId()));
			}
		}
    	
    	map.put("clients", clients);
    	map.put("events", events);
    	map.put("drugs", drugs);
    	map.put("drugOrders", drugOrders);
        return new ResponseEntity<>(map, HttpStatus.OK);
	}
}
