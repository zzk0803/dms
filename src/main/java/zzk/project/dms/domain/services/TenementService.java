package zzk.project.dms.domain.services;

import org.springframework.data.domain.Pageable;
import zzk.project.dms.domain.DormitoryManageException;
import zzk.project.dms.domain.entities.Tenement;
import zzk.project.dms.domain.services.common.FormSupportService;

import java.util.List;

public interface TenementService extends FormSupportService<Tenement, Long> {
    void distributeBerthForTenement(Tenement tenement) throws DormitoryManageException;

    List<Tenement> filterFromBackend(String name, Pageable pageable);

    int sizeInBackend(String name);
}
