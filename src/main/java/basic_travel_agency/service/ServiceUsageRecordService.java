package basic_travel_agency.service;

import basic_travel_agency.domain.ServiceUsageRecord;
import basic_travel_agency.repository.ServiceUsageRecordRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class ServiceUsageRecordService {
    private final ServiceUsageRecordRepository repository;

    @Transactional
    public void addRecord(final ServiceUsageRecord record) {
        repository.save(record);
    }

    public List<ServiceUsageRecord> getAllRecords() {
        return repository.findAll();
    }

    public List<ServiceUsageRecord> getRecordsOfMethod(final String className) {
        return repository.findAllByServiceClass(className);
    }

    @Transactional
    public void deleteAllRecords() {
        repository.deleteAll();
    }
}
