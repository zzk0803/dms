package zzk.project.dms.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zzk.project.dms.domain.entities.DormitorySpace;
import zzk.project.dms.domain.entities.DormitorySpaceType;

import java.util.List;

public interface DormitorySpaceRepository extends JpaRepository<DormitorySpace, Long> {
    List<DormitorySpace> findDormitorySpacesByParent(DormitorySpace upperSpace);

    boolean existsDormitorySpacesByParent(DormitorySpace upperSpace);

    int countDormitorySpacesByParent(DormitorySpace upperSpace);

    List<DormitorySpace> findDormitorySpacesByType(DormitorySpaceType type);
}
