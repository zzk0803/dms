package zzk.project.dms;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import zzk.project.dms.domain.entities.*;
import zzk.project.dms.domain.services.AccountService;
import zzk.project.dms.domain.services.DormitorySpaceService;
import zzk.project.dms.domain.services.FinancialRecordService;
import zzk.project.dms.domain.services.TenementService;

import java.util.List;
import java.util.concurrent.Executors;

@SpringBootApplication
public class DmsApplication {

    private static ConfigurableApplicationContext applicationContext;

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(DmsApplication.class, args);
    }

    public static <T> T getBean(Class<T> type) {
        return applicationContext.getBean(type);
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
            DormitorySpace root = dormitorySpaceService.save(dormitorySpace);

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

    @Bean
    @Scope("prototype")
    public EventBus eventBus() {
        return new AsyncEventBus(Executors.newCachedThreadPool());
    }

}
