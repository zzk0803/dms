package zzk.project.dms.domain.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zzk.project.dms.domain.DormitoryManageException;
import zzk.project.dms.domain.dao.DormitorySpaceRepository;
import zzk.project.dms.domain.entities.DormitorySpace;
import zzk.project.dms.domain.entities.DormitorySpaceType;
import zzk.project.dms.domain.services.DormitorySpaceService;
import zzk.project.dms.domain.services.TenementService;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(rollbackFor = DormitoryManageException.class)
public class DormitorySpaceServiceImpl implements DormitorySpaceService {

    private class SpaceTreeRepositoryIterator {
        private DormitorySpaceRepository dormitorySpaceRepository;
        private DormitorySpace parent;
        private DormitorySpace current;
        private List<DormitorySpace> result;

        public SpaceTreeRepositoryIterator(DormitorySpaceRepository dormitorySpaceRepository, DormitorySpace parent) {
            this.dormitorySpaceRepository = dormitorySpaceRepository;
            this.parent = parent;
            this.result = new LinkedList<>();
        }

        public void search() {
            this.current = this.parent;
            if (this.current.getType() == DormitorySpaceType.BERTH) {
                this.result.add(current);
            } else {
                search(this.current);
            }
        }

        private void search(DormitorySpace current) {
            List<DormitorySpace> child = dormitorySpaceRepository.findDormitorySpacesByParent(current);
            for (DormitorySpace space : child) {
                if (space.getType() != DormitorySpaceType.BERTH) {
                    search(space);
                } else {
                    result.add(space);
                }
            }
        }

        public List<DormitorySpace> getResult() {
            return result;
        }
    }

    @Autowired
    private DormitorySpaceRepository dormitorySpaceRepository;

    @Autowired
    private TenementService tenementService;

    @Override
    public JpaRepository<DormitorySpace, Long> getRepository() {
        return dormitorySpaceRepository;
    }

    @Override
    public DormitorySpace save(DormitorySpace entity) throws DormitoryManageException {
        final Long entityId = entity.getId();
        if (entityId != null) {
            final DormitorySpace oldSpace = findById(entityId);
            if (Objects.nonNull(oldSpace)) {
                final int capacity = entity.getCapacity();
                final int oldCapacity = oldSpace.getCapacity();
                if (capacity != oldCapacity && capacity < entity.getHasDivided()) {
                    throw new DormitoryManageException("更改宿舍空间大小，必须大于已分配的空间大小");
                } else {
                    getRepository().save(entity);
                }
            } else {
                throw new DormitoryManageException("DEV旧的空间虽然有ID，但判空DEV");
            }
        }
        return getRepository().save(entity);
    }

    @Override
    public List<DormitorySpace> listRootSpaces() {
        return dormitorySpaceRepository.findDormitorySpacesByParent(null);
    }

    @Override
    public List<DormitorySpace> listChildSpace(DormitorySpace parentSpace) {
        return dormitorySpaceRepository.findDormitorySpacesByParent(parentSpace);
    }

    @Override
    public List<DormitorySpace> listChildSpaceRecursive(DormitorySpace dormitorySpace) {
        SpaceTreeRepositoryIterator treeIterator = new SpaceTreeRepositoryIterator(this.dormitorySpaceRepository, dormitorySpace);
        treeIterator.search();
        return treeIterator.getResult();
    }

    @Override
    public List<DormitorySpace> listChildSpace(DormitorySpace parentSpace, Pageable pageable) {
        return dormitorySpaceRepository.findDormitorySpacesByParent(parentSpace, pageable).getContent();
    }

    @Override
    public List<DormitorySpace> listSpaceByType(DormitorySpaceType spaceType) {
        return dormitorySpaceRepository.findDormitorySpacesByType(spaceType);
    }

    @Override
    public boolean hasChildSpace(DormitorySpace parentSpace) {
        return dormitorySpaceRepository.existsDormitorySpacesByParent(parentSpace);
    }

    @Override
    public int countChildSpace(DormitorySpace parentSpace) {
        return dormitorySpaceRepository.countDormitorySpacesByParent(parentSpace);
    }

