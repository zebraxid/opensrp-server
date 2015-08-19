package org.opensrp.register.mcare.mapper;

import static ch.lambdaj.collection.LambdaCollections.with;
import static org.opensrp.common.AllConstants.HHRegistrationFields.*;

import java.util.List;

import org.opensrp.dto.register.HHRegisterDTO;
import org.opensrp.dto.register.HHRegisterEntryDTO;
import org.opensrp.register.mcare.HHRegisterEntry;
import org.opensrp.register.mcare.HHRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ch.lambdaj.function.convert.Converter;

@Component
public class HHRegisterMapper {

	private static Logger logger = LoggerFactory.getLogger(ELCORegisterMapper.class.toString());
	
	public HHRegisterDTO mapToDTO(HHRegister hhRegister)
	{

        List<HHRegisterEntryDTO> hhRegisterEntryDTOs =
                with(hhRegister.hhRegisterEntries())
                        .convert(new Converter<HHRegisterEntry, HHRegisterEntryDTO>() {
                            @Override
                            public HHRegisterEntryDTO convert(HHRegisterEntry entry) {
                                return new HHRegisterEntryDTO()
                                .withCASEID(entry.CASEID())
                                .withPROVIDERID(entry.PROVIDERID())  
                        		//.withLOCATIONID(entry.LOCATIONID())
                        		.withTODAY(entry.TODAY())
                        		.withFWNHREGDATE(entry.FWNHREGDATE())
                        		.withFWGOBHHID(entry.FWGOBHHID())
                        		.withFWJIVHHID(entry.FWJIVHHID())
                        		.withFWNHHHGPS(entry.FWNHHHGPS())
                        		.withFWHOHFNAME(entry.FWHOHFNAME())
                        		.withFWHOHLNAME(entry.FWHOHLNAME())
                        		.withFWHOHBIRTHDATE(entry.FWHOHBIRTHDATE())
                        		.withFWHOHGENDER(entry.FWHOHGENDER())
                        		.withFWNHHMBRNUM(entry.FWNHHMBRNUM())
                        		.withFWNHHMWRA(entry.FWNHHMWRA())
                        		.withMWRA(entry.MWRA())
                        		.withELCODETAILS(entry.ELCODETAILS())
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
        return new HHRegisterDTO(hhRegisterEntryDTOs);   
	
	}
}