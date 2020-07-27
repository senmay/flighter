package basic_travel_agency.controler;

import basic_travel_agency.domain.Payment;
import basic_travel_agency.domain.dto.PaymentDto;
import basic_travel_agency.domain.dto.PaymentListDto;
import basic_travel_agency.exceptions.WrongDateFormatException;
import basic_travel_agency.mapper.PaymentMapper;
import basic_travel_agency.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.regex.Pattern;

@RestController
@RequestMapping
@AllArgsConstructor
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private PaymentMapper paymentMapper;

    @PostMapping("payments")
    public void addPayment(@RequestBody PaymentDto dto) {
        paymentService.addPayment(paymentMapper.mapToPayment(dto));
    }

    @GetMapping("payments/{id}")
    public PaymentDto getPayment(@PathVariable("id") Long id) {
        return paymentMapper.mapToDto(paymentService.getPaymentById(id));
    }

    @GetMapping("payments")
    public PaymentListDto getAllPayments() {
        return new PaymentListDto(paymentMapper.mapToDtoList(paymentService.getAllPayments()));
    }

    @PutMapping("payments")
    @Transactional
    public PaymentDto updatePayment(@RequestBody PaymentDto updatingDto) {
        Payment payment = paymentService.getPaymentById(updatingDto.getId());
        BigDecimal currentValue = payment.getValue().setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal updatingValue = updatingDto.getValue().setScale(2, RoundingMode.HALF_EVEN);

        if (! payment.hasPaymentDate() && updatingDto.hasValidDate()) {
            payment.setPaymentDate(LocalDate.parse(updatingDto.getPaymentDate()));
        }
        if (
                payment.hasPaymentDate() &&
                        updatingDto.hasValidDate() &&
                        ! (payment.getPaymentDate().toString().equals(updatingDto.getPaymentDate()))

        ) {
            payment.setPaymentDate(LocalDate.parse(updatingDto.getPaymentDate()));
        }
        if (! payment.getStatus().equals(updatingDto.getStatus())) {
            payment.setStatus(updatingDto.getStatus());
        }
        if (! currentValue.equals(updatingValue)) {
            payment.setValue(updatingDto.getValue());
        }

        return paymentMapper.mapToDto(payment);
    }

    @GetMapping("payments/")
    public PaymentListDto getPaymentsByDate(@RequestParam("date") String date) {
        Pattern datePattern = Pattern.compile("^((19|2[0-9])[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$");
        if (datePattern.matcher(date).matches()) {
            return new PaymentListDto(paymentMapper.mapToDtoList(paymentService.getPaymentsByDate(LocalDate.parse(date))));
        }
        throw new WrongDateFormatException();
    }

}
