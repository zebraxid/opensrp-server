package org.opensrp.register.mcare.mapper;

import static ch.lambdaj.collection.LambdaCollections.with;

import java.util.List;

import org.opensrp.dto.register.ChildRegisterDTO;
import org.opensrp.dto.register.ChildRegisterEntryDTO;
import org.opensrp.register.mcare.ChildRegistration;
import org.opensrp.register.mcare.ChildRegistrationEntry;
import org.springframework.stereotype.Component;

import ch.lambdaj.function.convert.Converter;

@Component
public class ChildRegisterMapper {
	
	public ChildRegisterDTO mapToDTO(ChildRegistration childRegister) {
		
		List<ChildRegisterEntryDTO> childRegisterEntryDTOs = with(childRegister.childRegistrationEntries()).convert(
		    new Converter<ChildRegistrationEntry, ChildRegisterEntryDTO>() {
			    
			    @Override
			    public ChildRegisterEntryDTO convert(ChildRegistrationEntry entry) {
				    return new ChildRegisterEntryDTO()
				            .withCASEID(entry.CASEID())
				            .withINSTANCEID(entry.INSTANCEID())
				            .withPROVIDERID(entry.PROVIDERID())
				            //.withLOCATIONID(entry.LOCATIONID())
				            .withTODAY(entry.TODAY())
				            .withFWNHREGDATE(entry.FWNHREGDATE())
				            //.withFWGOBHHID(entry.FWGOBHHID())
				            //.withFWJIVHHID(entry.FWJIVHHID())
				            .withFWCOUNTRY(entry.FWCOUNTRY())
				            .withFWDIVISION(entry.FWDIVISION())
				            .withFWDISTRICT(entry.FWDISTRICT())
				            .withFWUPAZILLA(entry.FWUPAZILLA())
				            .withFWUNION(entry.FWUNION())
				            .withFWWARD(entry.FWWARD())
				            .withFWSUBUNIT(entry.FWSUBUNIT())
				            .withFWMAUZA_PARA(entry.FWMAUZA_PARA())
				            //.withFWNHHHGPS(entry.FWNHHHGPS())
				            .withform_name(entry.form_name())
				            .withFWHOHFNAME(entry.FWHOHFNAME())
				            .withFWHOHLNAME(entry.FWHOHLNAME())
				            .withFWHOHBIRTHDATE(entry.FWHOHBIRTHDATE())
				            .withFWHOHGENDER(entry.FWHOHGENDER())
				            //.withFWNHHMBRNUM(entry.FWNHHMBRNUM())
				            //.withFWNHHMWRA(entry.FWNHHMWRA())
				            .withELCO(entry.ELCO())
				            .withuser_type(entry.user_type())
				            .withexternal_user_ID(entry.external_user_ID())
				            .withcurrent_formStatus(entry.current_formStatus())
				            .withELCODETAILS(entry.ELCODETAILS())
				            .withmultimediaAttachments(entry.multimediaAttachments())
				            .withDetails(entry.details())
				            .withLOCATIONID(entry.LOCATIONID())
				            .withTODAY(entry.TODAY())
				            .withSTART(entry.START())
				            .withEND(entry.END())
				    /*.withFWWOMAGE(entry.FWWOMAGE())
				    .withFWBIRTHDATE(entry.FWBIRTHDATE())
				    .withid(entry.id())
				    .withFWWOMLNAME(entry.FWWOMLNAME())
				    .withFWWOMFNAME(entry.FWWOMFNAME())
				    .withJiVitAHHID(entry.FWJIVHHID())
				    .withFWGENDER(entry.FWGENDER())
				    .withFWWOMBID(entry.FWWOMBID())
				    .withFWWOMNID(entry.FWWOMNID())
				    .withFWHUSNAME(entry.FWHUSNAME())
				    .withFWELIGIBLE(entry.FWELIGIBLE())
				    .withFWDISPLAYAGE(entry.FWDISPLAYAGE())*/;
			    }
		    });
		return new ChildRegisterDTO(childRegisterEntryDTOs);
		
	}
}
