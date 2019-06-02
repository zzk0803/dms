package zzk.project.dms.domain.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import zzk.project.dms.domain.entities.DormitorySpace;
import zzk.project.dms.domain.entities.DormitorySpaceType;

import java.util.List;

public interface DormitorySpaceRepository extends JpaRepository<DormitorySpace, Long> {
    List<DormitorySpace> findDormitorySpacesByParent(DormitorySpace parent);

    Page<DormitorySpace> findDormitorySpacesByParent(DormitorySpace parent, Pageable pageable);

    boolean existsDormitorySpacesByParent(DormitorySpace parent);

    int countDormitorySpacesByParent(DormitorySpace parent);

    List<DormitorySpace> findDormitorySpacesByType(DormitorySpaceType type);

    int countByType(DormitorySpaceType type);

    DormitorySpace findFirstByAvailableAndParent(boolean available, DormitorySpace dormitorySpace);

    Page<DormitorySpace> findByNameContaining(String name, Pageable pageable);

    int countByNameContains(String name);
}
