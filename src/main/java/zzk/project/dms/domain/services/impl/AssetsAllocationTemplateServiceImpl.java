//package zzk.project.dms.domain.services.impl;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.transaction.annotation.Transactional;
//import zzk.project.dms.domain.dao.AssetsAllocationsTemplateRepository;
//import zzk.project.dms.domain.entities.AssetsAllocationTemplate;
//import zzk.project.dms.domain.services.AssetsAllocationTemplateService;
//import zzk.project.dms.middle.ServiceAndSubscriber;
//
//@Transactional
//@ServiceAndSubscriber
//public class AssetsAllocationTemplateServiceImpl implements AssetsAllocationTemplateService {
//    private AssetsAllocationsTemplateRepository assetsAllocationsTemplateRepository;
//    @Override
//    public JpaRepository<AssetsAllocationTemplate, Long> getRepository() {
//        return assetsAllocationsTemplateRepository;
//    }
//
//    @Override
//    public AssetsAllocationTemplate findByName(String templateName) {
//        return assetsAllocationsTemplateRepository.findByName(templateName);
//    }
//}
