package basic_travel_agency.controler;

import basic_travel_agency.domain.Payment;
import basic_travel_agency.domain.Reservation;
import basic_travel_agency.domain.dto.ReservationCreationDto;
import basic_travel_agency.domain.dto.ReservationDto;
import basic_travel_agency.domain.dto.ReservationListDto;
import basic_travel_agency.mapper.ReservationMapper;
import basic_travel_agency.service.PaymentService;
import basic_travel_agency.service.ReservationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping
@AllArgsConstructor
public class ReservationController {
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ReservationMapper reservationMapper;
    @Autowired
    private PaymentService paymentService;

    @PostMapping("reservations")
    public void addReservation(@RequestBody ReservationCreationDto dto) {
        reservationService.addReservation(reservationMapper.mapToReservationFromCreationDto(dto));
    }

    @GetMapping("reservations")
    public ReservationListDto getReservations() {
        return new ReservationListDto(reservationMapper.mapToDtoList(reservationService.getAllReservations()));
    }

    @GetMapping("reservations/{id}")
    public ReservationDto getReservation(@PathVariable("id") Long id) {
        return reservationMapper.mapToDto(reservationService.getReservationById(id));
    }

    @GetMapping("reservations/")
    public ReservationListDto getReservationBySurname(@RequestParam("surname") String surname) {
        return new ReservationListDto(reservationMapper.mapToDtoList(reservationService.getReservationsBySurname(surname)));
    }

    @DeleteMapping("reservations/{id}")
    public void deleteReservation(@PathVariable("id") Long id) {
        reservationService.deleteReservationById(id);
    }

    @GetMapping("reservations/count/{city}")
    public Long countReservationsInCity(@PathVariable("city") String city) {
        return reservationService.getNumberOfReservationsInCity(city);
    }

    @PutMapping("reservations")
    @Transactional
    public ReservationDto updateReservation(@RequestBody ReservationDto updatingDto) {
        Reservation reservation = reservationService.getReservationById(updatingDto.getId());
        Payment payment = paymentService.getPaymentById(updatingDto.getPaymentDto().getId());

        /**
         * Checks differences on Reservation object
         */
        if (! reservation.getThereFlightDepartureCity().equals(updatingDto.getFlightDepartureCity())) {
            reservation.setThereFlightDepartureCity(updatingDto.getFlightDepartureCity());
        }
        if (! reservation.getThereFlightDepartureAirportCode().equals(updatingDto.getFlightDepartureAirportCode())) {
            reservation.setThereFlightDepartureAirportCode(updatingDto.getFlightDepartureAirportCode());
        }
        if (! reservation.getThereFlightDestinationCity().equals(updatingDto.getFlightDestinationCity())) {
            reservation.setThereFlightDestinationCity(updatingDto.getFlightDestinationCity());
        }
        if (! reservation.getThereFlightDestinationAirportCode().equals(updatingDto.getFlightDestinationAirportCode())) {
            reservation.setThereFlightDestinationAirportCode(updatingDto.getFlightDestinationAirportCode());
        }
        if (! reservation.getThereFlightDate().equals(LocalDate.parse(updatingDto.getFlightDate()))) {
            reservation.setThereFlightDate(LocalDate.parse(updatingDto.getFlightDate()));
        }
        if (! reservation.getReturnFlightDepartureCity().equals(updatingDto.getReturnFlightDepartureCity())) {
            reservation.setReturnFlightDepartureCity(updatingDto.getReturnFlightDepartureCity());
        }
        if (! reservation.getReturnFlightDepartureAirportCode().equals(updatingDto.getReturnFlightDepartureAirportCode())) {
            reservation.setReturnFlightDepartureAirportCode(updatingDto.getReturnFlightDepartureAirportCode());
        }
        if (! reservation.getReturnFlightDestinationCity().equals(updatingDto.getReturnFlightDestinationCity())) {
            reservation.setReturnFlightDestinationCity(updatingDto.getReturnFlightDestinationCity());
        }
        if (! reservation.getReturnFlightDestinationAirportCode().equals(updatingDto.getReturnFlightDestinationAirportCode())) {
            reservation.setReturnFlightDestinationAirportCode(updatingDto.getReturnFlightDestinationAirportCode());
        }
        if (! reservation.getReturnFlightDate().equals(LocalDate.parse(updatingDto.getReturnFlightDate()))) {
            reservation.setReturnFlightDate(LocalDate.parse(updatingDto.getReturnFlightDate()));
        }
        if (! reservation.getName().equals(updatingDto.getName())) {
            reservation.setName(updatingDto.getName());
        }
        if (! reservation.getSurname().equals(updatingDto.getSurname())) {
            reservation.setSurname(updatingDto.getSurname());
        }
        if (! reservation.getEmail().equals(updatingDto.getEmail())) {
            reservation.setEmail(updatingDto.getEmail());
        }
        if (! reservation.getPrice().equals(updatingDto.getPrice())) {
            reservation.setPrice(updatingDto.getPrice());
        }

        /**
         * Checks differences on Payment object
         */
        if (! payment.getStatus().equals(updatingDto.getPaymentDto().getStatus())) {
            payment.setStatus(updatingDto.getPaymentDto().getStatus());
        }
        if (! payment.getValue().equals(updatingDto.getPaymentDto().getValue())) {
            payment.setValue(updatingDto.getPaymentDto().getValue());
        }
        if (! payment.hasPaymentDate() && updatingDto.getPaymentDto().hasValidDate()) {
            payment.setPaymentDate(LocalDate.parse(updatingDto.getPaymentDto().getPaymentDate()));
        }
        if (
                payment.hasPaymentDate() &&
                        updatingDto.getPaymentDto().hasValidDate() &&
                        ! (payment.getPaymentDate().toString().equals(updatingDto.getPaymentDto().getPaymentDate()))

        ) {
            payment.setPaymentDate(LocalDate.parse(updatingDto.getPaymentDto().getPaymentDate()));
        }

        return reservationMapper.mapToDto(reservation);

    }
}
