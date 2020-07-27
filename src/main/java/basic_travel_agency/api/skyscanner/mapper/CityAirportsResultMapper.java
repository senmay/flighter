package basic_travel_agency.api.skyscanner.mapper;

import basic_travel_agency.api.skyscanner.domain.CityAirportsResult;
import basic_travel_agency.api.skyscanner.domain.dto.CityAirportsResultDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CityAirportsResultMapper {
    private final AirportMapper airportMapper;

    public CityAirportsResult mapToCityAirportsResult(CityAirportsResultDto dto) {
        return new CityAirportsResult(airportMapper.mapToAirportList(dto.getAirports()));
    }
}
