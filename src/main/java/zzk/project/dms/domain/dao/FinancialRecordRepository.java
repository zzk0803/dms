package zzk.project.dms.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zzk.project.dms.domain.entities.FinancialRecord;

import java.util.List;

public interface FinancialRecordRepository extends JpaRepository<FinancialRecord,Long> {
}
