package basic_travel_agency.api.skyscanner.mapper;

import basic_travel_agency.api.skyscanner.domain.Airport;
import basic_travel_agency.api.skyscanner.domain.dto.AirportDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class AirportMapper {
    private final Pattern airportCodePattern = Pattern.compile(".{3}-sky");

    private Airport mapToAirport(AirportDto dto) {
        return Airport.builder()
                /***
                 * Airport codes are 3 charactes long. Sometimes first result is country, ex. "MOSC-sky" when proper airport code format is "***-sky".
                 * When code does not match code pattern, record is marked as not airport for further filtering.
                 */
                .airportCode(
                        (airportCodePattern.matcher(dto.getAirportCode()).matches()) ? dto.getAirportCode().substring(0, 3) : "NOT AIRPORT"
                )
                .airportName(dto.getAirportName())
                .country(dto.getCountry())
                .build();
    }

    public List<Airport> mapToAirportList(List<AirportDto> dtoList) {
        return dtoList.stream()
                .map(this::mapToAirport)
                /**
                 * If mapping to Airport assign airport code longer than 3 charaacter then it's not proper code and needs to be filtered out.
                 */
                .filter(e -> e.getAirportCode().length() == 3)
                .collect(Collectors.toList());
    }
}
