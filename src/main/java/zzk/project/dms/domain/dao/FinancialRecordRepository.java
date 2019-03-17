package zzk.project.dms.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import zzk.project.dms.domain.entities.FinancialRecord;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface FinancialRecordRepository
        extends JpaRepository<FinancialRecord,Long> , JpaSpecificationExecutor<FinancialRecord> {

}
