package basic_travel_agency.controler;

import basic_travel_agency.domain.User;
import basic_travel_agency.domain.dto.UserDto;
import basic_travel_agency.domain.dto.UserRegistrationDto;
import basic_travel_agency.mapper.UserMapper;
import basic_travel_agency.repository.StartupArgsRepository;
import basic_travel_agency.service.UserService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StartupArgsRepository repository;
    @MockBean
    private UserService userService;
    @MockBean
    private UserMapper userMapper;

    private Gson gson = new Gson();

    @Test
    public void registerUser() throws Exception {
        User testUser = User.builder()
                .id(1L)
                .name("Brad")
                .surname("Bardo")
                .email("test@test.com")
                .registered(LocalDate.parse("2020-07-27"))
                .securePassword("password")
                .notificationPreferences(new HashSet<>())
                .build();
        UserRegistrationDto dto = UserRegistrationDto.builder()
                .name("Brad")
                .surname("Bardo")
                .email("test@test.com")
                .securePassword("password")
                .build();
        when(userService.addUser(testUser)).thenReturn(testUser);
        when(userMapper.mapRegistrationDtoToUser(dto)).thenReturn(testUser);
        when(userService.getUserById(1L)).thenReturn(testUser);

        String jsonContent = gson.toJson(dto);

        // When & Then
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    public void getUser() throws Exception {
        //Given
        User testUser = User.builder()
                .id(1L)
                .name("Brad")
                .surname("Bardo")
                .email("test@test.com")
                .registered(LocalDate.parse("2020-07-27"))
                .securePassword("password")
                .notificationPreferences(new HashSet<>())
                .build();
        UserDto testDto = UserDto.builder()
                .id(1L)
                .name("Brad")
                .surname("Bardo")
                .email("test@test.com")
                .registered("2020-07-27")
                .securePassword("password")
                .notificationIds(new HashSet<>())
                .build();
        when(userService.getUserById(1L)).thenReturn(testUser);
        when(userMapper.mapToDto(testUser)).thenReturn(testDto);

        // When & Then
        mockMvc.perform(get("/users/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Brad")))
                .andExpect(jsonPath("$.surname", is("Bardo")))
                .andExpect(jsonPath("$.email", is("test@test.com")))
                .andExpect(jsonPath("$.securePassword", is("password")));
        verify(userService, times(1)).getUserById(1L);

    }

    @Test
    public void testGetUsers() throws Exception {
        //Given
        User testUser = User.builder()
                .name("Brad")
                .surname("Bardo")
                .email("test@test.com")
                .registered(LocalDate.now())
                .securePassword("password")
                .notificationPreferences(new HashSet<>())
                .build();
        User testUserTwo = User.builder()
                .name("Brad")
                .surname("Bardo 2")
                .email("Bardo@Bardo32.com")
                .registered(LocalDate.now())
                .securePassword("password")
                .notificationPreferences(new HashSet<>())
                .build();
        List<User> users = Arrays.asList(testUser, testUserTwo);

        UserDto testDto = UserDto.builder()
                .name("Brad")
                .surname("Bardo")
                .email("test@test.com")
                .registered(LocalDate.now().toString())
                .securePassword("password")
                .notificationIds(new HashSet<>())
                .build();
        UserDto testDtoTwo = UserDto.builder()
                .name("Brad")
                .surname("Bardo 2")
                .email("Bardo@Bardo32.com")
                .registered(LocalDate.now().toString())
                .securePassword("password")
                .notificationIds(new HashSet<>())
                .build();
        List<UserDto> userDtos = Arrays.asList(testDto, testDtoTwo);

        when(userService.getAllUsers()).thenReturn(users);
        when(userMapper.mapToUserDtoList(users)).thenReturn(userDtos);

        // When & Then
        mockMvc.perform(get("/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.users", hasSize(2)))
                .andExpect(jsonPath("$.users[0].surname", is("Bardo")))
                .andExpect(jsonPath("$.users[0].email", is("test@test.com")))
                .andExpect(jsonPath("$.users[1].surname", is("Bardo 2")))
                .andExpect(jsonPath("$.users[1].email", is("Bardo@Bardo32.com")));

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    public void updateUser() throws Exception {
        User testUser = User.builder()
                .id(1L)
                .name("Brad")
                .surname("Bardo")
                .email("test@test.com")
                .registered(LocalDate.parse("2020-07-27"))
                .securePassword("password")
                .notificationPreferences(new HashSet<>())
                .build();
        UserDto dto = UserDto.builder()
                .id(1L)
                .name("Brad EDITED")
                .surname("Bardo 2")
                .email("test@test.com")
                .securePassword("password")
                .registered("2020-07-27")
                .build();
        when(userService.getUserById(1L)).thenReturn(testUser);
        when(userMapper.mapToUser(dto)).thenReturn(testUser);

        String jsonContent = gson.toJson(dto);

        // When & Then
        mockMvc.perform(put("/users").contentType(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isOk());
        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    public void deleteUser() throws Exception {
        //When & Then
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk());
        verify(userService, times(1)).deleteUserById(1L);
    }
}