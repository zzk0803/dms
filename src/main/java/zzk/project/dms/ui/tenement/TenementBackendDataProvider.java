package zzk.project.dms.ui.tenement;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.QuerySortOrderBuilder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.vaadin.artur.spring.dataprovider.FilterablePageableDataProvider;
import zzk.project.dms.domain.dao.TenementRepository;
import zzk.project.dms.domain.entities.Tenement;
import zzk.project.dms.domain.services.TenementService;

import java.util.List;
import java.util.stream.Stream;

@SpringComponent
public class TenementBackendDataProvider extends FilterablePageableDataProvider<Tenement, Void> {

    @Autowired
    private TenementRepository tenementRepository;

    @Override
    protected Page<Tenement> fetchFromBackEnd(Query<Tenement, Void> query, Pageable pageable) {
        return tenementRepository.findAll(pageable);
    }

    @Override
    protected List<QuerySortOrder> getDefaultSortOrders() {
        QuerySortOrderBuilder builder = new QuerySortOrderBuilder();
        return builder.thenAsc("id").build();
    }

    @Override
    protected int sizeInBackEnd(Query<Tenement, Void> query) {
        return (int) tenementRepository.count();
    }
}
