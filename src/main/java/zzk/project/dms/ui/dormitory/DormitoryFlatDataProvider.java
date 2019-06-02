package zzk.project.dms.ui.dormitory;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import zzk.project.dms.domain.entities.DormitorySpace;
import zzk.project.dms.domain.services.DormitorySpaceService;

import java.util.stream.Stream;

public class DormitoryFlatDataProvider extends AbstractBackEndDataProvider<DormitorySpace, String> {

    private DormitorySpaceService dormitorySpaceService;

    public DormitoryFlatDataProvider(DormitorySpaceService dormitorySpaceService) {
        this.dormitorySpaceService = dormitorySpaceService;
    }

    @Override
    protected Stream<DormitorySpace> fetchFromBackEnd(Query<DormitorySpace, String> query) {
        return dormitorySpaceService.findByNameContains(parseFilterString(query), getPageable(query)).stream();
    }

    @Override
    protected int sizeInBackEnd(Query<DormitorySpace, String> query) {
        return dormitorySpaceService.countByNameContains(parseFilterString(query));
    }

    private Pageable getPageable(Query<DormitorySpace, String> query) {
        return PageRequest.of(query.getOffset() / query.getLimit(), query.getLimit());
    }

    private String parseFilterString(Query<DormitorySpace, String> query) {
        return query.getFilter().orElse("");
    }
}
