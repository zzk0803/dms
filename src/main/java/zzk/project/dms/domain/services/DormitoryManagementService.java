package zzk.project.dms.domain.services;

import zzk.project.dms.domain.DormitoryManageException;
import zzk.project.dms.domain.entities.DormitorySpace;

import java.util.List;

public interface DormitoryManagementService {
    /**
     * 创建或更新住宅空间
     * @param space
     * @return
     */
    String createOrUpdate(DormitorySpace space);

    String delete(DormitorySpace space) throws DormitoryManageException;

    List<DormitorySpace> listRootSpaces();

    List<DormitorySpace> listChildSpace(DormitorySpace upperSpace);

    List<DormitorySpace> listSpaceByType(DormitorySpace.SpaceType spaceType);

    boolean hasChildSpace(DormitorySpace upperSpace);

    int countChildSpace(DormitorySpace upperSpace);
}
