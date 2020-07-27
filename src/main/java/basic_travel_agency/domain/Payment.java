package basic_travel_agency.domain;

import basic_travel_agency.enumeration.PaymentStatus;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "PAYMENTS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
public class Payment {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    @NotNull
    private BigDecimal value;
    private LocalDate paymentDate;

    public boolean hasPaymentDate() {
        return ! (paymentDate == null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return id.equals(payment.id) &&
                status == payment.status &&
                value.equals(payment.value) &&
                paymentDate.equals(payment.paymentDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, value, paymentDate);
    }
}
