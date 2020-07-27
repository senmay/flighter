package basic_travel_agency.repository;

import basic_travel_agency.domain.NotificationPreference;
import basic_travel_agency.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationPreferenceRepository extends CrudRepository<NotificationPreference, Long> {
    @Override
    NotificationPreference save(NotificationPreference preference);

    @Override
    List<NotificationPreference> findAll();

    @Override
    void deleteAll();

    @Override
    void deleteById(Long id);

    @Override
    Optional<NotificationPreference> findById(Long id);

    void deleteAllByUser(User user);

    List<NotificationPreference> findAllByDestinationCity(String city);

    List<NotificationPreference> findAllByUser(User user);
}
