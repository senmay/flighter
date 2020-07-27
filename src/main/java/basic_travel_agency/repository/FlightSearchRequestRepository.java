package basic_travel_agency.repository;

import basic_travel_agency.domain.FlightSearchRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightSearchRequestRepository extends CrudRepository<FlightSearchRequest, Long> {
    @Override
    FlightSearchRequest save(FlightSearchRequest request);

    @Override
    List<FlightSearchRequest> findAll();

    @Override
    void deleteAll();
}
