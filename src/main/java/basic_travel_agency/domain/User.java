package basic_travel_agency.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "USERS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class User {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    @Column(unique = true)
    private String email;

    @NotNull
    private String securePassword;

    @NotNull
    private LocalDate registered;

    @NotNull
    @Column(name = "NOTIFICATION_PREFERENCES")
    @OneToMany(
            targetEntity = NotificationPreference.class,
            mappedBy = "user",
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private Set<NotificationPreference> notificationPreferences;

    public void addPreference(NotificationPreference preference) {
        notificationPreferences.add(preference);
    }

    public void removePreference(NotificationPreference preference) {
        notificationPreferences.remove(preference);
    }

    public void removeAllPreferences() {
        notificationPreferences.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) &&
                name.equals(user.name) &&
                surname.equals(user.surname) &&
                email.equals(user.email) &&
                securePassword.equals(user.securePassword) &&
                registered.equals(user.registered);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, email, securePassword, registered);
    }

}
