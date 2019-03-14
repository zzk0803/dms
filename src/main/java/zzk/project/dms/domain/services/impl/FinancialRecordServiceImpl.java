package zzk.project.dms.domain.services.impl;

import com.vaadin.flow.data.provider.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import zzk.project.dms.domain.dao.FinancialRecordRepository;
import zzk.project.dms.domain.entities.FinancialRecord;
import zzk.project.dms.domain.services.FinancialRecordService;
import zzk.project.dms.middle.ServiceAndSubscriber;

import java.util.List;
import java.util.stream.Stream;

@Transactional
@ServiceAndSubscriber
public class FinancialRecordServiceImpl implements FinancialRecordService {

    @Autowired
    private FinancialRecordRepository financialRecordRepository;

    @Override
    public JpaRepository<FinancialRecord, Long> getRepository() {
        return financialRecordRepository;
    }

    @Override
    public FinancialRecord putFinancialRecord(FinancialRecord financialRecord) {
        return financialRecordRepository.save(financialRecord);
    }

    @Override
    public List<FinancialRecord> findFinancialRecords() {
        return financialRecordRepository.findAll();
    }
}
