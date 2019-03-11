package zzk.project.dms.domain.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import zzk.project.dms.domain.dao.AssetsAllocationRepository;
import zzk.project.dms.domain.dao.AssetsAllocationsTemplateRepository;
import zzk.project.dms.domain.dao.AssetsArticleRepository;
import zzk.project.dms.domain.entities.AssetsAllocation;
import zzk.project.dms.domain.entities.AssetsAllocationTemplate;
import zzk.project.dms.domain.entities.AssetsArticle;
import zzk.project.dms.domain.entities.DormitorySpace;
import zzk.project.dms.domain.services.AssetsService;
import zzk.project.dms.middle.SubscriberAndService;

import java.util.List;

@SubscriberAndService
public class AssetsServiceImpl implements AssetsService {

    @Autowired
    private AssetsAllocationRepository assetsAllocationRepository;

    @Autowired
    private AssetsAllocationsTemplateRepository assetsAllocationsTemplateRepository;

    @Autowired
    private AssetsArticleRepository assetsArticleRepository;

    @Override
    public AssetsAllocationTemplate putAssetsAllocationTemplate(AssetsAllocationTemplate assetsAllocationTemplate) {
        return assetsAllocationsTemplateRepository.save(assetsAllocationTemplate);
    }

    @Override
    public AssetsAllocationTemplate findAssetsAllocationTemplateByName(String templateName) {
        return assetsAllocationsTemplateRepository.findByName(templateName);
    }

    @Override
    public AssetsAllocationTemplate deleteAssetsAllocationTemplate(AssetsAllocationTemplate assetsAllocationTemplate) {
        assetsAllocationTemplate.setDeprecated(true);
        return assetsAllocationsTemplateRepository.saveAndFlush(assetsAllocationTemplate);
    }

    @Override
    public List<AssetsAllocationTemplate> findAssetsAllocationTemplate() {
        return assetsAllocationsTemplateRepository.findAll();
    }

    @Override
    public AssetsAllocation putAssetsAllocation(AssetsAllocation assetsAllocation) {
        return assetsAllocationRepository.save(assetsAllocation);
    }

    @Override
    public AssetsAllocation deleteAssetsAllocation(AssetsAllocation assetsAllocation) {
        assetsAllocationRepository.delete(assetsAllocation);
        return assetsAllocation;
    }

    @Override
    public List<AssetsAllocation> findAssetsAllocationByDormitorySpace(DormitorySpace dormitorySpace) {
        return assetsAllocationRepository.findAllByDormitorySpace(dormitorySpace);
    }

    @Override
    public AssetsArticle putAssetsArticle(AssetsArticle assetsArticle) {
        return assetsArticleRepository.save(assetsArticle);
    }

    @Override
    public AssetsArticle deleteAssetsArticle(AssetsArticle assetsArticle) {
        assetsArticleRepository.delete(assetsArticle);
        return assetsArticle;
    }

    @Override
    public List<AssetsArticle> findAssetsArticle(AssetsArticle assetsArticle) {
        return assetsArticleRepository.findAll();
    }
}
