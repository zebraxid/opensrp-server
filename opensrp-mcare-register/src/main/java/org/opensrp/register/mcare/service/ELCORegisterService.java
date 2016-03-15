package org.opensrp.register.mcare.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.opensrp.domain.Multimedia;
import org.opensrp.register.mcare.ELCORegister;
import org.opensrp.register.mcare.ELCORegisterEntry;
import org.opensrp.register.mcare.domain.Elco;
import org.opensrp.register.mcare.repository.AllElcos;
import org.opensrp.repository.MultimediaRepository;

import static org.opensrp.common.AllConstants.ELCORegistrationFields.*;
import static org.opensrp.common.util.EasyMap.create;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ELCORegisterService {
	
	private final AllElcos allElcos;
	private MultimediaRepository multimediaRepository;
	
	@Autowired
	public ELCORegisterService(AllElcos allElcos, MultimediaRepository multimediaRepository)
	{
		this.allElcos = allElcos;
		this.multimediaRepository = multimediaRepository;
	}

	public ELCORegister getELCORegisterForProvider(String providerId)
	{
		ArrayList<ELCORegisterEntry> elcoRegisterEntries = new ArrayList<>();
        List<Elco> elcos = allElcos.allOpenELCOs();
        

        for (Elco ec : elcos) {
        	
        	List<Multimedia> multimediaList = multimediaRepository.findByCaseIdAndFileCategory(ec.caseId(), "dp");
    		
        	if(multimediaList.size()>0)
            {
        	    for (Multimedia multimedia : multimediaList){
        	    	
	    			 Map<String, String> att = create("contentType", multimedia.getContentType())
	    				.put("filePath", multimedia.getFilePath())
	    				.put("fileCategory", multimedia.getFileCategory())
	    				.map();       		
	    	
	    			 ec.attachments().add(att);
        	    }
    		}
        	
        	multimediaList = multimediaRepository.findByCaseIdAndFileCategory(ec.caseId(), "nidImage");
    		
        	if(multimediaList.size()>0)
            {
        	    for (Multimedia multimedia : multimediaList){
        	    	
	    			 Map<String, String> att = create("contentType", multimedia.getContentType())
	    				.put("filePath", multimedia.getFilePath())
	    				.put("fileCategory", multimedia.getFileCategory())
	    				.map();       		
	    	
	    			 ec.attachments().add(att);
        	    }
    		}
        	
        	allElcos.update(ec);
        
            ELCORegisterEntry ecRegisterEntry = new ELCORegisterEntry()
            		.withCASEID(ec.caseId())
            		.withINSTANCEID(ec.INSTANCEID())
            		.withPROVIDERID(ec.PROVIDERID())
            		.withLOCATIONID(ec.LOCATIONID())
            		.withTODAY(ec.TODAY())
            		//.withSTART(ec.START())
            		//.withEND(ec.END())
            		.withGOBHHID(ec.GOBHHID())
            		.withJiVitAHHID(ec.JiVitAHHID())
            		.withexisting_ELCO(ec.existing_ELCO())
            		.withnew_ELCO(ec.new_ELCO())
            		.withELCO(ec.ELCO())
            		.withWomanREGDATE(ec.WomanREGDATE())
            		.withFWCWOMHUSALV(ec.FWCWOMHUSALV())
            		.withFWCWOMHUSLIV(ec.FWCWOMHUSLIV())
            		.withFWCWOMHUSSTR(ec.FWCWOMHUSSTR())
            		.withFWCWOMSTRMEN(ec.FWCWOMSTRMEN())
            		.withFWCENDATE(ec.FWCENDATE())
            		.withFWCENSTAT(ec.FWCENSTAT())
            		.withFWWOMFNAME(ec.FWWOMFNAME())
            		.withFWWOMLNAME(ec.FWWOMLNAME())
            		.withFWWOMANYID(ec.FWWOMANYID())
            		.withFWWOMNID(ec.FWWOMNID())
            		.withFWWOMRETYPENID(ec.FWWOMRETYPENID())
            		.withFWWOMRETYPENID_CONCEPT(ec.FWWOMRETYPENID_CONCEPT())
            		.withFWWOMBID(ec.FWWOMBID())
            		.withFWWOMRETYPEBID(ec.FWWOMRETYPEBID())
            		.withFWWOMRETYPEBID_CONCEPT(ec.FWWOMRETYPEBID_CONCEPT())
            		.withFWHUSNAME(ec.FWHUSNAME())
            		.withFWBIRTHDATE(ec.FWBIRTHDATE())
            		.withFWGENDER(ec.FWGENDER())
            		.withFWWOMAGE(ec.FWWOMAGE())
            		.withFWELIGIBLE(ec.FWELIGIBLE())
            		.withFWELIGIBLE2(ec.FWELIGIBLE2())
            		.withFWWOMCOUNTRY(ec.FWWOMCOUNTRY())
                    .withFWWOMDIVISION(ec.FWWOMDIVISION())
                    .withFWWOMDISTRICT(ec.FWWOMDISTRICT())
                    .withFWWOMUPAZILLA(ec.FWWOMUPAZILLA())
                    .withFWWOMUNION(ec.FWWOMUNION())
                    .withFWWOMWARD(ec.FWWOMWARD())
                    .withFWWOMSUBUNIT(ec.FWWOMSUBUNIT())
                    .withFWWOMMAUZA_PARA(ec.FWWOMMAUZA_PARA())
                    .withFWWOMGOBHHID(ec.FWWOMGOBHHID())
                    .withFWWOMGPS(ec.FWWOMGPS())
                    .withform_name(ec.form_name())
            		.withDetails(ec.details())
            		.withattachments(ec.attachments())
            		.withPSRFDETAILS(ec.PSRFDETAILS())
            		.withSTART(ec.getDetail(START_DATE))
            		.withEND(ec.getDetail(END_DATE))
            		.withFWDISPLAYAGE(ec.getDetail(FW_DISPLAY_AGE))
            		.withFWWOMSTRMEN(ec.getDetail(FW_CWOMSTRMEN))
            		.withFWWOMHUSSTR(ec.getDetail(FW_CWOMHUSSTR))
            		.withFWWOMHUSALV(ec.getDetail(FW_CWOMHUSALV))
            		.withFWWOMHUSLIV(ec.getDetail(FW_CWOMHUSLIV));     
            
            elcoRegisterEntries.add(ecRegisterEntry);
        }
        return new ELCORegister(elcoRegisterEntries);
	}
}