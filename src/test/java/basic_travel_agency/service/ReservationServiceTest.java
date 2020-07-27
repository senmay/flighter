package basic_travel_agency.service;

import basic_travel_agency.domain.Payment;
import basic_travel_agency.domain.Reservation;
import basic_travel_agency.exceptions.ReservationNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReservationServiceTest {
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private PaymentService paymentService;

    @Before
    public void cleanUp(){
        reservationService.deleteAllReservations();
    }

    @Test
    public void testAddReservation() {
        //Given
        Reservation testReservation = Reservation.builder()
                .name("Brad")
                .surname("test")
                .email("test@test.com")
                .price(BigDecimal.valueOf(593.21))
                .thereFlightDepartureCity("Warsaw")
                .thereFlightDepartureAirportCode("WMI")
                .thereFlightDestinationCity("Hanover")
                .thereFlightDestinationAirportCode("HAJ")
                .thereFlightDate(LocalDate.of(2019,5,10))
                .returnFlightDepartureCity("Hanover")
                .returnFlightDepartureAirportCode("HAJ")
                .returnFlightDestinationCity("Warsaw")
                .returnFlightDestinationAirportCode("WMI")
                .returnFlightDate(LocalDate.of(2019,5,12))
                .build();

        reservationService.addReservation(testReservation);

        //When
        Reservation result = reservationService.getReservationById( testReservation.getId() );

        //Then
        assertNotNull( result.getPayment() );
        assertEquals(testReservation, result);
    }

    @Test(expected = ReservationNotFoundException.class)
    public void testGettingReservationByNotExistingId() {
        //Given, when & then
        Reservation result = reservationService.getReservationById(34L);
    }


    @Test
    public void testGetReservationsBySurname() {
        //Given
        Reservation testReservationOne = Reservation.builder()
                .name("Brad")
                .surname("test")
                .email("test@test.com")
                .price(BigDecimal.valueOf(593.21))
                .thereFlightDepartureCity("Warsaw")
                .thereFlightDepartureAirportCode("WMI")
                .thereFlightDestinationCity("hanover")
                .thereFlightDestinationAirportCode("HAJ")
                .thereFlightDate(LocalDate.of(2019,5,10))
                .returnFlightDepartureCity("Hanover")
                .returnFlightDepartureAirportCode("HAJ")
                .returnFlightDestinationCity("Warsaw")
                .returnFlightDestinationAirportCode("WMI")
                .returnFlightDate(LocalDate.of(2019,5,12))
                .build();
        Reservation testReservationTwo = Reservation.builder()
                .name("Brad")
                .surname("test")
                .email("test@test.com")
                .price(BigDecimal.valueOf(250.99))
                .thereFlightDepartureCity("Szczecin")
                .thereFlightDepartureAirportCode("SZC")
                .thereFlightDestinationCity("hanover")
                .thereFlightDestinationAirportCode("HAJ")
                .thereFlightDate(LocalDate.of(2020,5,10))
                .returnFlightDepartureCity("Hanover")
                .returnFlightDepartureAirportCode("HAJ")
                .returnFlightDestinationCity("Szczecin")
                .returnFlightDestinationAirportCode("SZC")
                .returnFlightDate(LocalDate.of(2020,5,12))
                .build();
        Reservation testReservationThree = Reservation.builder()
                .name("Brad")
                .surname("test2")
                .email("test2@test.com")
                .price(BigDecimal.valueOf(250.99))
                .thereFlightDepartureCity("Szczecin")
                .thereFlightDepartureAirportCode("SZC")
                .thereFlightDestinationCity("London")
                .thereFlightDestinationAirportCode("LHR")
                .thereFlightDate(LocalDate.of(2020,5,10))
                .returnFlightDepartureCity("Hanover")
                .returnFlightDepartureAirportCode("HAJ")
                .returnFlightDestinationCity("Szczecin")
                .returnFlightDestinationAirportCode("SZC")
                .returnFlightDate(LocalDate.of(2020,5,12))
                .build();

        reservationService.addReservation(testReservationOne);
        reservationService.addReservation(testReservationTwo);
        reservationService.addReservation(testReservationThree);

        //When
        List<Reservation> result = reservationService.getReservationsBySurname("test");

        //Then
        assertEquals(2, result.size());
        assertTrue(result.contains(testReservationOne));
        assertTrue(result.contains(testReservationTwo));
    }
    @Test
    public void testGetAllReservations() {
        //Given
        Reservation testReservationOne = Reservation.builder()
                .name("Brad")
                .surname("test")
                .email("test@test.com")
                .price(BigDecimal.valueOf(593.21))
                .thereFlightDepartureCity("Warsaw")
                .thereFlightDepartureAirportCode("WMI")
                .thereFlightDestinationCity("Hanover")
                .thereFlightDestinationAirportCode("HAJ")
                .thereFlightDate(LocalDate.of(2019,5,10))
                .returnFlightDepartureCity("Hanover")
                .returnFlightDepartureAirportCode("HAJ")
                .returnFlightDestinationCity("Warsaw")
                .returnFlightDestinationAirportCode("WMI")
                .returnFlightDate(LocalDate.of(2019,5,12))
                .build();
        Reservation testReservationTwo = Reservation.builder()
                .name("Brad")
                .surname("test")
                .email("test@test.com")
                .price(BigDecimal.valueOf(250.99))
                .thereFlightDepartureCity("Szczecin")
                .thereFlightDepartureAirportCode("SZC")
                .thereFlightDestinationCity("Hanover")
                .thereFlightDestinationAirportCode("HAJ")
                .thereFlightDate(LocalDate.of(2020,5,10))
                .returnFlightDepartureCity("Hanover")
                .returnFlightDepartureAirportCode("HAJ")
                .returnFlightDestinationCity("Szczecin")
                .returnFlightDestinationAirportCode("SZC")
                .returnFlightDate(LocalDate.of(2020,5,12))
                .build();
        Reservation testReservationThree = Reservation.builder()
                .name("Brad")
                .surname("test2")
                .email("test2@test.com")
                .price(BigDecimal.valueOf(15.55))
                .thereFlightDepartureCity("Szczecin")
                .thereFlightDepartureAirportCode("SZC")
                .thereFlightDestinationCity("London")
                .thereFlightDestinationAirportCode("LHR")
                .thereFlightDate(LocalDate.of(2020,5,10))
                .returnFlightDepartureCity("Hanover")
                .returnFlightDepartureAirportCode("HAJ")
                .returnFlightDestinationCity("Szczecin")
                .returnFlightDestinationAirportCode("SZC")
                .returnFlightDate(LocalDate.of(2020,5,12))
                .build();

        reservationService.addReservation(testReservationOne);
        reservationService.addReservation(testReservationTwo);
        reservationService.addReservation(testReservationThree);

        //When
        List<Reservation> result = reservationService.getAllReservations();

        //Then
        assertEquals(3, result.size());
        assertEquals(BigDecimal.valueOf(593.21), result.get(0).getPayment().getValue() );
        assertEquals(BigDecimal.valueOf(250.99), result.get(1).getPayment().getValue() );
        assertEquals(BigDecimal.valueOf(15.55), result.get(2).getPayment().getValue() );
    }

    @Test
    public void testDeleteAllReservations() {
        //Given
        Reservation testReservation = Reservation.builder()
                .name("Brad")
                .surname("test")
                .email("test@test.com")
                .price(BigDecimal.valueOf(593.21))
                .thereFlightDepartureCity("Warsaw")
                .thereFlightDepartureAirportCode("WMI")
                .thereFlightDestinationCity("Hanover")
                .thereFlightDestinationAirportCode("HAJ")
                .thereFlightDate(LocalDate.of(2019,5,10))
                .returnFlightDepartureCity("Hanover")
                .returnFlightDepartureAirportCode("HAJ")
                .returnFlightDestinationCity("Warsaw")
                .returnFlightDestinationAirportCode("WMI")
                .returnFlightDate(LocalDate.of(2019,5,12))
                .build();
        Reservation testReservationTwo = Reservation.builder()
                .name("Brad")
                .surname("test")
                .email("test@test.com")
                .price(BigDecimal.valueOf(250.99))
                .thereFlightDepartureCity("Szczecin")
                .thereFlightDepartureAirportCode("SZC")
                .thereFlightDestinationCity("Hanover")
                .thereFlightDestinationAirportCode("HAJ")
                .thereFlightDate(LocalDate.of(2020,5,10))
                .returnFlightDepartureCity("Hanover")
                .returnFlightDepartureAirportCode("HAJ")
                .returnFlightDestinationCity("Szczecin")
                .returnFlightDestinationAirportCode("SZC")
                .returnFlightDate(LocalDate.of(2020,5,12))
                .build();
        reservationService.addReservation(testReservation);
        reservationService.addReservation(testReservationTwo);

        //When
        reservationService.deleteAllReservations();
        List<Reservation> reservations = reservationService.getAllReservations();
        List<Payment> payments = paymentService.getAllPayments();

        //Then
        assertEquals(0, reservations.size() );
        assertEquals(0, payments.size());
    }
}