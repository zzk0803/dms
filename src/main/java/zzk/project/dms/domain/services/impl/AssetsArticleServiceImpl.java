package zzk.project.dms.domain.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import zzk.project.dms.domain.dao.AssetsAllocationRepository;
import zzk.project.dms.domain.dao.AssetsArticleRepository;
import zzk.project.dms.domain.entities.AssetsArticle;
import zzk.project.dms.domain.services.AssetsArticleService;
import zzk.project.dms.middle.ServiceAndSubscriber;

@Transactional
@ServiceAndSubscriber
public class AssetsArticleServiceImpl implements AssetsArticleService {

    @Autowired
    private AssetsArticleRepository assetsArticleRepository;

    @Override
    public JpaRepository<AssetsArticle, Long> getRepository() {
        return assetsArticleRepository;
    }
}
