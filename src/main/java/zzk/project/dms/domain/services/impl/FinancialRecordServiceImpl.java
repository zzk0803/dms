package zzk.project.dms.domain.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import zzk.project.dms.domain.dao.FinancialRecordRepository;
import zzk.project.dms.domain.entities.FinancialRecord;
import zzk.project.dms.domain.services.FinancialRecordService;
import zzk.project.dms.middle.SubscriberAndService;

import java.util.List;

@SubscriberAndService
public class FinancialRecordServiceImpl implements FinancialRecordService {

    @Autowired
    private FinancialRecordRepository financialRecordRepository;

    @Override
    public FinancialRecord putFinancialRecord(FinancialRecord financialRecord) {
        return financialRecordRepository.save(financialRecord);
    }

    @Override
    public List<FinancialRecord> findFinancialRecords() {
        return financialRecordRepository.findAll();
    }
}
