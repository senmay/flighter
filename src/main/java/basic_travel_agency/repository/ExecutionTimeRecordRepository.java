package basic_travel_agency.repository;

import basic_travel_agency.domain.ExecutionTimeRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExecutionTimeRecordRepository extends CrudRepository<ExecutionTimeRecord, Long> {

    @Override
    ExecutionTimeRecord save(ExecutionTimeRecord record);

    @Override
    List<ExecutionTimeRecord> findAll();

    @Override
    void deleteAll();

    List<ExecutionTimeRecord> findAllByMethod(String method);
}
