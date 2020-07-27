package basic_travel_agency.service;

import basic_travel_agency.domain.FlightSearchRequest;
import basic_travel_agency.repository.FlightSearchRequestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class FlightSearchRequestService {
    private final FlightSearchRequestRepository repository;

    @Transactional
    public void addSearchRequest(final FlightSearchRequest request) {
        repository.save(request);
    }

    public List<FlightSearchRequest> getAllRequests() {
        return repository.findAll();
    }

    @Transactional
    public void deleteAll() {
        repository.deleteAll();
    }
}
