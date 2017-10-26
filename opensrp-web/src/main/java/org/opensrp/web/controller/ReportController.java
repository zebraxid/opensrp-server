package org.opensrp.web.controller;

import com.google.gson.Gson;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.connector.DHIS2.DHIS2ReportBuilder;
import org.opensrp.connector.DHIS2.DHIS2Service;
import org.opensrp.connector.DHIS2.dxf2.DataValue;
import org.opensrp.connector.DHIS2.dxf2.DataValueSet;
import org.opensrp.connector.openmrs.service.OpenmrsReportingService;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.mapper.ELCORegisterMapper;
import org.opensrp.register.mcare.mapper.HHRegisterMapper;
import org.opensrp.register.mcare.report.mis1.MIS1Report;
import org.opensrp.register.mcare.repository.AllMembers;
import org.opensrp.register.mcare.service.reporting.HHReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ReportController {

	private OpenmrsReportingService reportService;
	private HHReportService hhReportService;
	private ELCORegisterMapper ecRegisterMapper;
	private HHRegisterMapper hhRegisterMapper;
	private AllMembers allMembers;
	
	
	@Autowired
	public ReportController(
			OpenmrsReportingService reportService,
			HHReportService hhReportService,
			ELCORegisterMapper ecRegisterMapper,
			HHRegisterMapper hhRegisterMapper, AllMembers allMembers) {
		this.reportService = reportService;
		this.hhReportService = hhReportService;
		this.ecRegisterMapper = ecRegisterMapper;
		this.hhRegisterMapper = hhRegisterMapper;
		this.allMembers = allMembers;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/report/report-definitions")
    public ResponseEntity<String> reportDefinitions() throws JSONException {
		return new ResponseEntity<>(new Gson().toJson(reportService.getReportDefinitions()),HttpStatus.OK);
    }
	
	@RequestMapping(method = RequestMethod.GET, value = "/report/report-data")
    public ResponseEntity<String> reportDefinitions(@RequestParam("uuid") String uuid) throws JSONException {
		return new ResponseEntity<>(new Gson().toJson(reportService.getReportData(uuid)),HttpStatus.OK);
    }


    //@RequestMapping(method = RequestMethod.GET, value = "/report/dhis2/mis1")
	public ResponseEntity<Map<String, JSONObject>> createAndSendMis1ReportToDhis2(@RequestParam("startDateTime")JSONObject data) {
		try {

			String startDate = data.getString("startDate");
			String endDate = data.getString("endDate");
			List<Members> members = allMembers.allMembersCreatedBetweenTwoDateBasedOnUpdatedTimeStamp(new DateTime().getMillis());
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
	/*
	@RequestMapping(method = RequestMethod.GET, value = "/report/report-hh-7-days")
    public ResponseEntity<HHRegisterDTO> reportHHForPrev7Days(@RequestParam("provider-id") String providerId) {
		 HHRegister hhRegister = hhReportService.getHHDataVisitedPrev7Days(providerId);
	     return new ResponseEntity<>(hhRegisterMapper.mapToDTO(hhRegister), HttpStatus.OK);
    }*/
/*
    @RequestMapping(method = RequestMethod.GET, value = "/report/actions")
    @ResponseBody
    public List<Action> getNewActionForANM(@RequestParam("anmIdentifier") String anmIdentifier, @RequestParam("timeStamp") Long timeStamp){
        List<org.opensrp.scheduler.Action> actions = actionService.getNewAlertsForANM(anmIdentifier, timeStamp);
        return with(actions).convert(new Converter<org.opensrp.scheduler.Action, Action>() {
            @Override
            public Action convert(org.opensrp.scheduler.Action action) {
                return ActionConvertor.from(action);
            }
        });
    }*/

}