    @Override
    public List<DormitorySpace> findByNameContains(String name, Pageable pageable) {
        return dormitorySpaceRepository.findByNameContaining(name, pageable).getContent();
    }

    @Override
    public int countByNameContains(String name) {
        return dormitorySpaceRepository.countByNameContains(name);
    }

    @Override
    public DormitorySpace allocateFromParentByExplicitCapacity(DormitorySpace parent, int allocate) throws DormitoryManageException {
        checkAllocatable(parent, allocate);

        //设置父级空间的已划分数量
        parent.setHasDivided(parent.getHasDivided() + allocate);
        parent = save(parent);

        DormitorySpace newSpace = new DormitorySpace();
        newSpace.setParent(parent);
        newSpace.setCapacity(allocate);
        newSpace.setType(parent.getType().smaller());
        newSpace.setName(String.format("A %s Has Capacity=%d Of %s", newSpace.getType(), newSpace.getCapacity(), parent.getName()));
        save(newSpace);

        return newSpace;
    }

    @Override
    public DormitorySpace allocateFromParentByExplicitCapacity(DormitorySpace parent, String childName, int allocate) throws DormitoryManageException {
        checkAllocatable(parent, allocate);

        //设置父级空间的已划分数量
        updateDivided(parent, allocate);
        DormitorySpace modifiedParent = save(parent);

        DormitorySpace newSpace = new DormitorySpace();
        newSpace.setParent(modifiedParent);
        newSpace.setCapacity(allocate);
        newSpace.setType(parent.getType().smaller());
        newSpace.setName(childName);
        save(newSpace);

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

    @Override
    public DormitorySpace findAvailableBerth(DormitorySpace dormitorySpace) throws DormitoryManageException {
        DormitorySpace assumeAvailable = dormitorySpaceRepository.findFirstByAvailableAndParent(true, dormitorySpace);
        if (assumeAvailable == null) {
            throw new DormitoryManageException("已找不到空闲的床位了");
        } else {
            if (assumeAvailable.getType() == DormitorySpaceType.BERTH) {
                return assumeAvailable;
            } else {
                return findAvailableBerth(assumeAvailable);
            }
        }
    }

    @Override
    public void updateOccupy(DormitorySpace berthSpace, int occupyAmount) {
        List<DormitorySpace> spaces = new LinkedList<>();
        DormitorySpace current = berthSpace;
        while (Objects.nonNull(current)) {
            spaces.add(current);
            int occupy = current.getHasOccupy() + occupyAmount;
            current.setHasOccupy(occupy);
            current.setAvailable((current.getCapacity() - current.getHasOccupy()) > 0);
            current = current.getParent();
        }
        dormitorySpaceRepository.saveAll(spaces);
    }

    @Override
    public void updateDivided(DormitorySpace dormitorySpace, int divided) {
        int hasDivided = dormitorySpace.getHasDivided();
        dormitorySpace.setHasDivided(hasDivided + divided);
    }

    @Override
    public DormitorySpace delete(DormitorySpace dormitorySpace) throws DormitoryManageException {
        if (hasChildSpace(dormitorySpace)) {
            throw new DormitoryManageException("该区域还有子区域，请先删除未有住户占有的子区域后，再删除该区域");
        }

        if (tenementService.countTenementInSpace(dormitorySpace) != 0) {
            throw new DormitoryManageException("该区域还有住宿的人员，请妥善处理住宿的人员后，再删除该区域");
        }

        DormitorySpace parent = dormitorySpace.getParent();
        if (parent != null) {
            updateDivided(parent, -dormitorySpace.getCapacity());
            save(parent);
        }
        getRepository().delete(dormitorySpace);
        return null;
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

    @Override
    public boolean isFormerChildOfLatter(DormitorySpace former, DormitorySpace latter) {
        if (former == null || latter == null) {
            return false;
        }

        DormitorySpace currentFormer = former;
        while (currentFormer != null) {
            DormitorySpace formerParent = currentFormer.getParent();
            if (formerParent != null && formerParent.equals(latter)) {
                return true;
            }
            currentFormer = formerParent;
        }

        return false;
    }
}
