//package zzk.project.dms.domain.services.impl;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import zzk.project.dms.domain.entities.AssetsAllocation;
//import zzk.project.dms.domain.entities.AssetsAllocationTemplate;
//import zzk.project.dms.domain.entities.AssetsArticle;
//import zzk.project.dms.domain.entities.DormitorySpace;
//import zzk.project.dms.domain.services.*;
//import zzk.project.dms.middle.ServiceAndSubscriber;
//
//import java.util.List;
//
//@ServiceAndSubscriber
//public class AssetsServiceImpl implements AssetsService {
//
//    @Autowired
//    private AssetsAllocationService assetsAllocationService;
//
//    @Autowired
//    private AssetsAllocationTemplateService assetsAllocationTemplateService;
//
//    @Autowired
//    private AssetsArticleService assetsArticleService;
//
//    public AssetsAllocationService getAssetsAllocationService() {
//        return assetsAllocationService;
//    }
//
//    public AssetsAllocationTemplateService getAssetsAllocationTemplateService() {
//        return assetsAllocationTemplateService;
//    }
//
//    public AssetsArticleService getAssetsArticleService() {
//        return assetsArticleService;
//    }
//
//    @Override
//    public AssetsAllocationTemplate putAssetsAllocationTemplate(AssetsAllocationTemplate assetsAllocationTemplate) {
//        return assetsAllocationTemplateService.save(assetsAllocationTemplate);
//    }
//
//    @Override
//    public AssetsAllocationTemplate findAssetsAllocationTemplateByName(String templateName) {
//        return assetsAllocationTemplateService.findByName(templateName);
//    }
//
//    @Override
//    public AssetsAllocationTemplate deleteAssetsAllocationTemplate(AssetsAllocationTemplate assetsAllocationTemplate) {
//        assetsAllocationTemplate.setDeprecated(true);
//        return assetsAllocationTemplateService.update(assetsAllocationTemplate);
//    }
//
//    @Override
//    public List<AssetsAllocationTemplate> findAssetsAllocationTemplate() {
//        return assetsAllocationTemplateService.findAll();
//    }
//
//    @Override
//    public AssetsAllocation putAssetsAllocation(AssetsAllocation assetsAllocation) {
//        return assetsAllocationService.save(assetsAllocation);
//    }
//
//    @Override
//    public AssetsAllocation deleteAssetsAllocation(AssetsAllocation assetsAllocation) {
//        assetsAllocationService.delete(assetsAllocation);
//        return assetsAllocation;
//    }
//
//    @Override
//    public List<AssetsAllocation> findAssetsAllocationByDormitorySpace(DormitorySpace dormitorySpace) {
//        return assetsAllocationService.findAllByDormitorySpace(dormitorySpace);
//    }
//
//    @Override
//    public AssetsArticle putAssetsArticle(AssetsArticle assetsArticle) {
//        return assetsArticleService.save(assetsArticle);
//    }
//
//    @Override
//    public AssetsArticle deleteAssetsArticle(AssetsArticle assetsArticle) {
//        assetsArticleService.delete(assetsArticle);
//        return assetsArticle;
//    }
//
//    @Override
//    public List<AssetsArticle> findAssetsArticle(AssetsArticle assetsArticle) {
//        return assetsArticleService.findAll();
//    }
//}
