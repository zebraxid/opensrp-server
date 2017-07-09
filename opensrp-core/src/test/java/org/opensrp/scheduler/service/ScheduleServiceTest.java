package org.opensrp.scheduler.service;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.Period;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.motechproject.model.Time;
import org.motechproject.scheduletracking.api.domain.Enrollment;
import org.motechproject.scheduletracking.api.domain.EnrollmentStatus;
import org.motechproject.scheduletracking.api.domain.Milestone;
import org.motechproject.scheduletracking.api.domain.Schedule;
import org.motechproject.scheduletracking.api.repository.AllSchedules;
import org.motechproject.scheduletracking.api.service.EnrollmentsQuery;
import org.motechproject.scheduletracking.api.service.ScheduleTrackingService;
import org.opensrp.common.util.DateUtil;
import org.opensrp.scheduler.HealthSchedulerService;
import org.opensrp.util.ScheduleBuilder;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Map;

import static java.util.Arrays.asList;
import static org.joda.time.LocalDate.parse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LocalTime.class)
public class ScheduleServiceTest {

    @Mock
    private ScheduleTrackingService scheduleTrackingService;
    @Mock
    private AllSchedules allSchedules;
    @Mock
    private AllEnrollmentWrapper allEnrollments;

    private ScheduleService scheduleService;
    private Milestone firstMilestone;
    private Milestone secondMilestone;
    private Schedule schedule;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        PowerMockito.mockStatic(LocalTime.class);
        firstMilestone = new Milestone("firstMilestone", weeks(1), weeks(1), weeks(1), weeks(1));
        secondMilestone = new Milestone("secondMilestone", weeks(5), weeks(1), weeks(1), weeks(1));
        schedule = new Schedule("my_schedule");
        schedule.addMilestones(firstMilestone);
        schedule.addMilestones(secondMilestone);
        scheduleService = new ScheduleService(scheduleTrackingService, allSchedules, 14, allEnrollments);
    }

    @Test
    public void shouldEnrollToFirstMilestoneBasedOnScheduleDatesAndReferenceDate() {
        DateUtil.fakeIt(parse("2012-01-01"));
        when(allSchedules.getByName("my_schedule")).thenReturn(schedule);

        scheduleService.enroll("entity_1", "my_schedule", "2012-01-01", "formsubmission1");

        verify(scheduleTrackingService).enroll(ScheduleBuilder.enrollmentFor("entity_1", "my_schedule", firstMilestone.getName(), "2012-01-01"));
    }

    @Test
    public void shouldEnrollToSecondMilestoneBasedOnScheduleDatesAndReferenceDate() {
        DateUtil.fakeIt(parse("2012-02-07"));
        when(allSchedules.getByName("my_schedule")).thenReturn(schedule);

        scheduleService.enroll("entity_1", "my_schedule", "2012-01-01", "formsubmission2");

        verify(scheduleTrackingService).enroll(ScheduleBuilder.enrollmentFor("entity_1", "my_schedule", secondMilestone.getName(), "2012-01-01"));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfSchduleIsNotFound() {
        scheduleService.enroll("entity_1", "my_schedule", "2012-01-01", "formsubmission2");
    }

    @Test
    public void shouldSuccessfullyFulfilMilestone() {
        Enrollment enrollment = ScheduleBuilder.enrollment("entity_1", "my_schedule", "milestone", new DateTime(0l), new DateTime(1l), EnrollmentStatus.ACTIVE, "formSubmission3");
        when(allEnrollments.getActiveEnrollment("entity_1", "my_schedule")).thenReturn(enrollment);
        when(LocalTime.now()).thenReturn(new DateTime(3l).toLocalTime());

        scheduleService.fulfillMilestone("entity_1", "my_schedule", new DateTime(0l).toLocalDate(), "formSubmission3");

        Map<String, String> metaData = enrollment.getMetadata();
        metaData.put(HealthSchedulerService.MetadataField.fulfillmentEvent.name(), "formSubmission3");
        enrollment.setMetadata(metaData);
        InOrder inOrder = Mockito.inOrder(allEnrollments, scheduleTrackingService);
        inOrder.verify(allEnrollments).update(enrollment);
        inOrder.verify(scheduleTrackingService).fulfillCurrentMilestone(
                "entity_1", "my_schedule", new DateTime(1l).toLocalDate(),
                new Time(LocalTime.now()));
    }

    @Test
    public void shouldNotTryToFulfilMilestoneIfNoEnrollmentFound() {
        scheduleService.fulfillMilestone("entity_1", "my_schedule", new DateTime(0l).toLocalDate(), "formSubmission3");
        verify(allEnrollments, never()).update(any(Enrollment.class));
        verifyNoMoreInteractions(scheduleTrackingService);
    }

    @Test
    public void shouldUnEnroll() {
        Enrollment enrollment = ScheduleBuilder.enrollment("entity_1", "my_schedule", "milestone", new DateTime(0l), new DateTime(1l), EnrollmentStatus.ACTIVE, "formSubmission3");
        when(allEnrollments.getActiveEnrollment("entity_1", "my_schedule")).thenReturn(enrollment);


        scheduleService.unenroll("entity_1", "my_schedule", "formSubmission3");

        Map<String, String> metaData = enrollment.getMetadata();
        metaData.put(HealthSchedulerService.MetadataField.unenrollmentEvent.name(), "formSubmission3");
        enrollment.setMetadata(metaData);
        InOrder inOrder = Mockito.inOrder(allEnrollments, scheduleTrackingService);
        inOrder.verify(allEnrollments).update(enrollment);
        inOrder.verify(scheduleTrackingService).unenroll(
                "entity_1", asList("my_schedule"));
    }

    @Test
    public void shouldNotTryToUnEnrollIfNoEnrollmentFound() {
        scheduleService.fulfillMilestone("entity_1", "my_schedule", new DateTime(0l).toLocalDate(), "formSubmission3");
        verify(allEnrollments, never()).update(any(Enrollment.class));
        verifyNoMoreInteractions(scheduleTrackingService);
    }

    @Test
    public void shouldUnEnrollGivenScheduleList() {
        Enrollment enrollment_1 = ScheduleBuilder.enrollment("entity_1", "my_schedule",
                "milestone", new DateTime(0l), new DateTime(1l),
                EnrollmentStatus.ACTIVE, "formSubmission3");
        Enrollment enrollment_2 = ScheduleBuilder.enrollment("entity_1", "my_schedule",
                "milestone", new DateTime(0l), new DateTime(1l),
                EnrollmentStatus.ACTIVE, "formSubmission3");
        when(allEnrollments.getActiveEnrollment("entity_1", "schedule_1")).thenReturn(enrollment_1);
        when(allEnrollments.getActiveEnrollment("entity_1", "schedule_2")).thenReturn(enrollment_2);


        scheduleService.unenroll("entity_1", asList("schedule_1", "schedule_2"), "formSubmission3");

        Map<String, String> metaData = enrollment_1.getMetadata();
        metaData.put(HealthSchedulerService.MetadataField.unenrollmentEvent.name(), "formSubmission3");
        enrollment_1.setMetadata(metaData);
        enrollment_2.setMetadata(metaData);

        InOrder inOrder = Mockito.inOrder(allEnrollments, scheduleTrackingService);
        inOrder.verify(allEnrollments).update(enrollment_1);
        inOrder.verify(allEnrollments).update(enrollment_2);
        inOrder.verify(scheduleTrackingService).unenroll(
                "entity_1", asList("schedule_1", "schedule_2"));
    }

    @Test
    public void findOpenEnrollments() {
        scheduleService.findOpenEnrollmentNames("entity_id");
        verify(scheduleTrackingService).search(new EnrollmentsQuery().havingExternalId("entity_id").havingState(EnrollmentStatus.ACTIVE));
    }

   /* @Test
    public void*/

    private Period weeks(int numberOfWeeks) {
        return new Period(0, 0, numberOfWeeks, 0, 0, 0, 0, 0);
    }
}
