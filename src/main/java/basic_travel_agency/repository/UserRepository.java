package basic_travel_agency.repository;

import basic_travel_agency.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Override
    User save(User user);

    @Override
    Optional<User> findById(Long id);

    @Override
    List<User> findAll();

    @Override
    void deleteAll();

    @Override
    void deleteById(Long id);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
