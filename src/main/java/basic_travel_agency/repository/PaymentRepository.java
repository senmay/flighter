package basic_travel_agency.repository;

import basic_travel_agency.domain.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Long> {

    @Override
    Payment save(Payment payment);

    @Override
    Optional<Payment> findById(Long id);

    @Override
    List<Payment> findAll();

    @Override
    void deleteAll();

    @Override
    void deleteById(Long id);

    List<Payment> findAllByPaymentDate(LocalDate date);
}
