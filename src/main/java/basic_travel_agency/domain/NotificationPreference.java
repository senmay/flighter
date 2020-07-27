package basic_travel_agency.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Entity
@Table(name = "NOTIFICATION_PREFERENCES")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString
public class NotificationPreference {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @NotNull
    private String departureCity;

    @NotNull
    private String destinationCity;

    @NotNull
    private Integer minTemperature;

    @NotNull
    private BigDecimal maxPrice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationPreference that = (NotificationPreference) o;
        return id.equals(that.id) &&
                user.equals(that.user) &&
                departureCity.equals(that.departureCity) &&
                destinationCity.equals(that.destinationCity) &&
                minTemperature.equals(that.minTemperature) &&
                maxPrice.setScale(2, RoundingMode.HALF_EVEN)
                        .equals(that.maxPrice.setScale(2, RoundingMode.HALF_EVEN));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, departureCity, destinationCity, minTemperature, maxPrice);
    }
}
