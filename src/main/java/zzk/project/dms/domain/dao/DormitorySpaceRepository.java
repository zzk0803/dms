package zzk.project.dms.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zzk.project.dms.domain.entities.DormitorySpace;

import java.util.List;

public interface DormitorySpaceRepository extends JpaRepository<DormitorySpace, String> {
    List<DormitorySpace> findDormitorySpacesByUpperSpace(DormitorySpace upperSpace);

    boolean existsDormitorySpacesByUpperSpace(DormitorySpace upperSpace);

    int countDormitorySpacesByUpperSpace(DormitorySpace upperSpace);

    List<DormitorySpace> findDormitorySpacesByType(DormitorySpace.SpaceType type);
}
