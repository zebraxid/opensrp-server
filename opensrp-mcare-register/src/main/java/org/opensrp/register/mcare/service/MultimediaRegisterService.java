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
        	
    		hh.multimediaAttachments().clear();
        	
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
        	
        	for (int i = 0; i < hh.ELCODETAILS().size(); i++){
        		
        		String id = hh.ELCODETAILS().get(i).get("id");
        		
        		List<Multimedia> profileImagePath = multimediaRepository.findByCaseIdAndFileCategory(id, "dp");
        		
        		if(profileImagePath.size()>0)
                {
        			hh.ELCODETAILS().get(i).put("profileImagePath", profileImagePath.get(0).getFilePath());
                }
        		
        		List<Multimedia> nidImagePath = multimediaRepository.findByCaseIdAndFileCategory(id, "nidImage");
        		
        		if(nidImagePath.size()>0)
                {
        			hh.ELCODETAILS().get(i).put("nidImagePath", nidImagePath.get(0).getFilePath());
                }

        	}
        	        	       	
      		allHouseHolds.update(hh);
        }
        
		List<Elco> elcos = allElcos.allOpenELCOs();

		for (Elco ec : elcos) {
	    	
    		ec.multimediaAttachments().clear();
	    	
	    	List<Multimedia> multimediaList1 = multimediaRepository.findByCaseIdAndFileCategory(ec.caseId(), "dp");
			
	    	if(multimediaList1.size()>0)
	        {	    		
	    	    for (Multimedia multimedia : multimediaList1){
	    	    	   	    	
	    	    	Map<String, String> att = create("contentType", multimedia.getContentType())
	    				.put("filePath", multimedia.getFilePath())
	    				.put("fileCategory", multimedia.getFileCategory())
	    				.map();       		
	    	
	    			 ec.multimediaAttachments().add(att);
	    	    }
			}
	    	
	    	List<Multimedia> multimediaList2 = multimediaRepository.findByCaseIdAndFileCategory(ec.caseId(), "nidImage");
			
	    	if(multimediaList2.size()>0)
	        { 	    	
	    	    for (Multimedia multimedia : multimediaList2){
	    	    	
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
