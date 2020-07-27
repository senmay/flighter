package basic_travel_agency.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NotNull
@Getter
public class PaymentListDto {
    private final List<PaymentDto> payments;
}
