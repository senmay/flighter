package basic_travel_agency.api.countries.mapper;

import basic_travel_agency.api.countries.domain.Country;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class CountryMapper {

    private Country mapToCountry(Map<String, String> response) {
        Country country = new Country();
        country.setName(response.get("country"));
        country.setCapital(response.get("capital"));
        return country;
    }

    public List<Country> mapToCountryList(Object response) {
        ArrayList<Map<String, String>> responseList = (ArrayList<Map<String, String>>) response;
        List<Country> countriesToReturn = new ArrayList<>();

        for (Map<String, String> countryMap : responseList) {
            countriesToReturn.add(this.mapToCountry(countryMap));
        }

        return countriesToReturn;
    }
}
