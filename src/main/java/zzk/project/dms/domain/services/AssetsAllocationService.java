package zzk.project.dms.domain.services;

import zzk.project.dms.domain.entities.AssetsAllocation;
import zzk.project.dms.domain.entities.DormitorySpace;

import java.util.List;

public interface AssetsAllocationService extends DataProviderSupportService<AssetsAllocation, Long, Void> {

    List<AssetsAllocation> findAllByDormitorySpace(DormitorySpace dormitorySpace);
}
