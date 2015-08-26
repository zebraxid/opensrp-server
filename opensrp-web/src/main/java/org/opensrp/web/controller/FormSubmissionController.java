package org.opensrp.web.controller;

import static ch.lambdaj.collection.LambdaCollections.with;
import static java.text.MessageFormat.format;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.opensrp.api.domain.Client;
import org.opensrp.api.domain.Event;
import org.opensrp.connector.OpenmrsConnector;
import org.opensrp.connector.openmrs.constants.OpenmrsHouseHold;
import org.opensrp.connector.openmrs.service.EncounterService;
import org.opensrp.connector.openmrs.service.HouseholdService;
import org.opensrp.connector.openmrs.service.PatientService;
import org.opensrp.domain.Multimedia;
import org.opensrp.dto.form.FormSubmissionDTO;
import org.opensrp.dto.form.MultimediaDTO;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.form.service.FormSubmissionConverter;
import org.opensrp.form.service.FormSubmissionService;
import org.opensrp.register.DrishtiScheduleConstants.OpenSRPEvent;
import org.opensrp.scheduler.SystemEvent;
import org.opensrp.scheduler.TaskSchedulerService;
import org.opensrp.service.MultimediaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ch.lambdaj.function.convert.Converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Controller
public class FormSubmissionController {
    private static Logger logger = LoggerFactory.getLogger(FormSubmissionController.class.toString());
    private FormSubmissionService formSubmissionService;
    private TaskSchedulerService scheduler;
    private EncounterService encounterService;
    private OpenmrsConnector openmrsConnector;
    private PatientService patientService;
    private HouseholdService householdService;
    private MultimediaService multimediaService;

    @Autowired
    public FormSubmissionController(FormSubmissionService formSubmissionService, TaskSchedulerService scheduler,
    		EncounterService encounterService, OpenmrsConnector openmrsConnector, PatientService patientService, 
    		HouseholdService householdService, MultimediaService multimediaService) {
        this.formSubmissionService = formSubmissionService;
        this.scheduler = scheduler;
        
        this.encounterService = encounterService;
        this.openmrsConnector = openmrsConnector;
        this.patientService = patientService;
        this.householdService = householdService;
        this.multimediaService = multimediaService;
    }

    @RequestMapping(method = GET, value = "/form-submissions")
    @ResponseBody
    private List<FormSubmissionDTO> getNewSubmissionsForANM(@RequestParam("anm-id") String anmIdentifier,
                                                            @RequestParam("timestamp") Long timeStamp,
                                                            @RequestParam(value = "batch-size", required = false)
                                                            Integer batchSize) {
        List<FormSubmission> newSubmissionsForANM = formSubmissionService
                .getNewSubmissionsForANM(anmIdentifier, timeStamp, batchSize);
        return with(newSubmissionsForANM).convert(new Converter<FormSubmission, FormSubmissionDTO>() {
            @Override
            public FormSubmissionDTO convert(FormSubmission submission) {
                return FormSubmissionConverter.from(submission);
            }
        });
    }

    @RequestMapping(method = GET, value="/all-form-submissions")
    @ResponseBody
    private List<FormSubmissionDTO> getAllFormSubmissions(@RequestParam("timestamp") Long timeStamp,
                                                          @RequestParam(value = "batch-size", required = false)
                                                          Integer batchSize) {
        List<FormSubmission> allSubmissions = formSubmissionService
                .getAllSubmissions(timeStamp, batchSize);
        return with(allSubmissions).convert(new Converter<FormSubmission, FormSubmissionDTO>() {
            @Override
            public FormSubmissionDTO convert(FormSubmission submission) {
                return FormSubmissionConverter.from(submission);
            }
        });
    }

