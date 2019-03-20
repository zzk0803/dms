package zzk.project.dms.domain.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zzk.project.dms.domain.dao.FinancialRecordRepository;
import zzk.project.dms.domain.entities.FinancialRecord;
import zzk.project.dms.domain.services.FinancialRecordService;

@Transactional
@Service
public class FinancialRecordServiceImpl implements FinancialRecordService {

    @Autowired
    private FinancialRecordRepository financialRecordRepository;

    @Override
    public JpaRepository<FinancialRecord, Long> getRepository() {
        return financialRecordRepository;
    }
}
