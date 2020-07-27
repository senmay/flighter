package basic_travel_agency.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "FLIGHT_SEARCHES")
@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FlightSearchRequest {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String departureCity;

    @NotNull
    private String departureAirport;

    @NotNull
    private String destinationCity;

    @NotNull
    private String destinationAirport;

    @NotNull
    private LocalDate departureDay;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightSearchRequest that = (FlightSearchRequest) o;
        return departureCity.equals(that.departureCity) &&
                departureAirport.equals(that.departureAirport) &&
                destinationCity.equals(that.destinationCity) &&
                destinationAirport.equals(that.destinationAirport) &&
                departureDay.equals(that.departureDay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departureCity, departureAirport, destinationCity, destinationAirport, departureDay);
    }
}
