package basic_travel_agency.service;

import basic_travel_agency.domain.ExecutionTimeRecord;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExecutionTimeRecordServiceTest {
    @Autowired
    private ExecutionTimeRecordService service;

    @Before
    public void clean(){
        service.deleteAllRecords();
    }

    @Test
    public void testAddRecord() {
        //Given
        ExecutionTimeRecord record = ExecutionTimeRecord.builder()
                .method("notifyAboutOffers")
                .whenExecuted( LocalDateTime.of(2019, 05, 11, 15, 52, 23) )
                .executionTime( 200L )
                .build();

        //When
        service.addRecord( record );
        Long id = record.getId();

        ExecutionTimeRecord result = service.getAllRecords().get(0);

        //Then
        assertEquals(record, result);
    }

    @Test
    public void testGetAllRecords() {
        //Given
        ExecutionTimeRecord recordOne = ExecutionTimeRecord.builder()
                .method("notifyAboutOffers")
                .whenExecuted( LocalDateTime.of(2019, 05, 11, 15, 52, 23) )
                .executionTime( 200L )
                .build();
        ExecutionTimeRecord recordTwo = ExecutionTimeRecord.builder()
                .method("someOtherMethod")
                .whenExecuted( LocalDateTime.of(2019, 05, 12, 15, 52, 23) )
                .executionTime( 208L )
                .build();
        ExecutionTimeRecord recordThree = ExecutionTimeRecord.builder()
                .method("notifyAboutOffers")
                .whenExecuted( LocalDateTime.of(2019, 05, 13, 15, 52, 23) )
                .executionTime( 192L )
                .build();

        List<ExecutionTimeRecord> records = Arrays.asList(recordOne, recordTwo, recordThree);

        //When
        service.addRecord( recordOne );
        service.addRecord( recordTwo );
        service.addRecord( recordThree );

        List<ExecutionTimeRecord> result = service.getAllRecords();

        //Then
        assertEquals(records, result);
    }

    @Test
    public void testGetRecordsOfMethod() {
        //Given
        ExecutionTimeRecord recordOne = ExecutionTimeRecord.builder()
                .method("notifyAboutOffers")
                .whenExecuted( LocalDateTime.of(2019, 05, 11, 15, 52, 23) )
                .executionTime( 200L )
                .build();
        ExecutionTimeRecord recordTwo = ExecutionTimeRecord.builder()
                .method("someOtherMethod")
                .whenExecuted( LocalDateTime.of(2019, 05, 12, 15, 52, 23) )
                .executionTime( 208L )
                .build();
        ExecutionTimeRecord recordThree = ExecutionTimeRecord.builder()
                .method("notifyAboutOffers")
                .whenExecuted( LocalDateTime.of(2019, 05, 13, 15, 52, 23) )
                .executionTime( 192L )
                .build();

        List<ExecutionTimeRecord> records = Arrays.asList(recordOne, recordTwo, recordThree);

        //When
        service.addRecord( recordOne );
        service.addRecord( recordTwo );
        service.addRecord( recordThree );

        List<ExecutionTimeRecord> result = service.getRecordsOfMethod("notifyAboutOffers");

        //Then
        assertEquals(2, result.size());
        assertTrue(result.contains( recordOne ));
        assertTrue(result.contains( recordThree ));
    }

    @Test
    public void testDeleteAllRecords() {
        //Given
        ExecutionTimeRecord recordOne = ExecutionTimeRecord.builder()
                .method("notifyAboutOffers")
                .whenExecuted( LocalDateTime.of(2019, 05, 11, 15, 52, 23) )
                .executionTime( 200L )
                .build();
        ExecutionTimeRecord recordTwo = ExecutionTimeRecord.builder()
                .method("someOtherMethod")
                .whenExecuted( LocalDateTime.of(2019, 05, 12, 15, 52, 23) )
                .executionTime( 208L )
                .build();
        ExecutionTimeRecord recordThree = ExecutionTimeRecord.builder()
                .method("notifyAboutOffers")
                .whenExecuted( LocalDateTime.of(2019, 05, 13, 15, 52, 23) )
                .executionTime( 192L )
                .build();

        List<ExecutionTimeRecord> records = Arrays.asList(recordOne, recordTwo, recordThree);
        service.addRecord( recordOne );
        service.addRecord( recordTwo );
        service.addRecord( recordThree );

        //When
        service.deleteAllRecords();
        List<ExecutionTimeRecord> result = service.getAllRecords();

        //Then
        assertTrue(result.isEmpty());
    }
}