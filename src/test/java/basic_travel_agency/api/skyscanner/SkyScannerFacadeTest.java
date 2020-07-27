package basic_travel_agency.api.skyscanner;

import basic_travel_agency.api.skyscanner.domain.Airport;
import basic_travel_agency.api.skyscanner.facade.SkyScannerFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SkyScannerFacadeTest {
    @Autowired
    private SkyScannerFacade skyScannerFacade;

    @Test
    public void testGetAirportsInCity() {
        //Given
        List<Airport> testAirports = skyScannerFacade.getAirportsInCity("Moscow");

        //Then
        assertNotNull(testAirports);
        assertTrue(testAirports.size() > 0);
        testAirports.stream()
                .forEach(e -> assertNotEquals("NOT AIRPORT", e.getAirportCode() ) );
    }

}