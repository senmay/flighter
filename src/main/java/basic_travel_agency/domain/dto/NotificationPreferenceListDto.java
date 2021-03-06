package basic_travel_agency.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NotNull
@Getter
public class NotificationPreferenceListDto {
    private final List<NotificationPreferenceDto> preferences;
}
