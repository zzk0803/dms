package zzk.project.dms.ui.tenement;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import zzk.project.dms.domain.entities.Tenement;
import zzk.project.dms.domain.services.TenementService;

import java.util.stream.Stream;

public class TenementNameFilterAndPageableDataProvider extends AbstractBackEndDataProvider<Tenement, String> {

    private TenementService tenementService;

    public TenementNameFilterAndPageableDataProvider(TenementService tenementService) {
        this.tenementService = tenementService;
    }

    @Override
    protected Stream<Tenement> fetchFromBackEnd(Query<Tenement, String> query) {
        return tenementService.filterFromBackend(query.getFilter().orElse(""), getPageable(query)).stream();
    }

    @Override
    protected int sizeInBackEnd(Query<Tenement, String> query) {
        return tenementService.sizeInBackend(query.getFilter().orElse(""));
    }

    private Pageable getPageable(Query<Tenement, String> query) {
        return PageRequest.of(query.getOffset() / query.getLimit(), query.getLimit());
    }
}
