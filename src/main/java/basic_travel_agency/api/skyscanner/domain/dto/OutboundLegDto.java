package basic_travel_agency.api.skyscanner.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OutboundLegDto {
    @JsonProperty("CarrierIds")
    private List<Integer> carriersIds;

    @JsonProperty("DepartureDate")
    private String departureDate;

}
