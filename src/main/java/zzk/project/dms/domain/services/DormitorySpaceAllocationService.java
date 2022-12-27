package zzk.project.dms.domain.services;

import zzk.project.dms.domain.DormitoryManageException;
import zzk.project.dms.domain.entities.DormitorySpace;
import zzk.project.dms.domain.services.common.JpaSupportService;

import java.util.List;

public interface DormitorySpaceAllocationService  {
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
}
