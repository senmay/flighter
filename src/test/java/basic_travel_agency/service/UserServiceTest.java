package basic_travel_agency.service;

import basic_travel_agency.domain.User;
import basic_travel_agency.exceptions.UserEmailAlreadyExistsException;
import basic_travel_agency.exceptions.UserNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private NotificationPreferenceService notificationPreferenceService;

    @Before
    public void cleanUp() {
        userService.deleteAllUsers();
    }

    @Test
    public void testAddUser() {
        //Given
        User testUser = User.builder()
                .name("Dominik")
                .surname("Barno")
                .email("test@test.com")
                .registered(LocalDate.now())
                .securePassword("password")
                .notificationPreferences(new HashSet<>())
                .build();

        //When
        userService.addUser(testUser);

        //Then
        assertEquals(1, userService.getAllUsers().size());
    }

    @Test
    public void testExistsByEmail() {
        //Given
        User testUser = User.builder()
                .name("Dominik")
                .surname("Barno")
                .email("test@test.com")
                .registered(LocalDate.now())
                .securePassword("password")
                .notificationPreferences(new HashSet<>())
                .build();

        //When
        userService.addUser(testUser);

        //Then
        assertTrue(userService.existsByEmail("test@test.com"));
    }

    @Test
    public void testGetAllUsers() {
        User testUserOne = User.builder()
                .name("Dominik")
                .surname("Barno")
                .email("test@test.com")
                .registered(LocalDate.now())
                .securePassword("password")
                .notificationPreferences(new HashSet<>())
                .build();
        User testUserTwo = User.builder()
                .name("Marcin")
                .surname("Draga")
                .email("Draga@empire.com")
                .registered(LocalDate.now())
                .securePassword("password")
                .notificationPreferences(new HashSet<>())
                .build();
        User testUserThree = User.builder()
                .name("Jack")
                .surname("Sparrrow")
                .email("no_mail@that_time.com")
                .registered(LocalDate.now())
                .securePassword("password")
                .notificationPreferences(new HashSet<>())
                .build();
        userService.addUser(testUserOne);
        userService.addUser(testUserTwo);
        userService.addUser(testUserThree);

        //When
        List<User> result = userService.getAllUsers();

        //Then
        assertEquals(3, result.size());
        assertTrue(result.contains(testUserOne));
        assertTrue(result.contains(testUserTwo));
        assertTrue(result.contains(testUserThree));
    }

    @Test
    public void testDeleteAllUsers() {
        User testUserOne = User.builder()
                .name("Dominik")
                .surname("Barno")
                .email("test@test.com")
                .registered(LocalDate.now())
                .securePassword("password")
                .notificationPreferences(new HashSet<>())
                .build();
        User testUserTwo = User.builder()
                .name("Marcin")
                .surname("Draga")
                .email("Draga@empire.com")
                .registered(LocalDate.now())
                .securePassword("password")
                .notificationPreferences(new HashSet<>())
                .build();
        User testUserThree = User.builder()
                .name("Jack")
                .surname("Sparrrow")
                .email("no_mail@that_time.com")
                .registered(LocalDate.now())
                .securePassword("password")
                .notificationPreferences(new HashSet<>())
                .build();
        userService.addUser(testUserOne);
        userService.addUser(testUserTwo);
        userService.addUser(testUserThree);

        //When
        userService.deleteAllUsers();
        List<User> result = userService.getAllUsers();


        //Then
        assertEquals(0, result.size());
    }

    @Test
    public void testDeleteUserById() {
        User testUserOne = User.builder()
                .name("Dominik")
                .surname("Barno")
                .email("test@test.com")
                .registered(LocalDate.now())
                .securePassword("password")
                .notificationPreferences(new HashSet<>())
                .build();
        User testUserTwo = User.builder()
                .name("Marcin")
                .surname("Draga")
                .email("Draga@empire.com")
                .registered(LocalDate.now())
                .securePassword("password")
                .notificationPreferences(new HashSet<>())
                .build();
        userService.addUser(testUserOne);
        userService.addUser(testUserTwo);

        //When
        Long id = testUserTwo.getId();
        userService.deleteUserById(id);
        List<User> result = userService.getAllUsers();

        //Then
        assertEquals(1, result.size());
        assertTrue( result.contains(testUserOne) );
        assertFalse( result.contains(testUserTwo) );
    }

    @Test
    public void testGetUserByEmail() {
        User testUserOne = User.builder()
                .name("Dominik")
                .surname("Barno")
                .email("test@test.com")
                .registered(LocalDate.now())
                .securePassword("password")
                .notificationPreferences(new HashSet<>())
                .build();
        userService.addUser(testUserOne);

        //When
        User result = userService.getUserByEmail("test@test.com");

        //Then
        assertEquals(testUserOne, result);
    }

    @Test(expected = UserNotFoundException.class)
    public void testGetUserByNotExistingEmail() {
        //When, When & Then
        User result = userService.getUserByEmail("tatatata@buziaczek.pl");
    }

    @Test(expected = UserNotFoundException.class)
    public void testGetUserByNotExistingId() {
        //When, When & Then
        User result = userService.getUserById(12L);
    }

    @Test(expected = UserEmailAlreadyExistsException.class)
    public void testAddDuplicatedUser() {
        //When, When & Then
        User testUserOne = User.builder()
                .name("Dominik")
                .surname("Barno")
                .email("test@test.com")
                .registered(LocalDate.now())
                .securePassword("password")
                .notificationPreferences(new HashSet<>())
                .build();
        userService.addUser(testUserOne);

        User testUserTwo = User.builder()
                .name("Marcin")
                .surname("Draga")
                .email("test@test.com")
                .registered(LocalDate.now())
                .securePassword("password")
                .notificationPreferences(new HashSet<>())
                .build();
        userService.addUser(testUserTwo);
    }
}