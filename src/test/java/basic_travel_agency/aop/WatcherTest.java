package basic_travel_agency.aop;

import basic_travel_agency.domain.ExecutionTimeRecord;
import basic_travel_agency.scheduler.NotificationScheduler;
import basic_travel_agency.service.ExecutionTimeRecordService;
import basic_travel_agency.service.ServiceUsageRecordService;
import basic_travel_agency.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class WatcherTest {
    @Autowired
    private NotificationScheduler scheduler;
    @Autowired
    ExecutionTimeRecordService executionTimeService;
    @Autowired
    ServiceUsageRecordService serviceUsageService;
    @Autowired
    UserService userService;

    @Test
    public void testMeasureNotificationTime() {
        //Given
        executionTimeService.deleteAllRecords();

        //When
        scheduler.notifyAboutOffers();
        List<ExecutionTimeRecord> times = executionTimeService.getAllRecords();

        //Then
        assertTrue(times.size() >= 1);
    }

}