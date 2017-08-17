package org.opensrp.web.controller.it;

import org.codehaus.jackson.JsonNode;
import org.junit.Before;
import org.junit.Test;
import org.opensrp.web.rest.it.BaseResourceTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.http.MediaType;
import org.springframework.test.web.server.MvcResult;
import org.springframework.test.web.server.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import java.io.*;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;

public class FormDownloadIntegrationTest extends BaseResourceTest {

	String BASE_URL = "/form/";

	String formToDownload;

/*
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
	}
*/

	@Test
	public void shouldFetchAllAvailableVersion() throws Exception {
		String url = "latest-form-versions";

		JsonNode actualObj = getCallAsJsonNode(BASE_URL + url, "", status().isOk());

		assertEquals(currentFormVersions(), actualObj);
	}

	@Test
	public void shouldDownloadCurrentChildFollowupForms() throws Exception {
		String url = "form-files";

		byte[] responseZipFile = getCallAsByeArray(BASE_URL + url, "formDirName=child_followup", status().isOk());
		byte[] expectedZipFile = getFormDirectoryAsZip("child_followup");

		assertArrayEquals(expectedZipFile, responseZipFile);
	}

	@Test(expected = NestedServletException.class)
	public void shouldThrowExceptionForInvalidFormDirectoryName() throws Exception {
		String url = "form-files";
		getCallAsByeArray(BASE_URL + url, "formDirName=invalid", status().isOk());
	}

	private byte[] getFormDirectoryAsZip(String directoryName) throws IOException {
		Resource resource = new ClassPathResource("/opensrp.properties");
		Properties props = PropertiesLoaderUtils.loadProperties(resource);
		String formDirPath = props.getProperty("form.directory.name");
		formToDownload = props.getProperty("form.download.files").replace(" ", "");
		ResourceLoader loader = new DefaultResourceLoader();
		return zipFiles(new File(loader.getResource(formDirPath).getURI().getPath() + "/" + directoryName));

	}

	private byte[] zipFiles(File directory) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(baos);
		byte bytes[] = new byte[2048];
		String[] fl = directory.list();
		for (String fileName : fl) {
			if (formToDownload.matches("(.+,)?" + fileName + "(,.+)?$")) {

				FileInputStream fis = new FileInputStream(directory.getPath() + "/" + fileName);
				BufferedInputStream bis = new BufferedInputStream(fis);

				zos.putNextEntry(new ZipEntry(fileName));

				int bytesRead;
				while ((bytesRead = bis.read(bytes)) != -1) {
					zos.write(bytes, 0, bytesRead);
				}
				zos.closeEntry();
				bis.close();
				fis.close();
			}
		}

		zos.flush();
		baos.flush();
		zos.close();
		baos.close();

		return baos.toByteArray();
	}

	/**
	 * Change the return type if any form name, directory or version changes.
	 *
	 * @return
	 * @throws IOException
	 */
	private JsonNode currentFormVersions() throws IOException {
		String currentFormVersionList = "{\"formVersions\" : [{\"formName\": \"Offsite_Child_Vaccination_Followup\", \"formDirName\": \"offsite_child_followup\", \"formDataDefinitionVersion\": \"1\"},{\"formName\": \"Woman_TT_Followup_Form\", \"formDirName\": \"woman_followup\", \"formDataDefinitionVersion\": \"1\"},{\"formName\": \"Child_Vaccination_Followup\", \"formDirName\": \"child_followup\", \"formDataDefinitionVersion\": \"1\"},{\"formName\": \"Child_Vaccination_Enrollment\", \"formDirName\": \"child_enrollment\", \"formDataDefinitionVersion\": \"1\"},{\"formName\": \"Vaccine_Stock_Position\", \"formDirName\": \"vaccine_stock_position\", \"formDataDefinitionVersion\": \"1\"},{\"formName\": \"Woman_TT_Enrollment_Form\", \"formDirName\": \"woman_enrollment\", \"formDataDefinitionVersion\": \"1\"},{\"formName\": \"Offsite_Woman_Followup_Form\", \"formDirName\": \"offsite_woman_followup\", \"formDataDefinitionVersion\": \"1\"}]}";
		return mapper.readTree(currentFormVersionList);
	}
}
