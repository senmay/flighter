package basic_travel_agency.api.skyscanner.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder(toBuilder = true)
public class Airport {
    private String airportCode;
    private String airportName;
    private String country;
}
