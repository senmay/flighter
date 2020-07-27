package basic_travel_agency.domain.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserDto {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String securePassword;
    private String registered;
    private Set<Long> notificationIds;

}
