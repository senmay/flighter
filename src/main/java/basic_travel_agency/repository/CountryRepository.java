package basic_travel_agency.repository;

import basic_travel_agency.api.countries.domain.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends CrudRepository<Country, Long> {

    @Override
    Country save(Country country);

    @Override
    List<Country> findAll();

    @Override
    Optional<Country> findById(Long id);

    @Override
    void deleteAll();

    Optional<Country> findByName(String name);

    Boolean existsByName(String name);
}
