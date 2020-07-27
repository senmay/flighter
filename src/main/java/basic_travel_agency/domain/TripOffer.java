package basic_travel_agency.domain;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TripOffer {
    private FlightConnectionOfferWithWeather thereConnection;
    private FlightConnectionOfferWithWeather returnConnection;
    private BigDecimal price;
}
