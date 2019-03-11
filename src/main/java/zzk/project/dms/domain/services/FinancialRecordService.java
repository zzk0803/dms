package zzk.project.dms.domain.services;

import zzk.project.dms.domain.entities.FinancialRecord;

import java.util.List;

public interface FinancialRecordService {
    FinancialRecord putFinancialRecord(FinancialRecord financialRecord);

    List<FinancialRecord> findFinancialRecords();
}
