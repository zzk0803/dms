package zzk.project.dms.domain.services;

import org.springframework.data.domain.Pageable;
import zzk.project.dms.domain.DormitoryManageException;
import zzk.project.dms.domain.entities.DormitorySpace;
import zzk.project.dms.domain.entities.DormitorySpaceType;
import zzk.project.dms.domain.services.common.JpaSupportService;

import java.util.List;

public interface DormitorySpaceService extends JpaSupportService<DormitorySpace, Long> {
    List<DormitorySpace> listRootSpaces();

    boolean hasChildSpace(DormitorySpace parentSpace);

    List<DormitorySpace> listChildSpace(DormitorySpace parentSpace, Pageable pageable);

    List<DormitorySpace> listChildSpaceRecursive(DormitorySpace dormitorySpace);

    List<DormitorySpace> listSpaceByType(DormitorySpaceType spaceType);

    int countChildSpace(DormitorySpace parentSpace);

    List<DormitorySpace> findByNameContains(String name, Pageable pageable);

    int countByNameContains(String name);

    void updateOccupy(DormitorySpace berthSpace, int occupyAmount);

    DormitorySpace findAvailableBerth(DormitorySpace dormitorySpace) throws DormitoryManageException;

}
