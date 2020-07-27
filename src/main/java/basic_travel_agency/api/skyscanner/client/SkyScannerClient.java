package basic_travel_agency.api.skyscanner.client;

import basic_travel_agency.api.skyscanner.domain.dto.CityAirportsResultDto;
import basic_travel_agency.api.skyscanner.domain.dto.FlightConnectionsResultDto;
import basic_travel_agency.config.AdminConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

@Component
@AllArgsConstructor
@Slf4j
public class SkyScannerClient {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AdminConfig adminConfig;

    private HttpHeaders getSkyScannerHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-RapidAPI-Host", adminConfig.getSkyScannerApiHeaderHost());
        headers.add("X-RapidAPI-Key", adminConfig.getSkyScannerApiHeaderKey());
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        return headers;
    }

    public FlightConnectionsResultDto getFlightConnections(String originAirportCode, String destinationAirportCode, LocalDate date) {
        HttpHeaders headers = this.getSkyScannerHeader();
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        URI url = UriComponentsBuilder.fromHttpUrl(adminConfig.getSkyScannerApiBaseUrl())
                .path("browseroutes/v1.0/PL/PLN/pl-PL/")
                .pathSegment(originAirportCode)
                .pathSegment(destinationAirportCode)
                .pathSegment(date.toString())
                .build().encode().toUri();

        try {
            FlightConnectionsResultDto response = restTemplate.exchange(url, HttpMethod.GET, entity, FlightConnectionsResultDto.class).getBody();
            return Optional.ofNullable(response).orElse(new FlightConnectionsResultDto());
        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
            return new FlightConnectionsResultDto();
        }
    }

    public CityAirportsResultDto getAirportsInCity(String city) {
        HttpHeaders headers = this.getSkyScannerHeader();
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        URI url = UriComponentsBuilder.fromHttpUrl(adminConfig.getSkyScannerApiBaseUrl())
                .path("autosuggest/v1.0/PL/PLN/pl-PL/")
                .queryParam("query", city)
                .build().encode().toUri();

        try {
            CityAirportsResultDto response = restTemplate.exchange(url, HttpMethod.GET, entity, CityAirportsResultDto.class).getBody();
            return Optional.ofNullable(response).orElse(new CityAirportsResultDto());
        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
            return new CityAirportsResultDto();
        }

    }

}
