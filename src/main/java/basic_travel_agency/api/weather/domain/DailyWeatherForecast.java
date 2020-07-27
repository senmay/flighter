package basic_travel_agency.api.weather.domain;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder(toBuilder = true)
public class DailyWeatherForecast {
    private double minTemperature;
    private double maxTemperature;
    private double expectedTemperature;
    private LocalDate date;
}
