package org.opensrp.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.opensrp.domain.Multimedia;
import org.opensrp.repository.MultimediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MultimediaService {

	private final MultimediaRepository multimediaRepository;
	
	@Autowired
	public MultimediaService(MultimediaRepository multimediaRepository)
	{
		this.multimediaRepository = multimediaRepository;
	}
	
	public String saveMultimediaFile(Multimedia multimedia, MultipartFile file)
	{
		boolean uploadStatus = uoloadFile(file,multimedia.getFileName());
		if(uploadStatus)
		{
			Multimedia multimediaFile = new Multimedia()
										.withCaseId(multimedia.getCaseId())
										.withProviderId(multimedia.getProviderId())
										.withFileName(multimedia.getFileName());
			
			multimediaRepository.add(multimediaFile);
			
			return "success";
		}
		else
		   return	"fail";
	}
	
	private boolean uoloadFile(MultipartFile file, String name)
	{
		if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream = 
                        new BufferedOutputStream(new FileOutputStream(new File(name + "-uploaded")));
                stream.write(bytes);
                stream.close();
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
	}
	
	public List<Multimedia> getMultimediaFiles(String providerId)
	{
	 return	multimediaRepository.all(providerId);
	}
}
