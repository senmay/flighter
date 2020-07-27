package basic_travel_agency.api.weather;

import basic_travel_agency.api.weather.domain.WeatherForecast;
import basic_travel_agency.api.weather.facade.WeatherClientFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WeatherClientFacadeTest {
    @Autowired
    private WeatherClientFacade weatherClientFacade;

    @Test
    public void testGetWeatherForecast() {
        //Given
        WeatherForecast testForecast = weatherClientFacade.getWeatherForecast("Warsaw");

        //Then
        assertNotNull(testForecast);
        assertEquals("Warsaw", testForecast.getCity());
        assertEquals(16, testForecast.getDailyForecasts().size());
        System.out.println( testForecast.getDailyForecasts().get(3).toString() );
    }
}