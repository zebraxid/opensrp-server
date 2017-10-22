package org.opensrp.connector.DHIS2.mPowerMis1Dhis2Data;


import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpStatus;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.opensrp.common.util.HttpResponse;
import org.opensrp.connector.DHIS2.dxf2.DHIS2;
import org.springframework.http.HttpMethod;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static java.util.Arrays.asList;

//Z5WPr2zconV
public class DataValueSetTemplateGenerator {
    private static String DATA_SET_TEMPLATE_PATH = "/home/habib/Music/projets/dgfp/opensrp-server/opensrp-connector/src/main/java/org/opensrp/connector/DHIS2/mPowerMis1Dhis2Data/dataSetTemplate.json";

    private static String BASE_URL = "http://123.200.18.20:8080";
    private static String USER_NAME = "dgfp";
    private static String PASSWORD = "Dgfp@123";

    private static Map<String, String> dataElementsIdName = new HashMap<>();
    private static Map<String, String> categoryOptionMap = new HashMap<>();
    private static Map<String, List<Template>> dataSetTemplateMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        getAllDataValues();
        getAllCategoryOptionCombs();
        createAllDataSetTemplates();
        createAnnotationTemplate();
        System.out.println("in main");
    }

    //@DHIS2(dateElementId="qQRGs57YZ2z",categoryOptionId="DHJ5tZVSSsl", dataSetId = "Z5WPr2zconV")
    private static void createAnnotationTemplate() {
//        Template template1 = new Template("dE", "cat", "dn", "cn");
//        dataSetTemplateMap.put("ds", asList(template1));
        String stringBuilder = "";
        for(Map.Entry dataSetTemplate : dataSetTemplateMap.entrySet()) {
            List<Template> templates = (List) dataSetTemplate.getValue();
            for(Template template : templates) {
                stringBuilder = "";
                stringBuilder += template.dataElementName + " " + template.categoryName;
                stringBuilder += " : ";
                stringBuilder += "@DHIS2";
                stringBuilder += "(";
                stringBuilder += "dataElementId=\"" + template.dataElementId + "\"";
                stringBuilder += ",";
                stringBuilder += "categoryOptionId=\"" + template.categoryOptionId + "\"";
                stringBuilder += ",";
                stringBuilder += "dataSetId=\"" + dataSetTemplate.getKey() + "\"";
                stringBuilder += ")";
            }
            System.out.println(stringBuilder);
        }
    }

    //http://123.200.18.20:8080/api/categoryOptionCombos.json?page=1
    //http://123.200.18.20:8080/api/dataElements.json?page=1
    private static void getAllDataValues() throws IOException {
        int totalPages = 2;
        int currentPage = 1;
        String url = BASE_URL + "/api" + "/dataElements.json?page=" + currentPage;
        JsonNode jsonNode = getCall(url, USER_NAME, PASSWORD);
       // totalPages = jsonNode.get("pager").get("total").getIntValue();
        JsonNode dataElements = jsonNode.get("dataElements");
        getDataValues(dataElements);
        currentPage++;
        while (currentPage <= totalPages) {
            url = BASE_URL + "/api" + "/dataElements.json?page=" + currentPage;
            jsonNode = getCall(url, USER_NAME, PASSWORD);
            dataElements = jsonNode.get("dataElements");
            getDataValues(dataElements);
            currentPage++;
        }

    }

    private static void getAllCategoryOptionCombs() throws IOException {
        int totalPages;
        int currentPage = 1;
        String url = BASE_URL + "/api" + "/categoryOptionCombos.json?page=" + currentPage;
        JsonNode jsonNode = getCall(url, USER_NAME, PASSWORD);
        totalPages = jsonNode.get("pager").get("total").getIntValue();
        JsonNode categoryOptions = jsonNode.get("dataElements");
        getCategoryOptions(categoryOptions);
        currentPage++;
        while (currentPage <= totalPages) {
            url = BASE_URL + "/api" + "/dataElements.json?page=" + currentPage;
            jsonNode = getCall(url, USER_NAME, PASSWORD);
            categoryOptions = jsonNode.get("dataElements");
            getCategoryOptions(categoryOptions);
            currentPage++;
        }

    }

    private static void createAllDataSetTemplates() throws IOException {
        List<String > allDataSets = Files.readAllLines(Paths.get(DATA_SET_TEMPLATE_PATH), Charset.forName("utf-8"));
        for (String dataset : allDataSets) {
            String  url = BASE_URL + "/api" + "/dataSets/" + dataset + "/dataValueSet.json";
            JsonNode jsonNode = getCall(url, USER_NAME, PASSWORD);
            List<Template> templates = createTemplate(jsonNode);
            dataSetTemplateMap.put(dataset, templates);
        }
    }


    private static void getDataValues(JsonNode dataElementJson) throws IOException {
        for (int i = 0; i < dataElementJson.size(); i++) {
            dataElementsIdName.put(dataElementJson.get(i).get("id").asText(), dataElementJson.get(i).get("displayName").asText());
        }
        return;
    }

    private static void getCategoryOptions(JsonNode categoryOptionJson) {
        for (int i = 0; i < categoryOptionJson.size(); i++) {
            categoryOptionMap.put(categoryOptionJson.get(i).get("id").asText(), categoryOptionJson.get(i).get("displayName").asText());
        }
    }

    private static List<Template> createTemplate(JsonNode jsonObject) {
        List<Template> templates = new ArrayList<>();
        JsonNode dataElementJson = jsonObject.get("dataValues");
        for (int i = 0; i < dataElementJson.size(); i++) {
            JsonNode templateJson = dataElementJson.get(i);
            String dataElementId = templateJson.get("dataElement").asText();
            String categoryOptionId = templateJson.get("categoryOptionCombo").asText();
            String dateElementName = dataElementsIdName.get(dataElementId);
            String categoryOptionName = categoryOptionMap.get(categoryOptionId);
            Template template = new Template(dataElementId, categoryOptionId, dateElementName, categoryOptionName);
            templates.add(template);
        }
        return templates;
    }

    private static JsonNode getCall(String url, String username, String password) throws IOException {
        String output = null;
        if (url.endsWith("/")) {
            url = url.substring(0, url.lastIndexOf("/"));
        }
        try {
            URL urlo = new URL(url);
            HttpURLConnection con = (HttpURLConnection) urlo.openConnection();
            con.setRequestProperty("Content-Type", "application/json");
            String charset = "UTF-8";
            con.setRequestProperty("Accept-Charset", charset);
            String encoded = new String(Base64.encodeBase64((username + ":" + password).getBytes()));
            con.setRequestProperty("Authorization", "Basic " + encoded);
            con.setRequestMethod(HttpMethod.GET.name());
            con.setDoOutput(true);
            int statusCode = con.getResponseCode();
            if (statusCode != HttpURLConnection.HTTP_OK) {
                // throw some exception
            }
            BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
            StringBuilder sb = new StringBuilder();

            while ((output = br.readLine()) != null) {
                sb.append(output);
            }
            HttpResponse response = new HttpResponse(con.getResponseCode() == HttpStatus.SC_OK, sb.toString());
            return new ObjectMapper().readTree(response.body());
        } catch (FileNotFoundException e) {
            return new ObjectMapper().readTree("");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static class Template {
        String dataElementId;
        String categoryOptionId;
        String dataElementName;
        String categoryName;

        public Template() {
        }

        public Template(String dataElementId, String categoryOptionId, String dataElementName, String categoryName) {
            this.dataElementId = dataElementId;
            this.categoryOptionId = categoryOptionId;
            this.dataElementName = dataElementName;
            this.categoryName = categoryName;
        }

        public String getDataElementId() {
            return dataElementId;
        }

        public void setDataElementId(String dataElementId) {
            this.dataElementId = dataElementId;
        }

        public String getCategoryOptionId() {
            return categoryOptionId;
        }

        public void setCategoryOptionId(String categoryOptionId) {
            this.categoryOptionId = categoryOptionId;
        }

        public String getDataElementName() {
            return dataElementName;
        }

        public void setDataElementName(String dataElementName) {
            this.dataElementName = dataElementName;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }
    }
}
