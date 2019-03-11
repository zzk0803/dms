package zzk.project.dms.domain.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import zzk.project.dms.domain.dao.TenementRepository;
import zzk.project.dms.domain.entities.Tenement;
import zzk.project.dms.domain.services.TenementService;
import zzk.project.dms.middle.SubscriberAndService;

@SubscriberAndService
@Transactional
public class TenementServiceImpl implements TenementService {

    @Autowired
    private TenementRepository tenementRepository;

    @Override
    public JpaRepository<Tenement, Long> getRepository() {
        return tenementRepository;
    }
}
