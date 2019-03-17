package zzk.project.dms.domain.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zzk.project.dms.domain.DormitoryManageException;
import zzk.project.dms.domain.entities.DormitorySpace;
import zzk.project.dms.domain.entities.DormitorySpaceType;
import zzk.project.dms.domain.services.common.JpaSupportService;

import java.util.List;

public interface DormitorySpaceService extends JpaSupportService<DormitorySpace, Long> {
    List<DormitorySpace> listRootSpaces();

    boolean hasChildSpace(DormitorySpace parentSpace);
    List<DormitorySpace> listChildSpace(DormitorySpace parentSpace);
    int countChildSpace(DormitorySpace parentSpace);

    Page<DormitorySpace> findByNameContains(String name, Pageable pageable);
    int countByNameContains(String name);

    List<DormitorySpace> listSpaceByType(DormitorySpaceType spaceType);

    DormitorySpace allocateFromParentByExplicitCapacity(DormitorySpace parent, int allocate) throws DormitoryManageException;
    DormitorySpace allocateFromParentByExplicitCapacity(DormitorySpace parent, String childName, int allocate) throws DormitoryManageException;
    List<DormitorySpace> allocateFromParentByExplicitNumber(DormitorySpace parent, int childNumber) throws DormitoryManageException;
    List<DormitorySpace> allocateFromParentByExplicitAllocationByEqualization(DormitorySpace parent, int allocate) throws DormitoryManageException;

    void updateOccupy(DormitorySpace berthSpace, int occupyAmount);
    DormitorySpace findAvailableBerth(DormitorySpace airborne) throws DormitoryManageException;


}
