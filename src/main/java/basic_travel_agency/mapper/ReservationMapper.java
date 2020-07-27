package basic_travel_agency.mapper;

import basic_travel_agency.domain.Reservation;
import basic_travel_agency.domain.dto.ReservationCreationDto;
import basic_travel_agency.domain.dto.ReservationDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ReservationMapper {
    private final PaymentMapper paymentMapper;

    public Reservation mapToReservationFromCreationDto(final ReservationCreationDto dto) {
        return Reservation.builder()
                .thereFlightDepartureCity(dto.getFlightDepartureCity())
                .thereFlightDepartureAirportCode(dto.getFlightDepartureAirportCode())
                .thereFlightDestinationCity(dto.getFlightDestinationCity())
                .thereFlightDestinationAirportCode(dto.getFlightDestinationAirportCode())
                .thereFlightDate(LocalDate.parse(dto.getFlightDate()))
                .returnFlightDepartureCity(dto.getReturnFlightDepartureCity())
                .returnFlightDepartureAirportCode(dto.getReturnFlightDepartureAirportCode())
                .returnFlightDestinationCity(dto.getReturnFlightDestinationCity())
                .returnFlightDestinationAirportCode(dto.getReturnFlightDestinationAirportCode())
                .returnFlightDate(LocalDate.parse(dto.getReturnFlightDate()))
                .name(dto.getName())
                .surname(dto.getSurname())
                .email(dto.getEmail())
                .price(dto.getPrice())
                .build();
    }

    public Reservation mapToReservation(final ReservationDto dto) {
        return Reservation.builder()
                .id(dto.getId())
                .thereFlightDepartureCity(dto.getFlightDepartureCity())
                .thereFlightDepartureAirportCode(dto.getFlightDepartureAirportCode())
                .thereFlightDestinationCity(dto.getFlightDestinationCity())
                .thereFlightDestinationAirportCode(dto.getFlightDestinationAirportCode())
                .thereFlightDate(LocalDate.parse(dto.getFlightDate()))
                .returnFlightDepartureCity(dto.getReturnFlightDepartureCity())
                .returnFlightDepartureAirportCode(dto.getReturnFlightDepartureAirportCode())
                .returnFlightDestinationCity(dto.getReturnFlightDestinationCity())
                .returnFlightDestinationAirportCode(dto.getReturnFlightDestinationAirportCode())
                .returnFlightDate(LocalDate.parse(dto.getReturnFlightDate()))
                .name(dto.getName())
                .surname(dto.getSurname())
                .email(dto.getEmail())
                .price(dto.getPrice())
                .payment(paymentMapper.mapToPayment(dto.getPaymentDto()))
                .build();
    }

    public List<Reservation> mapToReservationList(final List<ReservationDto> dtoList) {
        return dtoList.stream()
                .map(this::mapToReservation)
                .collect(Collectors.toList());
    }

    public ReservationDto mapToDto(final Reservation reservation) {
        return ReservationDto.builder()
                .id(reservation.getId())
                .flightDepartureCity(reservation.getThereFlightDepartureCity())
                .flightDepartureAirportCode(reservation.getThereFlightDepartureAirportCode())
                .flightDestinationCity(reservation.getThereFlightDestinationCity())
                .flightDestinationAirportCode(reservation.getThereFlightDestinationAirportCode())
                .flightDate(reservation.getThereFlightDate().toString())
                .returnFlightDepartureCity(reservation.getReturnFlightDepartureCity())
                .returnFlightDepartureAirportCode(reservation.getReturnFlightDepartureAirportCode())
                .returnFlightDestinationCity(reservation.getReturnFlightDestinationCity())
                .returnFlightDestinationAirportCode(reservation.getReturnFlightDestinationAirportCode())
                .returnFlightDate(reservation.getReturnFlightDate().toString())
                .name(reservation.getName())
                .surname(reservation.getSurname())
                .email(reservation.getEmail())
                .price(reservation.getPrice())
                .paymentDto(paymentMapper.mapToDto(reservation.getPayment()))
                .build();
    }

    public List<ReservationDto> mapToDtoList(final List<Reservation> reservationList) {
        return reservationList.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
