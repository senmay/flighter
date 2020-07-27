package basic_travel_agency.service;

import basic_travel_agency.domain.NotificationPreference;
import basic_travel_agency.domain.User;
import basic_travel_agency.exceptions.NotificationPreferenceNotFoundException;
import basic_travel_agency.repository.NotificationPreferenceRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class NotificationPreferenceService {
    private final NotificationPreferenceRepository notificationPreferenceRepository;
    private final UserService userService;

    /**
     * Adds notification preference to the database and parent object's set of children objects.
     *
     * @param preference
     * @return
     */
    @Transactional
    public NotificationPreference addPreference(NotificationPreference preference) {
        notificationPreferenceRepository.save(preference);
        preference.getUser().getNotificationPreferences().add(preference);
        return preference;
    }

    public NotificationPreference getPreferenceById(Long id) {
        return notificationPreferenceRepository.findById(id).orElseThrow(NotificationPreferenceNotFoundException::new);
    }

    public List<NotificationPreference> getAllPreferences() {
        return notificationPreferenceRepository.findAll();
    }

    /**
     * Returns a list of preferences with destination departureCity chosen as departureCity provided as method @param
     *
     * @param city
     * @return
     */
    public List<NotificationPreference> getAllPreferencesByCity(String city) {
        return notificationPreferenceRepository.findAllByDestinationCity(city);
    }

    /**
     * Returns a list of preferences created by user provided as @param If provided user does not exist, returns empty list.
     * Prevents InvalidDataAccessApiUsageException when trying to use unsaved User object
     *
     * @param user
     * @return
     */
    public List<NotificationPreference> getAllPreferencesByUser(User user) {
        if (userService.exists(user)) {
            return notificationPreferenceRepository.findAllByUser(user);
        } else {
            log.error("Attempting to load preferences of unsaved User: returning empty list");
            return new ArrayList<>();
        }
    }

    @Transactional
    public void deleteAllPreferences() {
        userService.getAllUsers().stream().forEach(User::removeAllPreferences);
    }

    /**
     * Deletes all preferences created by user provided as @param
     *
     * @param user
     */
    @Transactional
    public void deleteAllPreferencesByUser(User user) {
        if (userService.exists(user)) {
            userService.getUserById(user.getId()).removeAllPreferences();
        } else {
            log.error("Attempting to delete preferences of unsaved User");
        }
    }

    /**
     * Due to the existing link with parent object, method removes preference with @param id by removing it from
     * the parent object collection.
     * If, by any chance, there is no relation with parent object, preference is removed with repository method.
     *
     * @param id
     */
    @Transactional
    public void deletePreferenceById(Long id) {
        NotificationPreference preference = this.getPreferenceById(id);

        if (Objects.nonNull(preference.getUser())) {
            preference.getUser().removePreference(preference);
        } else {
            notificationPreferenceRepository.deleteById(id);
        }
    }

}
