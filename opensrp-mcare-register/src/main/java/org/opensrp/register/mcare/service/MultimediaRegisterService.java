/**
 * The MultimediaRegisterService class implements Multimedia support for Elco registry and Household registry. 
 * @author Asifur
 */

package org.opensrp.register.mcare.service;

import static org.opensrp.common.util.EasyMap.create;

import java.util.List;
import java.util.Map;

import org.opensrp.domain.Multimedia;
import org.opensrp.register.mcare.domain.Elco;
import org.opensrp.register.mcare.domain.HouseHold;
import org.opensrp.register.mcare.repository.AllElcos;
import org.opensrp.register.mcare.repository.AllHouseHolds;
import org.opensrp.repository.MultimediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MultimediaRegisterService {
	private final AllHouseHolds allHouseHolds;
	private final AllElcos allElcos;
	private MultimediaRepository multimediaRepository;
	
	@Autowired
	public MultimediaRegisterService(AllHouseHolds allHouseHolds, AllElcos allElcos, MultimediaRepository multimediaRepository)
	{
		this.allHouseHolds = allHouseHolds;
		this.allElcos = allElcos;
		this.multimediaRepository = multimediaRepository;
	}
	
	public void getMultimedia()
	{
		
		List<HouseHold> hhs = allHouseHolds.findAllHouseHolds();
        
        for (HouseHold hh : hhs) {
        	
        	List<Multimedia> multimediaList = multimediaRepository.findByCaseIdAndFileCategory(hh.caseId(), "dp");
    		
        	if(multimediaList.size()>0)
            {
        	    for (Multimedia multimedia : multimediaList){
        	    	
	    			 Map<String, String> att = create("contentType", multimedia.getContentType())
	    				.put("filePath", multimedia.getFilePath())
	    				.put("fileCategory", multimedia.getFileCategory())
	    				.map();       		
	    	
	    			 hh.multimediaAttachments().add(att);
        	    }
    		}
        	        	       	
      		allHouseHolds.update(hh);
        }
        
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
	    	
	    			 ec.multimediaAttachments().add(att);
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
	    	
	    			 ec.multimediaAttachments().add(att);
	    	    }
			}
	    	
	    	allElcos.update(ec);
		}
	}
}
