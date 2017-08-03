package org.opensrp.etl.service.it;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opensrp.etl.entity.MarkerEntity;
import org.opensrp.etl.service.MarkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-applicationContext-opensrp-etl.xml")
public class MarkerServiceTest {
	@Autowired
	private MarkerService markerService;
	
	@Before
    public void setUp() throws Exception {
		
	}
	
	@Ignore@Test
	public void testSave(){
		MarkerEntity markerEntity = new MarkerEntity();
		markerEntity.setName("household");
		markerEntity.setType("load");
		markerEntity.setTimeStamp(System.currentTimeMillis());		
		markerService.save(markerEntity);
	}
	
	@Ignore@Test
	public void testUpdate(){
		MarkerEntity markerEntity = markerService.getMarkerByNameAndType("household","load");		
		if(markerEntity!=null){
			markerEntity.setTimeStamp(0);
			markerService.update(markerEntity);
			
		}
		System.err.println(markerService.getMarkerByNameAndType("household","load"));
	
	}

}
