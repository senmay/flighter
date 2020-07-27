package basic_travel_agency.api.weather.facade;


import basic_travel_agency.api.weather.client.WeatherClient;
import basic_travel_agency.api.weather.domain.WeatherForecast;
import basic_travel_agency.api.weather.mapper.WeatherForecastMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class WeatherClientFacade {
    @Autowired
    private WeatherClient weatherClient;
    @Autowired
    private WeatherForecastMapper weatherForecastMapper;

    public WeatherForecast getWeatherForecast(String city) {
        return weatherForecastMapper.mapToWeatherForecast(weatherClient.getWeatherForecast(city));
    }
}
