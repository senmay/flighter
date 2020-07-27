package basic_travel_agency.api.skyscanner.mapper;

import basic_travel_agency.api.skyscanner.domain.FlightConnectionsResult;
import basic_travel_agency.api.skyscanner.domain.dto.CarrierDto;
import basic_travel_agency.api.skyscanner.domain.dto.FlightConnectionsResultDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
public class FlightConnectionsResultMapper {
    private final FlightMapper flightMapper;

    public FlightConnectionsResult mapToFlightConnectionsResult(FlightConnectionsResultDto dto) {
        Map<Integer, String> carrierIdAndName = new HashMap<>();
        for (CarrierDto carrierDto : dto.getCarriers()) {
            carrierIdAndName.put(carrierDto.getCarrierId(), carrierDto.getCarrierName());
        }
        return new FlightConnectionsResult(
                flightMapper.mapToFlightList(dto.getFlights(), carrierIdAndName)
        );
    }
}
