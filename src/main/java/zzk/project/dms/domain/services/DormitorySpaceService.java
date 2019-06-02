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

    boolean isFormerChildOfLatter(DormitorySpace former, DormitorySpace latter);

    List<DormitorySpace> listChildSpace(DormitorySpace parentSpace);

    List<DormitorySpace> listChildSpace(DormitorySpace parentSpace, Pageable pageable);

    List<DormitorySpace> listChildSpaceRecursive(DormitorySpace dormitorySpace);

    List<DormitorySpace> listSpaceByType(DormitorySpaceType spaceType);

    int countChildSpace(DormitorySpace parentSpace);

    List<DormitorySpace> findByNameContains(String name, Pageable pageable);

    int countByNameContains(String name);

    DormitorySpace allocateFromParentByExplicitCapacity(DormitorySpace parent, int allocate) throws DormitoryManageException;

    DormitorySpace allocateFromParentByExplicitCapacity(DormitorySpace parent, String childName, int allocate) throws DormitoryManageException;

    /**
     * 通过数量均分父级空间
     * 分割后的子空间已持久化
     *
     * @param parent
     * @param childNumber
     * @return List<DormitorySpace>
     * @throws DormitoryManageException
     */
    List<DormitorySpace> allocateFromParentByExplicitNumberByEqualization(DormitorySpace parent, int childNumber) throws DormitoryManageException;

    /**
     * 通过大小均分父级空间
     * 分割后的子空间已持久化
     *
     * @param parent
     * @param allocate
     * @return List<DormitorySpace>
     * @throws DormitoryManageException
     */
    List<DormitorySpace> allocateFromParentByExplicitAllocationByEqualization(DormitorySpace parent, int allocate) throws DormitoryManageException;

    void updateOccupy(DormitorySpace berthSpace, int occupyAmount);

    DormitorySpace findAvailableBerth(DormitorySpace dormitorySpace) throws DormitoryManageException;

    void updateDivided(DormitorySpace dormitorySpace, int divided);
}
