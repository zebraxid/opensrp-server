package org.opensrp.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.opensrp.domain.Client;
import org.opensrp.domain.Multimedia;
import org.opensrp.dto.form.MultimediaDTO;
import org.opensrp.repository.MultimediaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@Service
public class MultimediaService {

	private static Logger logger = LoggerFactory.getLogger(MultimediaService.class.toString());

	public static final String IMAGES_DIR = "patient_images";

	private static final String VIDEOS_DIR = "videos";

	private final MultimediaRepository multimediaRepository;

	private final ClientService clientService;

	private String multimediaDirPath;

	@Value("#{opensrp['multimedia.directory.name']}")
	String baseMultimediaDirPath;

	@Value("#{opensrp['aws_access_key_id']}")
	String awsAccessKeyId;

	@Value("#{opensrp['aws_secret_access_key']}")
	String awsSecretAccessKey;

	@Value("#{opensrp['aws_region']}")
	String awsRegion;

	@Value("#{opensrp['aws_bucket']}")
	String awsBucket;

	@Value("#{opensrp['aws_key_folder']}")
	String awsKeyFolder;

	@Autowired
	public MultimediaService(MultimediaRepository multimediaRepository, ClientService clientService) {
		this.multimediaRepository = multimediaRepository;
		this.clientService = clientService;
	}

	public String saveMultimediaFile(MultimediaDTO multimediaDTO, MultipartFile file) {

		if (uploadFile(multimediaDTO, file)) {
			try {
				logger.info("Image path : " + multimediaDirPath);

				Multimedia multimediaFile = new Multimedia().withCaseId(multimediaDTO.getCaseId())
						.withProviderId(multimediaDTO.getProviderId()).withContentType(multimediaDTO.getContentType())
						.withFilePath(multimediaDTO.getFilePath()).withFileCategory(multimediaDTO.getFileCategory());

				multimediaRepository.add(multimediaFile);
				Client client = clientService.getByBaseEntityId(multimediaDTO.getCaseId());
				if (client != null) {
					if (client.getAttribute("Patient Image") != null) {
						client.removeAttribute("Patient Image");
					}
					client.addAttribute("Patient Image", multimediaDTO.getCaseId() + ".jpg");
					client.setServerVersion(null);
					clientService.updateClient(client);
				}
				return "success";
			}
			catch (Exception e) {
				e.getMessage();
			}
		}

		return "fail";
	}

	public boolean uploadFile(MultimediaDTO multimediaDTO, MultipartFile multimediaFile) {

		// String baseMultimediaDirPath = System.getProperty("user.home");

		if (!multimediaFile.isEmpty()) {
			try {

				multimediaDirPath = baseMultimediaDirPath + File.separator;
				String fileExt = ".jpg";
				switch (multimediaDTO.getContentType()) {

					case "application/octet-stream":
						multimediaDirPath += VIDEOS_DIR;
						fileExt = ".mp4";
						break;

					case "image/jpeg":
						multimediaDirPath += IMAGES_DIR;
						fileExt = ".jpg";
						break;

					case "image/gif":
						multimediaDirPath += IMAGES_DIR;
						fileExt = ".gif";
						break;

					case "image/png":
						multimediaDirPath += IMAGES_DIR;
						fileExt = ".png";
						break;

				}
				new File(multimediaDirPath).mkdir();
				String fileName = multimediaDirPath + File.separator + multimediaDTO.getCaseId() + fileExt;
				multimediaDTO.withFilePath(fileName);
				File multimediaDir = new File(fileName);

				multimediaFile.transferTo(multimediaDir);

				uploadImageTos3(multimediaDir, awsAccessKeyId, awsSecretAccessKey, awsRegion,awsBucket, awsKeyFolder);

				return true;

			}
			catch (Exception e) {
				logger.error("", e);
				return false;
			}
		} else {
			return false;
		}
	}

	public List<Multimedia> getMultimediaFiles(String providerId) {
		return multimediaRepository.all(providerId);
	}

	public Multimedia findByCaseId(String entityId) {
		return multimediaRepository.findByCaseId(entityId);
	}

	public boolean uploadImageTos3(File imageFile, String awsAccessKeyId, String awsSecretAccessKey, String awsRegion,
	                               String awsBucket, String awsKeyFolder) {

		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(
				new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccessKeyId, awsSecretAccessKey)))
				.withRegion(awsRegion).build();
		InputStream inputStream;
		try {
			byte[] md5 = DigestUtils.md5(new FileInputStream(imageFile));
			inputStream = new FileInputStream(imageFile);
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(imageFile.length());
			metadata.setContentMD5(new String(Base64.encodeBase64(md5)));
			PutObjectRequest request = new PutObjectRequest(awsBucket, awsKeyFolder + imageFile.getName(), inputStream,
					metadata);
			//			request.setCannedAcl(CannedAccessControlList.PublicRead);
			//			PutObjectResult result = s3Client.putObject(request);
			s3Client.putObject(request);
			return true;
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}
}
