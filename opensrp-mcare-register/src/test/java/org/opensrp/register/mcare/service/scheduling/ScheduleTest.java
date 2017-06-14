package org.opensrp.register.mcare.service.scheduling;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.motechproject.scheduletracking.api.domain.Enrollment;
import org.motechproject.scheduletracking.api.domain.Schedule;
import org.motechproject.scheduletracking.api.repository.AllEnrollments;
import org.motechproject.scheduletracking.api.repository.AllSchedules;
import org.motechproject.scheduletracking.api.repository.TrackedSchedulesJsonReaderImpl;
import org.opensrp.register.mcare.encounter.sync.TestConfig;
import org.opensrp.scheduler.service.AllEnrollmentWrapper;
import org.powermock.api.mockito.PowerMockito;

import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


@RunWith(PowerMockRunner.class)
@PrepareForTest({AllSchedules.class})
@PowerMockIgnore({ "org.apache.log4j.*", "org.apache.commons.logging.*" })
public class ScheduleTest extends TestConfig {
	
	@Mock
	private AllEnrollmentWrapper allEnrollmentWrapper;
	@Mock
	private AllSchedules allSchedules;
	@Mock
	private AllEnrollments allEnrollments;
	@Mock
	private Enrollment enrollment;
	@Before
	public void setUp() throws Exception
	{	
		PowerMockito.mockStatic(AllSchedules.class);
		PowerMockito.mockStatic(AllEnrollments.class);
		PowerMockito.mockStatic(Enrollment.class);
		new TrackedSchedulesJsonReaderImpl("/schedules");
		// allSchedules = new AllSchedules(getStdCouchDbConnectorForSchedule(),new TrackedSchedulesJsonReaderImpl("/schedules"), new ScheduleFactory());
		// allEnrollmentWrapper.setAllSchedules(allSchedules);
		 allEnrollmentWrapper = new AllEnrollmentWrapper(getStdCouchDbConnectorForSchedule());
		// allEnrollments = new AllEnrollments(getStdCouchDbConnectorForSchedule());
	}

	@Test
	public void DD(){	
		System.err.println(enrollment);
		BDDMockito.given(allSchedules.getByName(Matchers.any(String.class))).willReturn(new Schedule("Woman_TT4"));   
		System.err.println(allEnrollments.findByExternalId("02c87089-be5e-45ec-b327-35da75f58a56"));
		
		allEnrollments.remove(enrollment);
		//allEnrollments.findByExternalId("0225f8ca-15f9-4c4d-866e-29b47b366c98");
		
	}
}
