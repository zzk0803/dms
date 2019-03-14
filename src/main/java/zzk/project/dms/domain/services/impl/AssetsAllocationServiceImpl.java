package zzk.project.dms.domain.services.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import zzk.project.dms.domain.dao.AssetsAllocationRepository;
import zzk.project.dms.domain.dao.AssetsArticleRepository;
import zzk.project.dms.domain.entities.AssetsAllocation;
import zzk.project.dms.domain.entities.DormitorySpace;
import zzk.project.dms.domain.services.AssetsAllocationService;
import zzk.project.dms.middle.ServiceAndSubscriber;

import java.util.List;

@Transactional
@ServiceAndSubscriber
public class AssetsAllocationServiceImpl implements AssetsAllocationService {
    private AssetsAllocationRepository assetsAllocationRepository;
    @Override
    public JpaRepository<AssetsAllocation, Long> getRepository() {
        return assetsAllocationRepository;
    }

    @Override
    public List<AssetsAllocation> findAllByDormitorySpace(DormitorySpace dormitorySpace) {
        return assetsAllocationRepository.findAllByDormitorySpace(dormitorySpace);
    }
}
