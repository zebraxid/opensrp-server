package org.opensrp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.opensrp.domain.Multimedia;
import org.opensrp.dto.form.MultimediaDTO;
import org.opensrp.repository.MultimediaRepository;
import org.opensrp.service.ClientService;
import org.opensrp.service.MultimediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-applicationContext-opensrp.xml")
public class MultimediaServiceTest {

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

	@Mock
	private MultimediaService multimediaService;

	@Autowired
	private MultimediaRepository multimediaRepository;

	@Autowired
	private ClientService clientService;

	@Autowired
	@Value("#{opensrp['multimedia.directory.name']}")
	private String multimediaDirPath;

	@Before
	public void setUp() throws Exception {
		initMocks(this);

		multimediaService = new MultimediaService(multimediaRepository, clientService);
	}

	@Ignore
	@Test
	public void shouldSaveMultimediaFile() throws FileNotFoundException {
		MultimediaDTO multimedia = new MultimediaDTO("1234567891", "opensrp", "image/jpeg", "", "nid");
		String baseDirPath = System.getProperty("user.home");
		FileInputStream fis = new FileInputStream(baseDirPath + File.separator + ".OpenSRP/multimedia/image.jpeg");

		MultipartFile multipartFile = null;

		try {
			multipartFile = new MockMultipartFile("file", fis);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String status = multimediaService.saveMultimediaFile(multimedia, multipartFile);

		Assert.assertEquals("success", status);

	}

	@Ignore
	@Test
	public void shouldGetMultimediaFiles() throws FileNotFoundException {
		MultimediaDTO multimediaDTO = new MultimediaDTO("1234567890", "opensrp", "image/jpeg", "", "profile");

		Multimedia expectedMultimedia = new Multimedia().withCaseId(multimediaDTO.getCaseId())
				.withProviderId(multimediaDTO.getProviderId()).withContentType(multimediaDTO.getContentType())
				.withFilePath(multimediaDTO.getFilePath()).withFileCategory(multimediaDTO.getFileCategory());

		String baseDirPath = System.getProperty("user.home");
		FileInputStream fis = new FileInputStream(baseDirPath + File.separator + ".OpenSRP/multimedia/image.jpeg");
		MultipartFile multipartFile = null;

		try {
			multipartFile = new MockMultipartFile("file", fis);
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		boolean status = multimediaService.uploadFile(multimediaDTO, multipartFile);

		if (status)
			multimediaRepository.add(expectedMultimedia);

		List<Multimedia> multimediaFiles = multimediaService.getMultimediaFiles("opensrp");

		for (Multimedia actualMultimedia : multimediaFiles) {
			if (actualMultimedia.getCaseId().equals(multimediaDTO.getCaseId()))
				Assert.assertEquals(expectedMultimedia.getFilePath(), actualMultimedia.getFilePath());
		}
	}

	@Test
	public void shouldSaveImageTos3() throws Exception {
		File fileImage = new File("/Users/ona/.OpenMRS/patient_images/7f58a6cf-51ec-4bb4-929e-5ffcd3b1fac3.jpg");
		Assert.assertTrue(multimediaService
				.uploadImageTos3(fileImage, awsAccessKeyId, awsSecretAccessKey, awsRegion, awsBucket, awsKeyFolder));

	}
}
