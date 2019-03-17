package zzk.project.dms.domain.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import zzk.project.dms.domain.entities.Tenement;

public interface TenementRepository extends JpaRepository<Tenement, Long> {
    Page<Tenement> findAllByNameContains(String name, Pageable pageable);

    int countAllByNameContains(@Param("name") String name);

    int countByDormitorySpaceIsNotNull();
}
