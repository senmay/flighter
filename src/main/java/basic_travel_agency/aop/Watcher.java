package basic_travel_agency.aop;

import basic_travel_agency.domain.ExecutionTimeRecord;
import basic_travel_agency.service.ExecutionTimeRecordService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@AllArgsConstructor
@Component
@Slf4j
public class Watcher {
    private final ExecutionTimeRecordService executionTimeRecordService;

    @Around("execution(* basic_travel_agency.scheduler.NotificationScheduler.notifyAboutOffers(..))")
    public Object measureNotificationTime(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result;
        try {
            long begin = System.currentTimeMillis();
            result = proceedingJoinPoint.proceed();
            long end = System.currentTimeMillis();
            long executionTime = end - begin;
            log.info("Time of doing operation: " + executionTime + "[ms]");
            ExecutionTimeRecord record = ExecutionTimeRecord.builder()
                    .method("notifyAboutOffers")
                    .whenExecuted(LocalDateTime.now())
                    .executionTime(executionTime)
                    .build();
            executionTimeRecordService.addRecord(record);
            log.info("Added record = " + record.toString());
        } catch (Throwable t) {
            log.error(t.getMessage());
            throw t;
        }
        return result;
    }
}
