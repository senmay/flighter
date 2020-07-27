package basic_travel_agency.service;

import basic_travel_agency.domain.Payment;
import basic_travel_agency.domain.Reservation;
import basic_travel_agency.domain.ServiceUsageRecord;
import basic_travel_agency.enumeration.PaymentStatus;
import basic_travel_agency.exceptions.ReservationNotFoundException;
import basic_travel_agency.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final PaymentService paymentService;
    private final ServiceUsageRecordService recordService;

    @Transactional
    public Reservation addReservation(final Reservation reservation) {
        log.info("Creating Payment object for reservation");
        Payment payment = Payment.builder()
                .value(reservation.getPrice())
                .status(PaymentStatus.AWAITING)
                .build();
        log.info("Saving Payment object to database");
        paymentService.addPayment(payment);
        log.info("Assigning Payment object to Reservation object");
        reservation.setPayment(payment);

        ServiceUsageRecord record = ServiceUsageRecord.builder()
                .whenExecuted(LocalDateTime.now())
                .serviceClass(this.getClass().getName())
                .methodArgument("reservation")
                .build();
        recordService.addRecord(record);

        return reservationRepository.save(reservation);
    }

    public Reservation getReservationById(final Long id) {
        return reservationRepository.findById(id).orElseThrow(ReservationNotFoundException::new);
    }

    public List<Reservation> getReservationsBySurname(final String surname) {
        return reservationRepository.findAllBySurname(surname);
    }

    public Long getNumberOfReservationsInCity(final String city) {
        return reservationRepository.countAllByThereFlightDestinationCity(city);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Transactional
    public void deleteAllReservations() {
        reservationRepository.deleteAll();
    }

    @Transactional
    public void deleteReservationById(final Long id) {
        reservationRepository.deleteById(id);
    }

}
