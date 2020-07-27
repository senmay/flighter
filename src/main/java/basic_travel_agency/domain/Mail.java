package basic_travel_agency.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
public class Mail {
    private final String mailTo;
    private final String toCc;
    private final String subject;
    private final String message;
}
