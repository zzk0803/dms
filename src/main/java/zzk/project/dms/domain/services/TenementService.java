package zzk.project.dms.domain.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zzk.project.dms.domain.DormitoryManageException;
import zzk.project.dms.domain.entities.Tenement;
import zzk.project.dms.domain.services.common.FormSupportService;

public interface TenementService extends FormSupportService<Tenement, Long> {
    void distributeBerthForTenement(Tenement tenement) throws DormitoryManageException;

    Page<Tenement> filterFromBackend(String name, Pageable pageable);

    int sizeInBackend(String name);
}
