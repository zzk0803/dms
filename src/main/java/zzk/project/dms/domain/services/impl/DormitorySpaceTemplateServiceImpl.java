//package zzk.project.dms.domain.services.impl;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.transaction.annotation.Transactional;
//import zzk.project.dms.domain.dao.DormitorySpaceTemplateRepository;
//import zzk.project.dms.domain.entities.DormitorySpaceTemplate;
//import zzk.project.dms.domain.services.DormitorySpaceTemplateService;
//import zzk.project.dms.middle.ServiceAndSubscriber;
//
//@Transactional
//@ServiceAndSubscriber
//public class DormitorySpaceTemplateServiceImpl implements DormitorySpaceTemplateService {
//
//    @Autowired
//    private DormitorySpaceTemplateRepository dormitorySpaceTemplateRepository;
//
//    @Override
//    public JpaRepository<DormitorySpaceTemplate, Long> getRepository() {
//        return this.dormitorySpaceTemplateRepository;
//    }
//}
