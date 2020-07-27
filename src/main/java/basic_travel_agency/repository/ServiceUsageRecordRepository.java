package basic_travel_agency.repository;

import basic_travel_agency.domain.ServiceUsageRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceUsageRecordRepository extends CrudRepository<ServiceUsageRecord, Long> {

    @Override
    ServiceUsageRecord save(ServiceUsageRecord record);

    @Override
    List<ServiceUsageRecord> findAll();

    @Override
    void deleteAll();

    List<ServiceUsageRecord> findAllByServiceClass(String className);
}
