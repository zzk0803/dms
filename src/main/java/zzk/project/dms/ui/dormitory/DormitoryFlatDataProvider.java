package zzk.project.dms.ui.dormitory;

import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.QuerySortOrderBuilder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.vaadin.artur.spring.dataprovider.FilterablePageableDataProvider;
import zzk.project.dms.domain.entities.DormitorySpace;
import zzk.project.dms.domain.services.DormitorySpaceService;

import java.util.List;

@SpringComponent(value = "dormitoryFlatDataProvider")
@Scope(value = "prototype")
public class DormitoryFlatDataProvider extends FilterablePageableDataProvider<DormitorySpace, String> {

    @Autowired
    private DormitorySpaceService dormitorySpaceService;

    @Override
    protected Page<DormitorySpace> fetchFromBackEnd(Query<DormitorySpace, String> query, Pageable pageable) {
        return dormitorySpaceService.findByNameContains(parseFilterString(query), pageable);
    }

    @Override
    protected List<QuerySortOrder> getDefaultSortOrders() {
        QuerySortOrderBuilder builder = new QuerySortOrderBuilder();
        List<QuerySortOrder> orders = builder.thenAsc("id")
                .build();
        return orders;
    }

    @Override
    protected int sizeInBackEnd(Query<DormitorySpace, String> query) {
        return dormitorySpaceService.countByNameContains(parseFilterString(query));
    }

    private String parseFilterString(Query<DormitorySpace, String> query) {
        return query.getFilter().orElse("");
    }
}
