package org.opensrp.register.mcare.encounter.sync;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.opensrp.register.encounter.sync.mapping.domain.EncounterSyncMapping;
import org.opensrp.register.encounter.sync.mapping.repository.AllEncounterSyncMapping;

public class EncounterSyncMappingIntegrationTest extends TestConfig {	
	
	@Mock
	private AllEncounterSyncMapping allEncounterSyncMapping;
	@Before
	public void setUp() throws Exception
	{
		allEncounterSyncMapping = Mockito.mock(AllEncounterSyncMapping.class);
		allEncounterSyncMapping = new AllEncounterSyncMapping(1,getStdCouchDbConnectorForOpensrp());		
	}
	
	@Test
	public void shouldAddEncounterSyncMapping(){		
		EncounterSyncMapping encounterSyncMapping = new EncounterSyncMapping();
		encounterSyncMapping.setVaccineName("BCG");
		encounterSyncMapping.setEncounterId("05cbaa2b-d3a6-40f6-a604-328bf725ddbf");
		encounterSyncMapping.setInstanceId("6819d51c-590c-410d-96fa-2cc0da2130ba");
		encounterSyncMapping.setDose(0);
		encounterSyncMapping.setCreated(System.currentTimeMillis());
		encounterSyncMapping.setUpdated(System.currentTimeMillis());		
		allEncounterSyncMapping.add(encounterSyncMapping);
		//Mockito.doNothing().when(allEncounterSyncMapping).add(Matchers.any(EncounterSyncMapping.class));		
	}	
	
	@Ignore@Test
	public void shouldUpdateEncounterSyncMapping(){		
		EncounterSyncMapping encounterSyncMapping = new EncounterSyncMapping();
		encounterSyncMapping.setVaccineName("BCG");
		encounterSyncMapping.setId("5ab071d58be25a02463f69416e0da215");
		encounterSyncMapping.setEncounterId("05cbaa2b-d3a6-40f6-a604-328bf725ddbf");
		encounterSyncMapping.setInstanceId("6819d51c-590c-410d-96fa-2cc0da2130ba");
		encounterSyncMapping.setCreated(System.currentTimeMillis());
		encounterSyncMapping.setUpdated(System.currentTimeMillis());		
		allEncounterSyncMapping.update(encounterSyncMapping);
		Mockito.doNothing().when(allEncounterSyncMapping).update(Matchers.any(EncounterSyncMapping.class));		
	}
	
}
