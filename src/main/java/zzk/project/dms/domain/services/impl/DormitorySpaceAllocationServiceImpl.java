package zzk.project.dms.domain.services.impl;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zzk.project.dms.domain.DormitoryManageException;
import zzk.project.dms.domain.entities.DormitorySpace;
import zzk.project.dms.domain.entities.DormitorySpaceType;
import zzk.project.dms.domain.services.DormitorySpaceAllocationService;
import zzk.project.dms.domain.services.DormitorySpaceService;
import zzk.project.dms.domain.services.common.JpaSupportService;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Service
public class DormitorySpaceAllocationServiceImpl implements DormitorySpaceAllocationService {


    private DormitorySpaceServiceImpl dormitorySpaceService;

    @Autowired
    public DormitorySpaceAllocationServiceImpl(DormitorySpaceService dormitorySpaceService) {
        this.dormitorySpaceService = (DormitorySpaceServiceImpl) dormitorySpaceService;
    }

    @Override
    public DormitorySpace allocateFromParentByExplicitCapacity(DormitorySpace parent, int allocate) throws DormitoryManageException {
        checkAllocatable(parent, allocate);

        //设置父级空间的已划分数量
        parent.setHasDivided(parent.getHasDivided() + allocate);
        parent = dormitorySpaceService.save(parent);

        DormitorySpace newSpace = new DormitorySpace();
        newSpace.setParent(parent);
        newSpace.setCapacity(allocate);
        newSpace.setType(parent.getType().smaller());
        newSpace.setName(String.format("A %s Has Capacity=%d Of %s", newSpace.getType(), newSpace.getCapacity(), parent.getName()));
        dormitorySpaceService.save(newSpace);

        return newSpace;
    }

    @Override
    public DormitorySpace allocateFromParentByExplicitCapacity(DormitorySpace parent, String childName, int allocate) throws DormitoryManageException {
        checkAllocatable(parent, allocate);

        //设置父级空间的已划分数量
        dormitorySpaceService.updateDivided(parent, allocate);
        DormitorySpace modifiedParent = dormitorySpaceService.save(parent);

        DormitorySpace newSpace = new DormitorySpace();
        newSpace.setParent(modifiedParent);
        newSpace.setCapacity(allocate);
        newSpace.setType(parent.getType().smaller());
        newSpace.setName(childName);
        dormitorySpaceService.save(newSpace);

        return newSpace;
    }

    @Override
    public List<DormitorySpace> allocateFromParentByExplicitNumberByEqualization(DormitorySpace parent, int childNumber) throws DormitoryManageException {
        int capacity = parent.getCapacity();
        int hasDivided = parent.getHasDivided();
        int remain = capacity - hasDivided;
        int allocate = remain / childNumber;
        checkAllocatable(parent, allocate);
        DormitorySpace newSpace;
        List<DormitorySpace> allocatedResult = new LinkedList<>();
        for (int i = 0; i < childNumber; i++) {
            newSpace = allocateFromParentByExplicitCapacity(parent, parent.getType().smaller().getCn() + (i + 1), allocate);
            allocatedResult.add(newSpace);
        }
        return allocatedResult;
    }

    @Override
    public List<DormitorySpace> allocateFromParentByExplicitAllocationByEqualization(DormitorySpace parent, int allocate) throws DormitoryManageException {
        checkAllocatable(parent, allocate);
        int capacity = parent.getCapacity();
        int hasDivided = parent.getHasDivided();
        int remainCapacity = capacity - hasDivided;
        int availableNumber = remainCapacity / allocate;

        DormitorySpace newSpace;
        List<DormitorySpace> allocatedResult = new LinkedList<>();
        for (int i = 0; i < availableNumber; i++) {
            newSpace = allocateFromParentByExplicitCapacity(parent, parent.getType().smaller().getCn() + (i + 1), allocate);
            allocatedResult.add(newSpace);
        }
        return allocatedResult;
    }

    private void checkAllocatable(DormitorySpace parent, int allocate) throws DormitoryManageException {
        if (parent.getType() == DormitorySpaceType.BERTH) {
            throw new DormitoryManageException("父级必须是寝室级以上的单位才能进行划分");
        }

        if (allocate <= 0) {
            throw new DormitoryManageException("分配数额必须不小于0");
        }

        int capacity = parent.getCapacity();
        if (parent.getHasDivided() == capacity) {
            throw new DormitoryManageException(parent.getName() + "的划分数量已达上限");
        }

        int hasDivided = parent.getHasDivided();
        int remain = capacity - hasDivided;
        if (remain < allocate) {
            throw new DormitoryManageException("给定的分配数额已大于剩余的分配数额");
        }
    }


}
