package basic_travel_agency.api.countries.facade;

import basic_travel_agency.api.countries.client.RestcountriesClient;
import basic_travel_agency.api.countries.domain.Country;
import basic_travel_agency.api.countries.mapper.CountryMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class RestcountriesFacade {
    @Autowired
    private final RestcountriesClient restcountriesClient;
    @Autowired
    private final CountryMapper countryMapper;

    public List<Country> getCountries() {
        return countryMapper.mapToCountryList(restcountriesClient.getResponse());
    }
}
