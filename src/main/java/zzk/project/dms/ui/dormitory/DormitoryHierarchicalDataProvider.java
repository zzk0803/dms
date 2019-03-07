package zzk.project.dms.ui.dormitory;

import com.vaadin.flow.data.provider.hierarchy.AbstractBackEndHierarchicalDataProvider;
import com.vaadin.flow.data.provider.hierarchy.HierarchicalQuery;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zzk.project.dms.domain.entities.DormitorySpace;
import zzk.project.dms.domain.services.DormitoryManagementService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@SpringComponent
public class DormitoryHierarchicalDataProvider extends AbstractBackEndHierarchicalDataProvider<DormitorySpace,Void> {

    private DormitoryManagementService dormitoryManagementService;

    public DormitoryManagementService getDormitoryManagementService() {
        return dormitoryManagementService;
    }

    @Autowired
    public void setDormitoryManagementService(DormitoryManagementService dormitoryManagementService) {
        this.dormitoryManagementService = dormitoryManagementService;
    }

    @Override
    protected Stream<DormitorySpace> fetchChildrenFromBackEnd(HierarchicalQuery<DormitorySpace, Void> query) {
        DormitorySpace parent = query.getParent();
        if (Objects.nonNull(parent)) {
            List<DormitorySpace> childSpace = dormitoryManagementService.listChildSpace(parent);
            return childSpace.stream();
        }
        return dormitoryManagementService.listRootSpaces().stream();
    }

    @Override
    public int getChildCount(HierarchicalQuery<DormitorySpace, Void> query) {
        DormitorySpace parent = query.getParent();
        if (Objects.nonNull(parent)) {
            return dormitoryManagementService.countChildSpace(parent);
        }
        return dormitoryManagementService.countChildSpace(null);
    }

    @Override
    public boolean hasChildren(DormitorySpace item) {
        return dormitoryManagementService.hasChildSpace(item);
    }

}
