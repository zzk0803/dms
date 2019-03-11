package zzk.project.dms.domain.services;

import zzk.project.dms.domain.DormitoryManageException;
import zzk.project.dms.domain.entities.DormitorySpace;
import zzk.project.dms.domain.entities.DormitorySpaceType;

import java.util.List;

public interface DormitorySpaceService extends JpaSupportService<DormitorySpace, Long> {
    List<DormitorySpace> listRootSpaces();

    List<DormitorySpace> listChildSpace(DormitorySpace upperSpace);

    List<DormitorySpace> listSpaceByType(DormitorySpaceType spaceType);

    boolean hasChildSpace(DormitorySpace upperSpace);

    int countChildSpace(DormitorySpace upperSpace);

    DormitorySpace allocateFromParentByExplicitCapacity(DormitorySpace parent, int childCapacity) throws DormitoryManageException;

    List<DormitorySpace> allocateFromParentByExplicitNumber(DormitorySpace parent, int childNumber) throws DormitoryManageException;
}
