package org.opensrp.service.formSubmission;


import com.google.gson.Gson;
import org.joda.time.DateTime;
import org.junit.Test;
import org.opensrp.BaseIntegrationTest;
import org.opensrp.domain.Address;
import org.opensrp.domain.Client;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.form.service.FormAttributeParser;
import org.opensrp.form.service.FormSubmissionMap;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

public class FormEntityConverterTest extends BaseIntegrationTest{

    @Autowired
    FormAttributeParser formAttributeParser;
    @Autowired
    private FormEntityConverter formEntityConverter;

    private Gson gson = new Gson();

    @Test
    public void test() throws Exception {
        FormSubmission fs = getFormSubmissionFor("new_household_registration", 1);
        System.out.println("************************form submission ************************");
        System.out.println(gson.toJson(fs));
        System.out.println("************************form submission ************************");
        System.out.println("************************ client ************************");
        Client client = formEntityConverter.getClientFromFormSubmission(fs);
        System.out.println(gson.toJson(client));
        System.out.println("************************ client ************************");
    }


    //TODO: test date approximation check
    @Test
    public void testCreateBaseClient() throws Exception {
        String addressString = "  {\n" +
                "      \"addressType\": \"usual_residence\",\n" +
                "      \"addressFields\": {\n" +
                "        \"landmark\": \"nothing\"\n" +
                "      },\n" +
                "      \"latitude\": \"34\",\n" +
                "      \"longitude\": \"34\",\n" +
                "      \"geopoint\": \"34 34 0 0\"\n" +
                "    }";
        Client expectedClient = new Client("a3f2abf4-2699-4761-819a-cea739224164");
        expectedClient.withFirstName("test")
                .withLastName(".")
                .withGender("male")
                .withBirthdate(new DateTime("1900-01-01").withTimeAtStartOfDay(),false)
                .withAddress(new Gson().fromJson(addressString, Address.class))
                .withIdentifier("JiVita HHID", "1234")
                .withIdentifier("GOB HHID", "1234");
        FormSubmission fsubmission = getFormSubmissionFor("new_household_registration", 1);
        FormSubmissionMap formSubmissionMap = formAttributeParser.createFormSubmissionMap(fsubmission);


        Client client = formEntityConverter.createBaseClient(formSubmissionMap);


        assertEquals(expectedClient.fullName(), client.fullName());
        assertEquals(expectedClient.getBirthdate(), client.getBirthdate());
        assertEquals(expectedClient.getBirthdateApprox(), client.getBirthdateApprox());
        assertEquals(expectedClient.getGender(), client.getGender());
        assertEquals(expectedClient.getIdentifier("JiVita HHID"), client.getIdentifier("JiVita HHID"));
        assertEquals(expectedClient.getIdentifier("GOB HHID"), client.getIdentifier("GOB HHID"));

    }

    @Test
    public void testCreateBaseClientWithOutFirstName() throws Exception {

        FormSubmission fsubmission = getFormSubmissionFor("new_household_registration", 9);
        FormSubmissionMap formSubmissionMap = formAttributeParser.createFormSubmissionMap(fsubmission);


        Client client = formEntityConverter.createBaseClient(formSubmissionMap);

        assertNull(client);

    }



}
