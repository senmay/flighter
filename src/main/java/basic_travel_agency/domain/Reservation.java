package basic_travel_agency.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "RESERVATIONS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString
public class Reservation {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String thereFlightDepartureCity;

    @NotNull
    private String thereFlightDepartureAirportCode;

    @NotNull
    private String thereFlightDestinationCity;

    @NotNull
    private String thereFlightDestinationAirportCode;

    @NotNull
    private LocalDate thereFlightDate;

    @NotNull
    private String returnFlightDepartureCity;

    @NotNull
    private String returnFlightDepartureAirportCode;

    @NotNull
    private String returnFlightDestinationCity;

    @NotNull
    private String returnFlightDestinationAirportCode;

    @NotNull
    private LocalDate returnFlightDate;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    private String email;

    @NotNull
    private BigDecimal price;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "PAYMENT_ID")
    private Payment payment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return thereFlightDepartureCity.equals(that.thereFlightDepartureCity) &&
                thereFlightDepartureAirportCode.equals(that.thereFlightDepartureAirportCode) &&
                thereFlightDestinationCity.equals(that.thereFlightDestinationCity) &&
                thereFlightDestinationAirportCode.equals(that.thereFlightDestinationAirportCode) &&
                thereFlightDate.equals(that.thereFlightDate) &&
                returnFlightDepartureCity.equals(that.returnFlightDepartureCity) &&
                returnFlightDepartureAirportCode.equals(that.returnFlightDepartureAirportCode) &&
                returnFlightDestinationCity.equals(that.returnFlightDestinationCity) &&
                returnFlightDestinationAirportCode.equals(that.returnFlightDestinationAirportCode) &&
                returnFlightDate.equals(that.returnFlightDate) &&
                name.equals(that.name) &&
                surname.equals(that.surname) &&
                email.equals(that.email) &&
                price.equals(that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(thereFlightDepartureCity, thereFlightDepartureAirportCode, thereFlightDestinationCity, thereFlightDestinationAirportCode, thereFlightDate, returnFlightDepartureCity, returnFlightDepartureAirportCode, returnFlightDestinationCity, returnFlightDestinationAirportCode, returnFlightDate, name, surname, email, price);
    }
}
