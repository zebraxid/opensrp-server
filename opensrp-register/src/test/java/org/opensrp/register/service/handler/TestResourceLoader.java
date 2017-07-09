package org.opensrp.register.service.handler;

import static org.mockito.MockitoAnnotations.initMocks;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.scheduletracking.api.domain.json.ScheduleRecord;
import org.motechproject.scheduletracking.api.repository.AllSchedules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-applicationContext-opensrp-register.xml")
public class TestResourceLoader {
    @Autowired
    private AllSchedules allSchedules;
	
    @Before
    public void setUp() throws Exception {
        initMocks(this);		
    }
	
    public String getFile() throws IOException{
        ResourceLoader loader = new DefaultResourceLoader();
        String scheduleConfigFilesPath = "./../assets/schedules/schedule-configs";
        File scheduleConfigsFolder = null;
        if (scheduleConfigsFolder == null && loader.getResource(scheduleConfigFilesPath).exists())
            scheduleConfigFilesPath = loader.getResource(scheduleConfigFilesPath).getURI().getPath();
        scheduleConfigsFolder = new File(scheduleConfigFilesPath);
        String scheduleConfigMapping = "";
        File[] scheduleFiles = scheduleConfigsFolder.listFiles();
        for (int i = 0; i < scheduleFiles.length; i++) {
            final File fileEntry = scheduleFiles[i];
            String scheduleConfig = FileUtils.readFileToString(new File(fileEntry.getAbsolutePath()), "UTF-8");
            scheduleConfigMapping += (i + 1 == scheduleFiles.length) ? scheduleConfig : scheduleConfig.concat(",");			
        }		
        return scheduleConfigMapping;
    }
	
    @Test
    public void getScheduleFile() throws IOException{
        ResourceLoader loader = new DefaultResourceLoader();
        String scheduleConfigFilesPath = "./../assets/schedules";
        File scheduleConfigsFolder = null;
        if (scheduleConfigsFolder == null && loader.getResource(scheduleConfigFilesPath).exists())
            scheduleConfigFilesPath = loader.getResource(scheduleConfigFilesPath).getURI().getPath();
        scheduleConfigsFolder = new File(scheduleConfigFilesPath);
        String scheduleConfigMapping = "";
        File[] scheduleFiles = scheduleConfigsFolder.listFiles();
        for (int i = 0; i < scheduleFiles.length; i++) {			
            try{ 
                final File fileEntry = scheduleFiles[i];			
                String scheduleConfig = FileUtils.readFileToString(new File(fileEntry.getAbsolutePath()), "UTF-8");                				 
                ObjectMapper mapper = new ObjectMapper();
                ScheduleRecord scheduleRecord = mapper.readValue(scheduleConfig, ScheduleRecord.class);
                allSchedules.getByName(scheduleRecord.name());
                if(allSchedules.getByName(scheduleRecord.name()) ==null){
                	allSchedules.add(scheduleRecord); 
                }
            }catch(Exception e){
				//e.printStackTrace();
            }
        }		
    }
		
    public List<String> jsonArrayToList(JSONArray jsonArray) throws JSONException {
        List<String> values = new ArrayList<String>();
        if (jsonArray == null) {
            return values;
        }		
        for (int i = 0; i < jsonArray.length(); i++) {
            values.add((String) jsonArray.get(i));
        }
        return values;
    }
}
