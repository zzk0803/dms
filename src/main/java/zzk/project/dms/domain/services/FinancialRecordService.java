package zzk.project.dms.domain.services;

import zzk.project.dms.domain.entities.FinancialRecord;
import zzk.project.dms.domain.entities.Tenement;
import zzk.project.dms.domain.services.common.DataProviderSupportService;

import java.util.List;

public interface FinancialRecordService extends DataProviderSupportService<FinancialRecord, Long, Tenement> {
    FinancialRecord putFinancialRecord(FinancialRecord financialRecord);

    List<FinancialRecord> findFinancialRecords();
}
