package basic_travel_agency.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "SENT_EMAILS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MailSentRecord {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private LocalDateTime sendTime;

    @NotNull
    private String recipientEmail;

    public MailSentRecord(LocalDateTime time, String email) {
        this.sendTime = time;
        this.recipientEmail = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MailSentRecord that = (MailSentRecord) o;
        return sendTime.equals(that.sendTime) &&
                recipientEmail.equals(that.recipientEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sendTime, recipientEmail);
    }
}
