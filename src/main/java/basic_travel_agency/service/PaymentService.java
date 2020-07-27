package basic_travel_agency.service;

import basic_travel_agency.domain.Payment;
import basic_travel_agency.domain.ServiceUsageRecord;
import basic_travel_agency.exceptions.PaymentNotFoundException;
import basic_travel_agency.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final ServiceUsageRecordService recordService;

    @Transactional
    public Payment addPayment(final Payment payment) {
        ServiceUsageRecord record = ServiceUsageRecord.builder()
                .whenExecuted(LocalDateTime.now())
                .serviceClass(this.getClass().getName())
                .methodArgument("payment")
                .build();
        recordService.addRecord(record);

        return paymentRepository.save(payment);
    }

    public Payment getPaymentById(final Long id) {
        return paymentRepository.findById(id).orElseThrow(PaymentNotFoundException::new);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public List<Payment> getPaymentsByDate(final LocalDate date) {
        return paymentRepository.findAllByPaymentDate(date);
    }

    @Transactional
    public void deleteAllPayments() {
        paymentRepository.deleteAll();
    }

    @Transactional
    public void deletePaymentById(final Long id) {
        paymentRepository.deleteById(id);
    }


}
