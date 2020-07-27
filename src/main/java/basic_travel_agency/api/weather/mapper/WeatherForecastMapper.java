package basic_travel_agency.api.weather.mapper;

import basic_travel_agency.api.weather.domain.WeatherForecast;
import basic_travel_agency.api.weather.domain.dto.WeatherForecastDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class WeatherForecastMapper {
    private final DailyWeatherForecastMapper dailyWeatherForecastMapper;

    public WeatherForecast mapToWeatherForecast(WeatherForecastDto dto) {
        return WeatherForecast.builder()
                .city(dto.getCity())
                .dailyForecasts(dailyWeatherForecastMapper.mapToDailyWeatherForecastList(dto.getWeatherForecasts()))
                .build();
    }
}
