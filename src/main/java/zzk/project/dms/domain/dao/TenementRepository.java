package zzk.project.dms.domain.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import zzk.project.dms.domain.entities.DormitorySpace;
import zzk.project.dms.domain.entities.Tenement;

import java.util.List;

public interface TenementRepository extends JpaRepository<Tenement, Long>, JpaSpecificationExecutor<Tenement> {
    int countByDormitorySpaceIsNotNull();

    int countByDormitorySpaceIn(List<DormitorySpace> dormitorySpace);

    List<Tenement> findAllByDormitorySpaceIn(List<DormitorySpace> dormitorySpace);

    Page<Tenement> findByNameContaining(String name, Pageable pageable);

    int countByNameContains(String name);
}
