package zzk.project.dms.domain.services;

import zzk.project.dms.domain.DormitoryManageException;
import zzk.project.dms.domain.entities.Tenement;
import zzk.project.dms.domain.services.common.DataProviderSupportService;

public interface TenementService extends DataProviderSupportService<Tenement, Long,String> {
    void distributeBerthForTenement(Tenement tenement) throws DormitoryManageException;
}
