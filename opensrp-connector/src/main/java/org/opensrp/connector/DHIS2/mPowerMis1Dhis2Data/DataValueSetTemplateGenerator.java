package org.opensrp.connector.DHIS2.mPowerMis1Dhis2Data;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

//Z5WPr2zconV
public class DataValueSetTemplateGenerator {
    private static String DATA_ELEMENTS_PATH = "/home/habib/Music/projets/dgfp/opensrp-server/opensrp-connector/src/main/java/org/opensrp/connector/DHIS2/mPowerMis1Dhis2Data/dataElements.json";
    private static String CATEGORY_OPTION_PATH = "/home/habib/Music/projets/dgfp/opensrp-server/opensrp-connector/src/main/java/org/opensrp/connector/DHIS2/mPowerMis1Dhis2Data/categoryOptionCombs.json";
    private static String DATA_SET_TEMPLATE_PATH = "/home/habib/Music/projets/dgfp/opensrp-server/opensrp-connector/src/main/java/org/opensrp/connector/DHIS2/mPowerMis1Dhis2Data/dataSetTemplate.json";


    public static void main(String[] args) throws IOException {
        System.out.println("in Main.");
        Map<String, String>  dataElementsIdName = getDataValues();
        Map<String ,String> categoryOptionMap = getCategoryOptions();
        List<Template> templates = createTemplate(dataElementsIdName, categoryOptionMap);
        System.out.println(new ObjectMapper().writeValueAsString(templates));

    }

    private static Map<String, String> getDataValues() {
        Map<String, String> dataElementsIdName = new HashMap<>();
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(DATA_ELEMENTS_PATH));
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonObject = objectMapper.readTree(jsonData);
            JsonNode dataElementJson = jsonObject.get("dataElements");
            for (int i = 0; i < dataElementJson.size(); i++) {
                dataElementsIdName.put(dataElementJson.get(i).get("id").asText(), dataElementJson.get(i).get("displayName").asText());
            }
            return dataElementsIdName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyMap();
    }

    private static Map<String, String> getCategoryOptions() {
        Map<String, String> categoryOptionIdName = new HashMap<>();
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(CATEGORY_OPTION_PATH));
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonObject = objectMapper.readTree(jsonData);
            JsonNode dataElementJson = jsonObject.get("categoryOptionCombos");
            for (int i = 0; i < dataElementJson.size(); i++) {
                categoryOptionIdName.put(dataElementJson.get(i).get("id").asText(), dataElementJson.get(i).get("displayName").asText());
            }
            return categoryOptionIdName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyMap();
    }

    private static List<Template> createTemplate(Map<String, String> dataElementMap, Map<String, String> categoryMap) {
        List<Template> templates = new ArrayList<>();
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(DATA_SET_TEMPLATE_PATH));
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonObject = objectMapper.readTree(jsonData);
            JsonNode dataElementJson = jsonObject.get("dataValues");
            for (int i = 0; i < dataElementJson.size(); i++) {
                JsonNode templateJson = dataElementJson.get(i);
                String dataElementId = templateJson.get("dataElement").asText();
                String categoryOptionId = templateJson.get("categoryOptionCombo").asText();
                String dateElementName = dataElementMap.get(dataElementId);
                String categoryOptionName = categoryMap.get(categoryOptionId);
                Template template = new Template(dataElementId, categoryOptionId, dateElementName, categoryOptionName);
                templates.add(template);
            }
            return templates;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;
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
