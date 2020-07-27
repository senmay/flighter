package basic_travel_agency.domain.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ReservationCreationDto {
    private String flightDepartureCity;
    private String flightDepartureAirportCode;
    private String flightDestinationCity;
    private String flightDestinationAirportCode;
    private String flightDate;
    private String returnFlightDepartureCity;
    private String returnFlightDepartureAirportCode;
    private String returnFlightDestinationCity;
    private String returnFlightDestinationAirportCode;
    private String returnFlightDate;
    private String name;
    private String surname;
    private String email;
    private BigDecimal price;
}
