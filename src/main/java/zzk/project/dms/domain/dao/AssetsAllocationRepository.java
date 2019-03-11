package zzk.project.dms.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zzk.project.dms.domain.entities.AssetsAllocation;
import zzk.project.dms.domain.entities.DormitorySpace;

import java.util.List;

public interface AssetsAllocationRepository extends JpaRepository<AssetsAllocation,Long> {
    List<AssetsAllocation> findAllByDormitorySpace(DormitorySpace dormitorySpace);
}
