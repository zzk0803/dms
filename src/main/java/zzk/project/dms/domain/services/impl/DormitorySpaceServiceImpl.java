package zzk.project.dms.domain.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import zzk.project.dms.domain.DormitoryManageException;
import zzk.project.dms.domain.dao.DormitorySpaceRepository;
import zzk.project.dms.domain.entities.DormitorySpace;
import zzk.project.dms.domain.entities.DormitorySpaceType;
import zzk.project.dms.domain.services.DormitorySpaceService;
import zzk.project.dms.middle.SubscriberAndService;

import java.util.Collections;
import java.util.List;

@SubscriberAndService
@Transactional
public class DormitorySpaceServiceImpl implements DormitorySpaceService {

    @Autowired
    private DormitorySpaceRepository dormitorySpaceRepository;

    @Override
    public JpaRepository<DormitorySpace, Long> getRepository() {
        return dormitorySpaceRepository;
    }

    @Override
    public List<DormitorySpace> listRootSpaces() {
        return dormitorySpaceRepository.findDormitorySpacesByParent(null);
    }

    @Override
    public List<DormitorySpace> listChildSpace(DormitorySpace Parent) {
        return dormitorySpaceRepository.findDormitorySpacesByParent(Parent);
    }

    @Override
    public List<DormitorySpace> listSpaceByType(DormitorySpaceType spaceType) {
        return dormitorySpaceRepository.findDormitorySpacesByType(spaceType);
    }

    @Override
    public boolean hasChildSpace(DormitorySpace Parent) {
        return dormitorySpaceRepository.existsDormitorySpacesByParent(Parent);
    }

    @Override
    public int countChildSpace(DormitorySpace Parent) {
        return dormitorySpaceRepository.countDormitorySpacesByParent(Parent);
    }

    @Override
    public DormitorySpace allocateFromParentByExplicitCapacity(DormitorySpace parent, int allocate) throws DormitoryManageException {
        checkAllocatable(parent);

        return null;
    }


    @Override
    public List<DormitorySpace> allocateFromParentByExplicitNumber(DormitorySpace parent, int childNumber) throws DormitoryManageException {
        checkAllocatable(parent);

        return Collections.emptyList();
    }

    private void checkAllocatable(DormitorySpace parent) {
        if (parent.getType() == DormitorySpaceType.BERTH) {
            throw new DormitoryManageException("床位不能再分配了");
        }
    }
}
