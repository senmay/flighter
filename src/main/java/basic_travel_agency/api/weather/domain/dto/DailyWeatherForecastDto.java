package basic_travel_agency.api.weather.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DailyWeatherForecastDto {

    @JsonProperty("min_temp")
    private double minTemperature;

    @JsonProperty("max_temp")
    private double maxTemperature;

    @JsonProperty("temp")
    private double expectedTemperature;

    @JsonProperty("valid_date")
    private String date;
}
