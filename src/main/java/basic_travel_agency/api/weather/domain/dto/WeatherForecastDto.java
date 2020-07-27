package basic_travel_agency.api.weather.domain.dto;

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
public class WeatherForecastDto {

    @JsonProperty("data")
    private List<DailyWeatherForecastDto> weatherForecasts;

    @JsonProperty("city_name")
    private String city;

}
