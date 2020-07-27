package basic_travel_agency.api.weather.client;

import basic_travel_agency.api.weather.domain.dto.WeatherForecastDto;
import basic_travel_agency.config.AdminConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Component
@AllArgsConstructor
@Slf4j
public class WeatherClient {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AdminConfig adminConfig;

    public WeatherForecastDto getWeatherForecast(String city) {
        URI url = UriComponentsBuilder.fromHttpUrl(adminConfig.getWeatherApiBaseUrl())
                .queryParam("key", adminConfig.getWeatherApiKey())
                .queryParam("lang", "en")
                .queryParam("units", "M")
                .queryParam("city", city)
                .build().encode().toUri();

        try {
            WeatherForecastDto response = restTemplate.getForObject(url, WeatherForecastDto.class);
            return Optional.ofNullable(response).orElse(new WeatherForecastDto());
        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
            return new WeatherForecastDto();
        }
    }
}
