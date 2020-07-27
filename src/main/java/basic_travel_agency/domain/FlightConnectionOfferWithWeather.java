package basic_travel_agency.domain;

import basic_travel_agency.api.skyscanner.domain.Flight;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FlightConnectionOfferWithWeather implements Comparable<FlightConnectionOfferWithWeather> {
    private Flight flight;
    private BigDecimal expectedTemperature;

    @Override
    public int compareTo(FlightConnectionOfferWithWeather other) {
        return this.getFlight().getMinPrice().compareTo(other.getFlight().getMinPrice());
    }
}
