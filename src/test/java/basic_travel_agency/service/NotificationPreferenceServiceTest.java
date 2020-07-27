package basic_travel_agency.service;

import basic_travel_agency.domain.NotificationPreference;
import basic_travel_agency.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NotificationPreferenceServiceTest {
    @Autowired
    private NotificationPreferenceService notificationPreferenceService;
    @Autowired
    private UserService userService;

    @Before
    public void cleanUp() {
        notificationPreferenceService.deleteAllPreferences();
        userService.deleteAllUsers();
    }

    @Test
    public void testAddPreference() {
        //Given
        User testUser = User.builder()
                .name("Dominik")
                .surname("test")
                .email("test@test.com")
                .registered(LocalDate.now())
                .securePassword("password")
                .notificationPreferences(new HashSet<>())
                .build();
        userService.addUser(testUser);

        NotificationPreference testPreference = NotificationPreference.builder()
                .departureCity("Warsaw")
                .destinationCity("Paris")
                .minTemperature(10)
                .maxPrice(BigDecimal.valueOf(500.00))
                .user(testUser)
                .build();

        //When
        notificationPreferenceService.addPreference(testPreference);
        Long id = testPreference.getId();
        NotificationPreference result = notificationPreferenceService.getPreferenceById(id);

        //Then
        assertEquals(testPreference, result);
        assertTrue( testUser.getNotificationPreferences().contains(testPreference) );
    }

    @Test
    public void testGetAllPreferences() {
        //Given
        User testUser = User.builder()
                .name("Dominik")
                .surname("test")
                .email("test@test.com")
                .registered(LocalDate.now())
                .securePassword("password")
                .notificationPreferences(new HashSet<>())
                .build();
        User testUserTwo = User.builder()
                .name("Dominik")
                .surname("test")
                .email("test@test32.com")
                .registered(LocalDate.now())
                .securePassword("password")
                .notificationPreferences(new HashSet<>())
                .build();
        userService.addUser(testUser);
        userService.addUser(testUserTwo);

        NotificationPreference testPreference = NotificationPreference.builder()
                .departureCity("Warsaw")
                .destinationCity("Paris")
                .minTemperature(10)
                .maxPrice(BigDecimal.valueOf(500.00))
                .user(testUser)
                .build();
        NotificationPreference testPreferenceTwo = NotificationPreference.builder()
                .departureCity("Berlin")
                .destinationCity("Paris")
                .minTemperature(10)
                .maxPrice(BigDecimal.valueOf(500.00))
                .user(testUser)
                .build();
        NotificationPreference testPreferenceThree = NotificationPreference.builder()
                .departureCity("Berlin")
                .destinationCity("Paris")
                .minTemperature(10)
                .maxPrice(BigDecimal.valueOf(500.00))
                .user(testUserTwo)
                .build();

        //When
        notificationPreferenceService.addPreference(testPreference);
        notificationPreferenceService.addPreference(testPreferenceTwo);
        notificationPreferenceService.addPreference(testPreferenceThree);
        List<NotificationPreference> result = notificationPreferenceService.getAllPreferences();

        //Then
        assertEquals(3, result.size());
        assertTrue( result.contains(testPreference) );
        assertTrue( result.contains(testPreferenceTwo) );
        assertTrue( result.contains(testPreferenceThree) );
    }

    @Test
    public void testGetAllPreferencesByCity() {
        //Given
        User testUser = User.builder()
                .name("Dominik")
                .surname("test")
                .email("test@test.com")
                .registered(LocalDate.now())
                .securePassword("password")
                .notificationPreferences(new HashSet<>())
                .build();
        userService.addUser(testUser);

        NotificationPreference testPreference = NotificationPreference.builder()
                .departureCity("Warsaw")
                .destinationCity("Berlin")
                .minTemperature(10)
                .maxPrice(BigDecimal.valueOf(500.00))
                .user(testUser)
                .build();
        NotificationPreference testPreferenceTwo = NotificationPreference.builder()
                .departureCity("Berlin")
                .destinationCity("Paris")
                .minTemperature(10)
                .maxPrice(BigDecimal.valueOf(700.00))
                .user(testUser)
                .build();
        NotificationPreference testPreferenceThree = NotificationPreference.builder()
                .departureCity("Berlin")
                .destinationCity("Paris")
                .minTemperature(10)
                .maxPrice(BigDecimal.valueOf(500.00))
                .user(testUser)
                .build();

        //When
        notificationPreferenceService.addPreference(testPreference);
        notificationPreferenceService.addPreference(testPreferenceTwo);
        notificationPreferenceService.addPreference(testPreferenceThree);
        List<NotificationPreference> result = notificationPreferenceService.getAllPreferencesByCity("Paris");

        //Then
        assertEquals(2, result.size());
        assertFalse( result.contains(testPreference) );
    }

    @Test
    public void testGetAllPreferencesByUser() {
        //Given
        User testUser = User.builder()
                .name("Dominik")
                .surname("test")
                .email("test@test.com")
                .registered(LocalDate.now())
                .securePassword("password")
                .notificationPreferences(new HashSet<>())
                .build();
        User testUserTwo = User.builder()
                .name("Dominik")
                .surname("test")
                .email("test@test32.com")
                .registered(LocalDate.now())
                .securePassword("password")
                .notificationPreferences(new HashSet<>())
                .build();
        userService.addUser(testUser);
        userService.addUser(testUserTwo);

        NotificationPreference testPreference = NotificationPreference.builder()
                .departureCity("Warsaw")
                .destinationCity("Paris")
                .minTemperature(10)
                .maxPrice(BigDecimal.valueOf(300.00))
                .user(testUser)
                .build();
        NotificationPreference testPreferenceTwo = NotificationPreference.builder()
                .departureCity("Berlin")
                .destinationCity("Paris")
                .minTemperature(10)
                .maxPrice(BigDecimal.valueOf(500.00))
                .user(testUser)
                .build();
        NotificationPreference testPreferenceThree = NotificationPreference.builder()
                .departureCity("Berno")
                .destinationCity("Paris")
                .minTemperature(10)
                .maxPrice(BigDecimal.valueOf(900.00))
                .user(testUserTwo)
                .build();

        //When
        notificationPreferenceService.addPreference(testPreference);
        notificationPreferenceService.addPreference(testPreferenceTwo);
        notificationPreferenceService.addPreference(testPreferenceThree);
        List<NotificationPreference> result = notificationPreferenceService.getAllPreferencesByUser(testUser);

        //Then
        assertEquals(2, result.size());
        assertFalse( result.contains(testPreferenceThree) );
    }

    @Test
    public void testDeleteAllPreferences() {
        //Given
        User testUser = User.builder()
                .name("Dominik")
                .surname("test")
                .email("test@test.com")
                .registered(LocalDate.now())
                .securePassword("password")
                .notificationPreferences(new HashSet<>())
                .build();
        userService.addUser(testUser);

        NotificationPreference testPreference = NotificationPreference.builder()
                .departureCity("Warsaw")
                .destinationCity("Paris")
                .minTemperature(10)
                .maxPrice(BigDecimal.valueOf(300.00))
                .user(testUser)
                .build();
        NotificationPreference testPreferenceTwo = NotificationPreference.builder()
                .departureCity("Berlin")
                .destinationCity("Paris")
                .minTemperature(10)
                .maxPrice(BigDecimal.valueOf(500.00))
                .user(testUser)
                .build();
        NotificationPreference testPreferenceThree = NotificationPreference.builder()
                .departureCity("Berno")
                .destinationCity("Paris")
                .minTemperature(10)
                .maxPrice(BigDecimal.valueOf(900.00))
                .user(testUser)
                .build();
        notificationPreferenceService.addPreference(testPreference);
        notificationPreferenceService.addPreference(testPreferenceTwo);
        notificationPreferenceService.addPreference(testPreferenceThree);

        //When
        notificationPreferenceService.deleteAllPreferences();
        List<NotificationPreference> result = notificationPreferenceService.getAllPreferences();

        //Then
        assertEquals(0, result.size() );
    }
}