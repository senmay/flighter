package basic_travel_agency.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "APPLICATION_STARTUP_ARGS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StartupArgs {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private LocalDateTime startupTime;

    private String[] args;

    public StartupArgs(LocalDateTime time, String[] args) {
        this.startupTime = time;
        this.args = args;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StartupArgs that = (StartupArgs) o;
        return Objects.equals(startupTime, that.startupTime) &&
                Arrays.equals(args, that.args);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(startupTime);
        result = 31 * result + Arrays.hashCode(args);
        return result;
    }
}
