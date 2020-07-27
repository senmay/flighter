package basic_travel_agency.service;

import basic_travel_agency.domain.FlightSearchRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FlightSearchRequestServiceTest {
    @Autowired
    private FlightSearchRequestService service;

    @Test
    public void testAddSearchRequest() {
        //Given
        service.deleteAll();

        FlightSearchRequest testRequest = FlightSearchRequest.builder()
                .departureAirport( "CHP" )
                .departureCity( "Warsaw" )
                .destinationAirport( "MED" )
                .destinationCity("Kingston")
                .departureDay(LocalDate.now())
                .build();

        service.addSearchRequest(testRequest);

        //When
        FlightSearchRequest result = service.getAllRequests().get(0);

        //Then
        assertEquals(testRequest, result);
    }

    @Test
    public void testGetAllRequests() {
        service.deleteAll();

        FlightSearchRequest testRequest = FlightSearchRequest.builder()
                .departureAirport( "CHP" )
                .departureCity( "Warsaw" )
                .destinationAirport( "MED" )
                .destinationCity("Kingston")
                .departureDay(LocalDate.now())
                .build();
        FlightSearchRequest testRequestTwo = FlightSearchRequest.builder()
                .departureAirport( "MED" )
                .departureCity( "Mediolan" )
                .destinationAirport( "WAW" )
                .destinationCity("Warsaw")
                .departureDay(LocalDate.now().plusDays(10))
                .build();
        List<FlightSearchRequest> requests = Arrays.asList(testRequest, testRequestTwo);

        service.addSearchRequest(testRequest);
        service.addSearchRequest(testRequestTwo);

        //When
        List<FlightSearchRequest> result = service.getAllRequests();

        //Then
        assertEquals(requests, result);
    }
}