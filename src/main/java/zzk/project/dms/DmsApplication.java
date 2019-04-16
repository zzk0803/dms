package zzk.project.dms;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import zzk.project.dms.domain.entities.*;
import zzk.project.dms.domain.services.AccountService;
import zzk.project.dms.domain.services.DormitorySpaceService;
import zzk.project.dms.domain.services.FinancialRecordService;
import zzk.project.dms.domain.services.TenementService;

import java.util.List;

@SpringBootApplication
public class DmsApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(DmsApplication.class, args);
    }

    @Bean
    public InitializingBean initializingBean(
            @Autowired DormitorySpaceService dormitorySpaceService,
            @Autowired TenementService tenementService,
            @Autowired FinancialRecordService financialRecordService,
            @Autowired AccountService accountService

    ) {
        return () -> {
            DormitorySpace dormitorySpace = new DormitorySpace();
            dormitorySpace.setName("小社区");
            dormitorySpace.setType(DormitorySpaceType.COMMUNITY);
            dormitorySpace.setCapacity(1000);
            DormitorySpace root = dormitorySpaceService.put(dormitorySpace);

            List<DormitorySpace> builds = dormitorySpaceService.allocateFromParentByExplicitNumberByEqualization(root, 4);
            builds.forEach(
                    build -> {
                        List<DormitorySpace> floors = dormitorySpaceService.allocateFromParentByExplicitNumberByEqualization(build, 5);
                        floors.forEach(floor -> {
                            List<DormitorySpace> rooms = dormitorySpaceService.allocateFromParentByExplicitAllocationByEqualization(floor, 10);
                            rooms.forEach(room->dormitorySpaceService.allocateFromParentByExplicitAllocationByEqualization(room, 1));
                        });
                    }
            );

            Account adminAccount = new Account();
            adminAccount.setUsername("admin");
            adminAccount.setPassword("admin");
            accountService.register(adminAccount);
        };
    }

}
