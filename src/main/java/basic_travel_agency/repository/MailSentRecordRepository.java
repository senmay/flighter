package basic_travel_agency.repository;

import basic_travel_agency.domain.MailSentRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MailSentRecordRepository extends CrudRepository<MailSentRecord, Long> {
    @Override
    MailSentRecord save(MailSentRecord record);

    @Override
    List<MailSentRecord> findAll();

    @Override
    void deleteAll();

}
