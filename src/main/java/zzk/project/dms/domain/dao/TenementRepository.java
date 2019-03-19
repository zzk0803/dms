package zzk.project.dms.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import zzk.project.dms.domain.entities.DormitorySpace;
import zzk.project.dms.domain.entities.Tenement;

import java.util.List;

public interface TenementRepository extends JpaRepository<Tenement, Long> , JpaSpecificationExecutor<Tenement> {
    int countByDormitorySpaceIsNotNull();

    List<Tenement> findAllByDormitorySpaceIn(List<DormitorySpace> dormitorySpace);
}
