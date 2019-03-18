package zzk.project.dms.domain.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import zzk.project.dms.domain.entities.DormitorySpace;
import zzk.project.dms.domain.entities.DormitorySpaceType;

import java.util.List;

public interface DormitorySpaceRepository extends JpaRepository<DormitorySpace, Long>, JpaSpecificationExecutor<DormitorySpace> {
    List<DormitorySpace> findDormitorySpacesByParent(DormitorySpace upperSpace);

    boolean existsDormitorySpacesByParent(DormitorySpace upperSpace);

    int countDormitorySpacesByParent(DormitorySpace upperSpace);

    List<DormitorySpace> findDormitorySpacesByType(DormitorySpaceType type);

    int countByType(DormitorySpaceType type);

    DormitorySpace findFirstByAvailableAndParent(boolean available, DormitorySpace dormitorySpace);
}