    @RequestMapping(headers = {"Accept=application/json"}, method = POST, value = "/form-submissions")
    public ResponseEntity<HttpStatus> submitForms(@RequestBody List<FormSubmissionDTO> formSubmissionsDTO) {
        try {
            if (formSubmissionsDTO.isEmpty()) {
                return new ResponseEntity<>(BAD_REQUEST);
            }

            scheduler.notifyEvent(new SystemEvent<>(OpenSRPEvent.FORM_SUBMISSION, formSubmissionsDTO));
            
            try{
            ////////TODO MAIMOONA : SHOULD BE IN EVENT but event needs to be moved to web so for now kept here
            String json = new Gson().toJson(formSubmissionsDTO);
            System.out.println("MMMMMMMMMMMYYYYYYYYYYYYYY::"+json);
            List<FormSubmissionDTO> formSubmissions = new Gson().fromJson(json, new TypeToken<List<FormSubmissionDTO>>() {
            }.getType());
            List<FormSubmission> fsl = with(formSubmissions).convert(new Converter<FormSubmissionDTO, FormSubmission>() {
                @Override
                public FormSubmission convert(FormSubmissionDTO submission) {
                    return FormSubmissionConverter.toFormSubmission(submission);
                }
            });
            for (FormSubmission formSubmission : fsl) {
            	if(openmrsConnector.isOpenmrsForm(formSubmission)){
	            	JSONObject p = patientService.getPatientByIdentifier(formSubmission.entityId());
	            	
	            	if(p != null){	            		
	            		Event e = openmrsConnector.getEventFromFormSubmission(formSubmission);
		        		System.out.println(encounterService.createEncounter(e));
	            	}
	            	else {
	            		Map<String, Map<String, Object>> dep = openmrsConnector.getDependentClientsFromFormSubmission(formSubmission);
	            		
	            		if(dep.size()>0){
	            			Client hhhClient = openmrsConnector.getClientFromFormSubmission(formSubmission);
	            			Event hhhEvent = openmrsConnector.getEventFromFormSubmission(formSubmission);
	            			OpenmrsHouseHold hh = new OpenmrsHouseHold(hhhClient, hhhEvent);
	    	    			for (Map<String, Object> cm : dep.values()) {
	    	    				hh.addHHMember((Client)cm.get("client"), (Event)cm.get("event"));
	    	    			}
	    	    			
	    	    			householdService.saveHH(hh);
	            		}
	            		else {
	            			Client c = openmrsConnector.getClientFromFormSubmission(formSubmission);
	            			System.out.println(patientService.createPatient(c));
	            			Event e = openmrsConnector.getEventFromFormSubmission(formSubmission);
			        		System.out.println(encounterService.createEncounter(e));
	            		}
	            	}
            	}
    		}
            }
            catch(Exception e){
            	e.printStackTrace();
            }
            logger.debug(format("Added Form submissions to queue.\nSubmissions: {0}", formSubmissionsDTO));
        } catch (Exception e) {
            logger.error(format("Form submissions processing failed with exception {0}.\nSubmissions: {1}", e, formSubmissionsDTO));
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(CREATED);
    }
    
    @RequestMapping(headers = {"Accept=application/json"}, method = GET, value = "/multimedia-file")
    public List<MultimediaDTO> getFiles(@RequestParam("anm-id") String providerId) {
    	
    	List<Multimedia> Multimedias = multimediaService.getMultimediaFiles(providerId);
    	
    	return with(Multimedias).convert(new Converter<Multimedia, MultimediaDTO>() {
			@Override
			public MultimediaDTO convert(Multimedia md) {
				return new MultimediaDTO(md.getCaseId(), md.getProviderId(), md.getFileName());
			}
		});
    }
    @RequestMapping(headers = {"Accept=application/json"}, method = POST, value = "/multimedia-file")
    public ResponseEntity<HttpStatus> uploadFiles(@RequestBody List<MultimediaDTO> multimediaDTO, @RequestParam("file") MultipartFile file) {
    	String json = new Gson().toJson(multimediaDTO);
    	List<MultimediaDTO> multimedia = new Gson().fromJson(json, new TypeToken<MultimediaDTO>() {
        }.getType());
    	
    	 List<Multimedia> mdl = with(multimedia).convert(new Converter<MultimediaDTO, Multimedia>() {
             @Override
             public Multimedia convert(MultimediaDTO multimedia) {
                 return new Multimedia(multimedia.caseId(), multimedia.providerId(), multimedia.fileName());
             }
         });
    	
    	 for(Multimedia md : mdl)
    	 {
    		 multimediaService.saveMultimediaFile(md, file);
    	 }
    	 return new ResponseEntity<>(HttpStatus.OK);
    }
}
