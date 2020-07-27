package basic_travel_agency.service;

import basic_travel_agency.domain.ServiceUsageRecord;
import basic_travel_agency.domain.User;
import basic_travel_agency.exceptions.UserEmailAlreadyExistsException;
import basic_travel_agency.exceptions.UserNotFoundException;
import basic_travel_agency.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ServiceUsageRecordService recordService;

    /**
     * Method checks if in database already exists user with email provided as @param
     * Email is unique in database, so can not exist two users with same email address
     *
     * @param email
     * @return true or false
     */
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Wraps existsByEmail(String email) so that @param object can be user
     *
     * @param user which existence in database is checked by its id
     * @return
     */
    public boolean exists(User user) {
        return existsByEmail(user.getEmail());
    }

    /**
     * Adds new user to the database. Before checks if it already exists.
     *
     * @param user
     * @return
     */
    @Transactional
    public User addUser(final User user) {
        if (this.exists(user)) {
            throw new UserEmailAlreadyExistsException();
        }

        ServiceUsageRecord record = ServiceUsageRecord.builder()
                .whenExecuted(LocalDateTime.now())
                .serviceClass(this.getClass().getName())
                .methodArgument("user")
                .build();
        recordService.addRecord(record);

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    @Transactional
    public void deleteUserById(final Long id) {
        userRepository.deleteById(id);
    }

    public User getUserById(final Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public User getUserByEmail(final String email) {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }
}
