package zzk.project.dms;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import zzk.project.dms.domain.entities.DormitorySpace;
import zzk.project.dms.domain.entities.DormitorySpaceType;
import zzk.project.dms.domain.services.DormitorySpaceService;
import zzk.project.dms.domain.services.FinancialRecordService;
import zzk.project.dms.domain.services.TenementService;
import zzk.project.dms.utilies.SpringBeansUtil;

@SpringBootApplication()
public class DmsApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(DmsApplication.class, args);
        SpringBeansUtil.setApplicationContext(applicationContext);
    }

    @Bean
    public InitializingBean initializingBean(
            @Autowired DormitorySpaceService dormitorySpaceService,
            @Autowired TenementService tenementService,
//            @Autowired AssetsService assetsService,
            @Autowired FinancialRecordService financialRecordService
    ) {
        return () -> {
            DormitorySpace dormitorySpace = new DormitorySpace();
            dormitorySpace.setName("万人坑");
            dormitorySpace.setType(DormitorySpaceType.COMMUNITY);
            dormitorySpace.setCapacity(10000);
            dormitorySpaceService.put(dormitorySpace);
        };
    }

}
