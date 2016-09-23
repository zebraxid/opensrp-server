package org.opensrp.web.rest.rapid;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.opensrp.domain.Client;
import org.opensrp.domain.Event;
import org.opensrp.service.ClientService;
import org.opensrp.service.EventService;
import org.opensrp.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/rest/rapid/client")
public class ANCClientResource {
	@Autowired
	ClientService clientService;
	@Autowired
    EventService eventService;

	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static final String ANC_CONCEPT="162942AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	private static final String ANC_CONCEPT_VALUE="1065AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	private static final String ANC_LMP_CONCEPT="1427AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";

	@RequestMapping("/anc")
	@ResponseBody
	public Map<String, Object> getAncClient(HttpServletRequest req) {
		String id = req.getParameter("id");
		Map<String, Object> m = new HashMap<String, Object>();
		Client client = clientService.find(id);
		if (client == null) {
			m.put("found", false);
			return m;
		}

		Date now = new Date();

		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.DATE, 1);
		String dateTo = dateFormat.format(cal.getTime());
		cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.DATE, -315);
		String dateFrom = dateFormat.format(cal.getTime());

		List<Event> events = eventService.findByClientAndConceptAndDate(client.getBaseEntityId(), ANC_CONCEPT,
				ANC_CONCEPT_VALUE, dateFrom, dateTo);
		if(events!=null && !events.isEmpty()){
			//Date lmp=getLmp(events.get(0));
			eventService.findByEventTypeAndDate("0475cc01-80fc-4c28-a40b-e43728d29ad6", "ANC Reminder Visit 1", dateFrom, dateTo);
		}
		
//		m.put("found", true);
//		String eventType = req.getParameter("eventType");
//		m.put("client", client);
//
//		int age = Weeks.weeksBetween(client.getBirthdate(), new DateTime().now()).getWeeks();
//		m.put("age", age);
//
//		ArrayList<String> ev = eligibleVaccines(age);
//		m.put("eligibleVaccines", ev);
//		Map<String, Object> receivedVacines = new HashMap<>();
//		try {
//			List<Event> el = eventService.findByBaseEntityId(client.getBaseEntityId());
//			m.put("vaccineCard", makeVaccineCard(el, ev));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		m.put("receivedVaccines", receivedVacines);
		return m;
	}
   private Date getLmp(Event event) throws Exception{
	Map<String, Object> obs = Utils.getEventObs(Utils.eventToJson(event));
	if(obs==null || (obs!=null && !obs.containsKey(ANC_LMP_CONCEPT))){
		return null;
	}
	String lmp=obs.get(ANC_LMP_CONCEPT).toString();
	return dateFormat.parse(lmp);
	
}
}
