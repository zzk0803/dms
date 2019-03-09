package zzk.project.dms.domain.services;

import zzk.project.dms.domain.entities.DormitorySpace;

import java.util.List;

public interface DormitoryManagementService extends JpaSupportServiceTemplate<DormitorySpace, Long> {
    List<DormitorySpace> listRootSpaces();

    List<DormitorySpace> listChildSpace(DormitorySpace upperSpace);

    List<DormitorySpace> listSpaceByType(DormitorySpace.SpaceType spaceType);

    boolean hasChildSpace(DormitorySpace upperSpace);

    int countChildSpace(DormitorySpace upperSpace);
}
