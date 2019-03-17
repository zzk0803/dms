package zzk.project.dms.domain.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import zzk.project.dms.domain.dao.DormitorySpaceRepository;
import zzk.project.dms.domain.dao.FinancialRecordRepository;
import zzk.project.dms.domain.dao.TenementRepository;
import zzk.project.dms.domain.entities.DormitorySpaceType;
import zzk.project.dms.domain.entities.FinancialRecord;
import zzk.project.dms.domain.services.SummarizeService;
import zzk.project.dms.middle.ServiceAndSubscriber;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@ServiceAndSubscriber
public class SummarizeServiceImpl implements SummarizeService {

    @Autowired
    private DormitorySpaceRepository dormitorySpaceRepository;

    @Autowired
    private TenementRepository tenementRepository;

    @Autowired
    private FinancialRecordRepository financialRecordRepository;

    @Override
    public int countCommunities() {
        return dormitorySpaceRepository.countByType(DormitorySpaceType.COMMUNITY);
    }

    @Override
    public int countBuildings() {
        return dormitorySpaceRepository.countByType(DormitorySpaceType.BUILDING);
    }

    @Override
    public int countRooms() {
        return dormitorySpaceRepository.countByType(DormitorySpaceType.ROOM);
    }

    @Override
    public int countBerths() {
        return dormitorySpaceRepository.countByType(DormitorySpaceType.BERTH);
    }

    @Override
    public int countResident() {
        return tenementRepository.countByDormitorySpaceIsNotNull();
    }

    @Override
    public int countPerson() {
        return (int) tenementRepository.count();
    }

    @Override
    public int countCurrentMouthBills() {
        LocalDate end = LocalDate.now();
        LocalDate start = end.withDayOfMonth(1);
        List<FinancialRecord> records = financialRecordRepository.findAll(((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.between(
                root.get("recordDate"),
                start,
                end
        )));
        return records.size();
    }

    @Override
    public int countCurrentMouthBills(boolean mark) {
        LocalDate end = LocalDate.now();
        LocalDate start = end.withDayOfMonth(1);
        List<FinancialRecord> records = financialRecordRepository.findAll(((root, criteriaQuery, criteriaBuilder) -> {
            Predicate recordDatePredicate = criteriaBuilder.between(root.get("recordDate"), start, end);
            Path<Boolean> markPath = root.get("mark");
            Predicate markPredicate = mark
                    ? criteriaBuilder.isTrue(markPath)
                    : criteriaBuilder.isFalse(markPath);
            return criteriaQuery.where(recordDatePredicate, markPredicate).getRestriction();
        }));
        return records.size();
    }

    @Override
    public double amountUnmarkCheckIn() {
        List<FinancialRecord> records = financialRecordRepository.findAll(((root, criteriaQuery, criteriaBuilder) -> {
            Predicate markPredicate = criteriaBuilder.isFalse(root.get("mark"));
            return criteriaQuery.where(markPredicate).getRestriction();
        }));
        return records.stream().mapToDouble(record -> record.getCheckIn().doubleValue()).sum();
    }
}
