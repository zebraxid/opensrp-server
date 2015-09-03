package org.opensrp.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Properties;

import org.opensrp.domain.Multimedia;
import org.opensrp.dto.form.MultimediaDTO;
import org.opensrp.repository.MultimediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MultimediaService {

	private final MultimediaRepository multimediaRepository;
	private String baseMultimediaDirPath;

	@Autowired
	public MultimediaService(
			MultimediaRepository multimediaRepository,
			@Value("#{opensrp['multimedia.directory.name']}") String baseMultimediaDirPath) {
		this.multimediaRepository = multimediaRepository;
		this.baseMultimediaDirPath = baseMultimediaDirPath;
	}

	public String saveMultimediaFile(MultimediaDTO multimediaDTO, MultipartFile file) {
		
		boolean uploadStatus = uoloadFile(multimediaDTO, file);

		if (uploadStatus) {
			try {
				Multimedia multimediaFile = new Multimedia()
						.withCaseId(multimediaDTO.caseId())
						.withProviderId(multimediaDTO.providerId())
						.withFileName(multimediaDTO.fileName());

				multimediaRepository.add(multimediaFile);

				return "success";

			} catch (Exception e) {
				e.getMessage();
			}
		}

		return "fail";

	}

	public boolean uoloadFile(MultimediaDTO multimediaDTO,
			MultipartFile multimediaFile) {
		// String BASE_MULTIMEDIA_DIR = "../assets/multimedia";
		// String rootPath = System.getProperty("user.home");

		if (!multimediaFile.isEmpty()) {
			try {

				byte[] bytes = multimediaFile.getBytes();

				String multimediaDirPath = baseMultimediaDirPath
						+ File.separator + multimediaDTO.providerId()
						+ File.separator;

				File multimediaDir = new File(multimediaDirPath);

				switch ("") {
				case "application/octet-stream":
					multimediaDirPath += "videos" + File.separator
							+ multimediaDTO.caseId() + ".mp4";
					break;

				case "image/jpeg":
					multimediaDirPath += "images" + File.separator
							+ multimediaDTO.caseId() + ".jpg";
					break;

				case "image/gif":
					multimediaDirPath += "images" + File.separator
							+ multimediaDTO.caseId() + ".gif";
					break;

				case "image/png":
					multimediaDirPath += "images" + File.separator
							+ multimediaDTO.caseId() + ".png";
					break;

				default:
					multimediaDirPath += "images" + File.separator
							+ multimediaDTO.caseId() + ".jpg";
					break;

				}

				if (!multimediaDir.exists())
					multimediaDir.mkdirs();

				// multimediaFile.transferTo(dir);

				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(multimediaDirPath));
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

	public List<Multimedia> getMultimediaFiles(String providerId) {
		return multimediaRepository.all(providerId);
	}
}
