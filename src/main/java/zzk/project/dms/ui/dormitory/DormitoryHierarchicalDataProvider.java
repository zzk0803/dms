package zzk.project.dms.ui.dormitory;

import com.vaadin.flow.data.provider.hierarchy.AbstractBackEndHierarchicalDataProvider;
import com.vaadin.flow.data.provider.hierarchy.HierarchicalQuery;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import zzk.project.dms.domain.entities.DormitorySpace;
import zzk.project.dms.domain.services.DormitorySpaceService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@SpringComponent
public class DormitoryHierarchicalDataProvider extends AbstractBackEndHierarchicalDataProvider<DormitorySpace, Void> {

    private DormitorySpaceService dormitorySpaceService;

    @Autowired
    public void setDormitorySpaceService(DormitorySpaceService dormitorySpaceService) {
        this.dormitorySpaceService = dormitorySpaceService;
    }

    @Override
    protected Stream<DormitorySpace> fetchChildrenFromBackEnd(HierarchicalQuery<DormitorySpace, Void> query) {
        int offset = query.getOffset();
        int limit = query.getLimit();
        DormitorySpace parent = query.getParent();
        if (Objects.nonNull(parent)) {
            List<DormitorySpace> childSpace = dormitorySpaceService.listChildSpace(parent, PageRequest.of(offset / limit, limit));
            return childSpace.stream();
        }
        return dormitorySpaceService.listRootSpaces().stream();
    }

    @Override
    public int getChildCount(HierarchicalQuery<DormitorySpace, Void> query) {
        DormitorySpace parent = query.getParent();
        if (Objects.nonNull(parent)) {
            return dormitorySpaceService.countChildSpace(parent);
        }
        return dormitorySpaceService.countChildSpace(null);
    }

    @Override
    public boolean hasChildren(DormitorySpace item) {
        return dormitorySpaceService.hasChildSpace(item);
    }

}
