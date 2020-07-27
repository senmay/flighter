package basic_travel_agency.controler;

import basic_travel_agency.domain.NotificationPreference;
import basic_travel_agency.domain.User;
import basic_travel_agency.domain.dto.NotificationPreferenceCreationDto;
import basic_travel_agency.domain.dto.NotificationPreferenceDto;
import basic_travel_agency.domain.dto.UserDto;
import basic_travel_agency.mapper.NotificationPreferenceMapper;
import basic_travel_agency.mapper.UserMapper;
import basic_travel_agency.repository.StartupArgsRepository;
import basic_travel_agency.service.NotificationPreferenceService;
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

import java.math.BigDecimal;
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
@WebMvcTest(NotificationPreferenceController.class)
public class NotificationPreferenceControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StartupArgsRepository repository;
    @MockBean
    private NotificationPreferenceService preferenceService;
    @MockBean
    private NotificationPreferenceMapper preferenceMapper;
    @MockBean
    private UserService userService;
    @MockBean
    private UserMapper userMapper;

    private Gson gson = new Gson();

    private User testUser = User.builder()
            .id(1L)
            .name("Dominik")
            .surname("Raja")
            .email("test@test.com")
            .registered(LocalDate.parse("2020-05-07"))
            .securePassword("password")
            .notificationPreferences(new HashSet<>())
            .build();


    @Test
    public void testAddPreference() throws Exception {
        //Given
        NotificationPreference testPreference = NotificationPreference.builder()
                .id(3L)
                .departureCity("Warsaw")
                .destinationCity("Paris")
                .minTemperature(10)
                .maxPrice(BigDecimal.valueOf(500.00))
                .user(testUser)
                .build();
        NotificationPreferenceCreationDto creationDto = NotificationPreferenceCreationDto.builder()
                .id(2L)
                .userId(1L)
                .departureCity("Warsaw")
                .destinationCity("Paris")
                .minTemperature(10)
                .maxPrice(BigDecimal.valueOf(500.00))
                .build();

        when(userService.getUserById( creationDto.getUserId() )).thenReturn(testUser);
        when(preferenceMapper.creationDtoToPreference( creationDto )).thenReturn(testPreference);
        when(preferenceService.addPreference(testPreference)).thenReturn(testPreference);
        when(preferenceService.getPreferenceById(3L)).thenReturn(testPreference);

        String jsonContent = gson.toJson(creationDto);

        // When & Then
        mockMvc.perform(post("/preferences").contentType(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isOk());

    }

    @Test
    public void testUpdatePreference() throws Exception {
        NotificationPreference testPreference = NotificationPreference.builder()
                .id(2L)
                .departureCity("Warsaw")
                .destinationCity("Paris")
                .minTemperature(10)
                .maxPrice(BigDecimal.valueOf(500.00))
                .user(testUser)
                .build();
        NotificationPreferenceDto resultDto = NotificationPreferenceDto.builder()
                .id(2L)
                .departureCity("Berlin")
                .destinationCity("Paris")
                .minTemperature(18)
                .maxPrice(BigDecimal.valueOf(500.00))
                .userDto(new UserDto())
                .build();

        NotificationPreferenceDto updateDto = NotificationPreferenceDto.builder()
                .id(2L)
                .departureCity("Berlin")
                .minTemperature(18)
                .build();

        when(preferenceService.getPreferenceById(2L)).thenReturn(testPreference);
        when(preferenceMapper.mapToPreferenceDto(testPreference)).thenReturn(resultDto);

        String jsonContent = gson.toJson(updateDto);

        // When & Then
        mockMvc.perform(put("/preferences").contentType(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.departureCity", is("Berlin")))
                .andExpect(jsonPath("$.minTemperature", is(18)));
        verify(preferenceService, times(1)).getPreferenceById(2L);

    }

    @Test
    public void testDeletePreference() throws Exception {
        //When & Then
        mockMvc.perform(delete("/preferences/1"))
                .andExpect(status().isOk());
        verify(preferenceService, times(1)).deletePreferenceById(1L);
    }

    @Test
    public void testGetPreferences() throws Exception {
        NotificationPreference testPreferenceOne = NotificationPreference.builder()
                .id(2L)
                .departureCity("Warsaw")
                .destinationCity("Paris")
                .minTemperature(10)
                .maxPrice(BigDecimal.valueOf(500.00))
                .user(testUser)
                .build();
        NotificationPreferenceDto testDtoOne = NotificationPreferenceDto.builder()
                .id(2L)
                .departureCity("Warsaw")
                .destinationCity("Paris")
                .minTemperature(10)
                .maxPrice(BigDecimal.valueOf(500.00))
                .userDto(new UserDto())
                .build();

        NotificationPreference testPreferenceTwo = NotificationPreference.builder()
                .id(3L)
                .departureCity("Berlin")
                .destinationCity("Berno")
                .minTemperature(10)
                .maxPrice(BigDecimal.valueOf(500.00))
                .user(testUser)
                .build();
        NotificationPreferenceDto testDtoTwo = NotificationPreferenceDto.builder()
                .id(3L)
                .departureCity("Berlin")
                .destinationCity("Berno")
                .minTemperature(18)
                .maxPrice(BigDecimal.valueOf(500.00))
                .userDto(new UserDto())
                .build();

        List<NotificationPreference> preferences = Arrays.asList(testPreferenceOne, testPreferenceTwo);
        List<NotificationPreferenceDto> dtos = Arrays.asList(testDtoOne, testDtoTwo);

        when(preferenceService.getAllPreferences()).thenReturn(preferences);
        when(preferenceMapper.mapToPrefrenceDtoList(preferences)).thenReturn(dtos);

        // When & Then
        mockMvc.perform(get("/preferences").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.preferences", hasSize(2)))
                .andExpect(jsonPath("$.preferences[0].departureCity", is("Warsaw")))
                .andExpect(jsonPath("$.preferences[0].destinationCity", is("Paris")))
                .andExpect(jsonPath("$.preferences[1].departureCity", is("Berlin")))
                .andExpect(jsonPath("$.preferences[1].destinationCity", is("Berno")));

        verify(preferenceService, times(1)).getAllPreferences();

    }

    @Test
    public void testGetPreferencesByDestinationCity() throws Exception {
        NotificationPreference testPreferenceOne = NotificationPreference.builder()
                .id(2L)
                .departureCity("Warsaw")
                .destinationCity("Paris")
                .minTemperature(10)
                .maxPrice(BigDecimal.valueOf(500.00))
                .user(testUser)
                .build();
        NotificationPreferenceDto testDtoOne = NotificationPreferenceDto.builder()
                .id(2L)
                .departureCity("Warsaw")
                .destinationCity("Paris")
                .minTemperature(10)
                .maxPrice(BigDecimal.valueOf(500.00))
                .userDto(new UserDto())
                .build();

        List<NotificationPreference> preferences = Arrays.asList(testPreferenceOne);
        List<NotificationPreferenceDto> dtos = Arrays.asList(testDtoOne);

        when(preferenceService.getAllPreferencesByCity("Paris")).thenReturn(preferences);
        when(preferenceMapper.mapToPrefrenceDtoList(preferences)).thenReturn(dtos);

        // When & Then
        mockMvc.perform(get("/preferences/?city=Paris").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.preferences", hasSize(1)))
                .andExpect(jsonPath("$.preferences[0].departureCity", is("Warsaw")))
                .andExpect(jsonPath("$.preferences[0].destinationCity", is("Paris")))
                .andExpect(jsonPath("$.preferences[0].minTemperature", is(10)))
                .andExpect(jsonPath("$.preferences[0].maxPrice", is(500.00)));

        verify(preferenceService, times(1)).getAllPreferencesByCity("Paris");
    }

    @Test
    public void testGetPreference() throws Exception {
        //Given
        NotificationPreference testPreference = NotificationPreference.builder()
                .id(2L)
                .departureCity("Warsaw")
                .destinationCity("Paris")
                .minTemperature(10)
                .maxPrice(BigDecimal.valueOf(500.00))
                .user(testUser)
                .build();
        NotificationPreferenceDto testDto = NotificationPreferenceDto.builder()
                .id(2L)
                .departureCity("Warsaw")
                .destinationCity("Paris")
                .minTemperature(10)
                .maxPrice(BigDecimal.valueOf(500.00))
                .userDto(new UserDto())
                .build();

        when(preferenceService.getPreferenceById(2L)).thenReturn(testPreference);
        when(preferenceMapper.mapToPreferenceDto(testPreference)).thenReturn(testDto);

        // When & Then
        mockMvc.perform(get("/preferences/2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.departureCity", is("Warsaw")))
                .andExpect(jsonPath("$.destinationCity", is("Paris")))
                .andExpect(jsonPath("$.minTemperature", is(10)))
                .andExpect(jsonPath("$.maxPrice", is(500.00)));
        verify(preferenceService, times(1)).getPreferenceById(2L);
    }
}