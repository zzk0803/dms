package zzk.project.dms;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import zzk.project.dms.domain.entities.DormitorySpace;
import zzk.project.dms.domain.services.DormitoryManagementService;

@SpringBootApplication
public class DmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DmsApplication.class, args);
    }

    @Bean
    public InitializingBean initializingBean(@Autowired DormitoryManagementService service) {
        return () -> {
            DormitorySpace root = new DormitorySpace();
            root.setName("万人坑");
            root.setType(DormitorySpace.SpaceType.COMMUNITY);
            root.setCapacity(10000);
            service.createOrUpdate(root);

            DormitorySpace unitOne = new DormitorySpace();
            unitOne.setName("一单元");
            unitOne.setCapacity(3000);
            unitOne.setType(DormitorySpace.SpaceType.BUILDING);
            unitOne.setUpperSpace(root);
            service.createOrUpdate(unitOne);

            DormitorySpace floorOne = new DormitorySpace();
            floorOne.setName("一楼");
            floorOne.setCapacity(500);
            floorOne.setType(DormitorySpace.SpaceType.FLOOR);
            floorOne.setUpperSpace(unitOne);
            service.createOrUpdate(floorOne);

            DormitorySpace roomOne = new DormitorySpace();
            roomOne.setName("01寝室");
            roomOne.setCapacity(8);
            roomOne.setType(DormitorySpace.SpaceType.ROOM);
            roomOne.setUpperSpace(floorOne);
            service.createOrUpdate(roomOne);

            DormitorySpace berth;
            for (int i = 0; i < 7; i++) {
                berth = new DormitorySpace();
                berth.setName(i + 1 + "床位");
                berth.setCapacity(1);
                berth.setType(DormitorySpace.SpaceType.BERTH);
                berth.setUpperSpace(roomOne);
                service.createOrUpdate(berth);
            }
        };
    }

}
