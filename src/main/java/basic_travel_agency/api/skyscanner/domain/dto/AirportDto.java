package basic_travel_agency.api.skyscanner.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AirportDto {

    @JsonProperty("PlaceId")
    private String airportCode;

    @JsonProperty("PlaceName")
    private String airportName;

    @JsonProperty("CountryName")
    private String country;

}
