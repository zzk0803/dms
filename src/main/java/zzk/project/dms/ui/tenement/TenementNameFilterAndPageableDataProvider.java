package zzk.project.dms.ui.tenement;

import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.QuerySortOrderBuilder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.vaadin.artur.spring.dataprovider.FilterablePageableDataProvider;
import zzk.project.dms.domain.entities.Tenement;
import zzk.project.dms.domain.services.TenementService;

import java.util.List;

public class TenementNameFilterAndPageableDataProvider extends FilterablePageableDataProvider<Tenement,String> {

    private TenementService tenementService;

    public TenementNameFilterAndPageableDataProvider(TenementService tenementService) {
        this.tenementService = tenementService;
    }

    @Override
    protected Page<Tenement> fetchFromBackEnd(Query<Tenement, String> query, Pageable pageable) {
        String filterString = query.getFilter().orElse("");
        return tenementService.filterFromBackend(filterString, pageable);
    }

    @Override
    protected List<QuerySortOrder> getDefaultSortOrders() {
        QuerySortOrderBuilder builder = new QuerySortOrderBuilder();
        return builder.thenAsc("id").build();
    }

    @Override
    protected int sizeInBackEnd(Query<Tenement, String> query) {
        String filterString = query.getFilter().orElse("");
        return tenementService.sizeInBackend(filterString);
    }
}
