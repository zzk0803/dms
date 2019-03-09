package zzk.project.dms.domain.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zzk.project.dms.domain.dao.DormitorySpaceRepository;
import zzk.project.dms.domain.entities.DormitorySpace;
import zzk.project.dms.domain.services.DormitoryManagementService;

import java.util.List;

@Service
@Transactional
public class DormitoryManagementServiceImpl implements DormitoryManagementService {

    @Autowired
    private DormitorySpaceRepository dormitorySpaceRepository;

    @Override
    public JpaRepository<DormitorySpace, Long> getRepository() {
        return dormitorySpaceRepository;
    }

    @Override
    public List<DormitorySpace> listRootSpaces() {
        return dormitorySpaceRepository.findDormitorySpacesByUpperSpace(null);
    }

    @Override
    public List<DormitorySpace> listChildSpace(DormitorySpace upperSpace) {
        return dormitorySpaceRepository.findDormitorySpacesByUpperSpace(upperSpace);
    }

    @Override
    public List<DormitorySpace> listSpaceByType(DormitorySpace.SpaceType spaceType) {
        return dormitorySpaceRepository.findDormitorySpacesByType(spaceType);
    }

    @Override
    public boolean hasChildSpace(DormitorySpace upperSpace) {
        return dormitorySpaceRepository.existsDormitorySpacesByUpperSpace(upperSpace);
    }

    @Override
    public int countChildSpace(DormitorySpace upperSpace) {
        return dormitorySpaceRepository.countDormitorySpacesByUpperSpace(upperSpace);
    }
}
