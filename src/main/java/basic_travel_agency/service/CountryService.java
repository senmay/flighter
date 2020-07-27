package basic_travel_agency.service;

import basic_travel_agency.api.countries.domain.Country;
import basic_travel_agency.exceptions.CountryNotFoundException;
import basic_travel_agency.repository.CountryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class CountryService {
    @Autowired
    private final CountryRepository countryRepository;

    private Boolean exists(Country country) {
        return countryRepository.existsByName(country.getName());
    }

    private Boolean notExists(Country country) {
        return ! this.exists(country);
    }

    @Transactional
    public Country addCountry(final Country country) {
        return countryRepository.save(country);
    }

    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    public Country getCountryById(final Long id) {
        return countryRepository.findById(id).orElseThrow(CountryNotFoundException::new);
    }

    public Country getCountryByName(final String name) {
        return countryRepository.findByName(name).orElseThrow(CountryNotFoundException::new);
    }

    @Transactional
    public void updateDatabase(final List<Country> countries) {
        countries.stream()
                .filter(this::notExists)
                .forEach(this::addCountry);
    }

    @Transactional
    public void deleteAllCountries() {
        countryRepository.deleteAll();
    }
}
