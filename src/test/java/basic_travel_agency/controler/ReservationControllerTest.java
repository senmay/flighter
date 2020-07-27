package basic_travel_agency.controler;

import basic_travel_agency.domain.Payment;
import basic_travel_agency.domain.Reservation;
import basic_travel_agency.domain.dto.PaymentDto;
import basic_travel_agency.domain.dto.ReservationCreationDto;
import basic_travel_agency.domain.dto.ReservationDto;
import basic_travel_agency.enumeration.PaymentStatus;
import basic_travel_agency.mapper.PaymentMapper;
import basic_travel_agency.mapper.ReservationMapper;
import basic_travel_agency.repository.StartupArgsRepository;
import basic_travel_agency.service.PaymentService;
import basic_travel_agency.service.ReservationService;
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
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StartupArgsRepository repository;
    @MockBean
    private PaymentService paymentService;
    @MockBean
    private PaymentMapper paymentMapper;
    @MockBean
    private ReservationService reservationService;
    @MockBean
    private ReservationMapper reservationMapper;

    private Gson gson = new Gson();

    @Test
    public void testAddReservation() throws Exception {
        //Given
        ReservationCreationDto creationDto = ReservationCreationDto.builder()
                .flightDepartureCity( "Warsaw" )
                .flightDepartureAirportCode( "WAW" )
                .flightDestinationCity( "Hannover" )
                .flightDestinationAirportCode( "HAJ" )
                .returnFlightDepartureCity( "Hannover" )
                .returnFlightDepartureAirportCode( "HAJ" )
                .returnFlightDestinationCity( "Warsaw" )
                .returnFlightDestinationAirportCode( "WAW" )
                .name( "John" )
                .surname( "Travolta" )
                .email( "Travolta@Travolta.com" )
                .price(BigDecimal.valueOf(555,66))
                .build();
        Reservation reservation = Reservation.builder()
                .id(1L)
                .thereFlightDepartureCity( "Warsaw" )
                .thereFlightDepartureAirportCode( "WAW" )
                .thereFlightDestinationCity( "Hannover" )
                .thereFlightDestinationAirportCode( "HAJ" )
                .thereFlightDate(LocalDate.of(2020,10,16))
                .returnFlightDepartureCity( "Hannover" )
                .returnFlightDepartureAirportCode( "HAJ" )
                .returnFlightDestinationCity( "Warsaw" )
                .returnFlightDestinationAirportCode( "WAW" )
                .returnFlightDate( LocalDate.of(2020,10,18) )
                .name( "John" )
                .surname( "Travolta" )
                .email( "Travolta@Travolta.com" )
                .price(BigDecimal.valueOf(555,66))
                .payment(new Payment())
                .build();

        when(reservationService.addReservation(reservation)).thenReturn(reservation);
        when(reservationMapper.mapToReservationFromCreationDto(creationDto)).thenReturn(reservation);

        String jsonContent = gson.toJson(creationDto);

        // When & Then
        mockMvc.perform(post("/reservations").contentType(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetReservations() throws Exception {
        //Given
        ReservationDto reservationDto = ReservationDto.builder()
                .id(1L)
                .flightDepartureCity( "Warsaw" )
                .flightDepartureAirportCode( "WAW" )
                .flightDestinationCity( "Hannover" )
                .flightDestinationAirportCode( "HAJ" )
                .returnFlightDepartureCity( "Hannover" )
                .returnFlightDepartureAirportCode( "HAJ" )
                .returnFlightDestinationCity( "Warsaw" )
                .returnFlightDestinationAirportCode( "WAW" )
                .name( "Dominik" )
                .surname( "Raj" )
                .email( "test@Test.com" )
                .price(BigDecimal.valueOf(555,66))
                .build();
        Reservation reservation = Reservation.builder()
                .id(1L)
                .thereFlightDepartureCity( "Warsaw" )
                .thereFlightDepartureAirportCode( "WAW" )
                .thereFlightDestinationCity( "Hannover" )
                .thereFlightDestinationAirportCode( "HAJ" )
                .thereFlightDate(LocalDate.of(2020,10,16))
                .returnFlightDepartureCity( "Hannover" )
                .returnFlightDepartureAirportCode( "HAJ" )
                .returnFlightDestinationCity( "Warsaw" )
                .returnFlightDestinationAirportCode( "WAW" )
                .returnFlightDate( LocalDate.of(2020,10,18) )
                .name( "Dominik" )
                .surname( "Raj" )
                .email( "test@Test.com" )
                .price(BigDecimal.valueOf(555,66))
                .payment(new Payment())
                .build();

        ReservationDto reservationDtoTwo = ReservationDto.builder()
                .id(2L)
                .flightDepartureCity( "Warsaw" )
                .flightDepartureAirportCode( "WAW" )
                .flightDestinationCity( "Hannover" )
                .flightDestinationAirportCode( "HAJ" )
                .returnFlightDepartureCity( "Hannover" )
                .returnFlightDepartureAirportCode( "HAJ" )
                .returnFlightDestinationCity( "Warsaw" )
                .returnFlightDestinationAirportCode( "WAW" )
                .name( "Marcin" )
                .surname( "drugi" )
                .email( "mail@mail" )
                .price(BigDecimal.valueOf(555,66))
                .build();
        Reservation reservationTwo = Reservation.builder()
                .id(2L)
                .thereFlightDepartureCity( "Warsaw" )
                .thereFlightDepartureAirportCode( "WAW" )
                .thereFlightDestinationCity( "Hannover" )
                .thereFlightDestinationAirportCode( "HAJ" )
                .thereFlightDate(LocalDate.of(2020,10,16))
                .returnFlightDepartureCity( "Hannover" )
                .returnFlightDepartureAirportCode( "HAJ" )
                .returnFlightDestinationCity( "Warsaw" )
                .returnFlightDestinationAirportCode( "WAW" )
                .returnFlightDate( LocalDate.of(2020,10,18) )
                .name( "Marcin" )
                .surname( "drugi" )
                .email( "mail@mail" )
                .price(BigDecimal.valueOf(555,66))
                .payment(new Payment())
                .build();

        List<Reservation> reservations = Arrays.asList(reservation, reservationTwo);
        List<ReservationDto> dtos = Arrays.asList(reservationDto, reservationDtoTwo);

        when(reservationService.getAllReservations()).thenReturn(reservations);
        when(reservationMapper.mapToDtoList(reservations)).thenReturn(dtos);

        //When & then
        mockMvc.perform(get("/reservations").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reservations", hasSize(2)))
                .andExpect(jsonPath("$.reservations[0].name", is("Dominik")))
                .andExpect(jsonPath("$.reservations[0].surname", is("Raj")))
                .andExpect(jsonPath("$.reservations[1].name", is("Marcin")))
                .andExpect(jsonPath("$.reservations[1].surname", is("drugi")));

        verify(reservationService, times(1)).getAllReservations();
    }

    @Test
    public void testGetReservation() throws Exception {
        //Given
        ReservationDto reservationDto = ReservationDto.builder()
                .flightDepartureCity( "Warsaw" )
                .flightDepartureAirportCode( "WAW" )
                .flightDestinationCity( "Hannover" )
                .flightDestinationAirportCode( "HAJ" )
                .returnFlightDepartureCity( "Hannover" )
                .returnFlightDepartureAirportCode( "HAJ" )
                .returnFlightDestinationCity( "Warsaw" )
                .returnFlightDestinationAirportCode( "WAW" )
                .name( "Dominik" )
                .surname( "Raj" )
                .email( "test@test.com" )
                .price(BigDecimal.valueOf(555,66))
                .build();
        Reservation reservation = Reservation.builder()
                .id(1L)
                .thereFlightDepartureCity( "Warsaw" )
                .thereFlightDepartureAirportCode( "WAW" )
                .thereFlightDestinationCity( "Hannover" )
                .thereFlightDestinationAirportCode( "HAJ" )
                .thereFlightDate(LocalDate.of(2020,10,16))
                .returnFlightDepartureCity( "Hannover" )
                .returnFlightDepartureAirportCode( "HAJ" )
                .returnFlightDestinationCity( "Warsaw" )
                .returnFlightDestinationAirportCode( "WAW" )
                .returnFlightDate( LocalDate.of(2020,10,18) )
                .name( "Marcin" )
                .surname( "drugi" )
                .email( "test@test.com" )
                .price(BigDecimal.valueOf(555,66))
                .payment(new Payment())
                .build();

        when(reservationService.getReservationById(1L)).thenReturn(reservation);
        when(reservationMapper.mapToDto(reservation)).thenReturn(reservationDto);

        //When & then
        mockMvc.perform(get("/reservations/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Dominik")))
                .andExpect(jsonPath("$.surname", is("Raj")));

        verify(reservationService, times(1)).getReservationById(1L);
    }

    @Test
    public void testDeleteReservation() throws Exception {
        //When & Then
        mockMvc.perform(delete("/reservations/1"))
                .andExpect(status().isOk());
        verify(reservationService, times(1)).deleteReservationById(1L);
    }

    @Test
    public void testCountReservationsInCity() throws Exception {
        //When & then
        when(reservationService.getNumberOfReservationsInCity("Hannover")).thenReturn(1L);

        mockMvc.perform(get("/reservations/count/Hannover"))
                .andExpect(jsonPath("$", is(1)))
                .andExpect(status().isOk());
        verify(reservationService, times(1)).getNumberOfReservationsInCity("Hannover");
    }
}