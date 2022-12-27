package zzk.project.dms;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;
import zzk.project.dms.domain.entities.Account;
import zzk.project.dms.domain.entities.DormitorySpace;
import zzk.project.dms.domain.entities.DormitorySpaceType;
import zzk.project.dms.domain.services.*;

import java.util.List;
import java.util.concurrent.Executors;

@SpringBootApplication
public class DmsApplication {

    private static ConfigurableApplicationContext applicationContext;

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(DmsApplication.class, args);

//        SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder();
//        springApplicationBuilder.sources(DmsApplication.class)
//                .parent()
//                .child()
//                .bannerMode(Banner.Mode.OFF)
//                .run(args)
    }

    public static <T> T getBean(Class<T> type) {
        return applicationContext.getBean(type);
    }

    @Bean
    public InitializingBean initializingBean(
            @Autowired DormitorySpaceService dormitorySpaceService,
            @Autowired DormitorySpaceAllocationService dormitorySpaceAllocationService,
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

            List<DormitorySpace> builds = dormitorySpaceAllocationService.allocateFromParentByExplicitNumberByEqualization(root, 4);
            builds.forEach(
                    build -> {
                        List<DormitorySpace> floors = dormitorySpaceAllocationService.allocateFromParentByExplicitNumberByEqualization(build, 5);
                        floors.forEach(floor -> {
                            List<DormitorySpace> rooms = dormitorySpaceAllocationService.allocateFromParentByExplicitAllocationByEqualization(floor, 10);
                            rooms.forEach(room -> dormitorySpaceAllocationService.allocateFromParentByExplicitAllocationByEqualization(room, 1));
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
