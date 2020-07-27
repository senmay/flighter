package basic_travel_agency.api.skyscanner.domain;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
public class Flight {
    private String departureCity;
    private String destinationCity;
    private String departureAirport;
    private String destinationAirport;
    private BigDecimal minPrice;
    private boolean direct;
    private List<String> carriers;
    private LocalDate departureDate;
}
