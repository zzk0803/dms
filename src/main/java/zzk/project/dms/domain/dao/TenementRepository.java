package zzk.project.dms.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zzk.project.dms.domain.entities.Tenement;

import java.util.List;

public interface TenementRepository extends JpaRepository<Tenement, Long> {
    List<Tenement> findDistinctByNameContains(String name);

   int countDistinctByNameContains(String name);

    int countByDormitorySpaceIsNotNull();
}
