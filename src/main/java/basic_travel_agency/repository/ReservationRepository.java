package basic_travel_agency.repository;

import basic_travel_agency.domain.Reservation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {

    @Override
    Reservation save(Reservation user);

    @Override
    Optional<Reservation> findById(Long id);

    @Override
    List<Reservation> findAll();

    @Override
    void deleteAll();

    @Override
    void deleteById(Long id);

    List<Reservation> findAllBySurname(String surname);

    long countAllByThereFlightDestinationCity(String city);
}
