package basic_travel_agency.service;

import basic_travel_agency.api.skyscanner.domain.Flight;
import basic_travel_agency.domain.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest
public class FlightConnectionOffersCreatorTestWithWeather {
    @Autowired
    private WeekendFlightOffersCreator weekendFlightOffersCreator;
    @Autowired
    private UserService userService;
    @Autowired
    private NotificationPreferenceService notificationPreferenceService;

    @After
    public void cleanUp() {
        notificationPreferenceService.deleteAllPreferences();
        userService.deleteAllUsers();
    }

    @Before
    public void prepareDatabase() {
        Random rng = new Random();
        User testUser = User.builder()
                .name("John")
                .surname("Rambo")
                .email("rambo" + rng.nextInt(10) + "@rambo.com")
                .registered(LocalDate.now())
                .securePassword("123456789")
                .notificationPreferences(new HashSet<>())
                .build();
        userService.addUser(testUser);

        NotificationPreference testPreference = NotificationPreference.builder()
                .departureCity("Warsaw")
                .destinationCity("Hanover")
                .minTemperature(1)
                .maxPrice(BigDecimal.valueOf(5000.00))
                .user(testUser)
                .build();
        NotificationPreference testPreferenceTwo = NotificationPreference.builder()
                .departureCity("Warsaw")
                .destinationCity("Hanover")
                .minTemperature(1)
                .maxPrice(BigDecimal.valueOf(5000.00))
                .user(testUser)
                .build();
        NotificationPreference testPreferenceThree = NotificationPreference.builder()
                .departureCity("Wrocław")
                .destinationCity("Gdańsk")
                .minTemperature(1)
                .maxPrice(BigDecimal.valueOf(5000.00))
                .user(testUser)
                .build();
        notificationPreferenceService.addPreference(testPreference);
        notificationPreferenceService.addPreference(testPreferenceTwo);
        notificationPreferenceService.addPreference(testPreferenceThree);
    }

    @Test
    public void testGetAllSearchRequests() {
        //When
        Set<FlightSearchRequest> result = weekendFlightOffersCreator.getAllSearchRequests();
        //Then
        result.forEach(System.out::println);
        assertEquals(6, result.size());
    }

    @Test
    public void testGettingForecastForDestinationCities() {
        //When
        Map<String, Double> result = weekendFlightOffersCreator.getWeatherForDestinationCities();

        //Then
        assertTrue( result.containsKey("hanover") );
        assertTrue( result.containsKey("gdańsk") );
    }

    @Test
    public void testGettingFlightsForAllPreferences() {
        //When
        List<Flight> flights = weekendFlightOffersCreator.getConnectionsForAllPreferences();

        //Then
        assertTrue( flights.size() > 0 );
    }

    @Test
    public void testGettingConnectionOfferWithWeather() {
        //When
        List<FlightConnectionOfferWithWeather> offers = weekendFlightOffersCreator.getAllFlightOffersWithExpectedWeather();

        //Then
        assertTrue( offers.size() > 0 );
    }

    @Test
    public void testGetPreferencesAndOffers() {
        //Given and When
        Map<NotificationPreference, TripOffer> result = weekendFlightOffersCreator.getPreferencesAndOffers();

        //Then
        assertTrue( result.entrySet().size() > 0 );
    }

}